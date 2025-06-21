package com.leclowndu93150.simple_villager_follow.goal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;
import java.util.function.Predicate;

public class VillagerFollowGoal extends Goal {
    private static final TargetingConditions FOLLOW_TARGETING = TargetingConditions.forNonCombat().ignoreLineOfSight();
    private final TargetingConditions targetingConditions;
    protected final PathfinderMob mob;
    private final double speedModifier;
    private final double range;
    private double px;
    private double py;
    private double pz;
    private double pRotX;
    private double pRotY;
    protected Player player;
    private int calmDown;
    private boolean isRunning;
    private final Predicate<ItemStack> items;
    private final boolean canScare;

    public VillagerFollowGoal(PathfinderMob pMob, double pSpeedModifier, Predicate<ItemStack> pItems, boolean pCanScare, double range) {
        this.mob = pMob;
        this.speedModifier = pSpeedModifier;
        this.items = pItems;
        this.canScare = pCanScare;
        this.range = range;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.targetingConditions = FOLLOW_TARGETING.copy().selector((entity, level) -> this.shouldFollow(entity));
    }

    public boolean canUse() {
        if (this.calmDown > 0) {
            --this.calmDown;
            return false;
        } else {
            if (this.mob.level() instanceof ServerLevel serverLevel) {
                this.player = serverLevel.getNearestPlayer(this.targetingConditions.range(this.range), this.mob);
                return this.player != null;
            }
            return false;
        }
    }

    private boolean shouldFollow(LivingEntity entity) {
        return this.items.test(entity.getMainHandItem()) || this.items.test(entity.getOffhandItem());
    }

    public boolean canContinueToUse() {
        if (this.canScare()) {
            if (this.mob.distanceToSqr(this.player) < (double)36.0F) {
                if (this.player.distanceToSqr(this.px, this.py, this.pz) > 0.010000000000000002) {
                    return false;
                }

                if (Math.abs((double)this.player.getXRot() - this.pRotX) > (double)5.0F || Math.abs((double)this.player.getYRot() - this.pRotY) > (double)5.0F) {
                    return false;
                }
            } else {
                this.px = this.player.getX();
                this.py = this.player.getY();
                this.pz = this.player.getZ();
            }

            this.pRotX = (double)this.player.getXRot();
            this.pRotY = (double)this.player.getYRot();
        }

        return this.canUse();
    }

    protected boolean canScare() {
        return this.canScare;
    }

    public void start() {
        this.px = this.player.getX();
        this.py = this.player.getY();
        this.pz = this.player.getZ();
        this.isRunning = true;
    }

    public void stop() {
        this.player = null;
        this.mob.getNavigation().stop();
        this.calmDown = reducedTickDelay(100);
        this.isRunning = false;
    }

    public void tick() {
        this.mob.getLookControl().setLookAt(this.player, (float)(this.mob.getMaxHeadYRot() + 20), (float)this.mob.getMaxHeadXRot());
        if (this.mob.distanceToSqr(this.player) < (double)6.25F) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().moveTo(this.player, this.speedModifier);
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }
}