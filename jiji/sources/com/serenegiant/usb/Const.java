package com.serenegiant.usb;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public interface Const {
    public static final String ACTION_USB_DEVICE_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    public static final String ACTION_USB_DEVICE_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED";
    public static final int REQ_CODE_GET_CUR = 129;
    public static final int REQ_CODE_GET_DEF = 135;
    public static final int REQ_CODE_GET_INFO = 134;
    public static final int REQ_CODE_GET_LEN = 133;
    public static final int REQ_CODE_GET_MAX = 131;
    public static final int REQ_CODE_GET_MEM = 133;
    public static final int REQ_CODE_GET_MIN = 130;
    public static final int REQ_CODE_GET_RES = 132;
    public static final int REQ_CODE_GET_START = 255;
    public static final int REQ_CODE_SET_CUR = 1;
    public static final int REQ_CODE_SET_DEF = 7;
    public static final int REQ_CODE_SET_INFO = 6;
    public static final int REQ_CODE_SET_LEN = 5;
    public static final int REQ_CODE_SET_MAX = 3;
    public static final int REQ_CODE_SET_MEM = 5;
    public static final int REQ_CODE_SET_MIN = 2;
    public static final int REQ_CODE_SET_RES = 4;
    public static final int REQ_CODE_UNDEFINED = 0;
    public static final int REQ_GET_ = 128;
    public static final int REQ_SET_ = 0;
    public static final int REQ__CUR = 1;
    public static final int REQ__DEF = 7;
    public static final int REQ__INFO = 6;
    public static final int REQ__LEN = 5;
    public static final int REQ__MAX = 3;
    public static final int REQ__MEM = 5;
    public static final int REQ__MIN = 2;
    public static final int REQ__RES = 4;
    public static final int USB_DIR_IN = 128;
    public static final int USB_DIR_OUT = 0;
    public static final int USB_DT_BOS = 15;
    public static final int USB_DT_CONFIG = 2;
    public static final int USB_DT_CS_CONFIG = 34;
    public static final int USB_DT_CS_DEVICE = 33;
    public static final int USB_DT_CS_ENDPOINT = 37;
    public static final int USB_DT_CS_INTERFACE = 36;
    public static final int USB_DT_CS_RADIO_CONTROL = 35;
    public static final int USB_DT_CS_STRING = 35;
    public static final int USB_DT_DEBUG = 10;
    public static final int USB_DT_DEVICE = 1;
    public static final int USB_DT_DEVICE_CAPABILITY = 16;
    public static final int USB_DT_DEVICE_QUALIFIER = 6;
    public static final int USB_DT_DEVICE_SIZE = 18;
    public static final int USB_DT_ENCRYPTION_TYPE = 14;
    public static final int USB_DT_ENDPOINT = 5;
    public static final int USB_DT_INTERFACE = 4;
    public static final int USB_DT_INTERFACE_ASSOCIATION = 11;
    public static final int USB_DT_INTERFACE_POWER = 8;
    public static final int USB_DT_KEY = 13;
    public static final int USB_DT_OTG = 9;
    public static final int USB_DT_OTHER_SPEED_CONFIG = 7;
    public static final int USB_DT_PIPE_USAGE = 36;
    public static final int USB_DT_RPIPE = 34;
    public static final int USB_DT_SECURITY = 12;
    public static final int USB_DT_SS_ENDPOINT_COMP = 48;
    public static final int USB_DT_STRING = 3;
    public static final int USB_DT_WIRELESS_ENDPOINT_COMP = 17;
    public static final int USB_DT_WIRE_ADAPTER = 33;
    public static final int USB_RECIP_DEVICE = 0;
    public static final int USB_RECIP_ENDPOINT = 2;
    public static final int USB_RECIP_INTERFACE = 1;
    public static final int USB_RECIP_MASK = 31;
    public static final int USB_RECIP_OTHER = 3;
    public static final int USB_RECIP_PORT = 4;
    public static final int USB_RECIP_RPIPE = 5;
    public static final int USB_REQ_CLEAR_FEATURE = 1;
    public static final int USB_REQ_CS_DEVICE_GET = 160;
    public static final int USB_REQ_CS_DEVICE_SET = 32;
    public static final int USB_REQ_CS_ENDPOINT_GET = 162;
    public static final int USB_REQ_CS_ENDPOINT_SET = 34;
    public static final int USB_REQ_CS_INTERFACE_GET = 161;
    public static final int USB_REQ_CS_INTERFACE_SET = 33;
    public static final int USB_REQ_GET_CONFIGURATION = 8;
    public static final int USB_REQ_GET_DESCRIPTOR = 6;
    public static final int USB_REQ_GET_ENCRYPTION = 14;
    public static final int USB_REQ_GET_HANDSHAKE = 16;
    public static final int USB_REQ_GET_INTERFACE = 10;
    public static final int USB_REQ_GET_SECURITY_DATA = 19;
    public static final int USB_REQ_GET_STATUS = 0;
    public static final int USB_REQ_LOOPBACK_DATA_READ = 22;
    public static final int USB_REQ_LOOPBACK_DATA_WRITE = 21;
    public static final int USB_REQ_RPIPE_ABORT = 14;
    public static final int USB_REQ_RPIPE_RESET = 15;
    public static final int USB_REQ_SET_ADDRESS = 5;
    public static final int USB_REQ_SET_CONFIGURATION = 9;
    public static final int USB_REQ_SET_CONNECTION = 17;
    public static final int USB_REQ_SET_DESCRIPTOR = 7;
    public static final int USB_REQ_SET_ENCRYPTION = 13;
    public static final int USB_REQ_SET_FEATURE = 3;
    public static final int USB_REQ_SET_HANDSHAKE = 15;
    public static final int USB_REQ_SET_INTERFACE = 11;
    public static final int USB_REQ_SET_INTERFACE_DS = 23;
    public static final int USB_REQ_SET_ISOCH_DELAY = 49;
    public static final int USB_REQ_SET_SECURITY_DATA = 18;
    public static final int USB_REQ_SET_SEL = 48;
    public static final int USB_REQ_SET_WUSB_DATA = 20;
    public static final int USB_REQ_STANDARD_DEVICE_GET = 128;
    public static final int USB_REQ_STANDARD_DEVICE_SET = 0;
    public static final int USB_REQ_STANDARD_ENDPOINT_GET = 130;
    public static final int USB_REQ_STANDARD_ENDPOINT_SET = 2;
    public static final int USB_REQ_STANDARD_INTERFACE_GET = 129;
    public static final int USB_REQ_STANDARD_INTERFACE_SET = 1;
    public static final int USB_REQ_SYNCH_FRAME = 12;
    public static final int USB_REQ_VENDER_DEVICE_GET = 192;
    public static final int USB_REQ_VENDER_DEVICE_SET = 64;
    public static final int USB_REQ_VENDER_ENDPOINT_GET = 194;
    public static final int USB_REQ_VENDER_ENDPOINT_SET = 66;
    public static final int USB_REQ_VENDER_INTERFACE_GET = 193;
    public static final int USB_REQ_VENDER_INTERFACE_SET = 65;
    public static final int USB_TYPE_CLASS = 32;
    public static final int USB_TYPE_MASK = 96;
    public static final int USB_TYPE_RESERVED = 96;
    public static final int USB_TYPE_STANDARD = 0;
    public static final int USB_TYPE_VENDOR = 64;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface RequestCode {
    }
}
