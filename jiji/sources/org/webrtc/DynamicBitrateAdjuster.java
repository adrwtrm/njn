package org.webrtc;

/* loaded from: classes.dex */
class DynamicBitrateAdjuster extends BaseBitrateAdjuster {
    private static final double BITRATE_ADJUSTMENT_MAX_SCALE = 4.0d;
    private static final double BITRATE_ADJUSTMENT_SEC = 3.0d;
    private static final int BITRATE_ADJUSTMENT_STEPS = 20;
    private static final double BITS_PER_BYTE = 8.0d;
    private int bitrateAdjustmentScaleExp;
    private double deviationBytes;
    private double timeSinceLastAdjustmentMs;

    @Override // org.webrtc.BaseBitrateAdjuster, org.webrtc.BitrateAdjuster
    public void setTargets(int i, double d) {
        if (this.targetBitrateBps > 0 && i < this.targetBitrateBps) {
            this.deviationBytes = (this.deviationBytes * i) / this.targetBitrateBps;
        }
        super.setTargets(i, d);
    }

    @Override // org.webrtc.BaseBitrateAdjuster, org.webrtc.BitrateAdjuster
    public void reportEncodedFrame(int i) {
        if (this.targetFramerateFps == 0.0d) {
            return;
        }
        this.deviationBytes += i - ((this.targetBitrateBps / BITS_PER_BYTE) / this.targetFramerateFps);
        this.timeSinceLastAdjustmentMs += 1000.0d / this.targetFramerateFps;
        double d = this.targetBitrateBps / BITS_PER_BYTE;
        double d2 = BITRATE_ADJUSTMENT_SEC * d;
        double min = Math.min(this.deviationBytes, d2);
        this.deviationBytes = min;
        double max = Math.max(min, -d2);
        this.deviationBytes = max;
        if (this.timeSinceLastAdjustmentMs <= 3000.0d) {
            return;
        }
        if (max > d) {
            int i2 = this.bitrateAdjustmentScaleExp - ((int) ((max / d) + 0.5d));
            this.bitrateAdjustmentScaleExp = i2;
            this.bitrateAdjustmentScaleExp = Math.max(i2, -20);
            this.deviationBytes = d;
        } else {
            double d3 = -d;
            if (max < d3) {
                int i3 = this.bitrateAdjustmentScaleExp + ((int) (((-max) / d) + 0.5d));
                this.bitrateAdjustmentScaleExp = i3;
                this.bitrateAdjustmentScaleExp = Math.min(i3, 20);
                this.deviationBytes = d3;
            }
        }
        this.timeSinceLastAdjustmentMs = 0.0d;
    }

    private double getBitrateAdjustmentScale() {
        return Math.pow(BITRATE_ADJUSTMENT_MAX_SCALE, this.bitrateAdjustmentScaleExp / 20.0d);
    }

    @Override // org.webrtc.BaseBitrateAdjuster, org.webrtc.BitrateAdjuster
    public int getAdjustedBitrateBps() {
        return (int) (this.targetBitrateBps * getBitrateAdjustmentScale());
    }
}
