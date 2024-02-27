package com.epson.iprojection.ui.activities.remote;

import android.content.Context;
import android.content.SharedPreferences;
import com.epson.iprojection.common.utils.PrefUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes.dex */
public class RemotePrefUtils {
    public static final String PREF_TAG_PASS_CHECK_ALL = "pref_tag_pass_check_all";
    public static final String PREF_TAG_REMOTE_PASS = "pref_tag_saved_remote_pass_per_one_device";
    public static final String SEPARATOR = ",";
    public static final String TAG = "remote_isAuthenticated";

    public static boolean exists(Context context) {
        return (context == null || PrefUtils.read(context, TAG) == null) ? false : true;
    }

    public static String getAuthenticatedData(Context context) {
        if (context == null) {
            return null;
        }
        return PrefUtils.read(context, TAG);
    }

    public static void authenticated(Context context, byte[] bArr) {
        if (context == null) {
            return;
        }
        add(context, convertHexToString(bArr));
    }

    public static boolean isAuthenticated(Context context, byte[] bArr) {
        if (context == null) {
            return false;
        }
        String read = PrefUtils.read(context, TAG);
        String convertHexToString = convertHexToString(bArr);
        if (read == null) {
            return false;
        }
        for (String str : read.split(SEPARATOR)) {
            if (str.compareTo(convertHexToString) == 0) {
                return true;
            }
        }
        return false;
    }

    public static void delete(Context context, byte[] bArr) {
        String read;
        if (context == null || (read = PrefUtils.read(context, TAG)) == null) {
            return;
        }
        StringBuilder sb = new StringBuilder(read);
        String convertHexToString = convertHexToString(bArr);
        ArrayList arrayList = new ArrayList(Arrays.asList(sb.toString().split(SEPARATOR)));
        arrayList.remove(convertHexToString);
        StringBuilder sb2 = new StringBuilder();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (sb2.length() != 0) {
                sb2.append(SEPARATOR);
            }
            sb2.append(str);
        }
        PrefUtils.write(context, TAG, sb2.toString());
    }

    public static void deleteAll(Context context) {
        if (context == null) {
            return;
        }
        PrefUtils.delete(context, TAG, (SharedPreferences.Editor) null);
    }

    private static void add(Context context, String str) {
        if (context == null) {
            return;
        }
        String read = PrefUtils.read(context, TAG);
        if (read != null && read.length() != 0) {
            if (exists(str, read)) {
                return;
            }
            str = read + SEPARATOR + str;
        }
        PrefUtils.write(context, TAG, str);
    }

    private static boolean exists(String str, String str2) {
        for (String str3 : str2.split(SEPARATOR)) {
            if (str3.compareTo(str) == 0) {
                return true;
            }
        }
        return false;
    }

    private static String convertHexToString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02x", Byte.valueOf(b)));
        }
        return sb.toString();
    }
}
