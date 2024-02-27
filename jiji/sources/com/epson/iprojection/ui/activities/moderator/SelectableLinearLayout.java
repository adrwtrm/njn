package com.epson.iprojection.ui.activities.moderator;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.MethodUtil;

/* loaded from: classes.dex */
public class SelectableLinearLayout extends LinearLayout {
    private boolean _isDisplaying;
    private long _uniqueID;

    public SelectableLinearLayout(Context context) {
        this(context, null);
    }

    public SelectableLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this._isDisplaying = false;
        this._uniqueID = -1L;
    }

    public void clear() {
        this._isDisplaying = false;
        this._uniqueID = -1L;
        clearBackground();
    }

    public void setUniqueID(long j) {
        this._uniqueID = j;
    }

    public long getUniqueID() {
        return this._uniqueID;
    }

    public void setDisplay(boolean z, boolean z2) {
        if (z) {
            return;
        }
        if (z2) {
            setBackgroundColor(MethodUtil.compatGetColor(getContext(), R.color.Indigo200));
        } else {
            setBackgroundColor(Color.argb(255, 210, 210, 210));
        }
        this._isDisplaying = true;
    }

    public void cancelDisplay() {
        setBackgroundColor(-1);
        this._isDisplaying = false;
    }

    public boolean isDisplaying() {
        return this._isDisplaying;
    }

    public void clearBackground() {
        setBackgroundColor(-1);
    }
}
