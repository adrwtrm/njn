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
public class PermissionDescriptionDialogV4 extends DialogFragmentEx {
    private static final String ARGS_KEY_PERMISSIONS = "permissions";
    private static final String TAG = "PermissionDescriptionDialogV4";
    private DialogResultListener mDialogListener;
    private final DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() { // from class: com.serenegiant.dialog.PermissionDescriptionDialogV4.1
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            PermissionDescriptionDialogV4.this.callOnMessageDialogResult(i == -1);
        }
    };

    /* loaded from: classes2.dex */
    public interface DialogResultListener {
        void onDialogResult(PermissionDescriptionDialogV4 permissionDescriptionDialogV4, int i, String[] strArr, boolean z);
    }

    public static PermissionDescriptionDialogV4 showDialog(FragmentActivity fragmentActivity, int i, int i2, int i3, String[] strArr) throws IllegalStateException {
        PermissionDescriptionDialogV4 newInstance = newInstance(i, i2, i3, strArr);
        newInstance.show(fragmentActivity.getSupportFragmentManager(), TAG);
        return newInstance;
    }

    public static PermissionDescriptionDialogV4 showDialog(Fragment fragment, int i, int i2, int i3, String[] strArr) throws IllegalStateException {
        PermissionDescriptionDialogV4 newInstance = newInstance(i, i2, i3, strArr);
        newInstance.setTargetFragment(fragment, fragment.getId());
        newInstance.show(fragment.requireFragmentManager(), TAG);
        return newInstance;
    }

    public static PermissionDescriptionDialogV4 newInstance(int i, int i2, int i3, String[] strArr) {
        PermissionDescriptionDialogV4 permissionDescriptionDialogV4 = new PermissionDescriptionDialogV4();
        Bundle bundle = new Bundle();
        bundle.putInt("requestCode", i);
        bundle.putInt("title", i2);
        bundle.putInt("message", i3);
        bundle.putStringArray(ARGS_KEY_PERMISSIONS, strArr);
        permissionDescriptionDialogV4.setArguments(bundle);
        return permissionDescriptionDialogV4;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DialogResultListener) {
            this.mDialogListener = (DialogResultListener) context;
        }
        if (this.mDialogListener == null) {
            Fragment targetFragment = getTargetFragment();
            if (targetFragment instanceof DialogResultListener) {
                this.mDialogListener = (DialogResultListener) targetFragment;
            }
        }
        if (this.mDialogListener == null && BuildCheck.isAndroid4_2()) {
            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof DialogResultListener) {
                this.mDialogListener = (DialogResultListener) parentFragment;
            }
        }
        if (this.mDialogListener == null) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        if (bundle == null) {
            bundle = requireArguments();
        }
        int i = bundle.getInt("title");
        return new AlertDialog.Builder(requireActivity(), getTheme()).setIcon(17301543).setTitle(i).setMessage(bundle.getInt("message")).setPositiveButton(17039370, this.mOnClickListener).setNegativeButton(17039360, this.mOnClickListener).create();
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        super.onCancel(dialogInterface);
        callOnMessageDialogResult(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callOnMessageDialogResult(boolean z) throws IllegalStateException {
        Bundle requireArguments = requireArguments();
        try {
            this.mDialogListener.onDialogResult(this, requireArguments.getInt("requestCode"), requireArguments.getStringArray(ARGS_KEY_PERMISSIONS), z);
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }
}
