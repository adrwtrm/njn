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
public abstract class ToolbarNotavailableMirroringBinding extends ViewDataBinding {
    public final Guideline leftGuideline;
    public final Toolbar toolbarNotavailableMirroring;
    public final TextView txtTitlebarFilename;

    /* JADX INFO: Access modifiers changed from: protected */
    public ToolbarNotavailableMirroringBinding(Object obj, View view, int i, Guideline guideline, Toolbar toolbar, TextView textView) {
        super(obj, view, i);
        this.leftGuideline = guideline;
        this.toolbarNotavailableMirroring = toolbar;
        this.txtTitlebarFilename = textView;
    }

    public static ToolbarNotavailableMirroringBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarNotavailableMirroringBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ToolbarNotavailableMirroringBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_notavailable_mirroring, viewGroup, z, obj);
    }

    public static ToolbarNotavailableMirroringBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarNotavailableMirroringBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ToolbarNotavailableMirroringBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_notavailable_mirroring, null, false, obj);
    }

    public static ToolbarNotavailableMirroringBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarNotavailableMirroringBinding bind(View view, Object obj) {
        return (ToolbarNotavailableMirroringBinding) bind(obj, view, R.layout.toolbar_notavailable_mirroring);
    }
}
