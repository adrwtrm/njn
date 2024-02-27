package com.epson.iprojection.ui.activities.remote;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import com.epson.iprojection.ui.activities.remote.AuthWebViewClient;
import com.epson.iprojection.ui.common.uiparts.OkCancelDialog;
import com.epson.iprojection.ui.engine_wrapper.Pj;

/* loaded from: classes.dex */
public class AuthWebViewClient extends WebViewClient {
    private static final int AUTH_STEP_1 = 1;
    private static final int AUTH_STEP_2 = 2;
    private static final int AUTH_STEP_3 = 3;
    private static final int AUTH_STEP_4 = 4;
    private static final int AUTH_STEP_5 = 5;
    private static final int AUTH_STEP_6 = 6;
    public static final boolean DEFAULT_VALUE_PASS_CHECK_ALL = true;
    private final AuthKind _authKind;
    private final View _backgroundView;
    private final boolean _batch;
    private final Context _context;
    private OkCancelDialog _dialog;
    private HttpAuthHandler _httpHandler;
    private final IOnLockBatchAuthenticationListener _implBatchLocker;
    private final ICurrentFragmentChecker _implCurrentFragmentChecker;
    private final IWebPageListener _implPageListener;
    private final AuthInfo _info;
    private boolean _isReceivedHttpAuthRequestWhenPause;
    private boolean _isResumed;
    private String _password;
    private Runnable _proceedTimeout;
    private final ProgressBar _progressBar;
    private Repeater _repeater;
    private int _tryPasswordCount;
    private WebView _webView;
    private final Handler _uiHandler = new Handler();
    private int _authStep = 1;
    private boolean _isFirstLoadFinish = false;
    private boolean _isSaved = false;
    private final PasswordChecker _passwordChecker = new PasswordChecker();

    /* loaded from: classes.dex */
    protected enum AuthKind {
        AUTH_DIGEST,
        AUTH_BASIC
    }

    public AuthWebViewClient(IOnLockBatchAuthenticationListener iOnLockBatchAuthenticationListener, Context context, IWebPageListener iWebPageListener, ICurrentFragmentChecker iCurrentFragmentChecker, AuthInfo authInfo, AuthKind authKind, boolean z, ProgressBar progressBar, View view) {
        this._implBatchLocker = iOnLockBatchAuthenticationListener;
        this._context = context;
        this._implPageListener = iWebPageListener;
        this._implCurrentFragmentChecker = iCurrentFragmentChecker;
        this._info = authInfo;
        this._authKind = authKind;
        this._batch = z;
        this._progressBar = progressBar;
        this._backgroundView = view;
    }

    public void cancel(boolean z) {
        HttpAuthHandler httpAuthHandler = this._httpHandler;
        if (httpAuthHandler != null) {
            httpAuthHandler.cancel();
            this._httpHandler = null;
        }
        dismissAuthDialog();
        IWebPageListener iWebPageListener = this._implPageListener;
        if (iWebPageListener != null) {
            iWebPageListener.onAuthCanceled(this._info.getPjInfo(), z);
        }
        IOnLockBatchAuthenticationListener iOnLockBatchAuthenticationListener = this._implBatchLocker;
        if (iOnLockBatchAuthenticationListener != null) {
            iOnLockBatchAuthenticationListener.onUnlockedBatchAuthentication();
        }
    }

    public void dismissAuthDialog() {
        OkCancelDialog okCancelDialog = this._dialog;
        if (okCancelDialog == null || !okCancelDialog.isShowing()) {
            return;
        }
        this._dialog.dismiss();
    }

    public void resume() {
        if (this._isResumed) {
            return;
        }
        this._isResumed = true;
        if (this._isReceivedHttpAuthRequestWhenPause) {
            recoverySession();
            if (this._httpHandler != null) {
                authenticate();
            }
        }
    }

    public void pause() {
        if (this._isResumed) {
            this._isResumed = false;
            this._isReceivedHttpAuthRequestWhenPause = false;
        }
    }

