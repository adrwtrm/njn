package com.epson.iprojection.ui.common.singleton;

/* loaded from: classes.dex */
public class ServiceMessageReceiver {
    private static final ServiceMessageReceiver _inst = new ServiceMessageReceiver();
    private boolean _isReceivedDisconnectMsg = false;

    public void receiveDisconnectMsg(boolean z) {
        this._isReceivedDisconnectMsg = z;
    }

    public boolean isReceivedDisconnectMsg() {
        return this._isReceivedDisconnectMsg;
    }

    private ServiceMessageReceiver() {
    }

    public static ServiceMessageReceiver getIns() {
        return _inst;
    }
}
