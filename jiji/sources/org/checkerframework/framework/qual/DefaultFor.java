package org.checkerframework.framework.qual;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes2.dex */
public @interface DefaultFor {
    String[] names() default {};

    String[] namesExceptions() default {};

    TypeKind[] typeKinds() default {};

    Class<?>[] types() default {};

    TypeUseLocation[] value() default {};
}