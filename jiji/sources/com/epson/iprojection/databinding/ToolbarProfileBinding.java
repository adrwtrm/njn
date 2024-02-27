package com.epson.iprojection.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.epson.iprojection.R;

/* loaded from: classes.dex */
public abstract class ToolbarProfileBinding extends ViewDataBinding {
    public final Button btnTitleSearch;
    public final Guideline leftGuideline;
    public final View modAndLock;
    public final Guideline rightGuideline;
    public final Toolbar toolbarProfile;
    public final TextView txtTitlebarFilename;

    /* JADX INFO: Access modifiers changed from: protected */
    public ToolbarProfileBinding(Object obj, View view, int i, Button button, Guideline guideline, View view2, Guideline guideline2, Toolbar toolbar, TextView textView) {
        super(obj, view, i);
        this.btnTitleSearch = button;
        this.leftGuideline = guideline;
        this.modAndLock = view2;
        this.rightGuideline = guideline2;
        this.toolbarProfile = toolbar;
        this.txtTitlebarFilename = textView;
    }

    public static ToolbarProfileBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarProfileBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ToolbarProfileBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_profile, viewGroup, z, obj);
    }

    public static ToolbarProfileBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarProfileBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ToolbarProfileBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_profile, null, false, obj);
    }

    public static ToolbarProfileBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarProfileBinding bind(View view, Object obj) {
        return (ToolbarProfileBinding) bind(obj, view, R.layout.toolbar_profile);
    }
}
