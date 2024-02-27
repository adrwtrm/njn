package com.epson.iprojection.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.common.ReactiveEditText;

/* loaded from: classes.dex */
public abstract class ToolbarWebBinding extends ViewDataBinding {
    public final ProgressBar ProBarId;
    public final ImageButton btnBackHome;
    public final ImageButton btnProjection;
    public final ImageButton btnWebMainPaint;
    public final ImageButton clearUrl;
    public final ReactiveEditText editUrl;
    public final Guideline leftGuideline;
    public final View modAndLock;
    public final Guideline rightGuideline;
    public final Toolbar toolbarweb;
    public final ConstraintLayout urleditArea;

    /* JADX INFO: Access modifiers changed from: protected */
    public ToolbarWebBinding(Object obj, View view, int i, ProgressBar progressBar, ImageButton imageButton, ImageButton imageButton2, ImageButton imageButton3, ImageButton imageButton4, ReactiveEditText reactiveEditText, Guideline guideline, View view2, Guideline guideline2, Toolbar toolbar, ConstraintLayout constraintLayout) {
        super(obj, view, i);
        this.ProBarId = progressBar;
        this.btnBackHome = imageButton;
        this.btnProjection = imageButton2;
        this.btnWebMainPaint = imageButton3;
        this.clearUrl = imageButton4;
        this.editUrl = reactiveEditText;
        this.leftGuideline = guideline;
        this.modAndLock = view2;
        this.rightGuideline = guideline2;
        this.toolbarweb = toolbar;
        this.urleditArea = constraintLayout;
    }

    public static ToolbarWebBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarWebBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ToolbarWebBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_web, viewGroup, z, obj);
    }

    public static ToolbarWebBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarWebBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ToolbarWebBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_web, null, false, obj);
    }

    public static ToolbarWebBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarWebBinding bind(View view, Object obj) {
        return (ToolbarWebBinding) bind(obj, view, R.layout.toolbar_web);
    }
}
