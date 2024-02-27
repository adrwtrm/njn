package com.serenegiant.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.preference.DialogPreference;
import com.google.android.material.timepicker.TimeModel;
import com.serenegiant.common.R;
import com.serenegiant.utils.TypedArrayUtils;
import java.util.Locale;

/* loaded from: classes2.dex */
public class NumberPickerPreferenceV7 extends DialogPreference {
    private static final boolean DEBUG = false;
    private static final String TAG = "NumberPickerPreferenceV7";
    private int mDefaultValue;
    private final int mMaxValue;
    private final int mMinValue;
    private int mValue;

    public NumberPickerPreferenceV7(Context context) {
        this(context, null);
    }

    public NumberPickerPreferenceV7(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R.attr.dialogPreferenceStyle, 16842897));
    }

    public NumberPickerPreferenceV7(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.NumberPicker, i, 0);
        this.mDefaultValue = obtainStyledAttributes.getInt(R.styleable.NumberPicker_DefaultValue, 0);
        int i2 = obtainStyledAttributes.getInt(R.styleable.NumberPicker_MinValue, 0);
        int i3 = obtainStyledAttributes.getInt(R.styleable.NumberPicker_MaxValue, 100);
        obtainStyledAttributes.recycle();
        this.mMinValue = Math.min(i2, i3);
        this.mMaxValue = Math.max(i2, i3);
        setPositiveButtonText(17039370);
        setNegativeButtonText(17039360);
    }

    @Override // androidx.preference.Preference
    protected Object onGetDefaultValue(TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    @Override // androidx.preference.Preference
    protected void onSetInitialValue(Object obj) {
        if (obj != null) {
            this.mDefaultValue = Integer.parseInt((String) obj);
        }
        this.mValue = getPersistedInt(this.mDefaultValue);
        setSummary(getSummary());
    }

    @Override // androidx.preference.Preference
    public CharSequence getSummary() {
        return String.format(Locale.US, TimeModel.NUMBER_FORMAT, Integer.valueOf(this.mValue));
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public int getValue() {
        return this.mValue;
    }

    public void setValue(int i) {
        boolean z = getValue() != i;
        if (z) {
            this.mValue = i;
            persistInt(i);
            if (z) {
                notifyChanged();
            }
        }
    }
}
