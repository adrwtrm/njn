package com.serenegiant.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.serenegiant.common.R;
import com.serenegiant.view.ViewUtils;

/* loaded from: classes2.dex */
public class RecyclerViewWithEmptyView extends RecyclerView {
    private static final boolean DEBUG = false;
    private static final String TAG = "RecyclerViewWithEmptyView";
    private final RecyclerView.AdapterDataObserver mAdapterDataObserver;
    private View mEmptyView;
    private int mEmptyViewId;

    public RecyclerViewWithEmptyView(Context context) {
        this(context, null, 0);
    }

    public RecyclerViewWithEmptyView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RecyclerViewWithEmptyView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAdapterDataObserver = new RecyclerView.AdapterDataObserver() { // from class: com.serenegiant.widget.RecyclerViewWithEmptyView.2
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                super.onChanged();
                RecyclerViewWithEmptyView.this.updateEmptyView();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i2, int i3) {
                super.onItemRangeChanged(i2, i3);
                RecyclerViewWithEmptyView.this.updateEmptyView();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeRemoved(int i2, int i3) {
                super.onItemRangeRemoved(i2, i3);
                RecyclerViewWithEmptyView.this.updateEmptyView();
            }
        };
        Drawable drawable = null;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.RecyclerViewWithEmptyView, i, 0);
            try {
                if (obtainStyledAttributes.hasValue(R.styleable.RecyclerViewWithEmptyView_listDivider)) {
                    drawable = obtainStyledAttributes.getDrawable(R.styleable.RecyclerViewWithEmptyView_listDivider);
                }
            } catch (Exception unused) {
            }
            try {
                this.mEmptyViewId = obtainStyledAttributes.getResourceId(R.styleable.RecyclerViewWithEmptyView_emptyView, -1);
            } catch (Exception unused2) {
            }
            obtainStyledAttributes.recycle();
        }
        int orientation = getLayoutManager() instanceof LinearLayoutManager ? ((LinearLayoutManager) getLayoutManager()).getOrientation() : 1;
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, drawable);
        dividerItemDecoration.setOrientation(orientation);
        addItemDecoration(dividerItemDecoration);
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (getAdapter() != adapter) {
            try {
                if (getAdapter() != null) {
                    getAdapter().unregisterAdapterDataObserver(this.mAdapterDataObserver);
                }
            } catch (Exception unused) {
            }
            super.setAdapter(adapter);
            if (adapter != null) {
                adapter.registerAdapterDataObserver(this.mAdapterDataObserver);
            }
        }
        updateEmptyView();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mEmptyView == null) {
            setEmptyView(ViewUtils.findViewInParent(this, new int[]{this.mEmptyViewId, R.id.empty, 16908292}, View.class));
        }
    }

    public void setEmptyView(View view) {
        if (this.mEmptyView != view) {
            this.mEmptyView = view;
            updateEmptyView();
        }
    }

    protected void updateEmptyView() {
        if (this.mEmptyView == null || isInEditMode()) {
            return;
        }
        final RecyclerView.Adapter adapter = getAdapter();
        post(new Runnable() { // from class: com.serenegiant.widget.RecyclerViewWithEmptyView.1
            @Override // java.lang.Runnable
            public void run() {
                View view = RecyclerViewWithEmptyView.this.mEmptyView;
                RecyclerView.Adapter adapter2 = adapter;
                view.setVisibility((adapter2 == null || adapter2.getItemCount() == 0) ? 0 : 8);
            }
        });
    }
}
