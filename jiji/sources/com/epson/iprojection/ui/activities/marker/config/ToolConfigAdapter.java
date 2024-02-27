package com.epson.iprojection.ui.activities.marker.config;

import android.content.Context;
import com.epson.iprojection.ui.activities.marker.config.ConfigDefine;

/* loaded from: classes.dex */
abstract class ToolConfigAdapter implements IToolConfig {
    protected int _btnId;
    protected Context _context;
    protected ConfigDefine.ConfigKind _kind = ConfigDefine.ConfigKind.NONE;

    @Override // com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public int getAlpha() {
        return -1;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public int getColor() {
        return -1;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public int getColor(int i) {
        return -1;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public int getColorId() {
        return 0;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public abstract ConfigDefine.ConfigKind getKind();

    @Override // com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public int getWidth() {
        return -1;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public int getWidthMax() {
        return -1;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public boolean hasAlpha() {
        return false;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public void saveAlphaID(boolean z) {
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public void saveColorID(int i) {
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public void saveWidth(int i) {
    }

    public ToolConfigAdapter(Context context, int i) {
        this._context = context;
        this._btnId = i;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public int getBtnId() {
        return this._btnId;
    }
}
