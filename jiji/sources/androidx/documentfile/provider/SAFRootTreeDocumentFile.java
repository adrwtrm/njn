package androidx.documentfile.provider;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.system.SAFUtils;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes.dex */
public class SAFRootTreeDocumentFile extends DocumentFile {
    private final Context mContext;

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean canRead() {
        return true;
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean canWrite() {
        return false;
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean delete() {
        return false;
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean exists() {
        return false;
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public String getName() {
        return "SAFRootDir";
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public String getType() {
        return null;
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean isDirectory() {
        return true;
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean isFile() {
        return false;
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean isVirtual() {
        return true;
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public long lastModified() {
        return 0L;
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public long length() {
        return 0L;
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public boolean renameTo(String str) {
        return false;
    }

    public static DocumentFile fromContext(Context context) {
        return new SAFRootTreeDocumentFile(context);
    }

    SAFRootTreeDocumentFile(Context context) {
        super(null);
        this.mContext = context;
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public DocumentFile createFile(String str, String str2) {
        throw new UnsupportedOperationException();
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public DocumentFile createDirectory(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public Uri getUri() {
        return Uri.parse("content://com.serenegiant.SAFRootTreeDocumentFile");
    }

    @Override // androidx.documentfile.provider.DocumentFile
    public DocumentFile[] listFiles() {
        ArrayList arrayList = new ArrayList();
        if (BuildCheck.isLollipop()) {
            for (Map.Entry<Integer, Uri> entry : SAFUtils.getStorageUriAll(this.mContext).entrySet()) {
                arrayList.add(new SAFTreeDocumentFile(this, this.mContext, DocumentsContract.buildDocumentUriUsingTree(entry.getValue(), DocumentsContract.getTreeDocumentId(entry.getValue())), entry.getKey().intValue()));
            }
        }
        return (DocumentFile[]) arrayList.toArray(new DocumentFile[0]);
    }
}
