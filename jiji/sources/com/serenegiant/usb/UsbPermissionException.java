package com.serenegiant.usb;

/* loaded from: classes2.dex */
public class UsbPermissionException extends UsbException {
    private static final long serialVersionUID = -8430122770852248672L;

    public UsbPermissionException() {
    }

    public UsbPermissionException(String str) {
        super(str);
    }

    public UsbPermissionException(String str, Throwable th) {
        super(str, th);
    }

    public UsbPermissionException(Throwable th) {
        super(th);
    }
}
