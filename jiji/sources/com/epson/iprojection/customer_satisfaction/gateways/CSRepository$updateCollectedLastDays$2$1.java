package com.epson.iprojection.customer_satisfaction.gateways;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CSRepository.kt */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "preferences", "Landroidx/datastore/preferences/core/MutablePreferences;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "com.epson.iprojection.customer_satisfaction.gateways.CSRepository$updateCollectedLastDays$2$1", f = "CSRepository.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes.dex */
final class CSRepository$updateCollectedLastDays$2$1 extends SuspendLambda implements Function2<MutablePreferences, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $localDateString;
    /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CSRepository$updateCollectedLastDays$2$1(String str, Continuation<? super CSRepository$updateCollectedLastDays$2$1> continuation) {
        super(2, continuation);
        this.$localDateString = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        CSRepository$updateCollectedLastDays$2$1 cSRepository$updateCollectedLastDays$2$1 = new CSRepository$updateCollectedLastDays$2$1(this.$localDateString, continuation);
        cSRepository$updateCollectedLastDays$2$1.L$0 = obj;
        return cSRepository$updateCollectedLastDays$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(MutablePreferences mutablePreferences, Continuation<? super Unit> continuation) {
        return ((CSRepository$updateCollectedLastDays$2$1) create(mutablePreferences, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Preferences.Key<String> collected_last_date = PreferencesKeys.INSTANCE.getCOLLECTED_LAST_DATE();
        String localDateString = this.$localDateString;
        Intrinsics.checkNotNullExpressionValue(localDateString, "localDateString");
        ((MutablePreferences) this.L$0).set(collected_last_date, localDateString);
        return Unit.INSTANCE;
    }
}