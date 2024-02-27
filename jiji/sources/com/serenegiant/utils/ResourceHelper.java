package com.serenegiant.utils;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.epson.iprojection.ui.activities.pjselect.Activity_PjSelect;

/* loaded from: classes2.dex */
public class ResourceHelper {
    private ResourceHelper() {
    }

    public static int get(Context context, String str, int i) throws Resources.NotFoundException, NumberFormatException, NullPointerException {
        int i2;
        if (!TextUtils.isEmpty(str) && str.startsWith("@")) {
            int identifier = context.getResources().getIdentifier(str.substring(1), null, context.getPackageName());
            return identifier > 0 ? context.getResources().getInteger(identifier) : i;
        }
        if (str == null || str.length() <= 2 || str.charAt(0) != '0' || !(str.charAt(1) == 'x' || str.charAt(1) == 'X')) {
            i2 = 10;
        } else {
            str = str.substring(2);
            i2 = 16;
        }
        return Integer.parseInt(str, i2);
    }

    public static boolean get(Context context, String str, boolean z) throws Resources.NotFoundException, NumberFormatException, NullPointerException {
        int i;
        if (Activity_PjSelect.TRUE.equalsIgnoreCase(str)) {
            return true;
        }
        if (!"FALSE".equalsIgnoreCase(str)) {
            if (!TextUtils.isEmpty(str) && str.startsWith("@")) {
                int identifier = context.getResources().getIdentifier(str.substring(1), null, context.getPackageName());
                if (identifier > 0) {
                    z = context.getResources().getBoolean(identifier);
                }
                return z;
            }
            if (str == null || str.length() <= 2 || str.charAt(0) != '0' || !(str.charAt(1) == 'x' || str.charAt(1) == 'X')) {
                i = 10;
            } else {
                str = str.substring(2);
                i = 16;
            }
            if (Integer.parseInt(str, i) != 0) {
                return true;
            }
        }
        return false;
    }

    public static String get(Context context, String str, String str2) throws Resources.NotFoundException, NullPointerException {
        if (str == null) {
            str = str2;
        }
        if (TextUtils.isEmpty(str) || !str.startsWith("@")) {
            return str;
        }
        int identifier = context.getResources().getIdentifier(str.substring(1), null, context.getPackageName());
        return identifier > 0 ? context.getResources().getString(identifier) : str;
    }

    public static CharSequence get(Context context, CharSequence charSequence, CharSequence charSequence2) throws Resources.NotFoundException, NullPointerException {
        if (charSequence == null) {
            charSequence = charSequence2;
        }
        if (TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        String obj = charSequence.toString();
        if (obj.startsWith("@")) {
            int identifier = context.getResources().getIdentifier(obj.substring(1), null, context.getPackageName());
            return identifier > 0 ? context.getResources().getText(identifier) : charSequence;
        }
        return charSequence;
    }
}
