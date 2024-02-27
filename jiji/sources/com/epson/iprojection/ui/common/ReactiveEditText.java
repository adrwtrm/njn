package com.epson.iprojection.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import androidx.appcompat.widget.AppCompatEditText;

/* loaded from: classes.dex */
public class ReactiveEditText extends AppCompatEditText {
    public ReactiveEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        if (i == 4 && keyEvent.getAction() == 1) {
            onFocusChanged(false, i, null);
        }
        return super.onKeyPreIme(i, keyEvent);
    }
}
