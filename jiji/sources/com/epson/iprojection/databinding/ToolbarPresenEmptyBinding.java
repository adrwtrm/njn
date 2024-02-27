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
public abstract class ToolbarPresenEmptyBinding extends ViewDataBinding {
    public final Guideline leftGuideline;
    public final View modAndLock;
    public final Toolbar toolbarpresenempty;
    public final TextView txtTitlebarFilename;

    /* JADX INFO: Access modifiers changed from: protected */
    public ToolbarPresenEmptyBinding(Object obj, View view, int i, Guideline guideline, View view2, Toolbar toolbar, TextView textView) {
        super(obj, view, i);
        this.leftGuideline = guideline;
        this.modAndLock = view2;
        this.toolbarpresenempty = toolbar;
        this.txtTitlebarFilename = textView;
    }

    public static ToolbarPresenEmptyBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarPresenEmptyBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ToolbarPresenEmptyBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_presen_empty, viewGroup, z, obj);
    }

    public static ToolbarPresenEmptyBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarPresenEmptyBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ToolbarPresenEmptyBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_presen_empty, null, false, obj);
    }

    public static ToolbarPresenEmptyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarPresenEmptyBinding bind(View view, Object obj) {
        return (ToolbarPresenEmptyBinding) bind(obj, view, R.layout.toolbar_presen_empty);
    }
}
