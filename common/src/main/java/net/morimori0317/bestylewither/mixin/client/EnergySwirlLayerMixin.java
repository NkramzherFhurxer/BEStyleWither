package net.morimori0317.bestylewither.mixin.client;

import net.minecraft.client.renderer.entity.layers.WitherArmorLayer;
import net.minecraft.client.renderer.entity.state.WitherRenderState;
import net.minecraft.resources.Identifier;
import net.morimori0317.bestylewither.BEStyleWither;
import net.morimori0317.bestylewither.client.BSWWitherRenderState;
import net.morimori0317.bestylewither.entity.ExplodeByDieConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Wither-specific implementation; no longer mixes into the generic EnergySwirlLayer. */
@Mixin(WitherArmorLayer.class)
public abstract class EnergySwirlLayerMixin {
    @Unique private static final Identifier BESTYLEWITHER_BLUE = Identifier.withDefaultNamespace("textures/entity/creeper/creeper_armor.png");
    @Unique private boolean beStyleWither$deathEnergy;
    @Unique private float beStyleWither$deathProgress;

    @Inject(method = "isPowered(Lnet/minecraft/client/renderer/entity/state/WitherRenderState;)Z", at = @At("RETURN"), cancellable = true)
    private void beStyleWither$deathArmor(WitherRenderState state, CallbackInfoReturnable<Boolean> cir) {
        BSWWitherRenderState extra = (BSWWitherRenderState) state;
        beStyleWither$deathEnergy = BEStyleWither.getConfig().isEnableExplodeByDie()
                && state.deathTime > ExplodeByDieConstants.ENERGY_LAYER_START_TICK
                && extra.beStyleWither$isDeathEnergyVisible();
        beStyleWither$deathProgress = extra.beStyleWither$getDeathProgress();
        if (beStyleWither$deathEnergy) cir.setReturnValue(true);
    }

    @Inject(method = "getTextureLocation()Lnet/minecraft/resources/Identifier;", at = @At("RETURN"), cancellable = true)
    private void beStyleWither$deathArmorTexture(CallbackInfoReturnable<Identifier> cir) {
        if (beStyleWither$deathEnergy) cir.setReturnValue(BESTYLEWITHER_BLUE);
    }

    @Inject(method = "xOffset(F)F", at = @At("RETURN"), cancellable = true)
    private void beStyleWither$deathArmorSpeed(float age, CallbackInfoReturnable<Float> cir) {
        if (beStyleWither$deathEnergy) cir.setReturnValue(cir.getReturnValueF() * (1.0f + beStyleWither$deathProgress));
    }
}
