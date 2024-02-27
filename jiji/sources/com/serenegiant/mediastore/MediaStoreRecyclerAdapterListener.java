package com.serenegiant.mediastore;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public interface MediaStoreRecyclerAdapterListener {
    void onItemClick(RecyclerView.Adapter<?> adapter, View view, MediaInfo mediaInfo);

    boolean onItemLongClick(RecyclerView.Adapter<?> adapter, View view, MediaInfo mediaInfo);
}
