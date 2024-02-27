package com.epson.iprojection.ui.activities.pjselect.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.ALPFUtils;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.linkagedata.data.D_LinkageData;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseHaveButtonDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;
import com.epson.iprojection.ui.activities.web.WebUtils;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.singleton.LinkageDataInfoStacker;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class WhiteboardDialog extends BaseHaveButtonDialog {
    public WhiteboardDialog(Context context, IOnDialogEventListener iOnDialogEventListener, BaseDialog.ResultAction resultAction) {
        super(context, iOnDialogEventListener, null, null, null, resultAction);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseHaveButtonDialog, com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog
    public void setConfig(Context context, AlertDialog.Builder builder) {
        builder.setMessage(context.getString(R.string._ShowingWBOnBrowser_));
        builder.setPositiveButton(context.getString(R.string._OK_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.WhiteboardDialog$$ExternalSyntheticLambda0
            {
                WhiteboardDialog.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                WhiteboardDialog.this.m143x42e9ebdf(dialogInterface, i);
            }
        });
        builder.setNegativeButton(context.getString(R.string._Cancel_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.dialogs.WhiteboardDialog$$ExternalSyntheticLambda1
            {
                WhiteboardDialog.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                WhiteboardDialog.this.m144x26159f20(dialogInterface, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setConfig$0$com-epson-iprojection-ui-activities-pjselect-dialogs-WhiteboardDialog  reason: not valid java name */
    public /* synthetic */ void m143x42e9ebdf(DialogInterface dialogInterface, int i) {
        goWhiteboard();
        this._impl.onClickDialogOK(null, this._action);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$setConfig$1$com-epson-iprojection-ui-activities-pjselect-dialogs-WhiteboardDialog  reason: not valid java name */
    public /* synthetic */ void m144x26159f20(DialogInterface dialogInterface, int i) {
        this._impl.onClickDialogNG(BaseDialog.ResultAction.ONLY_CLOSE_DIALOG);
    }

    private void goWhiteboard() {
        ArrayList<ConnectPjInfo> nowConnectingPJList = Pj.getIns().getNowConnectingPJList();
        D_LinkageData d_LinkageData = LinkageDataInfoStacker.getIns().get();
        D_PjInfo d_PjInfo = null;
        for (int i = 0; i < nowConnectingPJList.size(); i++) {
            d_PjInfo = nowConnectingPJList.get(i).getPjInfo();
            if (d_LinkageData.macAddr == null) {
                if (isSameIp(LinkageDataInfoStacker.getIns().getConnectedIpAddr(), d_PjInfo.IPAddr)) {
                    break;
                }
            } else if (isSameMac(d_LinkageData.macAddr, d_PjInfo.UniqInfo)) {
                break;
            }
        }
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(WebUtils.URL_PREF_HTTP + NetUtils.cvtIPAddr(d_PjInfo.IPAddr) + "/wb?ALPF=" + ALPFUtils.convert(d_PjInfo.sharedWbPin)));
        Activity frontActivity = ActivityGetter.getIns().getFrontActivity();
        if (frontActivity.getPackageManager().resolveActivity(intent, 0) != null) {
            frontActivity.startActivity(intent);
        }
    }

    private boolean isSameIp(int[] iArr, byte[] bArr) {
        for (int i = 0; i < 4; i++) {
            if (((byte) iArr[i]) != bArr[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isSameMac(int[] iArr, byte[] bArr) {
        for (int i = 0; i < 6; i++) {
            if (((byte) iArr[i]) != bArr[i]) {
                return false;
            }
        }
        return true;
    }
}
