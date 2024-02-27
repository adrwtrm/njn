package com.epson.iprojection.ui.activities.presen.thumbnails;

import android.app.Activity;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.common.activity.ActivityGetter;

/* loaded from: classes.dex */
public class ScrollViewMgr {
    private ScrollView _verScrollView = null;
    private HorizontalScrollView _horScrollView = null;
    private LinearLayout _layout = null;
    private Mode _mode = Mode.Vertical;

    /* loaded from: classes.dex */
    enum Mode {
        Vertical,
        Horizontal
    }

    public void setVerticalScrollView(ScrollView scrollView, LinearLayout linearLayout) {
        this._verScrollView = scrollView;
        this._horScrollView = null;
        this._layout = linearLayout;
        this._mode = Mode.Vertical;
    }

    public void setHorizontalScrollView(HorizontalScrollView horizontalScrollView, LinearLayout linearLayout) {
        this._verScrollView = null;
        this._horScrollView = horizontalScrollView;
        this._layout = linearLayout;
        this._mode = Mode.Horizontal;
    }

    public int getVisualCenterViewID() {
        int scrollX;
        int childCount;
        Activity frontActivity = ActivityGetter.getIns().getFrontActivity();
        int thumbWidth = ThumbMgr.getThumbWidth(frontActivity);
        int thumbHeight = ThumbMgr.getThumbHeight(frontActivity) + frontActivity.getResources().getDimensionPixelSize(R.dimen.PresenTumbText);
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$presen$thumbnails$ScrollViewMgr$Mode[this._mode.ordinal()];
        if (i == 1) {
            scrollX = (this._horScrollView.getScrollX() + (this._horScrollView.getWidth() / 2)) / thumbWidth;
            childCount = this._layout.getChildCount();
        } else if (i != 2) {
            scrollX = 0;
            childCount = 0;
        } else {
            scrollX = (this._verScrollView.getScrollY() + (this._verScrollView.getHeight() / 2)) / thumbHeight;
            childCount = this._layout.getChildCount();
        }
        int i2 = scrollX >= 0 ? scrollX : 0;
        return i2 >= childCount ? childCount - 1 : i2;
    }

    /* renamed from: com.epson.iprojection.ui.activities.presen.thumbnails.ScrollViewMgr$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$presen$thumbnails$ScrollViewMgr$Mode;

        static {
            int[] iArr = new int[Mode.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$presen$thumbnails$ScrollViewMgr$Mode = iArr;
            try {
                iArr[Mode.Horizontal.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$presen$thumbnails$ScrollViewMgr$Mode[Mode.Vertical.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public void smoothScrollTo(int i, int i2, int i3, int i4) {
        ScrollView scrollView = this._verScrollView;
        if (scrollView != null) {
            int height = scrollView.getHeight();
            Lg.d(" scrollX=" + this._verScrollView.getScrollX() + " scrollY=" + this._verScrollView.getScrollY());
            this._verScrollView.smoothScrollTo(i, i2 - ((height - i4) / 2));
        }
        if (this._horScrollView != null) {
            this._horScrollView.smoothScrollTo(i - ((this._verScrollView.getWidth() - i3) / 2), i2);
        }
    }
}
