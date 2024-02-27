package javassist.util.proxy;

/* loaded from: classes2.dex */
public interface ProxyObject extends Proxy {
    MethodHandler getHandler();

    @Override // javassist.util.proxy.Proxy
    void setHandler(MethodHandler methodHandler);
}
