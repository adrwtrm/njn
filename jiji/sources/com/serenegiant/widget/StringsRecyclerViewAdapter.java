package com.serenegiant.widget;

import android.widget.TextView;
import com.serenegiant.view.ViewUtils;
import com.serenegiant.widget.ArrayListRecyclerViewAdapter;
import java.util.List;

/* loaded from: classes2.dex */
public class StringsRecyclerViewAdapter extends ArrayListRecyclerViewAdapter<String> {
    private static final boolean DEBUG = false;
    private static final String TAG = "StringsRecyclerViewAdapter";

    @Override // com.serenegiant.widget.ArrayListRecyclerViewAdapter
    protected void registerDataSetObserver(List<String> list) {
    }

    @Override // com.serenegiant.widget.ArrayListRecyclerViewAdapter
    protected void unregisterDataSetObserver(List<String> list) {
    }

    public StringsRecyclerViewAdapter(int i, List<String> list) {
        super(i, list);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [T, java.lang.Object] */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ArrayListRecyclerViewAdapter.ViewHolder<String> viewHolder, int i) {
        viewHolder.mItem = getItem(i);
        TextView findTitleView = ViewUtils.findTitleView(viewHolder.mView);
        if (findTitleView != null) {
            findTitleView.setText(viewHolder.mItem);
            return;
        }
        throw new RuntimeException("TextView not found!");
    }
}
