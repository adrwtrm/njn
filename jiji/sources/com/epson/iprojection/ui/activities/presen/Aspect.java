package com.epson.iprojection.ui.activities.presen;

import com.epson.iprojection.common.Lg;
import com.epson.iprojection.ui.activities.remote.RemotePrefUtils;

/* loaded from: classes.dex */
public class Aspect {
    int _h;
    int _preH;
    int _preW;
    int _w;

    public Aspect(int i, int i2) {
        this._w = i;
        this._preW = i;
        this._h = i2;
        this._preH = i2;
    }

    public void set(int i, int i2) {
        this._preW = this._w;
        this._preH = this._h;
        this._w = i;
        this._h = i2;
    }

    public boolean isAspectChanged() {
        Lg.d("旧(" + this._preW + RemotePrefUtils.SEPARATOR + this._preH + "), 新(" + this._w + RemotePrefUtils.SEPARATOR + this._h + ")");
        return isAspectChanged(this._preW, this._preH, this._w, this._h);
    }

    public static boolean isAspectChanged(int i, int i2, int i3, int i4) {
        if (i2 == 0 || i4 == 0 || (i * 1000) / i2 == (i3 * 1000) / i4) {
            return false;
        }
        Lg.i("アスペクト比が変わりました");
        return true;
    }
}
