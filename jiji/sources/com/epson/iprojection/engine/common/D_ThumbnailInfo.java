package com.epson.iprojection.engine.common;

import com.epson.iprojection.ui.engine_wrapper.Pj;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class D_ThumbnailInfo {
    public int bufferSize;
    public int thumbnailHeight;
    public int thumbnailWidth;
    public long userUniqueId;
    public ByteBuffer buffer = null;
    public byte[] bufByte = null;
    public boolean disconnected = false;

    public String getUserName() {
        return Pj.getIns().getMppUserNameByUniqueID(this.userUniqueId);
    }
}
