package com.serenegiant.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.serenegiant.view.ViewUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class ValueSelectorAdapter extends ArrayAdapter<ValueEntry> {
    private static final boolean DEBUG = false;
    private static final String TAG = "ValueSelectorAdapter";
    private final LayoutInflater mInflater;
    private final int mLayoutId;
    private final ValueSelectorAdapterListener mListener;
    private final View.OnTouchListener mOnTouchListener;
    private final int mTitleId;

    /* loaded from: classes2.dex */
    public interface ValueSelectorAdapterListener {
        void onTouch(View view, MotionEvent motionEvent, int i);
    }

    /* loaded from: classes2.dex */
    public static class ValueEntry {
        public final String title;
        public final String value;

        private ValueEntry(String str, String str2) {
            this.title = str;
            this.value = str2;
        }
    }

    private static List<ValueEntry> createEntries(Context context, int i, int i2) {
        String[] stringArray = context.getResources().getStringArray(i);
        String[] stringArray2 = context.getResources().getStringArray(i2);
        int min = Math.min(stringArray != null ? stringArray.length : 0, stringArray2 != null ? stringArray2.length : 0);
        ArrayList arrayList = new ArrayList(min);
        for (int i3 = 0; i3 < min; i3++) {
            arrayList.add(new ValueEntry(stringArray[i3], stringArray2[i3]));
        }
        return arrayList;
    }

    public ValueSelectorAdapter(Context context, int i, int i2, int i3, int i4, ValueSelectorAdapterListener valueSelectorAdapterListener) {
        super(context, i, createEntries(context, i3, i4));
        this.mOnTouchListener = new View.OnTouchListener() { // from class: com.serenegiant.widget.ValueSelectorAdapter.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (ValueSelectorAdapter.this.mListener != null) {
                    ViewHolder viewHolder = (ViewHolder) view.getTag();
                    try {
                        ValueSelectorAdapter.this.mListener.onTouch(view, motionEvent, viewHolder != null ? viewHolder.position : -1);
                        return false;
                    } catch (Exception unused) {
                        return false;
                    }
                }
                return false;
            }
        };
        this.mInflater = LayoutInflater.from(context);
        this.mLayoutId = i;
        this.mTitleId = i2;
        this.mListener = valueSelectorAdapterListener;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.mInflater.inflate(this.mLayoutId, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.titleTv = ViewUtils.findTitleView(view);
            view.setOnTouchListener(this.mOnTouchListener);
            view.setTag(viewHolder);
        }
        ViewHolder viewHolder2 = (ViewHolder) view.getTag();
        ValueEntry item = getItem(i);
        if (item != null && viewHolder2.titleTv != null) {
            viewHolder2.titleTv.setText(item.title);
        }
        viewHolder2.position = i;
        return view;
    }

    @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return getView(i, view, viewGroup);
    }

    public int getPosition(int i) {
        String num = Integer.toString(i);
        int count = getCount();
        for (int i2 = 0; i2 < count; i2++) {
            if (num.equals(getItem(i2).value)) {
                return i2;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class ViewHolder {
        int position;
        TextView titleTv;

        private ViewHolder() {
        }
    }
}
