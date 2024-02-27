package com.epson.iprojection.ui.activities.presen.main_image.gesture;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import com.epson.iprojection.common.utils.DipUtils;
import com.epson.iprojection.ui.activities.presen.main_image.EdgeInfo;

/* loaded from: classes.dex */
public class FlickEventCtrlr {
    private static final float FLICK_LENGTH = 30.0f;
    private static final float PI = 3.1415927f;
    private final IOnFlickListener _impl;
    private float _flick_dip = 0.0f;
    private float _scrollLength = 0.0f;
    private final Vector _vector = new Vector();
    private boolean _isEdge = false;
    private boolean _isDisable = false;
    private Vector _pos = new Vector();
    private boolean _isFlicked = false;

    public FlickEventCtrlr(IOnFlickListener iOnFlickListener, Context context) {
        this._impl = iOnFlickListener;
        setPixel2dip(context);
    }

    public void addVector(Vector vector, EdgeInfo edgeInfo) {
        if (this._isDisable) {
            return;
        }
        this._vector.x += vector.x;
        this._vector.y += vector.y;
        if (this._flick_dip < this._scrollLength) {
            if (this._isEdge) {
                float atan2 = (float) Math.atan2(this._vector.y, this._vector.x);
                if (-0.7853982f < atan2 && atan2 < 0.7853982f) {
                    if (edgeInfo.isTouching(EdgeInfo.eDirection.eLeft)) {
                        this._impl.onFlickRight();
                        this._isFlicked = true;
                    }
                } else if ((atan2 < -2.3561945f || 2.3561945f < atan2) && edgeInfo.isTouching(EdgeInfo.eDirection.eRight)) {
                    this._impl.onFlickLeft();
                    this._isFlicked = true;
                }
            }
            this._isDisable = true;
        }
        this._scrollLength += vector.getLength();
    }

    public void setEdge() {
        this._isEdge = true;
    }

    public void clear() {
        this._vector.x = 0.0f;
        this._vector.y = 0.0f;
        this._scrollLength = 0.0f;
        this._isEdge = false;
        this._isDisable = false;
        this._isFlicked = false;
    }

    public boolean onTouch(boolean z, MotionEvent motionEvent, EdgeInfo edgeInfo) {
        if (motionEvent.getPointerCount() > 1) {
            this._isDisable = true;
            return false;
        }
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this._pos = new Vector(motionEvent.getX(), motionEvent.getY());
            clear();
        } else if (action == 1) {
            clear();
        } else if (action == 2) {
            if (motionEvent.getPointerCount() == 1) {
                addVector(new Vector(motionEvent.getX() - this._pos.x, motionEvent.getY() - this._pos.y), edgeInfo);
                this._pos.x = motionEvent.getX();
                this._pos.y = motionEvent.getY();
            }
            if (!z) {
                setEdge();
            }
        }
        return true;
    }

    public boolean isFlicked() {
        return this._isFlicked;
    }

    private void setPixel2dip(Context context) {
        this._flick_dip = DipUtils.getDensity((Activity) context) * FLICK_LENGTH;
    }
}
