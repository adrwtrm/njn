package com.epson.iprojection.ui.engine_wrapper.interfaces;

import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;

/* loaded from: classes.dex */
public interface IOnMinConnectListener {
    void onConnectionFailed(int i, IOnConnectListener.FailReason failReason);

    void onConnectionSucceeded();
}
