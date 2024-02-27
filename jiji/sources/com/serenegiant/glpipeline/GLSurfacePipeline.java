package com.serenegiant.glpipeline;

import com.serenegiant.math.Fraction;

/* loaded from: classes2.dex */
public interface GLSurfacePipeline extends GLPipeline {
    int getId();

    boolean hasSurface();

    void setSurface(Object obj) throws IllegalStateException, IllegalArgumentException;

    void setSurface(Object obj, Fraction fraction) throws IllegalStateException, IllegalArgumentException;

    static GLSurfacePipeline findById(GLPipeline gLPipeline, int i) {
        for (GLPipeline findFirst = GLPipeline.findFirst(gLPipeline); findFirst != null; findFirst = findFirst.getPipeline()) {
            if (findFirst instanceof GLSurfacePipeline) {
                GLSurfacePipeline gLSurfacePipeline = (GLSurfacePipeline) findFirst;
                if (gLSurfacePipeline.getId() == i) {
                    return gLSurfacePipeline;
                }
            }
        }
        return null;
    }
}
