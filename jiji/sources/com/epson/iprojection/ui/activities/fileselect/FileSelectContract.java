package com.epson.iprojection.ui.activities.fileselect;

import android.content.Intent;
import com.epson.iprojection.ui.activities.delivery.D_DeliveryPermission;
import kotlin.Metadata;

/* compiled from: FileSelectContract.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001:\u0004\u0002\u0003\u0004\u0005¨\u0006\u0006"}, d2 = {"Lcom/epson/iprojection/ui/activities/fileselect/FileSelectContract;", "", "Presenter", "ReceiveDataProgressListener", "UseCase", "View", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface FileSelectContract {

    /* compiled from: FileSelectContract.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\u0003H&J\b\u0010\f\u001a\u00020\u0003H&J\b\u0010\r\u001a\u00020\u0003H&¨\u0006\u000e"}, d2 = {"Lcom/epson/iprojection/ui/activities/fileselect/FileSelectContract$Presenter;", "", "onActivityResult", "", "requestCode", "", "resultCode", "intent", "Landroid/content/Intent;", "onBackKeyDown", "", "onDestroy", "onPause", "onResume", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface Presenter {
        void onActivityResult(int i, int i2, Intent intent);

        boolean onBackKeyDown();

        void onDestroy();

        void onPause();

        void onResume();
    }

    /* compiled from: FileSelectContract.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/activities/fileselect/FileSelectContract$ReceiveDataProgressListener;", "", "onUpdatedReceiveDataProgress", "", "receivedNum", "", "totalNum", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface ReceiveDataProgressListener {
        void onUpdatedReceiveDataProgress(int i, int i2);
    }

    /* compiled from: FileSelectContract.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u001a\u0010\b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u000b"}, d2 = {"Lcom/epson/iprojection/ui/activities/fileselect/FileSelectContract$UseCase;", "", "cancelReceiving", "", "cleanDirectory", "directoryPath", "", "prepareCleanDirectory", "receiveFiles", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface UseCase {
        void cancelReceiving();

        void cleanDirectory(String str);

        void prepareCleanDirectory(String str);

        String receiveFiles(Intent intent, String str);
    }

    /* compiled from: FileSelectContract.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\u001a\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH&J\u0018\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH&J\b\u0010\u0010\u001a\u00020\u0003H&J\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u000fH&J\u0010\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\bH&¨\u0006\u0015"}, d2 = {"Lcom/epson/iprojection/ui/activities/fileselect/FileSelectContract$View;", "", "callFinish", "", "callIsFinishing", "", "callOnDeliverImage", "path", "", "permission", "Lcom/epson/iprojection/ui/activities/delivery/D_DeliveryPermission;", "callStartActivityForResult", "intent", "Landroid/content/Intent;", "requestCode", "", "clearProgress", "showToast", "strId", "updateProgressText", "progressText", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface View {
        void callFinish();

        boolean callIsFinishing();

        void callOnDeliverImage(String str, D_DeliveryPermission d_DeliveryPermission);

        void callStartActivityForResult(Intent intent, int i);

        void clearProgress();

        void showToast(int i);

        void updateProgressText(String str);
    }
}
