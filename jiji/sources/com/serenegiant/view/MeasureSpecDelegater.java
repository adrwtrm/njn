package com.serenegiant.view;

import android.view.View;

/* loaded from: classes2.dex */
public class MeasureSpecDelegater {
    private MeasureSpecDelegater() {
    }

    /* loaded from: classes2.dex */
    public static class MeasureSpec {
        public int heightMeasureSpec;
        public int widthMeasureSpec;

        private MeasureSpec(int i, int i2) {
            this.widthMeasureSpec = i;
            this.heightMeasureSpec = i2;
        }
    }

    public static MeasureSpec onMeasure(View view, double d, int i, boolean z, int i2, int i3) {
        MeasureSpec measureSpec = new MeasureSpec(i2, i3);
        if (d > 0.0d && i == 0 && z) {
            int size = View.MeasureSpec.getSize(i2);
            int size2 = View.MeasureSpec.getSize(i3);
            int paddingLeft = view.getPaddingLeft() + view.getPaddingRight();
            int paddingTop = view.getPaddingTop() + view.getPaddingBottom();
            int i4 = size - paddingLeft;
            int i5 = size2 - paddingTop;
            double d2 = i4;
            double d3 = i5;
            double d4 = (d / (d2 / d3)) - 1.0d;
            if (Math.abs(d4) > 0.005d) {
                if (d4 > 0.0d) {
                    i5 = (int) (d2 / d);
                } else {
                    i4 = (int) (d3 * d);
                }
                measureSpec.widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(i4 + paddingLeft, 1073741824);
                measureSpec.heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(i5 + paddingTop, 1073741824);
            }
        }
        return measureSpec;
    }
}
