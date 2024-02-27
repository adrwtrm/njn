package com.epson.iprojection.common.utils;

import android.app.Activity;
import com.epson.iprojection.common.Lg;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes.dex */
public class XmlUtils {

    /* loaded from: classes.dex */
    public enum filterType {
        MPLIST,
        MASTER,
        SHMPLIST,
        SETTING,
        USERDATA,
        BOTH,
        IGNORE
    }

    /* loaded from: classes.dex */
    public enum mplistType {
        LOCAL,
        SHARED,
        BOTH,
        LOST,
        UNKNOWN
    }

    /* loaded from: classes.dex */
    public enum place {
        USER,
        APPS
    }

    public static void transportSettingsUserToApps() {
        String userDirPath = PathGetter.getIns().getUserDirPath();
        if (userDirPath == null || FileUtils.mkDirectory(userDirPath)) {
            return;
        }
        File file = new File(PathGetter.getIns().getUserDirPath() + "/iprojection.def");
        if (!file.exists()) {
            return;
        }
        FileUtils.mkDirectory(PathGetter.getIns().getAppsDirPath());
        File file2 = new File(PathGetter.getIns().getAppsDirPath() + "/iprojection.def");
        FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    FileInputStream fileInputStream2 = new FileInputStream(file);
                    try {
                        transport(file2, fileInputStream2);
                        fileInputStream2.close();
                    } catch (IOException unused) {
                        fileInputStream = fileInputStream2;
                        Lg.e("stream error.");
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        file.delete();
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException unused2) {
                                Lg.e("stream close error.");
                                throw th;
                            }
                        }
                        file.delete();
                        throw th;
                    }
                } catch (IOException unused3) {
                }
                file.delete();
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException unused4) {
            Lg.e("stream close error.");
        }
    }

    public static void transportXmlUserToApps() {
        String userDirPath = PathGetter.getIns().getUserDirPath();
        if (userDirPath == null || FileUtils.mkDirectory(userDirPath)) {
            return;
        }
        File file = new File(PathGetter.getIns().getUserDirPath() + "/root.mplist");
        if (file.exists()) {
            FileUtils.mkDirectory(PathGetter.getIns().getAppsDirPath());
            File file2 = new File(PathGetter.getIns().getAppsDirPath() + "/.mplist_local_master");
            FileInputStream fileInputStream = null;
            try {
                try {
                    try {
                        FileInputStream fileInputStream2 = new FileInputStream(file);
                        try {
                            transport(file2, fileInputStream2);
                            fileInputStream2.close();
                        } catch (IOException unused) {
                            fileInputStream = fileInputStream2;
                            Lg.e("stream error.");
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            file.delete();
                        } catch (Throwable th) {
                            th = th;
                            fileInputStream = fileInputStream2;
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException unused2) {
                                    Lg.e("stream close error.");
                                    throw th;
                                }
                            }
                            file.delete();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (IOException unused3) {
                }
                file.delete();
            } catch (IOException unused4) {
                Lg.e("stream close error.");
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0095 A[Catch: IOException -> 0x0099, TRY_LEAVE, TryCatch #0 {IOException -> 0x0099, blocks: (B:41:0x0090, B:43:0x0095), top: B:48:0x0090 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0090 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void transportXmlHttpToApps(java.lang.String r6) {
        /*
            java.lang.String r0 = "http disconnct or stream close error."
            java.lang.String r1 = "/.mplist_shared_master"
            if (r6 == 0) goto L9d
            java.lang.String r2 = ""
            boolean r2 = r6.equals(r2)
            if (r2 != 0) goto L9d
            boolean r2 = checkXml(r6)
            if (r2 != 0) goto L16
            goto L9d
        L16:
            r2 = 0
            java.io.File r3 = new java.io.File     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            r4.<init>()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            com.epson.iprojection.common.utils.PathGetter r5 = com.epson.iprojection.common.utils.PathGetter.getIns()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            java.lang.String r5 = r5.getAppsDirPath()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            java.lang.StringBuilder r1 = r4.append(r1)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            java.net.URL r1 = new java.net.URL     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            r1.<init>(r6)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            java.net.URLConnection r6 = r1.openConnection()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            java.net.HttpURLConnection r6 = (java.net.HttpURLConnection) r6     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            if (r6 != 0) goto L4c
            if (r6 == 0) goto L4b
            r6.disconnect()     // Catch: java.io.IOException -> L48
            goto L4b
        L48:
            com.epson.iprojection.common.Lg.e(r0)
        L4b:
            return
        L4c:
            r1 = 5000(0x1388, float:7.006E-42)
            r6.setConnectTimeout(r1)     // Catch: java.lang.Exception -> L70 java.lang.Throwable -> L8d
            java.lang.String r1 = "GET"
            r6.setRequestMethod(r1)     // Catch: java.lang.Exception -> L70 java.lang.Throwable -> L8d
            r6.connect()     // Catch: java.lang.Exception -> L70 java.lang.Throwable -> L8d
            java.io.InputStream r2 = r6.getInputStream()     // Catch: java.lang.Exception -> L70 java.lang.Throwable -> L8d
            boolean r1 = transport(r3, r2)     // Catch: java.lang.Exception -> L70 java.lang.Throwable -> L8d
            if (r1 != 0) goto L68
            java.lang.String r1 = "App領域へのコピーに失敗"
            com.epson.iprojection.common.Lg.e(r1)     // Catch: java.lang.Exception -> L70 java.lang.Throwable -> L8d
        L68:
            if (r2 == 0) goto L6d
            r2.close()     // Catch: java.io.IOException -> L89
        L6d:
            if (r6 == 0) goto L8c
            goto L85
        L70:
            r1 = move-exception
            goto L77
        L72:
            r1 = move-exception
            r6 = r2
            goto L8e
        L75:
            r1 = move-exception
            r6 = r2
        L77:
            java.lang.String r1 = r1.getMessage()     // Catch: java.lang.Throwable -> L8d
            com.epson.iprojection.common.Lg.e(r1)     // Catch: java.lang.Throwable -> L8d
            if (r2 == 0) goto L83
            r2.close()     // Catch: java.io.IOException -> L89
        L83:
            if (r6 == 0) goto L8c
        L85:
            r6.disconnect()     // Catch: java.io.IOException -> L89
            goto L8c
        L89:
            com.epson.iprojection.common.Lg.e(r0)
        L8c:
            return
        L8d:
            r1 = move-exception
        L8e:
            if (r2 == 0) goto L93
            r2.close()     // Catch: java.io.IOException -> L99
        L93:
            if (r6 == 0) goto L9c
            r6.disconnect()     // Catch: java.io.IOException -> L99
            goto L9c
        L99:
            com.epson.iprojection.common.Lg.e(r0)
        L9c:
            throw r1
        L9d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.common.utils.XmlUtils.transportXmlHttpToApps(java.lang.String):void");
    }

    private static boolean checkXml(String str) {
        return FileUtils.isProfile(FileUtils.getFileName(str));
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0057, code lost:
        if (r3 == null) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0059, code lost:
        r3.close();
        r7 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0063, code lost:
        if (r3 == null) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean transport(java.io.File r7, java.io.InputStream r8) {
        /*
            java.lang.String r0 = "stream close error."
            com.epson.iprojection.common.utils.PathGetter r1 = com.epson.iprojection.common.utils.PathGetter.getIns()
            java.lang.String r1 = r1.getAppsDirPath()
            r2 = 4096(0x1000, float:5.74E-42)
            byte[] r2 = new byte[r2]
            r3 = 0
            r4 = 0
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L51 java.io.FileNotFoundException -> L5d
            java.lang.String r7 = com.epson.iprojection.common.utils.FileUtils.getFileName(r7)     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L51 java.io.FileNotFoundException -> L5d
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L51 java.io.FileNotFoundException -> L5d
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L51 java.io.FileNotFoundException -> L5d
            r6.<init>()     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L51 java.io.FileNotFoundException -> L5d
            java.lang.StringBuilder r1 = r6.append(r1)     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L51 java.io.FileNotFoundException -> L5d
            java.lang.String r6 = "/"
            java.lang.StringBuilder r1 = r1.append(r6)     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L51 java.io.FileNotFoundException -> L5d
            java.lang.StringBuilder r7 = r1.append(r7)     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L51 java.io.FileNotFoundException -> L5d
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L51 java.io.FileNotFoundException -> L5d
            r5.<init>(r7)     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L51 java.io.FileNotFoundException -> L5d
            r7 = r4
        L35:
            int r1 = r8.read(r2)     // Catch: java.lang.Throwable -> L48 java.io.IOException -> L4b java.io.FileNotFoundException -> L4d
            if (r1 <= 0) goto L40
            r5.write(r2, r4, r1)     // Catch: java.lang.Throwable -> L48 java.io.IOException -> L4b java.io.FileNotFoundException -> L4d
            int r7 = r7 + r1
            goto L35
        L40:
            r5.close()     // Catch: java.io.IOException -> L44
            goto L66
        L44:
            com.epson.iprojection.common.Lg.e(r0)
            goto L66
        L48:
            r7 = move-exception
            r3 = r5
            goto L6a
        L4b:
            r3 = r5
            goto L52
        L4d:
            r3 = r5
            goto L5e
        L4f:
            r7 = move-exception
            goto L6a
        L51:
            r7 = r4
        L52:
            java.lang.String r8 = "IOException."
            com.epson.iprojection.common.Lg.e(r8)     // Catch: java.lang.Throwable -> L4f
            if (r3 == 0) goto L66
        L59:
            r3.close()     // Catch: java.io.IOException -> L44
            goto L66
        L5d:
            r7 = r4
        L5e:
            java.lang.String r8 = "FileNotFoundException."
            com.epson.iprojection.common.Lg.e(r8)     // Catch: java.lang.Throwable -> L4f
            if (r3 == 0) goto L66
            goto L59
        L66:
            if (r7 == 0) goto L69
            r4 = 1
        L69:
            return r4
        L6a:
            if (r3 == 0) goto L73
            r3.close()     // Catch: java.io.IOException -> L70
            goto L73
        L70:
            com.epson.iprojection.common.Lg.e(r0)
        L73:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.common.utils.XmlUtils.transport(java.io.File, java.io.InputStream):boolean");
    }

    public static void deleteProfileMasterData(filterType filtertype) {
        if (FileUtils.mkDirectory(PathGetter.getIns().getAppsDirPath())) {
            return;
        }
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$common$utils$XmlUtils$filterType[filtertype.ordinal()];
        if (i == 1) {
            File file = new File(PathGetter.getIns().getAppsDirPath() + "/.mplist_shared_master");
            if (file.exists()) {
                file.delete();
            }
        } else if (i != 2) {
        } else {
            File file2 = new File(PathGetter.getIns().getAppsDirPath() + "/.mplist_local_master");
            if (file2.exists()) {
                file2.delete();
            }
        }
    }

    /* renamed from: com.epson.iprojection.common.utils.XmlUtils$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$common$utils$XmlUtils$filterType;

        static {
            int[] iArr = new int[filterType.values().length];
            $SwitchMap$com$epson$iprojection$common$utils$XmlUtils$filterType = iArr;
            try {
                iArr[filterType.SHMPLIST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$common$utils$XmlUtils$filterType[filterType.MPLIST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$common$utils$XmlUtils$filterType[filterType.USERDATA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$common$utils$XmlUtils$filterType[filterType.MASTER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$common$utils$XmlUtils$filterType[filterType.IGNORE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$epson$iprojection$common$utils$XmlUtils$filterType[filterType.SETTING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static mplistType existProfileTypes() {
        mplistType mplisttype = mplistType.LOST;
        boolean exists = new File(PathGetter.getIns().getAppsDirPath() + "/.mplist_local_master").exists();
        boolean exists2 = new File(PathGetter.getIns().getAppsDirPath() + "/.mplist_shared_master").exists();
        if (exists && exists2) {
            return mplistType.BOTH;
        }
        if (exists) {
            mplisttype = mplistType.LOCAL;
        }
        return exists2 ? mplistType.SHARED : mplisttype;
    }

    public static File[] listFilter(filterType filtertype, place placeVar) {
        File file;
        if (place.USER == placeVar) {
            file = new File(PathGetter.getIns().getUserDirPath());
        } else {
            file = new File(PathGetter.getIns().getAppsDirPath());
        }
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$common$utils$XmlUtils$filterType[filtertype.ordinal()];
        if (i != 2) {
            if (i != 3) {
                if (i != 4) {
                    if (i != 5) {
                        if (i != 6) {
                            return null;
                        }
                        return file.listFiles(new settingFileList());
                    }
                    return file.listFiles(new ignoreList());
                }
                return file.listFiles(new masterList());
            }
            return file.listFiles(new userDataList());
        }
        return file.listFiles(new profileList());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 4, insn: 0x00a6: MOVE  (r1 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:48:0x00a6 */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v4, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r10v7 */
    public static XmlPullParser buildXmlParse(Activity activity, String str) {
        ?? r10;
        BufferedReader bufferedReader;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        FileInputStream fileInputStream3 = null;
        try {
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            StringBuilder sb = new StringBuilder();
            try {
                try {
                    try {
                        fileInputStream = activity.openFileInput(str);
                    } catch (IOException unused) {
                        bufferedReader = null;
                        fileInputStream = null;
                    } catch (XmlPullParserException unused2) {
                        bufferedReader = null;
                        fileInputStream = null;
                    } catch (Throwable th) {
                        th = th;
                        r10 = 0;
                    }
                    try {
                        if (fileInputStream.available() >= 3) {
                            byte[] bArr = {0, 0, 0};
                            fileInputStream.read(bArr, 0, 3);
                            if (bArr[0] != -17 || bArr[1] != -69 || bArr[2] != -65) {
                                fileInputStream.close();
                                fileInputStream = activity.openFileInput(str);
                            }
                        }
                        bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                        while (true) {
                            try {
                                String readLine = bufferedReader.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                sb.append(readLine).append("\n");
                            } catch (IOException unused3) {
                                Lg.e("IOException return.");
                                if (fileInputStream != null) {
                                    fileInputStream.close();
                                }
                                if (bufferedReader == null) {
                                    return null;
                                }
                                bufferedReader.close();
                                return null;
                            } catch (XmlPullParserException unused4) {
                                Lg.e("XmlPullParserException return.");
                                if (fileInputStream != null) {
                                    fileInputStream.close();
                                }
                                if (bufferedReader == null) {
                                    return null;
                                }
                                bufferedReader.close();
                                return null;
                            }
                        }
                        newPullParser.setInput(new StringReader(sb.toString()));
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException unused5) {
                                Lg.e("BufferReader close error.");
                            }
                        }
                        bufferedReader.close();
                        return newPullParser;
                    } catch (IOException unused6) {
                        bufferedReader = null;
                    } catch (XmlPullParserException unused7) {
                        bufferedReader = null;
                    } catch (Throwable th2) {
                        th = th2;
                        r10 = 0;
                        fileInputStream3 = fileInputStream;
                        if (fileInputStream3 != null) {
                            try {
                                fileInputStream3.close();
                            } catch (IOException unused8) {
                                Lg.e("BufferReader close error.");
                                throw th;
                            }
                        }
                        if (r10 != 0) {
                            r10.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    fileInputStream3 = fileInputStream2;
                    r10 = activity;
                    th = th3;
                }
            } catch (IOException unused9) {
                Lg.e("BufferReader close error.");
                return null;
            }
        } catch (XmlPullParserException unused10) {
            Lg.e("Fail to create XmlPullParser instance.");
            return null;
        }
    }

    private XmlUtils() {
    }
}
