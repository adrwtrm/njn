package com.epson.iprojection.ui.activities.marker.config;

import com.epson.iprojection.R;

/* loaded from: classes.dex */
public class ConfigDefine {
    public static final int[] ERASER_BTN_IDs;
    public static final int ERASER_DEFAULT_WIDTH = 1;
    public static final int[] ERASER_WIDTH;
    public static final int ERASER_WIDTH_N;
    public static final int PEN_ALPHA_OFF = 0;
    public static final int PEN_ALPHA_OFF_VAL = 255;
    public static final int PEN_ALPHA_ON = 1;
    public static final int PEN_ALPHA_ON_VAL = 128;
    public static final int[] PEN_BTN_IDs;
    public static final int[][] PEN_COLOR = {new int[]{255, 0, 0}, new int[]{0, 255, 0}, new int[]{0, 0, 255}, new int[]{255, 255, 0}, new int[]{255, 0, 255}, new int[]{0, 255, 255}, new int[]{0, 173, 8}, new int[]{255, 93, 234}, new int[]{255, 126, 0}, new int[]{114, 78, 7}, new int[]{0, 0, 0}, new int[]{255, 255, 255}};
    public static final int PEN_COLOR_N;
    public static final int PEN_DEFAULT_ALPHA = 0;
    public static final int[] PEN_DEFAULT_COLOR_ID;
    public static final int PEN_DEFAULT_WIDTH = 9;
    public static final int PEN_WIDTH_MAX = 15;
    public static final int TOOL_ID_ERASER = 2;
    public static final int TOOL_ID_PEN1 = 0;
    public static final int TOOL_ID_PEN2 = 1;

    /* loaded from: classes.dex */
    public enum ConfigKind {
        NONE,
        PEN,
        ERASER
    }

    static {
        int[] iArr = {R.id.btn_marker_penColorRed, R.id.btn_marker_penColorGreen, R.id.btn_marker_penColorBlue, R.id.btn_marker_penColorYellow, R.id.btn_marker_penColorMagenda, R.id.btn_marker_penColorCyan, R.id.btn_marker_penColorDarkGreen, R.id.btn_marker_penColorPink, R.id.btn_marker_penColorOrange, R.id.btn_marker_penColorCha, R.id.btn_marker_penColorBlack, R.id.btn_marker_penColorWhite};
        PEN_BTN_IDs = iArr;
        ERASER_WIDTH = new int[]{12, 24, 36};
        int[] iArr2 = {R.id.btn_marker_eraserWidthThin, R.id.btn_marker_eraserWidthStandard, R.id.btn_marker_eraserWidthThick};
        ERASER_BTN_IDs = iArr2;
        PEN_COLOR_N = iArr.length;
        ERASER_WIDTH_N = iArr2.length;
        PEN_DEFAULT_COLOR_ID = new int[]{10, 2};
    }
}
