package com.epson.iprojection.ui.activities.pjselect;

import android.view.View;
import com.epson.iprojection.common.utils.XmlUtils;
import com.epson.iprojection.engine.common.D_PjInfo;

/* loaded from: classes.dex */
public interface IProjListListener {
    void onClickFolder(int i);

    void onClickItem(D_PjInfo d_PjInfo, View view);

    void onClickProfile();

    void onClickProfile(XmlUtils.mplistType mplisttype);

    boolean onClickProjectorDetail(D_PjInfo d_PjInfo);

    void updateList(boolean z);
}
