package com.serenegiant.usb;

import java.io.IOException;

/* loaded from: classes2.dex */
public class UsbException extends IOException {
    private static final long serialVersionUID = 9211466216423287742L;

    public UsbException() {
    }

    public UsbException(String str) {
        super(str);
    }

    public UsbException(String str, Throwable th) {
        super(str, th);
    }

    public UsbException(Throwable th) {
        super(th);
    }
}
