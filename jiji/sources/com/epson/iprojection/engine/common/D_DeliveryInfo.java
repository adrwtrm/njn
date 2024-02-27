package com.epson.iprojection.engine.common;

import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class D_DeliveryInfo {
    public static final int e_DeliveryCommand_ChangeParmission = 2;
    public static final int e_DeliveryCommand_DeliveryEncryptionModeDisable = 7;
    public static final int e_DeliveryCommand_DeliveryEncryptionModeEnable = 6;
    public static final int e_DeliveryCommand_DeliveryModeMulticast = 5;
    public static final int e_DeliveryCommand_DeliveryModeUnicast = 4;
    public static final int e_DeliveryCommand_DeliveryWhitePaper = 1;
    public static final int e_DeliveryCommand_FinishDelivery = 3;
    public static final int e_DeliveryCommand_ReceivedData = 8;
    public static final int e_DeliveryCommand_RequestDelivery = 0;
    public ByteBuffer buffer;
    public int bufferSize;
    public int command;
    public boolean enableChangeUI;
    public boolean enableSave;
    public boolean enableWirte;
    public boolean validControlParmission;
    public short whitePaperHeight;
    public short whitePaperWidth;

    public D_DeliveryInfo() {
        this.buffer = null;
    }

    public D_DeliveryInfo(D_DeliveryInfo d_DeliveryInfo) {
        this.buffer = null;
        this.command = d_DeliveryInfo.command;
        this.validControlParmission = d_DeliveryInfo.validControlParmission;
        this.enableWirte = d_DeliveryInfo.enableWirte;
        this.enableSave = d_DeliveryInfo.enableSave;
        this.enableChangeUI = d_DeliveryInfo.enableChangeUI;
        this.whitePaperWidth = d_DeliveryInfo.whitePaperWidth;
        this.whitePaperHeight = d_DeliveryInfo.whitePaperHeight;
        this.bufferSize = d_DeliveryInfo.bufferSize;
        ByteBuffer byteBuffer = d_DeliveryInfo.buffer;
        if (byteBuffer != null) {
            ByteBuffer allocate = ByteBuffer.allocate(byteBuffer.limit());
            this.buffer = allocate;
            allocate.clear();
            this.buffer.position(0);
            d_DeliveryInfo.buffer.position(0);
            this.buffer.put(d_DeliveryInfo.buffer);
            this.buffer.position(0);
        }
    }
}
