package dagger.hilt;

import dagger.hilt.internal.GeneratedComponent;
import dagger.hilt.internal.GeneratedComponentManager;
import dagger.hilt.internal.Preconditions;
import dagger.hilt.internal.TestSingletonComponent;
import java.lang.annotation.Annotation;
import javax.annotation.Nonnull;

/* loaded from: classes2.dex */
public final class EntryPoints {
    private static final String EARLY_ENTRY_POINT = "dagger.hilt.android.EarlyEntryPoint";

    @Nonnull
    public static <T> T get(Object component, Class<T> entryPoint) {
        if (component instanceof GeneratedComponent) {
            if (component instanceof TestSingletonComponent) {
                Preconditions.checkState(!hasAnnotationReflection(entryPoint, EARLY_ENTRY_POINT), "Interface, %s, annotated with @EarlyEntryPoint should be called with EarlyEntryPoints.get() rather than EntryPoints.get()", entryPoint.getCanonicalName());
            }
            return entryPoint.cast(component);
        } else if (component instanceof GeneratedComponentManager) {
            return (T) get(((GeneratedComponentManager) component).generatedComponent(), entryPoint);
        } else {
            throw new IllegalStateException(String.format("Given component holder %s does not implement %s or %s", component.getClass(), GeneratedComponent.class, GeneratedComponentManager.class));
        }
    }

    private static boolean hasAnnotationReflection(Class<?> clazz, String annotationName) {
        for (Annotation annotation : clazz.getAnnotations()) {
            if (annotation.annotationType().getCanonicalName().contentEquals(annotationName)) {
                return true;
            }
        }
        return false;
    }

    private EntryPoints() {
    }
}
