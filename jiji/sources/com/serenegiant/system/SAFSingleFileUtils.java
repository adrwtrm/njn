package com.serenegiant.system;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.Pair;
import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.fragment.app.Fragment;
import com.serenegiant.system.SAFPermission;
import java.io.FileNotFoundException;

/* loaded from: classes2.dex */
public class SAFSingleFileUtils {
    private static final boolean DEBUG = false;
    private static final String EXTRA_KEY_REQUEST_ID = "SAFSingleFileUtils.EXTRA_KEY_REQUEST_ID";
    private static final String TAG = "SAFSingleFileUtils";
    private final ActivityResultLauncher<Intent> mLauncher;

    public SAFSingleFileUtils(ComponentActivity componentActivity, SAFPermission.SAFCallback sAFCallback) {
        this.mLauncher = componentActivity.registerForActivityResult(new SingleDocument(), new MyActivityResultCallback(sAFCallback));
    }

    public SAFSingleFileUtils(Fragment fragment, SAFPermission.SAFCallback sAFCallback) {
        this.mLauncher = fragment.registerForActivityResult(new SingleDocument(), new MyActivityResultCallback(sAFCallback));
    }

    public void requestOpen(int i, String str) throws IllegalArgumentException {
        this.mLauncher.launch(prepareOpenDocumentIntent(i, str));
    }

    public void requestCreate(int i, String str, String str2) throws IllegalArgumentException {
        this.mLauncher.launch(prepareCreateDocument(i, str, str2));
    }

    /* loaded from: classes2.dex */
    private static class MyActivityResultCallback implements ActivityResultCallback<Pair<Integer, Uri>> {
        private static final String TAG = "MyActivityResultCallback";
        final SAFPermission.SAFCallback callback;

        private MyActivityResultCallback(SAFPermission.SAFCallback sAFCallback) {
            this.callback = sAFCallback;
        }

        @Override // androidx.activity.result.ActivityResultCallback
        public void onActivityResult(Pair<Integer, Uri> pair) {
            if (pair != null) {
                int intValue = ((Integer) pair.first).intValue();
                Uri uri = (Uri) pair.second;
                if (uri != null) {
                    this.callback.onResult(intValue, uri);
                    return;
                } else {
                    this.callback.onFailed(intValue);
                    return;
                }
            }
            this.callback.onFailed(0);
        }
    }

    /* loaded from: classes2.dex */
    private static class SingleDocument extends ActivityResultContract<Intent, Pair<Integer, Uri>> {
        private static final String TAG = "SingleDocument";
        private Intent input;

        private SingleDocument() {
        }

        @Override // androidx.activity.result.contract.ActivityResultContract
        public Intent createIntent(Context context, Intent intent) {
            this.input = intent;
            String action = intent != null ? intent.getAction() : null;
            String type = intent != null ? intent.getType() : null;
            String stringExtra = intent != null ? intent.getStringExtra("android.intent.extra.TITLE") : null;
            if (action == null) {
                action = "android.intent.action.OPEN_DOCUMENT";
            }
            Intent intent2 = new Intent(action);
            if (intent != null) {
                intent2.putExtra("android.provider.extra.INITIAL_URI", intent != null ? intent.getData() : null);
            }
            if (!TextUtils.isEmpty(type)) {
                intent2.setType(type);
            }
            if (!TextUtils.isEmpty(stringExtra)) {
                intent2.putExtra("android.intent.extra.TITLE", stringExtra);
            }
            return intent2;
        }

        @Override // androidx.activity.result.contract.ActivityResultContract
        public final ActivityResultContract.SynchronousResult<Pair<Integer, Uri>> getSynchronousResult(Context context, Intent intent) {
            this.input = intent;
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.activity.result.contract.ActivityResultContract
        public final Pair<Integer, Uri> parseResult(int i, Intent intent) {
            Uri data = (intent == null || i != -1) ? null : intent.getData();
            Intent intent2 = this.input;
            return Pair.create(Integer.valueOf(intent2 != null ? intent2.getIntExtra(SAFSingleFileUtils.EXTRA_KEY_REQUEST_ID, 0) : 0), data);
        }
    }

    private static Intent prepareOpenDocumentIntent(int i, String str) throws IllegalArgumentException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("mime type should not a null/empty");
        }
        return new Intent("android.intent.action.OPEN_DOCUMENT").setType(str).putExtra(EXTRA_KEY_REQUEST_ID, i);
    }

    private static Intent prepareCreateDocument(int i, String str, String str2) throws IllegalArgumentException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("mime type should not a null/empty");
        }
        return new Intent("android.intent.action.OPEN_DOCUMENT").setType(str).putExtra("android.intent.extra.TITLE", str2);
    }

    public static boolean requestDeleteDocument(Context context, Uri uri) {
        try {
            if (BuildCheck.isKitKat()) {
                return DocumentsContract.deleteDocument(context.getContentResolver(), uri);
            }
            return false;
        } catch (FileNotFoundException unused) {
            return false;
        }
    }
}
