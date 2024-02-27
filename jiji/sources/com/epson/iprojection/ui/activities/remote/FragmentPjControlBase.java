package com.epson.iprojection.ui.activities.remote;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import com.epson.iprojection.ui.common.activity.base.IproBaseActivity;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.event.screenview.ScreenNameUtils;

/* loaded from: classes.dex */
public abstract class FragmentPjControlBase extends Fragment implements View.OnClickListener {
    protected Activity _activity;
    protected IFragmentPjControlListener _implFragmentPjControlListener;
    protected View _view;

    /* JADX INFO: Access modifiers changed from: protected */
    public void authenticated() {
    }

    public boolean canPjControl() {
        return true;
    }

    public void changeFlipper(boolean z, boolean z2) {
    }

    public abstract void disable();

    public abstract void enable();

    protected abstract int getLayoutId();

    public abstract D_HistoryInfo getPjInfo();

    public boolean haveFlipper() {
        return false;
    }

    public boolean isAuthenticated() {
        return false;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
    }

    public void pause() {
    }

    public void resume() {
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!Analytics.getIns().isSetuped()) {
            Analytics.getIns().setup(getActivity());
        }
        this._activity = getActivity();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        setRetainInstance(true);
        if (this._view == null) {
            this._view = layoutInflater.inflate(getLayoutId(), viewGroup, false);
        }
        return this._view;
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentPjControlListener) {
            this._implFragmentPjControlListener = (IFragmentPjControlListener) context;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        this._implFragmentPjControlListener = null;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        IFragmentPjControlListener iFragmentPjControlListener = this._implFragmentPjControlListener;
        if (iFragmentPjControlListener != null) {
            iFragmentPjControlListener.onFragmentViewCreated();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        onFragmentResume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFragmentResume() {
        IFragmentPjControlListener iFragmentPjControlListener = this._implFragmentPjControlListener;
        if (iFragmentPjControlListener != null) {
            iFragmentPjControlListener.onFragmentResume();
        }
    }

    public void sendGoogleAnalyticsScreenName(String str) {
        ((IproBaseActivity) requireActivity())._screenName = ScreenNameUtils.Companion.createScreenName(str);
        Analytics.getIns().sendScreenEvent(((IproBaseActivity) requireActivity())._screenName);
    }
}
