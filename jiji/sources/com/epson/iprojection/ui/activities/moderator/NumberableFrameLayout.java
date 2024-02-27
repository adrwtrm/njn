package com.epson.iprojection.ui.activities.moderator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/* loaded from: classes.dex */
public class NumberableFrameLayout extends FrameLayout {
    public int _id;

    public NumberableFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setWindID(int i) {
        this._id = i;
    }

    public int getWindID() {
        return this._id;
    }
}
