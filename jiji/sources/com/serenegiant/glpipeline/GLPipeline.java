package com.serenegiant.glpipeline;

import android.util.Log;
import com.serenegiant.gl.GLConst;

/* loaded from: classes2.dex */
public interface GLPipeline extends GLConst {
    public static final String TAG = "GLPipeline";

    int getHeight();

    GLPipeline getParent();

    GLPipeline getPipeline();

    int getWidth();

    boolean isActive();

    boolean isValid();

    void onFrameAvailable(boolean z, int i, float[] fArr);

    void refresh();

    void release();

    void remove();

    void resize(int i, int i2) throws IllegalStateException;

    void setParent(GLPipeline gLPipeline);

    void setPipeline(GLPipeline gLPipeline);

    static GLPipeline findLast(GLPipeline gLPipeline) {
        GLPipeline pipeline = gLPipeline.getPipeline();
        while (true) {
            GLPipeline gLPipeline2 = pipeline;
            GLPipeline gLPipeline3 = gLPipeline;
            gLPipeline = gLPipeline2;
            if (gLPipeline == null) {
                return gLPipeline3;
            }
            pipeline = gLPipeline.getPipeline();
        }
    }

    static GLPipeline findFirst(GLPipeline gLPipeline) {
        GLPipeline parent = gLPipeline.getParent();
        while (true) {
            GLPipeline gLPipeline2 = parent;
            GLPipeline gLPipeline3 = gLPipeline;
            gLPipeline = gLPipeline2;
            if (gLPipeline == null) {
                return gLPipeline3;
            }
            parent = gLPipeline.getParent();
        }
    }

    static <T extends GLPipeline> T find(GLPipeline gLPipeline, Class<T> cls) {
        for (GLPipeline findFirst = findFirst(gLPipeline); findFirst != null; findFirst = findFirst.getPipeline()) {
            if (findFirst.getClass() == cls) {
                return cls.cast(findFirst);
            }
        }
        return null;
    }

    static GLPipeline append(GLPipeline gLPipeline, GLPipeline gLPipeline2) {
        findLast(gLPipeline).setPipeline(gLPipeline2);
        return gLPipeline2;
    }

    static GLPipeline insert(GLPipeline gLPipeline, GLPipeline gLPipeline2) {
        GLPipeline pipeline = gLPipeline.getPipeline();
        if (pipeline == null) {
            gLPipeline.setPipeline(gLPipeline2);
        } else if (pipeline != gLPipeline2) {
            GLPipeline findLast = findLast(gLPipeline2);
            gLPipeline.setPipeline(gLPipeline2);
            findLast.setPipeline(pipeline);
        }
        return gLPipeline2;
    }

    static String pipelineString(GLPipeline gLPipeline) {
        StringBuilder sb = new StringBuilder("[");
        for (GLPipeline gLPipeline2 = gLPipeline; gLPipeline2 != null; gLPipeline2 = gLPipeline2.getPipeline()) {
            if (gLPipeline2 != gLPipeline) {
                sb.append(',');
            }
            sb.append(gLPipeline2);
        }
        sb.append("]");
        return sb.toString();
    }

    static boolean validatePipelineChain(GLPipeline gLPipeline) {
        boolean z = true;
        while (gLPipeline != null) {
            GLPipeline pipeline = gLPipeline.getPipeline();
            if (pipeline != null && pipeline.getParent() != gLPipeline) {
                Log.v(TAG, "validatePipelineChain:found wrong chain" + gLPipeline + "=>" + pipeline + (pipeline != null ? "(" + pipeline.getParent() + ")" : ""));
                pipeline.setParent(gLPipeline);
                z = false;
            }
            gLPipeline = pipeline;
        }
        return z;
    }
}
