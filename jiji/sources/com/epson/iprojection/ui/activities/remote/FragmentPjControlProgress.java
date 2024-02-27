package com.epson.iprojection.ui.activities.remote;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;

/* loaded from: classes.dex */
public class FragmentPjControlProgress extends FragmentPjControlBase {
    private static final String KEY_PJ_INFO = "key_pj_info";

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public boolean canPjControl() {
        return false;
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public void disable() {
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public void enable() {
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    protected int getLayoutId() {
        return R.layout.main_conpj_progress;
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
    }

    public static FragmentPjControlProgress newInstance(D_HistoryInfo d_HistoryInfo) {
        FragmentPjControlProgress fragmentPjControlProgress = new FragmentPjControlProgress();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PJ_INFO, d_HistoryInfo);
        fragmentPjControlProgress.setArguments(bundle);
        return fragmentPjControlProgress;
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase
    public D_HistoryInfo getPjInfo() {
        return (D_HistoryInfo) getArguments().getSerializable(KEY_PJ_INFO);
    }

    @Override // com.epson.iprojection.ui.activities.remote.FragmentPjControlBase, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
