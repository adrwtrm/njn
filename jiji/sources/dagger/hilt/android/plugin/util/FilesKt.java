package dagger.hilt.android.plugin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.gradle.api.Project;

/* compiled from: Files.kt */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0002\u001a$\u0010\u0002\u001a\u00020\u0003*\u00020\u00042\u0018\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00030\u0006\u001a\n\u0010\t\u001a\u00020\u0001*\u00020\n\u001a\n\u0010\u000b\u001a\u00020\f*\u00020\u0001\u001a\n\u0010\u000b\u001a\u00020\f*\u00020\b\u001a\n\u0010\r\u001a\u00020\f*\u00020\u0001¨\u0006\u000e"}, d2 = {"getSdkPathFromEnvironmentVariable", "Ljava/io/File;", "forEachZipEntry", "", "Ljava/util/zip/ZipInputStream;", "block", "Lkotlin/Function2;", "Ljava/io/InputStream;", "Ljava/util/zip/ZipEntry;", "getSdkPath", "Lorg/gradle/api/Project;", "isClassFile", "", "isJarFile", "plugin"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class FilesKt {
    public static final boolean isClassFile(File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        return file.isFile() && Intrinsics.areEqual(kotlin.io.FilesKt.getExtension(file), "class");
    }

    public static final boolean isClassFile(ZipEntry zipEntry) {
        Intrinsics.checkNotNullParameter(zipEntry, "<this>");
        if (zipEntry.isDirectory()) {
            return false;
        }
        String name = zipEntry.getName();
        Intrinsics.checkNotNullExpressionValue(name, "this.name");
        return kotlin.text.StringsKt.endsWith$default(name, ".class", false, 2, (Object) null);
    }

    public static final boolean isJarFile(File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        return file.isFile() && Intrinsics.areEqual(kotlin.io.FilesKt.getExtension(file), "jar");
    }

    public static final void forEachZipEntry(ZipInputStream zipInputStream, Function2<? super InputStream, ? super ZipEntry, Unit> block) {
        Intrinsics.checkNotNullParameter(zipInputStream, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        ZipInputStream zipInputStream2 = zipInputStream;
        try {
            ZipInputStream zipInputStream3 = zipInputStream2;
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            while (nextEntry != null) {
                block.invoke(zipInputStream, nextEntry);
                nextEntry = zipInputStream.getNextEntry();
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(zipInputStream2, null);
        } finally {
        }
    }

    public static final File getSdkPath(Project project) {
        Intrinsics.checkNotNullParameter(project, "<this>");
        File projectDir = project.getRootProject().getProjectDir();
        Intrinsics.checkNotNullExpressionValue(projectDir, "rootProject.projectDir");
        File resolve = kotlin.io.FilesKt.resolve(projectDir, "local.properties");
        if (resolve.exists()) {
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(resolve);
            try {
                properties.load(fileInputStream);
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(fileInputStream, null);
                Object obj = properties.get("sdk.dir");
                String obj2 = obj != null ? obj.toString() : null;
                if (obj2 != null) {
                    File file = new File(obj2);
                    if (file.isDirectory()) {
                        return file;
                    }
                }
            } finally {
            }
        }
        return getSdkPathFromEnvironmentVariable();
    }

    private static final File getSdkPathFromEnvironmentVariable() {
        for (String str : CollectionsKt.listOf((Object[]) new String[]{"ANDROID_HOME", "ANDROID_SDK_ROOT"})) {
            String str2 = System.getenv(str);
            if (str2 != null) {
                File file = new File(str2);
                if (file.isDirectory()) {
                    return file;
                }
            }
        }
        throw new IllegalStateException("ANDROID_SDK_ROOT environment variable is not set".toString());
    }
}
