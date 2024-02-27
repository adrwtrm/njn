package com.epson.iprojection.ui.activities.delivery;

import com.epson.iprojection.engine.common.D_DeliveryInfo;
import java.io.Serializable;

/* loaded from: classes.dex */
public class D_DeliveryPermission implements Serializable {
    public static final String INTENT_TAG_DELIVERY_PARMISSION = "IntentTagDeliveryPermission";
    private static final long serialVersionUID = -4853347452418337463L;
    private boolean _changePermission;
    private boolean _enableChangeView;
    private boolean _enableSave;
    private boolean _enableWrite;

    public D_DeliveryPermission() {
        this._changePermission = false;
        this._enableWrite = true;
        this._enableSave = true;
        this._enableChangeView = true;
    }

    public D_DeliveryPermission(boolean z, boolean z2, boolean z3, boolean z4) {
        this._changePermission = z;
        this._enableWrite = z2;
        this._enableSave = z3;
        this._enableChangeView = z4;
    }

    public D_DeliveryPermission(D_DeliveryInfo d_DeliveryInfo) {
        this._changePermission = false;
        this._enableWrite = true;
        this._enableSave = true;
        this._enableChangeView = true;
        this._changePermission = d_DeliveryInfo.validControlParmission;
        this._enableWrite = d_DeliveryInfo.enableWirte;
        this._enableSave = d_DeliveryInfo.enableSave;
        this._enableChangeView = d_DeliveryInfo.enableChangeUI;
    }

    public boolean isValid() {
        return this._changePermission;
    }

    public boolean isEnableChangeView() {
        return this._enableChangeView;
    }

    public boolean isEnableWrite() {
        return this._enableWrite;
    }

    public boolean isEnableSave() {
        return this._enableSave;
    }
}
