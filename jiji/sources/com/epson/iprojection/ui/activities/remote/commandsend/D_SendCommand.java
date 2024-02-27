package com.epson.iprojection.ui.activities.remote.commandsend;

import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: D_SendCommand.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lcom/epson/iprojection/ui/activities/remote/commandsend/D_SendCommand;", "", "password", "", "info", "Lcom/epson/iprojection/ui/activities/pjselect/control/D_HistoryInfo;", "(Ljava/lang/String;Lcom/epson/iprojection/ui/activities/pjselect/control/D_HistoryInfo;)V", "getInfo", "()Lcom/epson/iprojection/ui/activities/pjselect/control/D_HistoryInfo;", "getPassword", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class D_SendCommand {
    private final D_HistoryInfo info;
    private final String password;

    public static /* synthetic */ D_SendCommand copy$default(D_SendCommand d_SendCommand, String str, D_HistoryInfo d_HistoryInfo, int i, Object obj) {
        if ((i & 1) != 0) {
            str = d_SendCommand.password;
        }
        if ((i & 2) != 0) {
            d_HistoryInfo = d_SendCommand.info;
        }
        return d_SendCommand.copy(str, d_HistoryInfo);
    }

    public final String component1() {
        return this.password;
    }

    public final D_HistoryInfo component2() {
        return this.info;
    }

    public final D_SendCommand copy(String password, D_HistoryInfo info) {
        Intrinsics.checkNotNullParameter(password, "password");
        Intrinsics.checkNotNullParameter(info, "info");
        return new D_SendCommand(password, info);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof D_SendCommand) {
            D_SendCommand d_SendCommand = (D_SendCommand) obj;
            return Intrinsics.areEqual(this.password, d_SendCommand.password) && Intrinsics.areEqual(this.info, d_SendCommand.info);
        }
        return false;
    }

    public int hashCode() {
        return (this.password.hashCode() * 31) + this.info.hashCode();
    }

    public String toString() {
        return "D_SendCommand(password=" + this.password + ", info=" + this.info + ')';
    }

    public D_SendCommand(String password, D_HistoryInfo info) {
        Intrinsics.checkNotNullParameter(password, "password");
        Intrinsics.checkNotNullParameter(info, "info");
        this.password = password;
        this.info = info;
    }

    public final String getPassword() {
        return this.password;
    }

    public final D_HistoryInfo getInfo() {
        return this.info;
    }
}
