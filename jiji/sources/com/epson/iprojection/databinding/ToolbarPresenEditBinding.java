package com.epson.iprojection.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.epson.iprojection.R;

/* loaded from: classes.dex */
public abstract class ToolbarPresenEditBinding extends ViewDataBinding {
    public final ImageButton btnTitleDelete;
    public final Guideline leftGuideline;
    public final Guideline rightGuideline;
    public final Toolbar toolbarpresenedit;
    public final TextView txtTitlebarFilename;

    /* JADX INFO: Access modifiers changed from: protected */
    public ToolbarPresenEditBinding(Object obj, View view, int i, ImageButton imageButton, Guideline guideline, Guideline guideline2, Toolbar toolbar, TextView textView) {
        super(obj, view, i);
        this.btnTitleDelete = imageButton;
        this.leftGuideline = guideline;
        this.rightGuideline = guideline2;
        this.toolbarpresenedit = toolbar;
        this.txtTitlebarFilename = textView;
    }

    public static ToolbarPresenEditBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarPresenEditBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ToolbarPresenEditBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_presen_edit, viewGroup, z, obj);
    }

    public static ToolbarPresenEditBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarPresenEditBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ToolbarPresenEditBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_presen_edit, null, false, obj);
    }

    public static ToolbarPresenEditBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarPresenEditBinding bind(View view, Object obj) {
        return (ToolbarPresenEditBinding) bind(obj, view, R.layout.toolbar_presen_edit);
    }
}
