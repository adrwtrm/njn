package com.epson.iprojection.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.epson.iprojection.R;

/* loaded from: classes.dex */
public abstract class ToolbarModeratorBinding extends ViewDataBinding {
    public final CheckBox checkboxMultictrlThumbnail;
    public final Guideline leftGuideline;
    public final View modAndLock;
    public final Guideline rightGuideline;
    public final Toolbar toolbarmoderator;

    /* JADX INFO: Access modifiers changed from: protected */
    public ToolbarModeratorBinding(Object obj, View view, int i, CheckBox checkBox, Guideline guideline, View view2, Guideline guideline2, Toolbar toolbar) {
        super(obj, view, i);
        this.checkboxMultictrlThumbnail = checkBox;
        this.leftGuideline = guideline;
        this.modAndLock = view2;
        this.rightGuideline = guideline2;
        this.toolbarmoderator = toolbar;
    }

    public static ToolbarModeratorBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarModeratorBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ToolbarModeratorBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_moderator, viewGroup, z, obj);
    }

    public static ToolbarModeratorBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarModeratorBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ToolbarModeratorBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_moderator, null, false, obj);
    }

    public static ToolbarModeratorBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarModeratorBinding bind(View view, Object obj) {
        return (ToolbarModeratorBinding) bind(obj, view, R.layout.toolbar_moderator);
    }
}
