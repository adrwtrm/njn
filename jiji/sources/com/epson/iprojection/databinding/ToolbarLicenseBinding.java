package com.epson.iprojection.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.epson.iprojection.R;

/* loaded from: classes.dex */
public abstract class ToolbarLicenseBinding extends ViewDataBinding {
    public final View include3;
    public final Guideline leftGuideline;
    public final Toolbar toolbarLicence;

    /* JADX INFO: Access modifiers changed from: protected */
    public ToolbarLicenseBinding(Object obj, View view, int i, View view2, Guideline guideline, Toolbar toolbar) {
        super(obj, view, i);
        this.include3 = view2;
        this.leftGuideline = guideline;
        this.toolbarLicence = toolbar;
    }

    public static ToolbarLicenseBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarLicenseBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ToolbarLicenseBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_license, viewGroup, z, obj);
    }

    public static ToolbarLicenseBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarLicenseBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ToolbarLicenseBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_license, null, false, obj);
    }

    public static ToolbarLicenseBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarLicenseBinding bind(View view, Object obj) {
        return (ToolbarLicenseBinding) bind(obj, view, R.layout.toolbar_license);
    }
}
