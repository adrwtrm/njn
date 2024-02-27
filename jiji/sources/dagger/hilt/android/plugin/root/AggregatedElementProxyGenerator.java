package dagger.hilt.android.plugin.root;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import dagger.hilt.processor.internal.root.ir.AggregatedElementProxyIr;
import java.io.File;
import javax.lang.model.element.Modifier;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AggregatedElementProxyGenerator.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 \t2\u00020\u0001:\u0001\tB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Ldagger/hilt/android/plugin/root/AggregatedElementProxyGenerator;", "", "outputDir", "Ljava/io/File;", "(Ljava/io/File;)V", "generate", "", "aggregatedElementProxy", "Ldagger/hilt/processor/internal/root/ir/AggregatedElementProxyIr;", "Companion", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class AggregatedElementProxyGenerator {
    private final File outputDir;
    public static final Companion Companion = new Companion(null);
    private static final ClassName AGGREGATED_ELEMENT_PROXY_ANNOTATION = ClassName.get("dagger.hilt.android.internal.legacy", "AggregatedElementProxy", new String[0]);

    public AggregatedElementProxyGenerator(File outputDir) {
        Intrinsics.checkNotNullParameter(outputDir, "outputDir");
        this.outputDir = outputDir;
    }

    public final void generate(AggregatedElementProxyIr aggregatedElementProxy) {
        Intrinsics.checkNotNullParameter(aggregatedElementProxy, "aggregatedElementProxy");
        JavaFile.builder(aggregatedElementProxy.getFqName().packageName(), TypeSpec.classBuilder(aggregatedElementProxy.getFqName()).addAnnotation(AnnotationSpec.builder(AGGREGATED_ELEMENT_PROXY_ANNOTATION).addMember("value", "$T.class", aggregatedElementProxy.getValue()).build()).addModifiers(Modifier.PUBLIC, Modifier.FINAL).build()).build().writeTo(this.outputDir);
    }

    /* compiled from: AggregatedElementProxyGenerator.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0019\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Ldagger/hilt/android/plugin/root/AggregatedElementProxyGenerator$Companion;", "", "()V", "AGGREGATED_ELEMENT_PROXY_ANNOTATION", "Lcom/squareup/javapoet/ClassName;", "kotlin.jvm.PlatformType", "getAGGREGATED_ELEMENT_PROXY_ANNOTATION", "()Lcom/squareup/javapoet/ClassName;", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ClassName getAGGREGATED_ELEMENT_PROXY_ANNOTATION() {
            return AggregatedElementProxyGenerator.AGGREGATED_ELEMENT_PROXY_ANNOTATION;
        }
    }
}
