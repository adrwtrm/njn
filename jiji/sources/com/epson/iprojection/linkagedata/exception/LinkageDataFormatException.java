package com.epson.iprojection.linkagedata.exception;

import com.epson.iprojection.common.Lg;

/* loaded from: classes.dex */
public class LinkageDataFormatException extends Exception {
    public LinkageDataFormatException(String str) {
        super(str);
        Lg.e(str);
    }
}
