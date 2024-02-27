package com.serenegiant.view;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;

/* loaded from: classes2.dex */
public class ChainedTouchDelegate extends TouchDelegate {
    private final TouchDelegate mParentTouchDelegate;

    public ChainedTouchDelegate(View view, View view2, Rect rect) {
        super(rect, view2);
        this.mParentTouchDelegate = view.getTouchDelegate();
        view.setTouchDelegate(this);
    }

    @Override // android.view.TouchDelegate
    public boolean onTouchEvent(MotionEvent motionEvent) {
        TouchDelegate touchDelegate;
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        return (onTouchEvent || (touchDelegate = this.mParentTouchDelegate) == null) ? onTouchEvent : touchDelegate.onTouchEvent(motionEvent);
    }

    @Override // android.view.TouchDelegate
    public boolean onTouchExplorationHoverEvent(MotionEvent motionEvent) {
        TouchDelegate touchDelegate;
        boolean onTouchExplorationHoverEvent = super.onTouchExplorationHoverEvent(motionEvent);
        return (onTouchExplorationHoverEvent || (touchDelegate = this.mParentTouchDelegate) == null) ? onTouchExplorationHoverEvent : touchDelegate.onTouchExplorationHoverEvent(motionEvent);
    }
}
