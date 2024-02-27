package com.serenegiant.media;

import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.fragment.app.Fragment;
import com.serenegiant.media.ScreenCaptureUtils;

/* loaded from: classes2.dex */
public class ScreenCaptureUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "ScreenCaptureUtils";
    private final ActivityResultLauncher<Void> mLauncher;
    private boolean mRequestProjection;

    /* loaded from: classes2.dex */
    public interface ScreenCaptureCallback {
        void onFailed();

        void onResult(Intent intent);
    }

    public ScreenCaptureUtils(ComponentActivity componentActivity, final ScreenCaptureCallback screenCaptureCallback) {
        this.mLauncher = componentActivity.registerForActivityResult(new ScreenCapture(), new ActivityResultCallback() { // from class: com.serenegiant.media.ScreenCaptureUtils$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                ScreenCaptureUtils.lambda$new$0(ScreenCaptureUtils.ScreenCaptureCallback.this, (Intent) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$new$0(ScreenCaptureCallback screenCaptureCallback, Intent intent) {
        if (intent != null) {
            screenCaptureCallback.onResult(intent);
        } else {
            screenCaptureCallback.onFailed();
        }
    }

    public ScreenCaptureUtils(Fragment fragment, final ScreenCaptureCallback screenCaptureCallback) {
        this.mLauncher = fragment.registerForActivityResult(new ScreenCapture(), new ActivityResultCallback() { // from class: com.serenegiant.media.ScreenCaptureUtils$$ExternalSyntheticLambda0
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                ScreenCaptureUtils.lambda$new$1(ScreenCaptureUtils.ScreenCaptureCallback.this, (Intent) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$new$1(ScreenCaptureCallback screenCaptureCallback, Intent intent) {
        if (intent != null) {
            screenCaptureCallback.onResult(intent);
        } else {
            screenCaptureCallback.onFailed();
        }
    }

    public void requestScreenCapture() {
        if (this.mRequestProjection) {
            return;
        }
        this.mRequestProjection = true;
        this.mLauncher.launch(null);
    }

    /* loaded from: classes2.dex */
    private static class ScreenCapture extends ActivityResultContract<Void, Intent> {
        private static final String TAG = "ScreenCapture";

        @Override // androidx.activity.result.contract.ActivityResultContract
        public final ActivityResultContract.SynchronousResult<Intent> getSynchronousResult(Context context, Void r2) {
            return null;
        }

        @Override // androidx.activity.result.contract.ActivityResultContract
        public final Intent parseResult(int i, Intent intent) {
            if (intent == null || i != -1) {
                return null;
            }
            return intent;
        }

        private ScreenCapture() {
        }

        @Override // androidx.activity.result.contract.ActivityResultContract
        public Intent createIntent(Context context, Void r2) {
            return ((MediaProjectionManager) context.getSystemService("media_projection")).createScreenCaptureIntent();
        }
    }
}
