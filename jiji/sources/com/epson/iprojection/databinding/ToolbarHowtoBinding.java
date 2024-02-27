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
public abstract class ToolbarHowtoBinding extends ViewDataBinding {
    public final Guideline leftGuideline;
    public final View modAndLock;
    public final Toolbar toolbarHowto;
    public final TextView txtTitlebarFilename;

    /* JADX INFO: Access modifiers changed from: protected */
    public ToolbarHowtoBinding(Object obj, View view, int i, Guideline guideline, View view2, Toolbar toolbar, TextView textView) {
        super(obj, view, i);
        this.leftGuideline = guideline;
        this.modAndLock = view2;
        this.toolbarHowto = toolbar;
        this.txtTitlebarFilename = textView;
    }

    public static ToolbarHowtoBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarHowtoBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ToolbarHowtoBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_howto, viewGroup, z, obj);
    }

    public static ToolbarHowtoBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarHowtoBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ToolbarHowtoBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_howto, null, false, obj);
    }

    public static ToolbarHowtoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarHowtoBinding bind(View view, Object obj) {
        return (ToolbarHowtoBinding) bind(obj, view, R.layout.toolbar_howto);
    }
}
