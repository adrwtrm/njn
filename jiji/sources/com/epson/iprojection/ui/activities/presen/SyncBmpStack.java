package com.epson.iprojection.ui.activities.presen;

import android.graphics.Bitmap;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class SyncBmpStack {
    private final LinkedList<Bitmap> _list = new LinkedList<>();

    private synchronized Bitmap operate(Bitmap bitmap) {
        if (bitmap == null) {
            return this._list.poll();
        }
        this._list.add(bitmap);
        return null;
    }

    public Bitmap push(Bitmap bitmap) {
        return operate(bitmap);
    }

    public Bitmap pop() {
        return operate(null);
    }
}
