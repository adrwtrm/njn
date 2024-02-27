package com.epson.iprojection.common.utils;

import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class DefLoader {
    public static final String CHECKED = "Checked";
    public static final String SETTING_AUDIO_TRANSFER = "AudioTransfer";
    public static final String SETTING_AUTO_DISP = "AutoDisplayDelivery";
    public static final String SETTING_BAND_WIDTH = "BandWidth";
    public static final String SETTING_CLIENTMODE = "ClientMode";
    public static final String SETTING_ENCRYPT = "Encrypt";
    public static final String SETTING_ROOT = "Configuration";
    public static final String SETTING_SP_PATH = "SharedProfilePath";
    public static final String SETTING_USERNAME = "UserName";
    public static final String SETTING_WEB_BOOKMARK = "WebBookmark";
    public static final String SETTING_WEB_STARTPAGE = "WebStartPage";
    public static final String SETTING_WEB_TITLE = "Title";
    public static final String SETTING_WEB_URL = "Url";
    public static final String UNCHECKED = "Unchecked";
    private static final String[] _startpageList = {PrefTagDefine.WEB_STARTPAGE_TITLE_TAG, PrefTagDefine.WEB_STARTPAGE_URL_TAG};
    private static final String[] _bookmarkList = {PrefTagDefine.WEB_BOOKMARK_TITLE_TAG, PrefTagDefine.WEB_BOOKMARK_URL_TAG};

    /* JADX WARN: Removed duplicated region for block: B:102:0x01d8 A[Catch: IOException -> 0x04da, XmlPullParserException -> 0x04e9, all -> 0x0505, TryCatch #4 {IOException -> 0x04da, XmlPullParserException -> 0x04e9, blocks: (B:11:0x0040, B:13:0x005f, B:15:0x0066, B:21:0x008e, B:24:0x0096, B:26:0x00a1, B:29:0x00a8, B:30:0x00b4, B:32:0x00ba, B:35:0x00c2, B:37:0x00cd, B:40:0x00d4, B:41:0x00e2, B:45:0x00fd, B:47:0x0104, B:60:0x013b, B:63:0x0144, B:66:0x014d, B:68:0x0155, B:70:0x015d, B:72:0x0167, B:74:0x016c, B:76:0x0174, B:78:0x017c, B:80:0x0183, B:82:0x018d, B:84:0x0195, B:86:0x019c, B:88:0x01a6, B:90:0x01ae, B:93:0x01b7, B:95:0x01bf, B:97:0x01c7, B:100:0x01d0, B:102:0x01d8, B:104:0x01e0, B:107:0x01e9, B:109:0x01f1, B:111:0x01f9, B:114:0x0202, B:116:0x020a, B:118:0x0210, B:120:0x021a, B:122:0x0220, B:123:0x0225, B:125:0x022b, B:127:0x0231, B:54:0x0121, B:56:0x0127, B:131:0x025e, B:133:0x0272, B:135:0x028b, B:137:0x0291, B:138:0x0296, B:141:0x02a0, B:145:0x02a9, B:146:0x02ad, B:148:0x02b3, B:149:0x02b7, B:151:0x02be, B:152:0x02c2, B:185:0x0328, B:186:0x0332, B:187:0x033c, B:188:0x0346, B:189:0x0350, B:190:0x035a, B:191:0x0364, B:192:0x036e, B:193:0x0378, B:194:0x0381, B:196:0x0387, B:199:0x038f, B:201:0x0399, B:203:0x03ae, B:204:0x03b6, B:205:0x03be, B:154:0x02c6, B:157:0x02d0, B:160:0x02da, B:163:0x02e4, B:166:0x02ee, B:169:0x02f8, B:172:0x0302, B:175:0x030c, B:178:0x0317, B:206:0x03d5, B:208:0x03db, B:212:0x03e4, B:216:0x03f2, B:218:0x03fc, B:220:0x040a, B:222:0x0412, B:223:0x041a, B:224:0x0422, B:226:0x042a, B:227:0x0432, B:213:0x03e8, B:215:0x03ee, B:228:0x0439, B:230:0x043f, B:234:0x0448, B:235:0x044c, B:237:0x0452, B:238:0x0456, B:240:0x045c, B:244:0x0465, B:245:0x046f, B:247:0x0475, B:248:0x0479, B:250:0x047f, B:254:0x0496, B:255:0x04a6, B:258:0x04ae, B:265:0x04be, B:268:0x04cd, B:267:0x04c8, B:269:0x04d0), top: B:281:0x0040, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x01f1 A[Catch: IOException -> 0x04da, XmlPullParserException -> 0x04e9, all -> 0x0505, TryCatch #4 {IOException -> 0x04da, XmlPullParserException -> 0x04e9, blocks: (B:11:0x0040, B:13:0x005f, B:15:0x0066, B:21:0x008e, B:24:0x0096, B:26:0x00a1, B:29:0x00a8, B:30:0x00b4, B:32:0x00ba, B:35:0x00c2, B:37:0x00cd, B:40:0x00d4, B:41:0x00e2, B:45:0x00fd, B:47:0x0104, B:60:0x013b, B:63:0x0144, B:66:0x014d, B:68:0x0155, B:70:0x015d, B:72:0x0167, B:74:0x016c, B:76:0x0174, B:78:0x017c, B:80:0x0183, B:82:0x018d, B:84:0x0195, B:86:0x019c, B:88:0x01a6, B:90:0x01ae, B:93:0x01b7, B:95:0x01bf, B:97:0x01c7, B:100:0x01d0, B:102:0x01d8, B:104:0x01e0, B:107:0x01e9, B:109:0x01f1, B:111:0x01f9, B:114:0x0202, B:116:0x020a, B:118:0x0210, B:120:0x021a, B:122:0x0220, B:123:0x0225, B:125:0x022b, B:127:0x0231, B:54:0x0121, B:56:0x0127, B:131:0x025e, B:133:0x0272, B:135:0x028b, B:137:0x0291, B:138:0x0296, B:141:0x02a0, B:145:0x02a9, B:146:0x02ad, B:148:0x02b3, B:149:0x02b7, B:151:0x02be, B:152:0x02c2, B:185:0x0328, B:186:0x0332, B:187:0x033c, B:188:0x0346, B:189:0x0350, B:190:0x035a, B:191:0x0364, B:192:0x036e, B:193:0x0378, B:194:0x0381, B:196:0x0387, B:199:0x038f, B:201:0x0399, B:203:0x03ae, B:204:0x03b6, B:205:0x03be, B:154:0x02c6, B:157:0x02d0, B:160:0x02da, B:163:0x02e4, B:166:0x02ee, B:169:0x02f8, B:172:0x0302, B:175:0x030c, B:178:0x0317, B:206:0x03d5, B:208:0x03db, B:212:0x03e4, B:216:0x03f2, B:218:0x03fc, B:220:0x040a, B:222:0x0412, B:223:0x041a, B:224:0x0422, B:226:0x042a, B:227:0x0432, B:213:0x03e8, B:215:0x03ee, B:228:0x0439, B:230:0x043f, B:234:0x0448, B:235:0x044c, B:237:0x0452, B:238:0x0456, B:240:0x045c, B:244:0x0465, B:245:0x046f, B:247:0x0475, B:248:0x0479, B:250:0x047f, B:254:0x0496, B:255:0x04a6, B:258:0x04ae, B:265:0x04be, B:268:0x04cd, B:267:0x04c8, B:269:0x04d0), top: B:281:0x0040, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x020a A[Catch: IOException -> 0x04da, XmlPullParserException -> 0x04e9, all -> 0x0505, TryCatch #4 {IOException -> 0x04da, XmlPullParserException -> 0x04e9, blocks: (B:11:0x0040, B:13:0x005f, B:15:0x0066, B:21:0x008e, B:24:0x0096, B:26:0x00a1, B:29:0x00a8, B:30:0x00b4, B:32:0x00ba, B:35:0x00c2, B:37:0x00cd, B:40:0x00d4, B:41:0x00e2, B:45:0x00fd, B:47:0x0104, B:60:0x013b, B:63:0x0144, B:66:0x014d, B:68:0x0155, B:70:0x015d, B:72:0x0167, B:74:0x016c, B:76:0x0174, B:78:0x017c, B:80:0x0183, B:82:0x018d, B:84:0x0195, B:86:0x019c, B:88:0x01a6, B:90:0x01ae, B:93:0x01b7, B:95:0x01bf, B:97:0x01c7, B:100:0x01d0, B:102:0x01d8, B:104:0x01e0, B:107:0x01e9, B:109:0x01f1, B:111:0x01f9, B:114:0x0202, B:116:0x020a, B:118:0x0210, B:120:0x021a, B:122:0x0220, B:123:0x0225, B:125:0x022b, B:127:0x0231, B:54:0x0121, B:56:0x0127, B:131:0x025e, B:133:0x0272, B:135:0x028b, B:137:0x0291, B:138:0x0296, B:141:0x02a0, B:145:0x02a9, B:146:0x02ad, B:148:0x02b3, B:149:0x02b7, B:151:0x02be, B:152:0x02c2, B:185:0x0328, B:186:0x0332, B:187:0x033c, B:188:0x0346, B:189:0x0350, B:190:0x035a, B:191:0x0364, B:192:0x036e, B:193:0x0378, B:194:0x0381, B:196:0x0387, B:199:0x038f, B:201:0x0399, B:203:0x03ae, B:204:0x03b6, B:205:0x03be, B:154:0x02c6, B:157:0x02d0, B:160:0x02da, B:163:0x02e4, B:166:0x02ee, B:169:0x02f8, B:172:0x0302, B:175:0x030c, B:178:0x0317, B:206:0x03d5, B:208:0x03db, B:212:0x03e4, B:216:0x03f2, B:218:0x03fc, B:220:0x040a, B:222:0x0412, B:223:0x041a, B:224:0x0422, B:226:0x042a, B:227:0x0432, B:213:0x03e8, B:215:0x03ee, B:228:0x0439, B:230:0x043f, B:234:0x0448, B:235:0x044c, B:237:0x0452, B:238:0x0456, B:240:0x045c, B:244:0x0465, B:245:0x046f, B:247:0x0475, B:248:0x0479, B:250:0x047f, B:254:0x0496, B:255:0x04a6, B:258:0x04ae, B:265:0x04be, B:268:0x04cd, B:267:0x04c8, B:269:0x04d0), top: B:281:0x0040, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x021a A[Catch: IOException -> 0x04da, XmlPullParserException -> 0x04e9, all -> 0x0505, TryCatch #4 {IOException -> 0x04da, XmlPullParserException -> 0x04e9, blocks: (B:11:0x0040, B:13:0x005f, B:15:0x0066, B:21:0x008e, B:24:0x0096, B:26:0x00a1, B:29:0x00a8, B:30:0x00b4, B:32:0x00ba, B:35:0x00c2, B:37:0x00cd, B:40:0x00d4, B:41:0x00e2, B:45:0x00fd, B:47:0x0104, B:60:0x013b, B:63:0x0144, B:66:0x014d, B:68:0x0155, B:70:0x015d, B:72:0x0167, B:74:0x016c, B:76:0x0174, B:78:0x017c, B:80:0x0183, B:82:0x018d, B:84:0x0195, B:86:0x019c, B:88:0x01a6, B:90:0x01ae, B:93:0x01b7, B:95:0x01bf, B:97:0x01c7, B:100:0x01d0, B:102:0x01d8, B:104:0x01e0, B:107:0x01e9, B:109:0x01f1, B:111:0x01f9, B:114:0x0202, B:116:0x020a, B:118:0x0210, B:120:0x021a, B:122:0x0220, B:123:0x0225, B:125:0x022b, B:127:0x0231, B:54:0x0121, B:56:0x0127, B:131:0x025e, B:133:0x0272, B:135:0x028b, B:137:0x0291, B:138:0x0296, B:141:0x02a0, B:145:0x02a9, B:146:0x02ad, B:148:0x02b3, B:149:0x02b7, B:151:0x02be, B:152:0x02c2, B:185:0x0328, B:186:0x0332, B:187:0x033c, B:188:0x0346, B:189:0x0350, B:190:0x035a, B:191:0x0364, B:192:0x036e, B:193:0x0378, B:194:0x0381, B:196:0x0387, B:199:0x038f, B:201:0x0399, B:203:0x03ae, B:204:0x03b6, B:205:0x03be, B:154:0x02c6, B:157:0x02d0, B:160:0x02da, B:163:0x02e4, B:166:0x02ee, B:169:0x02f8, B:172:0x0302, B:175:0x030c, B:178:0x0317, B:206:0x03d5, B:208:0x03db, B:212:0x03e4, B:216:0x03f2, B:218:0x03fc, B:220:0x040a, B:222:0x0412, B:223:0x041a, B:224:0x0422, B:226:0x042a, B:227:0x0432, B:213:0x03e8, B:215:0x03ee, B:228:0x0439, B:230:0x043f, B:234:0x0448, B:235:0x044c, B:237:0x0452, B:238:0x0456, B:240:0x045c, B:244:0x0465, B:245:0x046f, B:247:0x0475, B:248:0x0479, B:250:0x047f, B:254:0x0496, B:255:0x04a6, B:258:0x04ae, B:265:0x04be, B:268:0x04cd, B:267:0x04c8, B:269:0x04d0), top: B:281:0x0040, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x022b A[Catch: IOException -> 0x04da, XmlPullParserException -> 0x04e9, all -> 0x0505, TryCatch #4 {IOException -> 0x04da, XmlPullParserException -> 0x04e9, blocks: (B:11:0x0040, B:13:0x005f, B:15:0x0066, B:21:0x008e, B:24:0x0096, B:26:0x00a1, B:29:0x00a8, B:30:0x00b4, B:32:0x00ba, B:35:0x00c2, B:37:0x00cd, B:40:0x00d4, B:41:0x00e2, B:45:0x00fd, B:47:0x0104, B:60:0x013b, B:63:0x0144, B:66:0x014d, B:68:0x0155, B:70:0x015d, B:72:0x0167, B:74:0x016c, B:76:0x0174, B:78:0x017c, B:80:0x0183, B:82:0x018d, B:84:0x0195, B:86:0x019c, B:88:0x01a6, B:90:0x01ae, B:93:0x01b7, B:95:0x01bf, B:97:0x01c7, B:100:0x01d0, B:102:0x01d8, B:104:0x01e0, B:107:0x01e9, B:109:0x01f1, B:111:0x01f9, B:114:0x0202, B:116:0x020a, B:118:0x0210, B:120:0x021a, B:122:0x0220, B:123:0x0225, B:125:0x022b, B:127:0x0231, B:54:0x0121, B:56:0x0127, B:131:0x025e, B:133:0x0272, B:135:0x028b, B:137:0x0291, B:138:0x0296, B:141:0x02a0, B:145:0x02a9, B:146:0x02ad, B:148:0x02b3, B:149:0x02b7, B:151:0x02be, B:152:0x02c2, B:185:0x0328, B:186:0x0332, B:187:0x033c, B:188:0x0346, B:189:0x0350, B:190:0x035a, B:191:0x0364, B:192:0x036e, B:193:0x0378, B:194:0x0381, B:196:0x0387, B:199:0x038f, B:201:0x0399, B:203:0x03ae, B:204:0x03b6, B:205:0x03be, B:154:0x02c6, B:157:0x02d0, B:160:0x02da, B:163:0x02e4, B:166:0x02ee, B:169:0x02f8, B:172:0x0302, B:175:0x030c, B:178:0x0317, B:206:0x03d5, B:208:0x03db, B:212:0x03e4, B:216:0x03f2, B:218:0x03fc, B:220:0x040a, B:222:0x0412, B:223:0x041a, B:224:0x0422, B:226:0x042a, B:227:0x0432, B:213:0x03e8, B:215:0x03ee, B:228:0x0439, B:230:0x043f, B:234:0x0448, B:235:0x044c, B:237:0x0452, B:238:0x0456, B:240:0x045c, B:244:0x0465, B:245:0x046f, B:247:0x0475, B:248:0x0479, B:250:0x047f, B:254:0x0496, B:255:0x04a6, B:258:0x04ae, B:265:0x04be, B:268:0x04cd, B:267:0x04c8, B:269:0x04d0), top: B:281:0x0040, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01bf A[Catch: IOException -> 0x04da, XmlPullParserException -> 0x04e9, all -> 0x0505, TryCatch #4 {IOException -> 0x04da, XmlPullParserException -> 0x04e9, blocks: (B:11:0x0040, B:13:0x005f, B:15:0x0066, B:21:0x008e, B:24:0x0096, B:26:0x00a1, B:29:0x00a8, B:30:0x00b4, B:32:0x00ba, B:35:0x00c2, B:37:0x00cd, B:40:0x00d4, B:41:0x00e2, B:45:0x00fd, B:47:0x0104, B:60:0x013b, B:63:0x0144, B:66:0x014d, B:68:0x0155, B:70:0x015d, B:72:0x0167, B:74:0x016c, B:76:0x0174, B:78:0x017c, B:80:0x0183, B:82:0x018d, B:84:0x0195, B:86:0x019c, B:88:0x01a6, B:90:0x01ae, B:93:0x01b7, B:95:0x01bf, B:97:0x01c7, B:100:0x01d0, B:102:0x01d8, B:104:0x01e0, B:107:0x01e9, B:109:0x01f1, B:111:0x01f9, B:114:0x0202, B:116:0x020a, B:118:0x0210, B:120:0x021a, B:122:0x0220, B:123:0x0225, B:125:0x022b, B:127:0x0231, B:54:0x0121, B:56:0x0127, B:131:0x025e, B:133:0x0272, B:135:0x028b, B:137:0x0291, B:138:0x0296, B:141:0x02a0, B:145:0x02a9, B:146:0x02ad, B:148:0x02b3, B:149:0x02b7, B:151:0x02be, B:152:0x02c2, B:185:0x0328, B:186:0x0332, B:187:0x033c, B:188:0x0346, B:189:0x0350, B:190:0x035a, B:191:0x0364, B:192:0x036e, B:193:0x0378, B:194:0x0381, B:196:0x0387, B:199:0x038f, B:201:0x0399, B:203:0x03ae, B:204:0x03b6, B:205:0x03be, B:154:0x02c6, B:157:0x02d0, B:160:0x02da, B:163:0x02e4, B:166:0x02ee, B:169:0x02f8, B:172:0x0302, B:175:0x030c, B:178:0x0317, B:206:0x03d5, B:208:0x03db, B:212:0x03e4, B:216:0x03f2, B:218:0x03fc, B:220:0x040a, B:222:0x0412, B:223:0x041a, B:224:0x0422, B:226:0x042a, B:227:0x0432, B:213:0x03e8, B:215:0x03ee, B:228:0x0439, B:230:0x043f, B:234:0x0448, B:235:0x044c, B:237:0x0452, B:238:0x0456, B:240:0x045c, B:244:0x0465, B:245:0x046f, B:247:0x0475, B:248:0x0479, B:250:0x047f, B:254:0x0496, B:255:0x04a6, B:258:0x04ae, B:265:0x04be, B:268:0x04cd, B:267:0x04c8, B:269:0x04d0), top: B:281:0x0040, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean LoadSettings(android.app.Activity r25) {
        /*
            Method dump skipped, instructions count: 1360
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.common.utils.DefLoader.LoadSettings(android.app.Activity):boolean");
    }

    public static boolean checkTag(String str, String str2) {
        return str != null && str.equals(str2);
    }

    public static void removeDuplicates(ArrayList<ArrayList<String>> arrayList) {
        HashSet hashSet = new HashSet();
        ArrayList arrayList2 = new ArrayList();
        Iterator<ArrayList<String>> it = arrayList.iterator();
        while (it.hasNext()) {
            ArrayList<String> next = it.next();
            if (hashSet.add(next)) {
                arrayList2.add(next);
            }
        }
        arrayList.clear();
        arrayList.addAll(arrayList2);
    }

    private DefLoader() {
    }
}
