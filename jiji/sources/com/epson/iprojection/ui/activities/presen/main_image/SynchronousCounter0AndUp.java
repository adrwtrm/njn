package com.epson.iprojection.ui.activities.presen.main_image;

import com.epson.iprojection.common.utils.SynchronousCounter;

/* loaded from: classes.dex */
public class SynchronousCounter0AndUp extends SynchronousCounter {
    @Override // com.epson.iprojection.common.utils.SynchronousCounter
    public synchronized void subtract() {
        if (this.n > 0) {
            this.n--;
        }
    }
}
