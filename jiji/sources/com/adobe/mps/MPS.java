package com.adobe.mps;

import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class MPS {
    public native int MPSInit(String str, String str2);

    public native int MPSTerm();

    public native int PDFDocInit(String str);

    public native int[] PDFGetPageAttributes(int i);

    public native int PDFPageRender(int i, int i2, int i3, int i4, ByteBuffer byteBuffer, int i5, int[] iArr);

    public native int PDFPagetoImage(int i, String str, int i2, int i3, int i4);

    static {
        System.loadLibrary("APC");
        System.loadLibrary("MPS");
    }
}
