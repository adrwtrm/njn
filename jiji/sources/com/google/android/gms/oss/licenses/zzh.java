package com.google.android.gms.oss.licenses;

import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;

/* loaded from: classes.dex */
public final class zzh extends GoogleApi<Api.ApiOptions.NoOptions> {
    private static final Api<Api.ApiOptions.NoOptions> API;
    private static final Api.ClientKey<zzn> CLIENT_KEY;
    private static final Api.AbstractClientBuilder<zzn, Api.ApiOptions.NoOptions> zzs;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzh(Context context) {
        super(context, API, (Api.ApiOptions) null, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    static {
        Api.ClientKey<zzn> clientKey = new Api.ClientKey<>();
        CLIENT_KEY = clientKey;
        zzi zziVar = new zzi();
        zzs = zziVar;
        API = new Api<>("OssLicensesService.API", zziVar, clientKey);
    }
}
