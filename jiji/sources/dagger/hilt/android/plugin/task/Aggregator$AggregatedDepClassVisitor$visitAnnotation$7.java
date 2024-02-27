package dagger.hilt.android.plugin.task;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import dagger.hilt.android.plugin.task.Aggregator;
import dagger.hilt.processor.internal.root.ir.AggregatedUninstallModulesIr;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.objectweb.asm.AnnotationVisitor;

/* compiled from: Aggregator.kt */
@Metadata(d1 = {"\u0000+\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u001a\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00032\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u000e\u001a\u00020\u0003H\u0016J\b\u0010\u0012\u001a\u00020\rH\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0013"}, d2 = {"dagger/hilt/android/plugin/task/Aggregator$AggregatedDepClassVisitor$visitAnnotation$7", "Lorg/objectweb/asm/AnnotationVisitor;", "testClass", "", "getTestClass", "()Ljava/lang/String;", "setTestClass", "(Ljava/lang/String;)V", "uninstallModulesClasses", "", "getUninstallModulesClasses", "()Ljava/util/List;", "visit", "", AppMeasurementSdk.ConditionalUserProperty.NAME, "value", "", "visitArray", "visitEnd", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class Aggregator$AggregatedDepClassVisitor$visitAnnotation$7 extends AnnotationVisitor {
    final /* synthetic */ AnnotationVisitor $nextAnnotationVisitor;
    public String testClass;
    final /* synthetic */ Aggregator.AggregatedDepClassVisitor this$0;
    private final List<String> uninstallModulesClasses;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Aggregator$AggregatedDepClassVisitor$visitAnnotation$7(Aggregator.AggregatedDepClassVisitor aggregatedDepClassVisitor, AnnotationVisitor annotationVisitor, int i) {
        super(i, annotationVisitor);
        this.this$0 = aggregatedDepClassVisitor;
        this.$nextAnnotationVisitor = annotationVisitor;
        this.uninstallModulesClasses = new ArrayList();
    }

    public final String getTestClass() {
        String str = this.testClass;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("testClass");
        return null;
    }

    public final void setTestClass(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.testClass = str;
    }

    public final List<String> getUninstallModulesClasses() {
        return this.uninstallModulesClasses;
    }

    @Override // org.objectweb.asm.AnnotationVisitor
    public void visit(String name, Object obj) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (Intrinsics.areEqual(name, "test")) {
            if (obj == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
            }
            setTestClass((String) obj);
        }
        super.visit(name, obj);
    }

    @Override // org.objectweb.asm.AnnotationVisitor
    public AnnotationVisitor visitArray(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (Intrinsics.areEqual(name, "uninstallModules")) {
            return this.this$0.visitValue(new Function1<Object, Unit>() { // from class: dagger.hilt.android.plugin.task.Aggregator$AggregatedDepClassVisitor$visitAnnotation$7$visitArray$1
                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                    invoke2(obj);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final void invoke2(Object value) {
                    Intrinsics.checkNotNullParameter(value, "value");
                    Aggregator$AggregatedDepClassVisitor$visitAnnotation$7.this.getUninstallModulesClasses().add((String) value);
                }
            });
        }
        return super.visitArray(name);
    }

    @Override // org.objectweb.asm.AnnotationVisitor
    public void visitEnd() {
        this.this$0.getUninstallModulesDeps().add(new AggregatedUninstallModulesIr(this.this$0.getAnnotatedClassName(), getTestClass(), this.uninstallModulesClasses));
        super.visitEnd();
    }
}
