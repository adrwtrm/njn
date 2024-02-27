package com.epson.iprojection.ui.engine_wrapper;

import android.content.Context;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.PrefUtils;

/* loaded from: classes.dex */
public class StateMachine {
    private static final String FALSE = "false";
    private static final String TAG_REGISTERED_PJS = "tag_registered_pjs";
    private static final String TRUE = "true";
    protected Context _context = null;
    protected ConnectState _connectState = ConnectState.NotInitYet;
    protected RegisterState _registerState = RegisterState.NoRegister;

    /* loaded from: classes.dex */
    public enum ConnectState {
        NotInitYet,
        NeedRestart,
        Default,
        PreTryConnectManualSearching,
        WaitOtherPJReady,
        TryConnecting,
        TryDisconnecting,
        NowConnecting
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public enum RegisterState {
        NoRegister,
        Registered
    }

    /* loaded from: classes.dex */
    public enum State {
        Unselected,
        Registered,
        Connected
    }

    public State getState() {
        State state = State.Unselected;
        if (this._connectState == ConnectState.NowConnecting) {
            return State.Connected;
        }
        return this._registerState == RegisterState.Registered ? State.Registered : state;
    }

    public ConnectState getConnectState() {
        return this._connectState;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setConnectState(ConnectState connectState) {
        outputLogConnectState(this._connectState, connectState);
        this._connectState = connectState;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setRegisterState(RegisterState registerState) {
        this._registerState = registerState;
        PrefUtils.write(this._context, TAG_REGISTERED_PJS, registerState == RegisterState.Registered ? "true" : "false");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public RegisterState getRegisterState() {
        RegisterState registerState = RegisterState.NoRegister;
        String read = PrefUtils.read(this._context, TAG_REGISTERED_PJS);
        return (read == null || !read.equals("true")) ? registerState : RegisterState.Registered;
    }

    public boolean isRegistered() {
        return this._registerState == RegisterState.Registered;
    }

    protected static void outputLogConnectState(ConnectState connectState, ConnectState connectState2) {
        if (Lg.isEnable()) {
            Lg.d(String.format("%s [ %s(%04d) %s() ]", String.format("【ステータス変更】[%s]→[%s] from ", getStateMachineStatusMsg(connectState), getStateMachineStatusMsg(connectState2)), Thread.currentThread().getStackTrace()[3].getFileName(), Integer.valueOf(Thread.currentThread().getStackTrace()[3].getLineNumber()), Thread.currentThread().getStackTrace()[3].getMethodName()));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.engine_wrapper.StateMachine$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$ConnectState;

        static {
            int[] iArr = new int[ConnectState.values().length];
            $SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$ConnectState = iArr;
            try {
                iArr[ConnectState.NotInitYet.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$ConnectState[ConnectState.NeedRestart.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$ConnectState[ConnectState.Default.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$ConnectState[ConnectState.TryConnecting.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$ConnectState[ConnectState.TryDisconnecting.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$ConnectState[ConnectState.NowConnecting.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$ConnectState[ConnectState.PreTryConnectManualSearching.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static String getStateMachineStatusMsg(ConnectState connectState) {
        switch (AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$engine_wrapper$StateMachine$ConnectState[connectState.ordinal()]) {
            case 1:
                return "未初期化";
            case 2:
                return "再起動要求";
            case 3:
                return "デフォルト";
            case 4:
                return "接続を試み中";
            case 5:
                return "切断を試み中";
            case 6:
                return "接続中";
            case 7:
                return "接続前指定検索中";
            default:
                return "不明なステータス(未定義)";
        }
    }
}
