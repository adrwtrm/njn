package com.google.android.gms.common.api.internal;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zaan extends zaau {
    final /* synthetic */ zaak zagj;
    private final Map<Api.Client, zaam> zagl;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaan(zaak zaakVar, Map<Api.Client, zaam> map) {
        super(zaakVar, null);
        this.zagj = zaakVar;
        this.zagl = map;
    }

    @Override // com.google.android.gms.common.api.internal.zaau
    public final void zaan() {
        GoogleApiAvailabilityLight googleApiAvailabilityLight;
        Context context;
        boolean z;
        Context context2;
        zabe zabeVar;
        com.google.android.gms.signin.zad zadVar;
        com.google.android.gms.signin.zad zadVar2;
        zabe zabeVar2;
        Context context3;
        boolean z2;
        googleApiAvailabilityLight = this.zagj.zaey;
        GoogleApiAvailabilityCache googleApiAvailabilityCache = new GoogleApiAvailabilityCache(googleApiAvailabilityLight);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Api.Client client : this.zagl.keySet()) {
            if (client.requiresGooglePlayServices()) {
                z2 = this.zagl.get(client).zaec;
                if (!z2) {
                    arrayList.add(client);
                }
            }
            arrayList2.add(client);
        }
        int i = 0;
        int i2 = -1;
        if (arrayList.isEmpty()) {
            int size = arrayList2.size();
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                context3 = this.zagj.mContext;
                i2 = googleApiAvailabilityCache.getClientAvailability(context3, (Api.Client) obj);
                if (i2 == 0) {
                    break;
                }
            }
        } else {
            int size2 = arrayList.size();
            while (i < size2) {
                Object obj2 = arrayList.get(i);
                i++;
                context = this.zagj.mContext;
                i2 = googleApiAvailabilityCache.getClientAvailability(context, (Api.Client) obj2);
                if (i2 != 0) {
                    break;
                }
            }
        }
        if (i2 != 0) {
            ConnectionResult connectionResult = new ConnectionResult(i2, null);
            zabeVar2 = this.zagj.zaft;
            zabeVar2.zaa(new zaao(this, this.zagj, connectionResult));
            return;
        }
        z = this.zagj.zagd;
        if (z) {
            zadVar = this.zagj.zagb;
            if (zadVar != null) {
                zadVar2 = this.zagj.zagb;
                zadVar2.connect();
            }
        }
        for (Api.Client client2 : this.zagl.keySet()) {
            zaam zaamVar = this.zagl.get(client2);
            if (client2.requiresGooglePlayServices()) {
                context2 = this.zagj.mContext;
                if (googleApiAvailabilityCache.getClientAvailability(context2, client2) != 0) {
                    zabeVar = this.zagj.zaft;
                    zabeVar.zaa(new zaap(this, this.zagj, zaamVar));
                }
            }
            client2.connect(zaamVar);
        }
    }
}
