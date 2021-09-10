package de.teamlapen.vampirism.effects;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;

import javax.annotation.Nonnull;

public class FreezeEffect extends VampirismEffect {
    public FreezeEffect(String name) {
        super(name, MobEffectCategory.HARMFUL, 0xFFFFFF);
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.setDeltaMovement(0, Math.min(0, entityLivingBaseIn.getDeltaMovement().y()), 0);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Nonnull
    @Override
    protected String getOrCreateDescriptionId() {
        return "action.vampirism.freeze";
    }
}
