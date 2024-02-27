package com.epson.iprojection.ui.activities.start;

import android.app.Activity;
import android.os.Bundle;
import com.epson.iprojection.ui.activities.pjselect.dialogs.MessageDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.BaseDialog;
import com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener;
import com.epson.iprojection.ui.activities.pjselect.nfc.NfcDataManager;
import com.epson.iprojection.ui.activities.splash.Activity_Splash;
import com.epson.iprojection.ui.common.Initializer;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.application.IproApplication;
import com.epson.iprojection.ui.common.singleton.AppStartActivityManager;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class Activity_StartFromNfc extends Activity_BaseStart implements IOnDialogEventListener {
    @Override // com.epson.iprojection.ui.activities.start.Activity_BaseStart, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((IproApplication) getApplication()).mIsNFCEventHappenedDuringQRConnect = true;
        if (!Pj.getIns().isAlreadyInited()) {
            Initializer.initialize(this);
            Pj.getIns().initialize(this);
        }
        if (Pj.getIns().isShowingSpinDialog()) {
            finish();
        } else if (Pj.getIns().isConnected()) {
            Pj.getIns().showMsgDialog(MessageDialog.MessageType.Nfc_AlreadyConnect, this, BaseDialog.ResultAction.NOACTION);
            if (ActivityGetter.getIns().isAppFinished()) {
                startActivitySplash();
            }
            finish();
        } else if (Pj.getIns().isConnectingByLinkageData()) {
            finish();
        } else {
            Activity frontActivity = ActivityGetter.getIns().getFrontActivity();
            if (!AppStartActivityManager.isNFCAvailableActivity(frontActivity)) {
                finish();
            } else if (isLaunchedFromHistory()) {
                startPjSelect();
            } else {
                Pj.getIns().clearRegisteredPjInf();
                NfcDataManager.getIns().decodeRawBytesAndStack(getIntent());
                NfcDataManager.getIns().setIsEventOccured(true);
                if (AppStartActivityManager.shouldFinishActivityWhenNfc(frontActivity)) {
                    frontActivity.finish();
                    finish();
                    return;
                }
                startPjSelect();
                finish();
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        NfcDataManager.getIns().setIsEventOccured(false);
    }

    private void startPjSelect() {
        if (ActivityGetter.getIns().isAppFinished()) {
            startNextActivity(Activity_Splash.class);
        } else {
            AppStartActivityManager.getIns().transitionActivityPjSelectAny(this);
        }
    }

    private void startActivitySplash() {
        startNextActivity(Activity_Splash.class);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onClickDialogOK(String str, BaseDialog.ResultAction resultAction) {
        Pj.getIns().clearDialog();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onClickDialogNG(BaseDialog.ResultAction resultAction) {
        Pj.getIns().clearDialog();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.dialogs.base.IOnDialogEventListener
    public void onDialogCanceled() {
        Pj.getIns().clearDialog();
    }

    private boolean isLaunchedFromHistory() {
        return (getIntent().getFlags() & 1048576) != 0;
    }
}
