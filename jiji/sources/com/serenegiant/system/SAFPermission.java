package com.serenegiant.system;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Pair;
import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.fragment.app.Fragment;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class SAFPermission {
    private static final boolean DEBUG = false;
    public static final SAFCallback DEFAULT_CALLBACK = new DefaultSAFCallback();
    private static final String TAG = "SAFPermission";
    private final ActivityResultLauncher<Pair<Integer, Uri>> mLauncher;
    private final WeakReference<Context> mWeakContext;

    /* loaded from: classes2.dex */
    public static class DefaultSAFCallback implements SAFCallback {
        @Override // com.serenegiant.system.SAFPermission.SAFCallback
        public void onFailed(int i) {
        }

        @Override // com.serenegiant.system.SAFPermission.SAFCallback
        public void onResult(int i, Uri uri) {
        }
    }

    /* loaded from: classes2.dex */
    public interface SAFCallback {
        void onFailed(int i);

        void onResult(int i, Uri uri);
    }

    public SAFPermission(ComponentActivity componentActivity, SAFCallback sAFCallback) {
        WeakReference<Context> weakReference = new WeakReference<>(componentActivity);
        this.mWeakContext = weakReference;
        this.mLauncher = componentActivity.registerForActivityResult(new OpenDocumentTree(), new MyActivityResultCallback(weakReference, sAFCallback));
    }

    public SAFPermission(Fragment fragment, SAFCallback sAFCallback) {
        WeakReference<Context> weakReference = new WeakReference<>(fragment.requireContext());
        this.mWeakContext = weakReference;
        this.mLauncher = fragment.registerForActivityResult(new OpenDocumentTree(), new MyActivityResultCallback(weakReference, sAFCallback));
    }

    public void requestPermission(int i) throws IllegalStateException {
        requestPermission(i, null);
    }

    public void requestPermission(int i, Uri uri) throws IllegalStateException {
        Context context = this.mWeakContext.get();
        if (context != null) {
            SAFUtils.releasePersistableUriPermission(context, i);
            this.mLauncher.launch(Pair.create(Integer.valueOf(i), uri));
            return;
        }
        throw new IllegalStateException("context is already released!");
    }

    /* loaded from: classes2.dex */
    private static class MyActivityResultCallback implements ActivityResultCallback<Pair<Integer, Uri>> {
        private static final String TAG = "MyActivityResultCallback";
        final SAFCallback callback;
        private final WeakReference<Context> mWeakContext;

        private MyActivityResultCallback(WeakReference<Context> weakReference, SAFCallback sAFCallback) {
            this.mWeakContext = weakReference;
            this.callback = sAFCallback;
        }

        @Override // androidx.activity.result.ActivityResultCallback
        public void onActivityResult(Pair<Integer, Uri> pair) {
            if (pair != null) {
                int intValue = ((Integer) pair.first).intValue();
                Uri uri = (Uri) pair.second;
                Context context = this.mWeakContext.get();
                if (uri != null) {
                    if (context != null) {
                        SAFUtils.takePersistableUriPermission(context, intValue, uri);
                    }
                    this.callback.onResult(intValue, uri);
                    return;
                }
                if (context != null) {
                    SAFUtils.releasePersistableUriPermission(context, intValue);
                }
                this.callback.onFailed(intValue);
                return;
            }
            this.callback.onFailed(0);
        }
    }

    /* loaded from: classes2.dex */
    private static class OpenDocumentTree extends ActivityResultContract<Pair<Integer, Uri>, Pair<Integer, Uri>> {
        private static final String TAG = "OpenDocumentTree";
        private Pair<Integer, Uri> input;

        private OpenDocumentTree() {
        }

        @Override // androidx.activity.result.contract.ActivityResultContract
        public Intent createIntent(Context context, Pair<Integer, Uri> pair) {
            this.input = pair;
            Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
            if (pair != null) {
                intent.putExtra("android.provider.extra.INITIAL_URI", pair != null ? (Uri) pair.second : null);
            }
            return intent;
        }

        @Override // androidx.activity.result.contract.ActivityResultContract
        public final ActivityResultContract.SynchronousResult<Pair<Integer, Uri>> getSynchronousResult(Context context, Pair<Integer, Uri> pair) {
            Uri storageUri;
            this.input = pair;
            if (pair == null || (storageUri = SAFUtils.getStorageUri(context, ((Integer) pair.first).intValue())) == null) {
                return null;
            }
            return new ActivityResultContract.SynchronousResult<>(Pair.create((Integer) pair.first, storageUri));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.activity.result.contract.ActivityResultContract
        public final Pair<Integer, Uri> parseResult(int i, Intent intent) {
            Uri data = (intent == null || i != -1) ? null : intent.getData();
            Pair<Integer, Uri> pair = this.input;
            return Pair.create(Integer.valueOf(pair != null ? ((Integer) pair.first).intValue() : 0), data);
        }
    }
}
