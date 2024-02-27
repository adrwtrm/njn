package com.epson.iprojection.common.utils;

import com.epson.iprojection.common.CommonDefine;
import java.io.File;
import java.io.FilenameFilter;

/* compiled from: XmlUtils.java */
/* loaded from: classes.dex */
class ignoreList implements FilenameFilter {
    @Override // java.io.FilenameFilter
    public boolean accept(File file, String str) {
        for (String str2 : CommonDefine.EXCEPT_DELETE_FILE_LIST) {
            if (str2.equals(str)) {
                return false;
            }
        }
        return true;
    }
}
