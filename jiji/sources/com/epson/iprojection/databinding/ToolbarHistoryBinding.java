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
public abstract class ToolbarHistoryBinding extends ViewDataBinding {
    public final Button btnHomePjselectConnectHistory;
    public final Guideline leftGuideline;
    public final View modAndLock;
    public final Guideline rightGuideline;
    public final Toolbar toolbarHistory;
    public final TextView txtTitlebarFilename;

    /* JADX INFO: Access modifiers changed from: protected */
    public ToolbarHistoryBinding(Object obj, View view, int i, Button button, Guideline guideline, View view2, Guideline guideline2, Toolbar toolbar, TextView textView) {
        super(obj, view, i);
        this.btnHomePjselectConnectHistory = button;
        this.leftGuideline = guideline;
        this.modAndLock = view2;
        this.rightGuideline = guideline2;
        this.toolbarHistory = toolbar;
        this.txtTitlebarFilename = textView;
    }

    public static ToolbarHistoryBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarHistoryBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ToolbarHistoryBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_history, viewGroup, z, obj);
    }

    public static ToolbarHistoryBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarHistoryBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ToolbarHistoryBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_history, null, false, obj);
    }

    public static ToolbarHistoryBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarHistoryBinding bind(View view, Object obj) {
        return (ToolbarHistoryBinding) bind(obj, view, R.layout.toolbar_history);
    }
}
