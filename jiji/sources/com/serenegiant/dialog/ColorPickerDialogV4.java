package com.serenegiant.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.serenegiant.common.R;
import com.serenegiant.widget.ColorPickerView;

/* loaded from: classes2.dex */
public class ColorPickerDialogV4 extends DialogFragmentEx {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_COLOR = -1;
    private static final String KEY_COLOR_CURRENT = "current_color";
    private static final String KEY_COLOR_INIT = "initial_color";
    private static final String TAG = "ColorPickerDialog";
    private boolean isCanceled;
    private OnColorChangedListener mListener;
    private int mRequestCode;
    private int mTitleResId;
    private int mInitialColor = -1;
    private int mCurrentColor = -1;
    private final ColorPickerView.ColorPickerListener mColorPickerListener = new ColorPickerView.ColorPickerListener() { // from class: com.serenegiant.dialog.ColorPickerDialogV4.1
        @Override // com.serenegiant.widget.ColorPickerView.ColorPickerListener
        public void onColorChanged(ColorPickerView colorPickerView, int i) {
            if (ColorPickerDialogV4.this.mCurrentColor != i) {
                ColorPickerDialogV4.this.mCurrentColor = i;
                if (ColorPickerDialogV4.this.mListener != null) {
                    OnColorChangedListener onColorChangedListener = ColorPickerDialogV4.this.mListener;
                    ColorPickerDialogV4 colorPickerDialogV4 = ColorPickerDialogV4.this;
                    onColorChangedListener.onColorChanged(colorPickerDialogV4, colorPickerDialogV4.mRequestCode, i);
                }
            }
        }
    };
    private final DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() { // from class: com.serenegiant.dialog.ColorPickerDialogV4.2
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -2) {
                dialogInterface.cancel();
            } else if (i != -1) {
            } else {
                dialogInterface.dismiss();
            }
        }
    };

    /* loaded from: classes2.dex */
    public interface OnColorChangedListener {
        void onCancel(ColorPickerDialogV4 colorPickerDialogV4, int i);

        void onColorChanged(ColorPickerDialogV4 colorPickerDialogV4, int i, int i2);

        void onDismiss(ColorPickerDialogV4 colorPickerDialogV4, int i, int i2);
    }

    public static ColorPickerDialogV4 show(FragmentActivity fragmentActivity, int i, int i2, int i3) {
        ColorPickerDialogV4 newInstance = newInstance(i, i2, i3);
        newInstance.show(fragmentActivity.getSupportFragmentManager(), TAG);
        return newInstance;
    }

    public static ColorPickerDialogV4 show(Fragment fragment, int i, int i2, int i3) {
        ColorPickerDialogV4 newInstance = newInstance(i, i2, i3);
        newInstance.setTargetFragment(fragment, 0);
        newInstance.show(fragment.getParentFragmentManager(), TAG);
        return newInstance;
    }

    public static ColorPickerDialogV4 newInstance(int i, int i2, int i3) {
        ColorPickerDialogV4 colorPickerDialogV4 = new ColorPickerDialogV4();
        colorPickerDialogV4.setArguments(i, i2, i3);
        return colorPickerDialogV4;
    }

    public void setArguments(int i, int i2, int i3) {
        Bundle arguments = getArguments();
        if (arguments == null) {
            arguments = new Bundle();
        }
        arguments.putInt("requestCode", i);
        arguments.putInt("title", i2);
        arguments.putInt(KEY_COLOR_INIT, i3);
        arguments.remove(KEY_COLOR_CURRENT);
        setArguments(arguments);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle requireArguments = requireArguments();
        this.mRequestCode = requireArguments.getInt("requestCode");
        this.mTitleResId = requireArguments.getInt("title");
        int i = requireArguments.getInt(KEY_COLOR_INIT, -1);
        this.mInitialColor = i;
        this.mCurrentColor = i;
        if (bundle != null) {
            this.mCurrentColor = bundle.getInt(KEY_COLOR_CURRENT, i);
        }
    }

    @Override // com.serenegiant.dialog.DialogFragmentEx, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(KEY_COLOR_CURRENT, this.mCurrentColor);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.isCanceled = false;
        try {
            this.mListener = (OnColorChangedListener) getTargetFragment();
        } catch (ClassCastException | NullPointerException unused) {
        }
        if (this.mListener == null) {
            try {
                this.mListener = (OnColorChangedListener) getParentFragment();
            } catch (ClassCastException | NullPointerException unused2) {
            }
        }
        if (this.mListener == null) {
            try {
                this.mListener = (OnColorChangedListener) context;
            } catch (ClassCastException | NullPointerException unused3) {
            }
        }
        if (this.mListener == null) {
            Log.w(TAG, "must implement OnColorChangedListener");
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        FragmentActivity requireActivity = requireActivity();
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(requireActivity).inflate(R.layout.color_picker, (ViewGroup) null);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        ColorPickerView colorPickerView = new ColorPickerView(requireActivity);
        colorPickerView.setColor(this.mCurrentColor);
        colorPickerView.setColorPickerListener(this.mColorPickerListener);
        frameLayout.addView(colorPickerView, layoutParams);
        AlertDialog.Builder negativeButton = new AlertDialog.Builder(requireActivity, getTheme()).setPositiveButton(R.string.color_picker_select, this.mOnClickListener).setNegativeButton(R.string.color_picker_cancel, this.mOnClickListener);
        int i = this.mTitleResId;
        if (i == 0) {
            i = R.string.color_picker_default_title;
        }
        AlertDialog create = negativeButton.setTitle(i).setView(frameLayout).create();
        create.setCanceledOnTouchOutside(true);
        return create;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        super.onCancel(dialogInterface);
        this.isCanceled = true;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        OnColorChangedListener onColorChangedListener = this.mListener;
        if (onColorChangedListener != null) {
            if (this.isCanceled) {
                onColorChangedListener.onCancel(this, this.mRequestCode);
            } else {
                onColorChangedListener.onDismiss(this, this.mRequestCode, this.mCurrentColor);
            }
        }
    }
}
