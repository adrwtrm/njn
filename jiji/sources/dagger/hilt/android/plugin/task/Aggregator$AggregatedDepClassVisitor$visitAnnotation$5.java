package dagger.hilt.android.plugin.task;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import dagger.hilt.android.plugin.task.Aggregator;
import dagger.hilt.processor.internal.root.ir.AggregatedDepsIr;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.objectweb.asm.AnnotationVisitor;

/* compiled from: Aggregator.kt */
@Metadata(d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0013\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u001a\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00042\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0019\u001a\u00020\u0004H\u0016J\b\u0010\u001d\u001a\u00020\u0018H\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\t\"\u0004\b\u000e\u0010\u000bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000bR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0006R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\t\"\u0004\b\u0016\u0010\u000b¨\u0006\u001e"}, d2 = {"dagger/hilt/android/plugin/task/Aggregator$AggregatedDepClassVisitor$visitAnnotation$5", "Lorg/objectweb/asm/AnnotationVisitor;", "componentClasses", "", "", "getComponentClasses", "()Ljava/util/List;", "componentEntryPoint", "getComponentEntryPoint", "()Ljava/lang/String;", "setComponentEntryPoint", "(Ljava/lang/String;)V", "entryPoint", "getEntryPoint", "setEntryPoint", "moduleClass", "getModuleClass", "setModuleClass", "replacesClasses", "getReplacesClasses", "testClass", "getTestClass", "setTestClass", "visit", "", AppMeasurementSdk.ConditionalUserProperty.NAME, "value", "", "visitArray", "visitEnd", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class Aggregator$AggregatedDepClassVisitor$visitAnnotation$5 extends AnnotationVisitor {
    final /* synthetic */ AnnotationVisitor $nextAnnotationVisitor;
    private final List<String> componentClasses;
    private String componentEntryPoint;
    private String entryPoint;
    private String moduleClass;
    private final List<String> replacesClasses;
    private String testClass;
    final /* synthetic */ Aggregator.AggregatedDepClassVisitor this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Aggregator$AggregatedDepClassVisitor$visitAnnotation$5(Aggregator.AggregatedDepClassVisitor aggregatedDepClassVisitor, AnnotationVisitor annotationVisitor, int i) {
        super(i, annotationVisitor);
        this.this$0 = aggregatedDepClassVisitor;
        this.$nextAnnotationVisitor = annotationVisitor;
        this.componentClasses = new ArrayList();
        this.replacesClasses = new ArrayList();
    }

    public final List<String> getComponentClasses() {
        return this.componentClasses;
    }

    public final String getTestClass() {
        return this.testClass;
    }

    public final void setTestClass(String str) {
        this.testClass = str;
    }

    public final List<String> getReplacesClasses() {
        return this.replacesClasses;
    }

    public final String getModuleClass() {
        return this.moduleClass;
    }

    public final void setModuleClass(String str) {
        this.moduleClass = str;
    }

    public final String getEntryPoint() {
        return this.entryPoint;
    }

    public final void setEntryPoint(String str) {
        this.entryPoint = str;
    }

    public final String getComponentEntryPoint() {
        return this.componentEntryPoint;
    }

    public final void setComponentEntryPoint(String str) {
        this.componentEntryPoint = str;
    }

    @Override // org.objectweb.asm.AnnotationVisitor
    public void visit(String name, Object obj) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (Intrinsics.areEqual(name, "test")) {
            if (obj == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
            }
            this.testClass = (String) obj;
        }
        super.visit(name, obj);
    }

    @Override // org.objectweb.asm.AnnotationVisitor
    public AnnotationVisitor visitArray(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        switch (name.hashCode()) {
            case -1229671435:
                if (name.equals("entryPoints")) {
                    return this.this$0.visitValue(new Function1<Object, Unit>() { // from class: dagger.hilt.android.plugin.task.Aggregator$AggregatedDepClassVisitor$visitAnnotation$5$visitArray$4
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
                            Aggregator$AggregatedDepClassVisitor$visitAnnotation$5.this.setEntryPoint((String) value);
                        }
                    });
                }
                break;
            case -447446250:
                if (name.equals("components")) {
                    return this.this$0.visitValue(new Function1<Object, Unit>() { // from class: dagger.hilt.android.plugin.task.Aggregator$AggregatedDepClassVisitor$visitAnnotation$5$visitArray$1
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
                            Aggregator$AggregatedDepClassVisitor$visitAnnotation$5.this.getComponentClasses().add((String) value);
                        }
                    });
                }
                break;
            case -430332865:
                if (name.equals("replaces")) {
                    return this.this$0.visitValue(new Function1<Object, Unit>() { // from class: dagger.hilt.android.plugin.task.Aggregator$AggregatedDepClassVisitor$visitAnnotation$5$visitArray$2
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
                            Aggregator$AggregatedDepClassVisitor$visitAnnotation$5.this.getReplacesClasses().add((String) value);
                        }
                    });
                }
                break;
            case 1227433863:
                if (name.equals("modules")) {
                    return this.this$0.visitValue(new Function1<Object, Unit>() { // from class: dagger.hilt.android.plugin.task.Aggregator$AggregatedDepClassVisitor$visitAnnotation$5$visitArray$3
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
                            Aggregator$AggregatedDepClassVisitor$visitAnnotation$5.this.setModuleClass((String) value);
                        }
                    });
                }
                break;
            case 1456877240:
                if (name.equals("componentEntryPoints")) {
                    return this.this$0.visitValue(new Function1<Object, Unit>() { // from class: dagger.hilt.android.plugin.task.Aggregator$AggregatedDepClassVisitor$visitAnnotation$5$visitArray$5
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
                            Aggregator$AggregatedDepClassVisitor$visitAnnotation$5.this.setComponentEntryPoint((String) value);
                        }
                    });
                }
                break;
        }
        return super.visitArray(name);
    }

    @Override // org.objectweb.asm.AnnotationVisitor
    public void visitEnd() {
        this.this$0.getAggregatedDeps().add(new AggregatedDepsIr(this.this$0.getAnnotatedClassName(), this.componentClasses, this.testClass, this.replacesClasses, this.moduleClass, this.entryPoint, this.componentEntryPoint));
        super.visitEnd();
    }
}
