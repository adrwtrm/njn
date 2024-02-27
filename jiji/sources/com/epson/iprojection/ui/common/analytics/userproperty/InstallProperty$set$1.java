package com.epson.iprojection.ui.common.analytics.userproperty;

import android.content.Context;
import com.epson.iprojection.ui.common.analytics.userproperty.enums.eUserProperty;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: InstallProperty.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.ui.common.analytics.userproperty.InstallProperty$set$1", f = "InstallProperty.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
public final class InstallProperty$set$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Context $context;
    final /* synthetic */ FirebaseAnalytics $firebaseAnalytics;
    int label;
    final /* synthetic */ InstallProperty this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InstallProperty$set$1(FirebaseAnalytics firebaseAnalytics, InstallProperty installProperty, Context context, Continuation<? super InstallProperty$set$1> continuation) {
        super(2, continuation);
        this.$firebaseAnalytics = firebaseAnalytics;
        this.this$0 = installProperty;
        this.$context = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new InstallProperty$set$1(this.$firebaseAnalytics, this.this$0, this.$context, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((InstallProperty$set$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String installInfo;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        FirebaseAnalytics firebaseAnalytics = this.$firebaseAnalytics;
        String userPropertyName = eUserProperty.Install.getUserPropertyName();
        installInfo = this.this$0.getInstallInfo(this.$context);
        firebaseAnalytics.setUserProperty(userPropertyName, installInfo);
        return Unit.INSTANCE;
    }
}
