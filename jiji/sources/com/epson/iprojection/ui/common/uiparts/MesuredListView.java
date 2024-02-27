package com.epson.iprojection.ui.common.uiparts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

/* loaded from: classes.dex */
public class MesuredListView extends ListView {
    private static final int MAX_SIZE = 99;

    public MesuredListView(Context context) {
        super(context);
    }

    public MesuredListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MesuredListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode != 1073741824) {
            ListAdapter adapter = getAdapter();
            int i3 = 0;
            if (adapter != null && !adapter.isEmpty()) {
                int i4 = 0;
                while (i3 < adapter.getCount() && i3 < 99) {
                    View view = adapter.getView(i3, null, this);
                    if (view instanceof ViewGroup) {
                        view.setLayoutParams(new AbsListView.LayoutParams(-2, -2));
                    }
                    view.measure(i, i2);
                    i4 += view.getMeasuredHeight();
                    i3++;
                }
                i3 = i4 + (getDividerHeight() * i3);
            }
            if (mode != Integer.MIN_VALUE || i3 <= size || i3 <= size) {
                size = i3;
            }
        } else {
            size = getMeasuredHeight();
        }
        setMeasuredDimension(getMeasuredWidth(), size);
    }
}
