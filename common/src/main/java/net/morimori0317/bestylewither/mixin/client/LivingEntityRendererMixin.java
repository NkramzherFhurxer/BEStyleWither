package net.morimori0317.bestylewither.mixin.client;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.WitherRenderState;
import net.minecraft.util.Mth;
import net.morimori0317.bestylewither.BEStyleWither;
import net.morimori0317.bestylewither.client.BSWWitherRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    // The 26.3 renderer takes LivingEntityRenderState, not an entity.
    @Inject(method = "getWhiteOverlayProgress(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;)F", at = @At("RETURN"), cancellable = true)
    private void beStyleWither$overlay( LivingEntityRenderState state, CallbackInfoReturnable<Float> cir) {
        if (!(state instanceof WitherRenderState wither)) return;
        if (BEStyleWither.getConfig().isEnableSpinAndWhiteSummon() && wither.invulnerableTicks > 80.0f) {
            cir.setReturnValue(Mth.clamp(1.0f - (100.0f - wither.invulnerableTicks) / 20.0f, 0.0f, 1.0f));
        }
        if (BEStyleWither.getConfig().isEnableExplodeByDie()) {
            float deathProgress = ((BSWWitherRenderState) wither).beStyleWither$getDeathProgress();
            if (deathProgress > 0.0f) cir.setReturnValue(Mth.clamp(deathProgress, 0.0f, 1.0f));
        }
    }
}
