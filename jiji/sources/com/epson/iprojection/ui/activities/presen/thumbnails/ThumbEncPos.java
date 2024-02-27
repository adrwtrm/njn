package com.epson.iprojection.ui.activities.presen.thumbnails;

import android.widget.LinearLayout;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.google.android.gms.common.util.CollectionUtils;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ThumbEncPos {
    public static final int ERROR = -1;
    private final ArrayList<LinearLayout> _list;
    private int _basePos = 0;
    private int _curPos = 0;
    private int _plusPos = 0;
    private int _minusPos = 0;

    public ThumbEncPos(ArrayList<LinearLayout> arrayList, int i) {
        this._list = arrayList;
        setCurrentPos(i);
    }

    public int setCurrentPos(int i) {
        this._curPos = i;
        this._basePos = i;
        this._plusPos = 0;
        this._minusPos = 0;
        if (CollectionUtils.isEmpty(this._list) || i >= this._list.size() || i < 0) {
            Lg.e("セットした位置が不正です。 pos:" + i);
            return -1;
        } else if (((ThumbImageView) this._list.get(i).findViewById(R.id.img_thumb)).isFinished()) {
            return next();
        } else {
            return 0;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0081, code lost:
        r8._curPos = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0084, code lost:
        return -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int next() {
        /*
            r8 = this;
            r0 = 0
            r1 = r0
        L2:
            if (r0 == 0) goto L6
            if (r1 != 0) goto L81
        L6:
            java.util.ArrayList<android.widget.LinearLayout> r2 = r8._list
            boolean r2 = com.google.android.gms.common.util.CollectionUtils.isEmpty(r2)
            if (r2 != 0) goto L81
            int r2 = r8._basePos
            int r3 = r8._minusPos
            int r4 = r2 - r3
            r5 = 1
            if (r4 >= 0) goto L1a
            if (r0 != 0) goto L1a
            r0 = r5
        L1a:
            r6 = 2131231167(0x7f0801bf, float:1.8078407E38)
            if (r0 != 0) goto L47
            int r7 = r8._plusPos
            if (r3 <= r7) goto L25
            if (r1 == 0) goto L47
        L25:
            java.util.ArrayList<android.widget.LinearLayout> r2 = r8._list
            java.lang.Object r2 = r2.get(r4)
            android.widget.LinearLayout r2 = (android.widget.LinearLayout) r2
            android.view.View r2 = r2.findViewById(r6)
            com.epson.iprojection.ui.activities.presen.thumbnails.ThumbImageView r2 = (com.epson.iprojection.ui.activities.presen.thumbnails.ThumbImageView) r2
            boolean r2 = r2.isFinished()
            if (r2 == 0) goto L3f
            int r2 = r8._minusPos
            int r2 = r2 + r5
            r8._minusPos = r2
            goto L2
        L3f:
            r8._curPos = r4
            int r0 = r8._minusPos
            int r1 = r8._plusPos
            int r0 = r0 + r1
            return r0
        L47:
            int r3 = r8._plusPos
            int r2 = r2 + r3
            java.util.ArrayList<android.widget.LinearLayout> r3 = r8._list
            int r3 = r3.size()
            if (r2 < r3) goto L55
            if (r1 != 0) goto L55
            r1 = r5
        L55:
            if (r1 != 0) goto L2
            int r3 = r8._minusPos
            int r4 = r8._plusPos
            if (r3 > r4) goto L5f
            if (r0 == 0) goto L2
        L5f:
            java.util.ArrayList<android.widget.LinearLayout> r3 = r8._list
            java.lang.Object r3 = r3.get(r2)
            android.widget.LinearLayout r3 = (android.widget.LinearLayout) r3
            android.view.View r3 = r3.findViewById(r6)
            com.epson.iprojection.ui.activities.presen.thumbnails.ThumbImageView r3 = (com.epson.iprojection.ui.activities.presen.thumbnails.ThumbImageView) r3
            boolean r3 = r3.isFinished()
            if (r3 == 0) goto L79
            int r2 = r8._plusPos
            int r2 = r2 + r5
            r8._plusPos = r2
            goto L2
        L79:
            r8._curPos = r2
            int r0 = r8._minusPos
            int r1 = r8._plusPos
            int r0 = r0 + r1
            return r0
        L81:
            r0 = -1
            r8._curPos = r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.presen.thumbnails.ThumbEncPos.next():int");
    }

    public int getPos() {
        return this._curPos;
    }
}
