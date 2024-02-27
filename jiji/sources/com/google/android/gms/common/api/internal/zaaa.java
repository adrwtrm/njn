package com.google.android.gms.common.api.internal;

import android.util.Log;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zaaa implements OnCompleteListener<Map<zai<?>, String>> {
    private final /* synthetic */ zax zafi;
    private SignInConnectionListener zafj;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zaaa(zax zaxVar, SignInConnectionListener signInConnectionListener) {
        this.zafi = zaxVar;
        this.zafj = signInConnectionListener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void cancel() {
        this.zafj.onComplete();
    }

    @Override // com.google.android.gms.tasks.OnCompleteListener
    public final void onComplete(Task<Map<zai<?>, String>> task) {
        Lock lock;
        Lock lock2;
        boolean z;
        boolean z2;
        Map map;
        Map map2;
        boolean zaa;
        Map map3;
        Map map4;
        Map map5;
        Map map6;
        ConnectionResult zaaf;
        Condition condition;
        Map map7;
        Map map8;
        Map map9;
        lock = this.zafi.zaeo;
        lock.lock();
        try {
            z = this.zafi.zafd;
            if (!z) {
                this.zafj.onComplete();
                return;
            }
            if (task.isSuccessful()) {
                zax zaxVar = this.zafi;
                map7 = this.zafi.zaev;
                zaxVar.zaff = new ArrayMap(map7.size());
                map8 = this.zafi.zaev;
                for (zaw zawVar : map8.values()) {
                    map9 = this.zafi.zaff;
                    map9.put(zawVar.zak(), ConnectionResult.RESULT_SUCCESS);
                }
            } else if (task.getException() instanceof AvailabilityException) {
                AvailabilityException availabilityException = (AvailabilityException) task.getException();
                z2 = this.zafi.zafb;
                if (z2) {
                    zax zaxVar2 = this.zafi;
                    map = this.zafi.zaev;
                    zaxVar2.zaff = new ArrayMap(map.size());
                    map2 = this.zafi.zaev;
                    for (zaw zawVar2 : map2.values()) {
                        Object zak = zawVar2.zak();
                        ConnectionResult connectionResult = availabilityException.getConnectionResult(zawVar2);
                        zaa = this.zafi.zaa(zawVar2, connectionResult);
                        if (zaa) {
                            map3 = this.zafi.zaff;
                            map3.put(zak, new ConnectionResult(16));
                        } else {
                            map4 = this.zafi.zaff;
                            map4.put(zak, connectionResult);
                        }
                    }
                } else {
                    this.zafi.zaff = availabilityException.zaj();
                }
            } else {
                Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                this.zafi.zaff = Collections.emptyMap();
            }
            if (this.zafi.isConnected()) {
                map5 = this.zafi.zafe;
                map6 = this.zafi.zaff;
                map5.putAll(map6);
                zaaf = this.zafi.zaaf();
                if (zaaf == null) {
                    this.zafi.zaad();
                    this.zafi.zaae();
                    condition = this.zafi.zaez;
                    condition.signalAll();
                }
            }
            this.zafj.onComplete();
        } finally {
            lock2 = this.zafi.zaeo;
            lock2.unlock();
        }
    }
}
