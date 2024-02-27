package com.serenegiant.mediastore;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.serenegiant.common.R;
import com.serenegiant.graphics.BitmapHelper;
import com.serenegiant.utils.ThreadPool;
import com.serenegiant.view.ViewUtils;
import java.io.IOException;

/* loaded from: classes2.dex */
public class MediaStoreCursorRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaStoreCursorRecyclerAdapter";
    private final MediaInfo info;
    private ChangeObserver mChangeObserver;
    private final Context mContext;
    private Cursor mCursor;
    private DataSetObserver mDataSetObserver;
    private boolean mDataValid;
    private final LayoutInflater mInflater;
    private final int mLayoutId;
    private MediaStoreRecyclerAdapterListener mListener;
    private int mMediaType;
    private boolean mNeedValidate;
    protected final View.OnClickListener mOnClickListener;
    protected final View.OnLongClickListener mOnLongClickListener;
    private final MyAsyncQueryHandler mQueryHandler;
    private RecyclerView mRecycleView;
    private String mSelection;
    private String[] mSelectionArgs;
    private boolean mShowTitle;
    private String mSortOrder;
    private final Object mSync;
    private final ThumbnailCache mThumbnailCache;
    private int mThumbnailHeight;
    private int mThumbnailWidth;
    private final Handler mUIHandler;

    public void notifyDataSetInvalidated() {
    }

    public MediaStoreCursorRecyclerAdapter(Context context, int i) {
        this(context, i, true);
    }

    public MediaStoreCursorRecyclerAdapter(Context context, int i, boolean z) {
        this.mSync = new Object();
        this.mUIHandler = new Handler(Looper.getMainLooper());
        this.info = new MediaInfo();
        this.mSelectionArgs = null;
        this.mSortOrder = null;
        this.mShowTitle = false;
        this.mMediaType = 0;
        this.mThumbnailWidth = 200;
        this.mThumbnailHeight = 200;
        this.mOnClickListener = new View.OnClickListener() { // from class: com.serenegiant.mediastore.MediaStoreCursorRecyclerAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(final View view) {
                MediaInfo mediaInfo;
                if (MediaStoreCursorRecyclerAdapter.this.mRecycleView == null || !MediaStoreCursorRecyclerAdapter.this.mRecycleView.isEnabled()) {
                    Log.w(MediaStoreCursorRecyclerAdapter.TAG, "onClick:mRecycleView=" + MediaStoreCursorRecyclerAdapter.this.mRecycleView);
                    return;
                }
                if (view instanceof Checkable) {
                    ((Checkable) view).setChecked(true);
                    MediaStoreCursorRecyclerAdapter.this.mUIHandler.postDelayed(new Runnable() { // from class: com.serenegiant.mediastore.MediaStoreCursorRecyclerAdapter.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ((Checkable) view).setChecked(false);
                        }
                    }, 100L);
                }
                if (MediaStoreCursorRecyclerAdapter.this.mListener == null || (mediaInfo = (MediaInfo) view.getTag(R.id.info)) == null) {
                    return;
                }
                try {
                    MediaStoreCursorRecyclerAdapter.this.mListener.onItemClick(MediaStoreCursorRecyclerAdapter.this, view, mediaInfo);
                } catch (Exception e) {
                    Log.w(MediaStoreCursorRecyclerAdapter.TAG, e);
                }
            }
        };
        this.mOnLongClickListener = new View.OnLongClickListener() { // from class: com.serenegiant.mediastore.MediaStoreCursorRecyclerAdapter.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                MediaInfo mediaInfo;
                if (MediaStoreCursorRecyclerAdapter.this.mRecycleView == null || !MediaStoreCursorRecyclerAdapter.this.mRecycleView.isEnabled() || MediaStoreCursorRecyclerAdapter.this.mListener == null || (mediaInfo = (MediaInfo) view.getTag(R.id.info)) == null) {
                    return false;
                }
                try {
                    return MediaStoreCursorRecyclerAdapter.this.mListener.onItemLongClick(MediaStoreCursorRecyclerAdapter.this, view, mediaInfo);
                } catch (Exception e) {
                    Log.w(MediaStoreCursorRecyclerAdapter.TAG, e);
                    return false;
                }
            }
        };
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mLayoutId = i;
        this.mQueryHandler = new MyAsyncQueryHandler(context.getContentResolver(), this);
        this.mThumbnailCache = new ThumbnailCache(context);
        this.mNeedValidate = true;
        if (z) {
            refresh();
        }
    }

    protected void finalize() throws Throwable {
        try {
            releaseCursor();
        } finally {
            super.finalize();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecycleView = recyclerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.mRecycleView = null;
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = this.mInflater.inflate(this.mLayoutId, viewGroup, false);
        inflate.setOnClickListener(this.mOnClickListener);
        inflate.setOnLongClickListener(this.mOnLongClickListener);
        return new ViewHolder(inflate);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        setInfo(viewHolder, getMediaInfo(i, this.info));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        int count;
        synchronized (this.mSync) {
            Cursor cursor = this.mCursor;
            count = cursor != null ? cursor.getCount() : 0;
        }
        return count;
    }

    public MediaInfo getItem(int i) {
        return getMediaInfo(i, null);
    }

    public void setValidateItems(boolean z) {
        if (this.mNeedValidate != z) {
            this.mNeedValidate = z;
        }
    }

    public void setListener(MediaStoreRecyclerAdapterListener mediaStoreRecyclerAdapterListener) {
        this.mListener = mediaStoreRecyclerAdapterListener;
    }

    public void refresh() {
        ThreadPool.preStartAllCoreThreads();
        this.mQueryHandler.requery();
    }

    public void setThumbnailSize(int i) {
        setThumbnailSize(i, i);
    }

    public void setThumbnailSize(int i, int i2) {
        if (this.mThumbnailWidth == i && this.mThumbnailHeight == i2) {
            return;
        }
        this.mThumbnailWidth = i;
        this.mThumbnailHeight = i2;
        this.mThumbnailCache.clear();
        onContentChanged();
    }

    public void setShowTitle(boolean z) {
        if (this.mShowTitle != z) {
            this.mShowTitle = z;
            onContentChanged();
        }
    }

    public boolean getShowTitle() {
        return this.mShowTitle;
    }

    public int getMediaType() {
        return this.mMediaType % 3;
    }

    public void setMediaType(int i) {
        int i2 = i % 3;
        if (this.mMediaType != i2) {
            this.mMediaType = i2;
            onContentChanged();
        }
    }

    private MediaInfo getMediaInfo(int i, MediaInfo mediaInfo) {
        if (mediaInfo == null) {
            mediaInfo = new MediaInfo();
        }
        synchronized (this.mSync) {
            Cursor cursor = this.mCursor;
            if (cursor == null) {
                throw new IllegalStateException("Cursor is not ready!");
            }
            if (cursor.moveToPosition(i)) {
                mediaInfo.loadFromCursor(this.mCursor);
            }
        }
        return mediaInfo;
    }

    protected void onContentChanged() {
        this.mQueryHandler.requery();
    }

    protected void releaseCursor() {
        Cursor swapCursor = swapCursor(null);
        if (swapCursor == null || swapCursor.isClosed()) {
            return;
        }
        swapCursor.close();
    }

    protected Cursor getCursor(int i) {
        Cursor cursor;
        if (!this.mDataValid || (cursor = this.mCursor) == null) {
            return null;
        }
        cursor.moveToPosition(i);
        return this.mCursor;
    }

    protected Cursor swapCursor(Cursor cursor) {
        Cursor cursor2 = this.mCursor;
        if (cursor == cursor2) {
            return null;
        }
        if (cursor2 != null) {
            ChangeObserver changeObserver = this.mChangeObserver;
            if (changeObserver != null) {
                cursor2.unregisterContentObserver(changeObserver);
            }
            DataSetObserver dataSetObserver = this.mDataSetObserver;
            if (dataSetObserver != null) {
                cursor2.unregisterDataSetObserver(dataSetObserver);
            }
        }
        synchronized (this.mSync) {
            this.mCursor = cursor;
        }
        if (cursor != null) {
            ChangeObserver changeObserver2 = this.mChangeObserver;
            if (changeObserver2 != null) {
                cursor.registerContentObserver(changeObserver2);
            }
            DataSetObserver dataSetObserver2 = this.mDataSetObserver;
            if (dataSetObserver2 != null) {
                cursor.registerDataSetObserver(dataSetObserver2);
            }
            this.mDataValid = true;
            notifyDataSetChanged();
        } else {
            this.mDataValid = false;
            notifyDataSetInvalidated();
        }
        return cursor2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class MyAsyncQueryHandler extends AsyncQueryHandler {
        private final MediaStoreCursorRecyclerAdapter mAdapter;

        public MyAsyncQueryHandler(ContentResolver contentResolver, MediaStoreCursorRecyclerAdapter mediaStoreCursorRecyclerAdapter) {
            super(contentResolver);
            this.mAdapter = mediaStoreCursorRecyclerAdapter;
        }

        public void requery() {
            synchronized (this.mAdapter.mSync) {
                if (this.mAdapter.mCursor != null) {
                    this.mAdapter.mCursor.close();
                    this.mAdapter.mCursor = null;
                }
                this.mAdapter.mSelection = MediaStoreUtils.SELECTIONS[this.mAdapter.mMediaType % 3];
                this.mAdapter.mSelectionArgs = null;
                startQuery(0, this.mAdapter, MediaStoreUtils.QUERY_URI_FILES, MediaStoreUtils.PROJ_MEDIA, this.mAdapter.mSelection, this.mAdapter.mSelectionArgs, this.mAdapter.mSortOrder);
            }
        }

        @Override // android.content.AsyncQueryHandler
        protected void onQueryComplete(int i, Object obj, Cursor cursor) {
            super.onQueryComplete(i, obj, cursor);
            Cursor swapCursor = this.mAdapter.swapCursor(cursor);
            if (swapCursor == null || swapCursor.isClosed()) {
                return;
            }
            swapCursor.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class ChangeObserver extends ContentObserver {
        @Override // android.database.ContentObserver
        public boolean deliverSelfNotifications() {
            return true;
        }

        public ChangeObserver() {
            super(MediaStoreCursorRecyclerAdapter.this.mUIHandler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            MediaStoreCursorRecyclerAdapter.this.refresh();
        }
    }

    /* loaded from: classes2.dex */
    private class MyDataSetObserver extends DataSetObserver {
        private MyDataSetObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            MediaStoreCursorRecyclerAdapter.this.mDataValid = true;
            MediaStoreCursorRecyclerAdapter.this.notifyDataSetChanged();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            MediaStoreCursorRecyclerAdapter.this.mDataValid = false;
            MediaStoreCursorRecyclerAdapter.this.notifyDataSetInvalidated();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class MyThumbnailLoaderDrawable extends ThumbnailLoaderDrawable {
        public MyThumbnailLoaderDrawable(Context context, int i, int i2) {
            super(context, i, i2);
        }

        @Override // com.serenegiant.mediastore.ThumbnailLoaderDrawable
        protected ThumbnailLoader createLoader() {
            return new MyThumbnailLoader(this);
        }

        @Override // com.serenegiant.mediastore.ThumbnailLoaderDrawable
        protected Bitmap checkCache(long j) {
            return MediaStoreCursorRecyclerAdapter.this.mThumbnailCache.get(j);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.mediastore.ThumbnailLoaderDrawable
        public void setBitmap(Bitmap bitmap) {
            if (bitmap == null) {
                bitmap = BitmapHelper.fromDrawable(getContext(), R.drawable.ic_error_outline_red_24dp);
            }
            super.setBitmap(bitmap);
        }
    }

    /* loaded from: classes2.dex */
    private class MyThumbnailLoader extends ThumbnailLoader {
        public MyThumbnailLoader(MyThumbnailLoaderDrawable myThumbnailLoaderDrawable) {
            super(myThumbnailLoaderDrawable);
        }

        @Override // com.serenegiant.mediastore.ThumbnailLoader
        protected Bitmap loadThumbnail(Context context, MediaInfo mediaInfo, int i, int i2) {
            try {
                return MediaStoreCursorRecyclerAdapter.this.mThumbnailCache.getThumbnail(context.getContentResolver(), mediaInfo, i, i2);
            } catch (IOException unused) {
                return null;
            }
        }
    }

    private void setInfo(ViewHolder viewHolder, MediaInfo mediaInfo) {
        viewHolder.info.set(mediaInfo);
        ImageView imageView = viewHolder.mImageView;
        TextView textView = viewHolder.mTitleView;
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (!(drawable instanceof ThumbnailLoaderDrawable)) {
                drawable = new MyThumbnailLoaderDrawable(this.mContext, this.mThumbnailWidth, this.mThumbnailHeight);
                imageView.setImageDrawable(drawable);
            }
            ((ThumbnailLoaderDrawable) drawable).startLoad(mediaInfo);
        }
        if (textView != null) {
            textView.setVisibility(this.mShowTitle ? 0 : 8);
            if (this.mShowTitle) {
                textView.setText(mediaInfo.title);
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MediaInfo info;
        private final ImageView mImageView;
        private final TextView mTitleView;

        public ViewHolder(View view) {
            super(view);
            MediaInfo mediaInfo = new MediaInfo();
            this.info = mediaInfo;
            view.setTag(R.id.info, mediaInfo);
            this.mImageView = ViewUtils.findIconView(view);
            this.mTitleView = ViewUtils.findTitleView(view);
        }
    }
}
