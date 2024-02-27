package dagger.hilt.android.plugin.util;

import com.android.build.gradle.api.BaseVariant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile;

/* compiled from: Tasks.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0000¨\u0006\u0007"}, d2 = {"getCompileKotlin", "Lorg/gradle/api/tasks/TaskProvider;", "Lorg/jetbrains/kotlin/gradle/tasks/KotlinCompile;", "variant", "Lcom/android/build/gradle/api/BaseVariant;", "project", "Lorg/gradle/api/Project;", "plugin"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class TasksKt {
    public static final TaskProvider<KotlinCompile> getCompileKotlin(BaseVariant variant, Project project) {
        Intrinsics.checkNotNullParameter(variant, "variant");
        Intrinsics.checkNotNullParameter(project, "project");
        TaskContainer tasks = project.getTasks();
        StringBuilder sb = new StringBuilder("compile");
        String name = variant.getName();
        Intrinsics.checkNotNullExpressionValue(name, "variant.name");
        TaskProvider<KotlinCompile> named = tasks.named(sb.append(StringsKt.capitalize$default(name, null, 1, null)).append("Kotlin").toString());
        if (named != null) {
            return named;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.gradle.api.tasks.TaskProvider<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>");
    }
}
