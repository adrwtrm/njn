package com.epson.iprojection.ui.activities.marker.config;

import android.content.Context;
import com.epson.iprojection.ui.activities.marker.config.ConfigDefine;
import com.epson.iprojection.ui.activities.marker.utils.SharedPrefMarkerConfig;

/* loaded from: classes.dex */
public class EraserConfig extends ToolConfigAdapter {
    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public int getAlpha() {
        return 255;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public int getColor() {
        return -1;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public /* bridge */ /* synthetic */ int getBtnId() {
        return super.getBtnId();
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public /* bridge */ /* synthetic */ int getColor(int i) {
        return super.getColor(i);
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public /* bridge */ /* synthetic */ int getColorId() {
        return super.getColorId();
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public /* bridge */ /* synthetic */ int getWidthMax() {
        return super.getWidthMax();
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public /* bridge */ /* synthetic */ boolean hasAlpha() {
        return super.hasAlpha();
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public /* bridge */ /* synthetic */ void saveAlphaID(boolean z) {
        super.saveAlphaID(z);
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public /* bridge */ /* synthetic */ void saveColorID(int i) {
        super.saveColorID(i);
    }

    public EraserConfig(Context context, int i) {
        super(context, i);
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public ConfigDefine.ConfigKind getKind() {
        return ConfigDefine.ConfigKind.ERASER;
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public int getWidth() {
        return ConfigDefine.ERASER_WIDTH[SharedPrefMarkerConfig.readEraserWidth(this._context)];
    }

    @Override // com.epson.iprojection.ui.activities.marker.config.ToolConfigAdapter, com.epson.iprojection.ui.activities.marker.config.IToolConfig
    public void saveWidth(int i) {
        for (int i2 = 0; i2 < ConfigDefine.ERASER_WIDTH.length; i2++) {
            if (i == ConfigDefine.ERASER_WIDTH[i2]) {
                SharedPrefMarkerConfig.writeEraserWidth(this._context, i2);
                return;
            }
        }
    }
}
