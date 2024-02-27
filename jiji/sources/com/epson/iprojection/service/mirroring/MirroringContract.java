package com.epson.iprojection.service.mirroring;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import kotlin.Metadata;

/* compiled from: MirroringContract.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004¨\u0006\u0005"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringContract;", "", "IMirroringJpeg", "IPresenter", "IView", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface MirroringContract {

    /* compiled from: MirroringContract.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0005H&J\u0010\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u0005H&J\b\u0010\u000b\u001a\u00020\u0005H&J\u0018\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u0005H&¨\u0006\u0012"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringContract$IMirroringJpeg;", "", "isStopped", "", "onClickAVMute", "", "onClickAVPlay", "onRotated", "newConfiguration", "Landroid/content/res/Configuration;", "pauseAudio", "resumeAudio", "start", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "stop", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IMirroringJpeg {
        boolean isStopped();

        void onClickAVMute();

        void onClickAVPlay();

        void onRotated(Configuration configuration);

        void pauseAudio();

        void resumeAudio();

        void start(Context context, Intent intent);

        void stop();
    }

    /* compiled from: MirroringContract.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0005H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u0003H&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH&J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H&J\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0010H&¨\u0006\u0013"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringContract$IPresenter;", "", "finish", "", "getCodecTypeForGA", "", "getProtocolTypeForGA", "onChangeAudioSettings", "shouldCaptureAudio", "", "onChangeMPPControlMode", "onRotated", "newConfiguration", "Landroid/content/res/Configuration;", "onStartCommand", "intent", "Landroid/content/Intent;", "start", "captureIntent", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IPresenter {
        void finish();

        String getCodecTypeForGA();

        String getProtocolTypeForGA();

        void onChangeAudioSettings(boolean z);

        void onChangeMPPControlMode();

        void onRotated(Configuration configuration);

        void onStartCommand(Intent intent);

        void start(Intent intent);
    }

    /* compiled from: MirroringContract.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\u0010\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\b"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringContract$IView;", "", "deleteNotification", "", "refreshNotification", "showToast", "stringID", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IView {
        void deleteNotification();

        void refreshNotification();

        void showToast(int i);
    }
}
