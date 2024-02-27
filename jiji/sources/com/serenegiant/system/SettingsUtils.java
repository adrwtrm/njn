package com.serenegiant.system;

import android.content.Context;
import android.content.res.Resources;
import androidx.constraintlayout.core.motion.utils.TypedValues;

/* loaded from: classes2.dex */
public class SettingsUtils {
    private SettingsUtils() {
    }

    public static int getMinimumScreenBrightnessSetting(Context context) {
        Resources system = Resources.getSystem();
        int identifier = system.getIdentifier("config_screenBrightnessSettingMinimum", TypedValues.Custom.S_INT, "android");
        if (identifier == 0) {
            identifier = system.getIdentifier("config_screenBrightnessDim", TypedValues.Custom.S_INT, "android");
        }
        if (identifier != 0) {
            try {
                return system.getInteger(identifier);
            } catch (Resources.NotFoundException unused) {
                return 0;
            }
        }
        return 0;
    }

    public static int getMaximumScreenBrightnessSetting(Context context) {
        Resources system = Resources.getSystem();
        int identifier = system.getIdentifier("config_screenBrightnessSettingMaximum", TypedValues.Custom.S_INT, "android");
        if (identifier != 0) {
            try {
                return system.getInteger(identifier);
            } catch (Resources.NotFoundException unused) {
                return 255;
            }
        }
        return 255;
    }
}
