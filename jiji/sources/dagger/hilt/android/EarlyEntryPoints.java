package dagger.hilt.android;

import android.app.Application;
import android.content.Context;
import dagger.hilt.EntryPoints;
import dagger.hilt.android.internal.Contexts;
import dagger.hilt.internal.GeneratedComponentManager;
import dagger.hilt.internal.GeneratedComponentManagerHolder;
import dagger.hilt.internal.Preconditions;
import dagger.hilt.internal.TestSingletonComponentManager;
import java.lang.annotation.Annotation;
import javax.annotation.Nonnull;

/* loaded from: classes2.dex */
public final class EarlyEntryPoints {
    @Nonnull
    public static <T> T get(Context applicationContext, Class<T> entryPoint) {
        Application application = Contexts.getApplication(applicationContext);
        Preconditions.checkState(application instanceof GeneratedComponentManagerHolder, "Expected application context to implement GeneratedComponentManagerHolder. Check that you're passing in an application context that uses Hilt.", new Object[0]);
        GeneratedComponentManager<?> componentManager = ((GeneratedComponentManagerHolder) application).componentManager();
        if (componentManager instanceof TestSingletonComponentManager) {
            Preconditions.checkState(hasAnnotationReflection(entryPoint, EarlyEntryPoint.class), "%s should be called with EntryPoints.get() rather than EarlyEntryPoints.get()", entryPoint.getCanonicalName());
            return entryPoint.cast(((TestSingletonComponentManager) componentManager).earlySingletonComponent());
        }
        return (T) EntryPoints.get(application, entryPoint);
    }

    private static boolean hasAnnotationReflection(Class<?> clazz, Class<? extends Annotation> annotationClazz) {
        for (Annotation annotation : clazz.getAnnotations()) {
            if (annotation.annotationType().equals(annotationClazz)) {
                return true;
            }
        }
        return false;
    }

    private EarlyEntryPoints() {
    }
}
