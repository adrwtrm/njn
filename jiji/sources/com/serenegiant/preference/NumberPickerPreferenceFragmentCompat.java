package com.serenegiant.preference;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceDialogFragmentCompat;

/* loaded from: classes2.dex */
public class NumberPickerPreferenceFragmentCompat extends PreferenceDialogFragmentCompat {
    private static final boolean DEBUG = false;
    private static final String SAVE_STATE_MAX_VALUE = "NumberPickerPreferenceFragment.maxValue";
    private static final String SAVE_STATE_MIN_VALUE = "NumberPickerPreferenceFragment.minValue";
    private static final String SAVE_STATE_VALUE = "NumberPickerPreferenceFragment.value";
    private static final String TAG = "NumberPickerPreferenceFragmentCompat";
    private boolean changed;
    private int mMaxValue;
    private int mMinValue;
    private final NumberPicker.OnValueChangeListener mOnValueChangeListener = new NumberPicker.OnValueChangeListener() { // from class: com.serenegiant.preference.NumberPickerPreferenceFragmentCompat.1
        @Override // android.widget.NumberPicker.OnValueChangeListener
        public void onValueChange(NumberPicker numberPicker, int i, int i2) {
            if (i != i2) {
                NumberPickerPreferenceFragmentCompat.this.changed = true;
            }
            NumberPickerPreferenceFragmentCompat.this.mValue = i2;
        }
    };
    private int mValue;

    public static NumberPickerPreferenceFragmentCompat newInstance(String str) {
        NumberPickerPreferenceFragmentCompat numberPickerPreferenceFragmentCompat = new NumberPickerPreferenceFragmentCompat();
        Bundle bundle = new Bundle(1);
        bundle.putString("key", str);
        numberPickerPreferenceFragmentCompat.setArguments(bundle);
        return numberPickerPreferenceFragmentCompat;
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            NumberPickerPreferenceV7 numberPickerPreference = getNumberPickerPreference();
            this.mMinValue = numberPickerPreference.getMinValue();
            this.mMaxValue = numberPickerPreference.getMaxValue();
            this.mValue = numberPickerPreference.getValue();
            return;
        }
        this.mMinValue = bundle.getInt(SAVE_STATE_MIN_VALUE, 0);
        this.mMaxValue = bundle.getInt(SAVE_STATE_MAX_VALUE, 100);
        this.mValue = bundle.getInt(SAVE_STATE_VALUE, 0);
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(SAVE_STATE_MIN_VALUE, this.mMinValue);
        bundle.putInt(SAVE_STATE_MAX_VALUE, this.mMaxValue);
        bundle.putInt(SAVE_STATE_VALUE, this.mValue);
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat
    public void onDialogClosed(boolean z) {
        NumberPickerPreferenceV7 numberPickerPreference = getNumberPickerPreference();
        if ((z || this.changed) && numberPickerPreference.callChangeListener(Integer.valueOf(this.mValue))) {
            numberPickerPreference.setValue(this.mValue);
        }
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat
    protected View onCreateDialogView(Context context) {
        NumberPicker numberPicker = new NumberPicker(context);
        numberPicker.setOnValueChangedListener(this.mOnValueChangeListener);
        numberPicker.setMinValue(this.mMinValue);
        numberPicker.setMaxValue(this.mMaxValue);
        numberPicker.setValue(this.mValue);
        this.changed = false;
        return numberPicker;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.preference.PreferenceDialogFragmentCompat
    public void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);
        builder.setPositiveButton((CharSequence) null, (DialogInterface.OnClickListener) null);
    }

    protected NumberPickerPreferenceV7 getNumberPickerPreference() {
        return (NumberPickerPreferenceV7) getPreference();
    }
}
