package com.leclowndu93150.simple_villager_follow.mixin;


import com.leclowndu93150.simple_villager_follow.goal.VillagerFollowGoal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @Inject(method = "addFreshEntity", at = @At("HEAD"))
    private void onAddFreshEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof AbstractVillager villager) {
            villager.goalSelector.addGoal(3, new VillagerFollowGoal(villager, 1.0,
                    stack -> stack.is(Items.EMERALD) || stack.is(Items.EMERALD_BLOCK), false, 10));
        }
    }
}
