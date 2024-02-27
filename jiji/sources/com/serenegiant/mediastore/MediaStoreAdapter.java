package com.serenegiant.mediastore;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cursoradapter.widget.CursorAdapter;
import com.serenegiant.common.R;
import com.serenegiant.graphics.BitmapHelper;
import com.serenegiant.utils.ThreadPool;
import com.serenegiant.view.ViewUtils;
import java.io.FileNotFoundException;
import java.io.IOException;

/* loaded from: classes2.dex */
public class MediaStoreAdapter extends CursorAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaStoreAdapter";
    private final MediaInfo info;
    private final Context mContext;
    private final LayoutInflater mInflater;
    private final int mLayoutId;
    private int mMediaType;
    private boolean mNeedValidate;
    private final MyAsyncQueryHandler mQueryHandler;
    private String mSelection;
    private String[] mSelectionArgs;
    private boolean mShowTitle;
    private final ThumbnailCache mThumbnailCache;
    private int mThumbnailHeight;
    private int mThumbnailWidth;

    public MediaStoreAdapter(Context context, int i) {
        this(context, i, true);
    }

    public MediaStoreAdapter(Context context, int i, boolean z) {
        super(context, (Cursor) null, 2);
        this.mThumbnailWidth = 200;
        this.mThumbnailHeight = 200;
        this.mShowTitle = false;
        this.mMediaType = 0;
        this.info = new MediaInfo();
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mQueryHandler = new MyAsyncQueryHandler(context.getContentResolver(), this);
        this.mLayoutId = i;
        this.mThumbnailCache = new ThumbnailCache(context);
        this.mNeedValidate = true;
        if (z) {
            refresh();
        }
    }

    protected void finalize() throws Throwable {
        try {
            changeCursor(null);
        } finally {
            super.finalize();
        }
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View inflate = this.mInflater.inflate(this.mLayoutId, viewGroup, false);
        getViewHolder(inflate);
        return inflate;
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = getViewHolder(view);
        ImageView imageView = viewHolder.mImageView;
        TextView textView = viewHolder.mTitleView;
        Drawable drawable = imageView.getDrawable();
        if (!(drawable instanceof ThumbnailLoaderDrawable)) {
            drawable = createLoaderDrawable(this.mContext);
            imageView.setImageDrawable(drawable);
        }
        this.info.loadFromCursor(cursor);
        ((ThumbnailLoaderDrawable) drawable).startLoad(this.info);
        if (textView != null) {
            textView.setVisibility(this.mShowTitle ? 0 : 8);
            if (this.mShowTitle) {
                textView.setText(cursor.getString(1));
            }
        }
    }

    protected ThumbnailLoaderDrawable createLoaderDrawable(Context context) {
        return new MyThumbnailLoaderDrawable(context, this.mThumbnailWidth, this.mThumbnailHeight);
    }

    private ViewHolder getViewHolder(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.mediastorephotoadapter);
        if (viewHolder == null) {
            ViewHolder viewHolder2 = new ViewHolder();
            viewHolder2.mImageView = ViewUtils.findIconView(view);
            viewHolder2.mTitleView = ViewUtils.findTitleView(view);
            view.setTag(R.id.mediastorephotoadapter, viewHolder2);
            return viewHolder2;
        }
        return viewHolder;
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter
    protected void onContentChanged() {
        this.mQueryHandler.requery();
    }

    public void refresh() {
        ThreadPool.preStartAllCoreThreads();
        onContentChanged();
    }

    public void setValidateItems(boolean z) {
        if (this.mNeedValidate != z) {
            this.mNeedValidate = z;
        }
    }

    public int getPositionFromId(long j) {
        int count = getCount();
        MediaInfo mediaInfo = new MediaInfo();
        for (int i = 0; i < count; i++) {
            getMediaInfo(i, mediaInfo);
            if (mediaInfo.id == j) {
                return i;
            }
        }
        return -1;
    }

    public Bitmap getImage(int i, int i2, int i3) throws FileNotFoundException, IOException {
        return BitmapHelper.asBitmap(this.mContext.getContentResolver(), getItemId(i), i2, i3);
    }

    public MediaInfo getMediaInfo(int i) {
        return getMediaInfo(i, null);
    }

    public synchronized MediaInfo getMediaInfo(int i, MediaInfo mediaInfo) {
        if (mediaInfo == null) {
            mediaInfo = new MediaInfo();
        }
        Cursor cursor = (Cursor) super.getItem(i);
        if (cursor != null) {
            mediaInfo.loadFromCursor(cursor);
        } else {
            throw new IllegalStateException("Cursor is not ready!");
        }
        return mediaInfo;
    }

    public void setThumbnailSize(int i) {
        if (this.mThumbnailWidth == i && this.mThumbnailHeight == i) {
            return;
        }
        this.mThumbnailHeight = i;
        this.mThumbnailWidth = i;
        this.mThumbnailCache.clear();
        onContentChanged();
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

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class MyAsyncQueryHandler extends AsyncQueryHandler {
        private final MediaStoreAdapter mAdapter;

        public MyAsyncQueryHandler(ContentResolver contentResolver, MediaStoreAdapter mediaStoreAdapter) {
            super(contentResolver);
            this.mAdapter = mediaStoreAdapter;
        }

        public void requery() {
            synchronized (this.mAdapter) {
                this.mAdapter.changeCursor(null);
                this.mAdapter.mSelection = MediaStoreUtils.SELECTIONS[this.mAdapter.mMediaType % 3];
                this.mAdapter.mSelectionArgs = null;
                startQuery(0, this.mAdapter, MediaStoreUtils.QUERY_URI_FILES, MediaStoreUtils.PROJ_MEDIA, this.mAdapter.mSelection, this.mAdapter.mSelectionArgs, null);
            }
        }

        @Override // android.content.AsyncQueryHandler
        protected void onQueryComplete(int i, Object obj, Cursor cursor) {
            Cursor swapCursor = this.mAdapter.swapCursor(cursor);
            if (swapCursor == null || swapCursor.isClosed()) {
                return;
            }
            swapCursor.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class ViewHolder {
        ImageView mImageView;
        TextView mTitleView;

        private ViewHolder() {
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
            return MediaStoreAdapter.this.mThumbnailCache.get(j);
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
                return MediaStoreAdapter.this.mThumbnailCache.getThumbnail(context.getContentResolver(), mediaInfo, i, i2);
            } catch (IOException unused) {
                return null;
            }
        }
    }
}
