package com.epson.iprojection.service.mirroring;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.service.mirroring.IMirroringService;
import com.epson.iprojection.service.mirroring.MirroringContract;
import com.epson.iprojection.service.webrtc.utils.WebRTCUtils;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MirroringService.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001\u0016B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\fH\u0016J \u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0011H\u0016J\b\u0010\u0014\u001a\u00020\fH\u0002J\b\u0010\u0015\u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringService;", "Landroid/app/Service;", "()V", "_presenter", "Lcom/epson/iprojection/service/mirroring/MirroringContract$IPresenter;", "bindserviceIf", "Lcom/epson/iprojection/service/mirroring/IMirroringService$Stub;", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onConfigurationChanged", "", "newConfig", "Landroid/content/res/Configuration;", "onCreate", "onStartCommand", "", "flags", "startId", "showNotification", "showPreparingNotification", "ImplIView", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MirroringService extends Service {
    private MirroringContract.IPresenter _presenter;
    private final IMirroringService.Stub bindserviceIf = new IMirroringService.Stub() { // from class: com.epson.iprojection.service.mirroring.MirroringService$bindserviceIf$1
        {
            MirroringService.this = this;
        }

        @Override // com.epson.iprojection.service.mirroring.IMirroringService
        public void start(Bundle bundle) {
            MirroringContract.IPresenter iPresenter;
            MirroringContract.IPresenter iPresenter2;
            MirroringContract.IPresenter iPresenter3;
            Lg.d("[s] start");
            MirroringNotification.INSTANCE.initialize();
            MirroringService.this.showNotification();
            MirroringContract.IPresenter iPresenter4 = null;
            Intent intent = bundle != null ? (Intent) bundle.getParcelable(CommonDefine.INTENT_EXTRA_CAPTURE_INTENT) : null;
            if (intent != null) {
                iPresenter = MirroringService.this._presenter;
                if (iPresenter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("_presenter");
                    iPresenter = null;
                }
                iPresenter.start(intent);
                Analytics.getIns().endContentsForce();
                Analytics ins = Analytics.getIns();
                iPresenter2 = MirroringService.this._presenter;
                if (iPresenter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("_presenter");
                    iPresenter2 = null;
                }
                String protocolTypeForGA = iPresenter2.getProtocolTypeForGA();
                iPresenter3 = MirroringService.this._presenter;
                if (iPresenter3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("_presenter");
                } else {
                    iPresenter4 = iPresenter3;
                }
                ins.setProtocolAndCodec(protocolTypeForGA, iPresenter4.getCodecTypeForGA());
                Analytics.getIns().startContents(eCustomEvent.MIRRORING_START);
                return;
            }
            Lg.e("[s] error!! captureIntentがnull!!");
        }

        @Override // com.epson.iprojection.service.mirroring.IMirroringService
        public void finish() {
            MirroringContract.IPresenter iPresenter;
            Lg.d("[s] finish");
            iPresenter = MirroringService.this._presenter;
            if (iPresenter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_presenter");
                iPresenter = null;
            }
            iPresenter.finish();
            Analytics.getIns().endContents(eCustomEvent.MIRRORING_END);
        }

        @Override // com.epson.iprojection.service.mirroring.IMirroringService
        public void onChangeMPPControlMode() {
            MirroringContract.IPresenter iPresenter;
            Lg.d("[s] onChangeMPPControlMode");
            iPresenter = MirroringService.this._presenter;
            if (iPresenter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_presenter");
                iPresenter = null;
            }
            iPresenter.onChangeMPPControlMode();
        }

        @Override // com.epson.iprojection.service.mirroring.IMirroringService
        public void onChangeAudioSettings(boolean z) {
            MirroringContract.IPresenter iPresenter;
            Lg.d("[s] onChangeAudioSettings");
            iPresenter = MirroringService.this._presenter;
            if (iPresenter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_presenter");
                iPresenter = null;
            }
            iPresenter.onChangeAudioSettings(z);
        }
    };

    @Override // android.app.Service
    public void onCreate() {
        MirroringPresenterJPEG mirroringPresenterJPEG;
        super.onCreate();
        Lg.d("[s] onCreate");
        WebRTCUtils webRTCUtils = WebRTCUtils.INSTANCE;
        MirroringService mirroringService = this;
        ArrayList<ConnectPjInfo> nowConnectingPJList = Pj.getIns().getNowConnectingPJList();
        Intrinsics.checkNotNullExpressionValue(nowConnectingPJList, "getIns().nowConnectingPJList");
        if (webRTCUtils.shouldUseWebRTC(mirroringService, nowConnectingPJList)) {
            Pj ins = Pj.getIns();
            Intrinsics.checkNotNullExpressionValue(ins, "getIns()");
            mirroringPresenterJPEG = new MirroringPresenterWebRTC(mirroringService, new ImplIView(), ins);
        } else {
            Pj ins2 = Pj.getIns();
            Intrinsics.checkNotNullExpressionValue(ins2, "getIns()");
            mirroringPresenterJPEG = new MirroringPresenterJPEG(mirroringService, new ImplIView(), ins2);
        }
        this._presenter = mirroringPresenterJPEG;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        super.onStartCommand(intent, i, i2);
        Lg.d("[s] onStartCommand");
        MirroringContract.IPresenter iPresenter = this._presenter;
        if (iPresenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_presenter");
            iPresenter = null;
        }
        iPresenter.onStartCommand(intent);
        return 2;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        Lg.d("[s] onBind intent:" + intent);
        showPreparingNotification();
        return this.bindserviceIf;
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        Lg.d("[s] onConfigurationChanged");
        MirroringContract.IPresenter iPresenter = this._presenter;
        if (iPresenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_presenter");
            iPresenter = null;
        }
        iPresenter.onRotated(newConfig);
    }

    public final void showNotification() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.epson.iprojection.service.mirroring.MirroringService$$ExternalSyntheticLambda0
            {
                MirroringService.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                MirroringService.showNotification$lambda$0(MirroringService.this);
            }
        }, 200L);
    }

    public static final void showNotification$lambda$0(MirroringService this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startForeground(1001, MirroringNotification.INSTANCE.create(this$0).build());
    }

    private final void showPreparingNotification() {
        startForeground(1001, MirroringPreparingNotification.INSTANCE.create(this).build());
    }

    /* compiled from: MirroringService.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringService$ImplIView;", "Lcom/epson/iprojection/service/mirroring/MirroringContract$IView;", "(Lcom/epson/iprojection/service/mirroring/MirroringService;)V", "deleteNotification", "", "refreshNotification", "showToast", "stringID", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    private final class ImplIView implements MirroringContract.IView {
        public ImplIView() {
            MirroringService.this = r1;
        }

        @Override // com.epson.iprojection.service.mirroring.MirroringContract.IView
        public void refreshNotification() {
            MirroringService.this.showNotification();
        }

        @Override // com.epson.iprojection.service.mirroring.MirroringContract.IView
        public void deleteNotification() {
            MirroringNotification.INSTANCE.delete(MirroringService.this);
        }

        @Override // com.epson.iprojection.service.mirroring.MirroringContract.IView
        public void showToast(int i) {
            MirroringService mirroringService = MirroringService.this;
            Toast.makeText(mirroringService, mirroringService.getString(i), 1).show();
        }
    }
}
