package com.epson.iprojection.ui.activities.presen;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/* loaded from: classes.dex */
public class TouchControlFrameLayout extends FrameLayout {
    private boolean _isTouchable;

    public TouchControlFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this._isTouchable = true;
    }

    public void touchable() {
        this._isTouchable = true;
    }

    public void untouchable() {
        this._isTouchable = false;
    }

    public boolean isTouchable() {
        return this._isTouchable;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this._isTouchable) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }
}
