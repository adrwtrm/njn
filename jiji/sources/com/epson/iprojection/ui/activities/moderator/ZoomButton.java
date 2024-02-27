package com.epson.iprojection.ui.activities.moderator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatImageButton;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.moderator.ButtonMgrThumbnail;

/* loaded from: classes.dex */
public class ZoomButton extends AppCompatImageButton {
    private int _nowSetResourceID;

    public ZoomButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this._nowSetResourceID = R.drawable.mpp_zoomin;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            onTouchDown();
        } else if (action == 1 || action == 3) {
            onTouchUp();
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setSize(ButtonMgrThumbnail.eSize esize) {
        if (esize == ButtonMgrThumbnail.eSize.LARGE) {
            setImageResource(R.drawable.mpp_zoomout);
        }
        if (esize == ButtonMgrThumbnail.eSize.SMALL) {
            setImageResource(R.drawable.mpp_zoomin);
        }
    }

    private void onTouchUp() {
        int i = this._nowSetResourceID;
        if (i == R.drawable.mpp_zoomout_select) {
            setImageResource(R.drawable.mpp_zoomout);
        } else if (i == R.drawable.mpp_zoomin_select) {
            setImageResource(R.drawable.mpp_zoomin);
        }
    }

    private void onTouchDown() {
        int i = this._nowSetResourceID;
        if (i == R.drawable.mpp_zoomout) {
            setImageResource(R.drawable.mpp_zoomout_select);
        } else if (i == R.drawable.mpp_zoomin) {
            setImageResource(R.drawable.mpp_zoomin_select);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatImageButton, android.widget.ImageView
    public void setImageResource(int i) {
        super.setImageResource(i);
        this._nowSetResourceID = i;
    }
}
