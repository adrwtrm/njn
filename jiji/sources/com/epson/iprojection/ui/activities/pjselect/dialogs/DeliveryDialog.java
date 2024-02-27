package com.epson.iprojection.ui.activities.pjselect.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eRequestTransferScreenDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;

/* loaded from: classes.dex */
public class DeliveryDialog extends BaseDialog {
    private RadioButton _rb0;
    private RadioButton _rb1;
    private RadioGroup _rg;
    private final MessageType _type;

    /* loaded from: classes.dex */
    public enum MessageType {
        Delivery
    }

    public DeliveryDialog(Context context, MessageType messageType, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        super(context, iOnDialogEventListener, resultAction);
        this._rg = null;
        this._rb0 = null;
        this._rb1 = null;
        this._type = messageType;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog
    public void create(Context context) {
        super.create(context);
        setConfig(context, this._builder);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog
    protected void setConfig(Context context, AlertDialog.Builder builder) {
        builder.setMessage(getMsg(context, this._type));
        createRadioGroup(context);
        builder.setView(this._rg);
        builder.setPositiveButton(R.string._OK_, new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.DeliveryDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DeliveryDialog.this.m135x9d0594b6(dialogInterface, i);
            }
        });
        builder.setNegativeButton(R.string._Cancel_, new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.DeliveryDialog$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DeliveryDialog.this.m136x8e572437(dialogInterface, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setConfig$0$com-epson-iprojection-ui-activities-pjselect-dialogs-DeliveryDialog  reason: not valid java name */
    public /* synthetic */ void m135x9d0594b6(DialogInterface dialogInterface, int i) {
        if (this._impl != null) {
            if (this._rg.getCheckedRadioButtonId() == this._rb0.getId()) {
                this._action = BaseDialog.ResultAction.DELIVERY;
                Analytics.getIns().setRequestTransferScreenEvent(eRequestTransferScreenDimension.projectionScreen);
            } else if (this._rg.getCheckedRadioButtonId() == this._rb1.getId()) {
                this._action = BaseDialog.ResultAction.DELIVERY_WHITE;
                Analytics.getIns().setRequestTransferScreenEvent(eRequestTransferScreenDimension.white);
            } else {
                this._action = BaseDialog.ResultAction.DELIVERY;
            }
            this._impl.onClickDialogOK(null, this._action);
            Analytics.getIns().sendEvent(eCustomEvent.REQUEST_TRANSFER_SCREEN);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setConfig$1$com-epson-iprojection-ui-activities-pjselect-dialogs-DeliveryDialog  reason: not valid java name */
    public /* synthetic */ void m136x8e572437(DialogInterface dialogInterface, int i) {
        if (this._impl != null) {
            this._action = BaseDialog.ResultAction.DELIVERY;
            this._impl.onClickDialogNG(this._action);
        }
    }

    private void createRadioGroup(Context context) {
        int dimensionPixelOffset = context.getResources().getDimensionPixelOffset(R.dimen.DialogEdgePadding);
        RadioGroup radioGroup = new RadioGroup(context);
        this._rg = radioGroup;
        radioGroup.setPadding(dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset);
        this._rb0 = new RadioButton(context);
        this._rb1 = new RadioButton(context);
        this._rb0.setText(context.getString(R.string._ProjectedImage_));
        this._rb1.setText(context.getString(R.string._WhitePaper_));
        this._rg.addView(this._rb0);
        this._rg.addView(this._rb1);
        this._rg.check(this._rb0.getId());
    }

    private String getMsg(Context context, MessageType messageType) {
        if (messageType == MessageType.Delivery) {
            return context.getString(R.string._QueryDelivery_);
        }
        return null;
    }
}
