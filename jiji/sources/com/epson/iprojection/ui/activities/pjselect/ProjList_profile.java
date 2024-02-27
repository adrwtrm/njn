package com.epson.iprojection.ui.activities.pjselect;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.utils.PathGetter;
import com.epson.iprojection.common.utils.XmlUtils;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.ui.activities.pjselect.layouts.ProfileDetailDialog;
import com.epson.iprojection.ui.activities.pjselect.layouts.RemovableView;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class ProjList_profile implements AdapterView.OnItemClickListener {
    public static final String ATTRIBUTE_NAME_SPACE = "xml:space";
    public static final String ATTRIBUTE_VAL_PRESERVE = "preserve";
    public static final int FOLDER_DEPATH_MAX = 7;
    public static final String NODE_NAME_ADDR_BOOK_INFO = "AddrBookInfo";
    public static final String NODE_NAME_APP_NAME = "APPName";
    public static final String NODE_NAME_COMMENT = "Comment";
    public static final String NODE_NAME_FILE_TYPE = "FileType";
    public static final String NODE_NAME_FOLDER = "Folder";
    public static final String NODE_NAME_IP_ADDR = "IPAddr";
    public static final String NODE_NAME_NODE_NAME = "NodeName";
    public static final String NODE_NAME_PROJ_FILE = "ProjFile";
    public static final String NODE_NAME_PROJ_NAME = "ProjName";
    public static final String NODE_NAME_ROOT = "Root";
    public static final String NODE_NAME_UNIQ_INFO = "UniqInfo";
    private final Activity _activity;
    private final IProjListListener _impl;
    private final ListView _pjList;
    private InflaterListProfileAdapter _listAdapter = null;
    private final ArrayList<D_ProfileItem> _listDtoInflater = new ArrayList<>();
    private final LinkedList<D_PjInfo> _listPjInfo = new LinkedList<>();
    private int _currentDepth = 0;

    /* loaded from: classes.dex */
    public class projectorData {
        String node = "";
        String pjName = "";
        String ip = "";
        String unique = "";
        String comment = "";

        public projectorData() {
        }
    }

    public ProjList_profile(Activity activity, ListView listView, IProjListListener iProjListListener, XmlUtils.mplistType mplisttype) {
        this._activity = activity;
        this._pjList = listView;
        this._impl = iProjListListener;
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$common$utils$XmlUtils$mplistType[mplisttype.ordinal()];
        if (i == 1) {
            loadProfile(CommonDefine.LOCAL_MASTER_PROFILE);
            XmlUtils.filterType filtertype = XmlUtils.filterType.MPLIST;
        } else if (i != 2) {
            return;
        } else {
            loadProfile(CommonDefine.SHARED_MASTER_PROFILE);
            XmlUtils.filterType filtertype2 = XmlUtils.filterType.SHMPLIST;
        }
        registList();
        listView.setScrollingCacheEnabled(false);
        listView.setOnItemClickListener(this);
    }

    /* renamed from: com.epson.iprojection.ui.activities.pjselect.ProjList_profile$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$epson$iprojection$common$utils$XmlUtils$mplistType;

        static {
            int[] iArr = new int[XmlUtils.mplistType.values().length];
            $SwitchMap$com$epson$iprojection$common$utils$XmlUtils$mplistType = iArr;
            try {
                iArr[XmlUtils.mplistType.LOCAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$epson$iprojection$common$utils$XmlUtils$mplistType[XmlUtils.mplistType.SHARED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public void clear() {
        this._listDtoInflater.clear();
    }

    public void registList() {
        InflaterListProfileAdapter inflaterListProfileAdapter = new InflaterListProfileAdapter((RemovableView) this._impl, this._listDtoInflater, this._listPjInfo);
        this._listAdapter = inflaterListProfileAdapter;
        this._pjList.setAdapter((ListAdapter) inflaterListProfileAdapter);
        this._pjList.setTextFilterEnabled(true);
    }

    public void redrawList(boolean z) {
        InflaterListProfileAdapter inflaterListProfileAdapter = this._listAdapter;
        if (inflaterListProfileAdapter == null) {
            registList();
        } else {
            inflaterListProfileAdapter.wrapNotifyDataSetChanged(z);
        }
    }

    public String updateListTitle() {
        InflaterListProfileAdapter inflaterListProfileAdapter = this._listAdapter;
        if (inflaterListProfileAdapter != null) {
            return inflaterListProfileAdapter.getParentNodeName(this._currentDepth);
        }
        return null;
    }

    public InflaterListProfileAdapter getAdapter() {
        InflaterListProfileAdapter inflaterListProfileAdapter = this._listAdapter;
        if (inflaterListProfileAdapter != null) {
            return inflaterListProfileAdapter;
        }
        return null;
    }

    public int getParentDepth() {
        InflaterListProfileAdapter inflaterListProfileAdapter = this._listAdapter;
        if (inflaterListProfileAdapter != null) {
            return inflaterListProfileAdapter.getMasterParentDepth(this._currentDepth);
        }
        return -1;
    }

    public void setCurrentDepth(int i) {
        this._currentDepth = i;
    }

    private boolean loadProfile(String str) {
        XmlPullParser buildXmlParse;
        if (new File(PathGetter.getIns().getAppsDirPath() + "/" + str).exists() && (buildXmlParse = XmlUtils.buildXmlParse(this._activity, str)) != null) {
            return LoadToInflater(buildXmlParse);
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x00dc, code lost:
        if (r9 != false) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00de, code lost:
        com.epson.iprojection.common.Lg.d("IPアドレスもプロジェクター名もありません");
        r5 = r2;
        r7 = r18;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r8v17, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean LoadToInflater(org.xmlpull.v1.XmlPullParser r30) {
        /*
            Method dump skipped, instructions count: 653
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.activities.pjselect.ProjList_profile.LoadToInflater(org.xmlpull.v1.XmlPullParser):boolean");
    }

    private boolean checkTag(String str, String str2) {
        return str != null && str.equals(str2);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [android.widget.Adapter] */
    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        D_ProfileItem d_ProfileItem = (D_ProfileItem) adapterView.getAdapter().getItem(i);
        if (!d_ProfileItem._isFoler) {
            if (view.getTag().equals(InflaterListProfileAdapter.tagMoreInfo)) {
                new ProfileDetailDialog(this._activity, d_ProfileItem).show();
                return;
            }
            this._impl.onClickItem(this._listPjInfo.get(d_ProfileItem._uniqueNum), view);
            redrawList(false);
            return;
        }
        this._impl.onClickFolder(d_ProfileItem._depth.current);
    }
}
