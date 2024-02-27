package com.epson.iprojection.ui.activities.remote;

import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatImageView;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.DipUtils;
import com.epson.iprojection.common.utils.MathUtils;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.activities.pjselect.control.D_HistoryInfo;
import com.epson.iprojection.ui.activities.remote.commandsend.CommandSender;
import com.epson.iprojection.ui.activities.remote.commandsend.D_SendCommand;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class SwipeCtrlView extends AppCompatImageView implements GestureDetector.OnGestureListener {
    private static final double ANGLE_INIT = -100.0d;
    private static final int ESCVP_CULL_TIME = 1;
    private static final int FIRST_TIME = -1;
    private static final double PI = 3.141592653589793d;
    private static final float SCALE_DIST_MAX = 1.1f;
    private static final float SCALE_DIST_MIN = 0.9f;
    private static final int SPD_LIST_LEN = 5;
    private double MAIN_INTERVAL;
    private double SLOW_INTERVAL;
    private double SPD_SLOW;
    private CommandSender _commandSender;
    private final GestureDetector _ges;
    private float _preDist;
    private PointF _prePt;
    private long _preSendTime;
    private long _preTime;
    private final LinkedList<PointF> _ptList;
    private final LinkedList<Double> _spdList;
    private D_HistoryInfo _targetPjInfo;
    private int _touchN;

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(MotionEvent motionEvent) {
    }

    public SwipeCtrlView(Context context) {
        this(context, null);
    }

    public SwipeCtrlView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this._ptList = new LinkedList<>();
        this.MAIN_INTERVAL = 30.0d;
        this.SLOW_INTERVAL = 90.0d;
        this.SPD_SLOW = 200.0d;
        this._spdList = new LinkedList<>();
        this._prePt = new PointF();
        this._touchN = 0;
        this._ges = new GestureDetector(getContext(), this);
        double density = DipUtils.getDensity((Activity) context);
        this.MAIN_INTERVAL *= density;
        this.SLOW_INTERVAL *= density;
        this.SPD_SLOW *= density;
    }

    public void setCommandSender(CommandSender commandSender) {
        this._commandSender = commandSender;
    }

    public void setTargetPjInfo(D_HistoryInfo d_HistoryInfo) {
        this._targetPjInfo = d_HistoryInfo;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this._ges.onTouchEvent(motionEvent);
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            this._ptList.clear();
            this._spdList.clear();
            Lg.d("clear");
            this._ptList.add(new PointF(x, y));
        } else if (action == 2 && motionEvent.getPointerCount() == 1) {
            this._ptList.add(new PointF(x, y));
            if (getTotalDist() > this.MAIN_INTERVAL) {
                if (getAverageSpeed() < this.SPD_SLOW) {
                    if (getTotalDist() > this.SLOW_INTERVAL) {
                        sendCommand(Math.atan2(y - this._ptList.get(0).y, x - this._ptList.get(0).x));
                        this._ptList.clear();
                        this._spdList.clear();
                    }
                } else {
                    sendCommand(Math.atan2(y - this._ptList.get(0).y, x - this._ptList.get(0).x));
                    this._ptList.clear();
                    this._spdList.clear();
                }
            }
        }
        onTouch(motionEvent);
        return true;
    }

    private double getTotalDist() {
        float f = 0.0f;
        for (int i = 1; i < this._ptList.size(); i++) {
            PointF pointF = this._ptList.get(i - 1);
            PointF pointF2 = this._ptList.get(i);
            f += MathUtils.getDist(pointF.x, pointF.y, pointF2.x, pointF2.y);
        }
        return f;
    }

    private void sendCommand(double d) {
        if (-0.7853981633974483d <= d && d < 0.7853981633974483d) {
            sendCommand(Pj.ESCVP_COMMAND_MVPT_RIGHT);
        }
        if (0.7853981633974483d <= d && d < 2.356194490192345d) {
            sendCommand(Pj.ESCVP_COMMAND_MVPT_DOWN);
        }
        if (2.356194490192345d <= d || d < -2.356194490192345d) {
            sendCommand(Pj.ESCVP_COMMAND_MVPT_LEFT);
        }
        if (-2.356194490192345d > d || d >= -0.7853981633974483d) {
            return;
        }
        sendCommand(Pj.ESCVP_COMMAND_MVPT_UP);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x004c, code lost:
        if (r9 != 6) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouch(android.view.MotionEvent r12) {
        /*
            r11 = this;
            int r0 = r12.getAction()
            r1 = -1
            if (r0 != 0) goto La
            r11._preTime = r1
        La:
            long r3 = java.lang.System.currentTimeMillis()
            long r5 = r11._preTime
            long r3 = r3 - r5
            int r0 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r0 != 0) goto L17
            r3 = 0
        L17:
            int r0 = r12.getPointerCount()
            r1 = 0
            r2 = 0
            r5 = 1
            if (r0 < r5) goto L29
            float r0 = r12.getX(r1)
            float r6 = r12.getY(r1)
            goto L2b
        L29:
            r0 = r2
            r6 = r0
        L2b:
            int r7 = r12.getPointerCount()
            r8 = 2
            if (r7 < r8) goto L3b
            float r2 = r12.getX(r5)
            float r7 = r12.getY(r5)
            goto L3c
        L3b:
            r7 = r2
        L3c:
            int r9 = r12.getAction()
            r9 = r9 & 255(0xff, float:3.57E-43)
            if (r9 == 0) goto La5
            if (r9 == r5) goto La2
            if (r9 == r8) goto L67
            r3 = 5
            if (r9 == r3) goto L4f
            r12 = 6
            if (r9 == r12) goto La2
            goto Lac
        L4f:
            android.graphics.PointF r1 = r11._prePt
            r1.set(r0, r6)
            float r1 = com.epson.iprojection.common.utils.MathUtils.getDist(r0, r6, r2, r7)
            r11._preDist = r1
            android.graphics.PointF r0 = com.epson.iprojection.common.utils.MathUtils.getMidPoint(r0, r6, r2, r7)
            r11._prePt = r0
            int r12 = r12.getPointerCount()
            r11._touchN = r12
            goto Lac
        L67:
            int r12 = r11._touchN
            if (r12 == r5) goto L73
            if (r12 == r8) goto L6e
            return r1
        L6e:
            android.graphics.PointF r12 = com.epson.iprojection.common.utils.MathUtils.getMidPoint(r0, r6, r2, r7)
            goto L78
        L73:
            android.graphics.PointF r12 = new android.graphics.PointF
            r12.<init>(r0, r6)
        L78:
            float r1 = r12.x
            android.graphics.PointF r9 = r11._prePt
            float r9 = r9.x
            float r1 = r1 - r9
            float r9 = r12.y
            android.graphics.PointF r10 = r11._prePt
            float r10 = r10.y
            float r9 = r9 - r10
            float r1 = r1 * r1
            float r9 = r9 * r9
            float r1 = r1 + r9
            double r9 = (double) r1
            double r9 = java.lang.Math.sqrt(r9)
            r11.registerSpeed(r9, r3)
            int r1 = r11._touchN
            if (r1 != r8) goto L98
            r11.Zoom(r0, r6, r2, r7)
        L98:
            android.graphics.PointF r0 = r11._prePt
            float r1 = r12.x
            float r12 = r12.y
            r0.set(r1, r12)
            goto Lac
        La2:
            r11._touchN = r1
            goto Lac
        La5:
            android.graphics.PointF r12 = r11._prePt
            r12.set(r0, r6)
            r11._touchN = r5
        Lac:
            long r0 = java.lang.System.currentTimeMillis()
            r11._preTime = r0
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.remote.SwipeCtrlView.onTouch(android.view.MotionEvent):boolean");
    }

    private void registerSpeed(double d, long j) {
        if (j == 0) {
            return;
        }
        this._spdList.add(Double.valueOf(d / (j / 1000.0d)));
        if (this._spdList.size() > 5) {
            this._spdList.remove(0);
        }
    }

    private double getAverageSpeed() {
        int size = this._spdList.size();
        double d = 0.0d;
        if (size == 0) {
            return 0.0d;
        }
        Iterator<Double> it = this._spdList.iterator();
        while (it.hasNext()) {
            d += it.next().doubleValue();
        }
        return d / size;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        sendCommand(Pj.ESCVP_COMMAND_ENTER);
        return false;
    }

    private void sendCommand(final String str) {
        if (1 < System.currentTimeMillis() - this._preSendTime) {
            new Thread(new Runnable() { // from class: com.epson.iprojection.ui.activities.remote.SwipeCtrlView$$ExternalSyntheticLambda0
                {
                    SwipeCtrlView.this = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    SwipeCtrlView.this.m178xc187bf23(str);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$sendCommand$0$com-epson-iprojection-ui-activities-remote-SwipeCtrlView  reason: not valid java name */
    public /* synthetic */ void m178xc187bf23(String str) {
        Lg.d("sendEscvpCommand [" + str + "]");
        if (this._targetPjInfo != null) {
            sendCommandWithSender(str);
        }
        this._preSendTime = System.currentTimeMillis();
    }

    private void sendCommandWithSender(String str) {
        ArrayList<D_SendCommand> arrayList = new ArrayList<>();
        String read = PrefUtils.read(getContext(), RemotePrefUtils.PREF_TAG_REMOTE_PASS + NetUtils.toHexString(this._targetPjInfo.macAddr));
        Lg.d("保存されていたパスワードは->" + read);
        if (read == null) {
            read = "";
        }
        arrayList.add(new D_SendCommand(read, this._targetPjInfo));
        CommandSender commandSender = this._commandSender;
        if (commandSender != null) {
            commandSender.send(str, (Activity) getContext(), Pj.getIns(), arrayList, false, null);
        }
    }

    void Zoom(float f, float f2, float f3, float f4) {
        if (this._preDist == 0.0f) {
            return;
        }
        float dist = MathUtils.getDist(f, f2, f3, f4);
        float f5 = dist / this._preDist;
        if (f5 > 1.1f) {
            sendCommand(Pj.ESCVP_COMMAND_ZOOM_UP);
            this._preDist = dist;
            Lg.d("ZoomUp");
        }
        if (f5 < SCALE_DIST_MIN) {
            sendCommand(Pj.ESCVP_COMMAND_ZOOM_DOWN);
            this._preDist = dist;
            Lg.d("ZoomDown");
        }
    }
}
