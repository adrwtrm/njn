package com.epson.iprojection.ui.engine_wrapper.interfaces;

import com.epson.iprojection.engine.common.D_PjInfo;

/* loaded from: classes.dex */
public interface IPjManualSearchResultListener {
    void onEndSearchPj();

    void onFindSearchPj(D_PjInfo d_PjInfo, boolean z);
}
