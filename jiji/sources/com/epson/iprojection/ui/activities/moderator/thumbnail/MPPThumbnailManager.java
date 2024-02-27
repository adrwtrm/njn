package com.epson.iprojection.ui.activities.moderator.thumbnail;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import com.epson.iprojection.R;
import com.epson.iprojection.common.utils.MethodUtil;
import com.epson.iprojection.engine.common.D_MppUserInfo;
import com.epson.iprojection.engine.common.D_ThumbnailInfo;
import com.epson.iprojection.ui.activities.moderator.MultiProjectionDisplaySettings;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnClickUserListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnDragStateListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnDropListener;
import com.epson.iprojection.ui.activities.moderator.interfaces.IOnSizeChangedW;
import com.epson.iprojection.ui.activities.moderator.thumbnail.InflaterThumbnailAdapter;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class MPPThumbnailManager implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, IOnSizeChangedW {
    private static final int LAND_LARGE_N = 1;
    private static final int LAND_SMALL_N = 2;
    private static final int PORT_LARGE_N = 2;
    private static final int PORT_SMALL_N = 5;
    private final Context _context;
    private final MeasurableGridView _gridView;
    private final IOnClickUserListener _impl;
    private final IOnDragStateListener _implDragState;
    private final IOnDropListener _implDrop;
    private boolean _isIgnoredOnSizeChangedW;
    private final boolean _isPortrait;
    private int _thumbH;
    private int _thumbW;
    private final Vibrator _vib;
    private InflaterThumbnailAdapter _adapter = null;
    private ArrayList<D_ThumbnailInfo> _arrayThumbnail = null;
    private final Handler _handler = new Handler();
    private final Runnable setThumbnailSize = new Runnable() { // from class: com.epson.iprojection.ui.activities.moderator.thumbnail.MPPThumbnailManager$$ExternalSyntheticLambda1
        {
            MPPThumbnailManager.this = this;
        }

        @Override // java.lang.Runnable
        public final void run() {
            MPPThumbnailManager.this.m106x9b1f30e2();
        }
    };
    private final Runnable delayGetThumbnail = new Runnable() { // from class: com.epson.iprojection.ui.activities.moderator.thumbnail.MPPThumbnailManager$$ExternalSyntheticLambda2
        {
            MPPThumbnailManager.this = this;
        }

        @Override // java.lang.Runnable
        public final void run() {
            MPPThumbnailManager.this.requestThumbnail();
        }
    };

    public MPPThumbnailManager(Activity activity, IOnClickUserListener iOnClickUserListener, IOnDropListener iOnDropListener, boolean z, boolean z2, IOnDragStateListener iOnDragStateListener) {
        this._impl = iOnClickUserListener;
        this._isPortrait = z;
        this._implDrop = iOnDropListener;
        this._isIgnoredOnSizeChangedW = z2;
        MeasurableGridView measurableGridView = (MeasurableGridView) activity.findViewById(R.id.grid_thumbnail);
        this._gridView = measurableGridView;
        measurableGridView.mySetOnDragListener(iOnDropListener);
        this._context = activity;
        measurableGridView.setOnItemClickListener(this);
        measurableGridView.setListener(this);
        measurableGridView.setDragStateListener(iOnDragStateListener);
        this._vib = (Vibrator) activity.getSystemService("vibrator");
        this._implDragState = iOnDragStateListener;
    }

    public void resume() {
        if (this._arrayThumbnail == null || this._adapter == null) {
            rebuild(null);
        }
        this._adapter.notifyDataSetChanged();
    }

    public void resumeWithRotate() {
        if (this._arrayThumbnail == null || this._adapter == null) {
            rebuild(null);
        }
        this._adapter.notifyDataSetChanged();
        this._handler.postDelayed(this.setThumbnailSize, 1000L);
    }

    public void end() {
        Pj.getIns().stopThumbnail();
        this._arrayThumbnail = null;
        this._adapter = null;
    }

    private void rebuild(ArrayList<D_MppUserInfo> arrayList) {
        this._arrayThumbnail = new ArrayList<>();
        if (arrayList == null) {
            arrayList = Pj.getIns().getMppUserList();
        }
        Iterator<D_MppUserInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            D_MppUserInfo next = it.next();
            D_ThumbnailInfo d_ThumbnailInfo = new D_ThumbnailInfo();
            d_ThumbnailInfo.userUniqueId = next.uniqueId;
            d_ThumbnailInfo.disconnected = next.disconnected;
            this._arrayThumbnail.add(d_ThumbnailInfo);
        }
        InflaterThumbnailAdapter inflaterThumbnailAdapter = new InflaterThumbnailAdapter(this._context, this._arrayThumbnail, this._implDrop, this._implDragState);
        this._adapter = inflaterThumbnailAdapter;
        inflaterThumbnailAdapter.setImageWH(this._thumbW, this._thumbH);
        this._adapter.updateLayout(Pj.getIns().getMppLayout());
        this._gridView.setAdapter((ListAdapter) this._adapter);
        this._handler.postDelayed(this.delayGetThumbnail, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-epson-iprojection-ui-activities-moderator-thumbnail-MPPThumbnailManager  reason: not valid java name */
    public /* synthetic */ void m106x9b1f30e2() {
        setThumbnailSize(MultiProjectionDisplaySettings.INSTANCE.isSmall());
    }

    public void setThumbnailSize(boolean z) {
        int i;
        MultiProjectionDisplaySettings.INSTANCE.setSmall(z);
        int dimension = (int) this._context.getResources().getDimension(R.dimen.TumbSpacing);
        if (z) {
            i = this._isPortrait ? 5 : 2;
            int width = (this._gridView.getWidth() - (dimension * i)) / i;
            this._gridView.setColumnWidth(width);
            this._thumbW = width;
            this._thumbH = (width * 3) / 4;
        } else {
            i = this._isPortrait ? 2 : 1;
            int width2 = (this._gridView.getWidth() - (dimension * i)) / i;
            this._gridView.setColumnWidth(width2);
            this._thumbW = width2;
            this._thumbH = (width2 * 3) / 4;
        }
        rebuild(null);
    }

    public void setModerator(boolean z) {
        if (z) {
            this._gridView.setOnItemLongClickListener(this);
            requestThumbnail();
            return;
        }
        this._gridView.setOnItemLongClickListener(null);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (this._impl != null) {
            this._impl.onClickUser(Pj.getIns().getMppUserInfoByUniqueID(j));
        }
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
        InflaterThumbnailAdapter.ViewHolder viewHolder = (InflaterThumbnailAdapter.ViewHolder) view.getTag();
        if (viewHolder.isNoImage) {
            return false;
        }
        final int i2 = (int) (viewHolder.lastTouchedX + 0.5f);
        final int i3 = (int) (viewHolder.lastTouchedY + 0.5f);
        Vibrator vibrator = this._vib;
        if (vibrator != null) {
            MethodUtil.compatVibrate(vibrator, 100L);
        }
        MethodUtil.compatStartDragAndDrop(null, new View.DragShadowBuilder(view) { // from class: com.epson.iprojection.ui.activities.moderator.thumbnail.MPPThumbnailManager.1
            @Override // android.view.View.DragShadowBuilder
            public void onProvideShadowMetrics(Point point, Point point2) {
                super.onProvideShadowMetrics(point, point2);
                point2.x = i2;
                point2.y = i3;
            }

            @Override // android.view.View.DragShadowBuilder
            public void onDrawShadow(Canvas canvas) {
                super.onDrawShadow(canvas);
            }
        }, view, 0);
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0050, code lost:
        if (r2.disconnected == false) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onUpdateMPPUserList(java.util.ArrayList<com.epson.iprojection.engine.common.D_MppUserInfo> r11, java.util.ArrayList<com.epson.iprojection.engine.common.D_MppLayoutInfo> r12) {
        /*
            r10 = this;
            com.epson.iprojection.ui.activities.moderator.thumbnail.InflaterThumbnailAdapter r0 = r10._adapter
            r0.updateLayout(r12)
            if (r11 == 0) goto L76
            java.util.ArrayList<com.epson.iprojection.engine.common.D_ThumbnailInfo> r12 = r10._arrayThumbnail
            if (r12 == 0) goto L76
            java.util.Iterator r12 = r11.iterator()
            r0 = 0
            r1 = r0
        L11:
            boolean r2 = r12.hasNext()
            if (r2 == 0) goto L5e
            java.lang.Object r2 = r12.next()
            com.epson.iprojection.engine.common.D_MppUserInfo r2 = (com.epson.iprojection.engine.common.D_MppUserInfo) r2
            java.util.ArrayList<com.epson.iprojection.engine.common.D_ThumbnailInfo> r3 = r10._arrayThumbnail
            java.util.Iterator r3 = r3.iterator()
            r4 = r0
        L24:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L11
            java.lang.Object r5 = r3.next()
            com.epson.iprojection.engine.common.D_ThumbnailInfo r5 = (com.epson.iprojection.engine.common.D_ThumbnailInfo) r5
            long r6 = r5.userUniqueId
            long r8 = r2.uniqueId
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 != 0) goto L5b
            boolean r3 = r5.disconnected
            r6 = 1
            if (r3 != 0) goto L4a
            boolean r3 = r2.disconnected
            if (r3 == 0) goto L4a
            r5.bufferSize = r0
            r1 = 0
            r5.buffer = r1
            r5.bufByte = r1
        L48:
            r1 = r6
            goto L53
        L4a:
            boolean r3 = r5.disconnected
            if (r3 == 0) goto L53
            boolean r3 = r2.disconnected
            if (r3 != 0) goto L53
            goto L48
        L53:
            boolean r2 = r2.disconnected
            r5.disconnected = r2
            r10.updateThumbnail(r4)
            goto L11
        L5b:
            int r4 = r4 + 1
            goto L24
        L5e:
            int r12 = r11.size()
            java.util.ArrayList<com.epson.iprojection.engine.common.D_ThumbnailInfo> r0 = r10._arrayThumbnail
            int r0 = r0.size()
            if (r12 != r0) goto L6c
            if (r1 == 0) goto L76
        L6c:
            r10.rebuild(r11)
            com.epson.iprojection.ui.activities.moderator.thumbnail.MeasurableGridView r11 = r10._gridView
            if (r11 == 0) goto L76
            r11.invalidateViews()
        L76:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.moderator.thumbnail.MPPThumbnailManager.onUpdateMPPUserList(java.util.ArrayList, java.util.ArrayList):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x002f, code lost:
        if (r1 != false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0033, code lost:
        if (r8.buffer == null) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0035, code lost:
        r8.buffer = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0038, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0039, code lost:
        updateThumbnail(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003c, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onThumbnailInfo(com.epson.iprojection.engine.common.D_ThumbnailInfo r8) {
        /*
            r7 = this;
            if (r8 == 0) goto L3c
            java.util.ArrayList<com.epson.iprojection.engine.common.D_ThumbnailInfo> r0 = r7._arrayThumbnail
            if (r0 == 0) goto L3c
            java.util.Iterator r0 = r0.iterator()
            r1 = 0
            r2 = r1
        Lc:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L2f
            java.lang.Object r3 = r0.next()
            com.epson.iprojection.engine.common.D_ThumbnailInfo r3 = (com.epson.iprojection.engine.common.D_ThumbnailInfo) r3
            long r3 = r3.userUniqueId
            long r5 = r8.userUniqueId
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 != 0) goto L2c
            byte[] r0 = r8.bufByte
            if (r0 == 0) goto L2b
            java.util.ArrayList<com.epson.iprojection.engine.common.D_ThumbnailInfo> r0 = r7._arrayThumbnail
            r0.set(r2, r8)
            r1 = 1
            goto L2f
        L2b:
            return
        L2c:
            int r2 = r2 + 1
            goto Lc
        L2f:
            if (r1 != 0) goto L39
            java.nio.ByteBuffer r0 = r8.buffer
            if (r0 == 0) goto L39
            r0 = 0
            r8.buffer = r0
            return
        L39:
            r7.updateThumbnail(r2)
        L3c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.moderator.thumbnail.MPPThumbnailManager.onThumbnailInfo(com.epson.iprojection.engine.common.D_ThumbnailInfo):void");
    }

    private void updateThumbnail(final int i) {
        this._gridView.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.moderator.thumbnail.MPPThumbnailManager$$ExternalSyntheticLambda0
            {
                MPPThumbnailManager.this = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                MPPThumbnailManager.this.m107x94be5a26(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$updateThumbnail$1$com-epson-iprojection-ui-activities-moderator-thumbnail-MPPThumbnailManager  reason: not valid java name */
    public /* synthetic */ void m107x94be5a26(int i) {
        View childAt;
        int firstVisiblePosition = i - this._gridView.getFirstVisiblePosition();
        if (firstVisiblePosition < 0 || (childAt = this._gridView.getChildAt(firstVisiblePosition)) == null) {
            return;
        }
        this._gridView.getAdapter().getView(i, childAt, this._gridView);
    }

    public void requestThumbnail() {
        Pj.getIns().requestThumbnail(this._thumbW, this._thumbH, 70);
    }

    public Bitmap getBitmap(int i) {
        D_ThumbnailInfo d_ThumbnailInfo = (D_ThumbnailInfo) this._adapter.getItem(i);
        if (d_ThumbnailInfo == null || d_ThumbnailInfo.bufByte == null) {
            return null;
        }
        return BitmapFactory.decodeByteArray(d_ThumbnailInfo.bufByte, 0, d_ThumbnailInfo.bufByte.length);
    }

    public Bitmap getBitmap(D_MppUserInfo d_MppUserInfo) {
        for (int i = 0; i < this._adapter.getCount(); i++) {
            D_ThumbnailInfo d_ThumbnailInfo = (D_ThumbnailInfo) this._adapter.getItem(i);
            if (d_ThumbnailInfo.userUniqueId == d_MppUserInfo.uniqueId) {
                if (d_ThumbnailInfo == null || d_ThumbnailInfo.bufByte == null) {
                    return null;
                }
                return BitmapFactory.decodeByteArray(d_ThumbnailInfo.bufByte, 0, d_ThumbnailInfo.bufByte.length);
            }
        }
        return null;
    }

    @Override // com.epson.iprojection.ui.activities.moderator.interfaces.IOnSizeChangedW
    public void onSizeChangedW(int i) {
        if (this._isIgnoredOnSizeChangedW) {
            return;
        }
        this._isIgnoredOnSizeChangedW = true;
        setThumbnailSize(MultiProjectionDisplaySettings.INSTANCE.isSmall());
    }
}
