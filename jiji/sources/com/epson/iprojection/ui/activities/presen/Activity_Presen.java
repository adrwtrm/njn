package com.epson.iprojection.ui.activities.presen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Size;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.common.IntentDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.BitmapUtils;
import com.epson.iprojection.common.utils.FileUtils;
import com.epson.iprojection.common.utils.MemoryUtils;
import com.epson.iprojection.common.utils.PathGetter;
import com.epson.iprojection.ui.activities.delivery.Activity_PresenDelivery;
import com.epson.iprojection.ui.activities.delivery.D_DeliveryPermission;
import com.epson.iprojection.ui.activities.drawermenu.eDrawerMenuItem;
import com.epson.iprojection.ui.activities.presen.Activity_Presen;
import com.epson.iprojection.ui.activities.presen.exceptions.GoExitException;
import com.epson.iprojection.ui.activities.presen.exceptions.UnavailableException;
import com.epson.iprojection.ui.activities.presen.img_filer.ImageFiler;
import com.epson.iprojection.ui.activities.presen.interfaces.IClickButtonListener;
import com.epson.iprojection.ui.activities.presen.interfaces.IClickThumbListener;
import com.epson.iprojection.ui.activities.presen.interfaces.IFatalErrorListener;
import com.epson.iprojection.ui.activities.presen.interfaces.IFiler;
import com.epson.iprojection.ui.activities.presen.interfaces.ILoadingStateListener;
import com.epson.iprojection.ui.activities.presen.interfaces.IOnClickMenuButtonListener;
import com.epson.iprojection.ui.activities.presen.interfaces.IOnFinishedCreatingThumb;
import com.epson.iprojection.ui.activities.presen.interfaces.IOnReadyListener;
import com.epson.iprojection.ui.activities.presen.interfaces.IThumbThreadManageable;
import com.epson.iprojection.ui.activities.presen.main_image.MainImgMgr;
import com.epson.iprojection.ui.activities.presen.main_image.MatrixImageView;
import com.epson.iprojection.ui.activities.presen.main_image.gesture.IOnFlickListener;
import com.epson.iprojection.ui.activities.presen.menu.FileSorter;
import com.epson.iprojection.ui.activities.presen.menu.IOnClickDialogOkListener;
import com.epson.iprojection.ui.activities.presen.pdf_filer.PdfFiler;
import com.epson.iprojection.ui.activities.presen.thumbnails.ScrollViewMgr;
import com.epson.iprojection.ui.activities.presen.thumbnails.ThumbMgr;
import com.epson.iprojection.ui.activities.presen.utils.FileChecker;
import com.epson.iprojection.ui.activities.start.IntentStreamFile;
import com.epson.iprojection.ui.common.RenderedImageFile;
import com.epson.iprojection.ui.common.ScaledImage;
import com.epson.iprojection.ui.common.actionbar.ActionBarSendable;
import com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.activity.ProjectableActivity;
import com.epson.iprojection.ui.common.activitystatus.PresenAspect;
import com.epson.iprojection.ui.common.activitystatus.eContentsType;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eFirstTimeProjectionDimension;
import com.epson.iprojection.ui.common.analytics.event.PresentationEvent;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.common.dialogs.ConnectWhenImplicitDialog;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import com.epson.iprojection.ui.common.interfaces.Capturable;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringEntrance;
import com.epson.iprojection.ui.common.toast.ToastMgr;
import com.epson.iprojection.ui.engine_wrapper.ContentRectHolder;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import java.io.File;
import java.util.Objects;

