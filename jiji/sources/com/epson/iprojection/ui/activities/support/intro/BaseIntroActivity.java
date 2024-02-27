package com.epson.iprojection.ui.activities.support.intro;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.common.activity.base.PjConnectableActivity;

/* loaded from: classes.dex */
public abstract class BaseIntroActivity extends PjConnectableActivity implements View.OnClickListener {
    protected ImageButton _btnBatsu;
    protected Button _btnOK;

    protected abstract PagerAdapter createPageAdapter(Activity activity);

    @Override // com.epson.iprojection.ui.common.activity.base.PjConnectableActivity, com.epson.iprojection.ui.common.activity.base.IproBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initialize(int i, int[] iArr) {
        setContentView(i);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(createPageAdapter(this));
        int length = iArr.length;
        ImageView[] imageViewArr = new ImageView[length];
        for (int i2 = 0; i2 < length; i2++) {
            imageViewArr[i2] = (ImageView) findViewById(iArr[i2]);
        }
        Button button = (Button) findViewById(R.id.btn_intro);
        this._btnOK = button;
        button.setOnClickListener(this);
        ImageButton imageButton = (ImageButton) findViewById(R.id.btn_batsu);
        this._btnBatsu = imageButton;
        imageButton.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener(imageViewArr, this._btnOK));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_proc);
        if (iArr.length == 1) {
            this._btnOK.setVisibility(0);
            linearLayout.setVisibility(8);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        super.finish();
    }
}
