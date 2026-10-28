package net.morimori0317.bestylewither.client;

/** Additional animation values copied from the Wither into its client render state. */
public interface BSWWitherRenderState {
    void beStyleWither$setChargeTicks(float ticks);
    float beStyleWither$getChargeTicks();

    void beStyleWither$setDeathEnergyVisible(boolean visible);
    boolean beStyleWither$isDeathEnergyVisible();

    void beStyleWither$setDeathProgress(float progress);
    float beStyleWither$getDeathProgress();
}
