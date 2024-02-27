package dagger.hilt.android.plugin.task;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import dagger.hilt.android.plugin.task.Aggregator;
import dagger.hilt.processor.internal.root.ir.ProcessedRootSentinelIr;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.objectweb.asm.AnnotationVisitor;

/* compiled from: Aggregator.kt */
@Metadata(d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00012\u0006\u0010\b\u001a\u00020\u0004H\u0016J\b\u0010\t\u001a\u00020\nH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"dagger/hilt/android/plugin/task/Aggregator$AggregatedDepClassVisitor$visitAnnotation$2", "Lorg/objectweb/asm/AnnotationVisitor;", "rootClasses", "", "", "getRootClasses", "()Ljava/util/List;", "visitArray", AppMeasurementSdk.ConditionalUserProperty.NAME, "visitEnd", "", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class Aggregator$AggregatedDepClassVisitor$visitAnnotation$2 extends AnnotationVisitor {
    final /* synthetic */ AnnotationVisitor $nextAnnotationVisitor;
    private final List<String> rootClasses;
    final /* synthetic */ Aggregator.AggregatedDepClassVisitor this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Aggregator$AggregatedDepClassVisitor$visitAnnotation$2(Aggregator.AggregatedDepClassVisitor aggregatedDepClassVisitor, AnnotationVisitor annotationVisitor, int i) {
        super(i, annotationVisitor);
        this.this$0 = aggregatedDepClassVisitor;
        this.$nextAnnotationVisitor = annotationVisitor;
        this.rootClasses = new ArrayList();
    }

    public final List<String> getRootClasses() {
        return this.rootClasses;
    }

    @Override // org.objectweb.asm.AnnotationVisitor
    public AnnotationVisitor visitArray(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return Intrinsics.areEqual(name, "roots") ? this.this$0.visitValue(new Function1<Object, Unit>() { // from class: dagger.hilt.android.plugin.task.Aggregator$AggregatedDepClassVisitor$visitAnnotation$2$visitArray$1
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
                Aggregator$AggregatedDepClassVisitor$visitAnnotation$2.this.getRootClasses().add((String) value);
            }
        }) : super.visitArray(name);
    }

    @Override // org.objectweb.asm.AnnotationVisitor
    public void visitEnd() {
        this.this$0.getProcessedRoots().add(new ProcessedRootSentinelIr(this.this$0.getAnnotatedClassName(), this.rootClasses));
        super.visitEnd();
    }
}
