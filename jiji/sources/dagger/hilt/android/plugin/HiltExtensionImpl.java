package dagger.hilt.android.plugin;

import kotlin.Metadata;

/* compiled from: HiltExtension.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000e\b\u0010\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001a\u0010\u000f\u001a\u00020\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\b¨\u0006\u0012"}, d2 = {"Ldagger/hilt/android/plugin/HiltExtensionImpl;", "Ldagger/hilt/android/plugin/HiltExtension;", "()V", "disableCrossCompilationRootValidation", "", "getDisableCrossCompilationRootValidation", "()Z", "setDisableCrossCompilationRootValidation", "(Z)V", "enableAggregatingTask", "getEnableAggregatingTask", "setEnableAggregatingTask", "enableExperimentalClasspathAggregation", "getEnableExperimentalClasspathAggregation", "setEnableExperimentalClasspathAggregation", "enableTransformForLocalTests", "getEnableTransformForLocalTests", "setEnableTransformForLocalTests", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public class HiltExtensionImpl implements HiltExtension {
    private boolean disableCrossCompilationRootValidation;
    private boolean enableAggregatingTask = true;
    private boolean enableExperimentalClasspathAggregation;
    private boolean enableTransformForLocalTests;

    @Override // dagger.hilt.android.plugin.HiltExtension
    public boolean getEnableExperimentalClasspathAggregation() {
        return this.enableExperimentalClasspathAggregation;
    }

    @Override // dagger.hilt.android.plugin.HiltExtension
    public void setEnableExperimentalClasspathAggregation(boolean z) {
        this.enableExperimentalClasspathAggregation = z;
    }

    @Override // dagger.hilt.android.plugin.HiltExtension
    public boolean getEnableTransformForLocalTests() {
        return this.enableTransformForLocalTests;
    }

    @Override // dagger.hilt.android.plugin.HiltExtension
    public void setEnableTransformForLocalTests(boolean z) {
        this.enableTransformForLocalTests = z;
    }

    @Override // dagger.hilt.android.plugin.HiltExtension
    public boolean getEnableAggregatingTask() {
        return this.enableAggregatingTask;
    }

    @Override // dagger.hilt.android.plugin.HiltExtension
    public void setEnableAggregatingTask(boolean z) {
        this.enableAggregatingTask = z;
    }

    @Override // dagger.hilt.android.plugin.HiltExtension
    public boolean getDisableCrossCompilationRootValidation() {
        return this.disableCrossCompilationRootValidation;
    }

    @Override // dagger.hilt.android.plugin.HiltExtension
    public void setDisableCrossCompilationRootValidation(boolean z) {
        this.disableCrossCompilationRootValidation = z;
    }
}
