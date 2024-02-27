package dagger.hilt.android.plugin.util;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.gradle.api.artifacts.transform.CacheableTransform;
import org.gradle.api.artifacts.transform.InputArtifact;
import org.gradle.api.artifacts.transform.TransformAction;
import org.gradle.api.artifacts.transform.TransformOutputs;
import org.gradle.api.artifacts.transform.TransformParameters;
import org.gradle.api.file.FileSystemLocation;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Classpath;

/* compiled from: CopyTransform.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b'\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058gX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\r"}, d2 = {"Ldagger/hilt/android/plugin/util/CopyTransform;", "Lorg/gradle/api/artifacts/transform/TransformAction;", "Lorg/gradle/api/artifacts/transform/TransformParameters$None;", "()V", "inputArtifactProvider", "Lorg/gradle/api/provider/Provider;", "Lorg/gradle/api/file/FileSystemLocation;", "getInputArtifactProvider", "()Lorg/gradle/api/provider/Provider;", "transform", "", "outputs", "Lorg/gradle/api/artifacts/transform/TransformOutputs;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
@CacheableTransform
/* loaded from: classes2.dex */
public abstract class CopyTransform implements TransformAction<TransformParameters.None> {
    @InputArtifact
    @Classpath
    public abstract Provider<FileSystemLocation> getInputArtifactProvider();

    public void transform(TransformOutputs outputs) {
        Intrinsics.checkNotNullParameter(outputs, "outputs");
        File asFile = ((FileSystemLocation) getInputArtifactProvider().get()).getAsFile();
        if (asFile.isDirectory()) {
            outputs.dir(asFile);
        } else if (!asFile.isFile()) {
            throw new IllegalStateException(Intrinsics.stringPlus("File/directory does not exist: ", asFile.getAbsolutePath()).toString());
        } else {
            outputs.file(asFile);
        }
    }
}
