package com.epson.iprojection.common.utils;

import java.util.LinkedList;

/* loaded from: classes.dex */
public class Fps {
    private long _lastTime;
    private LinkedList<Long> _list = new LinkedList<>();

    public void update() {
        long currentTimeMillis = System.currentTimeMillis();
        long j = this._lastTime;
        if (j != 0) {
            this._list.add(Long.valueOf(currentTimeMillis - j));
            if (this._list.size() > 60) {
                this._list.removeFirst();
            }
        }
        this._lastTime = currentTimeMillis;
    }

    public float get() {
        if (this._list.size() == 0) {
            return 0.0f;
        }
        long j = 0;
        for (int i = 0; i < this._list.size(); i++) {
            j += this._list.get(i).longValue();
        }
        return 1000.0f / (((float) j) / this._list.size());
    }
}
