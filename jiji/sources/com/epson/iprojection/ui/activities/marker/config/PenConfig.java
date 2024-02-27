package com.epson.iprojection.ui.activities.marker.config;

import android.content.Context;
import android.graphics.Color;
import com.epson.iprojection.ui.activities.marker.config.ConfigDefine;
import com.epson.iprojection.ui.activities.marker.utils.SharedPrefMarkerConfig;

/* loaded from: classes.dex */
public class PenConfig extends ToolConfigAdapter {
    private final int _penId;

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public int getWidthMax() {
        return 15;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public /* bridge */ /* synthetic */ int getBtnId() {
        return super.getBtnId();
    }

    public PenConfig(Context context, int i, int i2) {
        super(context, i);
        this._penId = i2;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public ConfigDefine.ConfigKind getKind() {
        return ConfigDefine.ConfigKind.PEN;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public int getColorId() {
        return SharedPrefMarkerConfig.readPenColor(this._context, this._penId);
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public void saveColorID(int i) {
        SharedPrefMarkerConfig.writePenColor(this._context, this._penId, i);
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public int getColor() {
        return getColor(getColorId());
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public int getColor(int i) {
        return Color.rgb(ConfigDefine.PEN_COLOR[i][0], ConfigDefine.PEN_COLOR[i][1], ConfigDefine.PEN_COLOR[i][2]);
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public boolean hasAlpha() {
        return SharedPrefMarkerConfig.readPenAlpha(this._context, this._penId) == 1;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public void saveAlphaID(boolean z) {
        SharedPrefMarkerConfig.writePenAlpha(this._context, this._penId, z ? 1 : 0);
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public int getAlpha() {
        return hasAlpha() ? 128 : 255;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public int getWidth() {
        return SharedPrefMarkerConfig.readPenWidth(this._context, this._penId) + 1;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public void saveWidth(int i) {
        SharedPrefMarkerConfig.writePenWidth(this._context, this._penId, i - 1);
    }
}
