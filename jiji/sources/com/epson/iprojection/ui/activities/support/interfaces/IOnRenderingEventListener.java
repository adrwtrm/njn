package com.epson.iprojection.ui.activities.support.interfaces;

/* loaded from: classes.dex */
public interface IOnRenderingEventListener {
    void onRenderingEnd(String str);

    void onRenderingError();

    void onRenderingInterrupt();

    void onRenderingStart(String str);
}
