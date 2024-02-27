package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zaco extends com.google.android.gms.internal.base.zap {
    private final /* synthetic */ zacm zakw;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaco(zacm zacmVar, Looper looper) {
        super(looper);
        this.zakw = zacmVar;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        Object obj;
        zacm zacmVar;
        zacm zacmVar2;
        zacm zacmVar3;
        int i = message.what;
        if (i != 0) {
            if (i != 1) {
                Log.e("TransformedResultImpl", new StringBuilder(70).append("TransformationResultHandler received unknown message type: ").append(message.what).toString());
                return;
            }
            RuntimeException runtimeException = (RuntimeException) message.obj;
            String valueOf = String.valueOf(runtimeException.getMessage());
            Log.e("TransformedResultImpl", valueOf.length() != 0 ? "Runtime exception on the transformation worker thread: ".concat(valueOf) : new String("Runtime exception on the transformation worker thread: "));
            throw runtimeException;
        }
        PendingResult<?> pendingResult = (PendingResult) message.obj;
        obj = this.zakw.zado;
        synchronized (obj) {
            if (pendingResult != null) {
                if (pendingResult instanceof zacd) {
                    zacmVar2 = this.zakw.zakp;
                    zacmVar2.zad(((zacd) pendingResult).getStatus());
                } else {
                    zacmVar = this.zakw.zakp;
                    zacmVar.zaa(pendingResult);
                }
            } else {
                zacmVar3 = this.zakw.zakp;
                zacmVar3.zad(new Status(13, "Transform returned null"));
            }
        }
    }
}
