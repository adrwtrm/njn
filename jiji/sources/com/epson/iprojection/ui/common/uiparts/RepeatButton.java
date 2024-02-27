package com.epson.iprojection.ui.common.uiparts;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;

/* loaded from: classes.dex */
public class RepeatButton extends AppCompatButton implements View.OnLongClickListener {
    private static final int REPEAT_INTERVAL = 100;
    private final Handler _handler;
    private boolean _isRepeat;
    private final Runnable _repeatRunnable;

    public RepeatButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this._isRepeat = true;
        this._repeatRunnable = new Runnable() { // from class: com.epson.iprojection.ui.common.uiparts.RepeatButton.1
            @Override // java.lang.Runnable
            public void run() {
                if (RepeatButton.this._isRepeat) {
                    RepeatButton.this.performClick();
                    RepeatButton.this._handler.postDelayed(this, 100L);
                }
            }
        };
        setOnLongClickListener(this);
        this._handler = new Handler();
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        if (motionEvent.getAction() == 1) {
            this._isRepeat = false;
        }
        return true;
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        this._isRepeat = true;
        this._handler.post(this._repeatRunnable);
        return true;
    }
}