/* loaded from: classes.dex */
public class Activity_Presen extends ProjectableActivity implements IClickThumbListener, IClickButtonListener, IOnReadyListener, Capturable, IOnFlickListener, IOnClickMenuButtonListener, IOnClickDialogOkListener, ILoadingStateListener, IThumbThreadManageable, IFatalErrorListener, IOnFinishedCreatingThumb {
    public static final String INTENT_TAG_PATH = "IntentTagPath";
    protected eContentsType _eContentsType;
    protected eDrawerMenuItem _eDrawerMenuItem;
    protected eType _eType;
    protected MatrixImageView _mainView;
    protected boolean _isReady = false;
    protected ThumbMgr _thumbMgr = null;
    protected TextView _textView = null;
    protected LinearLayout _scrollLayout = null;
    protected Aspect _aspect = null;
    protected IFiler _filer = null;
    protected MainImgMgr _mainImgMgr = null;
    protected Selector _selector = null;
    protected final Handler _handler = new Handler();
    protected final ScrollViewMgr _scrollViewMgr = new ScrollViewMgr();
    protected MenuMgr _menuMgr = null;
    protected String _path = null;
    protected int _streamFileDirNo = -1;
    protected SeekButtonMgr _seekButtonMgr = null;
    protected Grayout _grayout = null;
    protected RenderedImageFile _renderedImageFile = null;
    protected Thread _decodeThread = null;
    protected boolean _isMainRendering = false;
    protected boolean _isEmpty = false;
    protected boolean _isFailedCreation = false;
    protected boolean _isChangedFile = false;
    protected boolean _isActivated = true;
    protected long _pausedTime = 0;
    private Size _contentsSize = new Size(Defines.PDF_MAX_W, 720);
    Runnable Render = new AnonymousClass1();
    private final BroadcastReceiver _receiver = new BroadcastReceiver() { // from class: com.epson.iprojection.ui.activities.presen.Activity_Presen.3
        {
            Activity_Presen.this = this;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), IntentDefine.INTENT_ACTION_MIRRORING_ON)) {
                Lg.i("ミラーリングオン");
                if (Activity_Presen.this._mainImgMgr != null) {
                    Activity_Presen.this._mainImgMgr.invalidate();
                }
            }
            if (Objects.equals(intent.getAction(), IntentDefine.INTENT_ACTION_MIRRORING_OFF)) {
                Lg.i("ミラーリングオフ");
                if (Activity_Presen.this._mainImgMgr != null) {
                    Activity_Presen.this._mainImgMgr.invalidate();
                    ContentRectHolder.INSTANCE.setContentRect(Activity_Presen.this._contentsSize.getWidth(), Activity_Presen.this._contentsSize.getHeight());
                    try {
                        Activity_Presen.this._mainImgMgr.sendImage();
                    } catch (BitmapMemoryException unused) {
                    }
                }
            }
        }
    };

    protected boolean isDeleteProcessing() {
        return false;
    }

    protected boolean isKillMyself() {
        return false;
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IOnClickMenuButtonListener
    public void onClickEdit() {
    }

    public void onClickStartEdit() {
    }

    public void onClickStopEdit() {
    }

    protected void setSelectStatus() {
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            this._grayout = new Grayout(this);
            this._path = getIntent().getStringExtra(INTENT_TAG_PATH);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(IntentDefine.INTENT_ACTION_MIRRORING_ON);
            intentFilter.addAction(IntentDefine.INTENT_ACTION_MIRRORING_OFF);
            if (Build.VERSION.SDK_INT >= 33) {
                registerReceiver(this._receiver, intentFilter, 4);
            } else {
                registerReceiver(this._receiver, intentFilter);
            }
            String str = this._path;
            if (str == null) {
                this._isEmpty = true;
                ActionBarSendable.layout(this, R.layout.toolbar_presen_empty);
                setContentView(R.layout.main_presen_empty);
                this._baseActionBar = new ActionBarSendable(this, this, this, null, false, null, true);
                MenuMgr menuMgr = new MenuMgr(this, this, null);
                this._menuMgr = menuMgr;
                menuMgr.setType(eType.DELIVER);
                if (!MirroringEntrance.INSTANCE.isMirroringSwitchOn()) {
                    Pj.getIns().sendWaitImage();
                }
                updateSelectStatusAndFiler();
                setContentsSelectStatus(this._eContentsType);
                pushDrawerStatus(this._eDrawerMenuItem);
            } else if (!isFileAvailable(str)) {
                throw new GoExitException("利用可能なファイルではありません");
            } else {
                this._streamFileDirNo = IntentStreamFile.getIntentStreamFileDirNo(this._path);
                updateSelectStatusAndFiler();
                setContentsSelectStatus(this._eContentsType);
                pushDrawerStatus(this._eDrawerMenuItem);
                if (!this._filer.Initialize(this, this._path, this, true)) {
                    Lg.e("ファイルの初期化に失敗しました");
                    throw new GoExitException("error exit");
                }
                this._aspect = new Aspect(Pj.getIns().getShadowResWidth(), Pj.getIns().getShadowResHeight());
                this._renderedImageFile = new RenderedImageFile();
                if (this._menuMgr == null) {
                    this._menuMgr = new MenuMgr(this, this, this._thumbMgr);
                }
                this._menuMgr.setType(this._eType);
                if (isImplicitIntent() && Pj.getIns().getRegisteredPjList().size() != 0 && !Pj.getIns().isConnected()) {
                    new ConnectWhenImplicitDialog(this, this._drawerList);
                }
                startForGA();
            }
        } catch (GoExitException unused) {
            this._isFailedCreation = true;
            finish();
            if (MirroringEntrance.INSTANCE.isMirroringSwitchOn()) {
                return;
            }
            Pj.getIns().sendWaitImage();
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        if (isDeleteProcessing() || this._isEmpty) {
            return;
        }
        try {
            if (this._filer.isDisconnectOccured()) {
                throw new GoExitException("サービスの切断が起こったので終了します");
            }
            try {
                if (this._filer.shouldReInit()) {
                    Lg.i("ファイル名が違うので初期化し直しました");
                    if (!this._filer.initFile()) {
                        throw new GoExitException("初期化するファイルが存在しません");
                    }
                }
                Lg.d("service available : ".concat(this._isReady ? "yes" : "no"));
            } catch (UnavailableException unused) {
                throw new GoExitException("サービスが使用できないので終了します");
            }
        } catch (GoExitException e) {
            Lg.i("Activityを終了します。 理由：[" + e.getMessage() + "]");
            this._isReady = false;
            finish();
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.ProjectableActivity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (isDeleteProcessing()) {
            return;
        }
        this._isActivated = true;
        MainImgMgr mainImgMgr = this._mainImgMgr;
        if (mainImgMgr != null) {
            mainImgMgr.activate();
        }
        if (this._isEmpty) {
            return;
        }
        startMainImageProcess();
        if (this._isReady) {
            this._thumbMgr.startLoad();
            this._baseActionBar.update();
            try {
                String filePath = this._filer.getFilePath(this._selector.getNowSelectNum());
                if (!this._filer.exists(filePath)) {
                    restartActivity(filePath, null);
                } else if (this._filer.isFileChanged(this._path)) {
                    initThumb();
                }
            } catch (UnavailableException unused) {
                finish();
            }
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        ThumbMgr thumbMgr;
        super.onPause();
        if (isDeleteProcessing()) {
            return;
        }
        this._pausedTime = System.currentTimeMillis();
        this._isActivated = false;
        MainImgMgr mainImgMgr = this._mainImgMgr;
        if (mainImgMgr != null) {
            mainImgMgr.disactivate();
        }
        if (this._isEmpty) {
            return;
        }
        if (isFinishing()) {
            new ScaledImage(this).delete();
            PresenAspect.getIns().clear(super.getTaskId());
        } else {
            ScaledImage scaledImage = new ScaledImage(this);
            try {
                MainImgMgr mainImgMgr2 = this._mainImgMgr;
                if (mainImgMgr2 != null) {
                    scaledImage.save(mainImgMgr2.getShadowBitmap());
                }
            } catch (BitmapMemoryException unused) {
                ActivityGetter.getIns().killMyProcess();
            }
            PresenAspect.getIns().set(super.getTaskId(), Pj.getIns().getShadowResWidth(), Pj.getIns().getShadowResHeight());
        }
        if (isFinishing() && (thumbMgr = this._thumbMgr) != null) {
            thumbMgr.destroy();
        }
        MainImgMgr mainImgMgr3 = this._mainImgMgr;
        if (mainImgMgr3 != null) {
            mainImgMgr3.stop();
        }
        ThumbMgr thumbMgr2 = this._thumbMgr;
        if (thumbMgr2 != null) {
            thumbMgr2.stopLoad(!this._isReady);
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        endForGA();
        popDrawerStatus();
        clearContentsSelectStatus();
        if (this._isEmpty) {
            return;
        }
        ThumbMgr thumbMgr = this._thumbMgr;
        if (thumbMgr != null) {
            thumbMgr.destroy();
        }
        IFiler iFiler = this._filer;
        if (iFiler != null) {
            iFiler.destroy();
        }
        removeTempFile();
        unregisterReceiver(this._receiver);
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IOnReadyListener
    public boolean onReady() {
        boolean z;
        try {
            z = this instanceof Activity_PresenDelivery;
        } catch (UnavailableException unused) {
            finish();
            Lg.e("サービスが使用できないので終了します");
            return false;
        } catch (BitmapMemoryException unused2) {
            ActivityGetter.getIns().killMyProcess();
        }
        if (this._filer.isRendering()) {
            ToastMgr.getIns().show(this, ToastMgr.Type.NowUsingPleaseWait);
            finish();
            Lg.e("処理中です。しばらく待ってから起動して下さい。");
            return false;
        } else if (!this._filer.initFile()) {
            ToastMgr.getIns().show(this, ToastMgr.Type.FileOpenError);
            finish();
            Lg.e("ファイルが開けないので終了します");
            return false;
        } else {
            Selector selector = new Selector(this._filer.getTotalPages());
            this._selector = selector;
            selector.setNowSelectNum(this._filer.getPos(this._path));
            this._thumbMgr = new ThumbMgr(this._filer, this._selector, this, this, this._scrollViewMgr, z, this, this);
            MenuMgr menuMgr = new MenuMgr(this, this, this._thumbMgr);
            this._menuMgr = menuMgr;
            menuMgr.setType(this._eType);
            this._mainView = RenewLayout();
            this._grayout.initialize();
            this._baseActionBar = createToolBar();
            this._baseActionBar.setFlag_sendsImgWhenConnect();
            this._seekButtonMgr = new SeekButtonMgr(this, this, this._selector);
            MainImgMgr mainImgMgr = new MainImgMgr(this._filer, this, this, this, this);
            this._mainImgMgr = mainImgMgr;
            mainImgMgr.setImageView(this._mainView);
            this._thumbMgr.initialize(this._selector.getNowSelectNum(), this._filer.getTotalPages(), this);
            this._drawerList.create();
            if (this._thumbMgr.is100Over()) {
                this._grayout.show();
                this._menuMgr.disable();
                invalidateOptionsMenu();
                this._baseActionBar.disable();
                this._drawerList.disable();
            } else {
                initThumbContinued();
            }
            updateActionBar();
            super.updatePjButtonState();
            super.updateProjBtn();
            return true;
        }
    }

    public void initThumb() {
        LinearLayout linearLayout;
        try {
            ThumbMgr thumbMgr = this._thumbMgr;
            if (thumbMgr != null) {
                linearLayout = thumbMgr.getTargetLayout();
                this._thumbMgr.stopLoad(true);
            } else {
                linearLayout = null;
            }
            boolean z = this instanceof Activity_PresenDelivery;
            ImageFiler createImageFiler = createImageFiler(z);
            this._filer = createImageFiler;
            createImageFiler.Initialize(this, this._path, this, false);
            Selector selector = new Selector(this._filer.getTotalPages());
            this._selector = selector;
            selector.setNowSelectNum(this._filer.getPos(this._path));
            this._thumbMgr = new ThumbMgr(this._filer, this._selector, this, this, this._scrollViewMgr, z, this, this);
            MenuMgr menuMgr = new MenuMgr(this, this, this._thumbMgr);
            this._menuMgr = menuMgr;
            menuMgr.setType(this._eType);
            this._thumbMgr.setTargetLayout(linearLayout);
            this._seekButtonMgr = new SeekButtonMgr(this, this, this._selector);
            MainImgMgr mainImgMgr = new MainImgMgr(this._filer, this, this, this, this);
            this._mainImgMgr = mainImgMgr;
            mainImgMgr.setImageView(this._mainView);
            this._thumbMgr.initialize(this._selector.getNowSelectNum(), this._filer.getTotalPages(), this);
            if (this._thumbMgr.is100Over()) {
                this._grayout.show();
                this._menuMgr.disable();
                invalidateOptionsMenu();
                this._baseActionBar.disable();
                this._drawerList.disable();
                return;
            }
            initThumbContinued();
        } catch (Exception unused) {
        }
    }

    protected boolean isFileAvailable(String str) {
        return FileChecker.isAvailableFile(str);
    }

    protected void updateSelectStatusAndFiler() {
        if (FileUtils.isImageFile(this._path)) {
            this._eType = eType.PHOTO;
            this._filer = new ImageFiler(this, false);
            this._eContentsType = eContentsType.Photo;
            this._eDrawerMenuItem = eDrawerMenuItem.Photo;
        } else if (FileUtils.isPdfFile(this._path)) {
            this._eType = eType.PDF;
            this._filer = new PdfFiler();
            this._eContentsType = eContentsType.Pdf;
            this._eDrawerMenuItem = eDrawerMenuItem.Document;
        }
    }

    protected ImageFiler createImageFiler(boolean z) {
        return new ImageFiler(this, z);
    }

    protected void initThumbContinued() {
        try {
            this._thumbMgr.refreshListView();
            setThumbFocusPos(this._selector.getNowSelectNum(), false);
            this._thumbMgr.startLoad();
            onClickThumb(this._filer.getPos(this._path), true);
            this._textView.setText((this._selector.getNowSelectNum() + 1) + " / " + this._selector.getTotalPages());
            interruptThumbLoadThread();
            releaseInterruptThumbLoadThread();
            setTitle();
            this._isReady = true;
        } catch (Exception unused) {
        }
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IOnFinishedCreatingThumb
    public void onFinishedCreatingThumb() {
        if (this._thumbMgr.is100Over()) {
            initThumbContinued();
            this._grayout.delete();
            this._menuMgr.enable();
            invalidateOptionsMenu();
            this._baseActionBar.enable();
            this._drawerList.enable();
        }
    }

    protected void restartActivity(String str, D_DeliveryPermission d_DeliveryPermission) {
        Intent intent = new Intent(this, Activity_Presen.class);
        intent.putExtra(INTENT_TAG_PATH, str);
        startActivity(intent);
        finish();
    }

    protected BaseCustomActionBar createToolBar() {
        ActionBarSendable actionBarSendable = new ActionBarSendable(this, this, this, (ImageButton) findViewById(R.id.btn_presen_actionbar_marker), false, this._intentCalled, false);
        this._drawerList.enableDrawerToggleButton((Toolbar) findViewById(R.id.toolbarpresen));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        return actionBarSendable;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        boolean z;
        if (this._isEmpty) {
            super.onConfigurationChanged(configuration);
            return;
        }
        this._mainView = null;
        MainImgMgr mainImgMgr = this._mainImgMgr;
        if (mainImgMgr != null) {
            MatrixImageView imageView = mainImgMgr.getImageView();
            this._mainView = imageView;
            z = imageView.isTemp();
            MatrixImageView matrixImageView = this._mainView;
            if (matrixImageView != null) {
                matrixImageView.Abort();
                this._mainView = null;
            }
        } else {
            z = false;
        }
        try {
            this._mainView = RenewLayout();
            this._baseActionBar = createToolBar();
            this._baseActionBar.setFlag_sendsImgWhenConnect();
            super.onConfigurationChanged(configuration);
            super.recreateDrawerList();
            this._baseActionBar.updateTopBarGroup();
            updateActionBar();
            super.updatePjButtonState();
            super.updateProjBtn();
            setTitle();
            this._grayout.initialize();
            this._thumbMgr.refreshListView();
            this._seekButtonMgr = new SeekButtonMgr(this, this, this._selector);
            Bitmap setImageBitmap = this._mainImgMgr.getSetImageBitmap();
            this._mainImgMgr.setImageView(this._mainView);
            this._mainImgMgr.setImage(setImageBitmap, z);
            this._thumbMgr.requestFocus(this._selector.getNowSelectNum());
            if (!this._isMainRendering) {
                onRenderingEnd();
            }
            if (this._thumbMgr.isEditMode()) {
                super.disableDrawerList();
            }
        } catch (BitmapMemoryException unused) {
            ActivityGetter.getIns().killMyProcess();
        }
    }

    protected void removeTempFile() {
        int i;
        File[] listFiles;
        if (PathGetter.getIns().isAvailableExternalStorage(this) && (i = this._streamFileDirNo) != -1) {
            for (File file : new File(IntentStreamFile.getDirPath(i)).listFiles()) {
                if (isRemovableFile(file.getPath())) {
                    file.delete();
                }
            }
        }
    }

    protected boolean isRemovableFile(String str) {
        try {
            return Long.parseLong(FileUtils.getPreffix(FileUtils.getFileName(str)).replaceAll("[^0-9]", "")) <= this._pausedTime;
        } catch (NumberFormatException unused) {
            return true;
        }
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IClickThumbListener
    public void onClickThumb(int i, boolean z) {
        Lg.d(i + "番目のサムネイルをタップ");
        if (this._grayout.isNowGrayout()) {
            Lg.d("グレーアウト中につきキャンセル");
            return;
        }
        this._selector.setNowSelectNum(i);
        setThumbFocusPos(i, z);
        updateSelectedFilePath(i);
        MemoryUtils.show(this);
    }

    protected void updateSelectedFilePath(int i) {
        try {
            this._path = FileUtils.getFilePath(this._path) + "/" + this._filer.getFileName(i);
        } catch (UnavailableException unused) {
            Lg.e("ファイルパスを更新できませんでした");
        }
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IClickButtonListener
    public void onClickSeekButton(eSeek eseek) {
        Lg.d("シークボタンクリック");
        if (this._grayout.isNowGrayout()) {
            Lg.d("グレーアウト中につきキャンセル");
        } else if (this._selector.Seek(eseek)) {
            setThumbFocusPos(this._selector.getNowSelectNum(), false);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:75:0x0045 A[Catch: BitmapMemoryException -> 0x0058, TryCatch #0 {BitmapMemoryException -> 0x0058, blocks: (B:60:0x0000, B:62:0x0019, B:64:0x0027, B:75:0x0045, B:79:0x0052, B:76:0x0049, B:78:0x004d, B:67:0x0030, B:70:0x0039), top: B:83:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0049 A[Catch: BitmapMemoryException -> 0x0058, TryCatch #0 {BitmapMemoryException -> 0x0058, blocks: (B:60:0x0000, B:62:0x0019, B:64:0x0027, B:75:0x0045, B:79:0x0052, B:76:0x0049, B:78:0x004d, B:67:0x0030, B:70:0x0039), top: B:83:0x0000 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void startMainImageProcess() {
        /*
            r3 = this;
            com.epson.iprojection.ui.activities.presen.Aspect r0 = r3._aspect     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            com.epson.iprojection.ui.engine_wrapper.Pj r1 = com.epson.iprojection.ui.engine_wrapper.Pj.getIns()     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            int r1 = r1.getShadowResWidth()     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            com.epson.iprojection.ui.engine_wrapper.Pj r2 = com.epson.iprojection.ui.engine_wrapper.Pj.getIns()     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            int r2 = r2.getShadowResHeight()     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            r0.set(r1, r2)     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            boolean r0 = r3._isReady     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            if (r0 == 0) goto L5f
            com.epson.iprojection.ui.activities.presen.main_image.MainImgMgr r0 = r3._mainImgMgr     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            r0.start()     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            com.epson.iprojection.ui.activities.presen.Aspect r0 = r3._aspect     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            boolean r0 = r0.isAspectChanged()     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            r1 = 1
            if (r0 == 0) goto L30
            com.epson.iprojection.ui.activities.presen.main_image.MainImgMgr r0 = r3._mainImgMgr     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            boolean r0 = r0.resetRenderedImage()     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            if (r0 != 0) goto L42
            goto L43
        L30:
            com.epson.iprojection.ui.common.RenderedImageFile r0 = r3._renderedImageFile     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            boolean r0 = r0.exists(r3)     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            if (r0 != 0) goto L39
            goto L43
        L39:
            com.epson.iprojection.ui.activities.presen.main_image.MainImgMgr r0 = r3._mainImgMgr     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            boolean r0 = r0.exists()     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            if (r0 != 0) goto L42
            goto L43
        L42:
            r1 = 0
        L43:
            if (r1 == 0) goto L49
            r3.setMainImage()     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            goto L52
        L49:
            boolean r0 = r3._isActivated     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            if (r0 == 0) goto L52
            com.epson.iprojection.ui.activities.presen.main_image.MainImgMgr r0 = r3._mainImgMgr     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            r0.sendImage()     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
        L52:
            com.epson.iprojection.ui.common.actionbar.base.BaseCustomActionBar r0 = r3._baseActionBar     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            r0.updateTopBarGroup()     // Catch: com.epson.iprojection.ui.common.exception.BitmapMemoryException -> L58
            goto L5f
        L58:
            com.epson.iprojection.ui.common.activity.ActivityGetter r0 = com.epson.iprojection.ui.common.activity.ActivityGetter.getIns()
            r0.killMyProcess()
        L5f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.presen.Activity_Presen.startMainImageProcess():void");
    }

    protected void setThumbFocusPos(int i, boolean z) {
        this._thumbMgr.requestFocus(i);
        if (!z) {
            setMainImage();
        }
        this._seekButtonMgr.update();
    }

    protected MatrixImageView RenewLayout() {
        LinearLayout linearLayout = this._scrollLayout;
        if (linearLayout != null) {
            linearLayout.removeAllViews();
        }
        if (this._seekButtonMgr != null) {
            this._seekButtonMgr = null;
        }
        if (getResources().getConfiguration().orientation == 2) {
            setContentView(R.layout.main_fileview_imageviewer_horizontal);
            this._scrollLayout = (LinearLayout) findViewById(R.id.layout_presen_thumbList);
            this._scrollViewMgr.setVerticalScrollView((ScrollView) findViewById(R.id.scl_presem_thumbList_ver), this._scrollLayout);
            if (getResources().getConfiguration().hardKeyboardHidden == 1) {
                updateScrollbarForChromebook();
            }
        } else {
            setContentView(R.layout.main_fileview_imageviewer_vertical);
            this._scrollLayout = (LinearLayout) findViewById(R.id.layout_presen_thumbList);
            this._scrollViewMgr.setHorizontalScrollView((HorizontalScrollView) findViewById(R.id.scl_presem_thumbList_hor), this._scrollLayout);
        }
        View childAt = ((ViewGroup) findViewById(16908290)).getChildAt(0);
        if (childAt != null) {
            childAt.setFitsSystemWindows(true);
            ViewCompat.requestApplyInsets(childAt);
        }
        this._textView = (TextView) findViewById(R.id.txt_presen_progress);
        MatrixImageView matrixImageView = (MatrixImageView) findViewById(R.id.img_presen_MainImage);
        this._thumbMgr.setTargetLayout(this._scrollLayout);
        this._textView.setText((this._selector.getNowSelectNum() + 1) + " / " + this._selector.getTotalPages());
        return matrixImageView;
    }

    @Override // com.epson.iprojection.ui.common.interfaces.Capturable
    public Bitmap capture() throws BitmapMemoryException {
        if (this._isEmpty || this._grayout.isNowGrayout()) {
            return null;
        }
        return this._mainImgMgr.getShadowBitmap();
    }

    protected void setTitle() {
        setTitle(this._path);
    }

    public void setTitle(String str) {
        if (this._streamFileDirNo != -1) {
            return;
        }
        TextView textView = (TextView) findViewById(R.id.txt_titlebar_filename);
        String fileName = FileUtils.getFileName(str);
        if (fileName == null || textView == null) {
            return;
        }
        textView.setText(fileName);
        Lg.d(fileName);
    }

    @Override // com.epson.iprojection.ui.activities.presen.main_image.gesture.IOnFlickListener
    public void onFlickLeft() {
        Lg.d("onFlickLeft");
        onClickSeekButton(eSeek.eSeek_Next);
    }

    @Override // com.epson.iprojection.ui.activities.presen.main_image.gesture.IOnFlickListener
    public void onFlickRight() {
        Lg.d("onFlickRight");
        onClickSeekButton(eSeek.eSeek_Prev);
    }

    protected void setMainImageTmp() throws BitmapMemoryException {
        Bitmap imageThumb = this._thumbMgr.getImageThumb(this._selector.getNowSelectNum());
        if (imageThumb == null || this._thumbMgr.getBlankBitmap() == imageThumb) {
            return;
        }
        this._mainImgMgr.setImage(BitmapUtils.drawBitmapFitWithIn(imageThumb, Bitmap.createBitmap(ThumbMgr.getThumbWidth(this), (ThumbMgr.getThumbWidth(this) * Pj.getIns().getShadowResHeight()) / Pj.getIns().getShadowResWidth(), Bitmap.Config.RGB_565)), true);
    }

    protected void setMainImage() {
        try {
            this._mainImgMgr.initialize();
            this._grayout.show();
            this._baseActionBar.disable();
            setMainImageTmp();
            setTitle(this._thumbMgr.getFileName());
            Thread thread = this._decodeThread;
            if (thread != null && thread.isAlive()) {
                Lg.w("レンダリング中にレンダリング指示が行われました！");
            }
            Thread thread2 = new Thread(this.Render);
            this._decodeThread = thread2;
            thread2.setPriority(10);
            this._decodeThread.start();
            this._drawerList.disable();
        } catch (UnavailableException unused) {
            finish();
            Lg.e("サービスが使用できないので終了します");
        } catch (BitmapMemoryException unused2) {
            ActivityGetter.getIns().killMyProcess();
        }
    }

    /* renamed from: com.epson.iprojection.ui.activities.presen.Activity_Presen$1 */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
            Activity_Presen.this = r1;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                MemoryUtils.show(Activity_Presen.this);
                Activity_Presen.this._isMainRendering = true;
                Activity_Presen.this.interruptThumbLoadThread();
                Bitmap image = Activity_Presen.this._mainImgMgr.getImage(Activity_Presen.this._selector.getNowSelectNum());
                Activity_Presen.this.releaseInterruptThumbLoadThread();
                if (image == null) {
                    image = BitmapUtils.createBlackBitmap(32, 24);
                    Activity_Presen.this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.presen.Activity_Presen$1$$ExternalSyntheticLambda0
                        {
                            Activity_Presen.AnonymousClass1.this = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            Activity_Presen.AnonymousClass1.this.m157x17b315fa();
                        }
                    });
                }
                if (!MirroringEntrance.INSTANCE.isMirroringSwitchOn()) {
                    ContentRectHolder.INSTANCE.setContentRect(image.getWidth(), image.getHeight());
                }
                final Bitmap createBitmapFitWithIn = BitmapUtils.createBitmapFitWithIn(image, (int) (Pj.getIns().getShadowResWidth() * 1.0f), (int) (Pj.getIns().getShadowResHeight() * 1.0f));
                if (Activity_Presen.this._renderedImageFile == null) {
                    Lg.e("renderedImageFile is null!!!");
                } else {
                    Activity_Presen.this._renderedImageFile.save(Activity_Presen.this, image);
                }
                if (image != createBitmapFitWithIn) {
                    image.recycle();
                }
                Activity_Presen.this._contentsSize = new Size(image.getWidth(), image.getHeight());
                Activity_Presen.this._isMainRendering = false;
                Activity_Presen.this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.presen.Activity_Presen$1$$ExternalSyntheticLambda1
                    {
                        Activity_Presen.AnonymousClass1.this = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        Activity_Presen.AnonymousClass1.this.m158xabf18599(createBitmapFitWithIn);
                    }
                });
            } catch (UnavailableException unused) {
                Activity_Presen.this.finish();
                Lg.e("サービスが使用できないので終了します");
            } catch (BitmapMemoryException unused2) {
                killMyActivity();
            }
        }

        /* renamed from: lambda$run$0$com-epson-iprojection-ui-activities-presen-Activity_Presen$1 */
        public /* synthetic */ void m157x17b315fa() {
            Activity_Presen.this._filer.showOpenError(Activity_Presen.this);
        }

        /* renamed from: lambda$run$1$com-epson-iprojection-ui-activities-presen-Activity_Presen$1 */
        public /* synthetic */ void m158xabf18599(Bitmap bitmap) {
            Activity_Presen.this._textView.setText((Activity_Presen.this._selector.getNowSelectNum() + 1) + " / " + Activity_Presen.this._selector.getTotalPages());
            try {
                Activity_Presen.this._mainImgMgr.setImage(bitmap, false);
                Activity_Presen.this._grayout.delete();
                Activity_Presen.this._baseActionBar.enable();
                Activity_Presen.this._drawerList.enable();
            } catch (BitmapMemoryException unused) {
                ActivityGetter.getIns().killMyProcess();
            }
        }

        private void killMyActivity() {
            Activity_Presen.this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.presen.Activity_Presen$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityGetter.getIns().killMyProcess();
                }
            });
        }
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.ILoadingStateListener
    public boolean isNowLoading() {
        return this._grayout.isNowGrayout();
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.ILoadingStateListener
    public void onRenderingStart() {
        if (this._baseActionBar != null) {
            this._baseActionBar.disable();
        }
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.ILoadingStateListener
    public void onRenderingEnd() {
        if (this._baseActionBar == null || !this._isReady) {
            return;
        }
        this._baseActionBar.enable();
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IThumbThreadManageable
    public void releaseInterruptThumbLoadThread() {
        this._thumbMgr.interrupt(false);
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IThumbThreadManageable
    public void interruptThumbLoadThread() {
        this._thumbMgr.interrupt(true);
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener
    public void onDisconnect(int i, IOnConnectListener.DisconedReason disconedReason, boolean z) {
        super.onDisconnect(i, disconedReason, z);
        if (this._isEmpty) {
            return;
        }
        this._aspect.set(Pj.getIns().getShadowResWidth(), Pj.getIns().getShadowResHeight());
        if (this._isReady && this._aspect.isAspectChanged() && !this._mainImgMgr.resetRenderedImage()) {
            setMainImage();
        }
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IFatalErrorListener
    public void onFatalErrorOccured() {
        finish();
    }

    @Override // com.epson.iprojection.ui.activities.presen.menu.IOnClickDialogOkListener
    public void onClickDialogOk() {
        if (!this._filer.Initialize(this, this._path, this, true)) {
            Lg.e("ファイルの初期化に失敗しました");
        }
        this._baseActionBar.updateTopBarGroup();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuMgr menuMgr = this._menuMgr;
        if (menuMgr != null) {
            menuMgr.onCreateOptionsMenu(this, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean z;
        MenuMgr menuMgr = this._menuMgr;
        if (menuMgr != null) {
            z = menuMgr.onPrepareOptionsMenu(menu);
            setVisivilityActionBarDummyObject(z);
        } else {
            z = false;
        }
        super.onPrepareOptionsMenu(menu);
        return z;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        this._menuMgr.onOptionsItemSelected(menuItem);
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.epson.iprojection.ui.activities.presen.interfaces.IOnClickMenuButtonListener
    public void onClickSort() {
        new FileSorter(this);
    }

    public void setVisivilityActionBarDummyObject(boolean z) {
        View findViewById = findViewById(R.id.presen_dummy_space);
        if (findViewById != null) {
            if (findViewById.getVisibility() == 8 && z) {
                findViewById.setVisibility(0);
            }
            if (findViewById.getVisibility() != 0 || z) {
                return;
            }
            findViewById.setVisibility(8);
        }
    }

    private void updateScrollbarForChromebook() {
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scl_presem_thumbList_ver);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setOnTouchListener(new View.OnTouchListener() { // from class: com.epson.iprojection.ui.activities.presen.Activity_Presen.2
            private int oldY;

            {
                Activity_Presen.this = this;
            }

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Lg.d("scrollview motionEvent " + motionEvent);
                int action = motionEvent.getAction();
                if (action != 0) {
                    if (action == 1) {
                        scrollView.setVerticalScrollBarEnabled(false);
                    } else if (action == 2) {
                        scrollView.setVerticalScrollBarEnabled(this.oldY != view.getScrollY());
                    }
                } else {
                    this.oldY = view.getScrollY();
                }
                return false;
            }
        });
    }

    private void startForGA() {
        if (this._eType == null) {
            return;
        }
        int i = AnonymousClass4.$SwitchMap$com$epson$iprojection$ui$activities$presen$eType[this._eType.ordinal()];
        if (i == 1) {
            Analytics.getIns().setFirstTimeProjectionEvent(eFirstTimeProjectionDimension.photo);
            Analytics.getIns().startContents(eCustomEvent.PHOTO_DISPLAY_START);
        } else if (i == 2) {
            Analytics.getIns().setFirstTimeProjectionEvent(eFirstTimeProjectionDimension.document);
            Analytics.getIns().startContents(eCustomEvent.DOCUMENT_DISPLAY_START);
        } else if (i == 3) {
            Analytics.getIns().setFirstTimeProjectionEvent(eFirstTimeProjectionDimension.receivedImage);
            Analytics.getIns().startContents(eCustomEvent.RECEIVED_IMAGE_DISPLAY_START);
        }
        if (Pj.getIns().isConnected()) {
            Analytics.getIns().sendEvent(eCustomEvent.FIRST_TIME_PROJECTION);
        }
        Analytics.getIns().setPresentationEvent(PresentationEvent.convertFileNameToEnum(this._path));
        Analytics.getIns().sendEvent(eCustomEvent.PRESENTATION_SCREEN);
    }

    /* renamed from: com.epson.iprojection.ui.activities.presen.Activity_Presen$4 */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$presen$eType;

        static {
            int[] iArr = new int[eType.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$presen$eType = iArr;
            try {
                iArr[eType.PHOTO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$presen$eType[eType.PDF.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$presen$eType[eType.DELIVER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private void endForGA() {
        if (this._eType == null) {
            return;
        }
        int i = AnonymousClass4.$SwitchMap$com$epson$iprojection$ui$activities$presen$eType[this._eType.ordinal()];
        if (i == 1) {
            Analytics.getIns().endContents(eCustomEvent.PHOTO_DISPLAY_END);
        } else if (i == 2) {
            Analytics.getIns().endContents(eCustomEvent.DOCUMENT_DISPLAY_END);
        } else if (i == 3 && !isKillMyself()) {
            Analytics.getIns().endContents(eCustomEvent.RECEIVED_IMAGE_DISPLAY_END);
        }
    }
}
