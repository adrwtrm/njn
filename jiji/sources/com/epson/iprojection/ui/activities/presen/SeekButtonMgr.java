package com.epson.iprojection.ui.activities.presen;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.presen.interfaces.IClickButtonListener;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class SeekButtonMgr implements View.OnClickListener {
    private static final int BTN_END = 3;
    private static final int BTN_NEXT = 2;
    private static final int BTN_NUM = 4;
    private static final int BTN_PREV = 1;
    private static final int BTN_START = 0;
    private static final int GRAY_OUT_ALPHA = 96;
    private static final int NORMAL_ALPHA = 255;
    private final ArrayList<ImageButton> _btnList;
    private final IClickButtonListener _listenerImpl;
    private final Selector _selector;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SeekButtonMgr(Activity activity, IClickButtonListener iClickButtonListener, Selector selector) {
        ArrayList<ImageButton> arrayList = new ArrayList<>();
        this._btnList = arrayList;
        arrayList.add((ImageButton) activity.findViewById(R.id.btn_presen_seekStart));
        arrayList.add((ImageButton) activity.findViewById(R.id.btn_presen_seekPrev));
        arrayList.add((ImageButton) activity.findViewById(R.id.btn_presen_seekNext));
        arrayList.add((ImageButton) activity.findViewById(R.id.btn_presen_seekEnd));
        this._listenerImpl = iClickButtonListener;
        for (int i = 0; i < 4; i++) {
            this._btnList.get(i).setOnClickListener(this);
        }
        this._selector = selector;
        if (selector.getTotalPages() == 1) {
            this._btnList.get(0).setVisibility(4);
            this._btnList.get(1).setVisibility(4);
            this._btnList.get(2).setVisibility(4);
            this._btnList.get(3).setVisibility(4);
            return;
        }
        update();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        for (int i = 0; i < 4; i++) {
            if (view == this._btnList.get(i)) {
                if (i == 0) {
                    this._listenerImpl.onClickSeekButton(eSeek.eSeek_Start);
                } else if (i == 1) {
                    this._listenerImpl.onClickSeekButton(eSeek.eSeek_Prev);
                } else if (i == 2) {
                    this._listenerImpl.onClickSeekButton(eSeek.eSeek_Next);
                } else if (i == 3) {
                    this._listenerImpl.onClickSeekButton(eSeek.eSeek_End);
                }
            }
        }
        update();
    }

    public void update() {
        if (this._selector.getTotalPages() == 1) {
            return;
        }
        if (this._selector.getNowSelectNum() == 0) {
            disableButton(0);
            disableButton(1);
        } else {
            enableButton(0);
            enableButton(1);
        }
        if (this._selector.getNowSelectNum() == this._selector.getTotalPages() - 1) {
            disableButton(2);
            disableButton(3);
            return;
        }
        enableButton(2);
        enableButton(3);
    }

    public void beVisible() {
        this._btnList.get(0).setVisibility(0);
        this._btnList.get(1).setVisibility(0);
        this._btnList.get(2).setVisibility(0);
        this._btnList.get(3).setVisibility(0);
        update();
    }

    private void enableButton(int i) {
        if (this._btnList.get(i).isEnabled()) {
            return;
        }
        this._btnList.get(i).setEnabled(true);
        this._btnList.get(i).setImageAlpha(255);
    }

    private void disableButton(int i) {
        if (this._btnList.get(i).isEnabled()) {
            this._btnList.get(i).setEnabled(false);
            this._btnList.get(i).setImageAlpha(96);
        }
    }
}
