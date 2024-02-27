package com.epson.iprojection.ui.activities.moderator;

import android.view.View;
import android.view.ViewGroup;
import com.epson.iprojection.R;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnChangePlayBtn;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickPlayBtn;

/* loaded from: classes.dex */
public class PlayBtnMgr implements IOnClickPlayBtn {
    public static final int BTN_MUTE = 1;
    private static final int BTN_NUM = 3;
    public static final int BTN_PAUSE = 2;
    public static final int BTN_PLAY = 0;
    private final PlayBtnView[] _btn;
    private final IOnChangePlayBtn _impl;
    private int _selectingID = 0;

    public PlayBtnMgr(ViewGroup viewGroup, IOnChangePlayBtn iOnChangePlayBtn) {
        this._btn = r1;
        this._impl = iOnChangePlayBtn;
        PlayBtnView[] playBtnViewArr = {(PlayBtnView) viewGroup.findViewById(R.id.btn_play), (PlayBtnView) viewGroup.findViewById(R.id.btn_stop), (PlayBtnView) viewGroup.findViewById(R.id.btn_pause)};
        for (int i = 0; i < 3; i++) {
            this._btn[i].setBtnID(i);
            this._btn[i].setOnClickPlayBtnListener(this);
            this._btn[i].setVisibility(4);
        }
        this._btn[0].setSelecting(true);
    }

    public void visible() {
        for (PlayBtnView playBtnView : this._btn) {
            playBtnView.setVisibility(0);
        }
    }

    public void invisible() {
        for (PlayBtnView playBtnView : this._btn) {
            playBtnView.setVisibility(4);
        }
    }

    public void invalidate() {
        for (PlayBtnView playBtnView : this._btn) {
            playBtnView.invalidate();
        }
    }

    public void setSelecting(int i) {
        int i2 = 0;
        for (PlayBtnView playBtnView : this._btn) {
            playBtnView.setSelecting(i2 == i);
            i2++;
        }
    }

    public int getSelecting() {
        PlayBtnView[] playBtnViewArr = this._btn;
        int length = playBtnViewArr.length;
        int i = 0;
        for (int i2 = 0; i2 < length && !playBtnViewArr[i2].isSelected(); i2++) {
            i++;
        }
        return i;
    }

    @Override // com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickPlayBtn
    public void onClickPlayBtn(View view) {
        PlayBtnView[] playBtnViewArr = this._btn;
        if (view == playBtnViewArr[0]) {
            this._selectingID = 0;
        } else if (view == playBtnViewArr[1]) {
            this._selectingID = 1;
        } else if (view == playBtnViewArr[2]) {
            this._selectingID = 2;
        }
        int i = 0;
        while (i < 3) {
            this._btn[i].setSelecting(i == this._selectingID);
            i++;
        }
        IOnChangePlayBtn iOnChangePlayBtn = this._impl;
        if (iOnChangePlayBtn != null) {
            iOnChangePlayBtn.onChangePlayBtn(this._selectingID);
        }
    }
}
