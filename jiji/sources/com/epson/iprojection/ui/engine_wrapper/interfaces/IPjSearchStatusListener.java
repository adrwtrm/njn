package com.epson.iprojection.ui.engine_wrapper.interfaces;

import com.epson.iprojection.engine.common.D_PjInfo;

/* loaded from: classes.dex */
public interface IPjSearchStatusListener {
    void onPjFind(D_PjInfo d_PjInfo, boolean z);

    void onPjLose(int i);

    void onPjStatusChanged(D_PjInfo d_PjInfo, boolean z);

    void onSearchPause();

    void onSearchStart();
}
