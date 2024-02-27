package com.epson.iprojection.ui.activities.camera.views.adapters;

import android.graphics.drawable.Drawable;
import android.widget.ImageButton;
import android.widget.ImageView;

/* loaded from: classes.dex */
public class DataBindingAdapters {
    public static void setImageDrawable(ImageView imageView, Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    public static void setImageResource(ImageView imageView, int i) {
        imageView.setImageResource(i);
    }

    public static void setImageResource(ImageButton imageButton, Drawable drawable) {
        imageButton.setImageDrawable(drawable);
    }

    public static void setImageResource(ImageButton imageButton, int i) {
        imageButton.setImageResource(i);
    }
}
