package com.epson.iprojection.ui.engine_wrapper.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.view.ViewCompat;
import com.epson.iprojection.R;

/* loaded from: classes.dex */
public class SleepWarningBoardDrawer {
    public static void draw(Context context, Canvas canvas) {
        float width = canvas.getWidth() / 50.0f;
        float f = width / 2.0f;
        String string = context.getString(R.string._WarningSleep01_);
        String string2 = context.getString(R.string._WarningSleep02_);
        Paint paint = new Paint();
        paint.setTextSize(width);
        paint.setColor(-1);
        paint.setAntiAlias(true);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float f2 = -(fontMetrics.ascent * 0.8764912f);
        float f3 = -fontMetrics.ascent;
        float max = Math.max(paint.measureText(string), paint.measureText(string2));
        Paint paint2 = new Paint();
        paint2.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint2.setAlpha(165);
        float f4 = width + f;
        canvas.drawRect(width, width, max + f4 + f, f4 + f3 + f + f3 + f, paint2);
        float f5 = f2 + width + f;
        canvas.drawText(string, f4, f5, paint);
        canvas.drawText(string2, f4, f5 + f3 + f, paint);
    }
}
