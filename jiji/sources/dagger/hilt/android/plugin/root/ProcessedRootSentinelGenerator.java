package dagger.hilt.android.plugin.root;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.File;
import javax.lang.model.element.Modifier;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: ProcessedRootSentinelGenerator.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 \t2\u00020\u0001:\u0001\tB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Ldagger/hilt/android/plugin/root/ProcessedRootSentinelGenerator;", "", "outputDir", "Ljava/io/File;", "(Ljava/io/File;)V", "generate", "", "processedRootName", "Lcom/squareup/javapoet/ClassName;", "Companion", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class ProcessedRootSentinelGenerator {
    private final File outputDir;
    public static final Companion Companion = new Companion(null);
    private static final String PROCESSED_ROOT_SENTINEL_GEN_PACKAGE = "dagger.hilt.internal.processedrootsentinel.codegen";
    private static final ClassName PROCESSED_ROOT_SENTINEL_ANNOTATION = ClassName.get("dagger.hilt.internal.processedrootsentinel", "ProcessedRootSentinel", new String[0]);

    public ProcessedRootSentinelGenerator(File outputDir) {
        Intrinsics.checkNotNullParameter(outputDir, "outputDir");
        this.outputDir = outputDir;
    }

    public final void generate(ClassName processedRootName) {
        Intrinsics.checkNotNullParameter(processedRootName, "processedRootName");
        String str = PROCESSED_ROOT_SENTINEL_GEN_PACKAGE;
        String className = processedRootName.toString();
        Intrinsics.checkNotNullExpressionValue(className, "processedRootName.toString()");
        JavaFile.builder(str, TypeSpec.classBuilder(ClassName.get(str, Intrinsics.stringPlus("_", StringsKt.replace$default(className, '.', '_', false, 4, (Object) null)), new String[0])).addAnnotation(AnnotationSpec.builder(PROCESSED_ROOT_SENTINEL_ANNOTATION).addMember("roots", "$S", processedRootName).build()).addModifiers(Modifier.PUBLIC, Modifier.FINAL).build()).build().writeTo(this.outputDir);
    }

    /* compiled from: ProcessedRootSentinelGenerator.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0019\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\tX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Ldagger/hilt/android/plugin/root/ProcessedRootSentinelGenerator$Companion;", "", "()V", "PROCESSED_ROOT_SENTINEL_ANNOTATION", "Lcom/squareup/javapoet/ClassName;", "kotlin.jvm.PlatformType", "getPROCESSED_ROOT_SENTINEL_ANNOTATION", "()Lcom/squareup/javapoet/ClassName;", "PROCESSED_ROOT_SENTINEL_GEN_PACKAGE", "", "getPROCESSED_ROOT_SENTINEL_GEN_PACKAGE", "()Ljava/lang/String;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getPROCESSED_ROOT_SENTINEL_GEN_PACKAGE() {
            return ProcessedRootSentinelGenerator.PROCESSED_ROOT_SENTINEL_GEN_PACKAGE;
        }

        public final ClassName getPROCESSED_ROOT_SENTINEL_ANNOTATION() {
            return ProcessedRootSentinelGenerator.PROCESSED_ROOT_SENTINEL_ANNOTATION;
        }
    }
}
