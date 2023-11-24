package net.fabricmc.cryptic.utils;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.ControllablePlayerEntity;

public class MobEntityWrapper {
    public MobEntity entity;

    public MobEntityWrapper(ControllablePlayerEntity entity) {
        this.entity = entity;
    }

    public int getHealth() {
        return entity.method_2600();
    }

    public int getMaxHealth() {
        return entity.method_2599();
    }
}
