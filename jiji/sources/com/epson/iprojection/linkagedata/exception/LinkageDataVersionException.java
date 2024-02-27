package com.epson.iprojection.linkagedata.exception;

import com.epson.iprojection.common.Lg;

/* loaded from: classes.dex */
public class LinkageDataVersionException extends Exception {
    public LinkageDataVersionException(String str) {
        super(str);
        Lg.e(str);
    }
}
