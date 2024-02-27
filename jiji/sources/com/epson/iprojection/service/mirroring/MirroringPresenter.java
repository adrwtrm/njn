package com.epson.iprojection.service.mirroring;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.provider.Settings;
import com.epson.iprojection.R;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.service.mirroring.MirroringContract;
import com.epson.iprojection.service.mirroring.floatingview.FloatingButton;
import com.epson.iprojection.service.mirroring.floatingview.FloatingMediator;
import com.epson.iprojection.service.mirroring.floatingview.FloatingRemoverImage;
import com.epson.iprojection.service.mirroring.floatingview.IColleague;
import com.epson.iprojection.service.mirroring.floatingview.IMediator;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.toast.ToastMgr;
import com.epson.iprojection.ui.engine_wrapper.DisconReason;
import com.epson.iprojection.ui.engine_wrapper.IPj;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MirroringPresenter.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u000eH\u0002J\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H&J\b\u0010\u0013\u001a\u00020\u000eH\u0002J\b\u0010\u0014\u001a\u00020\u000eH\u0016J\u0010\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u001cH\u0002J\u0010\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u001aH\u0016J\b\u0010 \u001a\u00020\u000eH\u0002J\b\u0010!\u001a\u00020\u000eH\u0002J\b\u0010\"\u001a\u00020\u000eH\u0002R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lcom/epson/iprojection/service/mirroring/MirroringPresenter;", "Lcom/epson/iprojection/service/mirroring/MirroringContract$IPresenter;", "_context", "Landroid/content/Context;", "_view", "Lcom/epson/iprojection/service/mirroring/MirroringContract$IView;", "_pj", "Lcom/epson/iprojection/ui/engine_wrapper/IPj;", "(Landroid/content/Context;Lcom/epson/iprojection/service/mirroring/MirroringContract$IView;Lcom/epson/iprojection/ui/engine_wrapper/IPj;)V", "get_context", "()Landroid/content/Context;", "_mediator", "Lcom/epson/iprojection/service/mirroring/floatingview/IMediator;", "finish", "", "onCatchDisconnectCommand", "onCatchPauseCommand", "value", "", "onCatchProjectionMySelfCommand", "onChangeMPPControlMode", "onRotated", "newConfiguration", "Landroid/content/res/Configuration;", "onStartCommand", "intent", "Landroid/content/Intent;", "shouldOverlay", "", "shouldShowToast", "start", "captureIntent", "startOverlay", "stopOverlay", "updateOverlay", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public abstract class MirroringPresenter implements MirroringContract.IPresenter {
    private final Context _context;
    private IMediator _mediator;
    private final IPj _pj;
    private final MirroringContract.IView _view;

    public abstract void onCatchPauseCommand(String str);

    public MirroringPresenter(Context _context, MirroringContract.IView _view, IPj _pj) {
        Intrinsics.checkNotNullParameter(_context, "_context");
        Intrinsics.checkNotNullParameter(_view, "_view");
        Intrinsics.checkNotNullParameter(_pj, "_pj");
        this._context = _context;
        this._view = _view;
        this._pj = _pj;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final Context get_context() {
        return this._context;
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public void start(Intent captureIntent) {
        Intrinsics.checkNotNullParameter(captureIntent, "captureIntent");
        Lg.d("[s] >>> ミラーリング中 <<<");
        if (shouldOverlay()) {
            startOverlay();
        }
        this._view.showToast(R.string._ProjectionStarted_);
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public void onStartCommand(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        Lg.d("[s] onStartCommand");
        String stringExtra = intent.getStringExtra(CommonDefine.INTENT_EXTRA_PAUSE);
        if (stringExtra != null) {
            Lg.d("[s] AVミュートボタン押下受信");
            onCatchPauseCommand(stringExtra);
        }
        if (intent.getStringExtra(CommonDefine.INTENT_EXTRA_DISCONNECT) != null) {
            Lg.d("[s] 切断ボタン押下受信");
            onCatchDisconnectCommand();
        }
        if (intent.getStringExtra(CommonDefine.INTENT_EXTRA_MYSELF) != null) {
            Lg.d("[s] 自分投写ボタン押下受信");
            onCatchProjectionMySelfCommand();
        }
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public void finish() {
        Lg.d("[s] finish");
        this._view.deleteNotification();
        stopOverlay();
        this._pj.sendImageOfMirroringOff();
        this._view.showToast(R.string._ProjectionStop_);
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public void onRotated(Configuration newConfiguration) {
        Intrinsics.checkNotNullParameter(newConfiguration, "newConfiguration");
        Lg.d("onRotated : " + newConfiguration);
        IMediator iMediator = this._mediator;
        if (iMediator != null) {
            iMediator.onRotated();
        }
    }

    @Override // com.epson.iprojection.service.mirroring.MirroringContract.IPresenter
    public void onChangeMPPControlMode() {
        Lg.d("onChangeMPPControlMode");
        this._view.refreshNotification();
        updateOverlay();
    }

    private final void onCatchDisconnectCommand() {
        this._pj.disconnect(DisconReason.TemporaryForMirroring);
    }

    private final void onCatchProjectionMySelfCommand() {
        Pj.getIns().projectionMyself();
        ToastMgr.getIns().show(this._context, ToastMgr.Type.ProjectMe);
    }

    private final void startOverlay() {
        this._mediator = new FloatingMediator(this._context);
        FloatingButton floatingButton = new FloatingButton(this._context, IColleague.ViewType.DeliveryButton);
        FloatingRemoverImage floatingRemoverImage = new FloatingRemoverImage(this._context, IColleague.ViewType.RemoverImage);
        IMediator iMediator = this._mediator;
        Intrinsics.checkNotNull(iMediator);
        floatingButton.setMediator(iMediator);
        IMediator iMediator2 = this._mediator;
        Intrinsics.checkNotNull(iMediator2);
        floatingRemoverImage.setMediator(iMediator2);
        IMediator iMediator3 = this._mediator;
        if (iMediator3 != null) {
            iMediator3.addView(floatingRemoverImage);
        }
        if (iMediator3 != null) {
            iMediator3.addView(floatingButton);
        }
        if (iMediator3 != null) {
            iMediator3.startOverlay();
        }
    }

    private final void stopOverlay() {
        IMediator iMediator = this._mediator;
        if (iMediator != null) {
            iMediator.stopOverlay();
        }
        this._mediator = null;
    }

    private final void updateOverlay() {
        if (shouldOverlay()) {
            startOverlay();
        } else if (shouldShowToast()) {
            ToastMgr.getIns().show(this._context, ToastMgr.Type.RedoMirroring);
        } else if (this._pj.isModerator()) {
        } else {
            stopOverlay();
        }
    }

    private final boolean shouldOverlay() {
        return Pj.getIns().isEnableMppDelivery() && PrefUtils.readInt(this._context, PrefTagDefine.conPJ_DISPLAY_DELIVERY_BUTTON_TAG) == 1 && this._pj.isModerator() && Settings.canDrawOverlays(this._context);
    }

    private final boolean shouldShowToast() {
        return Pj.getIns().isEnableMppDelivery() && PrefUtils.readInt(this._context, PrefTagDefine.conPJ_DISPLAY_DELIVERY_BUTTON_TAG) == 1 && this._pj.isModerator() && !Settings.canDrawOverlays(this._context);
    }
}
