package com.epson.iprojection.ui.activities.presen.utils;

import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.activities.presen.Defines;
import java.io.File;

/* loaded from: classes.dex */
public class FileChecker {
    public static boolean isAvailableFile(String str) {
        if (str == null) {
            Lg.e("パスがnullです");
            return false;
        }
        Lg.d("渡されたpath[" + str + "]");
        File file = new File(str);
        if (!file.exists()) {
            Lg.e("ファイルがありません。");
            return false;
        } else if (!file.canRead()) {
            Lg.e("リード出来ないファイルです。");
            return false;
        } else if (isAvailableSuffix(str)) {
            Lg.d("利用可能な拡張子です");
            return true;
        } else {
            Lg.e("利用可能な拡張子ではありません");
            return false;
        }
    }

    private static boolean isAvailableSuffix(String str) {
        String suffix = getSuffix(str);
        if (suffix == null) {
            return false;
        }
        for (String str2 : Defines.USABLE_SUFFIX) {
            if (suffix.compareToIgnoreCase(str2) == 0) {
                return true;
            }
        }
        Lg.e("対応フォーマット外の拡張子です。[" + suffix + "]");
        return false;
    }

    private static String getSuffix(String str) {
        try {
            int lastIndexOf = str.lastIndexOf(".");
            if (lastIndexOf == -1) {
                Lg.e(".の位置が見つかりませんでした");
                return null;
            }
            String substring = str.substring(lastIndexOf + 1);
            if (substring == null) {
                Lg.e(".以降の文字列に分割できませんでした");
                return null;
            }
            return substring;
        } catch (Exception unused) {
            Lg.e(".以降の文字列に分割できませんでした");
            return null;
        }
    }
}
