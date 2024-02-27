package com.serenegiant.preference;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.preference.PreferenceViewHolder;
import com.serenegiant.common.R;
import com.serenegiant.utils.TypedArrayUtils;
import com.serenegiant.widget.ColorPickerView;

/* loaded from: classes2.dex */
public class ColorPickerDialogPreferenceV7 extends DialogPreferenceV7 {
    private static final String TAG = "ColorPickerDialogPreferenceV7";
    private boolean changed;
    private int mColor;
    private final ColorPickerView.ColorPickerListener mColorPickerListener;

    public ColorPickerDialogPreferenceV7(Context context) {
        this(context, null);
    }

    public ColorPickerDialogPreferenceV7(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R.attr.dialogPreferenceStyle, 16842897));
    }

    public ColorPickerDialogPreferenceV7(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mColor = -65536;
        this.mColorPickerListener = new ColorPickerView.ColorPickerListener() { // from class: com.serenegiant.preference.ColorPickerDialogPreferenceV7.1
            @Override // com.serenegiant.widget.ColorPickerView.ColorPickerListener
            public void onColorChanged(ColorPickerView colorPickerView, int i2) {
                if (ColorPickerDialogPreferenceV7.this.mColor != i2) {
                    ColorPickerDialogPreferenceV7.this.mColor = i2;
                    ColorPickerDialogPreferenceV7.this.changed = true;
                }
            }
        };
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mColor = getPersistedInt(this.mColor);
    }

    @Override // com.serenegiant.preference.DialogPreferenceV7
    protected View onCreateDialogView() {
        ColorPickerView colorPickerView = new ColorPickerView(getContext());
        colorPickerView.setColorPickerListener(this.mColorPickerListener);
        return colorPickerView;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.serenegiant.preference.DialogPreferenceV7
    public void onBindDialogView(View view) {
        super.onBindDialogView(view);
        int persistedInt = getPersistedInt(this.mColor);
        this.mColor = persistedInt;
        this.changed = false;
        if (view instanceof ColorPickerView) {
            ((ColorPickerView) view).setColor(persistedInt);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.serenegiant.preference.DialogPreferenceV7
    public void onDialogClosed(boolean z) {
        if (z || this.changed) {
            setSummary(getSummary());
            if (callChangeListener(Integer.valueOf(this.mColor))) {
                persistInt(this.mColor);
                notifyChanged();
            }
        }
        super.onDialogClosed(z || this.changed);
    }

    @Override // androidx.preference.Preference
    protected void onSetInitialValue(Object obj) {
        if (obj != null) {
            this.mColor = ((Integer) obj).intValue();
        }
        persistInt(this.mColor);
    }

    public int getValue() {
        return this.mColor;
    }
}
