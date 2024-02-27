package com.epson.iprojection.ui.common.activity.funcs;

import android.os.Handler;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.ui.activities.delivery.D_DeliveryPermission;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;

/* loaded from: classes.dex */
public class DeliveryActivityCaller {
    private final PjConnectableActivity _activity;
    private final String _path;
    private final D_DeliveryPermission _permission;
    private boolean _isAvairable = true;
    private final Handler _handler = new Handler();

    public DeliveryActivityCaller(PjConnectableActivity pjConnectableActivity, String str, D_DeliveryPermission d_DeliveryPermission) {
        this._activity = pjConnectableActivity;
        this._path = str;
        this._permission = d_DeliveryPermission;
    }

    public void start() {
        new Thread(new Runnable() { // from class: com.epson.iprojection.ui.common.activity.funcs.DeliveryActivityCaller$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DeliveryActivityCaller.this.m196xffb1f048();
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$start$1$com-epson-iprojection-ui-common-activity-funcs-DeliveryActivityCaller  reason: not valid java name */
    public /* synthetic */ void m196xffb1f048() {
        while (RegisteredDialog.getIns().isShowing()) {
            Sleeper.sleep(100L);
            if (!this._isAvairable) {
                return;
            }
        }
        this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.common.activity.funcs.DeliveryActivityCaller$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DeliveryActivityCaller.this.m195x1c863d07();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$start$0$com-epson-iprojection-ui-common-activity-funcs-DeliveryActivityCaller  reason: not valid java name */
    public /* synthetic */ void m195x1c863d07() {
        this._activity.startDeliveryActivity(this._path, this._permission);
    }

    public void delete() {
        this._isAvairable = false;
    }
}
