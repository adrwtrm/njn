package com.epson.iprojection.ui.activities.pjselect.linkage.state;

import com.epson.iprojection.ui.activities.pjselect.linkage.state.Define;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Utils.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0015\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Utils;", "", "()V", "convertLinkageConnectFailedReason", "Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Define$ConnectFailedReason;", "reason", "Lcom/epson/iprojection/ui/engine_wrapper/interfaces/IOnConnectListener$FailReason;", "equals", "", "array1", "", "array2", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class Utils {
    public static final Utils INSTANCE = new Utils();

    /* compiled from: Utils.kt */
    @Metadata(k = 3, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[IOnConnectListener.FailReason.values().length];
            try {
                iArr[IOnConnectListener.FailReason.Default.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[IOnConnectListener.FailReason.NpVersionError.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[IOnConnectListener.FailReason.IlligalKeyword.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[IOnConnectListener.FailReason.DiffCombiPj.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[IOnConnectListener.FailReason.MppMaxUser.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private Utils() {
    }

    public final boolean equals(byte[] array1, int[] array2) {
        Intrinsics.checkNotNullParameter(array1, "array1");
        Intrinsics.checkNotNullParameter(array2, "array2");
        int[] iArr = new int[array1.length];
        int length = array1.length;
        for (int i = 0; i < length; i++) {
            iArr[i] = array1[i] & 255;
        }
        return Arrays.equals(iArr, array2);
    }

    public final Define.ConnectFailedReason convertLinkageConnectFailedReason(IOnConnectListener.FailReason reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        int i = WhenMappings.$EnumSwitchMapping$0[reason.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        if (i == 5) {
                            return Define.ConnectFailedReason.MppMaxUser;
                        }
                        throw new RuntimeException("対応していない失敗理由");
                    }
                    return Define.ConnectFailedReason.DiffCombiPj;
                }
                return Define.ConnectFailedReason.IllegalKeyword;
            }
            return Define.ConnectFailedReason.VersionError;
        }
        return Define.ConnectFailedReason.Default;
    }
}
