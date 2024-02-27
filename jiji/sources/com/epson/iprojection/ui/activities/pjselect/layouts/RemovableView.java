package com.epson.iprojection.ui.activities.pjselect.layouts;

import android.content.Context;
import android.util.AttributeSet;
import androidx.drawerlayout.widget.DrawerLayout;

/* loaded from: classes.dex */
public abstract class RemovableView extends DrawerLayout {
    public abstract boolean pauseView(boolean z);

    public abstract void resumeView();

    public RemovableView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
