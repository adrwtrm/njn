package com.serenegiant.preference;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.serenegiant.common.R;
import com.serenegiant.utils.TypedArrayUtils;

/* loaded from: classes2.dex */
public class MultilineLabelPreferenceV7 extends Preference {
    private static final boolean DEBUG = false;
    private static final String TAG = "MultilineLabelPreferenceV7";

    public MultilineLabelPreferenceV7(Context context) {
        this(context, null);
    }

    public MultilineLabelPreferenceV7(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R.attr.preferenceStyle, 16842894));
    }

    public MultilineLabelPreferenceV7(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        try {
            ((TextView) preferenceViewHolder.findViewById(16908304)).setSingleLine(false);
        } catch (Exception unused) {
        }
    }
}
