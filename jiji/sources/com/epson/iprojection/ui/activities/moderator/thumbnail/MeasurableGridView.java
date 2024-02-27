package com.epson.iprojection.ui.activities.moderator.thumbnail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.GridView;
import com.epson.iprojection.ui.activities.moderator.TouchPosGettableImageView;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnDragStateListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnDropListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnSizeChangedW;

/* loaded from: classes.dex */
public class MeasurableGridView extends GridView {
    IOnSizeChangedW _impl;
    IOnDragStateListener _implDragState;

    public MeasurableGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void mySetOnDragListener(final IOnDropListener iOnDropListener) {
        setOnDragListener(new View.OnDragListener() { // from class: com.epson.iprojection.ui.activities.moderator.thumbnail.MeasurableGridView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnDragListener
            public final boolean onDrag(View view, DragEvent dragEvent) {
                return MeasurableGridView.this.m108xfa46eb7b(iOnDropListener, view, dragEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$mySetOnDragListener$0$com-epson-iprojection-ui-activities-moderator-thumbnail-MeasurableGridView  reason: not valid java name */
    public /* synthetic */ boolean m108xfa46eb7b(IOnDropListener iOnDropListener, View view, DragEvent dragEvent) {
        int action = dragEvent.getAction();
        if (action == 1) {
            this._implDragState.onStartDrag();
            return true;
        } else if (action != 2) {
            if (action != 3) {
                if (action == 4) {
                    this._implDragState.onEndDrag();
                }
                return false;
            }
            boolean isEnabledDrop = this._implDragState.isEnabledDrop();
            this._implDragState.onEndDrag();
            if (isEnabledDrop) {
                try {
                    iOnDropListener.onDropFromWindowToList(((TouchPosGettableImageView) dragEvent.getLocalState()).getWindID());
                    return true;
                } catch (Exception unused) {
                    return true;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public void setListener(IOnSizeChangedW iOnSizeChangedW) {
        this._impl = iOnSizeChangedW;
    }

    public void setDragStateListener(IOnDragStateListener iOnDragStateListener) {
        this._implDragState = iOnDragStateListener;
    }

    @Override // android.widget.AbsListView, android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        IOnSizeChangedW iOnSizeChangedW = this._impl;
        if (iOnSizeChangedW != null) {
            iOnSizeChangedW.onSizeChangedW(i);
        }
    }
}
