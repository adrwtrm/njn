package com.epson.iprojection.ui.activities.pjselect.qrcode.views.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import com.epson.iprojection.R;
import com.epson.iprojection.databinding.MainQrcodeCameraBinding;
import com.epson.iprojection.ui.activities.pjselect.qrcode.QRDataHolder;
import com.epson.iprojection.ui.activities.pjselect.qrcode.models.QrcodeContract;
import com.epson.iprojection.ui.activities.pjselect.qrcode.viewmodel.QrCameraViewModel;
import com.epson.iprojection.ui.activities.presen.Defines;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;

/* loaded from: classes.dex */
public class Activity_QrCamera extends PjConnectableActivity implements QrcodeContract.IView {
    public static final String INTENT_ORIENTATION = "IntentOrientation";
    private QrcodeContract.IViewModel _contractViewModel;
    private AlertDialog _infoDialog;

    @Override // com.epson.iprojection.ui.activities.pjselect.qrcode.models.QrcodeContract.IView
    public Activity getActivityForIntent() {
        return this;
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MainQrcodeCameraBinding mainQrcodeCameraBinding = (MainQrcodeCameraBinding) DataBindingUtil.setContentView(this, R.layout.main_qrcode_camera);
        QrCameraViewModel qrCameraViewModel = new QrCameraViewModel(this, mainQrcodeCameraBinding);
        mainQrcodeCameraBinding.setViewmodel(qrCameraViewModel);
        this._contractViewModel = qrCameraViewModel;
        findViewById(16908290).setSystemUiVisibility(Defines.PDF_MAX_W);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this._contractViewModel.onResume();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        this._contractViewModel.onPause();
        super.onPause();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        AlertDialog alertDialog = this._infoDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this._infoDialog.dismiss();
        }
        finish();
        super.onStop();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        getWindow().clearFlags(128);
        super.onDestroy();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.qrcode.models.QrcodeContract.IView
    public void destroy() {
        finish();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.qrcode.models.QrcodeContract.IView
    public void showActivityFinishDialog(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(i));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string._OK_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.qrcode.views.activities.Activity_QrCamera$$ExternalSyntheticLambda0
            {
                Activity_QrCamera.this = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                Activity_QrCamera.this.m154x149e0b3f(dialogInterface, i2);
            }
        });
        builder.show();
    }

    /* renamed from: lambda$showActivityFinishDialog$0$com-epson-iprojection-ui-activities-pjselect-qrcode-views-activities-Activity_QrCamera */
    public /* synthetic */ void m154x149e0b3f(DialogInterface dialogInterface, int i) {
        finish();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.qrcode.models.QrcodeContract.IView
    public void showInformationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string._QRViewTitle_));
        builder.setMessage(getString(R.string._QRViewMsg_));
        builder.setPositiveButton(getString(R.string._OK_), (DialogInterface.OnClickListener) null);
        this._infoDialog = builder.show();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.qrcode.models.QrcodeContract.IView
    public void onQREvent(byte[] bArr) {
        QRDataHolder.INSTANCE.setQrRowData(bArr);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.qrcode.models.QrcodeContract.IView
    public void goNextActivity(Intent intent, int i) {
        startActivityForResult(intent, i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
