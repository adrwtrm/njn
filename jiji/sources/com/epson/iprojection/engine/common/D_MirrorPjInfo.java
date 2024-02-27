package com.epson.iprojection.engine.common;

import java.io.Serializable;
import java.util.Arrays;

/* loaded from: classes.dex */
public class D_MirrorPjInfo implements Serializable {
    public static final int ECON_IPADDRESS_SIZE = 4;
    public static final int ECON_UNIQINFO_LENGTH = 6;
    public String prjName = null;
    public byte[] ipAddress = new byte[4];
    public byte[] uniqInfo = new byte[6];

    public boolean isSamePjInfo(D_PjInfo d_PjInfo) {
        return Arrays.equals(this.ipAddress, d_PjInfo.IPAddr) && this.prjName.equals(d_PjInfo.PrjName) && Arrays.equals(this.uniqInfo, d_PjInfo.UniqInfo);
    }
}
