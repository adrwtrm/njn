package dagger.hilt.android.plugin.task;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import dagger.hilt.android.plugin.task.Aggregator;
import dagger.hilt.processor.internal.root.ir.AggregatedRootIr;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Type;

/* compiled from: Aggregator.kt */
@Metadata(d1 = {"\u00003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u001a\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00032\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\u0012\u0010\"\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u001f\u001a\u00020\u0003H\u0016J\b\u0010#\u001a\u00020\u001eH\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0003X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0005\"\u0004\b\u0017\u0010\u0007R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0005\"\u0004\b\u001a\u0010\u0007R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00030\f¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u000e¨\u0006$"}, d2 = {"dagger/hilt/android/plugin/task/Aggregator$AggregatedDepClassVisitor$visitAnnotation$1", "Lorg/objectweb/asm/AnnotationVisitor;", "originatingRootClass", "", "getOriginatingRootClass", "()Ljava/lang/String;", "setOriginatingRootClass", "(Ljava/lang/String;)V", "originatingRootPackage", "getOriginatingRootPackage", "setOriginatingRootPackage", "originatingRootSimpleNames", "", "getOriginatingRootSimpleNames", "()Ljava/util/List;", "rootAnnotationClassName", "Lorg/objectweb/asm/Type;", "getRootAnnotationClassName", "()Lorg/objectweb/asm/Type;", "setRootAnnotationClassName", "(Lorg/objectweb/asm/Type;)V", "rootClass", "getRootClass", "setRootClass", "rootPackage", "getRootPackage", "setRootPackage", "rootSimpleNames", "getRootSimpleNames", "visit", "", AppMeasurementSdk.ConditionalUserProperty.NAME, "value", "", "visitArray", "visitEnd", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class Aggregator$AggregatedDepClassVisitor$visitAnnotation$1 extends AnnotationVisitor {
    final /* synthetic */ AnnotationVisitor $nextAnnotationVisitor;
    public String originatingRootClass;
    private String originatingRootPackage;
    private final List<String> originatingRootSimpleNames;
    public Type rootAnnotationClassName;
    public String rootClass;
    private String rootPackage;
    private final List<String> rootSimpleNames;
    final /* synthetic */ Aggregator.AggregatedDepClassVisitor this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Aggregator$AggregatedDepClassVisitor$visitAnnotation$1(Aggregator.AggregatedDepClassVisitor aggregatedDepClassVisitor, AnnotationVisitor annotationVisitor, int i) {
        super(i, annotationVisitor);
        this.this$0 = aggregatedDepClassVisitor;
        this.$nextAnnotationVisitor = annotationVisitor;
        this.rootSimpleNames = new ArrayList();
        this.originatingRootSimpleNames = new ArrayList();
    }

    public final String getRootClass() {
        String str = this.rootClass;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("rootClass");
        return null;
    }

    public final void setRootClass(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.rootClass = str;
    }

    public final String getRootPackage() {
        return this.rootPackage;
    }

    public final void setRootPackage(String str) {
        this.rootPackage = str;
    }

    public final List<String> getRootSimpleNames() {
        return this.rootSimpleNames;
    }

    public final String getOriginatingRootClass() {
        String str = this.originatingRootClass;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("originatingRootClass");
        return null;
    }

    public final void setOriginatingRootClass(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.originatingRootClass = str;
    }

    public final String getOriginatingRootPackage() {
        return this.originatingRootPackage;
    }

    public final void setOriginatingRootPackage(String str) {
        this.originatingRootPackage = str;
    }

    public final List<String> getOriginatingRootSimpleNames() {
        return this.originatingRootSimpleNames;
    }

    public final Type getRootAnnotationClassName() {
        Type type = this.rootAnnotationClassName;
        if (type != null) {
            return type;
        }
        Intrinsics.throwUninitializedPropertyAccessException("rootAnnotationClassName");
        return null;
    }

    public final void setRootAnnotationClassName(Type type) {
        Intrinsics.checkNotNullParameter(type, "<set-?>");
        this.rootAnnotationClassName = type;
    }

    @Override // org.objectweb.asm.AnnotationVisitor
    public void visit(String name, Object obj) {
        Intrinsics.checkNotNullParameter(name, "name");
        switch (name.hashCode()) {
            case -1909100069:
                if (name.equals("originatingRootPackage")) {
                    if (obj == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                    }
                    this.originatingRootPackage = (String) obj;
                    super.visit(name, obj);
                    return;
                }
                throw new IllegalStateException(Intrinsics.stringPlus("Unexpected annotation value: ", name));
            case -931010300:
                if (name.equals("rootPackage")) {
                    if (obj == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                    }
                    this.rootPackage = (String) obj;
                    super.visit(name, obj);
                    return;
                }
                throw new IllegalStateException(Intrinsics.stringPlus("Unexpected annotation value: ", name));
            case -413329999:
                if (name.equals("rootAnnotation")) {
                    if (obj == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.objectweb.asm.Type");
                    }
                    setRootAnnotationClassName((Type) obj);
                    super.visit(name, obj);
                    return;
                }
                throw new IllegalStateException(Intrinsics.stringPlus("Unexpected annotation value: ", name));
            case 3506402:
                if (name.equals("root")) {
                    if (obj == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                    }
                    setRootClass((String) obj);
                    super.visit(name, obj);
                    return;
                }
                throw new IllegalStateException(Intrinsics.stringPlus("Unexpected annotation value: ", name));
            case 879111147:
                if (name.equals("originatingRoot")) {
                    if (obj == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                    }
                    setOriginatingRootClass((String) obj);
                    super.visit(name, obj);
                    return;
                }
                throw new IllegalStateException(Intrinsics.stringPlus("Unexpected annotation value: ", name));
            default:
                throw new IllegalStateException(Intrinsics.stringPlus("Unexpected annotation value: ", name));
        }
    }

    @Override // org.objectweb.asm.AnnotationVisitor
    public AnnotationVisitor visitArray(final String name) {
        int i;
        Intrinsics.checkNotNullParameter(name, "name");
        i = this.this$0.asmApiVersion;
        return new AnnotationVisitor(i, super.visitArray(name)) { // from class: dagger.hilt.android.plugin.task.Aggregator$AggregatedDepClassVisitor$visitAnnotation$1$visitArray$1
            @Override // org.objectweb.asm.AnnotationVisitor
            public void visit(String str, Object obj) {
                String str2 = name;
                if (Intrinsics.areEqual(str2, "rootSimpleNames")) {
                    List<String> rootSimpleNames = this.getRootSimpleNames();
                    if (obj == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                    }
                    rootSimpleNames.add((String) obj);
                } else if (!Intrinsics.areEqual(str2, "originatingRootSimpleNames")) {
                    throw new IllegalStateException(Intrinsics.stringPlus("Unexpected annotation value: ", name).toString());
                } else {
                    List<String> originatingRootSimpleNames = this.getOriginatingRootSimpleNames();
                    if (obj == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
                    }
                    originatingRootSimpleNames.add((String) obj);
                }
                super.visit(str, obj);
            }
        };
    }

    @Override // org.objectweb.asm.AnnotationVisitor
    public void visitEnd() {
        this.this$0.getAggregatedRoots().add(new AggregatedRootIr(this.this$0.getAnnotatedClassName(), Aggregator.Companion.parseClassName(this.rootPackage, this.rootSimpleNames, getRootClass()), Aggregator.Companion.parseClassName(this.originatingRootPackage, this.originatingRootSimpleNames, getOriginatingRootClass()), Aggregator.Companion.toClassName(getRootAnnotationClassName()), false, 16, null));
        super.visitEnd();
    }
}
