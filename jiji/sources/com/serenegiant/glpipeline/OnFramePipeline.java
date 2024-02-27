package com.serenegiant.glpipeline;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OnFramePipeline.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\"\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/serenegiant/glpipeline/OnFramePipeline;", "Lcom/serenegiant/glpipeline/ProxyPipeline;", "mListener", "Lcom/serenegiant/glpipeline/OnFramePipeline$OnFrameAvailableListener;", "(Lcom/serenegiant/glpipeline/OnFramePipeline$OnFrameAvailableListener;)V", "onFrameAvailable", "", "isOES", "", "texId", "", "texMatrix", "", "OnFrameAvailableListener", "common_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class OnFramePipeline extends ProxyPipeline {
    private final OnFrameAvailableListener mListener;

    /* compiled from: OnFramePipeline.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lcom/serenegiant/glpipeline/OnFramePipeline$OnFrameAvailableListener;", "", "onFrameAvailable", "", "common_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public interface OnFrameAvailableListener {
        void onFrameAvailable();
    }

    public OnFramePipeline(OnFrameAvailableListener mListener) {
        Intrinsics.checkNotNullParameter(mListener, "mListener");
        this.mListener = mListener;
    }

    @Override // com.serenegiant.glpipeline.ProxyPipeline, com.serenegiant.glpipeline.GLPipeline
    public void onFrameAvailable(boolean z, int i, float[] texMatrix) {
        Intrinsics.checkNotNullParameter(texMatrix, "texMatrix");
        super.onFrameAvailable(z, i, texMatrix);
        this.mListener.onFrameAvailable();
    }
}
