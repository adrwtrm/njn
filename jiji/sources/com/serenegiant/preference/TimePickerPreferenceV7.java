package com.serenegiant.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;
import com.serenegiant.common.R;
import com.serenegiant.utils.TypedArrayUtils;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/* loaded from: classes2.dex */
public class TimePickerPreferenceV7 extends DialogPreferenceV7 {
    private final Calendar calendar;
    private final long mDefaultValue;
    private TimePicker picker;

    public TimePickerPreferenceV7(Context context) {
        this(context, null);
    }

    public TimePickerPreferenceV7(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R.attr.dialogPreferenceStyle, 16842897));
    }

    public TimePickerPreferenceV7(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.picker = null;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.TimePicker, i, 0);
        this.mDefaultValue = obtainStyledAttributes.getFloat(R.styleable.TimePicker_TimePickerDefaultValue, -1.0f);
        obtainStyledAttributes.recycle();
        setPositiveButtonText(17039370);
        setNegativeButtonText(17039360);
        this.calendar = new GregorianCalendar();
    }

    @Override // com.serenegiant.preference.DialogPreferenceV7
    protected View onCreateDialogView() {
        TimePicker timePicker = new TimePicker(getContext());
        this.picker = timePicker;
        timePicker.setIs24HourView(true);
        return this.picker;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.serenegiant.preference.DialogPreferenceV7
    public void onBindDialogView(View view) {
        super.onBindDialogView(view);
        this.picker.setCurrentHour(Integer.valueOf(this.calendar.get(11)));
        this.picker.setCurrentMinute(Integer.valueOf(this.calendar.get(12)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.serenegiant.preference.DialogPreferenceV7
    public void onDialogClosed(boolean z) {
        super.onDialogClosed(z);
        if (z) {
            this.calendar.set(11, this.picker.getCurrentHour().intValue());
            this.calendar.set(12, this.picker.getCurrentMinute().intValue());
            setSummary(getSummary());
            if (callChangeListener(Long.valueOf(this.calendar.getTimeInMillis()))) {
                persistLong(this.calendar.getTimeInMillis());
                notifyChanged();
            }
        }
    }

    @Override // androidx.preference.Preference
    protected Object onGetDefaultValue(TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    @Override // androidx.preference.Preference
    protected void onSetInitialValue(Object obj) {
        long j = this.mDefaultValue;
        if (j <= 0) {
            j = System.currentTimeMillis();
        }
        if (obj instanceof String) {
            j = Long.parseLong((String) obj);
        } else if (obj instanceof Long) {
            j = ((Long) obj).longValue();
        }
        this.calendar.setTimeInMillis(j);
        persistLong(this.calendar.getTimeInMillis());
        setSummary(getSummary());
    }

    @Override // androidx.preference.Preference
    public CharSequence getSummary() {
        if (this.calendar == null) {
            return super.getSummary();
        }
        return DateFormat.getTimeFormat(getContext()).format(new Date(this.calendar.getTimeInMillis()));
    }
}
