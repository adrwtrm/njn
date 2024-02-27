package com.epson.iprojection.ui.activities.remote;

import android.content.Context;
import android.content.SharedPreferences;
import com.epson.iprojection.common.utils.PrefUtils;

/* loaded from: classes.dex */
public class RemotePasswordPrefUtils {
    private static final String SEPARATOR = ":";
    private static final String TAG_REMOTE = "pref_tag_remote_password_remote";
    private static final String TAG_WEBCONTROL = "pref_tag_remote_password_webcontrol";

    public static void addToWebControl(Context context, String str) {
        if (context == null) {
            return;
        }
        String read = PrefUtils.read(context, TAG_WEBCONTROL);
        if (read != null && read.length() != 0) {
            if (exists(str, read)) {
                return;
            }
            str = read + SEPARATOR + str;
        }
        PrefUtils.write(context, TAG_WEBCONTROL, str);
    }

    public static void addToRemote(Context context, String str) {
        if (context == null) {
            return;
        }
        String read = PrefUtils.read(context, TAG_REMOTE);
        if (read != null && read.length() != 0) {
            if (exists(str, read)) {
                return;
            }
            str = read + SEPARATOR + str;
        }
        PrefUtils.write(context, TAG_REMOTE, str);
    }

    public static String[] getWebControl(Context context) {
        String read = PrefUtils.read(context, TAG_WEBCONTROL);
        if (read == null) {
            return null;
        }
        return read.split(SEPARATOR);
    }

    public static String[] getHome(Context context) {
        String read = PrefUtils.read(context, TAG_REMOTE);
        if (read == null) {
            return null;
        }
        return read.split(SEPARATOR);
    }

    public static void deleteAll(Context context) {
        if (context == null) {
            return;
        }
        PrefUtils.delete(context, TAG_WEBCONTROL, (SharedPreferences.Editor) null);
        PrefUtils.delete(context, TAG_REMOTE, (SharedPreferences.Editor) null);
    }

    private static boolean exists(String str, String str2) {
        for (String str3 : str2.split(SEPARATOR)) {
            if (str3.compareTo(str) == 0) {
                return true;
            }
        }
        return false;
    }
}
