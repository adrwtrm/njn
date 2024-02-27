package com.serenegiant.dialog;

import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import com.serenegiant.system.BuildCheck;

/* loaded from: classes2.dex */
public abstract class DialogFragmentEx extends DialogFragment {
    protected static final String ARGS_KEY_ID_MESSAGE = "message";
    protected static final String ARGS_KEY_ID_TITLE = "title";
    protected static final String ARGS_KEY_REQUEST_CODE = "requestCode";
    protected static final String ARGS_KEY_TAG = "tag";
    private static final String TAG = "DialogFragmentEx";

    protected void internalOnPause() {
    }

    protected void internalOnResume() {
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            bundle.putAll(arguments);
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (BuildCheck.isAndroid7()) {
            internalOnResume();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (BuildCheck.isAndroid7()) {
            return;
        }
        internalOnResume();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPause() {
        if (!BuildCheck.isAndroid7()) {
            internalOnPause();
        }
        super.onPause();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStop() {
        if (BuildCheck.isAndroid7()) {
            internalOnPause();
        }
        super.onStop();
    }

    protected void popBackStack() {
        FragmentActivity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        try {
            getParentFragmentManager().popBackStack();
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }
}
