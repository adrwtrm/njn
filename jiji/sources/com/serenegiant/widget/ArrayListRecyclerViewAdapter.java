package com.serenegiant.widget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import androidx.recyclerview.widget.RecyclerView;
import com.serenegiant.common.R;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class ArrayListRecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolder<T>> {
    private static final boolean DEBUG = false;
    private static final String TAG = "ArrayListRecyclerViewAdapter";
    private ArrayListRecyclerViewListener<T> mCustomRecycleViewListener;
    private final int mItemViewId;
    private final List<T> mItems;
    private LayoutInflater mLayoutInflater;
    private RecyclerView mRecycleView;
    private int mSelectedPosition = -1;
    protected final View.OnClickListener mOnClickListener = new View.OnClickListener() { // from class: com.serenegiant.widget.ArrayListRecyclerViewAdapter.1
        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.view.View.OnClickListener
        public void onClick(final View view) {
            if (ArrayListRecyclerViewAdapter.this.mRecycleView != null) {
                if (view instanceof Checkable) {
                    ((Checkable) view).setChecked(true);
                    view.postDelayed(new Runnable() { // from class: com.serenegiant.widget.ArrayListRecyclerViewAdapter.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ((Checkable) view).setChecked(false);
                        }
                    }, 100L);
                }
                if (ArrayListRecyclerViewAdapter.this.mCustomRecycleViewListener != null) {
                    Object tag = view.getTag(R.id.position);
                    if (tag instanceof Integer) {
                        try {
                            int intValue = ((Integer) tag).intValue();
                            ArrayListRecyclerViewAdapter.this.mCustomRecycleViewListener.onItemClick(ArrayListRecyclerViewAdapter.this, view, intValue, ArrayListRecyclerViewAdapter.this.getItem(intValue));
                            return;
                        } catch (Exception e) {
                            Log.w(ArrayListRecyclerViewAdapter.TAG, e);
                        }
                    }
                    try {
                        int childAdapterPosition = ArrayListRecyclerViewAdapter.this.mRecycleView.getChildAdapterPosition(view);
                        ArrayListRecyclerViewAdapter.this.mCustomRecycleViewListener.onItemClick(ArrayListRecyclerViewAdapter.this, view, childAdapterPosition, ArrayListRecyclerViewAdapter.this.getItem(childAdapterPosition));
                    } catch (Exception e2) {
                        Log.w(ArrayListRecyclerViewAdapter.TAG, e2);
                    }
                }
            }
        }
    };
    protected final View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() { // from class: com.serenegiant.widget.ArrayListRecyclerViewAdapter.2
        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) {
            if (ArrayListRecyclerViewAdapter.this.mRecycleView != null) {
                try {
                    if (ArrayListRecyclerViewAdapter.this.mCustomRecycleViewListener != null) {
                        Object tag = view.getTag(R.id.position);
                        if (!(tag instanceof Integer)) {
                            int childAdapterPosition = ArrayListRecyclerViewAdapter.this.mRecycleView.getChildAdapterPosition(view);
                            return ArrayListRecyclerViewAdapter.this.mCustomRecycleViewListener.onItemLongClick(ArrayListRecyclerViewAdapter.this, view, childAdapterPosition, ArrayListRecyclerViewAdapter.this.getItem(childAdapterPosition));
                        }
                        int intValue = ((Integer) tag).intValue();
                        return ArrayListRecyclerViewAdapter.this.mCustomRecycleViewListener.onItemLongClick(ArrayListRecyclerViewAdapter.this, view, intValue, ArrayListRecyclerViewAdapter.this.getItem(intValue));
                    }
                    return false;
                } catch (Exception e) {
                    Log.w(ArrayListRecyclerViewAdapter.TAG, e);
                    return false;
                }
            }
            return false;
        }
    };
    protected final View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() { // from class: com.serenegiant.widget.ArrayListRecyclerViewAdapter.3
        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z) {
            if (!z) {
                ArrayListRecyclerViewAdapter.this.mSelectedPosition = -1;
            } else if (z && ArrayListRecyclerViewAdapter.this.mRecycleView != null) {
                try {
                    Object tag = view.getTag(R.id.position);
                    if (tag instanceof Integer) {
                        int i = ArrayListRecyclerViewAdapter.this.mSelectedPosition = ((Integer) tag).intValue();
                        if (ArrayListRecyclerViewAdapter.this.mCustomRecycleViewListener != null) {
                            ArrayListRecyclerViewAdapter.this.mCustomRecycleViewListener.onItemSelected(ArrayListRecyclerViewAdapter.this, view, i, ArrayListRecyclerViewAdapter.this.getItem(i));
                            return;
                        }
                        return;
                    }
                    ArrayListRecyclerViewAdapter arrayListRecyclerViewAdapter = ArrayListRecyclerViewAdapter.this;
                    int i2 = arrayListRecyclerViewAdapter.mSelectedPosition = arrayListRecyclerViewAdapter.mRecycleView.getChildAdapterPosition(view);
                    Object item = ArrayListRecyclerViewAdapter.this.getItem(i2);
                    if (ArrayListRecyclerViewAdapter.this.mCustomRecycleViewListener != null) {
                        ArrayListRecyclerViewAdapter.this.mCustomRecycleViewListener.onItemSelected(ArrayListRecyclerViewAdapter.this, view, i2, item);
                        return;
                    }
                    return;
                } catch (Exception e) {
                    Log.w(ArrayListRecyclerViewAdapter.TAG, e);
                }
            }
            if (ArrayListRecyclerViewAdapter.this.mSelectedPosition >= 0 || ArrayListRecyclerViewAdapter.this.mCustomRecycleViewListener == null) {
                return;
            }
            ArrayListRecyclerViewAdapter.this.mCustomRecycleViewListener.onNothingSelected(ArrayListRecyclerViewAdapter.this);
        }
    };

    /* loaded from: classes2.dex */
    public interface ArrayListRecyclerViewListener<T> {
        void onItemClick(RecyclerView.Adapter<?> adapter, View view, int i, T t);

        boolean onItemLongClick(RecyclerView.Adapter<?> adapter, View view, int i, T t);

        void onItemSelected(RecyclerView.Adapter<?> adapter, View view, int i, T t);

        void onNothingSelected(RecyclerView.Adapter<?> adapter);
    }

    protected abstract void registerDataSetObserver(List<T> list);

    protected abstract void unregisterDataSetObserver(List<T> list);

    public ArrayListRecyclerViewAdapter(int i, List<T> list) {
        this.mItemViewId = i;
        this.mItems = list;
        synchronized (list) {
            registerDataSetObserver(list);
        }
    }

    protected void finalize() throws Throwable {
        synchronized (this.mItems) {
            unregisterDataSetObserver(this.mItems);
        }
        super.finalize();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mSelectedPosition = -1;
        this.mRecycleView = recyclerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.mRecycleView = null;
        this.mSelectedPosition = -1;
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder<T> onCreateViewHolder(ViewGroup viewGroup, int i) {
        View onCreateItemView = onCreateItemView(getLayoutInflater(viewGroup.getContext()), viewGroup, i);
        onCreateItemView.setOnClickListener(this.mOnClickListener);
        onCreateItemView.setOnLongClickListener(this.mOnLongClickListener);
        onCreateItemView.setOnFocusChangeListener(this.mOnFocusChangeListener);
        return onCreateViewHolder(onCreateItemView);
    }

    protected View onCreateItemView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return layoutInflater.inflate(this.mItemViewId, viewGroup, false);
    }

    protected ViewHolder<T> onCreateViewHolder(View view) {
        return new ViewHolder<>(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mItems.size();
    }

    public int getSelectedPosition() {
        return this.mSelectedPosition;
    }

    public List<T> getItems() {
        return Collections.unmodifiableList(this.mItems);
    }

    public T getItem(int i) throws IndexOutOfBoundsException {
        if (i >= 0 && i < this.mItems.size()) {
            return this.mItems.get(i);
        }
        throw new IndexOutOfBoundsException();
    }

    public void setOnItemClickListener(ArrayListRecyclerViewListener<T> arrayListRecyclerViewListener) {
        this.mCustomRecycleViewListener = arrayListRecyclerViewListener;
    }

    public RecyclerView getParent() {
        return this.mRecycleView;
    }

    public void clear() {
        synchronized (this.mItems) {
            unregisterDataSetObserver(this.mItems);
            this.mItems.clear();
        }
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends T> collection) {
        int size;
        int size2;
        synchronized (this.mItems) {
            size = this.mItems.size() - 1;
            unregisterDataSetObserver(this.mItems);
            this.mItems.addAll(collection);
            size2 = this.mItems.size() - 1;
            registerDataSetObserver(this.mItems);
        }
        if (size2 > size) {
            notifyItemRangeChanged(size, size2 - size);
        }
    }

    public void replaceAll(Collection<? extends T> collection) {
        synchronized (this.mItems) {
            unregisterDataSetObserver(this.mItems);
            this.mItems.clear();
            this.mItems.addAll(collection);
            registerDataSetObserver(this.mItems);
        }
        notifyDataSetChanged();
    }

    public void sort(Comparator<? super T> comparator) {
        synchronized (this.mItems) {
            Collections.sort(this.mItems, comparator);
        }
        notifyDataSetChanged();
    }

    protected LayoutInflater getLayoutInflater(Context context) {
        if (this.mLayoutInflater == null) {
            this.mLayoutInflater = LayoutInflater.from(context);
        }
        return this.mLayoutInflater;
    }

    /* loaded from: classes2.dex */
    public static class ViewHolder<T> extends RecyclerView.ViewHolder {
        public T mItem;
        public final View mView;
        public int position;

        public ViewHolder(View view) {
            super(view);
            this.mView = view;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ViewHolder
        public String toString() {
            return super.toString() + " '" + this.mItem + "'";
        }

        public void setEnable(boolean z) {
            this.mView.setEnabled(z);
        }

        public void hasDivider(boolean z) {
            View view = this.mView;
            if (view instanceof Dividable) {
                ((Dividable) view).hasDivider(z);
            } else {
                view.setTag(R.id.has_divider, Boolean.valueOf(z));
            }
        }

        public boolean hasDivider() {
            View view = this.mView;
            if (view instanceof Dividable) {
                return ((Dividable) view).hasDivider();
            }
            Boolean bool = (Boolean) view.getTag(R.id.has_divider);
            return bool != null && bool.booleanValue();
        }
    }
}
