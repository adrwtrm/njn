package com.epson.iprojection.ui.common.singleton.mirroring;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.service.mirroring.IMirroringService;
import com.epson.iprojection.service.mirroring.MirroringService;
import com.epson.iprojection.ui.common.singleton.mirroring.Contract;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MirroringServiceCommander.kt */
@Metadata(d1 = {"\u00005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005*\u0001\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\rH\u0016J\u0018\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0004H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/epson/iprojection/ui/common/singleton/mirroring/MirroringServiceCommander;", "Lcom/epson/iprojection/ui/common/singleton/mirroring/Contract$IMirroringServiceCommander;", "()V", "_captureIntent", "Landroid/content/Intent;", "_connection", "com/epson/iprojection/ui/common/singleton/mirroring/MirroringServiceCommander$_connection$1", "Lcom/epson/iprojection/ui/common/singleton/mirroring/MirroringServiceCommander$_connection$1;", "_context", "Landroid/content/Context;", "_service", "Lcom/epson/iprojection/service/mirroring/IMirroringService;", "finish", "", "onChangeAudioSettings", "shouldCaptureAudio", "", "onChangeMPPControlMode", "start", "context", "captureIntent", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class MirroringServiceCommander implements Contract.IMirroringServiceCommander {
    private Intent _captureIntent;
    private final MirroringServiceCommander$_connection$1 _connection = new ServiceConnection() { // from class: com.epson.iprojection.ui.common.singleton.mirroring.MirroringServiceCommander$_connection$1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName className, IBinder service) {
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(service, "service");
            Lg.d("[A] onServiceConnected: " + className);
            MirroringServiceCommander.access$set_service$p(MirroringServiceCommander.this, IMirroringService.Stub.asInterface(service));
            Bundle bundle = new Bundle();
            bundle.putParcelable(CommonDefine.INTENT_EXTRA_CAPTURE_INTENT, MirroringServiceCommander.access$get_captureIntent$p(MirroringServiceCommander.this));
            IMirroringService access$get_service$p = MirroringServiceCommander.access$get_service$p(MirroringServiceCommander.this);
            if (access$get_service$p != null) {
                access$get_service$p.start(bundle);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName className) {
            Intrinsics.checkNotNullParameter(className, "className");
            Lg.d("[A] onServiceDisconnected: " + className);
        }
    };
    private Context _context;
    private IMirroringService _service;

    public static final /* synthetic */ Intent access$get_captureIntent$p(MirroringServiceCommander mirroringServiceCommander) {
        return mirroringServiceCommander._captureIntent;
    }

    public static final /* synthetic */ IMirroringService access$get_service$p(MirroringServiceCommander mirroringServiceCommander) {
        return mirroringServiceCommander._service;
    }

    public static final /* synthetic */ void access$set_service$p(MirroringServiceCommander mirroringServiceCommander, IMirroringService iMirroringService) {
        mirroringServiceCommander._service = iMirroringService;
    }

    @Override // com.epson.iprojection.ui.common.singleton.mirroring.Contract.IMirroringServiceCommander
    public void start(Context context, Intent captureIntent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(captureIntent, "captureIntent");
        Lg.d("[A] startForegroundService() and bindService()");
        if (this._service != null) {
            Lg.e("[A] start指示されましたが、_serviceがnullなので抜けます");
            return;
        }
        this._context = context;
        this._captureIntent = captureIntent;
        Intent intent = new Intent(context, MirroringService.class);
        context.startService(intent);
        context.bindService(intent, this._connection, 1);
    }

    @Override // com.epson.iprojection.ui.common.singleton.mirroring.Contract.IMirroringServiceCommander
    public void finish() {
        Lg.d("finish()");
        IMirroringService iMirroringService = this._service;
        if (iMirroringService == null) {
            Lg.e("[A] finish指示されましたが、_serviceがnullなので抜けます");
            return;
        }
        if (iMirroringService != null) {
            iMirroringService.finish();
        }
        this._service = null;
        Context context = this._context;
        if (context != null) {
            context.unbindService(this._connection);
        }
        Intent intent = new Intent(this._context, MirroringService.class);
        Context context2 = this._context;
        if (context2 != null) {
            context2.stopService(intent);
        }
    }

    @Override // com.epson.iprojection.ui.common.singleton.mirroring.Contract.IMirroringServiceCommander
    public void onChangeMPPControlMode() {
        IMirroringService iMirroringService = this._service;
        if (iMirroringService != null) {
            iMirroringService.onChangeMPPControlMode();
        }
    }

    @Override // com.epson.iprojection.ui.common.singleton.mirroring.Contract.IMirroringServiceCommander
    public void onChangeAudioSettings(boolean z) {
        IMirroringService iMirroringService = this._service;
        if (iMirroringService != null) {
            iMirroringService.onChangeAudioSettings(z);
        }
    }
}
