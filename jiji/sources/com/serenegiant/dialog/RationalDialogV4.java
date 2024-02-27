package com.serenegiant.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.epson.iprojection.ui.activities.pjselect.permission.audio.PermissionAudioPresenter;
import com.serenegiant.common.R;
import com.serenegiant.system.BuildCheck;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class RationalDialogV4 extends DialogFragmentEx {
    private static final String ARGS_KEY_PERMISSIONS = "permissions";
    private static final boolean DEBUG = false;
    private static final String TAG = "RationalDialogV4";
    private static final Map<String, RationalResource> mRationalResources;
    private DialogResultListener mDialogListener;
    private final DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() { // from class: com.serenegiant.dialog.RationalDialogV4.1
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            RationalDialogV4.this.callOnMessageDialogResult(i == -1);
        }
    };

    /* loaded from: classes2.dex */
    public interface DialogResultListener {
        void onDialogResult(RationalDialogV4 rationalDialogV4, String[] strArr, boolean z);
    }

    /* loaded from: classes2.dex */
    private static class RationalResource {
        public final int messageRes;
        public final int titleRes;

        public RationalResource(int i, int i2) {
            this.titleRes = i;
            this.messageRes = i2;
        }
    }

    static {
        HashMap hashMap = new HashMap();
        mRationalResources = hashMap;
        hashMap.put("android.permission.CAMERA", new RationalResource(R.string.permission_title, R.string.permission_camera_reason));
        hashMap.put("android.permission.WRITE_EXTERNAL_STORAGE", new RationalResource(R.string.permission_title, R.string.permission_ext_storage_reason));
        hashMap.put("android.permission.READ_EXTERNAL_STORAGE", new RationalResource(R.string.permission_title, R.string.permission_read_ext_storage_reason));
        hashMap.put(PermissionAudioPresenter.permission, new RationalResource(R.string.permission_title, R.string.permission_audio_recording_reason));
        hashMap.put("android.permission.ACCESS_COARSE_LOCATION", new RationalResource(R.string.permission_title, R.string.permission_location_reason));
        hashMap.put("android.permission.ACCESS_FINE_LOCATION", new RationalResource(R.string.permission_title, R.string.permission_location_reason));
        hashMap.put("android.permission.ACCESS_NETWORK_STATE", new RationalResource(R.string.permission_title, R.string.permission_network_state_reason));
        hashMap.put("android.permission.CHANGE_NETWORK_STATE", new RationalResource(R.string.permission_title, R.string.permission_change_network_state_reason));
        if (Build.VERSION.SDK_INT >= 29) {
            hashMap.put("android.permission.ACCESS_BACKGROUND_LOCATION", new RationalResource(R.string.permission_title, R.string.permission_access_background_location));
        }
    }

    public static RationalDialogV4 showDialog(FragmentActivity fragmentActivity, String str) {
        Map<String, RationalResource> map = mRationalResources;
        RationalResource rationalResource = map.containsKey(str) ? map.get(str) : null;
        if (rationalResource != null) {
            return showDialog(fragmentActivity, rationalResource.titleRes, rationalResource.messageRes, new String[]{str});
        }
        return null;
    }

    public static RationalDialogV4 showDialog(Fragment fragment, String str) {
        Map<String, RationalResource> map = mRationalResources;
        RationalResource rationalResource = map.containsKey(str) ? map.get(str) : null;
        if (rationalResource != null) {
            return showDialog(fragment, rationalResource.titleRes, rationalResource.messageRes, new String[]{str});
        }
        return null;
    }

    public static RationalDialogV4 showDialog(FragmentActivity fragmentActivity, int i, int i2, String[] strArr) throws IllegalStateException {
        RationalDialogV4 newInstance = newInstance(i, i2, strArr);
        newInstance.show(fragmentActivity.getSupportFragmentManager(), TAG);
        return newInstance;
    }

    public static RationalDialogV4 showDialog(Fragment fragment, int i, int i2, String[] strArr) throws IllegalStateException {
        RationalDialogV4 newInstance = newInstance(i, i2, strArr);
        newInstance.setTargetFragment(fragment, fragment.getId());
        newInstance.show(fragment.getParentFragmentManager(), TAG);
        return newInstance;
    }

    public static RationalDialogV4 newInstance(int i, int i2, String[] strArr) {
        RationalDialogV4 rationalDialogV4 = new RationalDialogV4();
        Bundle bundle = new Bundle();
        bundle.putInt("title", i);
        bundle.putInt("message", i2);
        bundle.putStringArray(ARGS_KEY_PERMISSIONS, strArr);
        rationalDialogV4.setArguments(bundle);
        return rationalDialogV4;
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
        try {
            this.mDialogListener.onDialogResult(this, requireArguments().getStringArray(ARGS_KEY_PERMISSIONS), z);
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }
}
