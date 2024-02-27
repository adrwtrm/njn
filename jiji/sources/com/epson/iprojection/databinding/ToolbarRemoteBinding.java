package com.epson.iprojection.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.epson.iprojection.R;

/* loaded from: classes.dex */
public abstract class ToolbarRemoteBinding extends ViewDataBinding {
    public final ImageButton btnRemoteActionbarGesture;
    public final Guideline leftGuideline;
    public final View modAndLock;
    public final Spinner projSpinner;
    public final Guideline rightGuideline;
    public final Toolbar toolbarRemote;

    /* JADX INFO: Access modifiers changed from: protected */
    public ToolbarRemoteBinding(Object obj, View view, int i, ImageButton imageButton, Guideline guideline, View view2, Spinner spinner, Guideline guideline2, Toolbar toolbar) {
        super(obj, view, i);
        this.btnRemoteActionbarGesture = imageButton;
        this.leftGuideline = guideline;
        this.modAndLock = view2;
        this.projSpinner = spinner;
        this.rightGuideline = guideline2;
        this.toolbarRemote = toolbar;
    }

    public static ToolbarRemoteBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarRemoteBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ToolbarRemoteBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_remote, viewGroup, z, obj);
    }

    public static ToolbarRemoteBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarRemoteBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ToolbarRemoteBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_remote, null, false, obj);
    }

    public static ToolbarRemoteBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarRemoteBinding bind(View view, Object obj) {
        return (ToolbarRemoteBinding) bind(obj, view, R.layout.toolbar_remote);
    }
}
