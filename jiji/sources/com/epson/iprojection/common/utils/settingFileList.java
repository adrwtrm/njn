package com.epson.iprojection.common.utils;

import com.epson.iprojection.common.CommonDefine;
import java.io.File;
import java.io.FilenameFilter;

/* compiled from: XmlUtils.java */
/* loaded from: classes.dex */
class settingFileList implements FilenameFilter {
    @Override // java.io.FilenameFilter
    public boolean accept(File file, String str) {
        return str.equals(CommonDefine.IPROJECTION_SETTINGS);
    }
}
