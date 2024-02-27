package com.epson.iprojection.ui.engine_wrapper;

import com.epson.iprojection.common.utils.AspectRatioUtils;

/* loaded from: classes.dex */
public class Resolution {
    private static final int HEIGHT = 960;
    private static final int WIDTH = 1280;
    private int _height;
    private int _width;

    public int getDefaultHeight() {
        return HEIGHT;
    }

    public int getDefaultWidth() {
        return 1280;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Resolution() {
        setDefaultResolution();
    }

    public int getWidth() {
        return this._width;
    }

    public int getHeight() {
        return this._height;
    }

    private void setWidth(int i) {
        this._width = i;
    }

    private void setHeight(int i) {
        this._height = i;
    }

    private void setDefaultResolution() {
        setWidth(1280);
        setHeight(HEIGHT);
    }

    public void setResolutionOnConnect(int i, int i2) {
        setWidth(i);
        setHeight(i2);
    }

    public void setResolutionOnDisonnect() {
        setDefaultResolution();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.engine_wrapper.Resolution$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$common$utils$AspectRatioUtils$AspectRatio;

        static {
            int[] iArr = new int[AspectRatioUtils.AspectRatio.values().length];
            $SwitchMap$com$epson$iprojection$common$utils$AspectRatioUtils$AspectRatio = iArr;
            try {
                iArr[AspectRatioUtils.AspectRatio.ASPECTRATIO_21_09.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$common$utils$AspectRatioUtils$AspectRatio[AspectRatioUtils.AspectRatio.ASPECTRATIO_16_06.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public boolean isAspectRatioUltraWide() {
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$common$utils$AspectRatioUtils$AspectRatio[AspectRatioUtils.Companion.calc(this._width, this._height).ordinal()];
        return i == 1 || i == 2;
    }
}
