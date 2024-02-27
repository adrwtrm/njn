package com.epson.iprojection.engine.test;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NICManagerAndroid.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\tB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0019\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\b¨\u0006\n"}, d2 = {"Lcom/epson/iprojection/engine/test/NICManagerAndroid;", "", "()V", "getSelectedNIC", "", "NICs", "", "Lcom/epson/iprojection/engine/test/NICManagerAndroid$NICInfo;", "([Lcom/epson/iprojection/engine/test/NICManagerAndroid$NICInfo;)Ljava/lang/String;", "NICInfo", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class NICManagerAndroid {
    public static final NICManagerAndroid INSTANCE = new NICManagerAndroid();

    private NICManagerAndroid() {
    }

    /* compiled from: NICManagerAndroid.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0012"}, d2 = {"Lcom/epson/iprojection/engine/test/NICManagerAndroid$NICInfo;", "", AppMeasurementSdk.ConditionalUserProperty.NAME, "", "isThisForChromeOS", "", "(Ljava/lang/String;Z)V", "()Z", "getName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class NICInfo {
        private final boolean isThisForChromeOS;
        private final String name;

        public static /* synthetic */ NICInfo copy$default(NICInfo nICInfo, String str, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                str = nICInfo.name;
            }
            if ((i & 2) != 0) {
                z = nICInfo.isThisForChromeOS;
            }
            return nICInfo.copy(str, z);
        }

        public final String component1() {
            return this.name;
        }

        public final boolean component2() {
            return this.isThisForChromeOS;
        }

        public final NICInfo copy(String name, boolean z) {
            Intrinsics.checkNotNullParameter(name, "name");
            return new NICInfo(name, z);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof NICInfo) {
                NICInfo nICInfo = (NICInfo) obj;
                return Intrinsics.areEqual(this.name, nICInfo.name) && this.isThisForChromeOS == nICInfo.isThisForChromeOS;
            }
            return false;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int hashCode() {
            int hashCode = this.name.hashCode() * 31;
            boolean z = this.isThisForChromeOS;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            return hashCode + i;
        }

        public String toString() {
            return "NICInfo(name=" + this.name + ", isThisForChromeOS=" + this.isThisForChromeOS + ')';
        }

        public NICInfo(String name, boolean z) {
            Intrinsics.checkNotNullParameter(name, "name");
            this.name = name;
            this.isThisForChromeOS = z;
        }

        public final String getName() {
            return this.name;
        }

        public final boolean isThisForChromeOS() {
            return this.isThisForChromeOS;
        }
    }

    public final String getSelectedNIC(NICInfo[] NICs) {
        Intrinsics.checkNotNullParameter(NICs, "NICs");
        char c = 65535;
        String str = "";
        for (NICInfo nICInfo : NICs) {
            if (nICInfo.isThisForChromeOS() && c < 5) {
                str = nICInfo.getName();
                c = 5;
            }
            if (Intrinsics.areEqual("wlan1", nICInfo.getName()) && c < 4) {
                str = nICInfo.getName();
                c = 4;
            }
            if (Intrinsics.areEqual("wlan0", nICInfo.getName()) && c < 3) {
                str = nICInfo.getName();
                c = 3;
            }
            if (Intrinsics.areEqual("lan0", nICInfo.getName()) && c < 2) {
                str = nICInfo.getName();
                c = 2;
            }
            if (Intrinsics.areEqual("eth1", nICInfo.getName()) && c < 1) {
                str = nICInfo.getName();
                c = 1;
            }
            if (Intrinsics.areEqual("eth0", nICInfo.getName()) && c < 0) {
                str = nICInfo.getName();
                c = 0;
            }
        }
        return str;
    }
}
