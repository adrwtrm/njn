package com.epson.iprojection.ui.activities.support.intro.overlay;

import android.content.Context;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.support.intro.BaseCustomPagerAdapter;

/* loaded from: classes.dex */
public class CustomPagerAdapter extends BaseCustomPagerAdapter {
    private static final int[] IMG_ID = {R.drawable.intro_overlay_00, R.drawable.intro_overlay_01};
    private static final int[] STR_ID = {R.string._Tutorial_Overlay00_, R.string._Tutorial_Overlay01_};

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
