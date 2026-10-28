package net.morimori0317.bestylewither.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.WitherBossRenderer;
import net.minecraft.client.renderer.entity.state.WitherRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.morimori0317.bestylewither.BEStyleWither;
import net.morimori0317.bestylewither.client.BSWWitherRenderState;
import net.morimori0317.bestylewither.entity.BEWitherBoss;
import net.morimori0317.bestylewither.entity.WitherBossInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WitherBossRenderer.class)
public abstract class WitherBossRendererMixin {
    @Shadow @Final private static Identifier WITHER_INVULNERABLE_LOCATION;

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/boss/wither/WitherBoss;Lnet/minecraft/client/renderer/entity/state/WitherRenderState;F)V", at = @At("TAIL"))
    private void beStyleWither$extractAnimation(WitherBoss wither, WitherRenderState state, float partialTicks, CallbackInfo ci) {
        WitherBossInstance instance = ((BEWitherBoss) wither).beStyleWither$getInstance();
        BSWWitherRenderState extra = (BSWWitherRenderState) state;
        extra.beStyleWither$setChargeTicks(instance.getClientChargeTick(partialTicks));
        extra.beStyleWither$setDeathEnergyVisible(instance.isExplodeByDieEnergyLayerVisible());
        extra.beStyleWither$setDeathProgress(Math.max(0.0f, (wither.deathTime + partialTicks - 1.0f) / 28.0f));
    }

    @Inject(method = "scale(Lnet/minecraft/client/renderer/entity/state/WitherRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;)V", at = @At("TAIL"))
    private void beStyleWither$deathScale(WitherRenderState state, PoseStack poseStack, CallbackInfo ci) {
        if (!BEStyleWither.getConfig().isEnableExplodeByDie()) return;
        float deathProgress = ((BSWWitherRenderState) state).beStyleWither$getDeathProgress();
        if (deathProgress <= 0) return;
        float wd2 = deathProgress + 2.0f;
        float pulse = 1.0F + Mth.sin(wd2 * (wd2 / 20.0f) * 100.0f) * wd2 * 0.0075F;
        float power = Mth.clamp(deathProgress, 0.0F, 1.0F);
        power *= power;
        power *= power;
        float width = (1.0F + power * 0.4F) * pulse;
        float height = (1.0F + power * 0.1F) / pulse;
        poseStack.scale(width, height, width);
    }

    @Inject(method = "getTextureLocation(Lnet/minecraft/client/renderer/entity/state/WitherRenderState;)Lnet/minecraft/resources/Identifier;", at = @At("RETURN"), cancellable = true)
    private void beStyleWither$deathTexture(WitherRenderState state, CallbackInfoReturnable<Identifier> cir) {
        if (BEStyleWither.getConfig().isEnableExplodeByDie() && state.deathTime > 0.0f) {
            cir.setReturnValue(WITHER_INVULNERABLE_LOCATION);
        }
    }
}
