package com.epson.iprojection.ui.activities.pjselect;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.customer_satisfaction.entities.EState;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.registed.IOnCaughtSsidWarningListener;
import com.epson.iprojection.ui.activities.pjselect.registed.PanelButtons;
import com.epson.iprojection.ui.activities.pjselect.registed.SsidChecker;
import com.epson.iprojection.ui.common.uiparts.OKDialog;
import com.epson.iprojection.ui.common.uiparts.OkCancelDialog;
import com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class FragmentHomeRegistered extends FragmentHomeBaseSelected implements IPjManualSearchResultListener, IOnCaughtSsidWarningListener {
    protected PanelButtons _panelButtons;
    protected SsidChecker _ssidChecker;
    private OKDialog _ssidWarningDialog;
    private OkCancelDialog _unregisterDialog;
    protected final Handler _handler = new Handler();
    protected boolean _isResumed = false;
    private final Runnable _updater = new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.FragmentHomeRegistered.1
        {
            FragmentHomeRegistered.this = this;
        }

        @Override // java.lang.Runnable
        public void run() {
            FragmentHomeRegistered.this._adapter.notifyDataSetChanged();
            FragmentHomeRegistered.this._handler.removeCallbacks(FragmentHomeRegistered.this._updater);
            FragmentHomeRegistered.this._handler.postDelayed(FragmentHomeRegistered.this._updater, 1000L);
        }
    };

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase
    protected int getLayoutId() {
        return R.layout.main_home_registered;
    }

    public static FragmentHomeBase newInstance() {
        FragmentHomeRegistered fragmentHomeRegistered = new FragmentHomeRegistered();
        fragmentHomeRegistered.setArguments(new Bundle());
        return fragmentHomeRegistered;
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBaseSelected, com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) super.onCreateView(layoutInflater, viewGroup, bundle);
        setupViews(viewGroup2);
        startCyclicUpdateStatus();
        this._panelRootLayout = (LinearLayout) viewGroup2.findViewById(R.id.layout_panel_root);
        addPanelViews(this._panelRootLayout);
        return viewGroup2;
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= 29 || this._implFragmentHomeListener.prohibitStartSearch() || this._ssidChecker != null) {
            return;
        }
        SsidChecker ssidChecker = new SsidChecker(getActivity(), this, this);
        this._ssidChecker = ssidChecker;
        ssidChecker.start();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase, androidx.fragment.app.Fragment
    public void onResume() {
        if (this._shouldNotifyLaunchRoute && this._csController != null) {
            new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.FragmentHomeRegistered$$ExternalSyntheticLambda1
                {
                    FragmentHomeRegistered.this = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    FragmentHomeRegistered.this.m113xe974abab();
                }
            }.run();
        }
        super.onResume();
        this._isResumed = true;
        this._panelButtons.update(this._panelRootLayout);
        Pj.getIns().startUpdatePjStatusOfRegisted();
        if (this._implFragmentHomeListener.prohibitStartSearch() || this._ssidChecker != null) {
            return;
        }
        Pj.getIns().startCyclicSeachForRegisted();
    }

    /* renamed from: lambda$onResume$0$com-epson-iprojection-ui-activities-pjselect-FragmentHomeRegistered */
    public /* synthetic */ void m113xe974abab() {
        this._csController.onChangeState(EState.USING);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this._isResumed = false;
        Pj.getIns().stopUpdatePjStatusOfRegisted();
        Pj.getIns().stopCyclicSearchForRegisted();
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBaseSelected, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this._handler.removeCallbacks(this._updater);
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBaseSelected
    protected void addPanelViews(LinearLayout linearLayout) {
        if (Pj.getIns().isAllPjTypeHome()) {
            this._panelViews = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.inflater_home_registered_panels_home, (ViewGroup) null);
        } else {
            this._panelViews = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.inflater_home_registered_panels_signage, (ViewGroup) null);
        }
        linearLayout.addView(this._panelViews, linearLayout.getChildCount());
        this._panelButtons = new PanelButtons(getActivity(), linearLayout, ((Activity_PjSelect) getActivity()).getDrawerList());
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBaseSelected
    public void setupViews(View view) {
        super.setupViews(view);
        Button button = (Button) view.findViewById(R.id.listUnregister);
        button.setText(R.string._SelectProjectorAgain_);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.FragmentHomeRegistered$$ExternalSyntheticLambda2
            {
                FragmentHomeRegistered.this = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                FragmentHomeRegistered.this.m114x4f8bb6b2(view2);
            }
        });
        updateListVisibility();
    }

    /* renamed from: lambda$setupViews$2$com-epson-iprojection-ui-activities-pjselect-FragmentHomeRegistered */
    public /* synthetic */ void m114x4f8bb6b2(View view) {
        OkCancelDialog okCancelDialog = this._unregisterDialog;
        if (okCancelDialog == null || !okCancelDialog.isShowing()) {
            this._unregisterDialog = new OkCancelDialog(getActivity(), getActivity().getString(R.string._UnregisterProjectors_), new DialogInterface.OnClickListener() { // from class: com.epson.iprojection.ui.activities.pjselect.FragmentHomeRegistered$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    FragmentHomeRegistered.lambda$setupViews$1(dialogInterface, i);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$setupViews$1(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            Pj.getIns().clearRegisteredPjInf();
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBaseSelected
    protected ArrayList<ConnectPjInfo> getPjList() {
        return Pj.getIns().getRegisteredPjList();
    }

    private void startCyclicUpdateStatus() {
        this._handler.post(this._updater);
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
    public void onFindSearchPj(D_PjInfo d_PjInfo, boolean z) {
        SsidChecker ssidChecker = this._ssidChecker;
        if (ssidChecker != null) {
            ssidChecker.onFindSearchPj(d_PjInfo, z);
        }
    }

    @Override // com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener
    public void onEndSearchPj() {
        SsidChecker ssidChecker = this._ssidChecker;
        if (ssidChecker != null) {
            ssidChecker.onEndSearchPj();
            this._ssidChecker = null;
            if (this._isResumed) {
                Pj.getIns().startCyclicSeachForRegisted();
            }
        }
    }

    @Override // com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase
    public void onConnectFail(int i, IOnConnectListener.FailReason failReason) {
        Pj.getIns().startCyclicSeachForRegisted();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.epson.iprojection.ui.activities.pjselect.FragmentHomeRegistered$2] */
    @Override // com.epson.iprojection.ui.activities.pjselect.registed.IOnCaughtSsidWarningListener
    public void onCaughtSsidWarning() {
        new AsyncTask<Void, Void, Void>() { // from class: com.epson.iprojection.ui.activities.pjselect.FragmentHomeRegistered.2
            {
                FragmentHomeRegistered.this = this;
            }

            @Override // android.os.AsyncTask
            public Void doInBackground(Void... voidArr) {
                while (!FragmentHomeRegistered.this.isAdded()) {
                    Sleeper.sleep(100L);
                }
                return null;
            }

            @Override // android.os.AsyncTask
            public void onPostExecute(Void r1) {
                FragmentHomeRegistered.this.caughtSsidWarningDialog();
            }
        }.execute(new Void[0]);
    }

    public synchronized void caughtSsidWarningDialog() {
        OKDialog oKDialog = this._ssidWarningDialog;
        if (oKDialog == null || !oKDialog.isShowing()) {
            this._ssidWarningDialog = new OKDialog(getActivity(), getString(R.string._DifferentSSIDWhenRegisteredProjector_));
        }
    }
}
