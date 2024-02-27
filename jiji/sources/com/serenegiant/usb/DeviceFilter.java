package com.serenegiant.usb;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.utils.XmlHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public final class DeviceFilter implements Parcelable {
    public static final Parcelable.Creator<DeviceFilter> CREATOR = new Parcelable.Creator<DeviceFilter>() { // from class: com.serenegiant.usb.DeviceFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceFilter createFromParcel(Parcel parcel) {
            return new DeviceFilter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeviceFilter[] newArray(int i) {
            return new DeviceFilter[i];
        }
    };
    private static final String TAG = "DeviceFilter";
    public final boolean isExclude;
    public final int mClass;
    public final int[] mIntfClass;
    public final int[] mIntfProtocol;
    public final int[] mIntfSubClass;
    public final String mManufacturerName;
    public final int mProductId;
    public final String mProductName;
    public final int mProtocol;
    public final String mSerialNumber;
    public final int mSubclass;
    public final int mVendorId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static List<DeviceFilter> getDeviceFilters(Context context, int i) {
        XmlResourceParser xml = context.getResources().getXml(i);
        ArrayList arrayList = new ArrayList();
        try {
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if (eventType == 2) {
                    DeviceFilter readEntryOne = readEntryOne(context, xml);
                    if (readEntryOne != null) {
                        arrayList.add(readEntryOne);
                    }
                }
            }
        } catch (IOException e) {
            Log.d(TAG, "IOException", e);
        } catch (XmlPullParserException e2) {
            Log.d(TAG, "XmlPullParserException", e2);
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static DeviceFilter readEntryOne(Context context, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int eventType = xmlPullParser.getEventType();
        int i = -1;
        int i2 = -1;
        int i3 = -1;
        int i4 = -1;
        int i5 = -1;
        String str = null;
        String str2 = null;
        String str3 = null;
        int[] iArr = null;
        int[] iArr2 = null;
        int[] iArr3 = null;
        boolean z = false;
        boolean z2 = false;
        while (eventType != 1) {
            if ("usb-device".equalsIgnoreCase(xmlPullParser.getName())) {
                if (eventType == 2) {
                    int attribute = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "vendor-id", -1);
                    if (attribute == -1) {
                        attribute = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "vendorId", -1);
                    }
                    if (attribute == -1) {
                        attribute = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "venderId", -1);
                    }
                    i = attribute;
                    int attribute2 = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "product-id", -1);
                    if (attribute2 == -1) {
                        attribute2 = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "productId", -1);
                    }
                    i2 = attribute2;
                    i3 = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "class", -1);
                    i4 = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "subclass", -1);
                    i5 = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "protocol", -1);
                    String attribute3 = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "manufacturer-name", "");
                    if (TextUtils.isEmpty(attribute3)) {
                        attribute3 = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "manufacture", "");
                    }
                    str = attribute3;
                    String attribute4 = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "product-name", "");
                    if (TextUtils.isEmpty(attribute4)) {
                        attribute4 = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "product", "");
                    }
                    str2 = attribute4;
                    String attribute5 = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "serial-number", "");
                    if (TextUtils.isEmpty(attribute5)) {
                        attribute5 = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "serial", "");
                    }
                    str3 = attribute5;
                    z2 = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "exclude", false);
                    if (!TextUtils.isEmpty(xmlPullParser.getAttributeValue(null, "interfaceClass"))) {
                        int[] attribute6 = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "interfaceClass", new int[0]);
                        iArr = (attribute6 == null || attribute6.length != 0) ? attribute6 : null;
                    }
                    if (!TextUtils.isEmpty(xmlPullParser.getAttributeValue(null, "interfaceSubClass"))) {
                        int[] attribute7 = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "interfaceSubClass", new int[0]);
                        iArr2 = (attribute7 == null || attribute7.length != 0) ? attribute7 : null;
                    }
                    if (!TextUtils.isEmpty(xmlPullParser.getAttributeValue(null, "interfaceProtocol"))) {
                        int[] attribute8 = XmlHelper.getAttribute(context, xmlPullParser, (String) null, "interfaceProtocol", new int[0]);
                        iArr3 = (attribute8 == null || attribute8.length != 0) ? attribute8 : null;
                    }
                    z = true;
                } else if (eventType == 3 && z) {
                    return new DeviceFilter(i, i2, i3, i4, i5, str, str2, str3, iArr, iArr2, iArr3, z2);
                }
            }
            eventType = xmlPullParser.next();
        }
        return null;
    }

    public DeviceFilter(int i, int i2) {
        this(i, i2, -1, -1, -1, null, null, null, null, null, null, false);
    }

    public DeviceFilter(int i, int i2, int i3, int i4, int i5) {
        this(i, i2, i3, i4, i5, null, null, null, null, null, null, false);
    }

    public DeviceFilter(int i, int i2, int i3, int i4, int i5, String str, String str2, String str3) {
        this(i, i2, i3, i4, i5, str, str2, str3, null, null, null, false);
    }

    public DeviceFilter(int i, int i2, int i3, int i4, int i5, String str, String str2, String str3, int[] iArr, int[] iArr2, int[] iArr3, boolean z) {
        this.mVendorId = i;
        this.mProductId = i2;
        this.mClass = i3;
        this.mSubclass = i4;
        this.mProtocol = i5;
        this.mIntfClass = iArr == null ? new int[0] : iArr;
        this.mIntfSubClass = iArr2 == null ? new int[0] : iArr2;
        this.mIntfProtocol = iArr3 == null ? new int[0] : iArr3;
        this.mManufacturerName = TextUtils.isEmpty(str) ? null : str;
        this.mProductName = TextUtils.isEmpty(str2) ? null : str2;
        this.mSerialNumber = TextUtils.isEmpty(str3) ? null : str3;
        this.isExclude = z;
    }

    public DeviceFilter(UsbDevice usbDevice) {
        this(usbDevice, false);
    }

    public DeviceFilter(UsbDevice usbDevice, boolean z) {
        this.mVendorId = usbDevice.getVendorId();
        this.mProductId = usbDevice.getProductId();
        this.mClass = usbDevice.getDeviceClass();
        this.mSubclass = usbDevice.getDeviceSubclass();
        this.mProtocol = usbDevice.getDeviceProtocol();
        int max = Math.max(usbDevice.getInterfaceCount(), 0);
        this.mIntfClass = new int[max];
        this.mIntfSubClass = new int[max];
        this.mIntfProtocol = new int[max];
        for (int i = 0; i < max; i++) {
            UsbInterface usbInterface = usbDevice.getInterface(i);
            this.mIntfClass[i] = usbInterface.getInterfaceClass();
            this.mIntfSubClass[i] = usbInterface.getInterfaceSubclass();
            this.mIntfProtocol[i] = usbInterface.getInterfaceProtocol();
        }
        if (BuildCheck.isLollipop()) {
            this.mManufacturerName = usbDevice.getManufacturerName();
            this.mProductName = usbDevice.getProductName();
            this.mSerialNumber = usbDevice.getSerialNumber();
        } else {
            this.mManufacturerName = null;
            this.mProductName = null;
            this.mSerialNumber = null;
        }
        this.isExclude = z;
    }

    protected DeviceFilter(Parcel parcel) {
        this.mVendorId = parcel.readInt();
        this.mProductId = parcel.readInt();
        this.mClass = parcel.readInt();
        this.mSubclass = parcel.readInt();
        this.mProtocol = parcel.readInt();
        this.mManufacturerName = parcel.readString();
        this.mProductName = parcel.readString();
        this.mSerialNumber = parcel.readString();
        this.mIntfClass = parcel.createIntArray();
        this.mIntfSubClass = parcel.createIntArray();
        this.mIntfProtocol = parcel.createIntArray();
        this.isExclude = parcel.readByte() != 0;
    }

    public boolean matches(UsbDevice usbDevice) {
        if (this.mVendorId == -1 || usbDevice.getVendorId() == this.mVendorId) {
            if (this.mProductId == -1 || usbDevice.getProductId() == this.mProductId) {
                if (matches(usbDevice.getDeviceClass(), usbDevice.getDeviceSubclass(), usbDevice.getDeviceProtocol())) {
                    return true;
                }
                return interfaceMatches(usbDevice);
            }
            return false;
        }
        return false;
    }

    public boolean matches(UsbDeviceInfo usbDeviceInfo) {
        if (usbDeviceInfo == null || usbDeviceInfo.device == null) {
            return false;
        }
        if (this.mManufacturerName == null || usbDeviceInfo.manufacturer == null || this.mManufacturerName.equals(usbDeviceInfo.manufacturer)) {
            if (this.mProductName == null || usbDeviceInfo.product == null || this.mProductName.equals(usbDeviceInfo.product)) {
                if (this.mSerialNumber == null || usbDeviceInfo.serial == null || this.mSerialNumber.equals(usbDeviceInfo.serial)) {
                    return matches(usbDeviceInfo.device);
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public boolean isExclude(UsbDevice usbDevice) {
        return this.isExclude && matches(usbDevice);
    }

    public boolean equals(Object obj) {
        int i;
        int i2;
        int i3;
        int i4;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        int i5 = this.mVendorId;
        if (i5 != -1 && (i = this.mProductId) != -1 && (i2 = this.mClass) != -1 && (i3 = this.mSubclass) != -1 && (i4 = this.mProtocol) != -1) {
            if (obj instanceof DeviceFilter) {
                DeviceFilter deviceFilter = (DeviceFilter) obj;
                if (deviceFilter.mVendorId == i5 && deviceFilter.mProductId == i && deviceFilter.mClass == i2 && deviceFilter.mSubclass == i3 && deviceFilter.mProtocol == i4) {
                    String str8 = deviceFilter.mManufacturerName;
                    if ((str8 == null || this.mManufacturerName != null) && ((str8 != null || this.mManufacturerName == null) && (((str = deviceFilter.mProductName) == null || this.mProductName != null) && ((str != null || this.mProductName == null) && (((str2 = deviceFilter.mSerialNumber) == null || this.mSerialNumber != null) && (str2 != null || this.mSerialNumber == null)))))) {
                        return (str8 == null || (str7 = this.mManufacturerName) == null || str7.equals(str8)) && ((str3 = deviceFilter.mProductName) == null || (str6 = this.mProductName) == null || str6.equals(str3)) && (((str4 = deviceFilter.mSerialNumber) == null || (str5 = this.mSerialNumber) == null || str5.equals(str4)) && deviceFilter.isExclude != this.isExclude);
                    }
                    return false;
                }
                return false;
            } else if (obj instanceof UsbDevice) {
                UsbDevice usbDevice = (UsbDevice) obj;
                if (!this.isExclude && usbDevice.getVendorId() == this.mVendorId && usbDevice.getProductId() == this.mProductId && usbDevice.getDeviceClass() == this.mClass && usbDevice.getDeviceSubclass() == this.mSubclass && usbDevice.getDeviceProtocol() == this.mProtocol) {
                    return true;
                }
            }
        }
        return false;
    }

    public int hashCode() {
        return ((this.mVendorId << 16) | this.mProductId) ^ (((this.mClass << 16) | (this.mSubclass << 8)) | this.mProtocol);
    }

    public String toString() {
        return "DeviceFilter{mVendorId=" + this.mVendorId + ", mProductId=" + this.mProductId + ", mClass=" + this.mClass + ", mSubclass=" + this.mSubclass + ", mProtocol=" + this.mProtocol + ", mManufacturerName='" + this.mManufacturerName + "', mProductName='" + this.mProductName + "', mSerialNumber='" + this.mSerialNumber + "', mIntfClass=" + Arrays.toString(this.mIntfClass) + ", mIntfSubClass=" + Arrays.toString(this.mIntfSubClass) + ", mIntfProtocol=" + Arrays.toString(this.mIntfProtocol) + ", isExclude=" + this.isExclude + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mVendorId);
        parcel.writeInt(this.mProductId);
        parcel.writeInt(this.mClass);
        parcel.writeInt(this.mSubclass);
        parcel.writeInt(this.mProtocol);
        parcel.writeString(this.mManufacturerName);
        parcel.writeString(this.mProductName);
        parcel.writeString(this.mSerialNumber);
        parcel.writeIntArray(this.mIntfClass);
        parcel.writeIntArray(this.mIntfSubClass);
        parcel.writeIntArray(this.mIntfProtocol);
        parcel.writeByte(this.isExclude ? (byte) 1 : (byte) 0);
    }

    private boolean matches(int i, int i2, int i3) {
        int i4;
        int i5;
        int i6 = this.mClass;
        return (i6 == -1 || i == i6) && ((i4 = this.mSubclass) == -1 || i2 == i4) && ((i5 = this.mProtocol) == -1 || i3 == i5);
    }

    private boolean matchesIntfClass(int i) {
        int[] iArr = this.mIntfClass;
        if (iArr.length > 0) {
            for (int i2 : iArr) {
                if (i2 == i) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private boolean matchesIntfSubClass(int i) {
        int[] iArr = this.mIntfSubClass;
        if (iArr.length > 0) {
            for (int i2 : iArr) {
                if (i2 == i) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private boolean matchesIntfProtocol(int i) {
        int[] iArr = this.mIntfProtocol;
        if (iArr.length > 0) {
            for (int i2 : iArr) {
                if (i2 == i) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private boolean interfaceMatches(int i, int i2, int i3) {
        return this.mIntfClass.length > 0 && this.mIntfSubClass.length > 0 && this.mIntfProtocol.length > 0 && matchesIntfClass(i) && matchesIntfSubClass(i2) && matchesIntfProtocol(i3);
    }

    private boolean interfaceMatches(UsbDevice usbDevice) {
        int interfaceCount = usbDevice.getInterfaceCount();
        for (int i = 0; i < interfaceCount; i++) {
            UsbInterface usbInterface = usbDevice.getInterface(i);
            if (matches(usbInterface.getInterfaceClass(), usbInterface.getInterfaceSubclass(), usbInterface.getInterfaceProtocol()) || interfaceMatches(usbInterface.getInterfaceClass(), usbInterface.getInterfaceSubclass(), usbInterface.getInterfaceProtocol())) {
                return true;
            }
        }
        return false;
    }
}