    private void stopProceedTimeout() {
        Runnable runnable = this._proceedTimeout;
        if (runnable != null) {
            this._uiHandler.removeCallbacks(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void proceedDelayedTimeout(String str, String str2) {
        HttpAuthHandler httpAuthHandler = this._httpHandler;
        if (httpAuthHandler != null) {
            httpAuthHandler.proceed(str, str2);
            this._uiHandler.removeCallbacks(this._proceedTimeout);
            Handler handler = this._uiHandler;
            Runnable runnable = new Runnable() { // from class: com.epson.iprojection.ui.activities.remote.AuthWebViewClient$$ExternalSyntheticLambda0
                {
                    AuthWebViewClient.this = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    AuthWebViewClient.this.m172xf9127d4b();
                }
            };
            this._proceedTimeout = runnable;
            handler.postDelayed(runnable, 10000L);
            this._passwordChecker.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$proceedDelayedTimeout$0$com-epson-iprojection-ui-activities-remote-AuthWebViewClient  reason: not valid java name */
    public /* synthetic */ void m172xf9127d4b() {
        this._implPageListener.onErrorLoad(this._webView, this._info.getPjInfo(), 0, null, null);
    }

    private void proceed(String str, String str2) {
        HttpAuthHandler httpAuthHandler = this._httpHandler;
        if (httpAuthHandler != null) {
            httpAuthHandler.proceed(str, str2);
            this._httpHandler = null;
            this._passwordChecker.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recoverySession() {
        this._authStep = 1;
        this._tryPasswordCount = 0;
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2) {
        if (!this._isResumed && !this._implCurrentFragmentChecker.isCurrentFragmentBatch()) {
            httpAuthHandler.cancel();
            this._isReceivedHttpAuthRequestWhenPause = true;
            recoverySession();
            return;
        }
        this._httpHandler = httpAuthHandler;
        this._webView = webView;
        if (this._authStep != 1 && !this._passwordChecker.isPasswordWrong()) {
            recoverySession();
        }
        if (this._isResumed) {
            authenticate();
            return;
        }
        this._isReceivedHttpAuthRequestWhenPause = true;
        recoverySession();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0152  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void authenticate() {
        /*
            Method dump skipped, instructions count: 372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.remote.AuthWebViewClient.authenticate():void");
    }

    private void showInputPasswordDialog(WebView webView) {
        Activity activity = (Activity) webView.getContext();
        final View inflate = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate(R.layout.dialog_basic_auth, (ViewGroup) activity.findViewById(R.id.layout_root));
        TextView textView = (TextView) inflate.findViewById(R.id.text_pj_name);
        CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.check_apply_all);
        if (this._batch) {
            textView.setVisibility(0);
            textView.setText(activity.getString(R.string._PjNameColon_) + this._info.getPjInfo().pjName);
        } else {
            textView.setVisibility(8);
        }
        if (shouldShowApplyAllCheckBox()) {
            checkBox.setVisibility(0);
            checkBox.setChecked(PrefUtils.readBoolean(this._context, RemotePrefUtils.PREF_TAG_PASS_CHECK_ALL, true));
            checkBox.setOnClickListener(new View.OnClickListener() { // from class: com.epson.iprojection.ui.activities.remote.AuthWebViewClient$$ExternalSyntheticLambda1
                {
                    AuthWebViewClient.this = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AuthWebViewClient.this.m173x753a5747(view);
                }
            });
        } else {
            checkBox.setVisibility(8);
        }
        TextView textView2 = (TextView) inflate.findViewById(R.id.text_password_message);
        if (this._info.getPjInfo().getPasswordType() == D_HistoryInfo.PasswordType.PASSWORD_WEB_CONTROL) {
            textView2.setText(activity.getString(R.string._WebControlPassword_));
        } else {
            textView2.setText(activity.getString(R.string._BasicPassword_));
        }
        this._dialog = new OkCancelDialog(activity, inflate, new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.remote.AuthWebViewClient.1
            private boolean _isDone = false;

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (this._isDone) {
                    return;
                }
                if (i == -2) {
                    AuthWebViewClient.this.cancel(true);
                    this._isDone = true;
                } else if (i != -1) {
                } else {
                    AuthWebViewClient.this._password = ((EditText) inflate.findViewById(R.id.edt_dlg_pass)).getText().toString();
                    AuthWebViewClient.this._isSaved = false;
                    AuthWebViewClient authWebViewClient = AuthWebViewClient.this;
                    authWebViewClient.proceedDelayedTimeout(authWebViewClient._info.getUsername(), AuthWebViewClient.this._password);
                    this._isDone = true;
                    AuthWebViewClient.this._authStep = 1;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$showInputPasswordDialog$1$com-epson-iprojection-ui-activities-remote-AuthWebViewClient  reason: not valid java name */
    public /* synthetic */ void m173x753a5747(View view) {
        PrefUtils.write(this._context, RemotePrefUtils.PREF_TAG_PASS_CHECK_ALL, ((CheckBox) view).isChecked());
    }

    private boolean shouldShowApplyAllCheckBox() {
        return Pj.getIns().isRegistered() && Pj.getIns().getRegisteredPjList().size() >= 2;
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        this._implPageListener.onStartLoad(webView, this._info.getPjInfo(), str);
        savePassword();
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
        String title;
        this._tryPasswordCount = 0;
        this._authStep = 1;
        this._isReceivedHttpAuthRequestWhenPause = false;
        this._repeater = null;
        stopProceedTimeout();
        View view = this._backgroundView;
        if (view != null) {
            view.setVisibility(8);
        }
        if (webView != null && this._authKind == AuthKind.AUTH_DIGEST && (title = webView.getTitle()) != null && (title.equals("401 - Unauthorized") || title.contains("Unauthorized"))) {
            cancel(false);
            return;
        }
        if (!this._isFirstLoadFinish) {
            this._implPageListener.onFinishLoad(this._info.getPjInfo());
            this._isFirstLoadFinish = true;
        }
        IOnLockBatchAuthenticationListener iOnLockBatchAuthenticationListener = this._implBatchLocker;
        if (iOnLockBatchAuthenticationListener != null) {
            iOnLockBatchAuthenticationListener.onUnlockedBatchAuthentication();
        }
        this._progressBar.setVisibility(8);
        savePassword();
    }

    private void receivedError(WebView webView, D_HistoryInfo d_HistoryInfo, int i, String str, String str2, boolean z) {
        stopProceedTimeout();
        if (z) {
            this._implPageListener.onErrorLoad(webView, this._info.getPjInfo(), i, str, str2);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, int i, String str, String str2) {
        receivedError(webView, this._info.getPjInfo(), i, str, str2, true);
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        receivedError(webView, this._info.getPjInfo(), webResourceError.getErrorCode(), webResourceError.getDescription().toString(), webResourceRequest.getUrl().toString(), webResourceRequest.isForMainFrame());
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        try {
            if (containEpsonInOName(sslError.getCertificate().getIssuedBy().getOName())) {
                sslErrorHandler.proceed();
            } else if (containEpsonInOName(sslError.getCertificate().getIssuedTo().getOName())) {
                sslErrorHandler.proceed();
            } else if (sslError.getPrimaryError() == 3) {
                sslErrorHandler.proceed();
            } else {
                sslErrorHandler.cancel();
            }
        } catch (Exception unused) {
            sslErrorHandler.proceed();
        }
    }

    private boolean containEpsonInOName(String str) {
        return str.contains("Epson") || str.contains("EPSON") || str.contains("epson");
    }

    @Override // android.webkit.WebViewClient
    public void onLoadResource(WebView webView, String str) {
        super.onLoadResource(webView, str);
        savePassword();
    }

    private void savePassword() {
        if (this._isSaved || this._password == null) {
            return;
        }
        PrefUtils.write(this._context, RemotePrefUtils.PREF_TAG_REMOTE_PASS + NetUtils.toHexString(this._info.getPjInfo().macAddr), this._password, (SharedPreferences.Editor) null);
        if (PrefUtils.readBoolean(this._context, RemotePrefUtils.PREF_TAG_PASS_CHECK_ALL, true) && shouldShowApplyAllCheckBox()) {
            if (this._info.isUsedWebControlPassword()) {
                RemotePasswordPrefUtils.addToWebControl(this._context, this._password);
            } else {
                RemotePasswordPrefUtils.addToRemote(this._context, this._password);
            }
        }
        this._password = null;
        this._isSaved = true;
    }

    private void clearPassword() {
        PrefUtils.delete(this._context, RemotePrefUtils.PREF_TAG_REMOTE_PASS + NetUtils.toHexString(this._info.getPjInfo().macAddr), (SharedPreferences.Editor) null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isFirstLoadFinish() {
        return this._isFirstLoadFinish;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class PasswordChecker {
        private static final long NG_TIME = 1500;
        private long _proceededTime;

        PasswordChecker() {
        }

        public void start() {
            this._proceededTime = System.currentTimeMillis();
        }

        public boolean isPasswordWrong() {
            return this._proceededTime != 0 && System.currentTimeMillis() - this._proceededTime < 1500;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class Repeater {
        private int _counter;
        private final Handler _delayedHandler = new Handler();

        Repeater() {
        }

        public boolean isOver() {
            return this._counter >= 10;
        }

        public void repeatProceed() {
            AuthWebViewClient.this.recoverySession();
            this._delayedHandler.postDelayed(new Runnable() { // from class: com.epson.iprojection.ui.activities.remote.AuthWebViewClient$Repeater$$ExternalSyntheticLambda0
                {
                    AuthWebViewClient.Repeater.this = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    AuthWebViewClient.Repeater.this.m174x5ecf5487();
                }
            }, 1000L);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$repeatProceed$0$com-epson-iprojection-ui-activities-remote-AuthWebViewClient$Repeater  reason: not valid java name */
        public /* synthetic */ void m174x5ecf5487() {
            if (AuthWebViewClient.this._httpHandler != null) {
                AuthWebViewClient.this.authenticate();
            }
            this._counter++;
        }
    }
}
