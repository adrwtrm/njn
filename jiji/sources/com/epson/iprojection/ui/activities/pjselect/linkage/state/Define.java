package com.epson.iprojection.ui.activities.pjselect.linkage.state;

import kotlin.Metadata;

/* compiled from: Define.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0006"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Define;", "", "()V", "ConnectFailedReason", "ConnectingProgress", "FinishType", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class Define {

    /* compiled from: Define.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\r\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r¨\u0006\u000e"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Define$ConnectFailedReason;", "", "(Ljava/lang/String;I)V", "Default", "VersionError", "IllegalKeyword", "DiffCombiPj", "MppMaxUser", "Other", "SearchFailed", "WiFiChangeFailed", "ActivityStopped", "NfcEventHappened", "None", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public enum ConnectFailedReason {
        Default,
        VersionError,
        IllegalKeyword,
        DiffCombiPj,
        MppMaxUser,
        Other,
        SearchFailed,
        WiFiChangeFailed,
        ActivityStopped,
        NfcEventHappened,
        None
    }

    /* compiled from: Define.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Define$ConnectingProgress;", "", "(Ljava/lang/String;I)V", "ChangingWiFi", "SearchingPj", "ConnectingToPj", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public enum ConnectingProgress {
        ChangingWiFi,
        SearchingPj,
        ConnectingToPj
    }

    /* compiled from: Define.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/ui/activities/pjselect/linkage/state/Define$FinishType;", "", "(Ljava/lang/String;I)V", "Succeeded", "Failed", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public enum FinishType {
        Succeeded,
        Failed
    }
}
