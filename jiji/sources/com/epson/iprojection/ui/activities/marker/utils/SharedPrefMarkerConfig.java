package com.epson.iprojection.ui.activities.marker.utils;

import android.content.Context;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.activities.marker.config.ConfigDefine;

/* loaded from: classes.dex */
public class SharedPrefMarkerConfig {
    private static final String TAG_ERASER_WIDTH = "Paint_Conf_EraserWidth";
    private static final String TAG_PEN_ALPHA = "Paint_Conf_PenAlpha";
    private static final String TAG_PEN_COL_N = "Paint_Conf_PenColor_N";
    private static final String TAG_PEN_WIDTH = "Paint_Conf_PenWidth_V200";
    private static final String TAG_USING_TOOL_ID = "UsingToolID";

    public static void writeUsingToolID(Context context, int i) {
        if (i < 0 || i > 2) {
            return;
        }
        PrefUtils.writeInt(context, TAG_USING_TOOL_ID, i);
    }

    public static int readUsingToolID(Context context) {
        int readInt = PrefUtils.readInt(context, TAG_USING_TOOL_ID);
        if (readInt < 0 || 2 < readInt) {
            return 0;
        }
        return readInt;
    }

    public static void writePenColor(Context context, int i, int i2) {
        if (i2 < 0 || i2 > ConfigDefine.PEN_COLOR_N - 1) {
            return;
        }
        PrefUtils.writeInt(context, TAG_PEN_COL_N, i, i2);
    }

    public static int readPenColor(Context context, int i) {
        int readInt = PrefUtils.readInt(context, TAG_PEN_COL_N, i);
        if (readInt < 0 || ConfigDefine.PEN_COLOR_N - 1 < readInt) {
            if (i >= 0 && i < ConfigDefine.PEN_DEFAULT_COLOR_ID.length) {
                return ConfigDefine.PEN_DEFAULT_COLOR_ID[i];
            }
            return ConfigDefine.PEN_DEFAULT_COLOR_ID[0];
        }
        return readInt;
    }

    public static void writePenWidth(Context context, int i, int i2) {
        if (i2 < 0 || i2 > 14) {
            return;
        }
        PrefUtils.writeInt(context, TAG_PEN_WIDTH, i, i2);
    }

    public static int readPenWidth(Context context, int i) {
        int readInt = PrefUtils.readInt(context, TAG_PEN_WIDTH, i);
        if (readInt < 0 || 14 < readInt) {
            return 8;
        }
        return readInt;
    }

    public static void writePenAlpha(Context context, int i, int i2) {
        if (i2 == 0 || i2 == 1) {
            PrefUtils.writeInt(context, TAG_PEN_ALPHA, i, i2);
        }
    }

    public static int readPenAlpha(Context context, int i) {
        int readInt = PrefUtils.readInt(context, TAG_PEN_ALPHA, i);
        if (readInt == 0 || readInt == 1) {
            return readInt;
        }
        return 0;
    }

    public static void writeEraserWidth(Context context, int i) {
        if (i < 0 || i > ConfigDefine.ERASER_WIDTH_N - 1) {
            return;
        }
        PrefUtils.writeInt(context, TAG_ERASER_WIDTH, 2, i);
    }

    public static int readEraserWidth(Context context) {
        int readInt = PrefUtils.readInt(context, TAG_ERASER_WIDTH, 2);
        if (readInt < 0 || ConfigDefine.ERASER_WIDTH_N - 1 < readInt) {
            return 1;
        }
        return readInt;
    }
}
