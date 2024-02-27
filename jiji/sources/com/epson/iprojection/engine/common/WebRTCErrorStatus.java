package com.epson.iprojection.engine.common;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;

/* compiled from: WebRTCErrorStatus.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/epson/iprojection/engine/common/WebRTCErrorStatus;", "", "()V", "answerSDPParseFailed", "", "getOfferSDPError", "otherError", "startThreadFailed", FirebaseAnalytics.Param.SUCCESS, "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class WebRTCErrorStatus {
    public static final WebRTCErrorStatus INSTANCE = new WebRTCErrorStatus();
    public static final int answerSDPParseFailed = -4;
    public static final int getOfferSDPError = -2;
    public static final int otherError = -1;
    public static final int startThreadFailed = -3;
    public static final int success = 0;

    private WebRTCErrorStatus() {
    }
}
