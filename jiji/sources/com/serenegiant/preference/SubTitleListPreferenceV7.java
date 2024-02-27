package com.serenegiant.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceViewHolder;
import com.serenegiant.common.R;
import com.serenegiant.utils.TypedArrayUtils;

/* loaded from: classes2.dex */
public final class SubTitleListPreferenceV7 extends ListPreference {
    private static final boolean DEBUG = false;
    private static final String TAG = "SubTitlePreferenceV7";
    private CharSequence mSubTitle;
    private final int mSubTitleLayoutId;
    private TextView mSubTitleTextView;
    private final int mSubTitleTvId;
    private int mSubTitleVisibility;

    public SubTitleListPreferenceV7(Context context) {
        this(context, null);
    }

    public SubTitleListPreferenceV7(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R.attr.preferenceStyle, 16842894));
    }

    public SubTitleListPreferenceV7(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.SubTitlePreference, i, 0);
        this.mSubTitleLayoutId = obtainStyledAttributes.getResourceId(R.styleable.SubTitlePreference_subtitle_layout, R.layout.subtitle);
        this.mSubTitleTvId = obtainStyledAttributes.getResourceId(R.styleable.SubTitlePreference_subtitle_id, R.id.subtitle);
        this.mSubTitle = obtainStyledAttributes.getString(R.styleable.SubTitlePreference_subtitle);
        this.mSubTitleVisibility = obtainStyledAttributes.getInteger(R.styleable.SubTitlePreference_subtitle_visibility, 0);
        obtainStyledAttributes.recycle();
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        ViewGroup viewGroup;
        View inflate;
        super.onBindViewHolder(preferenceViewHolder);
        if (this.mSubTitleLayoutId == 0) {
            return;
        }
        RelativeLayout relativeLayout = null;
        if (preferenceViewHolder.itemView instanceof ViewGroup) {
            viewGroup = (ViewGroup) preferenceViewHolder.itemView;
            int childCount = viewGroup.getChildCount() - 1;
            while (true) {
                if (childCount < 0) {
                    break;
                }
                View childAt = viewGroup.getChildAt(childCount);
                if (childAt instanceof RelativeLayout) {
                    relativeLayout = (RelativeLayout) childAt;
                    break;
                }
                childCount--;
            }
        } else {
            viewGroup = null;
        }
        if (relativeLayout == null || (inflate = LayoutInflater.from(getContext()).inflate(this.mSubTitleLayoutId, viewGroup, false)) == null) {
            return;
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(3, 16908310);
        relativeLayout.addView(inflate, layoutParams);
        View findViewById = relativeLayout.findViewById(16908304);
        if (findViewById != null) {
            int id = inflate.getId();
            if (id == 0) {
                id = this.mSubTitleTvId;
            }
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams2.addRule(3, id);
            findViewById.setLayoutParams(layoutParams2);
        }
        this.mSubTitleTextView = (TextView) inflate.findViewById(this.mSubTitleTvId);
        setSubtitle(this.mSubTitle);
    }

    public void setSubtitle(int i) {
        TextView textView = this.mSubTitleTextView;
        if (textView != null) {
            textView.setText(i);
            CharSequence text = this.mSubTitleTextView.getText();
            this.mSubTitle = text;
            this.mSubTitleTextView.setVisibility(TextUtils.isEmpty(text) ? 8 : this.mSubTitleVisibility);
        }
    }

    public void setSubtitle(CharSequence charSequence) {
        this.mSubTitle = charSequence;
        TextView textView = this.mSubTitleTextView;
        if (textView != null) {
            textView.setText(charSequence);
            this.mSubTitleTextView.setVisibility(TextUtils.isEmpty(this.mSubTitle) ? 8 : this.mSubTitleVisibility);
        }
    }

    public void setSubTitleVisibility(int i) {
        this.mSubTitleVisibility = i;
        TextView textView = this.mSubTitleTextView;
        if (textView != null) {
            textView.setVisibility(i);
        }
    }
}
