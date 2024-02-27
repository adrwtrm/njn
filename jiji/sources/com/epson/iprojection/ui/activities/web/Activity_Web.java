package com.epson.iprojection.ui.activities.web;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Guideline;
import com.epson.iprojection.R;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.IntentDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.DefLoader;
import com.epson.iprojection.common.utils.PathGetter;
import com.epson.iprojection.ui.activities.drawermenu.eDrawerMenuItem;
import com.epson.iprojection.ui.activities.presen.Activity_Presen;
import com.epson.iprojection.ui.activities.web.WebUtils;
import com.epson.iprojection.ui.activities.web.interfaces.IOnClickBtnListener;
import com.epson.iprojection.ui.activities.web.interfaces.IOnRenderingEventListener;
import com.epson.iprojection.ui.activities.web.menu.WebMenu;
import com.epson.iprojection.ui.activities.web.menu.bookmark.Activity_Bookmark;
import com.epson.iprojection.ui.activities.web.menu.history.Activity_WebHistory;
import com.epson.iprojection.ui.common.Initializer;
import com.epson.iprojection.ui.common.ReactiveEditText;
import com.epson.iprojection.ui.common.RenderedImageFile;
import com.epson.iprojection.ui.common.ScaledImage;
import com.epson.iprojection.ui.common.activity.ActivityGetter;
import com.epson.iprojection.ui.common.activity.ProjectableActivity;
import com.epson.iprojection.ui.common.activitystatus.eContentsType;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eFirstTimeProjectionDimension;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.common.defines.IntentTagDefine;
import com.epson.iprojection.ui.common.dialogs.ConnectWhenImplicitDialog;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import com.epson.iprojection.ui.common.interfaces.Backable;
import com.epson.iprojection.ui.common.interfaces.Capturable;
import com.epson.iprojection.ui.common.singleton.RegisteredDialog;
import com.epson.iprojection.ui.common.uiparts.CustomWebChromeClient;
import com.epson.iprojection.ui.common.uiparts.IWebChromeClient;
import com.epson.iprojection.ui.common.uiparts.ProgressDialogMeterType;
import com.epson.iprojection.ui.common.uiparts.WebViewJavaScriptInterface;
import com.epson.iprojection.ui.engine_wrapper.ContentRectHolder;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.Objects;

