package com.epson.iprojection.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.epson.iprojection.R;

/* loaded from: classes.dex */
public abstract class ToolbarOtherBinding extends ViewDataBinding {
    public final View include;
    public final Guideline leftGuideline;
    public final Toolbar toolbarOther;
    public final TextView txtTitlebarFilename;

    /* JADX INFO: Access modifiers changed from: protected */
    public ToolbarOtherBinding(Object obj, View view, int i, View view2, Guideline guideline, Toolbar toolbar, TextView textView) {
        super(obj, view, i);
        this.include = view2;
        this.leftGuideline = guideline;
        this.toolbarOther = toolbar;
        this.txtTitlebarFilename = textView;
    }

    public static ToolbarOtherBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarOtherBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ToolbarOtherBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_other, viewGroup, z, obj);
    }

    public static ToolbarOtherBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarOtherBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ToolbarOtherBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_other, null, false, obj);
    }

    public static ToolbarOtherBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarOtherBinding bind(View view, Object obj) {
        return (ToolbarOtherBinding) bind(obj, view, R.layout.toolbar_other);
    }
}
