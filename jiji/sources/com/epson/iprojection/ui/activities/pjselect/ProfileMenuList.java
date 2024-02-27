package com.epson.iprojection.ui.activities.pjselect;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.epson.iprojection.common.utils.XmlUtils;
import com.epson.iprojection.ui.activities.pjselect.D_ListItem;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ProfileMenuList implements AdapterView.OnItemClickListener {
    private final Context _context;
    private final IProjListListener _impl;
    private final ArrayList<D_ListItem> _listDtoInflater;
    private final ListView _pjList;

    public ProfileMenuList(Context context, ListView listView, IProjListListener iProjListListener) {
        ArrayList<D_ListItem> arrayList = new ArrayList<>();
        this._listDtoInflater = arrayList;
        this._context = context;
        this._pjList = listView;
        this._impl = iProjListListener;
        arrayList.add(new D_ListItem(context, D_ListItem.listType.PROFILE_SHARED, null, false));
        arrayList.add(new D_ListItem(context, D_ListItem.listType.PROFILE_LOCAL, null, false));
        updateListener();
    }

    public void updateListener() {
        updateList();
        this._pjList.setScrollingCacheEnabled(false);
        this._pjList.setOnItemClickListener(this);
    }

    private void updateList() {
        this._pjList.setAdapter((ListAdapter) new InflaterMenuAdapter(this._context, this._listDtoInflater));
    }

    /* renamed from: com.epson.iprojection.ui.activities.pjselect.ProfileMenuList$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$listType;

        static {
            int[] iArr = new int[D_ListItem.listType.values().length];
            $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$listType = iArr;
            try {
                iArr[D_ListItem.listType.PROFILE_LOCAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$listType[D_ListItem.listType.PROFILE_SHARED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int i2 = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$activities$pjselect$D_ListItem$listType[this._listDtoInflater.get(i).getListType().ordinal()];
        if (i2 == 1) {
            this._impl.onClickProfile(XmlUtils.mplistType.LOCAL);
        } else if (i2 != 2) {
        } else {
            this._impl.onClickProfile(XmlUtils.mplistType.SHARED);
        }
    }
}
