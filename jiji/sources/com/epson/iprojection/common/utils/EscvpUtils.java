package com.epson.iprojection.common.utils;

import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: EscvpUtils.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/epson/iprojection/common/utils/EscvpUtils;", "", "()V", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class EscvpUtils {
    public static final Companion Companion = new Companion(null);

    /* compiled from: EscvpUtils.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/common/utils/EscvpUtils$Companion;", "", "()V", "sendSecureEscvpOfNetworkStandbyOnAndScomport", "", "pjInfo", "Lcom/epson/iprojection/engine/common/D_PjInfo;", "password", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int sendSecureEscvpOfNetworkStandbyOnAndScomport(D_PjInfo pjInfo, String password) {
            Intrinsics.checkNotNullParameter(pjInfo, "pjInfo");
            Intrinsics.checkNotNullParameter(password, "password");
            int sendDigestEscvp = Pj.getIns().sendDigestEscvp(Pj.ESCVP_COMMAND_SPOWERON, pjInfo.IPAddr, password);
            if (sendDigestEscvp == 0) {
                if (pjInfo.isNetworkPathWireless) {
                    return Pj.getIns().sendDigestEscvp(Pj.ESCVP_COMMAND_SCOMPORT_WIRELESS, pjInfo.IPAddr, password);
                }
                return Pj.getIns().sendDigestEscvp(Pj.ESCVP_COMMAND_SCOMPORT_WIRED, pjInfo.IPAddr, password);
            }
            return sendDigestEscvp;
        }
    }
}
