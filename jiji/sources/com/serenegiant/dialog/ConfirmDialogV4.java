package com.serenegiant.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.serenegiant.system.BuildCheck;

/* loaded from: classes2.dex */
public class ConfirmDialogV4 extends DialogFragmentEx {
    private static final String ARGS_KEY_ARGS = "ARGS_KEY_ARGS";
    private static final String ARGS_KEY_CANCELED_ON_TOUCH_OUTSIDE = "ARGS_KEY_CANCELED_ON_TOUCH_OUTSIDE";
    private static final String ARGS_KEY_MESSAGE_STRING = "ARGS_KEY_MESSAGE_STRING";
    private static final String TAG = "ConfirmDialogV4";
    private ConfirmDialogListener mListener;
    private final DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() { // from class: com.serenegiant.dialog.ConfirmDialogV4.1
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            ConfirmDialogV4.this.callOnMessageDialogResult(i);
        }
    };

    /* loaded from: classes2.dex */
    public interface ConfirmDialogListener {
        void onConfirmResult(ConfirmDialogV4 confirmDialogV4, int i, int i2, Bundle bundle);
    }

    public static ConfirmDialogV4 showDialog(FragmentActivity fragmentActivity, int i, int i2, int i3, boolean z) throws IllegalStateException {
        return showDialog(fragmentActivity, i, i2, i3, z, (Bundle) null);
    }

    public static ConfirmDialogV4 showDialog(FragmentActivity fragmentActivity, int i, int i2, int i3, boolean z, Bundle bundle) throws IllegalStateException {
        ConfirmDialogV4 newInstance = newInstance(i, i2, i3, null, z, bundle);
        newInstance.show(fragmentActivity.getSupportFragmentManager(), TAG);
        return newInstance;
    }

    public static ConfirmDialogV4 showDialog(FragmentActivity fragmentActivity, int i, int i2, CharSequence charSequence, boolean z) throws IllegalStateException {
        return showDialog(fragmentActivity, i, i2, charSequence, z, (Bundle) null);
    }

    public static ConfirmDialogV4 showDialog(FragmentActivity fragmentActivity, int i, int i2, CharSequence charSequence, boolean z, Bundle bundle) throws IllegalStateException {
        ConfirmDialogV4 newInstance = newInstance(i, i2, 0, charSequence, z, bundle);
        newInstance.show(fragmentActivity.getSupportFragmentManager(), TAG);
        return newInstance;
    }

    public static ConfirmDialogV4 showDialog(Fragment fragment, int i, int i2, int i3, boolean z) throws IllegalStateException {
        return showDialog(fragment, i, i2, i3, z, (Bundle) null);
    }

    public static ConfirmDialogV4 showDialog(Fragment fragment, int i, int i2, int i3, boolean z, Bundle bundle) throws IllegalStateException {
        ConfirmDialogV4 newInstance = newInstance(i, i2, i3, null, z, bundle);
        newInstance.setTargetFragment(fragment, fragment.getId());
        newInstance.show(fragment.getParentFragmentManager(), TAG);
        return newInstance;
    }

    public static ConfirmDialogV4 showDialog(Fragment fragment, int i, int i2, CharSequence charSequence, boolean z) throws IllegalStateException {
        return showDialog(fragment, i, i2, charSequence, z, (Bundle) null);
    }

    public static ConfirmDialogV4 showDialog(Fragment fragment, int i, int i2, CharSequence charSequence, boolean z, Bundle bundle) throws IllegalStateException {
        ConfirmDialogV4 newInstance = newInstance(i, i2, 0, charSequence, z, bundle);
        newInstance.setTargetFragment(fragment, fragment.getId());
        newInstance.show(fragment.getParentFragmentManager(), TAG);
        return newInstance;
    }

    public static ConfirmDialogV4 newInstance(int i, int i2, int i3, CharSequence charSequence, boolean z, Bundle bundle) {
        ConfirmDialogV4 confirmDialogV4 = new ConfirmDialogV4();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("requestCode", i);
        bundle2.putInt("title", i2);
        bundle2.putInt("message", i3);
        bundle2.putCharSequence(ARGS_KEY_MESSAGE_STRING, charSequence);
        bundle2.putBoolean(ARGS_KEY_CANCELED_ON_TOUCH_OUTSIDE, z);
        bundle2.putBundle(ARGS_KEY_ARGS, bundle);
        confirmDialogV4.setArguments(bundle2);
        return confirmDialogV4;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ConfirmDialogListener) {
            this.mListener = (ConfirmDialogListener) context;
        }
        if (this.mListener == null) {
            Fragment targetFragment = getTargetFragment();
            if (targetFragment instanceof ConfirmDialogListener) {
                this.mListener = (ConfirmDialogListener) targetFragment;
            }
        }
        if (this.mListener == null && BuildCheck.isAndroid4_2()) {
            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof ConfirmDialogListener) {
                this.mListener = (ConfirmDialogListener) parentFragment;
            }
        }
        if (this.mListener == null) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        if (bundle == null) {
            bundle = requireArguments();
        }
        int i = bundle.getInt("title");
        int i2 = bundle.getInt("message");
        CharSequence charSequence = bundle.getCharSequence(ARGS_KEY_MESSAGE_STRING);
        boolean z = bundle.getBoolean(ARGS_KEY_CANCELED_ON_TOUCH_OUTSIDE);
        AlertDialog.Builder negativeButton = new AlertDialog.Builder(requireContext(), getTheme()).setIcon(17301543).setTitle(i).setPositiveButton(17039370, this.mOnClickListener).setNegativeButton(17039360, this.mOnClickListener);
        if (i2 != 0) {
            negativeButton.setMessage(i2);
        } else {
            negativeButton.setMessage(charSequence);
        }
        AlertDialog create = negativeButton.create();
        create.setCanceledOnTouchOutside(z);
        return create;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        super.onCancel(dialogInterface);
        callOnMessageDialogResult(-2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callOnMessageDialogResult(int i) throws IllegalStateException {
        Bundle requireArguments = requireArguments();
        Bundle bundle = requireArguments.getBundle(ARGS_KEY_ARGS);
        try {
            this.mListener.onConfirmResult(this, requireArguments.getInt("requestCode"), i, bundle);
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }
}
