package com.epson.iprojection.ui.activities.remote;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class DisappearableDialog extends Dialog {
    public DisappearableDialog(Context context, int i) {
        super(context, i);
    }

    @Override // android.app.Dialog
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            super.dismiss();
            return false;
        }
        return false;
    }
}
