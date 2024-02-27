package com.serenegiant.usb;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.text.TextUtils;
import android.util.Log;
import com.serenegiant.nio.CharsetsUtils;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.system.ContextUtils;
import com.serenegiant.usb.USBMonitor;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes2.dex */
public class UsbUtils implements Const {
    private static final String TAG = "UsbUtils";

    private UsbUtils() {
    }

    public static String getString(UsbDeviceConnection usbDeviceConnection, int i, int i2, byte[] bArr) {
        byte[] bArr2 = new byte[256];
        for (int i3 = 1; i3 <= i2; i3++) {
            int controlTransfer = usbDeviceConnection.controlTransfer(128, 6, i | 768, bArr[i3], bArr2, 256, 0);
            if (controlTransfer > 2 && bArr2[0] == controlTransfer && bArr2[1] == 3) {
                String str = new String(bArr2, 2, controlTransfer - 2, CharsetsUtils.UTF16LE);
                if (!"Љ".equals(str)) {
                    return str;
                }
            }
        }
        return null;
    }

    public static boolean isSupported(UsbDevice usbDevice, int i, int i2, int i3) {
        if ((i < 0 || i == usbDevice.getDeviceClass()) && ((i2 < 0 || i2 == usbDevice.getDeviceSubclass()) && (i3 < 0 || i3 == usbDevice.getDeviceProtocol()))) {
            return true;
        }
        int interfaceCount = usbDevice.getInterfaceCount();
        for (int i4 = 0; i4 < interfaceCount; i4++) {
            UsbInterface usbInterface = usbDevice.getInterface(i4);
            if ((i < 0 || i == usbInterface.getInterfaceClass()) && ((i2 < 0 || i2 == usbInterface.getInterfaceSubclass()) && (i3 < 0 || i3 == usbInterface.getInterfaceProtocol()))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUVC(UsbDevice usbDevice) {
        return isSupported(usbDevice, 14, 1, -1) || isSupported(usbDevice, 14, 2, -1);
    }

    public static boolean isUAC(UsbDevice usbDevice) {
        return isSupported(usbDevice, 1, 1, -1) || isSupported(usbDevice, 1, 2, -1);
    }

    @Deprecated
    public static String getDeviceKeyNameWithSerial(Context context, UsbDevice usbDevice) {
        return getDeviceKeyName(UsbDeviceInfo.getDeviceInfo(context, usbDevice));
    }

    @Deprecated
    public static int getDeviceKeyWithSerial(Context context, UsbDevice usbDevice) {
        return getDeviceKeyNameWithSerial(context, usbDevice).hashCode();
    }

    public static String getDeviceKeyName(USBMonitor.UsbControlBlock usbControlBlock) {
        return getDeviceKeyName(usbControlBlock.getInfo());
    }

    public static int getDeviceKey(USBMonitor.UsbControlBlock usbControlBlock) {
        return getDeviceKeyName(usbControlBlock).hashCode();
    }

    public static String getDeviceKeyName(Context context, UsbDevice usbDevice) {
        return getDeviceKeyName(UsbDeviceInfo.getDeviceInfo(context, usbDevice));
    }

    public static int getDeviceKey(Context context, UsbDevice usbDevice) {
        return getDeviceKeyName(UsbDeviceInfo.getDeviceInfo(context, usbDevice)).hashCode();
    }

    public static String getDeviceKeyName(UsbDeviceInfo usbDeviceInfo) {
        UsbDevice usbDevice = usbDeviceInfo.device;
        if (usbDevice == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(usbDevice.getVendorId()).append("#").append(usbDevice.getProductId()).append("#").append(usbDevice.getDeviceClass()).append("#").append(usbDevice.getDeviceSubclass()).append("#").append(usbDevice.getDeviceProtocol());
        if (!TextUtils.isEmpty(usbDeviceInfo.serial)) {
            sb.append("#");
            sb.append(usbDeviceInfo.serial);
        }
        if (BuildCheck.isAndroid5()) {
            if (!TextUtils.isEmpty(usbDeviceInfo.manufacturer)) {
                sb.append("#").append(usbDeviceInfo.manufacturer);
            }
            if (usbDeviceInfo.configCounts >= 0) {
                sb.append("#").append(usbDeviceInfo.configCounts);
            }
            if (!TextUtils.isEmpty(usbDeviceInfo.version)) {
                sb.append("#").append(usbDeviceInfo.version);
            }
        }
        return sb.toString();
    }

    public static int getDeviceKey(UsbDeviceInfo usbDeviceInfo) {
        return getDeviceKeyName(usbDeviceInfo).hashCode();
    }

    @Deprecated
    public static final String getDeviceKeyName(UsbDevice usbDevice, boolean z, String str, String str2, int i, String str3) {
        if (usbDevice == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(usbDevice.getVendorId()).append("#").append(usbDevice.getProductId()).append("#").append(usbDevice.getDeviceClass()).append("#").append(usbDevice.getDeviceSubclass()).append("#").append(usbDevice.getDeviceProtocol());
        if (!TextUtils.isEmpty(str)) {
            sb.append("#");
            sb.append(str);
        }
        if (z && BuildCheck.isAndroid5()) {
            if (!TextUtils.isEmpty(str2)) {
                sb.append("#").append(str2);
            }
            if (i >= 0) {
                sb.append("#").append(i);
            }
            if (!TextUtils.isEmpty(str3)) {
                sb.append("#").append(str3);
            }
        }
        return sb.toString();
    }

    @Deprecated
    public static final int getDeviceKey(UsbDevice usbDevice) {
        if (usbDevice != null) {
            return getDeviceKeyName(usbDevice, null, false).hashCode();
        }
        return 0;
    }

    @Deprecated
    public static final int getDeviceKey(UsbDevice usbDevice, boolean z) {
        if (usbDevice != null) {
            return getDeviceKeyName(usbDevice, null, z).hashCode();
        }
        return 0;
    }

    @Deprecated
    public static final int getDeviceKey(UsbDevice usbDevice, boolean z, boolean z2) {
        if (usbDevice != null) {
            return getDeviceKeyName(usbDevice, null, z, z2).hashCode();
        }
        return 0;
    }

    @Deprecated
    public static final int getDeviceKey(UsbDevice usbDevice, String str, boolean z) {
        if (usbDevice != null) {
            return getDeviceKeyName(usbDevice, str, z).hashCode();
        }
        return 0;
    }

    @Deprecated
    public static final int getDeviceKey(UsbDevice usbDevice, String str, boolean z, boolean z2) {
        if (usbDevice != null) {
            return getDeviceKeyName(usbDevice, str, z, z2).hashCode();
        }
        return 0;
    }

    @Deprecated
    public static final String getDeviceKeyName(UsbDevice usbDevice) {
        return getDeviceKeyName(usbDevice, null, false);
    }

    @Deprecated
    public static final String getDeviceKeyName(UsbDevice usbDevice, boolean z) {
        return getDeviceKeyName(usbDevice, null, z);
    }

    @Deprecated
    public static final String getDeviceKeyName(UsbDevice usbDevice, String str, boolean z) {
        return getDeviceKeyName(usbDevice, str, z, false);
    }

    @Deprecated
    public static final String getDeviceKeyName(UsbDevice usbDevice, String str, boolean z, boolean z2) {
        if (usbDevice == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(usbDevice.getVendorId()).append("#").append(usbDevice.getProductId()).append("#").append(usbDevice.getDeviceClass()).append("#").append(usbDevice.getDeviceSubclass()).append("#").append(usbDevice.getDeviceProtocol());
        if (!TextUtils.isEmpty(str)) {
            sb.append("#").append(str);
        }
        if (z2) {
            sb.append("#").append(usbDevice.getDeviceName());
        }
        if (z && BuildCheck.isAndroid5()) {
            sb.append("#");
            if (TextUtils.isEmpty(str)) {
                sb.append(usbDevice.getSerialNumber()).append("#");
            }
            sb.append(usbDevice.getManufacturerName()).append("#").append(usbDevice.getConfigurationCount()).append("#");
            if (BuildCheck.isMarshmallow()) {
                sb.append(usbDevice.getVersion()).append("#");
            }
        }
        return sb.toString();
    }

    public static UsbDevice findDevice(List<UsbDevice> list, String str) {
        for (UsbDevice usbDevice : list) {
            if (usbDevice.getDeviceName().equals(str)) {
                return usbDevice;
            }
        }
        return null;
    }

    public static void dumpDevices(Context context) {
        HashMap<String, UsbDevice> deviceList = ((UsbManager) ContextUtils.requireSystemService(context, UsbManager.class)).getDeviceList();
        if (deviceList != null && !deviceList.isEmpty()) {
            Set<String> keySet = deviceList.keySet();
            if (keySet != null && keySet.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (String str : keySet) {
                    UsbDevice usbDevice = deviceList.get(str);
                    int interfaceCount = usbDevice != null ? usbDevice.getInterfaceCount() : 0;
                    sb.setLength(0);
                    for (int i = 0; i < interfaceCount; i++) {
                        sb.append(String.format(Locale.US, "interface%d:%s", Integer.valueOf(i), usbDevice.getInterface(i).toString()));
                    }
                    Log.i(TAG, "key=" + str + ":" + usbDevice + ":" + sb.toString());
                }
                return;
            }
            Log.i(TAG, "no device");
            return;
        }
        Log.i(TAG, "no device");
    }
}
