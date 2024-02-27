package com.epson.iprojection.ui.activities.moderator;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Vibrator;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.epson.iprojection.R;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.engine.common.D_MppLayoutInfo;
import com.epson.iprojection.engine.common.D_MppUserInfo;
import com.epson.iprojection.ui.activities.moderator.ListNameAdapter;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnChangeableWebRTCConnectStatusListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickWindowListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnDragStateListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnDropListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnWindowsChangedListener;
import com.epson.iprojection.ui.activities.moderator.thumbnail.InflaterThumbnailAdapter;
import com.epson.iprojection.ui.common.analytics.Analytics;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnConnectListener;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class WindowMgr implements IOnClickWindowListener {
    private static final int DIV_1 = 0;
    private static final int DIV_2 = 1;
    private static final int DIV_4 = 2;
    private static final int DIV_N = 3;
    public static final int ERR = -1;
    public static final int LEFT_BTM = 2;
    public static final int LEFT_TOP = 0;
    public static final int NO_SELECT = -1;
    public static final int RIGHT_BTM = 3;
    public static final int RIGHT_TOP = 1;
    public static final int WINDOW_NUM = 4;
    private final Activity _activity;
    private final IOnDragStateListener _implDragState;
    private final IOnDropListener _implDrop;
    private IOnWindowsChangedListener _implWindow;
    private int _selectingWindow;
    private final Vibrator _vib;
    private final Window[] _windows = new Window[4];
    private final ImageButton[] _btnWindowDivNum = new ImageButton[3];

    /* loaded from: classes.dex */
    public enum eStatus {
        eNoSelect,
        eActive,
        eInactive
    }

    public WindowMgr(Activity activity, IOnDropListener iOnDropListener, D_MppUserInfo d_MppUserInfo, IOnDragStateListener iOnDragStateListener, boolean z, int i, IOnChangeableWebRTCConnectStatusListener iOnChangeableWebRTCConnectStatusListener) {
        this._activity = activity;
        this._implDrop = iOnDropListener;
        this._implDragState = iOnDragStateListener;
        this._selectingWindow = i;
        int[] iArr = {R.id.mpp_window_LEFT_TOP, R.id.mpp_window_RIGHT_TOP, R.id.mpp_window_LEFT_BTM, R.id.mpp_window_RIGHT_BTM};
        for (int i2 = 0; i2 < 4; i2++) {
            NumberableFrameLayout numberableFrameLayout = (NumberableFrameLayout) activity.findViewById(iArr[i2]);
            TouchPosGettableImageView touchPosGettableImageView = (TouchPosGettableImageView) numberableFrameLayout.findViewById(R.id.img_frame);
            touchPosGettableImageView.setWindID(i2);
            numberableFrameLayout.setWindID(i2);
            mySetOnDragListener(numberableFrameLayout);
            mySetOnLongClickListener(touchPosGettableImageView, numberableFrameLayout);
            TextView textView = (TextView) numberableFrameLayout.findViewById(R.id.txt_name);
            setTextViewBackground(textView, z);
            this._windows[i2] = new Window(i2, (ViewGroup) activity.findViewById(iArr[i2]), (ImageView) numberableFrameLayout.findViewById(R.id.img_back), (ImageView) numberableFrameLayout.findViewById(R.id.img_frame), textView, this, iOnChangeableWebRTCConnectStatusListener);
        }
        this._windows[this._selectingWindow].activate();
        this._windows[this._selectingWindow].setFrame();
        if (!Pj.getIns().isModerator()) {
            this._windows[0].invisibleBtns();
        }
        this._btnWindowDivNum[0] = (ImageButton) activity.findViewById(R.id.btn_multictrl_mpp1);
        this._btnWindowDivNum[1] = (ImageButton) activity.findViewById(R.id.btn_multictrl_mpp2);
        this._btnWindowDivNum[2] = (ImageButton) activity.findViewById(R.id.btn_multictrl_mpp4);
        int mppWindowDivNum = Pj.getIns().getMppWindowDivNum();
        if (mppWindowDivNum == 1) {
            this._btnWindowDivNum[0].setImageResource(R.drawable.mpp_1_able);
        } else if (mppWindowDivNum == 2) {
            this._btnWindowDivNum[1].setImageResource(R.drawable.mpp_2_able);
        } else if (mppWindowDivNum == 4) {
            this._btnWindowDivNum[2].setImageResource(R.drawable.mpp_4_able);
        } else {
            Lg.e("WindowDiv Error");
        }
        this._vib = (Vibrator) activity.getSystemService("vibrator");
    }

    private void setTextViewBackground(TextView textView, boolean z) {
        if (z) {
            textView.setBackgroundColor(MethodUtil.compatGetColor(this._activity.getApplicationContext(), R.color.ModeratorTextBackground));
            textView.setTextColor(-1);
            return;
        }
        textView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        textView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
    }

    public void setOnWindowChangedListener(IOnWindowsChangedListener iOnWindowsChangedListener) {
        this._implWindow = iOnWindowsChangedListener;
    }

    public int convertWindowID(int i) {
        int[] iArr = {R.id.mpp_window_LEFT_TOP, R.id.mpp_window_RIGHT_TOP, R.id.mpp_window_LEFT_BTM, R.id.mpp_window_RIGHT_BTM};
        for (int i2 = 0; i2 < 4; i2++) {
            if (i == iArr[i2]) {
                return i2;
            }
        }
        return -1;
    }

    private void mySetOnDragListener(final View view) {
        view.setOnDragListener(new View.OnDragListener() { // from class: com.epson.iprojection.ui.activities.moderator.WindowMgr$$ExternalSyntheticLambda0
            @Override // android.view.View.OnDragListener
            public final boolean onDrag(View view2, DragEvent dragEvent) {
                return WindowMgr.this.m104x456f91ba(view, view2, dragEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$mySetOnDragListener$0$com-epson-iprojection-ui-activities-moderator-WindowMgr  reason: not valid java name */
    public /* synthetic */ boolean m104x456f91ba(View view, View view2, DragEvent dragEvent) {
        int action = dragEvent.getAction();
        if (action == 1) {
            this._implDragState.onStartDrag();
            return true;
        } else if (action != 2) {
            if (action != 3) {
                if (action == 4) {
                    this._implDragState.onEndDrag();
                }
                return false;
            }
            boolean isEnabledDrop = this._implDragState.isEnabledDrop();
            this._implDragState.onEndDrag();
            if (isEnabledDrop) {
                try {
                    try {
                        try {
                            try {
                                this._implDrop.onDropListItem(((ListNameAdapter.ViewHolder) ((View) dragEvent.getLocalState()).getTag())._position, ((NumberableFrameLayout) view).getWindID());
                                return true;
                            } catch (Exception unused) {
                                exchange(((TouchPosGettableImageView) dragEvent.getLocalState()).getWindID(), ((NumberableFrameLayout) view).getWindID());
                                return true;
                            }
                        } catch (Exception unused2) {
                            DraggableButton draggableButton = (DraggableButton) dragEvent.getLocalState();
                            this._implDrop.onDropMe(((NumberableFrameLayout) view).getWindID());
                            return true;
                        }
                    } catch (Exception unused3) {
                        return true;
                    }
                } catch (Exception unused4) {
                    this._implDrop.onDropListItem(((InflaterThumbnailAdapter.ViewHolder) ((View) dragEvent.getLocalState()).getTag())._position, ((NumberableFrameLayout) view).getWindID());
                    return true;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    private void mySetOnLongClickListener(final TouchPosGettableImageView touchPosGettableImageView, final ViewGroup viewGroup) {
        touchPosGettableImageView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.epson.iprojection.ui.activities.moderator.WindowMgr.1
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                try {
                    if (WindowMgr.this._vib != null) {
                        MethodUtil.compatVibrate(WindowMgr.this._vib, 100L);
                    }
                    MethodUtil.compatStartDragAndDrop(null, new View.DragShadowBuilder(viewGroup) { // from class: com.epson.iprojection.ui.activities.moderator.WindowMgr.1.1
                        @Override // android.view.View.DragShadowBuilder
                        public void onProvideShadowMetrics(Point point, Point point2) {
                            super.onProvideShadowMetrics(point, point2);
                            point2.x = touchPosGettableImageView.getTouchX();
                            point2.y = touchPosGettableImageView.getTouchY();
                        }

                        @Override // android.view.View.DragShadowBuilder
                        public void onDrawShadow(Canvas canvas) {
                            super.onDrawShadow(canvas);
                        }
                    }, view, 0);
                    return true;
                } catch (Exception unused) {
                    return true;
                }
            }
        });
    }

    public void changeWindow(int i) {
        int i2 = 0;
        this._btnWindowDivNum[0].setImageResource(R.drawable.mpp_1_disable);
        this._btnWindowDivNum[1].setImageResource(R.drawable.mpp_2_disable);
        this._btnWindowDivNum[2].setImageResource(R.drawable.mpp_4_disable);
        if (i == 1) {
            for (int i3 = 0; i3 < 4; i3++) {
                if (this._selectingWindow == i3) {
                    this._windows[i3].activate();
                } else {
                    this._windows[i3].inactivate();
                    this._windows[i3].invisibleBtns();
                }
            }
            this._btnWindowDivNum[0].setImageResource(R.drawable.mpp_1_able);
        } else if (i == 2) {
            while (i2 < 4) {
                if (this._selectingWindow / 2 == i2 / 2) {
                    this._windows[i2].activate();
                } else {
                    this._windows[i2].inactivate();
                    this._windows[i2].invisibleBtns();
                }
                i2++;
            }
            this._btnWindowDivNum[1].setImageResource(R.drawable.mpp_2_able);
        } else if (i == 4) {
            while (i2 < 4) {
                this._windows[i2].activate();
                i2++;
            }
            this._btnWindowDivNum[2].setImageResource(R.drawable.mpp_4_able);
        }
        updateVisibleBtns();
    }

    public void changeWindow(int i, int i2) {
        int i3 = 0;
        this._btnWindowDivNum[0].setImageResource(R.drawable.mpp_1_disable);
        this._btnWindowDivNum[1].setImageResource(R.drawable.mpp_2_disable);
        this._btnWindowDivNum[2].setImageResource(R.drawable.mpp_4_disable);
        if (i == 1) {
            for (int i4 = 0; i4 < 4; i4++) {
                if (i2 == i4) {
                    this._windows[i4].activate();
                } else {
                    this._windows[i4].inactivate();
                }
            }
            this._btnWindowDivNum[0].setImageResource(R.drawable.mpp_1_able);
        } else if (i == 2) {
            while (i3 < 4) {
                if (i2 / 2 == i3 / 2) {
                    this._windows[i3].activate();
                } else {
                    this._windows[i3].inactivate();
                }
                i3++;
            }
            this._btnWindowDivNum[1].setImageResource(R.drawable.mpp_2_able);
        } else if (i == 4) {
            while (i3 < 4) {
                this._windows[i3].activate();
                i3++;
            }
            this._btnWindowDivNum[2].setImageResource(R.drawable.mpp_4_able);
        }
        updateVisibleBtns();
    }

    private void updateVisibleBtns() {
        for (int i = 0; i < 4; i++) {
            if (this._windows[i].existsContents()) {
                if ((this._windows[i].isMe() && Pj.getIns().getMppControlMode() != IOnConnectListener.MppControlMode.ModeratorEntry) || Pj.getIns().isModerator()) {
                    if (this._windows[i].isActive()) {
                        this._windows[i].visibleBtns();
                    } else {
                        this._windows[i].invisibleBtns();
                    }
                } else {
                    this._windows[i].invisibleBtns();
                }
            } else {
                this._windows[i].invisibleBtns();
            }
        }
    }

    public void remove(long j) {
        for (int i = 0; i < 4; i++) {
            Window window = this._windows[i];
            if (window.getUniqueID() == j) {
                window.setText(null);
                window.setBitmap(null);
                window.clearUniqueID();
                window.setIsMe(false);
                this._windows[i].invisibleBtns();
            }
        }
    }

    public void remove(int i) {
        ArrayList<D_MppLayoutInfo> mppLayout = Pj.getIns().getMppLayout();
        int i2 = 0;
        while (true) {
            if (i2 >= mppLayout.size()) {
                break;
            }
            D_MppLayoutInfo d_MppLayoutInfo = mppLayout.get(i2);
            if (d_MppLayoutInfo.uniqueId == this._windows[i].getUniqueID()) {
                Pj.getIns().removeMppUserFromLayout(d_MppLayoutInfo);
                break;
            }
            i2++;
        }
        this._windows[i].setIsMe(false);
        this._windows[i].setBitmap(null);
        this._windows[i].setText(null);
        this._windows[i].clearUniqueID();
        this._windows[i].invisibleBtns();
    }

    public void removeAll() {
        for (int i = 0; i < 4; i++) {
            Window window = this._windows[i];
            window.setText(null);
            window.clearUniqueID();
            window.inactivate();
            this._windows[i].invisibleBtns();
        }
    }

    public void setData(int i, long j, String str, int i2, boolean z, Bitmap bitmap) {
        Window window;
        remove(j);
        if (i == 0) {
            window = this._windows[0];
        } else if (i == 1) {
            window = this._windows[1];
        } else if (i == 2) {
            window = this._windows[2];
        } else {
            window = i != 3 ? null : this._windows[3];
        }
        window.setText(str);
        window.setUniqueID(j);
        if (z) {
            for (Window window2 : this._windows) {
                window2.setIsMe(false);
            }
        }
        window.setIsMe(z);
        window.setSelecting(i2);
        window.setBitmap(bitmap);
    }

    public int getSelectingWindowID() {
        return this._selectingWindow;
    }

    public long getUniqueID(int i) {
        return this._windows[i].getUniqueID();
    }

    public boolean isActive(int i) {
        return this._windows[i].isActive();
    }

    public boolean existsContents(int i) {
        return this._windows[i].existsContents();
    }

    public void visiblePlayBtn(int i) {
        this._windows[i].visibleBtns();
    }

    public void invisiblePlayBtn(int i) {
        this._windows[i].invisibleBtns();
    }

    public void activateWindow(int i) {
        this._windows[i].activate();
    }

    public void deactivateWindow(int i) {
        this._windows[i].inactivate();
    }

    @Override // com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickWindowListener
    public void onClickWindow(int i) {
        if (this._selectingWindow == i) {
            this._windows[i].setFrame();
            this._selectingWindow = i;
            return;
        }
        for (Window window : this._windows) {
            window.clearFrame();
        }
        this._windows[i].setFrame();
        this._selectingWindow = i;
    }

    public boolean exchange(int i, int i2) {
        boolean z = false;
        if (i == i2) {
            return false;
        }
        if (this._windows[i].existsContents() || this._windows[i2].existsContents()) {
            Window window = this._windows[i];
            long uniqueID = window.getUniqueID();
            String text = window.getText();
            boolean isMe = window.isMe();
            int selecting = window.getSelecting();
            Bitmap bitmap = window.getBitmap();
            Window window2 = this._windows[i2];
            long uniqueID2 = window2.getUniqueID();
            String text2 = window2.getText();
            boolean isMe2 = window2.isMe();
            int selecting2 = window2.getSelecting();
            Bitmap bitmap2 = window2.getBitmap();
            setData(i2, uniqueID, text, selecting, isMe, bitmap);
            setData(i, uniqueID2, text2, selecting2, isMe2, bitmap2);
            IOnWindowsChangedListener iOnWindowsChangedListener = this._implWindow;
            if (iOnWindowsChangedListener != null) {
                iOnWindowsChangedListener.onWindowChanged();
            }
            z = true;
        }
        Pj.getIns().exchangeMppLayout(i, i2);
        if (this._windows[i].isSetFrame()) {
            this._windows[i].clearFrame();
            this._windows[i2].setFrame();
            this._selectingWindow = i2;
        } else if (this._windows[i2].isSetFrame()) {
            this._windows[i2].clearFrame();
            this._windows[i].setFrame();
            this._selectingWindow = i;
        }
        updateVisibleBtns();
        Analytics.getIns().setMultiProjectionEventType(eCustomEvent.USER_OPERATION);
        Analytics.getIns().sendEvent(eCustomEvent.USER_OPERATION);
        return z;
    }

    public void setBitmap(int i, Bitmap bitmap) {
        this._windows[i].setBitmap(bitmap);
    }

    public Window getWindow(int i) {
        return this._windows[i];
    }

    public int getEmptyWindowID() {
        for (int i = 0; i < 4; i++) {
            if (!this._windows[i].existsContents()) {
                return i;
            }
        }
        return -1;
    }

    public eStatus getMeStatus() {
        Window[] windowArr;
        for (Window window : this._windows) {
            if (window.isMe()) {
                if (window.isActive()) {
                    return eStatus.eActive;
                }
                return eStatus.eInactive;
            }
        }
        return eStatus.eNoSelect;
    }
}
