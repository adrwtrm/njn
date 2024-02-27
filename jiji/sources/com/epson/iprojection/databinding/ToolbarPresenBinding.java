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
public abstract class ToolbarPresenBinding extends ViewDataBinding {
    public final ImageButton btnPresenActionbarMarker;
    public final ImageButton btnProjection;
    public final Guideline leftGuideline;
    public final View modAndLock;
    public final View presenDummySpace;
    public final Toolbar toolbarpresen;
    public final TextView txtTitlebarFilename;

    /* JADX INFO: Access modifiers changed from: protected */
    public ToolbarPresenBinding(Object obj, View view, int i, ImageButton imageButton, ImageButton imageButton2, Guideline guideline, View view2, View view3, Toolbar toolbar, TextView textView) {
        super(obj, view, i);
        this.btnPresenActionbarMarker = imageButton;
        this.btnProjection = imageButton2;
        this.leftGuideline = guideline;
        this.modAndLock = view2;
        this.presenDummySpace = view3;
        this.toolbarpresen = toolbar;
        this.txtTitlebarFilename = textView;
    }

    public static ToolbarPresenBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarPresenBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ToolbarPresenBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_presen, viewGroup, z, obj);
    }

    public static ToolbarPresenBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarPresenBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ToolbarPresenBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_presen, null, false, obj);
    }

    public static ToolbarPresenBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarPresenBinding bind(View view, Object obj) {
        return (ToolbarPresenBinding) bind(obj, view, R.layout.toolbar_presen);
    }
}
