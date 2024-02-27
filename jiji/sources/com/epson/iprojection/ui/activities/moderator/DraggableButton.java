package com.epson.iprojection.ui.activities.moderator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.ui.activities.moderator.WindowMgr;

/* loaded from: classes.dex */
public class DraggableButton extends AppCompatButton {
    private final Vibrator _vib;
    private int _x;
    private int _y;

    public DraggableButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this._vib = (Vibrator) context.getSystemService("vibrator");
        setOnLongClickListener(new View.OnLongClickListener() { // from class: com.epson.iprojection.ui.activities.moderator.DraggableButton.1
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                if (DraggableButton.this._vib != null) {
                    MethodUtil.compatVibrate(DraggableButton.this._vib, 100L);
                }
                MethodUtil.compatStartDragAndDrop(null, new View.DragShadowBuilder(DraggableButton.this) { // from class: com.epson.iprojection.ui.activities.moderator.DraggableButton.1.1
                    @Override // android.view.View.DragShadowBuilder
                    public void onProvideShadowMetrics(Point point, Point point2) {
                        super.onProvideShadowMetrics(point, point2);
                        point2.x = DraggableButton.this._x;
                        point2.y = DraggableButton.this._y;
                    }

                    @Override // android.view.View.DragShadowBuilder
                    public void onDrawShadow(Canvas canvas) {
                        super.onDrawShadow(canvas);
                    }
                }, DraggableButton.this, 0);
                return true;
            }
        });
    }

    /* renamed from: com.epson.iprojection.ui.activities.moderator.DraggableButton$2  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$moderator$WindowMgr$eStatus;

        static {
            int[] iArr = new int[WindowMgr.eStatus.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$moderator$WindowMgr$eStatus = iArr;
            try {
                iArr[WindowMgr.eStatus.eActive.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$moderator$WindowMgr$eStatus[WindowMgr.eStatus.eInactive.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$moderator$WindowMgr$eStatus[WindowMgr.eStatus.eNoSelect.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public void setStatus(WindowMgr.eStatus estatus) {
        int i = AnonymousClass2.$SwitchMap$com$epson$iprojection$ui$activities$moderator$WindowMgr$eStatus[estatus.ordinal()];
        if (i == 1) {
            super.setBackgroundResource(R.drawable.me_btnback_blue);
        } else if (i == 2) {
            super.setBackgroundResource(R.drawable.me_btnback_gray);
        } else if (i != 3) {
        } else {
            super.setBackgroundResource(R.drawable.me_btnback_white);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this._x = (int) motionEvent.getX();
        this._y = (int) motionEvent.getY();
        return super.onTouchEvent(motionEvent);
    }
}