/* loaded from: classes.dex */
public class Activity_Web extends ProjectableActivity implements TextView.OnEditorActionListener, IOnClickBtnListener, Capturable, IOnRenderingEventListener, View.OnFocusChangeListener, View.OnClickListener, Backable, IWebChromeClient {
    private static final String GOOGLE_SEARCH = "http://www.google.com/search?ie=UTF-8&q=";
    public static final String INTENT_TAG_URL = "IntentTagUrl";
    private static final String PATH_TMP_PDF = "/tmp.pdf";
    private ImageButton _btnBatsu;
    private ImageButton _btnClearUrl;
    private ReactiveEditText _editTextUrl;
    private ProgressDialogMeterType _progressDialog;
    private CustomWebView _webView;
    private CustomWebViewClient _webViewClient;
    private ValueCallback<Uri[]> mFilePathCallback;
    private ValueCallback<Uri> mUploadMessage;
    private final WebMenu _webMenu = new WebMenu(this, this);
    private BrowserData _browserData = null;
    private String _currentURL = null;
    private boolean _isPaused = false;
    private InputMethodManager _inputMethodManager = null;
    private boolean _isURLEditMod = false;
    private final Handler _handler = new Handler();
    private boolean _isDownloading = false;
    private final BroadcastReceiver _receiver = new BroadcastReceiver() { // from class: com.epson.iprojection.ui.activities.web.Activity_Web.1
        {
            Activity_Web.this = this;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), IntentDefine.INTENT_ACTION_MIRRORING_OFF)) {
                Lg.i("ミラーリングオフ！");
                try {
                    ContentRectHolder.INSTANCE.setContentRect(Activity_Web.this._webView.getWidth(), Activity_Web.this._webView.getHeight());
                    Activity_Web.this._webView.sendImage();
                } catch (BitmapMemoryException unused) {
                }
            }
        }
    };

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        new RenderedImageFile().delete(this);
        setContentsSelectStatus(eContentsType.Web);
        pushDrawerStatus(eDrawerMenuItem.Web);
        Initializer.importFiles(this);
        DefLoader.LoadSettings(this);
        setContentView(R.layout.main_web);
        this._progressDialog = new ProgressDialogMeterType(this);
        this._drawerList.enableDrawerToggleButton((Toolbar) findViewById(R.id.toolbarweb));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        ImageButton imageButton = (ImageButton) findViewById(R.id.btnBackHome);
        this._btnBatsu = imageButton;
        imageButton.setOnClickListener(this);
        this._inputMethodManager = (InputMethodManager) getSystemService("input_method");
        this._browserData = new BrowserData(this);
        String stringExtra = getIntent().getStringExtra(INTENT_TAG_URL);
        if (stringExtra == null) {
            stringExtra = this._browserData.getStartpageUrl();
        }
        ReactiveEditText reactiveEditText = (ReactiveEditText) findViewById(R.id.editUrl);
        this._editTextUrl = reactiveEditText;
        reactiveEditText.setText(stringExtra);
        this._editTextUrl.setOnEditorActionListener(this);
        this._editTextUrl.setOnFocusChangeListener(this);
        this._editTextUrl.setOnTouchListener(new View.OnTouchListener() { // from class: com.epson.iprojection.ui.activities.web.Activity_Web$$ExternalSyntheticLambda4
            {
                Activity_Web.this = this;
            }

            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return Activity_Web.this.m186x350b688b(view, motionEvent);
            }
        });
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.clear_Url);
        this._btnClearUrl = imageButton2;
        imageButton2.setOnClickListener(this);
        this._webViewClient = new CustomWebViewClient(this);
        CustomWebView customWebView = (CustomWebView) findViewById(R.id.wbv_web_main);
        this._webView = customWebView;
        customWebView.setWebChromeClient(new CustomWebChromeClient(this, R.id.ProBarId, this));
        this._webView.addJavascriptInterface(new WebViewJavaScriptInterface(this), "Native");
        this._webView.getSettings().setJavaScriptEnabled(true);
        this._webView.Initialize();
        this._webView.setWebViewClient(this._webViewClient);
        this._webView.setOnFocusChangeListener(this);
        this._webView.setDownloadListener(new DownloadListener() { // from class: com.epson.iprojection.ui.activities.web.Activity_Web$$ExternalSyntheticLambda5
            {
                Activity_Web.this = this;
            }

            @Override // android.webkit.DownloadListener
            public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                Activity_Web.this.m187x4f26e72a(str, str2, str3, str4, j);
            }
        });
        this._baseActionBar = new WebActionBar(this, this, this, this, (ImageButton) findViewById(R.id.btn_web_main_Paint), true, this._intentCalled, this);
        if (isImplicitIntent() && Pj.getIns().getRegisteredPjList().size() != 0 && !Pj.getIns().isConnected()) {
            new ConnectWhenImplicitDialog(this, this._drawerList);
        }
        this._baseActionBar.setFlag_sendsImgWhenConnect();
        goSite(stringExtra);
        Analytics.getIns().setFirstTimeProjectionEvent(eFirstTimeProjectionDimension.web);
        if (Pj.getIns().isConnected()) {
            Analytics.getIns().sendEvent(eCustomEvent.FIRST_TIME_PROJECTION);
        }
        Analytics.getIns().sendEvent(eCustomEvent.WEB_SCREEN);
        Analytics.getIns().startContents(eCustomEvent.WEB_DISPLAY_START);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IntentDefine.INTENT_ACTION_MIRRORING_OFF);
        if (Build.VERSION.SDK_INT >= 33) {
            registerReceiver(this._receiver, intentFilter, 4);
        } else {
            registerReceiver(this._receiver, intentFilter);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0008, code lost:
        if (r4 != 1) goto L5;
     */
    /* renamed from: lambda$onCreate$0$com-epson-iprojection-ui-activities-web-Activity_Web */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ boolean m186x350b688b(android.view.View r3, android.view.MotionEvent r4) {
        /*
            r2 = this;
            int r4 = r4.getAction()
            r0 = 0
            r1 = 1
            if (r4 == 0) goto Lb
            if (r4 == r1) goto L15
            goto L1e
        Lb:
            boolean r4 = r2._isURLEditMod
            if (r4 != 0) goto L15
            r3.setFocusable(r0)
            r3.setFocusableInTouchMode(r0)
        L15:
            r3.setFocusable(r1)
            r3.setFocusableInTouchMode(r1)
            r3.requestFocus()
        L1e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.web.Activity_Web.m186x350b688b(android.view.View, android.view.MotionEvent):boolean");
    }

    /* renamed from: lambda$onCreate$1$com-epson-iprojection-ui-activities-web-Activity_Web */
    public /* synthetic */ void m187x4f26e72a(String str, String str2, String str3, String str4, long j) {
        if (!isPDF(str4) || isFinishing()) {
            return;
        }
        downloadFile(str, PathGetter.getIns().getCacheDirPath() + PATH_TMP_PDF);
    }

    @Override // android.app.Activity
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        goSite(this._editTextUrl.getText().toString());
    }

    @Override // com.epson.iprojection.ui.common.activity.ProjectableActivity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        setWebButtonVisibility(0);
        this._isPaused = false;
        if (this._webView != null) {
            new ScaledImage(this).delete();
            this._webView.focus();
            try {
                this._webView.startCycleSendImage();
            } catch (BitmapMemoryException unused) {
                ActivityGetter.getIns().killMyProcess();
            }
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this._webView.focus();
        this._webView.stopCycleSendImage();
        if (isFinishing()) {
            new RenderedImageFile().delete(this);
        }
        if (isFinishing()) {
            new ScaledImage(this).delete();
        } else {
            new ScaledImage(this).save(this._webView.capture());
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Analytics.getIns().endContents(eCustomEvent.WEB_DISPLAY_END);
        clearContentsSelectStatus();
        popDrawerStatus();
        this._editTextUrl = null;
        this._webView.stopLoading();
        this._webView.setWebChromeClient(null);
        this._webView.setWebViewClient(null);
        this._webView.destroy();
        this._webView = null;
        unregisterReceiver(this._receiver);
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        String replaceAll = ((SpannableStringBuilder) textView.getText()).toString().replaceAll("^[\\s\u3000]*", "").replaceAll("[\\s\u3000]*$", "");
        if (replaceAll == null || replaceAll.length() == 0) {
            this._webView.focus();
            setWebButtonVisibility(0);
            return false;
        }
        if (replaceAll.matches(".*\\s.*") || !isUrl(replaceAll)) {
            replaceAll = GOOGLE_SEARCH + replaceAll;
        }
        goSite(fixUrl(replaceAll));
        this._webView.focus();
        setWebButtonVisibility(0);
        return true;
    }

    private void downloadFile(final String str, final String str2) {
        if (this._isDownloading) {
            return;
        }
        this._isDownloading = true;
        createProgressDialog();
        new Thread(new Runnable() { // from class: com.epson.iprojection.ui.activities.web.Activity_Web$$ExternalSyntheticLambda0
            {
                Activity_Web.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                Activity_Web.this.m185x16a8a25f(str, str2);
            }
        }).start();
    }

    /* JADX WARN: Code restructure failed: missing block: B:92:0x00e7, code lost:
        if (r3 == null) goto L27;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: lambda$downloadFile$3$com-epson-iprojection-ui-activities-web-Activity_Web */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void m185x16a8a25f(java.lang.String r11, java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 249
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.web.Activity_Web.m185x16a8a25f(java.lang.String, java.lang.String):void");
    }

    /* renamed from: lambda$downloadFile$2$com-epson-iprojection-ui-activities-web-Activity_Web */
    public /* synthetic */ void m184xfc8d23c0(int i) {
        this._progressDialog.setProgress(i / 1024);
    }

    private void createProgressDialog() {
        if (this._progressDialog.isShowing()) {
            return;
        }
        ProgressDialogMeterType progressDialogMeterType = new ProgressDialogMeterType(this);
        this._progressDialog = progressDialogMeterType;
        progressDialogMeterType.setMessage(getString(R.string._PdfDownloading_));
        this._progressDialog.setMax(1000);
        this._progressDialog.setCancelable(true);
        this._progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.epson.iprojection.ui.activities.web.Activity_Web$$ExternalSyntheticLambda3
            {
                Activity_Web.this = this;
            }

            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                Activity_Web.this.m182xfe0edf91(dialogInterface);
            }
        });
        this._progressDialog.show();
        RegisteredDialog.getIns().setDialog(this._progressDialog);
    }

    /* renamed from: lambda$createProgressDialog$4$com-epson-iprojection-ui-activities-web-Activity_Web */
    public /* synthetic */ void m182xfe0edf91(DialogInterface dialogInterface) {
        this._isDownloading = false;
    }

    private void dismissProgressDialog() {
        if (this._isDownloading) {
            this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.web.Activity_Web$$ExternalSyntheticLambda2
                {
                    Activity_Web.this = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    Activity_Web.this.m183xa73ec47c();
                }
            });
        }
    }

    /* renamed from: lambda$dismissProgressDialog$5$com-epson-iprojection-ui-activities-web-Activity_Web */
    public /* synthetic */ void m183xa73ec47c() {
        if (!this._isDownloading || isFinishing()) {
            return;
        }
        Intent intent = new Intent(getApplicationContext(), Activity_Presen.class);
        intent.putExtra(Activity_Presen.INTENT_TAG_PATH, PathGetter.getIns().getCacheDirPath() + PATH_TMP_PDF);
        startActivity(intent);
        this._progressDialog.dismiss();
        this._isDownloading = false;
    }

    private boolean isUrl(String str) {
        return (!str.contains(".") || str.startsWith(".") || str.endsWith(".")) ? false : true;
    }

    private boolean isPDF(String str) {
        String[] split = str.split("/");
        return split.length == 2 && split[0].equals("application") && split[1].equals("pdf");
    }

    private String fixUrl(String str) {
        return WebUtils.isUrlAvailable(str) ? str : WebUtils.URL_PREF_HTTP + str;
    }

    @Override // com.epson.iprojection.ui.common.interfaces.Capturable
    public Bitmap capture() {
        return this._webView.capture();
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        String stringExtra;
        String dataString;
        if (i == 1032) {
            if (this.mFilePathCallback == null) {
                super.onActivityResult(i, i2, intent);
                return;
            } else {
                this.mFilePathCallback.onReceiveValue((i2 != -1 || (dataString = intent.getDataString()) == null) ? null : new Uri[]{Uri.parse(dataString)});
                this.mFilePathCallback = null;
            }
        } else {
            super.onActivityResult(i, i2, intent);
            if ((i == 1030 || i == 1031) && i2 == -1 && (stringExtra = intent.getStringExtra(IntentTagDefine.RESULT_TAG)) != null) {
                goSite(stringExtra);
            }
        }
        this._browserData = new BrowserData(this);
    }

    @Override // com.epson.iprojection.ui.activities.web.interfaces.IOnRenderingEventListener
    public void onRenderingStart(String str) {
        ((WebActionBar) this._baseActionBar).onLoadStart();
        resetUrl(str);
        invalidateOptionsMenu();
    }

    @Override // com.epson.iprojection.ui.activities.web.interfaces.IOnRenderingEventListener
    public void onRenderingInterrupt() {
        this._webView.stopLoading();
        hideSoftKeyboard(getCurrentFocus());
        setWebButtonVisibility(0);
        invalidateOptionsMenu();
    }

    @Override // com.epson.iprojection.ui.activities.web.interfaces.IOnRenderingEventListener
    public void onRenderingEnd(String str) {
        ((WebActionBar) this._baseActionBar).onLoadEnd();
        if (this._webView.getTitle() == null || str == null) {
            Lg.w("ページタイトルかURLがNULLです");
        } else {
            this._browserData.addHistoryData(this._webView.getTitle(), this._webView.getUrl());
        }
        resetUrl(str);
        try {
            this._webView.sendImage();
        } catch (BitmapMemoryException unused) {
            ActivityGetter.getIns().killMyProcess();
        }
        invalidateOptionsMenu();
    }

    @Override // com.epson.iprojection.ui.activities.web.interfaces.IOnRenderingEventListener
    public void onRenderingError() {
        invalidateOptionsMenu();
        if (Pj.getIns().isConnected()) {
            new NotUseWebDialog(this).show();
        }
    }

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 4) {
            setWebButtonVisibility(0);
        }
        return super.onKeyUp(i, keyEvent);
    }

    public void stopLoading() {
        this._webView.stopLoading();
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View view, boolean z) {
        ReactiveEditText reactiveEditText = this._editTextUrl;
        if (view == reactiveEditText) {
            if (z && reactiveEditText == getCurrentFocus()) {
                this._editTextUrl.setText(this._currentURL);
                setWebButtonVisibility(8);
                updateForcedStateChangeModLckIcons(8);
                showSoftKeyboard();
                return;
            }
            if (this._editTextUrl != getCurrentFocus()) {
                hideSoftKeyboard(this._editTextUrl);
                String obj = this._editTextUrl.getText().toString();
                if (!obj.equals(this._currentURL)) {
                    this._currentURL = obj;
                }
                resetUrl(this._currentURL);
            }
            this._webView.focus();
            setWebButtonVisibility(0);
            updateActionBar();
        }
    }

    @Override // com.epson.iprojection.ui.activities.web.interfaces.IOnClickBtnListener
    public void onClick_BackBtn() {
        this._webView.goBack();
    }

    @Override // com.epson.iprojection.ui.activities.web.interfaces.IOnClickBtnListener
    public void onClick_ForwardBtn() {
        this._webView.goForward();
    }

    @Override // com.epson.iprojection.ui.activities.web.interfaces.IOnClickBtnListener
    public void onClick_BookMarkBtn() {
        startActivityForResult(new Intent(getApplicationContext(), Activity_Bookmark.class), CommonDefine.REQUEST_CODE_WEB_INTENT_BOOKMARK);
    }

    @Override // com.epson.iprojection.ui.activities.web.interfaces.IOnClickBtnListener
    public void onClick_HistoryBtn() {
        startActivityForResult(new Intent(getApplicationContext(), Activity_WebHistory.class), CommonDefine.REQUEST_CODE_WEB_INTENT_HISTORY);
    }

    @Override // com.epson.iprojection.ui.activities.web.interfaces.IOnClickBtnListener
    public void onClick_AddBookMarkBtn() {
        String title = this._webView.getTitle();
        String url = this._webView.getUrl();
        if (url == null) {
            return;
        }
        if (title == null) {
            title = getString(R.string._Untitled_);
        }
        this._browserData.addBkmkData(title, url);
    }

    @Override // com.epson.iprojection.ui.activities.web.interfaces.IOnClickBtnListener
    public void onClick_ReloadBtn() {
        this._webView.reload();
    }

    @Override // com.epson.iprojection.ui.activities.web.interfaces.IOnClickBtnListener
    public void onClick_StopBtn() {
        this._webView.stopLoading();
    }

    @Override // com.epson.iprojection.ui.common.activity.ProjectableActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view == this._btnClearUrl) {
            onClick_ClearBtn();
        }
        if (view == this._btnBatsu) {
            finish();
        }
    }

    public void onClick_ClearBtn() {
        ReactiveEditText reactiveEditText = this._editTextUrl;
        if (reactiveEditText != null) {
            this._currentURL = null;
            reactiveEditText.setText((CharSequence) null);
        }
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        this._webMenu.onCreateOptionsMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public boolean onPrepareOptionsMenu(Menu menu) {
        String obj = this._editTextUrl.getText().toString();
        String str = this._currentURL;
        if (str != null) {
            obj = str;
        }
        boolean onPrepareOptionsMenu = this._webMenu.onPrepareOptionsMenu(menu, this._webView.canGoForward(), this._webViewClient.isRendering(), WebUtils.isUrlAvailable(obj));
        super.onPrepareOptionsMenu(menu);
        return onPrepareOptionsMenu;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        this._webMenu.onOptionsItemSelected(menuItem);
        return super.onOptionsItemSelected(menuItem);
    }

    private void resetUrl(String str) {
        if (str != null && !str.equals("") && this._editTextUrl != getCurrentFocus() && WebUtils.isUrlAvailable(str)) {
            this._currentURL = str;
            if (WebUtils.uriScheme.HTTP == WebUtils.getUriType(str)) {
                if (str.startsWith("http://www.")) {
                    str = str.substring(11);
                } else {
                    str = str.substring(7);
                }
            }
            Lg.d("omitted Url:" + str);
        }
        this._editTextUrl.setText(str);
    }

    private void goSite(String str) {
        if (str != null) {
            this._webView.focus();
            this._webView.loadUrl(str);
            return;
        }
        Lg.d("URL is null.");
    }

    private void showSoftKeyboard() {
        InputMethodManager inputMethodManager = this._inputMethodManager;
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(2, 0);
        }
    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = this._inputMethodManager;
        if (inputMethodManager != null) {
            if (view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } else {
                getWindow().setSoftInputMode(3);
            }
        }
    }

    public boolean isSendable() {
        return !this._isPaused && this._intentCalled.isCallable();
    }

    protected void setWebButtonVisibility(int i) {
        ImageButton imageButton = (ImageButton) findViewById(R.id.btn_web_main_Paint);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.clear_Url);
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.btn_projection);
        EditText editText = (EditText) findViewById(R.id.editUrl);
        if (8 == i) {
            editText.setPadding(5, 0, getResources().getDimensionPixelSize(R.dimen.ClearButtonPadSize), 0);
            editText.invalidate();
            imageButton2.setVisibility(0);
            this._isURLEditMod = true;
            this._editTextUrl.setFocusable(true);
        } else {
            editText.setPadding(5, 0, 5, 0);
            editText.invalidate();
            imageButton2.setVisibility(8);
            this._isURLEditMod = false;
            this._editTextUrl.setFocusable(false);
        }
        setCommonMenuVisibility(i);
        this._btnBatsu.setVisibility(i);
        imageButton.setVisibility(i);
        imageButton3.setVisibility(i);
    }

    public void setCommonMenuVisibility(int i) {
        Guideline guideline = (Guideline) findViewById(R.id.left_guideline);
        Guideline guideline2 = (Guideline) findViewById(R.id.right_guideline);
        if (i == 8) {
            guideline.setGuidelineBegin(0);
            guideline2.setGuidelineEnd(0);
            return;
        }
        guideline.setGuidelineBegin(getResources().getDimensionPixelSize(R.dimen.DrawerIconSpace));
        guideline2.setGuidelineEnd(getResources().getDimensionPixelSize(R.dimen.ActionBarSideMargin));
    }

    @Override // com.epson.iprojection.ui.common.interfaces.Backable
    public boolean canGoBack() {
        return this._webView.canGoBack();
    }

    @Override // com.epson.iprojection.ui.common.interfaces.Backable
    public void goBack() {
        this._webView.goBack();
    }

    @Override // com.epson.iprojection.ui.common.uiparts.IWebChromeClient
    public void setUploadMessage(ValueCallback<Uri> valueCallback) {
        this.mUploadMessage = valueCallback;
    }

    @Override // com.epson.iprojection.ui.common.uiparts.IWebChromeClient
    public ValueCallback<Uri> getUploadMessage() {
        return this.mUploadMessage;
    }

    @Override // com.epson.iprojection.ui.common.uiparts.IWebChromeClient
    public void setFilePathCallback(ValueCallback<Uri[]> valueCallback) {
        this.mFilePathCallback = valueCallback;
    }

    @Override // com.epson.iprojection.ui.common.uiparts.IWebChromeClient
    public ValueCallback<Uri[]> getFilePathCallback() {
        return this.mFilePathCallback;
    }
}
