package com.google.android.gms.common.server.response;

import com.google.android.gms.common.server.response.FastParser;
import java.io.BufferedReader;
import java.io.IOException;

/* loaded from: classes.dex */
final class zac implements FastParser.zaa<Float> {
    @Override // com.google.android.gms.common.server.response.FastParser.zaa
    public final /* synthetic */ Float zah(FastParser fastParser, BufferedReader bufferedReader) throws FastParser.ParseException, IOException {
        float zag;
        zag = fastParser.zag(bufferedReader);
        return Float.valueOf(zag);
    }
}
