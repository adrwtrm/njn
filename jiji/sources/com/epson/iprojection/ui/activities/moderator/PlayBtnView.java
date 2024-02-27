package com.epson.iprojection.ui.activities.moderator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatImageButton;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickPlayBtn;

/* loaded from: classes.dex */
public class PlayBtnView extends AppCompatImageButton {
    private static final int AVAILABLE = 1;
    private static final int[][] DRAWABLE_ID = {new int[]{R.drawable.play0, R.drawable.play1, R.drawable.play2}, new int[]{R.drawable.stop0, R.drawable.stop1, R.drawable.stop2}, new int[]{R.drawable.pause0, R.drawable.pause1, R.drawable.pause2}};
    private static final int PRESSED = 2;
    private static final int UNAVAILABLE = 0;
    private int _btnID;
    private IOnClickPlayBtn _impl;

    public PlayBtnView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setBtnID(int i) {
        this._btnID = i;
    }

    public void setOnClickPlayBtnListener(IOnClickPlayBtn iOnClickPlayBtn) {
        this._impl = iOnClickPlayBtn;
    }

    public void setSelecting(boolean z) {
        if (z) {
            setBackgroundResource(DRAWABLE_ID[this._btnID][1]);
        } else {
            setBackgroundResource(DRAWABLE_ID[this._btnID][0]);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            setBackgroundResource(DRAWABLE_ID[this._btnID][2]);
        } else if (action == 1 || action == 3) {
            this._impl.onClickPlayBtn(this);
        }
        return super.onTouchEvent(motionEvent);
    }
}
