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
public abstract class ToolbarDocumentBinding extends ViewDataBinding {
    public final ImageButton btnBackHome;
    public final Guideline leftGuideline;
    public final View modAndLock;
    public final Toolbar toolbardocument;
    public final TextView txtTitlebarFilename;

    /* JADX INFO: Access modifiers changed from: protected */
    public ToolbarDocumentBinding(Object obj, View view, int i, ImageButton imageButton, Guideline guideline, View view2, Toolbar toolbar, TextView textView) {
        super(obj, view, i);
        this.btnBackHome = imageButton;
        this.leftGuideline = guideline;
        this.modAndLock = view2;
        this.toolbardocument = toolbar;
        this.txtTitlebarFilename = textView;
    }

    public static ToolbarDocumentBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarDocumentBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ToolbarDocumentBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_document, viewGroup, z, obj);
    }

    public static ToolbarDocumentBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarDocumentBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ToolbarDocumentBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_document, null, false, obj);
    }

    public static ToolbarDocumentBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarDocumentBinding bind(View view, Object obj) {
        return (ToolbarDocumentBinding) bind(obj, view, R.layout.toolbar_document);
    }
}
