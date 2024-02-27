package dagger.hilt.android.plugin.util;

import dagger.hilt.android.plugin.root.AggregatedAnnotation;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.sequences.SequencesKt;
import org.gradle.api.artifacts.transform.CacheableTransform;
import org.gradle.api.artifacts.transform.InputArtifact;
import org.gradle.api.artifacts.transform.TransformAction;
import org.gradle.api.artifacts.transform.TransformOutputs;
import org.gradle.api.artifacts.transform.TransformParameters;
import org.gradle.api.file.FileSystemLocation;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Classpath;

/* compiled from: AggregatedPackagesTransform.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b'\u0018\u0000 \u00102\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058gX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0011"}, d2 = {"Ldagger/hilt/android/plugin/util/AggregatedPackagesTransform;", "Lorg/gradle/api/artifacts/transform/TransformAction;", "Lorg/gradle/api/artifacts/transform/TransformParameters$None;", "()V", "inputArtifactProvider", "Lorg/gradle/api/provider/Provider;", "Lorg/gradle/api/file/FileSystemLocation;", "getInputArtifactProvider", "()Lorg/gradle/api/provider/Provider;", "transform", "", "outputs", "Lorg/gradle/api/artifacts/transform/TransformOutputs;", "transformFile", "file", "Ljava/io/File;", "Companion", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
@CacheableTransform
/* loaded from: classes2.dex */
public abstract class AggregatedPackagesTransform implements TransformAction<TransformParameters.None> {
    public static final Companion Companion = new Companion(null);
    private static final String JAR_NAME = "hiltAggregated.jar";

    @InputArtifact
    @Classpath
    public abstract Provider<FileSystemLocation> getInputArtifactProvider();

    public void transform(TransformOutputs outputs) {
        Intrinsics.checkNotNullParameter(outputs, "outputs");
        File input = ((FileSystemLocation) getInputArtifactProvider().get()).getAsFile();
        if (input.isFile()) {
            Intrinsics.checkNotNullExpressionValue(input, "input");
            transformFile(outputs, input);
        } else if (!input.isDirectory()) {
            throw new IllegalStateException(Intrinsics.stringPlus("File/directory does not exist: ", input.getAbsolutePath()).toString());
        } else {
            Intrinsics.checkNotNullExpressionValue(input, "input");
            for (File file : SequencesKt.filter(kotlin.io.FilesKt.walkTopDown(input), new Function1<File, Boolean>() { // from class: dagger.hilt.android.plugin.util.AggregatedPackagesTransform$transform$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(File it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(it.isFile());
                }
            })) {
                transformFile(outputs, file);
            }
        }
    }

    private final void transformFile(TransformOutputs transformOutputs, File file) {
        if (FilesKt.isJarFile(file)) {
            final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            FileOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
            try {
                final ZipOutputStream zipOutputStream2 = zipOutputStream;
                FilesKt.forEachZipEntry(new ZipInputStream(new FileInputStream(file)), new Function2<InputStream, ZipEntry, Unit>() { // from class: dagger.hilt.android.plugin.util.AggregatedPackagesTransform$transformFile$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(InputStream inputStream, ZipEntry zipEntry) {
                        invoke2(inputStream, zipEntry);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke */
                    public final void invoke2(InputStream inputStream, ZipEntry inputEntry) {
                        boolean z;
                        Intrinsics.checkNotNullParameter(inputStream, "inputStream");
                        Intrinsics.checkNotNullParameter(inputEntry, "inputEntry");
                        if (FilesKt.isClassFile(inputEntry)) {
                            String name = inputEntry.getName();
                            Intrinsics.checkNotNullExpressionValue(name, "inputEntry.name");
                            String substringBeforeLast$default = kotlin.text.StringsKt.substringBeforeLast$default(name, '/', (String) null, 2, (Object) null);
                            List<String> aggregated_packages = AggregatedAnnotation.Companion.getAGGREGATED_PACKAGES();
                            if (!(aggregated_packages instanceof Collection) || !aggregated_packages.isEmpty()) {
                                for (String str : aggregated_packages) {
                                    if (kotlin.text.StringsKt.endsWith$default(substringBeforeLast$default, str, false, 2, (Object) null)) {
                                        z = true;
                                        break;
                                    }
                                }
                            }
                            z = false;
                            if (z) {
                                zipOutputStream2.putNextEntry(new ZipEntry(inputEntry.getName()));
                                ByteStreamsKt.copyTo$default(inputStream, zipOutputStream2, 0, 2, null);
                                zipOutputStream2.closeEntry();
                                booleanRef.element = true;
                            }
                        }
                    }
                });
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(zipOutputStream, null);
                if (!booleanRef.element) {
                    return;
                }
                File file2 = transformOutputs.file(JAR_NAME);
                Intrinsics.checkNotNullExpressionValue(file2, "outputs.file(JAR_NAME)");
                zipOutputStream = new FileOutputStream(file2);
                try {
                    byteArrayOutputStream.writeTo(zipOutputStream);
                    Unit unit2 = Unit.INSTANCE;
                    CloseableKt.closeFinally(zipOutputStream, null);
                } finally {
                }
            } finally {
            }
        } else if (FilesKt.isClassFile(file)) {
            File parentFile = file.getParentFile();
            Intrinsics.checkNotNullExpressionValue(parentFile, "file.parentFile");
            List<String> aggregated_packages = AggregatedAnnotation.Companion.getAGGREGATED_PACKAGES();
            boolean z = false;
            if (!(aggregated_packages instanceof Collection) || !aggregated_packages.isEmpty()) {
                Iterator<T> it = aggregated_packages.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    } else if (kotlin.io.FilesKt.endsWith(parentFile, (String) it.next())) {
                        z = true;
                        break;
                    }
                }
            }
            if (z) {
                transformOutputs.file(file);
            }
        }
    }

    /* compiled from: AggregatedPackagesTransform.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Ldagger/hilt/android/plugin/util/AggregatedPackagesTransform$Companion;", "", "()V", "JAR_NAME", "", "getJAR_NAME", "()Ljava/lang/String;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getJAR_NAME() {
            return AggregatedPackagesTransform.JAR_NAME;
        }
    }
}
