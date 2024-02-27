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
public final class zaz implements OnCompleteListener<Map<zai<?>, String>> {
    private final /* synthetic */ zax zafi;

    private zaz(zax zaxVar) {
        this.zafi = zaxVar;
    }

    @Override // com.google.android.gms.tasks.OnCompleteListener
    public final void onComplete(Task<Map<zai<?>, String>> task) {
        Lock lock;
        Lock lock2;
        boolean z;
        boolean z2;
        ConnectionResult zaaf;
        Map map;
        Map map2;
        boolean zaa;
        Map map3;
        Map map4;
        Map map5;
        ConnectionResult connectionResult;
        zaaw zaawVar;
        ConnectionResult connectionResult2;
        Condition condition;
        Map map6;
        Map map7;
        ConnectionResult zaaf2;
        Map map8;
        Map map9;
        Map map10;
        lock = this.zafi.zaeo;
        lock.lock();
        try {
            z = this.zafi.zafd;
            if (z) {
                if (task.isSuccessful()) {
                    zax zaxVar = this.zafi;
                    map8 = this.zafi.zaeu;
                    zaxVar.zafe = new ArrayMap(map8.size());
                    map9 = this.zafi.zaeu;
                    for (zaw zawVar : map9.values()) {
                        map10 = this.zafi.zafe;
                        map10.put(zawVar.zak(), ConnectionResult.RESULT_SUCCESS);
                    }
                } else if (task.getException() instanceof AvailabilityException) {
                    AvailabilityException availabilityException = (AvailabilityException) task.getException();
                    z2 = this.zafi.zafb;
                    if (z2) {
                        zax zaxVar2 = this.zafi;
                        map = this.zafi.zaeu;
                        zaxVar2.zafe = new ArrayMap(map.size());
                        map2 = this.zafi.zaeu;
                        for (zaw zawVar2 : map2.values()) {
                            Object zak = zawVar2.zak();
                            ConnectionResult connectionResult3 = availabilityException.getConnectionResult(zawVar2);
                            zaa = this.zafi.zaa(zawVar2, connectionResult3);
                            if (zaa) {
                                map3 = this.zafi.zafe;
                                map3.put(zak, new ConnectionResult(16));
                            } else {
                                map4 = this.zafi.zafe;
                                map4.put(zak, connectionResult3);
                            }
                        }
                    } else {
                        this.zafi.zafe = availabilityException.zaj();
                    }
                    zax zaxVar3 = this.zafi;
                    zaaf = zaxVar3.zaaf();
                    zaxVar3.zafh = zaaf;
                } else {
                    Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                    this.zafi.zafe = Collections.emptyMap();
                    this.zafi.zafh = new ConnectionResult(8);
                }
                map5 = this.zafi.zaff;
                if (map5 != null) {
                    map6 = this.zafi.zafe;
                    map7 = this.zafi.zaff;
                    map6.putAll(map7);
                    zax zaxVar4 = this.zafi;
                    zaaf2 = zaxVar4.zaaf();
                    zaxVar4.zafh = zaaf2;
                }
                connectionResult = this.zafi.zafh;
                if (connectionResult != null) {
                    zax.zaa(this.zafi, false);
                    zaawVar = this.zafi.zaex;
                    connectionResult2 = this.zafi.zafh;
                    zaawVar.zac(connectionResult2);
                } else {
                    this.zafi.zaad();
                    this.zafi.zaae();
                }
                condition = this.zafi.zaez;
                condition.signalAll();
            }
        } finally {
            lock2 = this.zafi.zaeo;
            lock2.unlock();
        }
    }
}
