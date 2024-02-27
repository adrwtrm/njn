package com.epson.iprojection.ui.activities.support.intro.overlay;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import androidx.viewpager.widget.PagerAdapter;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.support.intro.BaseIntroActivity;

/* loaded from: classes.dex */
public class Activity_IntroOverlay extends BaseIntroActivity {
    protected static final int[] IMG_ID = {R.id.img_proc00, R.id.img_proc01};

    @Override // com.epson.iprojection.ui.activities.support.intro.BaseIntroActivity, com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        super.initialize(R.layout.main_intro_overlay, IMG_ID);
    }

    @Override // com.epson.iprojection.ui.activities.support.intro.BaseIntroActivity
    protected PagerAdapter createPageAdapter(Activity activity) {
        return new CustomPagerAdapter(this);
    }

    @Override // com.epson.iprojection.ui.activities.support.intro.BaseIntroActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.finish();
    }
}
