package com.epson.iprojection.ui.common.toast;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;
import android.widget.Toast;
import com.epson.iprojection.R;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* loaded from: classes.dex */
public class ToastMgr {
    private static final ToastMgr _inst = new ToastMgr();
    private final Handler _handler = new Handler();
    private Type _nowShowingToast = Type.Nothing;
    private final LinkedHashSet<Type> _delayToastList = new LinkedHashSet<>();
    private final Runnable _showTimer = new Runnable() { // from class: com.epson.iprojection.ui.common.toast.ToastMgr$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            ToastMgr.this.m204lambda$new$0$comepsoniprojectionuicommontoastToastMgr();
        }
    };

    /* loaded from: classes.dex */
    public enum Type {
        ExitWhenConnecting,
        ExitWhenDisconnecting,
        FullBookmark,
        FileOpenError,
        RegisteredBookmark,
        NowUsingPleaseWait,
        InsertSDCard,
        NoResponse,
        ConnectOK,
        Disconnect,
        Delivered,
        Received,
        Nothing,
        ProjectMe,
        RedoMirroring
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-epson-iprojection-ui-common-toast-ToastMgr  reason: not valid java name */
    public /* synthetic */ void m204lambda$new$0$comepsoniprojectionuicommontoastToastMgr() {
        this._nowShowingToast = Type.Nothing;
    }

    public void show(Context context, Type type) {
        String string;
        if (this._nowShowingToast == type) {
            return;
        }
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
        if (!((PowerManager) context.getSystemService("power")).isInteractive() || keyguardManager.isKeyguardLocked()) {
            this._delayToastList.add(type);
            return;
        }
        this._delayToastList.remove(type);
        this._nowShowingToast = type;
        int i = 1;
        switch (AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$toast$ToastMgr$Type[type.ordinal()]) {
            case 1:
                string = context.getString(R.string._ExitPressAgainDisconnect_);
                i = 0;
                break;
            case 2:
                string = context.getString(R.string._ExitPressAgain_);
                i = 0;
                break;
            case 3:
                string = context.getString(R.string._BookmarkIsFull_);
                i = 0;
                break;
            case 4:
                string = context.getString(R.string._FileOpenError_);
                i = 0;
                break;
            case 5:
                string = context.getString(R.string._RegisterBookMark_);
                i = 0;
                break;
            case 6:
                string = context.getString(R.string._CurrentlyRunning_);
                break;
            case 7:
                string = context.getString(R.string._InsertSDCard_);
                break;
            case 8:
                string = context.getString(R.string._NoPjFound_);
                i = 0;
                break;
            case 9:
                string = context.getString(R.string._Connected_);
                break;
            case 10:
                string = context.getString(R.string._DisconnectFromiProjection_);
                i = 0;
                break;
            case 11:
                string = context.getString(R.string._ShareComplete_);
                break;
            case 12:
                string = context.getString(R.string._AutoDisplayReceivedImage_);
                break;
            case 13:
                string = context.getString(R.string._ProjectMe_);
                break;
            case 14:
                string = context.getString(R.string._RedoMirroring_);
                break;
            default:
                string = "";
                i = 0;
                break;
        }
        Toast.makeText(context, string, i).show();
        this._handler.postDelayed(this._showTimer, i == 0 ? 2000L : 4000L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.common.toast.ToastMgr$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$common$toast$ToastMgr$Type;

        static {
            int[] iArr = new int[Type.values().length];
            $SwitchMap$com$epson$iprojection$ui$common$toast$ToastMgr$Type = iArr;
            try {
                iArr[Type.ExitWhenConnecting.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$toast$ToastMgr$Type[Type.ExitWhenDisconnecting.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$toast$ToastMgr$Type[Type.FullBookmark.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$toast$ToastMgr$Type[Type.FileOpenError.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$toast$ToastMgr$Type[Type.RegisteredBookmark.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$toast$ToastMgr$Type[Type.NowUsingPleaseWait.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$toast$ToastMgr$Type[Type.InsertSDCard.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$toast$ToastMgr$Type[Type.NoResponse.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$toast$ToastMgr$Type[Type.ConnectOK.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$toast$ToastMgr$Type[Type.Disconnect.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$toast$ToastMgr$Type[Type.Delivered.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$toast$ToastMgr$Type[Type.Received.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$toast$ToastMgr$Type[Type.ProjectMe.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$common$toast$ToastMgr$Type[Type.RedoMirroring.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    public void showDelay(Context context) {
        Iterator it = new LinkedHashSet(this._delayToastList).iterator();
        while (it.hasNext()) {
            show(context, (Type) it.next());
        }
    }

    private ToastMgr() {
    }

    public static ToastMgr getIns() {
        return _inst;
    }
}
