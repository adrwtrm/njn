package com.epson.iprojection.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.common.ReactiveEditText;

/* loaded from: classes.dex */
public class ToolbarWebBindingImpl extends ToolbarWebBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, Object obj) {
        return true;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.mod_and_lock, 1);
        sparseIntArray.put(R.id.toolbarweb, 2);
        sparseIntArray.put(R.id.left_guideline, 3);
        sparseIntArray.put(R.id.btnBackHome, 4);
        sparseIntArray.put(R.id.urledit_area, 5);
        sparseIntArray.put(R.id.editUrl, 6);
        sparseIntArray.put(R.id.ProBarId, 7);
        sparseIntArray.put(R.id.clear_Url, 8);
        sparseIntArray.put(R.id.btn_projection, 9);
        sparseIntArray.put(R.id.btn_web_main_Paint, 10);
        sparseIntArray.put(R.id.right_guideline, 11);
    }

    public ToolbarWebBindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 12, sIncludes, sViewsWithIds));
    }

    private ToolbarWebBindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, (ProgressBar) objArr[7], (ImageButton) objArr[4], (ImageButton) objArr[9], (ImageButton) objArr[10], (ImageButton) objArr[8], (ReactiveEditText) objArr[6], (Guideline) objArr[3], (View) objArr[1], (Guideline) objArr[11], (Toolbar) objArr[2], (ConstraintLayout) objArr[5]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setRootTag(view);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.mDirtyFlags != 0;
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}
