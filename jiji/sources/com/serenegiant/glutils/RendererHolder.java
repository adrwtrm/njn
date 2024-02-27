package com.serenegiant.glutils;

import com.serenegiant.egl.EGLBase;
import com.serenegiant.glutils.AbstractRendererHolder;
import com.serenegiant.glutils.IRendererHolder;

/* loaded from: classes2.dex */
public class RendererHolder extends AbstractRendererHolder {
    private static final String TAG = "RendererHolder";

    public RendererHolder(int i, int i2, IRendererHolder.RenderHolderCallback renderHolderCallback) {
        this(i, i2, 3, null, 2, renderHolderCallback);
    }

    public RendererHolder(int i, int i2, int i3, EGLBase.IContext<?> iContext, int i4, IRendererHolder.RenderHolderCallback renderHolderCallback) {
        super(i, i2, i3, iContext, i4, renderHolderCallback);
    }

    @Override // com.serenegiant.glutils.AbstractRendererHolder
    protected AbstractRendererHolder.BaseRendererTask createRendererTask(int i, int i2, int i3, EGLBase.IContext<?> iContext, int i4) {
        return new AbstractRendererHolder.BaseRendererTask(this, i, i2, i3, iContext, i4, null);
    }
}
