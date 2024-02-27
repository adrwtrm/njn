package com.serenegiant.widget;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.TextView;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.RecyclerView;
import com.serenegiant.common.R;
import com.serenegiant.system.SAFUtils;
import com.serenegiant.utils.HandlerThreadHandler;
import com.serenegiant.utils.HandlerUtils;
import com.serenegiant.view.ViewUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class DocumentTreeRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final boolean DEBUG = false;
    private static final long DELAY_MILLIS = 100;
    private static final String TAG = "DocumentTreeRecyclerAdapter";
    private Handler mAsyncHandler;
    private final Context mContext;
    private DocumentFile mCurrentDir;
    private LayoutInflater mLayoutInflater;
    private final int mLayoutRes;
    private DocumentTreeRecyclerAdapterListener mListener;
    private RecyclerView mRecycleView;
    private final DocumentFile mRoot;
    private final Object mSync = new Object();
    private final Handler mUIHandler = new Handler(Looper.getMainLooper());
    private final List<FileInfo> mItems = new ArrayList();
    private final List<FileInfo> mWork = new ArrayList();
    private String[] mExtFilter = null;
    private final SAFUtils.FileFilter mFileFilter = new SAFUtils.FileFilter() { // from class: com.serenegiant.widget.DocumentTreeRecyclerAdapter.1
        @Override // com.serenegiant.system.SAFUtils.FileFilter
        public boolean accept(DocumentFile documentFile) {
            String[] strArr;
            if (DocumentTreeRecyclerAdapter.this.mExtFilter == null || documentFile.isDirectory()) {
                return true;
            }
            String lowerCase = documentFile.getName().toLowerCase(Locale.getDefault());
            int length = DocumentTreeRecyclerAdapter.this.mExtFilter.length;
            for (int i = 0; i < length; i++) {
                if (lowerCase.endsWith("." + strArr[i])) {
                    return true;
                }
            }
            return false;
        }
    };
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() { // from class: com.serenegiant.widget.DocumentTreeRecyclerAdapter.2
        @Override // android.view.View.OnClickListener
        public void onClick(final View view) {
            if (DocumentTreeRecyclerAdapter.this.mRecycleView == null || !DocumentTreeRecyclerAdapter.this.mRecycleView.isEnabled()) {
                Log.w(DocumentTreeRecyclerAdapter.TAG, "onClick:mRecycleView=" + DocumentTreeRecyclerAdapter.this.mRecycleView);
                return;
            }
            if (view instanceof Checkable) {
                ((Checkable) view).setChecked(true);
                DocumentTreeRecyclerAdapter.this.mRecycleView.postDelayed(new Runnable() { // from class: com.serenegiant.widget.DocumentTreeRecyclerAdapter.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ((Checkable) view).setChecked(false);
                    }
                }, DocumentTreeRecyclerAdapter.DELAY_MILLIS);
            }
            if (DocumentTreeRecyclerAdapter.this.mListener != null) {
                Object tag = view.getTag(R.id.position);
                if (tag instanceof Integer) {
                    try {
                        DocumentTreeRecyclerAdapterListener documentTreeRecyclerAdapterListener = DocumentTreeRecyclerAdapter.this.mListener;
                        DocumentTreeRecyclerAdapter documentTreeRecyclerAdapter = DocumentTreeRecyclerAdapter.this;
                        documentTreeRecyclerAdapterListener.onItemClick(documentTreeRecyclerAdapter, view, documentTreeRecyclerAdapter.getItem(((Integer) tag).intValue()));
                    } catch (Exception e) {
                        Log.w(DocumentTreeRecyclerAdapter.TAG, e);
                    }
                }
            }
        }
    };
    private final View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() { // from class: com.serenegiant.widget.DocumentTreeRecyclerAdapter.3
        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) {
            if (DocumentTreeRecyclerAdapter.this.mRecycleView == null || !DocumentTreeRecyclerAdapter.this.mRecycleView.isEnabled() || DocumentTreeRecyclerAdapter.this.mListener == null) {
                return false;
            }
            Object tag = view.getTag(R.id.position);
            if (tag instanceof Integer) {
                try {
                    DocumentTreeRecyclerAdapterListener documentTreeRecyclerAdapterListener = DocumentTreeRecyclerAdapter.this.mListener;
                    DocumentTreeRecyclerAdapter documentTreeRecyclerAdapter = DocumentTreeRecyclerAdapter.this;
                    return documentTreeRecyclerAdapterListener.onItemLongClick(documentTreeRecyclerAdapter, view, documentTreeRecyclerAdapter.getItem(((Integer) tag).intValue()));
                } catch (Exception e) {
                    Log.w(DocumentTreeRecyclerAdapter.TAG, e);
                    return false;
                }
            }
            return false;
        }
    };

    /* loaded from: classes2.dex */
    public interface DocumentTreeRecyclerAdapterListener {
        void onItemClick(RecyclerView.Adapter<?> adapter, View view, DocumentFile documentFile);

        boolean onItemLongClick(RecyclerView.Adapter<?> adapter, View view, DocumentFile documentFile);
    }

    public DocumentTreeRecyclerAdapter(Context context, int i, DocumentFile documentFile) {
        if (documentFile == null || !documentFile.isDirectory()) {
            throw new IllegalArgumentException("root should be a directory!");
        }
        this.mContext = context;
        this.mLayoutRes = i;
        this.mRoot = documentFile;
        this.mAsyncHandler = HandlerThreadHandler.createHandler(TAG);
        internalChangeDir(documentFile);
    }

    protected void finalize() throws Throwable {
        try {
            release();
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
    public final ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (this.mLayoutInflater == null) {
            this.mLayoutInflater = LayoutInflater.from(this.mContext);
        }
        View inflate = this.mLayoutInflater.inflate(this.mLayoutRes, viewGroup, false);
        inflate.setOnClickListener(this.mOnClickListener);
        inflate.setOnLongClickListener(this.mOnLongClickListener);
        return onCreateViewHolder(inflate);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.setItem(i, getFileInfo(i));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mItems.size();
    }

    public DocumentFile getItem(int i) throws IndexOutOfBoundsException {
        return getFileInfo(i).getFile();
    }

    public void setListener(DocumentTreeRecyclerAdapterListener documentTreeRecyclerAdapterListener) {
        this.mListener = documentTreeRecyclerAdapterListener;
    }

    public void changeDir(DocumentFile documentFile) throws IOException {
        if (documentFile.isDirectory()) {
            internalChangeDir(documentFile);
            notifyDataSetChanged();
            return;
        }
        throw new IOException(String.format(Locale.US, "%s is not a directory/could not read", documentFile.getUri()));
    }

    public void setExtFilter(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mExtFilter = Pattern.compile("[;\\s]+").split(str.toLowerCase(Locale.getDefault()));
        } else {
            this.mExtFilter = null;
        }
    }

    protected void release() {
        synchronized (this.mItems) {
            this.mItems.clear();
        }
        this.mWork.clear();
        synchronized (this.mSync) {
            Handler handler = this.mAsyncHandler;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                HandlerUtils.NoThrowQuit(this.mAsyncHandler);
                this.mAsyncHandler = null;
            }
        }
    }

    protected ViewHolder onCreateViewHolder(View view) {
        return new ViewHolder(view);
    }

    protected FileInfo getFileInfo(int i) throws IndexOutOfBoundsException {
        return this.mItems.get(i);
    }

    private void internalChangeDir(DocumentFile documentFile) {
        synchronized (this.mSync) {
            Handler handler = this.mAsyncHandler;
            if (handler != null) {
                handler.post(new ChangeDirTask(documentFile));
            } else {
                throw new IllegalStateException("already released");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class ChangeDirTask implements Runnable {
        private final DocumentFile newDir;

        public ChangeDirTask(DocumentFile documentFile) {
            this.newDir = documentFile;
        }

        @Override // java.lang.Runnable
        public void run() {
            DocumentFile parentFile;
            DocumentTreeRecyclerAdapter.this.mWork.clear();
            for (DocumentFile documentFile : SAFUtils.listFiles(this.newDir, DocumentTreeRecyclerAdapter.this.mFileFilter)) {
                DocumentTreeRecyclerAdapter.this.mWork.add(new FileInfo(documentFile, false));
            }
            Collections.sort(DocumentTreeRecyclerAdapter.this.mWork);
            if (!DocumentTreeRecyclerAdapter.this.mRoot.equals(this.newDir) && (parentFile = this.newDir.getParentFile()) != null) {
                DocumentTreeRecyclerAdapter.this.mWork.add(0, new FileInfo(parentFile, true));
            }
            DocumentTreeRecyclerAdapter.this.mUIHandler.postDelayed(new Runnable() { // from class: com.serenegiant.widget.DocumentTreeRecyclerAdapter.ChangeDirTask.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (DocumentTreeRecyclerAdapter.this.mItems) {
                        DocumentTreeRecyclerAdapter.this.mCurrentDir = ChangeDirTask.this.newDir;
                        DocumentTreeRecyclerAdapter.this.mItems.clear();
                        DocumentTreeRecyclerAdapter.this.mItems.addAll(DocumentTreeRecyclerAdapter.this.mWork);
                    }
                    DocumentTreeRecyclerAdapter.this.mWork.clear();
                    DocumentTreeRecyclerAdapter.this.notifyDataSetChanged();
                }
            }, DocumentTreeRecyclerAdapter.DELAY_MILLIS);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes2.dex */
    public static class FileInfo implements Comparable<FileInfo> {
        private final DocumentFile mFile;
        private final boolean mIsUpNavigation;
        private final Locale mLocale = Locale.getDefault();
        private boolean isSelected = false;

        public FileInfo(DocumentFile documentFile, boolean z) {
            this.mFile = documentFile;
            this.mIsUpNavigation = z;
        }

        public DocumentFile getFile() {
            return this.mFile;
        }

        public Uri getUri() {
            return this.mFile.getUri();
        }

        public String getName() {
            String name = this.mFile.getName();
            return this.mIsUpNavigation ? ".." : TextUtils.isEmpty(name) ? "" : name;
        }

        public boolean isDirectory() {
            return this.mFile.isDirectory();
        }

        public boolean canRead() {
            return this.mFile.canRead();
        }

        public boolean canWrite() {
            return this.mFile.canWrite();
        }

        public boolean isUpNavigation() {
            return this.mIsUpNavigation;
        }

        public boolean isSelected() {
            return this.isSelected;
        }

        public void select(boolean z) {
            this.isSelected = z;
        }

        @Override // java.lang.Comparable
        public int compareTo(FileInfo fileInfo) {
            if (!this.mFile.isDirectory() || fileInfo.getFile().isDirectory()) {
                if (this.mFile.isDirectory() || !fileInfo.getFile().isDirectory()) {
                    return this.mFile.getName().toLowerCase(this.mLocale).compareTo(fileInfo.getName().toLowerCase(this.mLocale));
                }
                return 1;
            }
            return -1;
        }

        public String toString() {
            return "FileInfo{file=" + this.mFile.getUri() + ",mIsUpNavigation=" + this.mIsUpNavigation + ",isSelected=" + this.isSelected + '}';
        }
    }

    /* loaded from: classes2.dex */
    public static class ViewHolder extends RecyclerView.ViewHolder implements Dividable {
        private FileInfo mItem;
        TextView mTitleTv;

        public ViewHolder(View view) {
            super(view);
            TextView findTitleView = ViewUtils.findTitleView(view);
            this.mTitleTv = findTitleView;
            if (findTitleView == null) {
                throw new IllegalArgumentException("TextView not found");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setItem(int i, FileInfo fileInfo) {
            this.mItem = fileInfo;
            this.itemView.setTag(R.id.position, Integer.valueOf(i));
            if (this.mTitleTv != null) {
                if (fileInfo.isDirectory() && !fileInfo.isUpNavigation()) {
                    this.mTitleTv.setText(fileInfo.getName() + "/");
                } else {
                    this.mTitleTv.setText(fileInfo.getName());
                }
            }
        }

        public FileInfo getItem() {
            return this.mItem;
        }

        public void setEnable(boolean z) {
            this.itemView.setEnabled(z);
        }

        @Override // com.serenegiant.widget.Dividable
        public void hasDivider(boolean z) {
            if (this.itemView instanceof Dividable) {
                ((Dividable) this.itemView).hasDivider(z);
            } else {
                this.itemView.setTag(R.id.has_divider, Boolean.valueOf(z));
            }
        }

        @Override // com.serenegiant.widget.Dividable
        public boolean hasDivider() {
            if (this.itemView instanceof Dividable) {
                return ((Dividable) this.itemView).hasDivider();
            }
            Boolean bool = (Boolean) this.itemView.getTag(R.id.has_divider);
            return bool != null && bool.booleanValue();
        }
    }
}
