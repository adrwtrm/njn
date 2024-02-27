package com.epson.iprojection.ui.activities.pjselect.dialogs;

import android.content.Context;
import android.os.Handler;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseProgressDialog;
import com.epson.iprojection.ui.common.uiparts.ProgressDialogInfinityType;

/* loaded from: classes.dex */
public class SpinDialog extends BaseProgressDialog {
    private final Handler _handler;
    private final int _limitTime;
    private SelfKillTimer _timer;
    private final MessageType _type;

    /* loaded from: classes.dex */
    public enum MessageType {
        Connecting,
        Disconnecting,
        Searching,
        Delivering
    }

    public SpinDialog(Context context, MessageType messageType, int i) {
        super(context);
        this._timer = null;
        this._handler = new Handler();
        this._type = messageType;
        this._limitTime = i;
        if (this._dialog == null || context == null || messageType == null) {
            return;
        }
        this._dialog.setMessage(getTitle(context, messageType));
    }

    public void recreate(Context context) {
        if (context == null || this._type == null) {
            return;
        }
        this._dialog = new ProgressDialogInfinityType(context);
        this._dialog.setCancelable(false);
        this._dialog.setMessage(getTitle(context, this._type));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.activities.pjselect.dialogs.SpinDialog$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$SpinDialog$MessageType;

        static {
            int[] iArr = new int[MessageType.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$SpinDialog$MessageType = iArr;
            try {
                iArr[MessageType.Connecting.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$SpinDialog$MessageType[MessageType.Disconnecting.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$SpinDialog$MessageType[MessageType.Searching.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$SpinDialog$MessageType[MessageType.Delivering.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private static String getTitle(Context context, MessageType messageType) {
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$dialogs$SpinDialog$MessageType[messageType.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return i != 4 ? "" : context.getString(R.string._Sharing_);
                }
                return context.getString(R.string._SearchingProjector_);
            }
            return context.getString(R.string._Disconnecting_);
        }
        return context.getString(R.string._Connecting_);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseProgressDialog
    public void show() {
        super.show();
        this._timer = new SelfKillTimer(this._limitTime);
        new Thread(this._timer).start();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseProgressDialog
    public void delete() {
        super.delete();
        if (this._dialog != null && this._dialog.isShowing()) {
            this._dialog.dismiss();
        }
        SelfKillTimer selfKillTimer = this._timer;
        if (selfKillTimer != null) {
            selfKillTimer.disable();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class SelfKillTimer implements Runnable {
        private static final int CHECK_WAIT_INTERVAL = 50;
        private boolean _isEnable = true;
        private int _limitTime;

        public SelfKillTimer(int i) {
            this._limitTime = i;
        }

        public void disable() {
            this._isEnable = false;
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z;
            if (this._limitTime <= 0) {
                disable();
            }
            while (true) {
                z = this._isEnable;
                if (!z || this._limitTime <= 0) {
                    break;
                }
                Sleeper.sleep(50L);
                this._limitTime -= 50;
            }
            if (z) {
                Handler handler = SpinDialog.this._handler;
                final SpinDialog spinDialog = SpinDialog.this;
                handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.SpinDialog$SelfKillTimer$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SpinDialog.this.delete();
                    }
                });
            }
        }
    }

    public MessageType getType() {
        return this._type;
    }

    public boolean isShowing() {
        if (this._dialog != null) {
            return this._dialog.isShowing();
        }
        return false;
    }
}
