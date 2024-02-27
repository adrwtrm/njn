package com.epson.iprojection.ui.activities.pjselect;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.epson.iprojection.customer_satisfaction.controllers.CSController;
import com.epson.iprojection.customer_satisfaction.datastore.CSDataStore;
import com.epson.iprojection.customer_satisfaction.entities.EState;
import com.epson.iprojection.customer_satisfaction.gateways.CSAnalytics;
import com.epson.iprojection.customer_satisfaction.gateways.CSRepository;
import com.epson.iprojection.customer_satisfaction.presenters.AssistancePresenter;
import com.epson.iprojection.customer_satisfaction.presenters.SatisfactionPresenter;
import com.epson.iprojection.customer_satisfaction.presenters.StoreReviewPresenter;
import com.epson.iprojection.engine.common.D_MppLayoutInfo;
import com.epson.iprojection.engine.common.D_MppUserInfo;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class FragmentHomeBase extends Fragment {
    protected CSController _csController;
    protected IFragmentHomeListener _implFragmentHomeListener;
    protected boolean _shouldNotifyLaunchRoute;
    protected View _view;

    protected abstract int getLayoutId();

    public void onChangeMPPControlMode(IOnConnectListener.MppControlMode mppControlMode) {
    }

    public void onChangeMirroringSwitch() {
    }

    public void onChangeScreenLockStatus(boolean z) {
    }

    public void onChangedScreenLockByMe(boolean z) {
    }

    public void onConnectCanceled() {
    }

    public void onConnectFail(int i, IOnConnectListener.FailReason failReason) {
    }

    public void onConnectSucceed() {
    }

    public void onDisconnect(int i, IOnConnectListener.DisconedReason disconedReason) {
    }

    public void onDisconnectOne(int i, IOnConnectListener.DisconedReason disconedReason) {
    }

    public void onRegisterSucceed() {
    }

    public void onUpdateMPPUserList(ArrayList<D_MppUserInfo> arrayList, ArrayList<D_MppLayoutInfo> arrayList2) {
    }

    public void setWifiConnector() {
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentHomeListener) {
            this._implFragmentHomeListener = (IFragmentHomeListener) context;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        this._implFragmentHomeListener = null;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        setRetainInstance(true);
        if (this._view == null) {
            this._view = layoutInflater.inflate(getLayoutId(), viewGroup, false);
        }
        this._csController = new CSController(new SatisfactionPresenter(this._view.getContext(), getParentFragmentManager(), getLayoutInflater()), new StoreReviewPresenter(this._view.getContext()), new CSRepository(CSDataStore.Companion.provideDataStore(this._view.getContext())), new CSAnalytics(), new AssistancePresenter(this._view.getContext()));
        getViewLifecycleOwner().getLifecycle().addObserver(this._csController);
        return this._view;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        IFragmentHomeListener iFragmentHomeListener = this._implFragmentHomeListener;
        if (iFragmentHomeListener != null) {
            iFragmentHomeListener.updateHomeActionBar();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        IFragmentHomeListener iFragmentHomeListener = this._implFragmentHomeListener;
        if (iFragmentHomeListener != null) {
            iFragmentHomeListener.onResumeFragment();
        }
        if (!this._shouldNotifyLaunchRoute || this._csController == null) {
            return;
        }
        new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.FragmentHomeBase$$ExternalSyntheticLambda0
            {
                FragmentHomeBase.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                FragmentHomeBase.this.m112xf5eee6ba();
            }
        }.run();
        this._shouldNotifyLaunchRoute = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onResume$0$com-epson-iprojection-ui-activities-pjselect-FragmentHomeBase  reason: not valid java name */
    public /* synthetic */ void m112xf5eee6ba() {
        this._csController.onChangeState(EState.COLLECTING_LAUNCH);
    }
}
