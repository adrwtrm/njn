package javassist.util.proxy;

import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public interface MethodHandler {
    Object invoke(Object obj, Method method, Method method2, Object[] objArr) throws Throwable;
}
