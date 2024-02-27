package com.epson.iprojection.ui.activities.fileselect;

import android.content.Context;
import android.content.Intent;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.ui.activities.fileselect.FileSelectContract;
import com.epson.iprojection.ui.activities.presen.Activity_Presen;
import com.epson.iprojection.ui.common.activitystatus.ActivityKillStatus;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.singleton.AppKiller;
import com.epson.iprojection.ui.engine_wrapper.IPj;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

/* compiled from: FileSelectPresenter.kt */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\b&\u0018\u0000 )2\u00020\u00012\u00020\u0002:\u0002()B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0012\u001a\u00020\u000fH&J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u0004H\u0002J\b\u0010\u0017\u001a\u00020\u0014H\u0002J\"\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u000fH\u0016J\b\u0010\u001f\u001a\u00020\u0014H\u0016J\b\u0010 \u001a\u00020\u0014H\u0016J\b\u0010!\u001a\u00020\u0014H\u0016J\u0018\u0010\"\u001a\u00020\u00142\u0006\u0010#\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020\u001aH\u0016J\u0010\u0010%\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\u001dH&J\b\u0010&\u001a\u00020\u0014H\u0002J\b\u0010'\u001a\u00020\u0014H\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lcom/epson/iprojection/ui/activities/fileselect/FileSelectPresenter;", "Lcom/epson/iprojection/ui/activities/fileselect/FileSelectContract$Presenter;", "Lcom/epson/iprojection/ui/activities/fileselect/FileSelectContract$ReceiveDataProgressListener;", "mContext", "Landroid/content/Context;", "mPj", "Lcom/epson/iprojection/ui/engine_wrapper/IPj;", "mView", "Lcom/epson/iprojection/ui/activities/fileselect/FileSelectContract$View;", "(Landroid/content/Context;Lcom/epson/iprojection/ui/engine_wrapper/IPj;Lcom/epson/iprojection/ui/activities/fileselect/FileSelectContract$View;)V", "mActivityReturnFrom", "Lcom/epson/iprojection/ui/activities/fileselect/FileSelectPresenter$ActivityReturnFrom;", "mFilePath", "", "mIsWindowActive", "", "mUseCase", "Lcom/epson/iprojection/ui/activities/fileselect/FileSelectUseCase;", "canSelectMultiple", "finish", "", "getPath", "context", "moveToDeliveredImageActivityIfNeed", "onActivityResult", "requestCode", "", "resultCode", "intent", "Landroid/content/Intent;", "onBackKeyDown", "onDestroy", "onPause", "onResume", "onUpdatedReceiveDataProgress", "receivedNum", "totalNum", "setMimeType", "startPresenActivity", "startSAFActivity", "ActivityReturnFrom", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public abstract class FileSelectPresenter implements FileSelectContract.Presenter, FileSelectContract.ReceiveDataProgressListener {
    public static final Companion Companion = new Companion(null);
    public static final String FOLDER_NAME = "/presen_data/";
    public static final int REQUEST_CODE_PRESEN = 2;
    public static final int REQUEST_CODE_SAF = 1;
    private ActivityReturnFrom mActivityReturnFrom;
    private final Context mContext;
    private String mFilePath;
    private boolean mIsWindowActive;
    private final IPj mPj;
    private FileSelectUseCase mUseCase;
    private final FileSelectContract.View mView;

    /* compiled from: FileSelectPresenter.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lcom/epson/iprojection/ui/activities/fileselect/FileSelectPresenter$ActivityReturnFrom;", "", "(Ljava/lang/String;I)V", "None", "SAF", "Presen", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public enum ActivityReturnFrom {
        None,
        SAF,
        Presen
    }

    /* compiled from: FileSelectPresenter.kt */
    @Metadata(k = 3, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ActivityReturnFrom.values().length];
            try {
                iArr[ActivityReturnFrom.None.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ActivityReturnFrom.Presen.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static /* synthetic */ void $r8$lambda$DGnbGUPMvaUn_sD1BRBvUadDHfM(FileSelectPresenter fileSelectPresenter, Intent intent) {
        onActivityResult$lambda$0(fileSelectPresenter, intent);
    }

    public abstract boolean canSelectMultiple();

    public abstract void setMimeType(Intent intent);

    public FileSelectPresenter(Context mContext, IPj mPj, FileSelectContract.View mView) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        Intrinsics.checkNotNullParameter(mPj, "mPj");
        Intrinsics.checkNotNullParameter(mView, "mView");
        this.mContext = mContext;
        this.mPj = mPj;
        this.mView = mView;
        this.mUseCase = new FileSelectUseCase(mContext, this);
        this.mActivityReturnFrom = ActivityReturnFrom.None;
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.Presenter
    public void onResume() {
        this.mIsWindowActive = true;
        if (ActivityKillStatus.getIns().isKilling() || AppKiller.getIns().isKilling()) {
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[this.mActivityReturnFrom.ordinal()];
        if (i == 1 || i == 2) {
            startSAFActivity();
        }
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.Presenter
    public void onPause() {
        this.mIsWindowActive = false;
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.Presenter
    public void onDestroy() {
        this.mUseCase.cleanDirectory(getPath(this.mContext));
    }

    public static final void onActivityResult$lambda$0(FileSelectPresenter fileSelectPresenter, Intent intent) {
        String receiveFiles = fileSelectPresenter.mUseCase.receiveFiles(intent, fileSelectPresenter.getPath(fileSelectPresenter.mContext));
        fileSelectPresenter.mFilePath = receiveFiles;
        if (receiveFiles == null) {
            return;
        }
        while (!fileSelectPresenter.mIsWindowActive && !fileSelectPresenter.mView.callIsFinishing()) {
            Sleeper.sleep(100L);
        }
        if (fileSelectPresenter.mView.callIsFinishing()) {
            return;
        }
        fileSelectPresenter.startPresenActivity();
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.Presenter
    public void onActivityResult(int i, int i2, final Intent intent) {
        if (i == 1) {
            this.mActivityReturnFrom = ActivityReturnFrom.SAF;
            if (i2 == -1 && intent != null) {
                FileSelectUseCase fileSelectUseCase = new FileSelectUseCase(this.mContext, this);
                this.mUseCase = fileSelectUseCase;
                fileSelectUseCase.prepareCleanDirectory(getPath(this.mContext));
                this.mView.clearProgress();
                new Thread(new Runnable() { // from class: com.epson.iprojection.ui.activities.fileselect.FileSelectPresenter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        FileSelectPresenter.$r8$lambda$DGnbGUPMvaUn_sD1BRBvUadDHfM(FileSelectPresenter.this, intent);
                    }
                }).start();
            } else {
                finish();
            }
        } else if (i == 2) {
            this.mActivityReturnFrom = ActivityReturnFrom.Presen;
        }
        moveToDeliveredImageActivityIfNeed();
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.Presenter
    public boolean onBackKeyDown() {
        if (this.mActivityReturnFrom == ActivityReturnFrom.SAF) {
            this.mUseCase.cancelReceiving();
            startSAFActivity();
            return true;
        }
        return false;
    }

    @Override // com.epson.iprojection.ui.activities.fileselect.FileSelectContract.ReceiveDataProgressListener
    public void onUpdatedReceiveDataProgress(int i, int i2) {
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new FileSelectPresenter$onUpdatedReceiveDataProgress$1(this, i, i2, null), 2, null);
    }

    private final void finish() {
        this.mView.callFinish();
    }

    private final void startPresenActivity() {
        if (this.mFilePath == null) {
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new FileSelectPresenter$startPresenActivity$1(this, null), 2, null);
            this.mView.callFinish();
            return;
        }
        Intent intent = new Intent(this.mContext, Activity_Presen.class);
        intent.putExtra(Activity_Presen.INTENT_TAG_PATH, this.mFilePath);
        this.mView.callStartActivityForResult(intent, 2);
    }

    private final void startSAFActivity() {
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        if (canSelectMultiple()) {
            intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
        }
        setMimeType(intent);
        this.mView.callStartActivityForResult(intent, 1);
        this.mPj.clearDeliveredImagePath();
    }

    private final String getPath(Context context) {
        return context.getExternalFilesDir(null) + FOLDER_NAME;
    }

    private final void moveToDeliveredImageActivityIfNeed() {
        String deliveredImagePath = this.mPj.getDeliveredImagePath();
        if (deliveredImagePath != null && PrefUtils.readInt(this.mContext, PrefTagDefine.conPJ_AUTO_DISPLAY_DELIVERY_TAG) == 1) {
            this.mView.callOnDeliverImage(deliveredImagePath, null);
            finish();
        }
    }

    /* compiled from: FileSelectPresenter.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/epson/iprojection/ui/activities/fileselect/FileSelectPresenter$Companion;", "", "()V", "FOLDER_NAME", "", "REQUEST_CODE_PRESEN", "", "REQUEST_CODE_SAF", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
