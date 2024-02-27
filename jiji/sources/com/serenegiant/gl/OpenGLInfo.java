package com.serenegiant.gl;

import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class OpenGLInfo {
    private static final int EGL_CLIENT_APIS = 12429;

    private OpenGLInfo() {
    }

    public static JSONObject get() throws JSONException {
        final Semaphore semaphore;
        final JSONObject jSONObject = new JSONObject();
        try {
            final GLContext gLContext = new GLContext(3, null, 0);
            semaphore = new Semaphore(0);
            new Thread(new Runnable() { // from class: com.serenegiant.gl.OpenGLInfo$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    OpenGLInfo.lambda$get$0(GLContext.this, jSONObject, semaphore);
                }
            }).start();
        } catch (Exception e) {
            jSONObject.put("EXCEPTION", e.getMessage());
        }
        if (semaphore.tryAcquire(3000L, TimeUnit.MILLISECONDS)) {
            return jSONObject;
        }
        throw new RuntimeException("Failed to get GL info");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(32:118|119|49|50|51|52|(2:53|54)|55|56|57|58|59|60|61|62|63|64|65|66|67|68|(2:69|70)|71|72|73|74|(2:75|76)|78|79|80|81|82) */
    /* JADX WARN: Code restructure failed: missing block: B:292:0x01be, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:294:0x01c0, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:295:0x01c1, code lost:
        r4 = "EGL_VENDOR";
     */
    /* JADX WARN: Code restructure failed: missing block: B:296:0x01c3, code lost:
        r5.put(r4, r0.getMessage());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void lambda$get$0(com.serenegiant.gl.GLContext r21, org.json.JSONObject r22, java.util.concurrent.Semaphore r23) {
        /*
            Method dump skipped, instructions count: 569
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.serenegiant.gl.OpenGLInfo.lambda$get$0(com.serenegiant.gl.GLContext, org.json.JSONObject, java.util.concurrent.Semaphore):void");
    }

    private static final JSONObject formatExtensions(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        String[] split = str.split(" ");
        Arrays.sort(split);
        for (int i = 0; i < split.length; i++) {
            jSONObject.put(Integer.toString(i), split[i]);
        }
        return jSONObject;
    }
}
