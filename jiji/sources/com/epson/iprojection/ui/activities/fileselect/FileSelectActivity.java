package com.epson.iprojection.ui.activities.fileselect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.delivery.D_DeliveryPermission;
import com.epson.iprojection.ui.activities.fileselect.FileSelectContract;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FileSelectActivity.kt */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\u001a\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\tH\u0016J\b\u0010\u0017\u001a\u00020\tH\u0002J\"\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u00152\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\u0012\u0010\u001a\u001a\u00020\t2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0014J\b\u0010\u001d\u001a\u00020\tH\u0014J\u0018\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020\tH\u0014J\b\u0010#\u001a\u00020\tH\u0014J\b\u0010$\u001a\u00020\u000bH\u0002J\u0010\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\u0015H\u0016J\u0010\u0010'\u001a\u00020\t2\u0006\u0010(\u001a\u00020\u000eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/epson/iprojection/ui/activities/fileselect/FileSelectActivity;", "Lcom/epson/iprojection/ui/common/activity/base/PjConnectableActivity;", "Lcom/epson/iprojection/ui/activities/fileselect/FileSelectContract$View;", "()V", "mPresenter", "Lcom/epson/iprojection/ui/activities/fileselect/FileSelectContract$Presenter;", "mProgressTextView", "Landroid/widget/TextView;", "callFinish", "", "callIsFinishing", "", "callOnDeliverImage", "path", "", "permission", "Lcom/epson/iprojection/ui/activities/delivery/D_DeliveryPermission;", "callStartActivityForResult", "intent", "Landroid/content/Intent;", "requestCode", "", "clearProgress", "createPresenter", "onActivityResult", "resultCode", "onCreate", "b", "Landroid/os/Bundle;", "onDestroy", "onKeyDown", "keyCode", NotificationCompat.CATEGORY_EVENT, "Landroid/view/KeyEvent;", "onPause", "onResume", "shouldCreatePresenterForImage", "showToast", "strId", "updateProgressText", "progressText", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class FileSelectActivity extends PjConnectableActivity implements FileSelectContract.View {
    private FileSelectContract.Presenter mPresenter;
    private TextView mProgressTextView;

    public FileSelectActivity() {
        Pj ins = Pj.getIns();
        Intrinsics.checkNotNullExpressionValue(ins, "getIns()");
        this.mPresenter = new NullFileSelectPresenter(this, ins, this);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_fileselect);
        this.mProgressTextView = (TextView) findViewById(R.id.progressTextView);
        createPresenter();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.mPresenter.onResume();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.mPresenter.onPause();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mPresenter.onDestroy();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.mPresenter.onActivityResult(i, i2, intent);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (i == 4) {
            return this.mPresenter.onBackKeyDown();
        }
        return false;
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.View
    public void updateProgressText(String progressText) {
        Intrinsics.checkNotNullParameter(progressText, "progressText");
        TextView textView = this.mProgressTextView;
        Intrinsics.checkNotNull(textView);
        textView.setText(progressText);
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.View
    public void clearProgress() {
        TextView textView = this.mProgressTextView;
        Intrinsics.checkNotNull(textView);
        textView.setText("");
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.View
    public void callOnDeliverImage(String path, D_DeliveryPermission d_DeliveryPermission) {
        Intrinsics.checkNotNullParameter(path, "path");
        super.onDeliverImage(path, d_DeliveryPermission);
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.View
    public void callStartActivityForResult(Intent intent, int i) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        super.startActivityForResult(intent, i);
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.View
    public void callFinish() {
        super.finish();
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.View
    public void showToast(int i) {
        Toast.makeText(this, getString(i), 1).show();
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.View
    public boolean callIsFinishing() {
        return super.isFinishing();
    }

    private final void createPresenter() {
        PdfFileSelectPresenter pdfFileSelectPresenter;
        if (shouldCreatePresenterForImage()) {
            Context applicationContext = getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
            Pj ins = Pj.getIns();
            Intrinsics.checkNotNullExpressionValue(ins, "getIns()");
            pdfFileSelectPresenter = new ImageFileSelectPresenter(applicationContext, ins, this);
        } else {
            Context applicationContext2 = getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext2, "applicationContext");
            Pj ins2 = Pj.getIns();
            Intrinsics.checkNotNullExpressionValue(ins2, "getIns()");
            pdfFileSelectPresenter = new PdfFileSelectPresenter(applicationContext2, ins2, this);
        }
        this.mPresenter = pdfFileSelectPresenter;
    }

    private final boolean shouldCreatePresenterForImage() {
        Bundle extras = getIntent().getExtras();
        return (extras != null ? extras.getString(ImageFileSelectPresenter.TAG_IMAGE) : null) != null;
    }
}
