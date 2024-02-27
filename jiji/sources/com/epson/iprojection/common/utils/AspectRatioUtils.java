package com.epson.iprojection.common.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: AspectRatioUtils.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 \u00042\u00020\u0001:\u0002\u0003\u0004B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/common/utils/AspectRatioUtils;", "", "()V", "AspectRatio", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class AspectRatioUtils {
    public static final Companion Companion = new Companion(null);

    /* compiled from: AspectRatioUtils.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\f\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r"}, d2 = {"Lcom/epson/iprojection/common/utils/AspectRatioUtils$AspectRatio;", "", "(Ljava/lang/String;I)V", "ASPECTRATIO_02_03", "ASPECTRATIO_04_05", "ASPECTRATIO_08_09", "ASPECTRATIO_21_18", "ASPECTRATIO_04_03", "ASPECTRATIO_16_10", "ASPECTRATIO_16_09", "ASPECTRATIO_21_09", "ASPECTRATIO_16_06", "ASPECTRATIO_UNKNOWN", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public enum AspectRatio {
        ASPECTRATIO_02_03,
        ASPECTRATIO_04_05,
        ASPECTRATIO_08_09,
        ASPECTRATIO_21_18,
        ASPECTRATIO_04_03,
        ASPECTRATIO_16_10,
        ASPECTRATIO_16_09,
        ASPECTRATIO_21_09,
        ASPECTRATIO_16_06,
        ASPECTRATIO_UNKNOWN
    }

    /* compiled from: AspectRatioUtils.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006¨\u0006\b"}, d2 = {"Lcom/epson/iprojection/common/utils/AspectRatioUtils$Companion;", "", "()V", "calc", "Lcom/epson/iprojection/common/utils/AspectRatioUtils$AspectRatio;", "width", "", "height", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final AspectRatio calc(int i, int i2) {
            if (i2 == 0) {
                return AspectRatio.ASPECTRATIO_UNKNOWN;
            }
            double d = i / i2;
            if (0.6d > d || d >= 0.7d) {
                if (0.75d > d || d >= 0.84d) {
                    if (0.84d > d || d >= 0.94d) {
                        if (1.15d > d || d >= 1.18d) {
                            if (1.2d > d || d >= 1.4d) {
                                if (1.5d > d || d >= 1.7d) {
                                    if (1.7d > d || d >= 1.9d) {
                                        if (2.3d > d || d >= 2.4d) {
                                            if (2.5d <= d && d < 2.7d) {
                                                return AspectRatio.ASPECTRATIO_16_06;
                                            }
                                            return AspectRatio.ASPECTRATIO_UNKNOWN;
                                        }
                                        return AspectRatio.ASPECTRATIO_21_09;
                                    }
                                    return AspectRatio.ASPECTRATIO_16_09;
                                }
                                return AspectRatio.ASPECTRATIO_16_10;
                            }
                            return AspectRatio.ASPECTRATIO_04_03;
                        }
                        return AspectRatio.ASPECTRATIO_21_18;
                    }
                    return AspectRatio.ASPECTRATIO_08_09;
                }
                return AspectRatio.ASPECTRATIO_04_05;
            }
            return AspectRatio.ASPECTRATIO_02_03;
        }
    }
}
