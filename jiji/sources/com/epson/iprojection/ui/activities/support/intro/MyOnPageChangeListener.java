package com.epson.iprojection.ui.activities.support.intro;

import android.widget.Button;
import android.widget.ImageView;
import androidx.viewpager.widget.ViewPager;
import com.epson.iprojection.R;

/* loaded from: classes.dex */
public class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
    private final Button _btn;
    private final ImageView[] _imgView;
    private final int _n;
    private int _page;

    @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
    }

    public MyOnPageChangeListener(ImageView[] imageViewArr, Button button) {
        this._imgView = imageViewArr;
        this._btn = button;
        imageViewArr[0].setImageResource(R.drawable.icon_proc_yu);
        this._n = imageViewArr.length;
    }

    @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        this._page = i;
        setAllNoImage();
        this._imgView[i].setImageResource(R.drawable.icon_proc_yu);
        if (i == this._n - 1) {
            this._btn.setVisibility(0);
        } else {
            this._btn.setVisibility(4);
        }
    }

    private void setAllNoImage() {
        for (ImageView imageView : this._imgView) {
            imageView.setImageResource(R.drawable.icon_proc_mu);
        }
    }

    public int getPage() {
        return this._page;
    }
}
