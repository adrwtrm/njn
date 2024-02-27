package com.epson.iprojection.ui.activities.support.intro.notfindpj;

import android.content.Context;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.support.intro.BaseCustomPagerAdapter;

/* loaded from: classes.dex */
public class CustomPagerAdapter extends BaseCustomPagerAdapter {
    private static final int[] IMG_ID = {R.drawable.intro_notfindpj_00, R.drawable.intro_notfindpj_01, R.drawable.intro_notfindpj_02, R.drawable.intro_notfindpj_03};
    private static final int[] STR_ID = {R.string._Tutorial_NoPjFound00_, R.string._Tutorial_NoPjFound01_, R.string._Tutorial_NoPjFound02_, R.string._Tutorial_NoPjFound03_};

    public CustomPagerAdapter(Context context) {
        super(context);
    }

    @Override // com.epson.iprojection.ui.activities.support.intro.BaseCustomPagerAdapter
    public int[] getImageResourceIDs() {
        return IMG_ID;
    }

    @Override // com.epson.iprojection.ui.activities.support.intro.BaseCustomPagerAdapter
    public int[] getStringResourceIDs() {
        return STR_ID;
    }
}
