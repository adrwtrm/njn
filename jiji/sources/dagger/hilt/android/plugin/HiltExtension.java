package dagger.hilt.android.plugin;

import kotlin.Metadata;

/* compiled from: HiltExtension.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u0018\u0010\u000b\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\f\u0010\u0005\"\u0004\b\r\u0010\u0007R\u0018\u0010\u000e\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u000f\u0010\u0005\"\u0004\b\u0010\u0010\u0007¨\u0006\u0011"}, d2 = {"Ldagger/hilt/android/plugin/HiltExtension;", "", "disableCrossCompilationRootValidation", "", "getDisableCrossCompilationRootValidation", "()Z", "setDisableCrossCompilationRootValidation", "(Z)V", "enableAggregatingTask", "getEnableAggregatingTask", "setEnableAggregatingTask", "enableExperimentalClasspathAggregation", "getEnableExperimentalClasspathAggregation", "setEnableExperimentalClasspathAggregation", "enableTransformForLocalTests", "getEnableTransformForLocalTests", "setEnableTransformForLocalTests", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public interface HiltExtension {
    boolean getDisableCrossCompilationRootValidation();

    boolean getEnableAggregatingTask();

    boolean getEnableExperimentalClasspathAggregation();

    boolean getEnableTransformForLocalTests();

    void setDisableCrossCompilationRootValidation(boolean z);

    void setEnableAggregatingTask(boolean z);

    void setEnableExperimentalClasspathAggregation(boolean z);

    void setEnableTransformForLocalTests(boolean z);
}
