package com.serenegiant.usb;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.system.ContextUtils;

/* loaded from: classes2.dex */
public class UsbDeviceInfo implements Const, Parcelable {
    public static final Parcelable.Creator<UsbDeviceInfo> CREATOR = new Parcelable.Creator<UsbDeviceInfo>() { // from class: com.serenegiant.usb.UsbDeviceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbDeviceInfo createFromParcel(Parcel parcel) {
            return new UsbDeviceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbDeviceInfo[] newArray(int i) {
            return new UsbDeviceInfo[i];
        }
    };
    private static final boolean DEBUG = false;
    private static final String TAG = "UsbDeviceInfo";
    public int configCounts;
    public UsbDevice device;
    public String manufacturer;
    public String product;
    public String serial;
    public String usb_version;
    public String version;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static UsbDeviceInfo getDeviceInfo(Context context, UsbDevice usbDevice) {
        return getDeviceInfo((UsbManager) ContextUtils.requireSystemService(context, UsbManager.class), usbDevice, new UsbDeviceInfo());
    }

    public static UsbDeviceInfo getDeviceInfo(UsbManager usbManager, UsbDevice usbDevice, UsbDeviceInfo usbDeviceInfo) {
        UsbDeviceConnection openDevice = (usbDevice == null || !usbManager.hasPermission(usbDevice)) ? null : usbManager.openDevice(usbDevice);
        try {
            return getDeviceInfo(openDevice, usbDevice, usbDeviceInfo);
        } finally {
            if (openDevice != null) {
                openDevice.close();
            }
        }
    }

    public static UsbDeviceInfo getDeviceInfo(UsbDeviceConnection usbDeviceConnection, UsbDevice usbDevice, UsbDeviceInfo usbDeviceInfo) {
        byte[] rawDescriptors;
        if (usbDeviceInfo == null) {
            usbDeviceInfo = new UsbDeviceInfo();
        }
        usbDeviceInfo.clear();
        usbDeviceInfo.device = usbDevice;
        if (usbDevice != null) {
            if (BuildCheck.isAPI29() && usbDeviceConnection != null) {
                usbDeviceInfo.manufacturer = usbDevice.getManufacturerName();
                usbDeviceInfo.product = usbDevice.getProductName();
                usbDeviceInfo.configCounts = usbDevice.getConfigurationCount();
            } else if (BuildCheck.isLollipop()) {
                usbDeviceInfo.manufacturer = usbDevice.getManufacturerName();
                usbDeviceInfo.product = usbDevice.getProductName();
                usbDeviceInfo.serial = usbDevice.getSerialNumber();
                usbDeviceInfo.configCounts = usbDevice.getConfigurationCount();
            }
            if (BuildCheck.isMarshmallow()) {
                usbDeviceInfo.version = usbDevice.getVersion();
            }
            if (usbDeviceConnection != null && (rawDescriptors = usbDeviceConnection.getRawDescriptors()) != null) {
                if (TextUtils.isEmpty(usbDeviceInfo.usb_version)) {
                    usbDeviceInfo.usb_version = String.format("%x.%02x", Integer.valueOf(rawDescriptors[3] & 255), Integer.valueOf(rawDescriptors[2] & 255));
                }
                if (TextUtils.isEmpty(usbDeviceInfo.version)) {
                    usbDeviceInfo.version = String.format("%x.%02x", Integer.valueOf(rawDescriptors[13] & 255), Integer.valueOf(rawDescriptors[12] & 255));
                }
                if (BuildCheck.isAPI29()) {
                    try {
                        usbDeviceInfo.serial = usbDevice.getSerialNumber();
                    } catch (Exception unused) {
                    }
                }
                if (TextUtils.isEmpty(usbDeviceInfo.serial)) {
                    usbDeviceInfo.serial = usbDeviceConnection.getSerial();
                }
                if (usbDeviceInfo.configCounts < 0) {
                    usbDeviceInfo.configCounts = 1;
                }
                byte[] bArr = new byte[256];
                int controlTransfer = usbDeviceConnection.controlTransfer(128, 6, 768, 0, bArr, 256, 0);
                int i = controlTransfer > 0 ? (controlTransfer - 2) / 2 : 0;
                if (i > 0) {
                    if (TextUtils.isEmpty(usbDeviceInfo.manufacturer)) {
                        usbDeviceInfo.manufacturer = UsbUtils.getString(usbDeviceConnection, rawDescriptors[14], i, bArr);
                    }
                    if (TextUtils.isEmpty(usbDeviceInfo.product)) {
                        usbDeviceInfo.product = UsbUtils.getString(usbDeviceConnection, rawDescriptors[15], i, bArr);
                    }
                    if (TextUtils.isEmpty(usbDeviceInfo.serial)) {
                        usbDeviceInfo.serial = UsbUtils.getString(usbDeviceConnection, rawDescriptors[16], i, bArr);
                    }
                }
            }
            if (TextUtils.isEmpty(usbDeviceInfo.manufacturer)) {
                usbDeviceInfo.manufacturer = UsbVendorId.vendorName(usbDevice.getVendorId());
            }
            if (TextUtils.isEmpty(usbDeviceInfo.manufacturer)) {
                usbDeviceInfo.manufacturer = String.format("%04x", Integer.valueOf(usbDevice.getVendorId()));
            }
            if (TextUtils.isEmpty(usbDeviceInfo.product)) {
                usbDeviceInfo.product = String.format("%04x", Integer.valueOf(usbDevice.getProductId()));
            }
        }
        return usbDeviceInfo;
    }

    public UsbDeviceInfo() {
        this.configCounts = -1;
    }

    public UsbDeviceInfo(Parcel parcel) {
        this.device = (UsbDevice) parcel.readParcelable(UsbDevice.class.getClassLoader());
        this.usb_version = parcel.readString();
        this.manufacturer = parcel.readString();
        this.product = parcel.readString();
        this.version = parcel.readString();
        this.serial = parcel.readString();
        this.configCounts = parcel.readInt();
    }

    private void clear() {
        this.device = null;
        this.serial = null;
        this.version = null;
        this.product = null;
        this.manufacturer = null;
        this.usb_version = null;
        this.configCounts = -1;
    }

    public String toString() {
        Object[] objArr = new Object[6];
        String str = this.usb_version;
        if (str == null) {
            str = "";
        }
        objArr[0] = str;
        String str2 = this.manufacturer;
        if (str2 == null) {
            str2 = "";
        }
        objArr[1] = str2;
        String str3 = this.product;
        if (str3 == null) {
            str3 = "";
        }
        objArr[2] = str3;
        String str4 = this.version;
        if (str4 == null) {
            str4 = "";
        }
        objArr[3] = str4;
        String str5 = this.serial;
        if (str5 == null) {
            str5 = "";
        }
        objArr[4] = str5;
        int i = this.configCounts;
        objArr[5] = i >= 0 ? Integer.toString(i) : "";
        return String.format("UsbDeviceInfo:usb_version=%s,manufacturer=%s,product=%s,version=%s,serial=%s,configCounts=%s", objArr);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.device, i);
        parcel.writeString(this.usb_version);
        parcel.writeString(this.manufacturer);
        parcel.writeString(this.product);
        parcel.writeString(this.version);
        parcel.writeString(this.serial);
        parcel.writeInt(this.configCounts);
    }
}
