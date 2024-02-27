package com.epson.iprojection.ui.common.analytics.customdimension.enums;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.epson.iprojection.engine.common.eBandWidth;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: eUseBandWidthDimension.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0011\b\u0086\u0001\u0018\u0000 \u00152\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0015B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014¨\u0006\u0016"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/enums/eUseBandWidthDimension;", "", TypedValues.Custom.S_STRING, "", "int", "", "(Ljava/lang/String;ILjava/lang/String;I)V", "getInt", "()I", "getString", "()Ljava/lang/String;", "noControl", "_4Mbps", "_2Mbps", "_1Mbbs", "_512Kbps", "_256Kbps", "_7Mbbs", "_15Mbps", "_25Mbps", "error", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public enum eUseBandWidthDimension {
    noControl("制御しない", eBandWidth.eNoControl.ordinal()),
    _4Mbps("4Mbps", eBandWidth.e4M.ordinal()),
    _2Mbps("2Mbps", eBandWidth.e2M.ordinal()),
    _1Mbbs("1Mbps", eBandWidth.e1M.ordinal()),
    _512Kbps("512Kbps", eBandWidth.e512K.ordinal()),
    _256Kbps("256Kbps", eBandWidth.e256K.ordinal()),
    _7Mbbs("7Mbps", eBandWidth.e7M.ordinal()),
    _15Mbps("15Mbps", eBandWidth.e15M.ordinal()),
    _25Mbps("25Mbps", eBandWidth.e25M.ordinal()),
    error("エラー", 99);
    
    public static final Companion Companion = new Companion(null);

    /* renamed from: int  reason: not valid java name */
    private final int f0int;
    private final String string;

    @JvmStatic
    public static final eUseBandWidthDimension valueOf(int i) {
        return Companion.valueOf(i);
    }

    eUseBandWidthDimension(String str, int i) {
        this.string = str;
        this.f0int = i;
    }

    public final int getInt() {
        return this.f0int;
    }

    public final String getString() {
        return this.string;
    }

    /* compiled from: eUseBandWidthDimension.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/common/analytics/customdimension/enums/eUseBandWidthDimension$Companion;", "", "()V", "valueOf", "Lcom/epson/iprojection/ui/common/analytics/customdimension/enums/eUseBandWidthDimension;", "bandWidthValue", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final eUseBandWidthDimension valueOf(int i) {
            eUseBandWidthDimension[] values;
            for (eUseBandWidthDimension eusebandwidthdimension : eUseBandWidthDimension.values()) {
                if (eusebandwidthdimension.getInt() == i) {
                    return eusebandwidthdimension;
                }
            }
            return null;
        }
    }
}
