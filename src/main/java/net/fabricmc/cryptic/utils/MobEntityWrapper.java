package net.fabricmc.cryptic.utils;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.ControllablePlayerEntity;

import java.util.Collection;
import java.util.HashMap;

public class MobEntityWrapper {
    public MobEntity entity;

    public MobEntityWrapper(ControllablePlayerEntity entity) {
        this.entity = entity;
    }

    public int getHealth() {
        return entity.method_2600();
    }

    public int getHurtTime() {
        return entity.field_3297;
    }

    public int getDeathTime() {
        return entity.field_3300;
    }

    public int getAttackTime() {
        return entity.field_3301;
    }

    public Collection<StatusEffectInstance> getActiveEffects() {
        return (Collection<StatusEffectInstance>) entity.method_2644();
    }

    public void setHealth(int health) {
        entity.method_2668(health);
    }

    public int getMaxHealth() {
        return entity.method_2599();
    }
}
