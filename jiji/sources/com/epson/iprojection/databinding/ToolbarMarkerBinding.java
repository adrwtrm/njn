package com.epson.iprojection.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.epson.iprojection.R;

/* loaded from: classes.dex */
public abstract class ToolbarMarkerBinding extends ViewDataBinding {
    public final ImageButton btnMarkerEraser;
    public final ImageButton btnMarkerPen1;
    public final ImageButton btnMarkerPen2;
    public final ImageButton btnMarkerRedo;
    public final ImageButton btnMarkerUndo;
    public final ImageButton btnProjection;
    public final Toolbar toolbarMarker;

    /* JADX INFO: Access modifiers changed from: protected */
    public ToolbarMarkerBinding(Object obj, View view, int i, ImageButton imageButton, ImageButton imageButton2, ImageButton imageButton3, ImageButton imageButton4, ImageButton imageButton5, ImageButton imageButton6, Toolbar toolbar) {
        super(obj, view, i);
        this.btnMarkerEraser = imageButton;
        this.btnMarkerPen1 = imageButton2;
        this.btnMarkerPen2 = imageButton3;
        this.btnMarkerRedo = imageButton4;
        this.btnMarkerUndo = imageButton5;
        this.btnProjection = imageButton6;
        this.toolbarMarker = toolbar;
    }

    public static ToolbarMarkerBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarMarkerBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ToolbarMarkerBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_marker, viewGroup, z, obj);
    }

    public static ToolbarMarkerBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarMarkerBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ToolbarMarkerBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.toolbar_marker, null, false, obj);
    }

    public static ToolbarMarkerBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ToolbarMarkerBinding bind(View view, Object obj) {
        return (ToolbarMarkerBinding) bind(obj, view, R.layout.toolbar_marker);
    }
}
