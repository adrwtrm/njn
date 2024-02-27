package com.serenegiant.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.tabs.TabLayout;

/* loaded from: classes2.dex */
public class CenteredTabLayout extends TabLayout {
    private static final boolean DEBUG = false;
    private static final String TAG = "CenteredTabLayout";
    private final GestureDetectorCompat mGestureDetector;
    private boolean mIsLocked;

    public CenteredTabLayout(Context context) {
        this(context, null, 0);
    }

    public CenteredTabLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CenteredTabLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsLocked = false;
        super.setTabMode(0);
        this.mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.serenegiant.widget.CenteredTabLayout.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (CenteredTabLayout.this.mIsLocked) {
                    return false;
                }
                final int selectedTabPosition = CenteredTabLayout.this.getSelectedTabPosition();
                if (f < 0.0f) {
                    if (selectedTabPosition < CenteredTabLayout.this.getTabCount() - 1) {
                        CenteredTabLayout.this.post(new Runnable() { // from class: com.serenegiant.widget.CenteredTabLayout.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                CenteredTabLayout.this.selectTab(CenteredTabLayout.this.getTabAt(selectedTabPosition + 1), true);
                            }
                        });
                        return false;
                    }
                    return false;
                } else if (selectedTabPosition > 0) {
                    CenteredTabLayout.this.post(new Runnable() { // from class: com.serenegiant.widget.CenteredTabLayout.1.2
                        @Override // java.lang.Runnable
                        public void run() {
                            CenteredTabLayout.this.selectTab(CenteredTabLayout.this.getTabAt(selectedTabPosition - 1), true);
                        }
                    });
                    return false;
                } else {
                    return false;
                }
            }
        });
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        View childAt = getChildAt(0);
        ViewGroup viewGroup = childAt instanceof ViewGroup ? (ViewGroup) childAt : null;
        int childCount = viewGroup != null ? viewGroup.getChildCount() : 0;
        if (getTabMode() == 0 && childCount > 0) {
            ViewCompat.setPaddingRelative(viewGroup, (getWidth() / 2) - (viewGroup.getChildAt(0).getWidth() / 2), 0, (getWidth() / 2) - (viewGroup.getChildAt(childCount - 1).getWidth() / 2), 0);
        } else {
            ViewCompat.setPaddingRelative(viewGroup, 0, 0, 0, 0);
        }
    }

    @Override // com.google.android.material.tabs.TabLayout, android.widget.HorizontalScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override // com.google.android.material.tabs.TabLayout
    public void selectTab(TabLayout.Tab tab, boolean z) {
        TabLayout.Tab selectedTab = getSelectedTab();
        if (!this.mIsLocked || (tab != null && tab.equals(selectedTab))) {
            super.selectTab(tab, z);
        }
    }

    public TabLayout.Tab getSelectedTab() {
        return getTabAt(getSelectedTabPosition());
    }

    public void setLock(boolean z) {
        this.mIsLocked = z;
        postInvalidate();
    }

    public boolean getLock() {
        return this.mIsLocked;
    }

    @Override // com.google.android.material.tabs.TabLayout
    public void setTabMode(int i) {
        throw new UnsupportedOperationException("CenteredTabLayout does not support #setTabMode.");
    }
}
