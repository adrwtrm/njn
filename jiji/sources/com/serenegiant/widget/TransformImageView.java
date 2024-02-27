package com.serenegiant.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import com.serenegiant.view.IViewTransformer;
import com.serenegiant.view.ViewTransformer;

/* loaded from: classes2.dex */
public class TransformImageView extends AppCompatImageView {
    private static final boolean DEBUG = false;
    private static final String TAG = "TransformImageView";
    private IViewTransformer mViewTransformer;

    public TransformImageView(Context context) {
        this(context, null, 0);
    }

    public TransformImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TransformImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        super.setScaleType(ImageView.ScaleType.MATRIX);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void superSetScaleType(ImageView.ScaleType scaleType) {
        super.setScaleType(scaleType);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void superSetImageMatrix(Matrix matrix) {
        super.setImageMatrix(matrix);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Matrix superGetImageMatrix(Matrix matrix) {
        if (matrix != null) {
            matrix.set(super.getImageMatrix());
            return matrix;
        }
        return new Matrix(super.getImageMatrix());
    }

    public void setViewTransformer(IViewTransformer iViewTransformer) {
        this.mViewTransformer = iViewTransformer;
    }

    public IViewTransformer getViewTransformer() {
        if (this.mViewTransformer == null) {
            this.mViewTransformer = new DefaultViewTransformer(this);
        }
        return this.mViewTransformer;
    }

    public void setTransform(Matrix matrix) {
        getViewTransformer().setTransform(matrix);
    }

    public Matrix getTransform(Matrix matrix) {
        return getViewTransformer().getTransform(matrix);
    }

    /* loaded from: classes2.dex */
    public static class DefaultViewTransformer extends ViewTransformer {
        public DefaultViewTransformer(TransformImageView transformImageView) {
            super(transformImageView);
        }

        @Override // com.serenegiant.view.ViewTransformer
        protected void setTransform(View view, Matrix matrix) {
            ((TransformImageView) getTargetView()).superSetImageMatrix(matrix);
        }

        @Override // com.serenegiant.view.ViewTransformer
        protected Matrix getTransform(View view, Matrix matrix) {
            return ((TransformImageView) getTargetView()).superGetImageMatrix(matrix);
        }
    }
}
