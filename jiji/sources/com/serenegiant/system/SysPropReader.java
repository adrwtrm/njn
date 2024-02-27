package com.serenegiant.system;

/* loaded from: classes2.dex */
public class SysPropReader {
    private static final boolean DEBUG = false;
    private static final String GETPROP_EXECUTABLE_PATH = "/system/bin/getprop";
    private static final String TAG = "SysPropReader";

    private SysPropReader() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0059, code lost:
        if (r6 == null) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0060, code lost:
        if (android.text.TextUtils.isEmpty(r0) == false) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0062, code lost:
        return "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:?, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0034, code lost:
        if (r6 == null) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0036, code lost:
        r6.destroy();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String read(java.lang.String r6) {
        /*
            r0 = 0
            java.lang.ProcessBuilder r1 = new java.lang.ProcessBuilder     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L52
            r2 = 0
            java.lang.String[] r3 = new java.lang.String[r2]     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L52
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L52
            r3 = 2
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L52
            java.lang.String r4 = "/system/bin/getprop"
            r3[r2] = r4     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L52
            r2 = 1
            r3[r2] = r6     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L52
            java.lang.ProcessBuilder r6 = r1.command(r3)     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L52
            java.lang.ProcessBuilder r6 = r6.redirectErrorStream(r2)     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L52
            java.lang.Process r6 = r6.start()     // Catch: java.lang.Throwable -> L43 java.lang.Exception -> L52
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L41
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L41
            java.io.InputStream r3 = r6.getInputStream()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L41
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L41
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L41
            java.lang.String r0 = r1.readLine()     // Catch: java.lang.Throwable -> L3a java.lang.Exception -> L54
            r1.close()     // Catch: java.io.IOException -> L34
        L34:
            if (r6 == 0) goto L5c
        L36:
            r6.destroy()
            goto L5c
        L3a:
            r0 = move-exception
            goto L47
        L3c:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L47
        L41:
            r1 = r0
            goto L54
        L43:
            r6 = move-exception
            r1 = r0
            r0 = r6
            r6 = r1
        L47:
            if (r1 == 0) goto L4c
            r1.close()     // Catch: java.io.IOException -> L4c
        L4c:
            if (r6 == 0) goto L51
            r6.destroy()
        L51:
            throw r0
        L52:
            r6 = r0
            r1 = r6
        L54:
            if (r1 == 0) goto L59
            r1.close()     // Catch: java.io.IOException -> L59
        L59:
            if (r6 == 0) goto L5c
            goto L36
        L5c:
            boolean r6 = android.text.TextUtils.isEmpty(r0)
            if (r6 == 0) goto L64
            java.lang.String r0 = ""
        L64:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.system.SysPropReader.read(java.lang.String):java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0053, code lost:
        if (r6 == null) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0070, code lost:
        if (r6 == null) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0072, code lost:
        r6.destroy();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0079, code lost:
        return r0.toString();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String readAll(java.lang.String r6) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 0
            java.lang.ProcessBuilder r2 = new java.lang.ProcessBuilder     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L6a
            r3 = 0
            java.lang.String[] r4 = new java.lang.String[r3]     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L6a
            r2.<init>(r4)     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L6a
            r4 = 2
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L6a
            java.lang.String r5 = "/system/bin/getprop"
            r4[r3] = r5     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L6a
            r3 = 1
            r4[r3] = r6     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L6a
            java.lang.ProcessBuilder r6 = r2.command(r4)     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L6a
            java.lang.ProcessBuilder r6 = r6.redirectErrorStream(r3)     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L6a
            java.lang.Process r6 = r6.start()     // Catch: java.lang.Throwable -> L5d java.lang.Exception -> L6a
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L6b
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L6b
            java.io.InputStream r4 = r6.getInputStream()     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L6b
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L6b
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L6b
        L32:
            java.lang.String r1 = r2.readLine()     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            boolean r3 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            if (r3 != 0) goto L4a
            int r3 = r0.length()     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            if (r3 <= 0) goto L47
            java.lang.String r3 = "\n"
            r0.append(r3)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
        L47:
            r0.append(r1)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
        L4a:
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            if (r1 == 0) goto L32
            r2.close()     // Catch: java.io.IOException -> L53
        L53:
            if (r6 == 0) goto L75
            goto L72
        L56:
            r0 = move-exception
            r1 = r2
            goto L5f
        L59:
            r1 = r2
            goto L6b
        L5b:
            r0 = move-exception
            goto L5f
        L5d:
            r0 = move-exception
            r6 = r1
        L5f:
            if (r1 == 0) goto L64
            r1.close()     // Catch: java.io.IOException -> L64
        L64:
            if (r6 == 0) goto L69
            r6.destroy()
        L69:
            throw r0
        L6a:
            r6 = r1
        L6b:
            if (r1 == 0) goto L70
            r1.close()     // Catch: java.io.IOException -> L70
        L70:
            if (r6 == 0) goto L75
        L72:
            r6.destroy()
        L75:
            java.lang.String r6 = r0.toString()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.system.SysPropReader.readAll(java.lang.String):java.lang.String");
    }
}
