package com.epson.iprojection.ui.engine_wrapper;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import com.epson.iprojection.common.CommonDefine;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.ChromeOSUtils;
import com.epson.iprojection.common.utils.NetUtils;
import com.epson.iprojection.common.utils.Sleeper;
import com.epson.iprojection.engine.Engine;
import com.epson.iprojection.engine.common.D_AddFixedSearchInfo;
import com.epson.iprojection.engine.common.D_AddPjInfo;
import com.epson.iprojection.engine.common.D_MirrorPjInfo;
import com.epson.iprojection.engine.common.D_PjInfo;
import com.epson.iprojection.engine.common.OnFindPjListener;
import com.epson.iprojection.engine.common.Tool;
import com.epson.iprojection.ui.engine_wrapper.PjFinder;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IOnSearchThreadEndListener;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjManualSearchResultListener;
import com.epson.iprojection.ui.engine_wrapper.interfaces.IPjSearchStatusListener;
import com.epson.iprojection.ui.engine_wrapper.utils.MsgGetterUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class PjFinder implements OnFindPjListener {
    private final ArrayList<ConnectPjInfo> _aSelectPjFromHistory;
    private ArrayList<ConnectPjInfo> _aSelectPjInfo;
    private final Context _context;
    private final Engine _engine;
    private final IOnSearchThreadEndListener _implOnSearchThreadEndListener;
    private IPjSearchStatusListener _implSearchStatus;
    private boolean _isLinkageDataSearching;
    private final LockedPjInfo _lockedPjInfo;
    private PjStatusOfRegistedUpdateThread _statusUpdateThread;
    private final int ERR = -1;
    private IPjManualSearchResultListener _implManualSearchResult = null;
    private Thread _thread = null;
    private final ArrayList<String> _manualSearchIP = new ArrayList<>();
    private boolean _bUINotInterrupt = false;
    private final ArrayList<D_PjInfo> _aSearchPj = new ArrayList<>();
    private final ArrayList<D_PjInfo> _aManualSearchedPj = new ArrayList<>();
    private final Handler _handler = new Handler(Looper.getMainLooper());
    private final SearchLooper _searchLooper = new SearchLooper();
    private PjList _pjList = new PjList();

    @Override // com.epson.iprojection.engine.common.OnFindPjListener
    public void onUpdateProjectorList() {
    }

    public PjFinder(Engine engine, IPjSearchStatusListener iPjSearchStatusListener, IOnSearchThreadEndListener iOnSearchThreadEndListener, LockedPjInfo lockedPjInfo, ArrayList<ConnectPjInfo> arrayList, ArrayList<ConnectPjInfo> arrayList2, boolean z, Context context) {
        this._engine = engine;
        this._context = context;
        this._implSearchStatus = iPjSearchStatusListener;
        this._implOnSearchThreadEndListener = iOnSearchThreadEndListener;
        this._lockedPjInfo = lockedPjInfo;
        this._aSelectPjInfo = arrayList;
        this._aSelectPjFromHistory = arrayList2;
        this._isLinkageDataSearching = z;
        ArrayList<ConnectPjInfo> nowConnectingPjArray = lockedPjInfo.getNowConnectingPjArray();
        if (nowConnectingPjArray != null) {
            for (int i = 0; i < nowConnectingPjArray.size(); i++) {
                onPjInfo(nowConnectingPjArray.get(i).getPjInfo());
            }
        }
    }

    public void setLinkageDataSearchingMode(boolean z) {
        this._isLinkageDataSearching = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isEnableLinkageDataSearching() {
        WifiManager wifiManager;
        return (!this._isLinkageDataSearching || (wifiManager = (WifiManager) this._context.getSystemService("wifi")) == null || wifiManager.getConnectionInfo().getBSSID() == null || wifiManager.getConnectionInfo().getIpAddress() == 0) ? false : true;
    }

    public void setPjSearchStatusListener(IPjSearchStatusListener iPjSearchStatusListener) {
        this._implSearchStatus = iPjSearchStatusListener;
        this._pjList = new PjList();
        if (this._engine.IsSearching()) {
            this._engine.EndSearchPj();
        }
        ArrayList<ConnectPjInfo> nowConnectingPjArray = this._lockedPjInfo.getNowConnectingPjArray();
        if (nowConnectingPjArray != null) {
            for (int i = 0; i < nowConnectingPjArray.size(); i++) {
                onPjInfo(nowConnectingPjArray.get(i).getPjInfo());
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0115 A[LOOP:3: B:43:0x010d->B:45:0x0115, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01f8  */
    @Override // com.epson.iprojection.engine.common.OnFindPjListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onPjInfo(com.epson.iprojection.engine.common.D_PjInfo r10) {
        /*
            Method dump skipped, instructions count: 650
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.engine_wrapper.PjFinder.onPjInfo(com.epson.iprojection.engine.common.D_PjInfo):void");
    }

    private int getSelectPjListID(int i) {
        if (this._aSelectPjInfo == null) {
            return -1;
        }
        for (int i2 = 0; i2 < this._aSelectPjInfo.size(); i2++) {
            if (this._aSelectPjInfo.get(i2).getPjInfo().ProjectorID == i) {
                return i2;
            }
        }
        return -1;
    }

    private boolean isStatusChanged(D_PjInfo d_PjInfo, D_PjInfo d_PjInfo2) {
        if (d_PjInfo.PrjName.compareTo(d_PjInfo2.PrjName) == 0 && d_PjInfo.Status == d_PjInfo2.Status && d_PjInfo.isNeededPjKeyword == d_PjInfo2.isNeededPjKeyword && d_PjInfo.bSelected == d_PjInfo2.bSelected && d_PjInfo.nDispStatus == d_PjInfo2.nDispStatus && d_PjInfo.bCurrentManualFound == d_PjInfo2.bCurrentManualFound && d_PjInfo.bManualFound == d_PjInfo2.bManualFound) {
            for (int i = 0; i < d_PjInfo.IPAddr.length && i < d_PjInfo2.IPAddr.length; i++) {
                if (d_PjInfo.IPAddr[i] != d_PjInfo2.IPAddr[i]) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override // com.epson.iprojection.engine.common.OnFindPjListener
    public void onSearchEnd(int i) {
        Lg.d("onSearchEnd()");
        IPjManualSearchResultListener iPjManualSearchResultListener = this._implManualSearchResult;
        if (iPjManualSearchResultListener != null) {
            iPjManualSearchResultListener.onEndSearchPj();
            this._implManualSearchResult = null;
            this._manualSearchIP.clear();
            this._aSearchPj.clear();
        }
        onSearchFinish();
        this._implOnSearchThreadEndListener.onSearchThreadEnd();
        if (i == 0) {
            for (int size = this._aSelectPjInfo.size() - 1; size >= 0; size--) {
                if (this._pjList.getPJByID(this._aSelectPjInfo.get(size).getPjInfo().ProjectorID) == null) {
                    this._aSelectPjInfo.remove(size);
                }
            }
            int tryConnectingPjNum = this._lockedPjInfo.getTryConnectingPjNum();
            int nowConnectingPjNum = this._lockedPjInfo.getNowConnectingPjNum();
            int[] iArr = new int[tryConnectingPjNum + nowConnectingPjNum];
            for (int i2 = 0; i2 < tryConnectingPjNum; i2++) {
                iArr[i2] = this._lockedPjInfo.getTryConnectingPjID(i2);
            }
            for (int i3 = 0; i3 < nowConnectingPjNum; i3++) {
                iArr[i3 + tryConnectingPjNum] = this._lockedPjInfo.getNowConnectingPjID(i3);
            }
            this._pjList.deleteNotFoundPj(this._implSearchStatus, iArr);
            IPjSearchStatusListener iPjSearchStatusListener = this._implSearchStatus;
            if (iPjSearchStatusListener != null) {
                iPjSearchStatusListener.onPjLose(-1);
            }
        }
    }

    public void onSearchFinish() {
        IPjSearchStatusListener iPjSearchStatusListener = this._implSearchStatus;
        if (iPjSearchStatusListener != null) {
            iPjSearchStatusListener.onSearchPause();
        }
        this._searchLooper.setIsSearching(false);
    }

    public void onSearchInterrupted() {
        IPjSearchStatusListener iPjSearchStatusListener = this._implSearchStatus;
        if (iPjSearchStatusListener != null) {
            iPjSearchStatusListener.onSearchPause();
        }
    }

    public void onConnected(D_PjInfo d_PjInfo) {
        IPjSearchStatusListener iPjSearchStatusListener = this._implSearchStatus;
        if (iPjSearchStatusListener != null) {
            iPjSearchStatusListener.onPjStatusChanged(d_PjInfo, true);
        }
    }

    public void onDisconnected(int i) {
        if (this._implSearchStatus != null) {
            D_PjInfo pJByID = this._pjList.getPJByID(i);
            if (pJByID == null) {
                Lg.i("illigal pjID:" + i);
            } else if (!pJByID.isMppMirror()) {
                this._implSearchStatus.onPjStatusChanged(pJByID, false);
            } else {
                for (int i2 = 0; i2 < this._lockedPjInfo.getNowConnectingPjNum(); i2++) {
                    this._implSearchStatus.onPjStatusChanged(this._pjList.getPJByID(this._lockedPjInfo.getNowConnectingPjID(i2)), false);
                }
            }
        }
    }

    public void updatePjList() {
        updatePjList(false);
    }

    public void updatePjList(boolean z) {
        ArrayList<D_PjInfo> pJList;
        boolean z2;
        PjList pjList = this._pjList;
        if (pjList == null || (pJList = pjList.getPJList()) == null || pJList.size() <= 0) {
            return;
        }
        int selectedPjStatus = getSelectedPjStatus();
        ArrayList<D_PjInfo> arrayList = new ArrayList<>();
        for (int i = 0; i < this._aSelectPjInfo.size(); i++) {
            ConnectPjInfo connectPjInfo = this._aSelectPjInfo.get(i);
            connectPjInfo.setNoInterrupt(this._bUINotInterrupt);
            arrayList.add(connectPjInfo.getPjInfo());
        }
        for (int i2 = 0; i2 < pJList.size(); i2++) {
            D_PjInfo d_PjInfo = pJList.get(i2);
            boolean z3 = d_PjInfo.bSelected;
            if (d_PjInfo.SetDispInfo(arrayList, selectedPjStatus, this._bUINotInterrupt, z)) {
                if (!z3 && d_PjInfo.bSelected) {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= this._aSelectPjInfo.size()) {
                            z2 = false;
                            break;
                        } else if (d_PjInfo.ProjectorID == this._aSelectPjInfo.get(i3).getPjInfo().ProjectorID) {
                            z2 = true;
                            break;
                        } else {
                            i3++;
                        }
                    }
                    if (!z2) {
                        this._aSelectPjInfo.add(new ConnectPjInfo(d_PjInfo, this._bUINotInterrupt));
                    }
                }
                if (this._implSearchStatus != null) {
                    LockedPjInfo lockedPjInfo = this._lockedPjInfo;
                    this._implSearchStatus.onPjStatusChanged(d_PjInfo, lockedPjInfo != null ? lockedPjInfo.isInNowConnectingPjID(d_PjInfo.ProjectorID) : false);
                }
            }
        }
    }

    public int startSearch() {
        Lg.d("startSearch()");
        this._searchLooper.setNoSearchState(false);
        if (this._thread == null) {
            this._searchLooper.enable();
            Thread thread = new Thread(this._searchLooper);
            this._thread = thread;
            thread.start();
        }
        return 0;
    }

    public int restartSearch(boolean z) {
        boolean z2 = false;
        this._searchLooper.setNoSearchState(false);
        if (!z) {
            this._engine.ClearManualSearchFlag();
        }
        int genericSearch = genericSearch(0, this);
        if (genericSearch != 0) {
            Lg.w("stratSearch失敗 理由=" + MsgGetterUtils.getErrMsg(genericSearch));
        } else {
            z2 = true;
        }
        IPjSearchStatusListener iPjSearchStatusListener = this._implSearchStatus;
        if (iPjSearchStatusListener != null) {
            iPjSearchStatusListener.onSearchStart();
        }
        if (!z2) {
            this._searchLooper.enable();
            onSearchFinish();
            this._searchLooper.setWaitSkip(true);
        }
        return genericSearch;
    }

    private int genericSearch(int i, OnFindPjListener onFindPjListener) {
        this._pjList.clearJustNowFoundList();
        return this._engine.StartSearchPj(i, onFindPjListener);
    }

    public int manualSearch(IPjManualSearchResultListener iPjManualSearchResultListener, String str, boolean z) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        return manualSearch(iPjManualSearchResultListener, arrayList, z);
    }

    public int manualSearch(IPjManualSearchResultListener iPjManualSearchResultListener, List<String> list, boolean z) {
        this._implManualSearchResult = iPjManualSearchResultListener;
        this._engine.ClearManualSearchFlag();
        if (!z) {
            this._engine.ClearCurrentManualSearchFlag();
        }
        boolean z2 = z;
        for (String str : list) {
            byte[] convertIpStringToBytes = NetUtils.convertIpStringToBytes(str);
            D_AddFixedSearchInfo d_AddFixedSearchInfo = new D_AddFixedSearchInfo();
            d_AddFixedSearchInfo.ipAddress = convertIpStringToBytes;
            if (!z) {
                if (this._engine.IsJNI()) {
                    this._pjList.clearAllCurrentManualFoundFlag();
                    Iterator<D_PjInfo> it = this._aManualSearchedPj.iterator();
                    while (it.hasNext()) {
                        it.next().bCurrentManualFound = false;
                    }
                }
                d_AddFixedSearchInfo.bCurrentManualSearch = true;
                d_AddFixedSearchInfo.bManualSearch = true;
            }
            int AddSearchProjector = this._engine.AddSearchProjector(d_AddFixedSearchInfo, new D_AddPjInfo());
            if (AddSearchProjector != -204 && AddSearchProjector != 0) {
                return AddSearchProjector;
            }
            this._manualSearchIP.add(NetUtils.getIpStringWithoutPrefZero(str));
            addManualSearchedIP(str);
            z2 = true;
        }
        if (z2) {
            return genericSearch(1, this);
        }
        return -1;
    }

    public void addToAddFixedSearchInfoArray(ArrayList<D_AddFixedSearchInfo> arrayList, byte[] bArr, int i) {
        D_AddFixedSearchInfo d_AddFixedSearchInfo = new D_AddFixedSearchInfo();
        d_AddFixedSearchInfo.ipAddress = bArr;
        d_AddFixedSearchInfo.prjID = i;
        arrayList.add(d_AddFixedSearchInfo);
    }

    public int manualSearchWithFixedInfo(IPjManualSearchResultListener iPjManualSearchResultListener, ArrayList<D_AddFixedSearchInfo> arrayList, boolean z) {
        this._implManualSearchResult = iPjManualSearchResultListener;
        Iterator<D_AddFixedSearchInfo> it = arrayList.iterator();
        boolean z2 = z;
        while (it.hasNext()) {
            D_AddFixedSearchInfo next = it.next();
            D_AddPjInfo d_AddPjInfo = new D_AddPjInfo();
            if (!z) {
                if (this._engine.IsJNI()) {
                    this._pjList.clearAllCurrentManualFoundFlag();
                }
                this._engine.ClearCurrentManualSearchFlag();
                next.bCurrentManualSearch = true;
                next.bManualSearch = true;
            }
            int AddSearchProjector = this._engine.AddSearchProjector(next, d_AddPjInfo);
            if (AddSearchProjector != -204 && AddSearchProjector != 0) {
                return AddSearchProjector;
            }
            this._manualSearchIP.add(NetUtils.cvtIPAddr(next.ipAddress));
            z2 = true;
        }
        if (z2) {
            return genericSearch(1, this);
        }
        return -1;
    }

    public void pauseSearch() {
        this._pjList.interrupted();
        this._searchLooper.disable();
        this._manualSearchIP.clear();
        if (this._engine.IsJNI()) {
            this._engine.ClearManualSearchFlag();
        }
        this._aSearchPj.clear();
        this._engine.EndSearchPj();
    }

    public void endSearch() {
        Lg.d("endSearch()");
        this._pjList.interrupted();
        this._searchLooper.disable();
        int EndSearchPj = this._engine.EndSearchPj();
        if (this._engine.IsJNI()) {
            if (EndSearchPj == 0 || EndSearchPj == -209) {
                Lg.d("エンジンが検索中状態なので、待機します");
            } else {
                Lg.d("エンジンが検索中状態ではないので、そのままコールバックします");
                this._searchLooper.setIsSearching(false);
                this._implOnSearchThreadEndListener.onSearchThreadEnd();
                if (EndSearchPj == -440) {
                    Lg.d("想定通りの返り値");
                } else {
                    Lg.e("Illigal return value!! -> " + MsgGetterUtils.getErrMsg(EndSearchPj));
                }
            }
        } else if (EndSearchPj == 0) {
            Lg.d("エンジンが検索中状態なので、待機します");
        } else {
            Lg.i("エンジンが検索中状態ではないので、そのままコールバックします");
            this._implOnSearchThreadEndListener.onSearchThreadEnd();
            if (EndSearchPj == -440) {
                Lg.d("想定通りの返り値");
            } else {
                Lg.e("Illigal return value!! -> " + MsgGetterUtils.getErrMsg(EndSearchPj));
            }
        }
        this._thread = null;
    }

    public void noSearch() {
        this._searchLooper.setNoSearchState(true);
    }

    public boolean isUsableListener() {
        return this._implSearchStatus != null;
    }

    public boolean isSearching() {
        return this._searchLooper.isRunning();
    }

    private int isHitManualSearchResult(D_PjInfo d_PjInfo) {
        if (this._implManualSearchResult != null) {
            if (this._manualSearchIP.size() != 0) {
                for (int i = 0; i < this._manualSearchIP.size(); i++) {
                    if (this._manualSearchIP.get(i).compareTo(NetUtils.cvtIPAddr(d_PjInfo.IPAddr)) == 0) {
                        this._manualSearchIP.remove(i);
                        if (d_PjInfo.Status == 0) {
                            Lg.i("手動検索-見つかりませんでした");
                            if (this._engine.IsJNI()) {
                                this._engine.ClearManualSearchFlag();
                            }
                            return -1;
                        } else if (this._manualSearchIP.size() == 0) {
                            Lg.d("手動検索-見つかりました!! 終わりました!!");
                            this._implManualSearchResult.onFindSearchPj(d_PjInfo, true);
                            if (this._engine.IsJNI()) {
                                this._engine.ClearManualSearchFlag();
                            }
                        } else {
                            Lg.d("手動検索-見つかりました!! まだあります!!");
                            this._implManualSearchResult.onFindSearchPj(d_PjInfo, false);
                        }
                    }
                }
                return 0;
            } else if (this._aSearchPj.size() != 0) {
                for (int i2 = 0; i2 < this._aSearchPj.size(); i2++) {
                    if (isWishPj(d_PjInfo, this._aSearchPj.get(i2))) {
                        this._aSearchPj.remove(i2);
                        if (d_PjInfo.Status == 0) {
                            Lg.i("手動検索-見つかりませんでした");
                            return -1;
                        } else if (this._aSearchPj.size() == 0) {
                            Lg.d("手動検索-見つかりました!! 終わりました!!");
                            this._implManualSearchResult.onFindSearchPj(d_PjInfo, true);
                        } else {
                            Lg.d("手動検索-見つかりました!! まだあります!!");
                            this._implManualSearchResult.onFindSearchPj(d_PjInfo, false);
                        }
                    }
                }
                return 0;
            } else {
                return 0;
            }
            return 1;
        }
        return 0;
    }

    private boolean isWishPj(D_PjInfo d_PjInfo, D_PjInfo d_PjInfo2) {
        boolean isEmpty = d_PjInfo2.PrjName.isEmpty();
        boolean IsEmptyIPAddr = Tool.IsEmptyIPAddr(d_PjInfo2.IPAddr);
        if (Tool.IsEmptyUniqInfo(d_PjInfo2.UniqInfo) || Arrays.equals(d_PjInfo.UniqInfo, d_PjInfo2.UniqInfo)) {
            if (isEmpty || d_PjInfo.PrjName.equals(d_PjInfo2.PrjName)) {
                if (IsEmptyIPAddr) {
                    return true;
                }
                return Arrays.equals(d_PjInfo.IPAddr, d_PjInfo2.IPAddr);
            }
            return false;
        }
        return false;
    }

    public void disableManualSearchListener() {
        Lg.e("disableManualSearchListener");
        this._implManualSearchResult = null;
    }

    public void disablePjListStatusListener() {
        this._implSearchStatus = null;
    }

    public D_PjInfo getPjInfoFromID(int i) {
        return this._pjList.getPJByID(i);
    }

    public D_PjInfo getPJByIP(String str) {
        return this._pjList.getPJByIP(str);
    }

    public D_PjInfo getPJByIPandName(String str, String str2) {
        return this._pjList.getPJByIPandName(str, str2);
    }

    public void setNotInterruptUI(boolean z) {
        this._bUINotInterrupt = z;
        updatePjList();
        updateInterruptStatusConnPjFromHistory();
    }

    private int notifyAndRemovePjInfo(D_PjInfo d_PjInfo, int i, int i2, boolean z) {
        if (z) {
            d_PjInfo.bSelected = false;
            d_PjInfo.nDispStatus = i;
            if (this._implSearchStatus != null) {
                LockedPjInfo lockedPjInfo = this._lockedPjInfo;
                this._implSearchStatus.onPjStatusChanged(d_PjInfo, lockedPjInfo != null ? lockedPjInfo.isInNowConnectingPjID(d_PjInfo.ProjectorID) : false);
            }
            this._aSelectPjInfo.remove(i2);
        }
        return i2;
    }

    private int getSelectedPjStatus() {
        int i;
        int size = this._aSelectPjInfo.size();
        if (size > 0) {
            boolean isPjListTypeBusiness = D_PjInfo.isPjListTypeBusiness(ConnectPjInfo.createPjInfoList(this._aSelectPjInfo));
            i = -1;
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                D_PjInfo pjInfo = this._aSelectPjInfo.get(i2).getPjInfo();
                switch (pjInfo.Status) {
                    case 1:
                        if (i == 4 || i == 5) {
                            i2 = notifyAndRemovePjInfo(pjInfo, 1, i2, isPjListTypeBusiness);
                            continue;
                        } else {
                            i2++;
                            i = 1;
                        }
                    case 2:
                        if (i == 4 || i == 5) {
                            i2 = notifyAndRemovePjInfo(pjInfo, 4, i2, isPjListTypeBusiness);
                        } else {
                            i2++;
                            i = 2;
                            continue;
                        }
                    case 3:
                    case 8:
                        i2 = notifyAndRemovePjInfo(pjInfo, 4, i2, isPjListTypeBusiness);
                        continue;
                    case 4:
                    case 6:
                        if (this._bUINotInterrupt && !pjInfo.isEnableModerator()) {
                            i2 = notifyAndRemovePjInfo(pjInfo, 4, i2, isPjListTypeBusiness);
                            continue;
                        } else if (i2 == 0) {
                            i2++;
                            i = 4;
                        } else {
                            i2 = notifyAndRemovePjInfo(pjInfo, 4, i2, isPjListTypeBusiness);
                        }
                        break;
                    case 5:
                    case 7:
                        if (this._bUINotInterrupt && !isEnableModeratorAll()) {
                            i2 = notifyAndRemovePjInfo(pjInfo, 4, i2, isPjListTypeBusiness);
                            continue;
                        } else if (i2 == 0) {
                            i2++;
                            i = 5;
                        } else if (i == 5) {
                            if (!pjInfo.isSameMirroringMPP(this._aSelectPjInfo.get(0).getPjInfo())) {
                                i2 = notifyAndRemovePjInfo(pjInfo, 4, i2, isPjListTypeBusiness);
                            }
                        } else {
                            notifyAndRemovePjInfo(pjInfo, 4, i2, isPjListTypeBusiness);
                        }
                        break;
                }
                i2++;
            }
        } else {
            i = -1;
        }
        if (this._aSelectPjInfo.size() == 0) {
            return -1;
        }
        return i;
    }

    private boolean isEnableModeratorAll() {
        ArrayList<ConnectPjInfo> arrayList = this._aSelectPjInfo;
        if (arrayList == null) {
            return false;
        }
        Iterator<ConnectPjInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            if (it.next().getPjInfo().isEnableModerator()) {
                return true;
            }
        }
        return false;
    }

    public boolean addManualSearchPj(IPjManualSearchResultListener iPjManualSearchResultListener, D_PjInfo d_PjInfo) {
        if (4 == this._aSearchPj.size()) {
            return false;
        }
        this._implManualSearchResult = iPjManualSearchResultListener;
        for (int i = 0; i < this._aSearchPj.size(); i++) {
            if (compareSearchPriority(d_PjInfo, this._aSearchPj.get(i))) {
                try {
                    this._aSearchPj.add(i, d_PjInfo);
                    return true;
                } catch (IndexOutOfBoundsException unused) {
                    return false;
                }
            }
        }
        this._aSearchPj.add(d_PjInfo);
        return true;
    }

    private boolean compareSearchPriority(D_PjInfo d_PjInfo, D_PjInfo d_PjInfo2) {
        boolean IsEmptyUniqInfo = Tool.IsEmptyUniqInfo(d_PjInfo.UniqInfo);
        boolean IsEmptyIPAddr = Tool.IsEmptyIPAddr(d_PjInfo.IPAddr);
        boolean isEmpty = d_PjInfo.PrjName.isEmpty();
        boolean IsEmptyUniqInfo2 = Tool.IsEmptyUniqInfo(d_PjInfo2.UniqInfo);
        boolean IsEmptyIPAddr2 = Tool.IsEmptyIPAddr(d_PjInfo2.IPAddr);
        boolean isEmpty2 = d_PjInfo2.PrjName.isEmpty();
        if (!IsEmptyUniqInfo || !IsEmptyUniqInfo2) {
            if (IsEmptyUniqInfo) {
                return false;
            }
            if (IsEmptyUniqInfo2) {
                return true;
            }
            return (isEmpty && isEmpty2) || !isEmpty;
        } else if (isEmpty && isEmpty2) {
            return true;
        } else {
            if (isEmpty) {
                return false;
            }
            if (isEmpty2) {
                return true;
            }
            return (IsEmptyIPAddr && IsEmptyIPAddr2) || !IsEmptyIPAddr;
        }
    }

    public void deleteManualSearchPj(D_PjInfo d_PjInfo) {
        for (int i = 0; i < this._aSearchPj.size(); i++) {
            D_PjInfo d_PjInfo2 = this._aSearchPj.get(i);
            if (d_PjInfo2.ProjectorID == d_PjInfo.ProjectorID) {
                if (d_PjInfo2.ProjectorID == -1) {
                    if (Arrays.equals(d_PjInfo2.IPAddr, d_PjInfo.IPAddr) && d_PjInfo2.PrjName.equals(d_PjInfo.PrjName) && Arrays.equals(d_PjInfo2.UniqInfo, d_PjInfo.UniqInfo)) {
                        Lg.d("remove inf projector id is -1");
                        this._aSearchPj.remove(i);
                        return;
                    }
                } else {
                    Lg.d("remove inf");
                    this._aSearchPj.remove(i);
                    return;
                }
            }
        }
    }

    public void clearManualSearchPj() {
        this._aSearchPj.clear();
        if (this._engine.IsJNI() || this._pjList != null) {
            this._pjList.clearAllCurrentManualFoundFlag();
            this._pjList.clearAllManualFoundFlag();
        }
    }

    public boolean isManualSearchPj(D_PjInfo d_PjInfo) {
        Iterator<D_PjInfo> it = this._aSearchPj.iterator();
        while (it.hasNext()) {
            D_PjInfo next = it.next();
            if (Arrays.equals(d_PjInfo.IPAddr, next.IPAddr) && Arrays.equals(d_PjInfo.UniqInfo, next.UniqInfo) && d_PjInfo.PrjName.equals(next.PrjName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasManualSearchPj() {
        return this._aSearchPj.size() > 0;
    }

    public int startManualSearchPj(IPjManualSearchResultListener iPjManualSearchResultListener) {
        if (iPjManualSearchResultListener != null) {
            this._implManualSearchResult = iPjManualSearchResultListener;
        }
        if (this._aSearchPj.size() == 0) {
            return -1;
        }
        boolean z = false;
        if (this._engine.IsJNI()) {
            this._pjList.clearAllCurrentManualFoundFlag();
            Iterator<D_PjInfo> it = this._aManualSearchedPj.iterator();
            while (it.hasNext()) {
                it.next().bCurrentManualFound = false;
            }
        }
        this._engine.ClearCurrentManualSearchFlag();
        Iterator<D_PjInfo> it2 = this._aSearchPj.iterator();
        while (it2.hasNext()) {
            D_PjInfo next = it2.next();
            D_AddFixedSearchInfo d_AddFixedSearchInfo = new D_AddFixedSearchInfo();
            D_AddPjInfo d_AddPjInfo = new D_AddPjInfo();
            if (!Tool.IsEmptyIPAddr(next.IPAddr)) {
                d_AddFixedSearchInfo.ipAddress = next.IPAddr;
            }
            if (!next.PrjName.isEmpty()) {
                d_AddFixedSearchInfo.prjName = next.PrjName;
            }
            if (!Tool.IsEmptyUniqInfo(next.UniqInfo)) {
                d_AddFixedSearchInfo.uniqInfo = next.UniqInfo;
            }
            d_AddFixedSearchInfo.bManualSearch = true;
            d_AddFixedSearchInfo.bCurrentManualSearch = true;
            int AddSearchProjector = this._engine.AddSearchProjector(d_AddFixedSearchInfo, d_AddPjInfo);
            if (AddSearchProjector != -204 && AddSearchProjector != 0) {
                Lg.w("検索情報の追加に失敗しました。 Ret:" + AddSearchProjector);
                return AddSearchProjector;
            }
            if (this._engine.IsJNI()) {
                addManualSearchedPj(next);
            }
            z = true;
        }
        if (!z) {
            this._implManualSearchResult.onFindSearchPj(null, true);
            return -1;
        }
        return genericSearch(1, this);
    }

    private void addManualSearchedPj(D_PjInfo d_PjInfo) {
        if (this._engine.IsJNI()) {
            d_PjInfo.bCurrentManualFound = true;
        }
        if (!this._aManualSearchedPj.isEmpty()) {
            Iterator<D_PjInfo> it = this._aManualSearchedPj.iterator();
            while (it.hasNext()) {
                D_PjInfo next = it.next();
                if (isSameSearchedPj(d_PjInfo, next)) {
                    if (this._engine.IsJNI()) {
                        next.bCurrentManualFound = true;
                        return;
                    }
                    return;
                }
            }
        }
        this._aManualSearchedPj.add(d_PjInfo);
    }

    private void addManualSearchedIP(String str) {
        D_PjInfo d_PjInfo = new D_PjInfo();
        d_PjInfo.IPAddr = NetUtils.convertIpStringToBytes(str);
        if (this._engine.IsJNI()) {
            d_PjInfo.bCurrentManualFound = true;
        }
        if (!this._aManualSearchedPj.isEmpty()) {
            Iterator<D_PjInfo> it = this._aManualSearchedPj.iterator();
            while (it.hasNext()) {
                D_PjInfo next = it.next();
                if (isSameSearchedPj(d_PjInfo, next)) {
                    if (this._engine.IsJNI()) {
                        next.bCurrentManualFound = true;
                        return;
                    }
                    return;
                }
            }
        }
        this._aManualSearchedPj.add(d_PjInfo);
    }

    private boolean isManualSearchedPj(D_PjInfo d_PjInfo) {
        Iterator<D_PjInfo> it = this._aManualSearchedPj.iterator();
        while (it.hasNext()) {
            D_PjInfo next = it.next();
            if (isWishPj(d_PjInfo, next)) {
                if (this._engine.IsJNI() && next.bCurrentManualFound) {
                    d_PjInfo.bCurrentManualFound = true;
                }
                return true;
            }
        }
        return false;
    }

    private boolean isSameSearchedPj(D_PjInfo d_PjInfo, D_PjInfo d_PjInfo2) {
        boolean isEmpty = d_PjInfo.PrjName.isEmpty();
        boolean IsEmptyIPAddr = Tool.IsEmptyIPAddr(d_PjInfo.IPAddr);
        boolean IsEmptyUniqInfo = Tool.IsEmptyUniqInfo(d_PjInfo.UniqInfo);
        if (isEmpty == d_PjInfo2.PrjName.isEmpty() && IsEmptyIPAddr == Tool.IsEmptyIPAddr(d_PjInfo2.IPAddr) && IsEmptyUniqInfo == Tool.IsEmptyUniqInfo(d_PjInfo2.UniqInfo)) {
            if (IsEmptyUniqInfo || Arrays.equals(d_PjInfo.UniqInfo, d_PjInfo2.UniqInfo)) {
                if (isEmpty || d_PjInfo.PrjName.equals(d_PjInfo2.PrjName)) {
                    if (IsEmptyIPAddr) {
                        return true;
                    }
                    return Arrays.equals(d_PjInfo.IPAddr, d_PjInfo2.IPAddr);
                }
                return false;
            }
            return false;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:59:0x00ba, code lost:
        if (r2 != 7) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00cd, code lost:
        if (r8._aSelectPjInfo.get(0).getPjInfo().isSameMirroringMPP(r1) == false) goto L64;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean addSelectPj(com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo r9) {
        /*
            Method dump skipped, instructions count: 226
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.epson.iprojection.ui.engine_wrapper.PjFinder.addSelectPj(com.epson.iprojection.ui.engine_wrapper.ConnectPjInfo):boolean");
    }

    private void addAndUpdateSelectPj(ConnectPjInfo connectPjInfo) {
        this._aSelectPjInfo.add(connectPjInfo);
        updatePjList(Pj.getIns().isRegistered() ? false : this._aSelectPjInfo.size() == 1);
        Lg.i("added inf");
    }

    public void removeSelectPj(ConnectPjInfo connectPjInfo) {
        if (this._aSelectPjInfo == null) {
            Lg.w("always no select");
        } else if (connectPjInfo == null || connectPjInfo.getPjInfo() == null) {
            Lg.w("inf is null");
        } else {
            boolean isAllPjTypeBusinessSelectHome = Pj.getIns().isAllPjTypeBusinessSelectHome();
            D_PjInfo pjInfo = connectPjInfo.getPjInfo();
            int i = 0;
            while (true) {
                if (i >= this._aSelectPjInfo.size()) {
                    break;
                }
                D_PjInfo pjInfo2 = this._aSelectPjInfo.get(i).getPjInfo();
                if (pjInfo2.ProjectorID == pjInfo.ProjectorID) {
                    if (pjInfo2.ProjectorID == -1) {
                        if (Arrays.equals(pjInfo2.IPAddr, pjInfo.IPAddr)) {
                            this._aSelectPjInfo.remove(i);
                            break;
                        }
                    } else {
                        this._aSelectPjInfo.remove(i);
                        break;
                    }
                }
                i++;
            }
            if (isAllPjTypeBusinessSelectHome && pjInfo.isMppMirror()) {
                for (int size = this._aSelectPjInfo.size() - 1; size >= 0; size--) {
                    D_PjInfo pjInfo3 = this._aSelectPjInfo.get(size).getPjInfo();
                    if (pjInfo3.isMppMirror() && pjInfo.isSameMirroringMPP(pjInfo3)) {
                        this._aSelectPjInfo.remove(size);
                    }
                }
            }
            updatePjList();
        }
    }

    public void removeAllSelectPJ() {
        this._aSelectPjInfo.clear();
        updatePjList();
    }

    public void mergeSelectPjList() {
        mergeSelectPjListNoMirroring();
        if (Pj.getIns().isAllPjTypeBusiness()) {
            ConnectPjInfo connectPjInfo = this._aSelectPjInfo.get(0);
            if (connectPjInfo.getPjInfo().isMppMirror()) {
                boolean[] zArr = {false, false, false, false};
                for (int i = 0; i < connectPjInfo.getPjInfo().aMirrorPjList.size(); i++) {
                    D_MirrorPjInfo d_MirrorPjInfo = connectPjInfo.getPjInfo().aMirrorPjList.get(i);
                    zArr[i] = true;
                    int i2 = 0;
                    while (true) {
                        if (i2 < this._aSelectPjInfo.size()) {
                            D_PjInfo pjInfo = this._aSelectPjInfo.get(i2).getPjInfo();
                            if (Arrays.equals(d_MirrorPjInfo.uniqInfo, pjInfo.UniqInfo) && d_MirrorPjInfo.prjName.equals(pjInfo.PrjName)) {
                                zArr[i] = false;
                                break;
                            }
                            i2++;
                        }
                    }
                }
                for (int i3 = 0; i3 < connectPjInfo.getPjInfo().aMirrorPjList.size(); i3++) {
                    if (zArr[i3]) {
                        D_MirrorPjInfo d_MirrorPjInfo2 = connectPjInfo.getPjInfo().aMirrorPjList.get(i3);
                        D_PjInfo d_PjInfo = new D_PjInfo();
                        System.arraycopy(d_MirrorPjInfo2.ipAddress, 0, d_PjInfo.IPAddr, 0, d_PjInfo.IPAddr.length);
                        d_PjInfo.PrjName = d_MirrorPjInfo2.prjName;
                        this._aSelectPjInfo.add(new ConnectPjInfo(d_PjInfo, false));
                    }
                }
            }
        }
    }

    public void mergeSelectPjListNoMirroring() {
        boolean[] zArr = new boolean[16];
        Arrays.fill(zArr, false);
        ArrayList<ConnectPjInfo> arrayList = this._aSelectPjInfo;
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        if (1 < this._aSelectPjInfo.size()) {
            int i = 0;
            while (i < this._aSelectPjInfo.size() - 1) {
                ConnectPjInfo connectPjInfo = this._aSelectPjInfo.get(i);
                int i2 = i + 1;
                for (int i3 = i2; i3 < this._aSelectPjInfo.size(); i3++) {
                    ConnectPjInfo connectPjInfo2 = this._aSelectPjInfo.get(i3);
                    if (connectPjInfo.getPjInfo().IPAddr.equals(connectPjInfo2.getPjInfo().IPAddr)) {
                        if (hasUniqueInfo(connectPjInfo)) {
                            zArr[i3] = true;
                        } else if (hasUniqueInfo(connectPjInfo2)) {
                            zArr[i] = true;
                        } else {
                            zArr[i3] = true;
                        }
                    }
                    if (Arrays.equals(connectPjInfo.getPjInfo().UniqInfo, connectPjInfo2.getPjInfo().UniqInfo)) {
                        zArr[i3] = true;
                    }
                }
                i = i2;
            }
        }
        for (int size = this._aSelectPjInfo.size() - 1; size >= 0; size--) {
            if (zArr[size]) {
                this._aSelectPjInfo.remove(size);
            }
        }
        for (int i4 = 0; i4 < this._aSelectPjInfo.size(); i4++) {
            ConnectPjInfo connectPjInfo3 = this._aSelectPjInfo.get(i4);
            if (!hasUniqueInfo(connectPjInfo3)) {
                String BinarryArrayToStringIPAddr = Tool.BinarryArrayToStringIPAddr(connectPjInfo3.getPjInfo().IPAddr);
                Lg.d("no unique info ip:" + BinarryArrayToStringIPAddr);
                D_PjInfo pJByIP = getPJByIP(BinarryArrayToStringIPAddr);
                if (pJByIP != null) {
                    Lg.d("set PJ info projector ID:" + pJByIP.ProjectorID);
                    connectPjInfo3.setPjInfo(pJByIP);
                }
            }
        }
    }

    public ArrayList<ConnectPjInfo> getSelectPjList() {
        return this._aSelectPjInfo;
    }

    public int getSelectPjNum() {
        ArrayList<ConnectPjInfo> arrayList = this._aSelectPjInfo;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    private boolean hasUniqueInfo(ConnectPjInfo connectPjInfo) {
        return !Arrays.equals(connectPjInfo.getPjInfo().UniqInfo, D_PjInfo.e_null_unique_info);
    }

    private boolean isDisableCombination(ConnectPjInfo connectPjInfo, ArrayList<ConnectPjInfo> arrayList) {
        if (arrayList != null && arrayList.size() != 0) {
            if (connectPjInfo.getPjInfo().isNotSupportNP) {
                Iterator<ConnectPjInfo> it = arrayList.iterator();
                while (it.hasNext()) {
                    if (!it.next().getPjInfo().bSupportedMPP) {
                        return true;
                    }
                }
            } else if (!connectPjInfo.getPjInfo().bSupportedMPP) {
                Iterator<ConnectPjInfo> it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    if (it2.next().getPjInfo().isNotSupportNP) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean addConnPjFromHistory(ConnectPjInfo connectPjInfo) {
        if (4 == this._aSelectPjFromHistory.size()) {
            return false;
        }
        this._aSelectPjFromHistory.add(connectPjInfo);
        return true;
    }

    public void removeConnPjFromHistory(ConnectPjInfo connectPjInfo) {
        for (int i = 0; i < this._aSelectPjFromHistory.size(); i++) {
            if (Arrays.equals(this._aSelectPjFromHistory.get(i).getPjInfo().IPAddr, connectPjInfo.getPjInfo().IPAddr)) {
                this._aSelectPjFromHistory.remove(i);
                return;
            }
        }
    }

    public void clearConnPjFromHistory() {
        this._aSelectPjFromHistory.clear();
    }

    public boolean isConnPjFromHistory(String str, String str2) {
        for (int i = 0; i < this._aSelectPjFromHistory.size(); i++) {
            ConnectPjInfo connectPjInfo = this._aSelectPjFromHistory.get(i);
            if (str2.equals(connectPjInfo.getPjInfo().PrjName) && str.equals(Tool.BinarryArrayToStringIPAddr(connectPjInfo.getPjInfo().IPAddr))) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<ConnectPjInfo> getConnPjFromHistory() {
        return this._aSelectPjFromHistory;
    }

    public int getConnPjNumFromHistory() {
        return this._aSelectPjFromHistory.size();
    }

    private void updateInterruptStatusConnPjFromHistory() {
        ArrayList<ConnectPjInfo> arrayList = this._aSelectPjFromHistory;
        if (arrayList == null) {
            return;
        }
        Iterator<ConnectPjInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().setNoInterrupt(this._bUINotInterrupt);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class SearchLooper implements Runnable {
        private static final int CHECK_WAIT_INTERVAL = 200;
        private static final int SEARCH_INTERVAL = 5000;
        private final int MANUAL_SEARCH_COUNT_FOR_CHROMEOS;
        private boolean _isAvailable;
        private boolean _isNoSearch;
        private boolean _isSearchStartWait;
        private boolean _isSearching;
        private boolean _isWaitSkip;
        private int _searchCounter;
        private final boolean _shouldNotClearManualSearchFlag;

        private SearchLooper() {
            this._isAvailable = true;
            this._isSearching = false;
            this._searchCounter = 0;
            this._isWaitSkip = false;
            this._isNoSearch = false;
            this._isSearchStartWait = false;
            this._shouldNotClearManualSearchFlag = ChromeOSUtils.INSTANCE.isChromeOS(PjFinder.this._context);
            this.MANUAL_SEARCH_COUNT_FOR_CHROMEOS = 2;
        }

        @Override // java.lang.Runnable
        public void run() {
            long j;
            Lg.d("---->run");
            while (this._isAvailable) {
                try {
                    if (!this._isSearching) {
                        if (PjFinder.this._isLinkageDataSearching && !PjFinder.this.isEnableLinkageDataSearching()) {
                            Sleeper.sleep(200L);
                        } else {
                            this._searchCounter++;
                            addSearchPjForChromeOS();
                            search();
                            while (this._isSearchStartWait) {
                                Sleeper.sleep(50L);
                            }
                        }
                    }
                    while (this._isSearching) {
                        if (!this._isAvailable) {
                            throw new DoubleLoopBreakException();
                        }
                        Sleeper.sleep(200L);
                    }
                    deleteSearchPjForChromeOS();
                    long j2 = 5000;
                    while (true) {
                        if (j2 > 0) {
                            if (!this._isAvailable) {
                                throw new DoubleLoopBreakException();
                            }
                            if (j2 > 200) {
                                j = j2 - 200;
                                j2 = 200;
                            } else {
                                j = 0;
                            }
                            Sleeper.sleep(j2);
                            if (PjFinder.this._isLinkageDataSearching) {
                                j = 0;
                            }
                            if (this._isWaitSkip) {
                                setWaitSkip(false);
                                j2 = 0;
                            } else {
                                j2 = j;
                            }
                        }
                    }
                } catch (DoubleLoopBreakException unused) {
                    Lg.d("<----run end");
                    return;
                }
            }
            throw new DoubleLoopBreakException();
        }

        private void addSearchPjForChromeOS() {
            if (ChromeOSUtils.INSTANCE.isChromeOS(PjFinder.this._context) && this._searchCounter == 2) {
                Lg.d("ChromeOSのために192.168.88.1を指定検索対象に追加します");
                PjFinder.this._engine.ClearManualSearchFlag();
                D_AddFixedSearchInfo d_AddFixedSearchInfo = new D_AddFixedSearchInfo();
                d_AddFixedSearchInfo.ipAddress = NetUtils.convertIpStringToBytes(CommonDefine.EASYMODE_PJIP);
                PjFinder.this._engine.AddSearchProjector(d_AddFixedSearchInfo, new D_AddPjInfo());
            }
        }

        private void deleteSearchPjForChromeOS() {
            if (ChromeOSUtils.INSTANCE.isChromeOS(PjFinder.this._context) && this._searchCounter == 2) {
                PjFinder.this._engine.ClearManualSearchFlag();
            }
        }

        /* loaded from: classes.dex */
        private class DoubleLoopBreakException extends Exception {
            private DoubleLoopBreakException() {
            }
        }

        public synchronized void setWaitSkip(boolean z) {
            this._isWaitSkip = z;
        }

        public boolean isRunning() {
            return this._isAvailable || this._isSearching;
        }

        public void disable() {
            this._isAvailable = false;
        }

        public void enable() {
            this._isAvailable = true;
        }

        public synchronized void setIsSearching(boolean z) {
            this._isSearching = z;
        }

        public synchronized void setNoSearchState(boolean z) {
            this._isNoSearch = z;
        }

        private void search() {
            this._isSearchStartWait = true;
            PjFinder.this._handler.post(new Runnable() { // from class: com.epson.iprojection.ui.engine_wrapper.PjFinder$SearchLooper$$ExternalSyntheticLambda0
                {
                    PjFinder.SearchLooper.this = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    PjFinder.SearchLooper.this.m212xb0547cc5();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$search$0$com-epson-iprojection-ui-engine_wrapper-PjFinder$SearchLooper  reason: not valid java name */
        public /* synthetic */ void m212xb0547cc5() {
            setIsSearching(true);
            if (!this._isNoSearch) {
                PjFinder.this.restartSearch(this._shouldNotClearManualSearchFlag);
            }
            this._isSearchStartWait = false;
        }
    }

    public void startUpdatePjStatusOfRegisted() {
        PjStatusOfRegistedUpdateThread pjStatusOfRegistedUpdateThread = this._statusUpdateThread;
        if (pjStatusOfRegistedUpdateThread != null) {
            pjStatusOfRegistedUpdateThread.finish();
        }
        PjStatusOfRegistedUpdateThread pjStatusOfRegistedUpdateThread2 = new PjStatusOfRegistedUpdateThread(this._context);
        this._statusUpdateThread = pjStatusOfRegistedUpdateThread2;
        pjStatusOfRegistedUpdateThread2.start();
    }

    public void stopUpdatePjStatusOfRegisted() {
        PjStatusOfRegistedUpdateThread pjStatusOfRegistedUpdateThread = this._statusUpdateThread;
        if (pjStatusOfRegistedUpdateThread != null) {
            pjStatusOfRegistedUpdateThread.finish();
        }
    }
}
