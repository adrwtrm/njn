package com.epson.iprojection.ui.activities.marker.config;

import com.epson.iprojection.ui.activities.marker.config.ConfigDefine;

/* loaded from: classes.dex */
public interface IToolConfig {
    int getAlpha();

    int getBtnId();

    int getColor();

    int getColor(int i);

    int getColorId();

    ConfigDefine.ConfigKind getKind();

    int getWidth();

    int getWidthMax();

    boolean hasAlpha();

    void saveAlphaID(boolean z);

    void saveColorID(int i);

    void saveWidth(int i);
}
