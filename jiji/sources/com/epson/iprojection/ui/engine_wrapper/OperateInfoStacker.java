package com.epson.iprojection.ui.engine_wrapper;

import com.epson.iprojection.common.Lg;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class OperateInfoStacker {
    private Type _type = Type.Nothing;
    private ArrayList<ConnectPjInfo> _inf = null;
    private final String _keyword = null;
    private DisconReason _reason = DisconReason.NoSet;

    /* loaded from: classes.dex */
    public enum Type {
        Nothing,
        Connect,
        Disconnect
    }

    public void setConnectPjInfo(ConnectPjInfo connectPjInfo) {
        if (this._type != Type.Nothing) {
            Lg.w("仕事が二重に登録されました");
        }
        set(connectPjInfo, DisconReason.NoSet);
        this._type = Type.Connect;
    }

    public void setConnectPjInfo(ArrayList<ConnectPjInfo> arrayList) {
        if (this._type != Type.Nothing) {
            Lg.w("仕事が二重に登録されました");
        }
        set(arrayList, DisconReason.NoSet);
        this._type = Type.Connect;
    }

    public void setDisconInfo(DisconReason disconReason) {
        if (this._type != Type.Nothing) {
            Lg.w("仕事が二重に登録されました");
        }
        clear();
        this._type = Type.Disconnect;
        this._reason = disconReason;
    }

    public boolean isConnectInfo() {
        return this._type == Type.Connect;
    }

    public boolean isDisconnectInfo() {
        return this._type == Type.Disconnect;
    }

    public ConnectPjInfo getConnectPjInfo() {
        ArrayList<ConnectPjInfo> arrayList = this._inf;
        if (arrayList == null) {
            return null;
        }
        return arrayList.get(0);
    }

    public ArrayList<ConnectPjInfo> getConnectPjArray() {
        return this._inf;
    }

    public String getKeyword() {
        return this._keyword;
    }

    public DisconReason getDisconReason() {
        return this._reason;
    }

    public void clear() {
        set((ArrayList<ConnectPjInfo>) null, DisconReason.NoSet);
        this._type = Type.Nothing;
    }

    private void set(ConnectPjInfo connectPjInfo, DisconReason disconReason) {
        if (connectPjInfo == null) {
            this._inf = null;
        } else {
            ArrayList<ConnectPjInfo> arrayList = new ArrayList<>();
            this._inf = arrayList;
            arrayList.add(connectPjInfo);
        }
        this._reason = disconReason;
    }

    private void set(ArrayList<ConnectPjInfo> arrayList, DisconReason disconReason) {
        this._inf = arrayList;
        this._reason = disconReason;
    }
}
