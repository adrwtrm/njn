package com.epson.iprojection.ui.activities.remote.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes.dex */
public class IgnoreSwipeViewPager extends ViewPager {
    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public IgnoreSwipeViewPager(Context context) {
        super(context);
    }

    public IgnoreSwipeViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
