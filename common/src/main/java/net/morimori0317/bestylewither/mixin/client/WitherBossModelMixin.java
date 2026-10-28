package net.morimori0317.bestylewither.mixin.client;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.monster.wither.WitherBossModel;
import net.minecraft.client.renderer.entity.state.WitherRenderState;
import net.minecraft.util.Mth;
import net.morimori0317.bestylewither.client.BSWWitherRenderState;
import net.morimori0317.bestylewither.entity.goal.WitherChargeAttackGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherBossModel.class)
public abstract class WitherBossModelMixin {
    @Shadow @Final private ModelPart ribcage;
    @Shadow @Final private ModelPart tail;

    // 26.3 models are driven by WitherRenderState rather than WitherBoss entities.
    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/WitherRenderState;)V", at = @At("TAIL"))
    private void beStyleWither$chargePose(WitherRenderState state, CallbackInfo ci) {
        float chargeTicks = ((BSWWitherRenderState) state).beStyleWither$getChargeTicks();
        float cp = chargeTicks / (float) WitherChargeAttackGoal.CHARGE_TIME;
        cp = Math.abs(-0.5f + cp);
        cp = 1.0f - cp / 0.5f;
        cp = Math.min(cp, 0.3f) / 0.3f;
        float degrees = 47.0f * cp;
        if (degrees > 0.0f) {
            float radiansPerDegree = -(float) Math.PI / 180.0f;
            this.ribcage.xRot += -degrees * radiansPerDegree;
            this.tail.setPos(-2.0F, 6.9F + Mth.cos(this.ribcage.xRot) * 10.0F,
                    -0.5F + Mth.sin(this.ribcage.xRot) * 10.0F);
            this.tail.xRot += -degrees * radiansPerDegree;
        }
    }
}
