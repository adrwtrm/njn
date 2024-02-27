package dagger.hilt.android.plugin;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Status;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformInput;
import dagger.hilt.android.plugin.util.FilesKt;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AndroidEntryPointTransform.kt */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J,\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u000e\u001a\u00020\u0006H\u0002J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J \u0010\u0019\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u0006H\u0002J\u0010\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J \u0010\u001f\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\tH\u0002¨\u0006!"}, d2 = {"Ldagger/hilt/android/plugin/AndroidEntryPointTransform;", "Lcom/android/build/api/transform/Transform;", "()V", "copyJar", "", "inputJar", "Ljava/io/File;", "outputJar", "createHiltClassTransformer", "Ldagger/hilt/android/plugin/AndroidEntryPointClassTransformer;", "inputs", "", "Lcom/android/build/api/transform/TransformInput;", "referencedInputs", "outputDir", "getInputTypes", "", "Lcom/android/build/api/transform/QualifiedContent$DefaultContentType;", "getName", "", "getScopes", "", "Lcom/android/build/api/transform/QualifiedContent$Scope;", "isIncremental", "", "toOutputFile", "inputDir", "inputFile", "transform", "invocation", "Lcom/android/build/api/transform/TransformInvocation;", "transformFile", "transformer", "plugin"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class AndroidEntryPointTransform extends Transform {

    /* compiled from: AndroidEntryPointTransform.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes2.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Status.values().length];
            iArr[Status.ADDED.ordinal()] = 1;
            iArr[Status.CHANGED.ordinal()] = 2;
            iArr[Status.REMOVED.ordinal()] = 3;
            iArr[Status.NOTCHANGED.ordinal()] = 4;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public String getName() {
        return "AndroidEntryPointTransform";
    }

    public boolean isIncremental() {
        return true;
    }

    public Set<QualifiedContent.DefaultContentType> getInputTypes() {
        return SetsKt.setOf(QualifiedContent.DefaultContentType.CLASSES);
    }

    public Set<QualifiedContent.Scope> getScopes() {
        return SetsKt.mutableSetOf(QualifiedContent.Scope.PROJECT);
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x00bf, code lost:
        r4 = r4.getDirectoryInputs();
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r4, "transformInput.directoryInputs");
        r4 = r4.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00d2, code lost:
        if (r4.hasNext() == false) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00d4, code lost:
        r5 = (com.android.build.api.transform.DirectoryInput) r4.next();
        r6 = r19.getOutputProvider().getContentLocation(r5.getName(), r5.getContentTypes(), r5.getScopes(), com.android.build.api.transform.Format.DIRECTORY);
        r8 = r19.getInputs();
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r8, "invocation.inputs");
        r12 = r19.getReferencedInputs();
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r12, "invocation.referencedInputs");
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, "outputDir");
        r8 = createHiltClassTransformer(r8, r12, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0111, code lost:
        if (r19.isIncremental() == false) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0113, code lost:
        r12 = r5.getChangedFiles();
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r12, "directoryInput.changedFiles");
        r12 = r12.entrySet().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0128, code lost:
        if (r12.hasNext() == false) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x012a, code lost:
        r15 = (java.util.Map.Entry) r12.next();
        r9 = (java.io.File) r15.getKey();
        r15 = (com.android.build.api.transform.Status) r15.getValue();
        r10 = r5.getFile();
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, "directoryInput.file");
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r9, "file");
        r10 = toOutputFile(r6, r10, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x014e, code lost:
        if (r15 != null) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0150, code lost:
        r11 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0152, code lost:
        r11 = dagger.hilt.android.plugin.AndroidEntryPointTransform.WhenMappings.$EnumSwitchMapping$0[r15.ordinal()];
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x015a, code lost:
        r17 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x015d, code lost:
        if (r11 == 1) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0160, code lost:
        if (r11 == 2) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0162, code lost:
        r1 = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0163, code lost:
        if (r11 == 3) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0165, code lost:
        r9 = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0166, code lost:
        if (r11 != 4) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0178, code lost:
        throw new java.lang.IllegalStateException(kotlin.jvm.internal.Intrinsics.stringPlus("Unknown status: ", r15).toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0179, code lost:
        r9 = 4;
        r10.delete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x017f, code lost:
        r1 = 3;
        r10 = r10.getParentFile();
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, "outputFile.parentFile");
        transformFile(r9, r10, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x018b, code lost:
        r9 = r1;
        r1 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0191, code lost:
        r17 = r1;
        r1 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0196, code lost:
        r17 = r1;
        r1 = r9;
        r9 = r5.getFile();
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r9, "directoryInput.file");
        r9 = kotlin.io.FilesKt.walkTopDown(r9).iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x01af, code lost:
        if (r9.hasNext() == false) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x01b1, code lost:
        r10 = r9.next();
        r12 = r5.getFile();
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r12, "directoryInput.file");
        r12 = toOutputFile(r6, r12, r10).getParentFile();
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r12, "outputFile.parentFile");
        transformFile(r10, r12, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x01cd, code lost:
        r9 = r1;
        r1 = r17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void transform(com.android.build.api.transform.TransformInvocation r19) {
        /*
            Method dump skipped, instructions count: 469
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: dagger.hilt.android.plugin.AndroidEntryPointTransform.transform(com.android.build.api.transform.TransformInvocation):void");
    }

    private final AndroidEntryPointClassTransformer createHiltClassTransformer(Collection<? extends TransformInput> collection, Collection<? extends TransformInput> collection2, File file) {
        ArrayList arrayList = new ArrayList();
        for (TransformInput transformInput : CollectionsKt.plus((Collection) collection, (Iterable) collection2)) {
            Collection directoryInputs = transformInput.getDirectoryInputs();
            Intrinsics.checkNotNullExpressionValue(directoryInputs, "input.directoryInputs");
            Collection jarInputs = transformInput.getJarInputs();
            Intrinsics.checkNotNullExpressionValue(jarInputs, "input.jarInputs");
            List<QualifiedContent> plus = CollectionsKt.plus(directoryInputs, (Iterable) jarInputs);
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(plus, 10));
            for (QualifiedContent qualifiedContent : plus) {
                arrayList2.add(qualifiedContent.getFile());
            }
            CollectionsKt.addAll(arrayList, arrayList2);
        }
        return new AndroidEntryPointClassTransformer(getName(), arrayList, file, true);
    }

    private final void transformFile(File file, File file2, AndroidEntryPointClassTransformer androidEntryPointClassTransformer) {
        if (FilesKt.isClassFile(file)) {
            androidEntryPointClassTransformer.transformFile(file);
        } else if (file.isFile()) {
            file2.mkdirs();
            kotlin.io.FilesKt.copyTo$default(file, new File(file2, file.getName()), true, 0, 4, null);
        }
    }

    private final void copyJar(File file, File file2) {
        File parentFile = file2.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
        kotlin.io.FilesKt.copyTo$default(file, file2, true, 0, 4, null);
    }

    private final File toOutputFile(File file, File file2, File file3) {
        return new File(file, kotlin.io.FilesKt.relativeTo(file3, file2).getPath());
    }
}
