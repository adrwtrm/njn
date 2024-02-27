package com.epson.iprojection.engine.common;

import kotlin.Metadata;

/* compiled from: eBandWidth.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001\u001a\u000e\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001¨\u0006\u0004"}, d2 = {"convertBandWidthDataForEngine", "", "num", "convertBandWidthDataForUi", "app_release"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class EBandWidthKt {
    public static final int convertBandWidthDataForUi(int i) {
        switch (i) {
            case 1:
                return eBandWidthForUI.BW_4Mbps.ordinal();
            case 2:
                return eBandWidthForUI.BW_2Mbps.ordinal();
            case 3:
                return eBandWidthForUI.BW_1Mbps.ordinal();
            case 4:
                return eBandWidthForUI.BW_512Kbps.ordinal();
            case 5:
                return eBandWidthForUI.BW_256Kbps.ordinal();
            case 6:
                return eBandWidthForUI.BW_7Mbps.ordinal();
            case 7:
                return eBandWidthForUI.BW_15Mbps.ordinal();
            case 8:
                return eBandWidthForUI.BW_25Mbps.ordinal();
            default:
                return eBandWidthForUI.BW_NoLimit.ordinal();
        }
    }

    public static final int convertBandWidthDataForEngine(int i) {
        switch (i) {
            case 1:
                return eBandWidth.e25M.ordinal();
            case 2:
                return eBandWidth.e15M.ordinal();
            case 3:
                return eBandWidth.e7M.ordinal();
            case 4:
                return eBandWidth.e4M.ordinal();
            case 5:
                return eBandWidth.e2M.ordinal();
            case 6:
                return eBandWidth.e1M.ordinal();
            case 7:
                return eBandWidth.e512K.ordinal();
            case 8:
                return eBandWidth.e256K.ordinal();
            default:
                return eBandWidth.eNoControl.ordinal();
        }
    }
}
