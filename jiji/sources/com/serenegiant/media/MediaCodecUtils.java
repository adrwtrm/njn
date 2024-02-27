package com.serenegiant.media;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.text.TextUtils;
import android.util.Log;
import com.serenegiant.app.PendingIntentCompat;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.utils.BufferHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class MediaCodecUtils {
    public static final int BUFFER_FLAG_KEY_FRAME;
    public static final String MIME_AUDIO_AAC = "audio/mp4a-latm";
    public static final String MIME_VIDEO_AVC = "video/avc";
    public static final int OMX_COLOR_Format12bitRGB444 = 3;
    public static final int OMX_COLOR_Format16bitARGB1555 = 5;
    public static final int OMX_COLOR_Format16bitARGB4444 = 4;
    public static final int OMX_COLOR_Format16bitBGR565 = 7;
    public static final int OMX_COLOR_Format16bitRGB565 = 6;
    public static final int OMX_COLOR_Format18BitBGR666 = 41;
    public static final int OMX_COLOR_Format18bitARGB1665 = 9;
    public static final int OMX_COLOR_Format18bitRGB666 = 8;
    public static final int OMX_COLOR_Format19bitARGB1666 = 10;
    public static final int OMX_COLOR_Format24BitABGR6666 = 43;
    public static final int OMX_COLOR_Format24BitARGB6666 = 42;
    public static final int OMX_COLOR_Format24bitARGB1887 = 13;
    public static final int OMX_COLOR_Format24bitBGR888 = 12;
    public static final int OMX_COLOR_Format24bitRGB888 = 11;
    public static final int OMX_COLOR_Format25bitARGB1888 = 14;
    public static final int OMX_COLOR_Format32bitARGB8888 = 16;
    public static final int OMX_COLOR_Format32bitBGRA8888 = 15;
    public static final int OMX_COLOR_Format8bitRGB332 = 2;
    public static final int OMX_COLOR_FormatAndroidOpaque = 2130708361;
    public static final int OMX_COLOR_FormatCbYCrY = 27;
    public static final int OMX_COLOR_FormatCrYCbY = 28;
    public static final int OMX_COLOR_FormatKhronosExtensions = 1862270976;
    public static final int OMX_COLOR_FormatL16 = 36;
    public static final int OMX_COLOR_FormatL2 = 33;
    public static final int OMX_COLOR_FormatL24 = 37;
    public static final int OMX_COLOR_FormatL32 = 38;
    public static final int OMX_COLOR_FormatL4 = 34;
    public static final int OMX_COLOR_FormatL8 = 35;
    public static final int OMX_COLOR_FormatMax = Integer.MAX_VALUE;
    public static final int OMX_COLOR_FormatMonochrome = 1;
    public static final int OMX_COLOR_FormatRawBayer10bit = 31;
    public static final int OMX_COLOR_FormatRawBayer8bit = 30;
    public static final int OMX_COLOR_FormatRawBayer8bitcompressed = 32;
    public static final int OMX_COLOR_FormatUnused = 0;
    public static final int OMX_COLOR_FormatVendorStartUnused = 2130706432;
    public static final int OMX_COLOR_FormatYCbYCr = 25;
    public static final int OMX_COLOR_FormatYCrYCb = 26;
    public static final int OMX_COLOR_FormatYUV411PackedPlanar = 18;
    public static final int OMX_COLOR_FormatYUV411Planar = 17;
    public static final int OMX_COLOR_FormatYUV420PackedPlanar = 20;
    public static final int OMX_COLOR_FormatYUV420PackedSemiPlanar = 39;
    public static final int OMX_COLOR_FormatYUV420Planar = 19;
    public static final int OMX_COLOR_FormatYUV420SemiPlanar = 21;
    public static final int OMX_COLOR_FormatYUV422PackedPlanar = 23;
    public static final int OMX_COLOR_FormatYUV422PackedSemiPlanar = 40;
    public static final int OMX_COLOR_FormatYUV422Planar = 22;
    public static final int OMX_COLOR_FormatYUV422SemiPlanar = 24;
    public static final int OMX_COLOR_FormatYUV444Interleaved = 29;
    public static final int OMX_QCOM_COLOR_FormatYUV420PackedSemiPlanar32m = 2141391876;
    public static final int OMX_QCOM_COLOR_FormatYUV420PackedSemiPlanar64x32Tile2m8ka = 2141391875;
    public static final int OMX_QCOM_COLOR_FormatYVU420SemiPlanar = 2141391872;
    public static final int OMX_SEC_COLOR_FormatNV12Tiled = 2143289346;
    public static final int OMX_TI_COLOR_FormatYUV420PackedSemiPlanar = 2130706688;
    public static final byte[] START_MARKER;
    private static final String TAG = "MediaCodecUtils";
    public static int[] recognizedFormats;
    private static final HashMap<String, HashMap<MediaCodecInfo, MediaCodecInfo.CodecCapabilities>> sCapabilities;
    private static final List<MediaCodecInfo> sCodecList;

    private MediaCodecUtils() {
    }

    static {
        BuildCheck.isLollipop();
        BUFFER_FLAG_KEY_FRAME = 1;
        START_MARKER = BufferHelper.ANNEXB_START_MARK;
        sCodecList = new ArrayList();
        sCapabilities = new HashMap<>();
        recognizedFormats = new int[]{19, 21, OMX_QCOM_COLOR_FormatYVU420SemiPlanar};
    }

    public static JSONObject get() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("VIDEO", getVideo());
        } catch (Exception e) {
            jSONObject.put("VIDEO", e.getMessage());
        }
        try {
            jSONObject.put("AUDIO", getAudio());
        } catch (Exception e2) {
            jSONObject.put("AUDIO", e2.getMessage());
        }
        return jSONObject;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:27:0x008a
        	at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:81)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:47)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
        */
    private static final org.json.JSONObject getVideo() throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.media.MediaCodecUtils.getVideo():org.json.JSONObject");
    }

    private static final JSONObject getAudio() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        int codecCount = getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = getCodecInfoAt(i);
            JSONObject jSONObject2 = new JSONObject();
            String[] supportedTypes = codecInfoAt.getSupportedTypes();
            int length = supportedTypes.length;
            boolean z = false;
            for (int i2 = 0; i2 < length; i2++) {
                if (supportedTypes[i2].startsWith("audio/")) {
                    jSONObject2.put(Integer.toString(i2), supportedTypes[i2]);
                    z = true;
                }
            }
            if (z) {
                jSONObject.put(codecInfoAt.getName(), jSONObject2);
            }
        }
        return jSONObject;
    }

    public static final String getColorFormatName(int i) {
        switch (i) {
            case 1:
                return "COLOR_FormatMonochrome";
            case 2:
                return "COLOR_Format8bitRGB332";
            case 3:
                return "COLOR_Format12bitRGB444";
            case 4:
                return "COLOR_Format16bitARGB4444";
            case 5:
                return "COLOR_Format16bitARGB1555";
            case 6:
                return "COLOR_Format16bitRGB565";
            case 7:
                return "COLOR_Format16bitBGR565";
            case 8:
                return "COLOR_Format18bitRGB666";
            case 9:
                return "COLOR_Format18bitARGB1665";
            case 10:
                return "COLOR_Format19bitARGB1666";
            case 11:
                return "COLOR_Format24bitRGB888";
            case 12:
                return "COLOR_Format24bitBGR888";
            case 13:
                return "COLOR_Format24bitARGB1887";
            case 14:
                return "COLOR_Format25bitARGB1888";
            case 15:
                return "COLOR_Format32bitBGRA8888";
            case 16:
                return "COLOR_Format32bitARGB8888";
            case 17:
                return "COLOR_FormatYUV411Planar";
            case 18:
                return "COLOR_FormatYUV411PackedPlanar";
            case 19:
                return "COLOR_FormatYUV420Planar";
            case 20:
                return "COLOR_FormatYUV420PackedPlanar";
            case 21:
                return "COLOR_FormatYUV420SemiPlanar";
            case 22:
                return "COLOR_FormatYUV422Planar";
            case 23:
                return "COLOR_FormatYUV422PackedPlanar";
            case 24:
                return "COLOR_FormatYUV422SemiPlanar";
            case 25:
                return "COLOR_FormatYCbYCr";
            case 26:
                return "COLOR_FormatYCrYCb";
            case 27:
                return "COLOR_FormatCbYCrY";
            case 28:
                return "COLOR_FormatCrYCbY";
            case 29:
                return "COLOR_FormatYUV444Interleaved";
            case 30:
                return "COLOR_FormatRawBayer8bit";
            case 31:
                return "COLOR_FormatRawBayer10bit";
            case 32:
                return "COLOR_FormatRawBayer8bitcompressed";
            case 33:
                return "COLOR_FormatL2";
            case 34:
                return "COLOR_FormatL4";
            case 35:
                return "COLOR_FormatL8";
            case 36:
                return "COLOR_FormatL16";
            case 37:
                return "COLOR_FormatL24";
            case 38:
                return "COLOR_FormatL32";
            case 39:
                return "COLOR_FormatYUV420PackedSemiPlanar";
            case 40:
                return "COLOR_FormatYUV422PackedSemiPlanar";
            case 41:
                return "COLOR_Format18BitBGR666";
            case 42:
                return "COLOR_Format24BitARGB6666";
            case 43:
                return "COLOR_Format24BitABGR6666";
            default:
                switch (i) {
                    case OMX_COLOR_FormatKhronosExtensions /* 1862270976 */:
                        return "OMX_COLOR_FormatKhronosExtensions";
                    case OMX_TI_COLOR_FormatYUV420PackedSemiPlanar /* 2130706688 */:
                        return "COLOR_TI_FormatYUV420PackedSemiPlanar";
                    case OMX_COLOR_FormatAndroidOpaque /* 2130708361 */:
                        return "COLOR_FormatSurface_COLOR_FormatAndroidOpaque";
                    case 2135033992:
                        return "COLOR_FormatYUV420Flexible";
                    case OMX_QCOM_COLOR_FormatYVU420SemiPlanar /* 2141391872 */:
                        return "COLOR_QCOM_FormatYUV420SemiPlanar";
                    case OMX_SEC_COLOR_FormatNV12Tiled /* 2143289346 */:
                        return "OMX_SEC_COLOR_FormatNV12Tiled";
                    default:
                        switch (i) {
                            case OMX_QCOM_COLOR_FormatYUV420PackedSemiPlanar64x32Tile2m8ka /* 2141391875 */:
                                return "OMX_QCOM_COLOR_FormatYUV420PackedSemiPlanar64x32Tile2m8ka";
                            case OMX_QCOM_COLOR_FormatYUV420PackedSemiPlanar32m /* 2141391876 */:
                                return "OMX_QCOM_COLOR_FormatYUV420PackedSemiPlanar32m";
                            default:
                                return String.format(Locale.getDefault(), "COLOR_Format_Unknown(%d)", Integer.valueOf(i));
                        }
                }
        }
    }

    public static String getProfileLevelString(String str, MediaCodecInfo.CodecProfileLevel codecProfileLevel) {
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        if (TextUtils.isEmpty(str)) {
            return "mime type is null";
        }
        if (str.equalsIgnoreCase("video/avc")) {
            int i = codecProfileLevel.profile;
            if (i == 1) {
                str6 = "AVCProfileBaseline";
            } else if (i == 2) {
                str6 = "AVCProfileMain";
            } else if (i == 4) {
                str6 = "AVCProfileExtended";
            } else if (i == 8) {
                str6 = "AVCProfileHigh";
            } else if (i == 16) {
                str6 = "AVCProfileHigh10";
            } else if (i != 32) {
                str6 = i != 64 ? "unknown profile " + codecProfileLevel.profile : "AVCProfileHigh444";
            } else {
                str6 = "AVCProfileHigh422";
            }
            int i2 = codecProfileLevel.level;
            if (i2 != 1) {
                if (i2 == 2) {
                    return str6 + ".AVCLevel1b";
                }
                switch (i2) {
                    case 4:
                        return str6 + ".AVCLevel11";
                    case 8:
                        return str6 + ".AVCLevel12";
                    case 16:
                        return str6 + ".AVCLevel13";
                    case 32:
                        return str6 + ".AVCLevel2";
                    case 64:
                        return str6 + ".AVCLevel21";
                    case 128:
                        return str6 + ".AVCLevel22";
                    case 256:
                        return str6 + ".AVCLevel3";
                    case 512:
                        return str6 + ".AVCLevel31";
                    case 1024:
                        return str6 + ".AVCLevel32";
                    case 2048:
                        return str6 + ".AVCLevel4";
                    case 4096:
                        return str6 + ".AVCLevel41";
                    case 8192:
                        return str6 + ".AVCLevel42";
                    case 16384:
                        return str6 + ".AVCLevel5";
                    case 32768:
                        return str6 + ".AVCLevel51";
                    default:
                        return str6 + ".unknown level " + codecProfileLevel.level;
                }
            }
            return str6 + ".AVCLevel1";
        } else if (str.equalsIgnoreCase("video/h263")) {
            int i3 = codecProfileLevel.profile;
            if (i3 == 1) {
                str5 = "H263ProfileBaseline";
            } else if (i3 == 2) {
                str5 = "H263ProfileH320Coding";
            } else if (i3 == 4) {
                str5 = "H263ProfileBackwardCompatible";
            } else if (i3 == 8) {
                str5 = "H263ProfileISWV2";
            } else if (i3 == 16) {
                str5 = "H263ProfileISWV3";
            } else if (i3 == 32) {
                str5 = "H263ProfileHighCompression";
            } else if (i3 == 64) {
                str5 = "H263ProfileInternet";
            } else if (i3 != 128) {
                str5 = i3 != 256 ? "unknown profile " + codecProfileLevel.profile : "H263ProfileHighLatency";
            } else {
                str5 = "H263ProfileInterlace";
            }
            int i4 = codecProfileLevel.level;
            if (i4 != 1) {
                if (i4 != 2) {
                    if (i4 != 4) {
                        if (i4 != 8) {
                            if (i4 != 16) {
                                if (i4 != 32) {
                                    if (i4 != 64) {
                                        if (i4 == 128) {
                                            return str5 + ".H263Level70";
                                        }
                                        return str5 + ".unknown level " + codecProfileLevel.level;
                                    }
                                    return str5 + ".H263Level60";
                                }
                                return str5 + ".H263Level50";
                            }
                            return str5 + ".H263Level45";
                        }
                        return str5 + ".H263Level40";
                    }
                    return str5 + ".H263Level30";
                }
                return str5 + ".H263Level20";
            }
            return str5 + ".H263Level10";
        } else if (str.equalsIgnoreCase("video/mpeg4")) {
            int i5 = codecProfileLevel.profile;
            if (i5 == 1) {
                str4 = "MPEG4ProfileSimple";
            } else if (i5 != 2) {
                switch (i5) {
                    case 4:
                        str4 = "MPEG4ProfileCore";
                        break;
                    case 8:
                        str4 = "MPEG4ProfileMain";
                        break;
                    case 16:
                        str4 = "MPEG4ProfileNbit";
                        break;
                    case 32:
                        str4 = "MPEG4ProfileScalableTexture";
                        break;
                    case 64:
                        str4 = "MPEG4ProfileSimpleFace";
                        break;
                    case 128:
                        str4 = "MPEG4ProfileSimpleFBA";
                        break;
                    case 256:
                        str4 = "MPEG4ProfileBasicAnimated";
                        break;
                    case 512:
                        str4 = "MPEG4ProfileHybrid";
                        break;
                    case 1024:
                        str4 = "MPEG4ProfileAdvancedRealTime";
                        break;
                    case 2048:
                        str4 = "MPEG4ProfileCoreScalable";
                        break;
                    case 4096:
                        str4 = "MPEG4ProfileAdvancedCoding";
                        break;
                    case 8192:
                        str4 = "MPEG4ProfileAdvancedCore";
                        break;
                    case 16384:
                        str4 = "MPEG4ProfileAdvancedScalable";
                        break;
                    case 32768:
                        str4 = "MPEG4ProfileAdvancedSimple";
                        break;
                    default:
                        str4 = "unknown profile " + codecProfileLevel.profile;
                        break;
                }
            } else {
                str4 = "MPEG4ProfileSimpleScalable";
            }
            int i6 = codecProfileLevel.level;
            if (i6 != 1) {
                if (i6 != 2) {
                    if (i6 != 4) {
                        if (i6 != 8) {
                            if (i6 != 16) {
                                if (i6 != 32) {
                                    if (i6 != 64) {
                                        if (i6 == 128) {
                                            return str4 + ".MPEG4Level5";
                                        }
                                        return str4 + ".unknown level " + codecProfileLevel.level;
                                    }
                                    return str4 + ".MPEG4Level4a";
                                }
                                return str4 + ".MPEG4Level4";
                            }
                            return str4 + ".MPEG4Level3";
                        }
                        return str4 + ".MPEG4Level2";
                    }
                    return str4 + ".MPEG4Level1";
                }
                return str4 + ".MPEG4Level0b";
            }
            return str4 + ".MPEG4Level0";
        } else if (str.equalsIgnoreCase("ausio/aac")) {
            int i7 = codecProfileLevel.level;
            if (i7 != 17) {
                if (i7 != 23) {
                    if (i7 != 29) {
                        if (i7 != 39) {
                            if (i7 != 42) {
                                switch (i7) {
                                    case 1:
                                        return "AACObjectMain";
                                    case 2:
                                        return "AACObjectLC";
                                    case 3:
                                        return "AACObjectSSR";
                                    case 4:
                                        return "AACObjectLTP";
                                    case 5:
                                        return "AACObjectHE";
                                    case 6:
                                        return "AACObjectScalable";
                                    default:
                                        return "profile:unknown " + codecProfileLevel.profile;
                                }
                            }
                            return "AACObjectXHE";
                        }
                        return "AACObjectELD";
                    }
                    return "AACObjectHE_PS";
                }
                return "AACObjectLD";
            }
            return "AACObjectERLC";
        } else if (str.equalsIgnoreCase("video/vp8")) {
            String str7 = codecProfileLevel.profile != 1 ? "unknown profile " + codecProfileLevel.profile : "VP8ProfileMain";
            int i8 = codecProfileLevel.level;
            if (i8 != 1) {
                if (i8 != 2) {
                    if (i8 != 4) {
                        if (i8 == 8) {
                            return str7 + ".VP8Level_Version3";
                        }
                        return str7 + ".unknown level" + codecProfileLevel.level;
                    }
                    return str7 + ".VP8Level_Version2";
                }
                return str7 + ".VP8Level_Version1";
            }
            return str7 + ".VP8Level_Version0";
        } else if (str.equalsIgnoreCase("video/vp9")) {
            int i9 = codecProfileLevel.profile;
            if (i9 == 1) {
                str3 = "VP9Profile0";
            } else if (i9 == 2) {
                str3 = "VP9Profile1";
            } else if (i9 == 4) {
                str3 = "VP9Profile2";
            } else if (i9 == 8) {
                str3 = "VP9Profile3";
            } else if (i9 == 4096) {
                str3 = "VP9Profile2HDR";
            } else if (i9 == 8192) {
                str3 = "VP9Profile3HDR";
            } else if (i9 != 16384) {
                str3 = i9 != 32768 ? "unknown profile " + codecProfileLevel.profile : "VP9Profile3HDR10Plus";
            } else {
                str3 = "VP9Profile2HDR10Plus";
            }
            int i10 = codecProfileLevel.level;
            if (i10 != 1) {
                if (i10 == 2) {
                    return str3 + ".VP9Level11";
                }
                switch (i10) {
                    case 4:
                        return str3 + ".VP9Level2";
                    case 8:
                        return str3 + ".VP9Level21";
                    case 16:
                        return str3 + ".VP9Level3";
                    case 32:
                        return str3 + ".VP9Level31";
                    case 64:
                        return str3 + ".VP9Level4";
                    case 128:
                        return str3 + ".VP9Level41";
                    case 256:
                        return str3 + ".VP9Level5";
                    case 512:
                        return str3 + ".VP9Level51";
                    case 1024:
                        return str3 + ".VP9Level52";
                    case 2048:
                        return str3 + ".VP9Level6";
                    case 4096:
                        return str3 + ".VP9Level61";
                    case 8192:
                        return str3 + ".VP9Level62";
                    default:
                        return str3 + ".unknown level" + codecProfileLevel.level;
                }
            }
            return str3 + ".VP9Level1";
        } else if (str.equalsIgnoreCase("video/hevc")) {
            int i11 = codecProfileLevel.profile;
            if (i11 == 1) {
                str2 = "HEVCProfileMain";
            } else if (i11 == 2) {
                str2 = "HEVCProfileMain10";
            } else if (i11 == 4) {
                str2 = "HEVCProfileMainStill";
            } else if (i11 != 4096) {
                str2 = i11 != 8192 ? "unknown profile " + codecProfileLevel.profile : "HEVCProfileMain10HDR10Plus";
            } else {
                str2 = "HEVCProfileMain10HDR10";
            }
            int i12 = codecProfileLevel.level;
            if (i12 != 1) {
                if (i12 == 2) {
                    return str2 + ".HEVCHighTierLevel1";
                }
                switch (i12) {
                    case 4:
                        return str2 + ".HEVCMainTierLevel2";
                    case 8:
                        return str2 + ".HEVCHighTierLevel2";
                    case 16:
                        return str2 + ".HEVCMainTierLevel21";
                    case 32:
                        return str2 + ".HEVCHighTierLevel21";
                    case 64:
                        return str2 + ".HEVCMainTierLevel3";
                    case 128:
                        return str2 + ".HEVCHighTierLevel3";
                    case 256:
                        return str2 + ".HEVCMainTierLevel31";
                    case 512:
                        return str2 + ".HEVCHighTierLevel31";
                    case 1024:
                        return str2 + ".HEVCMainTierLevel4";
                    case 2048:
                        return str2 + ".HEVCHighTierLevel4";
                    case 4096:
                        return str2 + ".HEVCMainTierLevel41";
                    case 8192:
                        return str2 + ".HEVCHighTierLevel41";
                    case 16384:
                        return str2 + ".HEVCMainTierLevel5";
                    case 32768:
                        return str2 + ".HEVCHighTierLevel5";
                    case 65536:
                        return str2 + ".HEVCMainTierLevel51";
                    case 131072:
                        return str2 + ".HEVCHighTierLevel51";
                    case 262144:
                        return str2 + ".HEVCMainTierLevel52";
                    case 524288:
                        return str2 + ".HEVCHighTierLevel52";
                    case 1048576:
                        return str2 + ".HEVCMainTierLevel6";
                    case 2097152:
                        return str2 + ".HEVCHighTierLevel6";
                    case 4194304:
                        return str2 + ".HEVCMainTierLevel61";
                    case 8388608:
                        return str2 + ".HEVCHighTierLevel61";
                    case 16777216:
                        return str2 + ".HEVCMainTierLevel62";
                    case PendingIntentCompat.FLAG_MUTABLE /* 33554432 */:
                        return str2 + ".HEVCHighTierLevel62";
                    default:
                        return str2 + ".unknown level" + codecProfileLevel.level;
                }
            }
            return str2 + ".HEVCMainTierLevel1";
        } else {
            return "unknown profile " + codecProfileLevel.profile;
        }
    }

    private static final void updateCodecs() {
        if (sCodecList.size() == 0) {
            int codecCount = MediaCodecList.getCodecCount();
            for (int i = 0; i < codecCount; i++) {
                sCodecList.add(MediaCodecList.getCodecInfoAt(i));
            }
        }
    }

    public static final int getCodecCount() {
        updateCodecs();
        return sCodecList.size();
    }

    public static final List<MediaCodecInfo> getCodecs() {
        updateCodecs();
        return sCodecList;
    }

    public static final MediaCodecInfo getCodecInfoAt(int i) {
        updateCodecs();
        return sCodecList.get(i);
    }

    public static MediaCodecInfo.CodecCapabilities getCodecCapabilities(MediaCodecInfo mediaCodecInfo, String str) {
        HashMap<String, HashMap<MediaCodecInfo, MediaCodecInfo.CodecCapabilities>> hashMap = sCapabilities;
        HashMap<MediaCodecInfo, MediaCodecInfo.CodecCapabilities> hashMap2 = hashMap.get(str);
        if (hashMap2 == null) {
            hashMap2 = new HashMap<>();
            hashMap.put(str, hashMap2);
        }
        MediaCodecInfo.CodecCapabilities codecCapabilities = hashMap2.get(mediaCodecInfo);
        if (codecCapabilities == null) {
            Thread.currentThread().setPriority(10);
            try {
                MediaCodecInfo.CodecCapabilities capabilitiesForType = mediaCodecInfo.getCapabilitiesForType(str);
                hashMap2.put(mediaCodecInfo, capabilitiesForType);
                return capabilitiesForType;
            } finally {
                Thread.currentThread().setPriority(5);
            }
        }
        return codecCapabilities;
    }

    public static MediaCodecInfo selectVideoEncoder(String str) {
        int codecCount = getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                for (String str2 : codecInfoAt.getSupportedTypes()) {
                    if (str2.equalsIgnoreCase(str) && selectColorFormat(codecInfoAt, str) > 0) {
                        return codecInfoAt;
                    }
                }
                continue;
            }
        }
        return null;
    }

    public static List<MediaCodecInfo> getVideoEncoderInfos(String str) {
        ArrayList arrayList = new ArrayList();
        int codecCount = getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                for (String str2 : codecInfoAt.getSupportedTypes()) {
                    if (str2.equalsIgnoreCase(str) && selectColorFormat(codecInfoAt, str) > 0) {
                        arrayList.add(codecInfoAt);
                    }
                }
            }
        }
        return arrayList;
    }

    public static final boolean isRecognizedVideoFormat(int i) {
        int[] iArr = recognizedFormats;
        int length = iArr != null ? iArr.length : 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (recognizedFormats[i2] == i) {
                return true;
            }
        }
        return false;
    }

    public static final int selectColorFormat(MediaCodecInfo mediaCodecInfo, String str) {
        int[] iArr;
        for (int i : getCodecCapabilities(mediaCodecInfo, str).colorFormats) {
            if (isRecognizedVideoFormat(i)) {
                return i;
            }
        }
        return 0;
    }

    public static final void dumpEncoders() {
        int codecCount = getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                String[] supportedTypes = codecInfoAt.getSupportedTypes();
                for (int i2 = 0; i2 < supportedTypes.length; i2++) {
                    Log.i(TAG, "codec:" + codecInfoAt.getName() + ",MIME:" + supportedTypes[i2]);
                    selectColorFormat(codecInfoAt, supportedTypes[i2]);
                }
            }
        }
    }

    public static final void dumpDecoders() {
        int codecCount = getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = getCodecInfoAt(i);
            if (!codecInfoAt.isEncoder()) {
                String[] supportedTypes = codecInfoAt.getSupportedTypes();
                for (int i2 = 0; i2 < supportedTypes.length; i2++) {
                    Log.i(TAG, "codec:" + codecInfoAt.getName() + ",MIME:" + supportedTypes[i2]);
                    selectColorFormat(codecInfoAt, supportedTypes[i2]);
                }
            }
        }
    }

    public static final boolean isSemiPlanarYUV(int i) {
        if (i == 39 || i == 2130706688 || i == 2141391872) {
            return true;
        }
        switch (i) {
            case 19:
            case 20:
                return false;
            case 21:
                return true;
            default:
                throw new RuntimeException("unknown format " + i);
        }
    }

    public static final MediaCodecInfo selectAudioEncoder(String str) {
        int codecCount = getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                for (String str2 : codecInfoAt.getSupportedTypes()) {
                    if (str2.equalsIgnoreCase(str)) {
                        return codecInfoAt;
                    }
                }
                continue;
            }
        }
        return null;
    }

    public static List<MediaCodecInfo> getAudioEncoderInfos(String str) {
        ArrayList arrayList = new ArrayList();
        int codecCount = getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                for (String str2 : codecInfoAt.getSupportedTypes()) {
                    if (str2.equalsIgnoreCase(str)) {
                        arrayList.add(codecInfoAt);
                    }
                }
            }
        }
        return arrayList;
    }

    public static boolean checkProfileLevel(String str, MediaCodecInfo mediaCodecInfo) {
        if (mediaCodecInfo == null || !str.equalsIgnoreCase("video/avc")) {
            return true;
        }
        for (MediaCodecInfo.CodecProfileLevel codecProfileLevel : getCodecCapabilities(mediaCodecInfo, str).profileLevels) {
            if (codecProfileLevel.level >= 16384) {
                return false;
            }
        }
        return true;
    }

    public static final int findStartMarker(byte[] bArr, int i) {
        byte[] bArr2 = START_MARKER;
        return BufferHelper.byteComp(bArr, i, bArr2, bArr2.length);
    }
}
