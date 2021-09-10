package de.teamlapen.vampirism.entity.hunter;

import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class TrainingDummyHunterEntity extends BasicHunterEntity {

    private final TargetingConditions PREDICATE = TargetingConditions.forNonCombat().ignoreLineOfSight();
    private int startTicks = 0;
    private float damageTaken = 0;

    public TrainingDummyHunterEntity(EntityType<? extends BasicHunterEntity> type, Level world) {
        super(type, world);
        this.disableImobConversion();
    }

    @Override
    public boolean hurt(@Nonnull DamageSource damageSource, float amount) {
        if (!this.level.isClientSide) {
            this.level.getNearbyPlayers(PREDICATE, this, this.getBoundingBox().inflate(40)).forEach(p -> p.displayClientMessage(new TextComponent("Damage " + amount + " from " + damageSource.msgId), false));
            if (this.startTicks != 0) this.damageTaken += amount;
        }
        return super.hurt(damageSource, amount);

    }

    @Override
    public void convertToMinion(Player lord) {
        super.convertToMinion(lord);
    }

    @Override
    protected void actuallyHurt(DamageSource damageSrc, float damageAmount) {
        if (damageSrc.isBypassInvul()) {
            super.actuallyHurt(damageSrc, damageAmount);
        }
    }

    @Nonnull
    @Override
    protected InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) { //processInteract
        if (!this.level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            if (startTicks == 0) {
                player.displayClientMessage(new TextComponent("Start recording"), false);
                this.startTicks = this.tickCount;
            } else {
                player.displayClientMessage(new TextComponent("Damage: " + damageTaken + " - DPS: " + (damageTaken / ((float) (this.tickCount - this.startTicks)) * 20f)), false);
                this.discard();
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void registerGoals() {

    }
}