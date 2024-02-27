package androidx.documentfile.provider;

import android.content.Context;
import android.net.Uri;
import com.serenegiant.system.SAFUtils;

/* loaded from: classes.dex */
public class SAFTreeDocumentFile extends TreeDocumentFile {
    private final Context mContext;
    private final int mRequestCode;

    @Override // androidx.documentfile.provider.TreeDocumentFile, androidx.documentfile.provider.DocumentFile
    public /* bridge */ /* synthetic */ boolean canRead() {
        return super.canRead();
    }

    @Override // androidx.documentfile.provider.TreeDocumentFile, androidx.documentfile.provider.DocumentFile
    public /* bridge */ /* synthetic */ boolean canWrite() {
        return super.canWrite();
    }

    @Override // androidx.documentfile.provider.TreeDocumentFile, androidx.documentfile.provider.DocumentFile
    public /* bridge */ /* synthetic */ DocumentFile createDirectory(String str) {
        return super.createDirectory(str);
    }

    @Override // androidx.documentfile.provider.TreeDocumentFile, androidx.documentfile.provider.DocumentFile
    public /* bridge */ /* synthetic */ DocumentFile createFile(String str, String str2) {
        return super.createFile(str, str2);
    }

    @Override // androidx.documentfile.provider.TreeDocumentFile, androidx.documentfile.provider.DocumentFile
    public /* bridge */ /* synthetic */ boolean exists() {
        return super.exists();
    }

    @Override // androidx.documentfile.provider.TreeDocumentFile, androidx.documentfile.provider.DocumentFile
    public /* bridge */ /* synthetic */ String getName() {
        return super.getName();
    }

    @Override // androidx.documentfile.provider.TreeDocumentFile, androidx.documentfile.provider.DocumentFile
    public /* bridge */ /* synthetic */ String getType() {
        return super.getType();
    }

    @Override // androidx.documentfile.provider.TreeDocumentFile, androidx.documentfile.provider.DocumentFile
    public /* bridge */ /* synthetic */ Uri getUri() {
        return super.getUri();
    }

    @Override // androidx.documentfile.provider.TreeDocumentFile, androidx.documentfile.provider.DocumentFile
    public /* bridge */ /* synthetic */ boolean isDirectory() {
        return super.isDirectory();
    }

    @Override // androidx.documentfile.provider.TreeDocumentFile, androidx.documentfile.provider.DocumentFile
    public /* bridge */ /* synthetic */ boolean isFile() {
        return super.isFile();
    }

    @Override // androidx.documentfile.provider.TreeDocumentFile, androidx.documentfile.provider.DocumentFile
    public /* bridge */ /* synthetic */ boolean isVirtual() {
        return super.isVirtual();
    }

    @Override // androidx.documentfile.provider.TreeDocumentFile, androidx.documentfile.provider.DocumentFile
    public /* bridge */ /* synthetic */ long lastModified() {
        return super.lastModified();
    }

    @Override // androidx.documentfile.provider.TreeDocumentFile, androidx.documentfile.provider.DocumentFile
    public /* bridge */ /* synthetic */ long length() {
        return super.length();
    }

    @Override // androidx.documentfile.provider.TreeDocumentFile, androidx.documentfile.provider.DocumentFile
    public /* bridge */ /* synthetic */ DocumentFile[] listFiles() {
        return super.listFiles();
    }

    @Override // androidx.documentfile.provider.TreeDocumentFile, androidx.documentfile.provider.DocumentFile
    public /* bridge */ /* synthetic */ boolean renameTo(String str) {
        return super.renameTo(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SAFTreeDocumentFile(DocumentFile documentFile, Context context, Uri uri, int i) {
        super(documentFile, context, uri);
        this.mContext = context;
        this.mRequestCode = i;
    }

    @Override // androidx.documentfile.provider.TreeDocumentFile, androidx.documentfile.provider.DocumentFile
    public boolean delete() {
        boolean delete = super.delete();
        if (delete) {
            SAFUtils.releasePersistableUriPermission(this.mContext, this.mRequestCode);
        }
        return delete;
    }
}
