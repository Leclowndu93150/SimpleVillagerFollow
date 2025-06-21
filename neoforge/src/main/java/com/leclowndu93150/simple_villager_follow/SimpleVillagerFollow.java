package com.leclowndu93150.simple_villager_follow;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class SimpleVillagerFollow {

    public SimpleVillagerFollow(IEventBus eventBus) {
        CommonClass.init();
    }
}
