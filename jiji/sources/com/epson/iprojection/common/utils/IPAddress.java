package com.epson.iprojection.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: IPAddress.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/epson/iprojection/common/utils/IPAddress;", "", "()V", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class IPAddress {
    public static final Companion Companion = new Companion(null);

    @JvmStatic
    public static final String getIpAddressOfAndroidOS() {
        return Companion.getIpAddressOfAndroidOS();
    }

    @JvmStatic
    public static final String getIpAddressOfChromeOS() {
        return Companion.getIpAddressOfChromeOS();
    }

    /* compiled from: IPAddress.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007J\b\u0010\u0005\u001a\u00020\u0004H\u0007J\u0012\u0010\u0006\u001a\u00020\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0002¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/common/utils/IPAddress$Companion;", "", "()V", "getIpAddressOfAndroidOS", "", "getIpAddressOfChromeOS", "readInput", "inputStream", "Ljava/io/InputStream;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final String getIpAddressOfChromeOS() {
            try {
                String readInput = readInput(new ProcessBuilder(new String[0]).command("/system/bin/getprop", "arc.net.ipv4.host_address").start().getInputStream());
                return StringsKt.isBlank(readInput) ? readInput(new ProcessBuilder(new String[0]).command("/system/bin/getprop", "vendor.arc.net.ipv4.host_address").start().getInputStream()) : readInput;
            } catch (Exception unused) {
                return "";
            }
        }

        @JvmStatic
        public final String getIpAddressOfAndroidOS() {
            String str = "none";
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                if (networkInterfaces == null) {
                    return "noneerror" + System.lineSeparator();
                }
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface nextElement = networkInterfaces.nextElement();
                    Enumeration<InetAddress> inetAddresses = nextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement2 = inetAddresses.nextElement();
                        if (nextElement2 instanceof Inet4Address) {
                            String name = nextElement.getName();
                            Intrinsics.checkNotNullExpressionValue(name, "network.name");
                            if (StringsKt.contains$default((CharSequence) name, (CharSequence) "wlan0", false, 2, (Object) null)) {
                                String hostAddress = ((Inet4Address) nextElement2).getHostAddress();
                                Intrinsics.checkNotNullExpressionValue(hostAddress, "address.hostAddress");
                                str = hostAddress;
                            }
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
            return str;
        }

        private final String readInput(InputStream inputStream) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine).append('\n');
                } else {
                    String sb2 = sb.toString();
                    Intrinsics.checkNotNullExpressionValue(sb2, "stringBuilder.toString()");
                    return sb2;
                }
            }
        }
    }
}
