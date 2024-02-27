package com.epson.iprojection.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.epson.iprojection.R;

/* loaded from: classes.dex */
public abstract class ToolbarSelectBinding extends ViewDataBinding {
    public final Button btnHomePjselectConnect;
    public final ImageButton btnProjection;
    public final Guideline leftGuideline;
    public final View modAndLock;
    public final Guideline rightGuideline;
    public final Toolbar toolbarPjSelect;
    public final TextView txtTitlebarFilename;

    /* JADX INFO: Access modifiers changed from: protected */
    public ToolbarSelectBinding(Object obj, View view, int i, Button button, ImageButton imageButton, Guideline guideline, View view2, Guideline guideline2, Toolbar toolbar, TextView textView) {
        super(obj, view, i);
        this.btnHomePjselectConnect = button;
        this.btnProjection = imageButton;
        this.leftGuideline = guideline;
        this.modAndLock = view2;
        this.rightGuideline = guideline2;
        this.toolbarPjSelect = toolbar;
        this.txtTitlebarFilename = textView;
    }

    public static ToolbarSelectBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarSelectBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ToolbarSelectBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_select, viewGroup, z, obj);
    }

    public static ToolbarSelectBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarSelectBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ToolbarSelectBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_select, null, false, obj);
    }

    public static ToolbarSelectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarSelectBinding bind(View view, Object obj) {
        return (ToolbarSelectBinding) bind(obj, view, R.layout.toolbar_select);
    }
}
