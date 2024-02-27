package com.serenegiant.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.serenegiant.common.R;
import com.serenegiant.utils.TypedArrayUtils;
import com.serenegiant.widget.ItemPicker;

/* loaded from: classes2.dex */
public final class ItemPickerPreferenceV7 extends Preference {
    private static final boolean DEBUG = false;
    private static final String TAG = "ItemPickerPreferenceV7";
    private ItemPicker mItemPicker;
    private int mMaxValue;
    private int mMinValue;
    private final ItemPicker.OnChangedListener mOnChangeListener;
    private int preferenceValue;

    public ItemPickerPreferenceV7(Context context) {
        this(context, null);
    }

    public ItemPickerPreferenceV7(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R.attr.dialogPreferenceStyle, 16842897));
    }

    public ItemPickerPreferenceV7(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMinValue = 1;
        this.mMaxValue = 100;
        this.mOnChangeListener = new ItemPicker.OnChangedListener() { // from class: com.serenegiant.preference.ItemPickerPreferenceV7.1
            @Override // com.serenegiant.widget.ItemPicker.OnChangedListener
            public void onChanged(ItemPicker itemPicker, int i2, int i3) {
                ItemPickerPreferenceV7.this.callChangeListener(Integer.valueOf(i3));
                ItemPickerPreferenceV7.this.preferenceValue = i3;
                ItemPickerPreferenceV7 itemPickerPreferenceV7 = ItemPickerPreferenceV7.this;
                itemPickerPreferenceV7.persistInt(itemPickerPreferenceV7.preferenceValue);
            }
        };
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        RelativeLayout relativeLayout;
        super.onBindViewHolder(preferenceViewHolder);
        if (preferenceViewHolder.itemView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) preferenceViewHolder.itemView;
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                if (childAt instanceof RelativeLayout) {
                    relativeLayout = (RelativeLayout) childAt;
                    break;
                }
            }
        }
        relativeLayout = null;
        if (relativeLayout == null) {
            throw new RuntimeException("unexpected item view type");
        }
        this.mItemPicker = new ItemPicker(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(3, 16908304);
        relativeLayout.addView(this.mItemPicker, layoutParams);
        this.mItemPicker.setRange(this.mMinValue, this.mMaxValue);
        this.mItemPicker.setValue(this.preferenceValue);
        int value = this.mItemPicker.getValue();
        this.preferenceValue = value;
        persistInt(value);
        this.mItemPicker.setOnChangeListener(this.mOnChangeListener);
    }

    @Override // androidx.preference.Preference
    protected Object onGetDefaultValue(TypedArray typedArray, int i) {
        return Integer.valueOf(typedArray.getInt(i, 0));
    }

    @Override // androidx.preference.Preference
    protected void onSetInitialValue(Object obj) {
        int i = this.preferenceValue;
        if (obj instanceof Integer) {
            i = ((Integer) obj).intValue();
        } else if (obj instanceof String) {
            try {
                i = Integer.parseInt((String) obj);
            } catch (Exception unused) {
            }
        }
        this.preferenceValue = i;
        persistInt(i);
    }

    public void setRange(int i, int i2) {
        if (i > i2) {
            i2 = i;
            i = i2;
        }
        if (this.mMinValue == i && this.mMaxValue == i2) {
            return;
        }
        this.mMaxValue = i2;
        this.mMinValue = i;
        ItemPicker itemPicker = this.mItemPicker;
        if (itemPicker != null) {
            itemPicker.setRange(i, i2);
            this.mItemPicker.setValue(this.preferenceValue);
            int value = this.mItemPicker.getValue();
            this.preferenceValue = value;
            persistInt(value);
        }
    }
}
