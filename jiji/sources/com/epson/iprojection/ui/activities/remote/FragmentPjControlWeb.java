package com.epson.iprojection.ui.activities.remote;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import com.epson.iprojection.ui.activities.remote.AuthWebViewClient;
import com.epson.iprojection.ui.common.uiparts.CustomWebChromeClient;
import com.epson.iprojection.ui.common.uiparts.IWebChromeClient;
import com.epson.iprojection.ui.common.uiparts.WebViewJavaScriptInterface;

/* loaded from: classes.dex */
public class FragmentPjControlWeb extends FragmentPjControlTouchPad {
    private static final String KEY_AUTH_BATCH = "key_auth_batch";
    private static final String KEY_AUTH_KIND = "key_auth_kind";
    private static final String KEY_PJ_INFO = "key_pj_info";
    protected IOnLockBatchAuthenticationListener _implBatchLocker;
    protected ICurrentFragmentChecker _implCurrentFragmentChecker;
    protected IWebChromeClient _implWebChromeClient;
    protected IWebPageListener _implWebPageListener;
    protected boolean _isResumed = false;
    protected WebView _web;
    protected AuthWebViewClient _webViewClient;

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public void disable() {
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public void enable() {
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    protected int getLayoutId() {
        return R.layout.main_conpj_pjcontrol_new;
    }

    public static FragmentPjControlWeb newInstance(D_HistoryInfo d_HistoryInfo, boolean z, boolean z2) {
        FragmentPjControlWeb fragmentPjControlWeb = new FragmentPjControlWeb();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PJ_INFO, d_HistoryInfo);
        bundle.putBoolean(KEY_AUTH_KIND, z);
        bundle.putBoolean(KEY_AUTH_BATCH, z2);
        fragmentPjControlWeb.setArguments(bundle);
        return fragmentPjControlWeb;
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public void resume() {
        this._isResumed = true;
        AuthWebViewClient authWebViewClient = this._webViewClient;
        if (authWebViewClient != null) {
            authWebViewClient.resume();
        }
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public void pause() {
        this._isResumed = false;
        AuthWebViewClient authWebViewClient = this._webViewClient;
        if (authWebViewClient != null) {
            authWebViewClient.pause();
            if (getActivity().isFinishing()) {
                this._webViewClient.cancel(false);
            }
        }
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void setOnLockBatchAuthenticationListener(IOnLockBatchAuthenticationListener iOnLockBatchAuthenticationListener) {
        this._implBatchLocker = iOnLockBatchAuthenticationListener;
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public D_HistoryInfo getPjInfo() {
        return (D_HistoryInfo) getArguments().getSerializable(KEY_PJ_INFO);
    }

    public AuthWebViewClient.AuthKind getAuthKind() {
        return getArguments().getBoolean(KEY_AUTH_KIND) ? AuthWebViewClient.AuthKind.AUTH_DIGEST : AuthWebViewClient.AuthKind.AUTH_BASIC;
    }

    public boolean getAuthBatch() {
        return getArguments().getBoolean(KEY_AUTH_BATCH);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public void authenticated() {
        RemotePrefUtils.authenticated(getActivity(), getPjInfo().macAddr);
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public boolean isAuthenticated() {
        return RemotePrefUtils.isAuthenticated(getActivity(), getPjInfo().macAddr);
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IWebPageListener) {
            this._implWebPageListener = (IWebPageListener) context;
        }
        if (context instanceof IWebChromeClient) {
            this._implWebChromeClient = (IWebChromeClient) context;
        }
        if (context instanceof ICurrentFragmentChecker) {
            this._implCurrentFragmentChecker = (ICurrentFragmentChecker) context;
        }
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        this._implWebPageListener = null;
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlTouchPad, com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        initializeTouchPad();
        if (this._web == null) {
            initializeWeb(view);
            this._web.clearCache(true);
            this._web.loadUrl(AuthInfo.createAuthInfo(getPjInfo()).getUrl());
        }
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        WebView webView = this._web;
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        WebView webView = this._web;
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        if (this._web != null) {
            dismissAuthDialog();
            this._web.stopLoading();
            this._web.setWebChromeClient(null);
            this._web.setWebViewClient(null);
            unregisterForContextMenu(this._web);
            this._web.destroy();
        }
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public void onFragmentResume() {
        if (this._webViewClient.isFirstLoadFinish()) {
            super.onFragmentResume();
        }
    }

    public void dismissAuthDialog() {
        AuthWebViewClient authWebViewClient = this._webViewClient;
        if (authWebViewClient != null) {
            authWebViewClient.dismissAuthDialog();
        }
    }

    protected void initializeWeb(View view) {
        this._web = (WebView) view.findViewById(R.id.web_remocon);
        AuthWebViewClient authWebViewClient = new AuthWebViewClient(this._implBatchLocker, getActivity(), this._implWebPageListener, this._implCurrentFragmentChecker, AuthInfo.createAuthInfo(getPjInfo()), getAuthKind(), getAuthBatch(), (ProgressBar) view.findViewById(R.id.prgress_not_auth_yet), view.findViewById(R.id.view_background));
        this._webViewClient = authWebViewClient;
        this._web.setWebViewClient(authWebViewClient);
        WebSettings settings = this._web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setCacheMode(1);
        setupZoomControl(settings);
        this._web.setScrollBarStyle(0);
        this._web.requestFocus();
        MethodUtil.compatSetDrawingCacheEnabled(this._web, true);
        this._web.setWebChromeClient(new CustomWebChromeClient(getActivity(), R.id.prg_load, this._implWebChromeClient));
        this._web.addJavascriptInterface(new WebViewJavaScriptInterface(getActivity()), "Native");
        this._web.getSettings().setJavaScriptEnabled(true);
        this._web.setVerticalScrollBarEnabled(true);
        this._web.clearCache(true);
        this._webViewClient.resume();
    }

    private void setupZoomControl(WebSettings webSettings) {
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);
    }
}
