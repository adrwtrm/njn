package com.epson.iprojection.ui.common.singleton.mirroring;

import android.content.Context;
import android.content.Intent;
import kotlin.Metadata;

/* compiled from: Contract.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"Lcom/epson/iprojection/ui/common/singleton/mirroring/Contract;", "", "IMirroringServiceCommander", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface Contract {

    /* compiled from: Contract.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\u0003H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&¨\u0006\r"}, d2 = {"Lcom/epson/iprojection/ui/common/singleton/mirroring/Contract$IMirroringServiceCommander;", "", "finish", "", "onChangeAudioSettings", "shouldCaptureAudio", "", "onChangeMPPControlMode", "start", "context", "Landroid/content/Context;", "captureIntent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IMirroringServiceCommander {
        void finish();

        void onChangeAudioSettings(boolean z);

        void onChangeMPPControlMode();

        void start(Context context, Intent intent);
    }
}
