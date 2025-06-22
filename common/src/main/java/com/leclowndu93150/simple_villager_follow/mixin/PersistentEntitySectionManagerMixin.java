package com.leclowndu93150.simple_villager_follow.mixin;

import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PersistentEntitySectionManager.class)
public class PersistentEntitySectionManagerMixin<T extends EntityAccess> {

    @Inject(method = "addEntity", at = @At("HEAD"))
    private void onAddEntity(T pEntity, boolean pWorldGenSpawned, CallbackInfoReturnable<Boolean> cir) {
        if (pEntity instanceof AbstractVillager villager && !villager.level().isClientSide) {
            villager.goalSelector.addGoal(3, new TemptGoal(villager, 0.5,
                    stack -> stack.is(Items.EMERALD) || stack.is(Items.EMERALD_BLOCK), false));
        }
    }

}
