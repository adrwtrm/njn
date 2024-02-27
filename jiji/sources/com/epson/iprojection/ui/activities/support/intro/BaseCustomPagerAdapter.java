package com.epson.iprojection.ui.activities.support.intro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;
import com.epson.iprojection.R;

/* loaded from: classes.dex */
public abstract class BaseCustomPagerAdapter extends PagerAdapter {
    public int[] _IMG_ID = getImageResourceIDs();
    public int[] _STR_ID = getStringResourceIDs();
    protected LayoutInflater _inflater;

    public abstract int[] getImageResourceIDs();

    public abstract int[] getStringResourceIDs();

    public BaseCustomPagerAdapter(Context context) {
        this._inflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        LinearLayout linearLayout = (LinearLayout) this._inflater.inflate(R.layout.inflater_intro_pager, (ViewGroup) null);
        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.img_pager);
        int i2 = this._IMG_ID[i];
        if (i2 != 0) {
            imageView.setImageResource(i2);
        }
        TextView textView = (TextView) linearLayout.findViewById(R.id.TutrialText);
        int i3 = this._STR_ID[i];
        if (i3 != 0) {
            textView.setText(i3);
        }
        viewGroup.addView(linearLayout);
        return linearLayout;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this._IMG_ID.length;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view.equals(obj);
    }
}
