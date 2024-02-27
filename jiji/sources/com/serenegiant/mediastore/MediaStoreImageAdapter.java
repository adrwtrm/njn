package com.serenegiant.mediastore;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;
import com.serenegiant.common.R;
import com.serenegiant.graphics.BitmapHelper;
import com.serenegiant.utils.ThreadPool;
import com.serenegiant.view.ViewUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class MediaStoreImageAdapter extends PagerAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaStoreImageAdapter";
    private final MediaInfo info;
    protected ChangeObserver mChangeObserver;
    private final Context mContext;
    private final ContentResolver mCr;
    private Cursor mCursor;
    protected DataSetObserver mDataSetObserver;
    protected boolean mDataValid;
    private final LayoutInflater mInflater;
    private final int mLayoutId;
    private boolean mNeedValidate;
    private final MyAsyncQueryHandler mQueryHandler;
    private final String mSelection;
    private String[] mSelectionArgs;
    private boolean mShowTitle;
    private final List<Integer> mValues;

    public void notifyDataSetInvalidated() {
    }

    public MediaStoreImageAdapter(Context context, int i) {
        this(context, i, true);
    }

    public MediaStoreImageAdapter(Context context, int i, boolean z) {
        this.mSelection = MediaStoreUtils.SELECTIONS[1];
        this.mSelectionArgs = null;
        this.info = new MediaInfo();
        this.mValues = new ArrayList();
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mLayoutId = i;
        ContentResolver contentResolver = context.getContentResolver();
        this.mCr = contentResolver;
        this.mQueryHandler = new MyAsyncQueryHandler(contentResolver, this);
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

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        int size;
        synchronized (this.mValues) {
            size = this.mValues.size();
        }
        return size;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = this.mInflater.inflate(this.mLayoutId, viewGroup, false);
        if (inflate != null) {
            viewGroup.addView(inflate);
            ViewHolder viewHolder = (ViewHolder) inflate.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder();
            }
            viewHolder.mImageView = ViewUtils.findIconView(inflate);
            viewHolder.mTitleView = ViewUtils.findTitleView(inflate);
            this.info.loadFromCursor(getCursor(i));
            Drawable drawable = viewHolder.mImageView.getDrawable();
            if (!(drawable instanceof LoaderDrawable)) {
                drawable = createLoaderDrawable(this.mContext, this.info);
                viewHolder.mImageView.setImageDrawable(drawable);
            }
            ((LoaderDrawable) drawable).startLoad(this.info);
            if (viewHolder.mTitleView != null) {
                viewHolder.mTitleView.setVisibility(this.mShowTitle ? 0 : 8);
                if (this.mShowTitle) {
                    viewHolder.mTitleView.setText(this.info.title);
                }
            }
        }
        return inflate;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        if (obj instanceof View) {
            viewGroup.removeView((View) obj);
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(Object obj) {
        return super.getItemPosition(obj);
    }

    public void setValidateItems(boolean z) {
        if (this.mNeedValidate != z) {
            this.mNeedValidate = z;
        }
    }

    public int getItemPositionFromID(long j) {
        Cursor query = this.mCr.query(MediaStoreUtils.QUERY_URI_FILES, MediaStoreUtils.PROJ_MEDIA, this.mSelection, this.mSelectionArgs, null);
        int i = -1;
        if (query != null) {
            try {
                if (query.moveToFirst()) {
                    int i2 = 0;
                    while (true) {
                        if (query.getLong(0) == j) {
                            i = i2;
                            break;
                        } else if (!query.moveToNext()) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
            } finally {
                query.close();
            }
        }
        return i;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view.equals(obj);
    }

    public MediaInfo getMediaInfo(int i) {
        return getMediaInfo(i, null);
    }

    public synchronized MediaInfo getMediaInfo(int i, MediaInfo mediaInfo) {
        int intValue;
        if (mediaInfo == null) {
            mediaInfo = new MediaInfo();
        }
        synchronized (this.mValues) {
            intValue = this.mValues.get(i).intValue();
        }
        Cursor cursor = this.mCursor;
        if (cursor == null) {
            throw new IllegalStateException("Cursor is not ready!");
        }
        if (cursor.moveToPosition(intValue)) {
            mediaInfo.loadFromCursor(this.mCursor);
        }
        return mediaInfo;
    }

    protected void changeCursor(Cursor cursor) {
        Cursor swapCursor = swapCursor(cursor);
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
        this.mCursor = cursor;
        int i = 0;
        if (cursor != null) {
            ChangeObserver changeObserver2 = this.mChangeObserver;
            if (changeObserver2 != null) {
                cursor.registerContentObserver(changeObserver2);
            }
            DataSetObserver dataSetObserver2 = this.mDataSetObserver;
            if (dataSetObserver2 != null) {
                cursor.registerDataSetObserver(dataSetObserver2);
            }
            synchronized (this.mValues) {
                this.mValues.clear();
                if (cursor.moveToFirst()) {
                    do {
                        this.info.loadFromCursor(cursor);
                        if (!this.mNeedValidate || this.info.canRead(this.mCr)) {
                            this.mValues.add(Integer.valueOf(i));
                        }
                        i++;
                    } while (cursor.moveToNext());
                }
            }
            this.mDataValid = true;
            notifyDataSetChanged();
        } else {
            synchronized (this.mValues) {
                this.mValues.clear();
            }
            this.mDataValid = false;
            notifyDataSetInvalidated();
        }
        return cursor2;
    }

    public void refresh() {
        ThreadPool.preStartAllCoreThreads();
        this.mQueryHandler.requery();
    }

    protected LoaderDrawable createLoaderDrawable(Context context, MediaInfo mediaInfo) {
        return new ImageLoaderDrawable(context, mediaInfo.width, mediaInfo.height);
    }

    /* loaded from: classes2.dex */
    private static final class ViewHolder {
        ImageView mImageView;
        TextView mTitleView;

        private ViewHolder() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class MyAsyncQueryHandler extends AsyncQueryHandler {
        private final MediaStoreImageAdapter mAdapter;

        public MyAsyncQueryHandler(ContentResolver contentResolver, MediaStoreImageAdapter mediaStoreImageAdapter) {
            super(contentResolver);
            this.mAdapter = mediaStoreImageAdapter;
        }

        public void requery() {
            synchronized (this.mAdapter) {
                startQuery(0, this.mAdapter, MediaStoreUtils.QUERY_URI_IMAGES, MediaStoreUtils.PROJ_MEDIA_IMAGE, this.mAdapter.mSelection, this.mAdapter.mSelectionArgs, null);
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
    public class ChangeObserver extends ContentObserver {
        @Override // android.database.ContentObserver
        public boolean deliverSelfNotifications() {
            return true;
        }

        public ChangeObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            MediaStoreImageAdapter.this.refresh();
        }
    }

    /* loaded from: classes2.dex */
    private class MyDataSetObserver extends DataSetObserver {
        private MyDataSetObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            MediaStoreImageAdapter.this.mDataValid = true;
            MediaStoreImageAdapter.this.notifyDataSetChanged();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            MediaStoreImageAdapter.this.mDataValid = false;
            MediaStoreImageAdapter.this.notifyDataSetInvalidated();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class ImageLoaderDrawable extends LoaderDrawable {
        @Override // com.serenegiant.mediastore.LoaderDrawable
        protected Bitmap checkCache(long j) {
            return null;
        }

        public ImageLoaderDrawable(Context context, int i, int i2) {
            super(context, i, i2);
        }

        @Override // com.serenegiant.mediastore.LoaderDrawable
        protected ImageLoader createImageLoader() {
            return new MyImageLoader(this);
        }
    }

    /* loaded from: classes2.dex */
    private static class MyImageLoader extends ImageLoader {
        public MyImageLoader(ImageLoaderDrawable imageLoaderDrawable) {
            super(imageLoaderDrawable);
        }

        @Override // com.serenegiant.mediastore.ImageLoader
        protected Bitmap loadBitmap(Context context, MediaInfo mediaInfo, int i, int i2) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapHelper.asBitmap(context.getContentResolver(), mediaInfo.id, i, i2);
                if (bitmap != null) {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    Rect rect = new Rect();
                    this.mParent.copyBounds(rect);
                    int centerX = rect.centerX();
                    int centerY = rect.centerY();
                    rect.set(centerX - (width / 2), centerY - (height / width), centerX + (width / 2), centerY + (height / 2));
                    this.mParent.onBoundsChange(rect);
                }
            } catch (IOException unused) {
            }
            return bitmap == null ? loadDefaultBitmap(context, R.drawable.ic_error_outline_red_24dp) : bitmap;
        }
    }
}
