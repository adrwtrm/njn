package com.epson.iprojection.common.utils;

import android.content.Context;
import android.net.wifi.WifiManager;
import com.epson.iprojection.common.Lg;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/* loaded from: classes.dex */
public final class NetUtils {
    public static String cvtIPAddr(int[] iArr) {
        byte[] bArr = new byte[4];
        for (int i = 0; i < 4; i++) {
            bArr[i] = (byte) iArr[i];
        }
        return cvtIPAddr(bArr);
    }

    public static String cvtIPAddr(byte[] bArr) {
        try {
            String inetAddress = InetAddress.getByAddress(bArr).toString();
            if (inetAddress != null) {
                return inetAddress.substring(1);
            }
            return null;
        } catch (UnknownHostException unused) {
            Lg.e("IPアドレスが取得できませんでした");
            return null;
        }
    }

    public static boolean isWifiOff(Context context) {
        return !((WifiManager) context.getSystemService("wifi")).isWifiEnabled();
    }

    public static boolean isAvailableIPAddress(String str) {
        if (str == null) {
            return false;
        }
        String[] split = str.split("\\.");
        if (split.length != 4) {
            return false;
        }
        int[] iArr = new int[4];
        try {
            iArr[0] = Integer.parseInt(split[0]);
            iArr[1] = Integer.parseInt(split[1]);
            iArr[2] = Integer.parseInt(split[2]);
            iArr[3] = Integer.parseInt(split[3]);
            for (int i = 0; i < 4; i++) {
                int i2 = iArr[i];
                if (i2 < 0 || 255 < i2) {
                    return false;
                }
            }
            int i3 = iArr[0];
            if (i3 == 0 && iArr[1] == 0 && iArr[2] == 0 && iArr[3] == 0) {
                return false;
            }
            return (i3 == 255 && iArr[1] == 255 && iArr[2] == 255 && iArr[3] == 255) ? false : true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public static String getIpStringWithoutPrefZero(String str) {
        if (str.isEmpty() || str == null) {
            Lg.e("IPアドレスが取得できませんでした");
            return null;
        }
        String[] split = str.split("\\.");
        int[] iArr = {Integer.parseInt(split[0], 10) & 255, Integer.parseInt(split[1], 10) & 255, Integer.parseInt(split[2], 10) & 255, Integer.parseInt(split[3], 10) & 255};
        return iArr[0] + "." + iArr[1] + "." + iArr[2] + "." + iArr[3];
    }

    public static byte[] convertIpStringToBytes(String str) {
        if (str.isEmpty() || str == null) {
            Lg.e("IPアドレスが取得できませんでした");
            return null;
        }
        String[] split = str.split("\\.");
        return new byte[]{(byte) (Integer.parseInt(split[0], 10) & 255), (byte) (Integer.parseInt(split[1], 10) & 255), (byte) (Integer.parseInt(split[2], 10) & 255), (byte) (Integer.parseInt(split[3], 10) & 255)};
    }

    public static byte[] convertMacAddrStringToBytes(String str) {
        if (str.isEmpty() || str == null) {
            Lg.d("MACアドレスが取得できませんでした");
            return null;
        }
        byte[] bArr = new byte[6];
        String[] split = str.split(":");
        if (1 == split.length) {
            split = str.split("\\.");
        }
        if (6 != split.length) {
            Lg.d(".または:の数がおかしいです");
            return null;
        }
        for (int i = 0; i < split.length; i++) {
            String str2 = split[i];
            int length = str2.length();
            if (length == 0) {
                Lg.d(".または:の位置がおかしいです");
                return null;
            } else if (length == 1 || length == 2) {
                try {
                    Integer.parseInt(str2, 16);
                    bArr[i] = Hex2Byte(str2);
                } catch (NumberFormatException unused) {
                    Lg.d("数字になっていない箇所があります");
                    return null;
                }
            } else {
                Lg.d("なんか長いです");
                return null;
            }
        }
        return bArr;
    }

    public static String toHexString(byte[] bArr) {
        String str = "";
        for (int i = 0; i < bArr.length; i++) {
            str = str + Integer.toHexString(bArr[i] & 255);
        }
        return str;
    }

    public static void showNetworkInterfacesToLog() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces == null) {
                Lg.e("ネットワークインターフェイスが見つかりませんでした");
                return;
            }
            while (networkInterfaces.hasMoreElements()) {
                Lg.e("NetworkInterface = " + networkInterfaces.nextElement().getName());
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private static byte Hex2Byte(String str) {
        int digit;
        if (str.length() == 2) {
            digit = (Character.digit(str.charAt(0), 16) * 16) + Character.digit(str.charAt(1), 16);
        } else {
            digit = Character.digit(str.charAt(0), 16);
        }
        return (byte) digit;
    }

    private NetUtils() {
    }
}
