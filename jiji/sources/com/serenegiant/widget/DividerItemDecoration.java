package com.serenegiant.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.serenegiant.common.R;

/* loaded from: classes2.dex */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = {16843284};
    public static final int HORIZONTAL_LIST = 0;
    public static final int VERTICAL_LIST = 1;
    private Drawable mDivider;
    private int mOrientation = 1;

    public DividerItemDecoration(Context context) {
        Drawable drawable;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(ATTRS);
        try {
            drawable = obtainStyledAttributes.getDrawable(0);
        } catch (Exception unused) {
            drawable = null;
        }
        obtainStyledAttributes.recycle();
        init(drawable);
    }

    public DividerItemDecoration(Context context, int i) {
        init(ContextCompat.getDrawable(context, i));
    }

    public DividerItemDecoration(Context context, Drawable drawable) {
        init(drawable);
    }

    private void init(Drawable drawable) {
        this.mDivider = drawable;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        if (this.mDivider == null) {
            return;
        }
        if (this.mOrientation == 1) {
            drawVertical(canvas, recyclerView);
        } else {
            drawHorizontal(canvas, recyclerView);
        }
    }

    protected void drawVertical(Canvas canvas, RecyclerView recyclerView) {
        recyclerView.getLayoutManager();
        int paddingLeft = recyclerView.getPaddingLeft();
        int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
        int childCount = recyclerView.getChildCount() - 1;
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            if (hasDivider(childAt)) {
                int bottom = childAt.getBottom();
                this.mDivider.setBounds(paddingLeft, bottom, width, this.mDivider.getIntrinsicHeight() + bottom);
                this.mDivider.draw(canvas);
            }
        }
    }

    protected void drawHorizontal(Canvas canvas, RecyclerView recyclerView) {
        recyclerView.getLayoutManager();
        int paddingTop = recyclerView.getPaddingTop();
        int height = recyclerView.getHeight() - recyclerView.getPaddingBottom();
        int childCount = recyclerView.getChildCount() - 1;
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            if (hasDivider(childAt)) {
                int left = childAt.getLeft();
                this.mDivider.setBounds(left, paddingTop, this.mDivider.getIntrinsicWidth() + left, height);
                this.mDivider.draw(canvas);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        recyclerView.getChildAdapterPosition(view);
        if (this.mDivider == null) {
            rect.set(0, 0, 0, 0);
        } else if (hasDivider(view)) {
            if (this.mOrientation == 1) {
                rect.set(0, 0, 0, this.mDivider.getIntrinsicHeight());
            } else {
                rect.set(0, 0, this.mDivider.getIntrinsicWidth(), 0);
            }
        } else {
            rect.set(0, 0, 0, 0);
        }
    }

    public void setOrientation(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.mOrientation = i;
    }

    protected boolean hasDivider(View view) {
        if (view instanceof Dividable) {
            return ((Dividable) view).hasDivider();
        }
        Boolean bool = (Boolean) view.getTag(R.id.has_divider);
        return bool != null && bool.booleanValue();
    }
}
