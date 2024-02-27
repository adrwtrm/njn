package com.epson.iprojection.ui.activities.moderator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatImageView;

/* loaded from: classes.dex */
public class TouchPosGettableImageView extends AppCompatImageView {
    private int _windID;
    private float _x;
    private float _y;

    public TouchPosGettableImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public int getTouchX() {
        return (int) this._x;
    }

    public int getTouchY() {
        return (int) this._y;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this._x = motionEvent.getX();
        this._y = motionEvent.getY();
        return super.onTouchEvent(motionEvent);
    }

    public void setWindID(int i) {
        this._windID = i;
    }

    public int getWindID() {
        return this._windID;
    }
}
