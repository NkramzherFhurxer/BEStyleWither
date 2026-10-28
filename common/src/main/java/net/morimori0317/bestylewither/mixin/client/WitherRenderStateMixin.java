package net.morimori0317.bestylewither.mixin.client;

import net.minecraft.client.renderer.entity.state.WitherRenderState;
import net.morimori0317.bestylewither.client.BSWWitherRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WitherRenderState.class)
public class WitherRenderStateMixin implements BSWWitherRenderState {
    @Unique private float beStyleWither$chargeTicks;
    @Unique private boolean beStyleWither$deathEnergyVisible;
    @Unique private float beStyleWither$deathProgress;

    @Override
    public void beStyleWither$setChargeTicks(float ticks) {
        this.beStyleWither$chargeTicks = ticks;
    }

    @Override
    public float beStyleWither$getChargeTicks() {
        return this.beStyleWither$chargeTicks;
    }

    @Override
    public void beStyleWither$setDeathEnergyVisible(boolean visible) {
        this.beStyleWither$deathEnergyVisible = visible;
    }

    @Override
    public boolean beStyleWither$isDeathEnergyVisible() {
        return this.beStyleWither$deathEnergyVisible;
    }

    @Override
    public void beStyleWither$setDeathProgress(float progress) {
        this.beStyleWither$deathProgress = progress;
    }

    @Override
    public float beStyleWither$getDeathProgress() {
        return this.beStyleWither$deathProgress;
    }
}
