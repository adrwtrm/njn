package com.epson.iprojection.ui.activities.pjselect;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.XmlUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.D_ListItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class ProjList implements AdapterView.OnItemClickListener {
    private final Context _context;
    private final IProjListListener _impl;
    private final ListView _pjList;
    private InflaterListAdapter _listAdapter = null;
    private final ArrayList<D_ListItem> _listDtoInflater = new ArrayList<>();
    private final LinkedList<D_PjInfo> _listPjInfo = new LinkedList<>();
    private int _selectedProjID = 0;
    private int _existProfile = 0;
    private final Handler uiHandler = new Handler();

    /* renamed from: $r8$lambda$ki0hRK-X_BmvcbhbmSIyv-hiuRY */
    public static /* synthetic */ void m131$r8$lambda$ki0hRKX_BmvcbhbmSIyvhiuRY(ProjList projList) {
        projList.UpdateProfile();
    }

    public ProjList(Context context, ListView listView, IProjListListener iProjListListener) {
        this._context = context;
        this._pjList = listView;
        this._impl = iProjListListener;
        UpdateProfile();
        registList();
        listView.setScrollingCacheEnabled(false);
        listView.setOnItemClickListener(this);
    }

    public void clear() {
        if (XmlUtils.mplistType.LOST == XmlUtils.existProfileTypes()) {
            deleteProfile();
            this._existProfile = 0;
        }
        int size = this._listDtoInflater.size();
        int i = this._existProfile;
        if (size > i) {
            this._listDtoInflater.subList(i, size).clear();
        }
        if (!this._listPjInfo.isEmpty()) {
            this._listPjInfo.clear();
        }
        redrawList();
    }

    public synchronized void uncheckAll() {
        int size = this._listDtoInflater.size();
        for (int i = this._existProfile; i < size; i++) {
            this._listDtoInflater.get(i).uncheck();
        }
        redrawList();
    }

    /* JADX WARN: Code restructure failed: missing block: B:76:0x0056, code lost:
        if (r8.bCurrentManualFound != false) goto L43;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void addItem(com.epson.iprojection.engine.common.D_PjInfo r8, boolean r9) {
        /*
            r7 = this;
            monitor-enter(r7)
            com.epson.iprojection.ui.activities.pjselect.InflaterListAdapter r0 = r7._listAdapter     // Catch: java.lang.Throwable -> L8a
            int r0 = r0.getCount()     // Catch: java.lang.Throwable -> L8a
            com.epson.iprojection.ui.activities.pjselect.InflaterListAdapter r1 = r7._listAdapter     // Catch: java.lang.Throwable -> L8a
            int r1 = r1.getCount()     // Catch: java.lang.Throwable -> L8a
            if (r1 >= 0) goto L16
            java.lang.String r8 = "Invalid index."
            com.epson.iprojection.common.Lg.e(r8)     // Catch: java.lang.Throwable -> L8a
            monitor-exit(r7)
            return
        L16:
            boolean r1 = r8.bManualFound     // Catch: java.lang.Throwable -> L8a
            if (r1 != 0) goto L1e
            boolean r1 = r8.bCurrentManualFound     // Catch: java.lang.Throwable -> L8a
            if (r1 == 0) goto L5c
        L1e:
            int r1 = r7._existProfile     // Catch: java.lang.Throwable -> L8a
            java.util.ArrayList<com.epson.iprojection.ui.activities.pjselect.D_ListItem> r2 = r7._listDtoInflater     // Catch: java.lang.Throwable -> L8a
            java.util.Iterator r2 = r2.iterator()     // Catch: java.lang.Throwable -> L8a
            r3 = 0
        L27:
            boolean r4 = r2.hasNext()     // Catch: java.lang.Throwable -> L8a
            if (r4 == 0) goto L5c
            java.lang.Object r4 = r2.next()     // Catch: java.lang.Throwable -> L8a
            com.epson.iprojection.ui.activities.pjselect.D_ListItem r4 = (com.epson.iprojection.ui.activities.pjselect.D_ListItem) r4     // Catch: java.lang.Throwable -> L8a
            com.epson.iprojection.ui.activities.pjselect.D_ListItem$listType r5 = r4.getListType()     // Catch: java.lang.Throwable -> L8a
            com.epson.iprojection.ui.activities.pjselect.D_ListItem$listType r6 = com.epson.iprojection.ui.activities.pjselect.D_ListItem.listType.PROJECTOR     // Catch: java.lang.Throwable -> L8a
            if (r5 != r6) goto L59
            int[] r5 = com.epson.iprojection.ui.activities.pjselect.ProjList.AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$dispPriority     // Catch: java.lang.Throwable -> L8a
            com.epson.iprojection.ui.activities.pjselect.D_ListItem$dispPriority r4 = r4.getPriority()     // Catch: java.lang.Throwable -> L8a
            int r4 = r4.ordinal()     // Catch: java.lang.Throwable -> L8a
            r4 = r5[r4]     // Catch: java.lang.Throwable -> L8a
            r5 = 1
            if (r4 == r5) goto L58
            r6 = 2
            if (r4 == r6) goto L54
            r5 = 3
            if (r4 == r5) goto L51
            goto L59
        L51:
            int r1 = r1 + 1
            goto L59
        L54:
            boolean r4 = r8.bCurrentManualFound     // Catch: java.lang.Throwable -> L8a
            if (r4 == 0) goto L51
        L58:
            r3 = r5
        L59:
            if (r3 == 0) goto L27
            r0 = r1
        L5c:
            com.epson.iprojection.ui.activities.pjselect.D_ListItem r1 = new com.epson.iprojection.ui.activities.pjselect.D_ListItem     // Catch: java.lang.Throwable -> L8a
            android.content.Context r2 = r7._context     // Catch: java.lang.Throwable -> L8a
            com.epson.iprojection.ui.activities.pjselect.D_ListItem$listType r3 = com.epson.iprojection.ui.activities.pjselect.D_ListItem.listType.PROJECTOR     // Catch: java.lang.Throwable -> L8a
            r1.<init>(r2, r3, r8, r9)     // Catch: java.lang.Throwable -> L8a
            boolean r9 = r8.bCurrentManualFound     // Catch: java.lang.Throwable -> L8a
            if (r9 == 0) goto L6c
            com.epson.iprojection.ui.activities.pjselect.D_ListItem$dispPriority r9 = com.epson.iprojection.ui.activities.pjselect.D_ListItem.dispPriority.DP_1ST     // Catch: java.lang.Throwable -> L8a
            goto L75
        L6c:
            boolean r9 = r8.bManualFound     // Catch: java.lang.Throwable -> L8a
            if (r9 == 0) goto L73
            com.epson.iprojection.ui.activities.pjselect.D_ListItem$dispPriority r9 = com.epson.iprojection.ui.activities.pjselect.D_ListItem.dispPriority.DP_2ND     // Catch: java.lang.Throwable -> L8a
            goto L75
        L73:
            com.epson.iprojection.ui.activities.pjselect.D_ListItem$dispPriority r9 = com.epson.iprojection.ui.activities.pjselect.D_ListItem.dispPriority.DP_NORMAL     // Catch: java.lang.Throwable -> L8a
        L75:
            r1.setPriority(r9)     // Catch: java.lang.Throwable -> L8a
            java.util.ArrayList<com.epson.iprojection.ui.activities.pjselect.D_ListItem> r9 = r7._listDtoInflater     // Catch: java.lang.Throwable -> L8a
            r9.add(r0, r1)     // Catch: java.lang.Throwable -> L8a
            java.util.LinkedList<com.epson.iprojection.engine.common.D_PjInfo> r9 = r7._listPjInfo     // Catch: java.lang.Throwable -> L8a
            int r1 = r7._existProfile     // Catch: java.lang.Throwable -> L8a
            int r0 = r0 - r1
            r9.add(r0, r8)     // Catch: java.lang.Throwable -> L8a
            r7.redrawList()     // Catch: java.lang.Throwable -> L8a
            monitor-exit(r7)
            return
        L8a:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.pjselect.ProjList.addItem(com.epson.iprojection.engine.common.D_PjInfo, boolean):void");
    }

    public synchronized void updateItem(D_PjInfo d_PjInfo, boolean z) {
        D_ListItem.dispPriority disppriority;
        int index = getIndex(d_PjInfo.ProjectorID);
        if (-1 == index) {
            Lg.w("updateすべきプロジェクタが見つかりません:" + d_PjInfo.PrjName);
            return;
        }
        D_ListItem d_ListItem = new D_ListItem(this._context, D_ListItem.listType.PROJECTOR, d_PjInfo, z);
        if (d_PjInfo.bCurrentManualFound) {
            disppriority = D_ListItem.dispPriority.DP_1ST;
        } else {
            disppriority = d_PjInfo.bManualFound ? D_ListItem.dispPriority.DP_2ND : D_ListItem.dispPriority.DP_NORMAL;
        }
        d_ListItem.setPriority(disppriority);
        this._listDtoInflater.set(this._existProfile + index, d_ListItem);
        this._listPjInfo.set(index, d_PjInfo);
        redrawList();
    }

    public void displayProfileListUIThread() {
        this.uiHandler.post(new Runnable() { // from class: com.epson.iprojection.ui.activities.pjselect.ProjList$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ProjList.m131$r8$lambda$ki0hRKX_BmvcbhbmSIyvhiuRY(ProjList.this);
            }
        });
    }

    public synchronized void UpdateProfile() {
        if (XmlUtils.listFilter(XmlUtils.filterType.MASTER, XmlUtils.place.APPS).length != 0) {
            if (1 != this._existProfile) {
                if (this._listDtoInflater.size() == 0 || !this._listDtoInflater.get(0).getListType().equals(D_ListItem.listType.PROFILE)) {
                    this._listDtoInflater.add(0, new D_ListItem(this._context, D_ListItem.listType.PROFILE, null, false));
                }
                this._existProfile = 1;
                registList();
            }
        } else if (this._existProfile != 0) {
            this._existProfile = 0;
            deleteProfile();
            registList();
        }
    }

    public static /* synthetic */ boolean lambda$deleteProfile$0(D_ListItem d_ListItem) {
        return d_ListItem.getListType().equals(D_ListItem.listType.PROFILE);
    }

    public synchronized void deleteProfile() {
        this._listDtoInflater.removeIf(new Predicate() { // from class: com.epson.iprojection.ui.activities.pjselect.ProjList$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ProjList.lambda$deleteProfile$0((D_ListItem) obj);
            }
        });
    }

    public synchronized void deleteItem(int i) {
        for (int i2 = 0; i2 < this._listPjInfo.size(); i2++) {
            if (i == this._listPjInfo.get(i2).ProjectorID) {
                this._listDtoInflater.remove(this._existProfile + i2);
                this._listPjInfo.remove(i2);
                redrawList();
            }
        }
    }

    public synchronized void registList() {
        InflaterListAdapter inflaterListAdapter = new InflaterListAdapter(this._context, this._listDtoInflater);
        this._listAdapter = inflaterListAdapter;
        this._pjList.setAdapter((ListAdapter) inflaterListAdapter);
    }

    public void redrawList() {
        InflaterListAdapter inflaterListAdapter = this._listAdapter;
        if (inflaterListAdapter == null) {
            registList();
            return;
        }
        inflaterListAdapter.notifyDataSetChanged();
        boolean z = true;
        boolean z2 = this._listDtoInflater.size() == 0;
        if (this._listDtoInflater.size() != 1 || !existsProfile()) {
            z = z2;
        }
        this._impl.updateList(z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.epson.iprojection.ui.activities.pjselect.ProjList$1 */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$dispPriority;
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$listType;

        static {
            int[] iArr = new int[D_ListItem.listType.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$listType = iArr;
            try {
                iArr[D_ListItem.listType.PROJECTOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$listType[D_ListItem.listType.PROFILE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[D_ListItem.dispPriority.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$dispPriority = iArr2;
            try {
                iArr2[D_ListItem.dispPriority.DP_NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$dispPriority[D_ListItem.dispPriority.DP_2ND.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$dispPriority[D_ListItem.dispPriority.DP_1ST.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int i2 = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$listType[this._listDtoInflater.get(i).getListType().ordinal()];
        if (i2 != 1) {
            if (i2 != 2) {
                return;
            }
            this._impl.onClickProfile();
        } else if (view.getTag().equals(InflaterListAdapter.tagMoreInfo)) {
            this._impl.onClickProjectorDetail(this._listPjInfo.get(i - this._existProfile));
        } else {
            this._selectedProjID = this._listPjInfo.get(i - this._existProfile).ProjectorID;
            this._impl.onClickItem(this._listPjInfo.get(i - this._existProfile), view);
        }
    }

    public boolean existsProfile() {
        return this._existProfile != 0;
    }

    public D_PjInfo getSelectedItem() {
        return getPjInfo(this._selectedProjID);
    }

    private D_PjInfo getPjInfo(int i) {
        Iterator<D_PjInfo> it = this._listPjInfo.iterator();
        while (it.hasNext()) {
            D_PjInfo next = it.next();
            if (next.ProjectorID == i) {
                return next;
            }
        }
        Lg.w("プロジェクターIDが見つかりませんでした id:" + i);
        return null;
    }

    private int getIndex(int i) {
        Iterator<D_PjInfo> it = this._listPjInfo.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (it.next().ProjectorID == i) {
                return i2;
            }
            i2++;
        }
        Lg.w("プロジェクターIDが見つかりませんでした id:" + i);
        return -1;
    }
}
