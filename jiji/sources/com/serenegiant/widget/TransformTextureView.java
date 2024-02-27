package com.serenegiant.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;
import com.serenegiant.view.IViewTransformer;
import com.serenegiant.view.ViewTransformer;

/* loaded from: classes2.dex */
public class TransformTextureView extends TextureView {
    private static final boolean DEBUG = false;
    private static final String TAG = "TransformTextureView";
    private IViewTransformer mViewTransformer;

    public TransformTextureView(Context context) {
        this(context, null, 0);
    }

    public TransformTextureView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TransformTextureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setViewTransformer(IViewTransformer iViewTransformer) {
        this.mViewTransformer = iViewTransformer;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void superSetTransform(Matrix matrix) {
        super.setTransform(matrix);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Matrix superGetTransform(Matrix matrix) {
        return super.getTransform(matrix);
    }

    public IViewTransformer getViewTransformer() {
        if (this.mViewTransformer == null) {
            this.mViewTransformer = new DefaultViewTransformer(this);
        }
        return this.mViewTransformer;
    }

    /* loaded from: classes2.dex */
    public static class DefaultViewTransformer extends ViewTransformer {
        public DefaultViewTransformer(TransformTextureView transformTextureView) {
            super(transformTextureView);
        }

        @Override // com.serenegiant.view.ViewTransformer
        protected void setTransform(View view, Matrix matrix) {
            ((TransformTextureView) getTargetView()).superSetTransform(matrix);
        }

        @Override // com.serenegiant.view.ViewTransformer
        protected Matrix getTransform(View view, Matrix matrix) {
            return ((TransformTextureView) getTargetView()).superGetTransform(matrix);
        }
    }
}
