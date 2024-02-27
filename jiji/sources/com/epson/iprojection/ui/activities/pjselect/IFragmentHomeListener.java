package com.epson.iprojection.ui.activities.pjselect;

import android.content.Intent;

/* loaded from: classes.dex */
public interface IFragmentHomeListener {
    void actionConnectByPjListItem();

    void onResumeFragment();

    boolean prohibitStartSearch();

    void restartEngine();

    void startActivityFromMyFragment(Intent intent, int i);

    void updateHomeActionBar();
}
