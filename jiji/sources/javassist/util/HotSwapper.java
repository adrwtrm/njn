package javassist.util;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.MethodEntryRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class HotSwapper {
    private static final String HOST_NAME = "localhost";
    private static final String TRIGGER_NAME = Trigger.class.getName();
    private VirtualMachine jvm;
    private Map<ReferenceType, byte[]> newClassFiles;
    private MethodEntryRequest request;
    private Trigger trigger;

    public HotSwapper(int i) throws IOException, IllegalConnectorArgumentsException {
        this(Integer.toString(i));
    }

    public HotSwapper(String str) throws IOException, IllegalConnectorArgumentsException {
        this.jvm = null;
        this.request = null;
        this.newClassFiles = null;
        this.trigger = new Trigger();
        AttachingConnector findConnector = findConnector("com.sun.jdi.SocketAttach");
        Map defaultArguments = findConnector.defaultArguments();
        ((Connector.Argument) defaultArguments.get("hostname")).setValue(HOST_NAME);
        ((Connector.Argument) defaultArguments.get("port")).setValue(str);
        VirtualMachine attach = findConnector.attach(defaultArguments);
        this.jvm = attach;
        this.request = methodEntryRequests(attach.eventRequestManager(), TRIGGER_NAME);
    }

    private Connector findConnector(String str) throws IOException {
        for (Connector connector : Bootstrap.virtualMachineManager().allConnectors()) {
            if (connector.name().equals(str)) {
                return connector;
            }
        }
        throw new IOException("Not found: " + str);
    }

    private static MethodEntryRequest methodEntryRequests(EventRequestManager eventRequestManager, String str) {
        MethodEntryRequest createMethodEntryRequest = eventRequestManager.createMethodEntryRequest();
        createMethodEntryRequest.addClassFilter(str);
        createMethodEntryRequest.setSuspendPolicy(1);
        return createMethodEntryRequest;
    }

    private void deleteEventRequest(EventRequestManager eventRequestManager, MethodEntryRequest methodEntryRequest) {
        eventRequestManager.deleteEventRequest(methodEntryRequest);
    }

    public void reload(String str, byte[] bArr) {
        ReferenceType refType = toRefType(str);
        HashMap hashMap = new HashMap();
        hashMap.put(refType, bArr);
        reload2(hashMap, str);
    }

    public void reload(Map<String, byte[]> map) {
        HashMap hashMap = new HashMap();
        String str = null;
        for (Map.Entry<String, byte[]> entry : map.entrySet()) {
            String key = entry.getKey();
            hashMap.put(toRefType(key), entry.getValue());
            str = key;
        }
        if (str != null) {
            reload2(hashMap, str + " etc.");
        }
    }

    private ReferenceType toRefType(String str) {
        List classesByName = this.jvm.classesByName(str);
        if (classesByName == null || classesByName.isEmpty()) {
            throw new RuntimeException("no such class: " + str);
        }
        return (ReferenceType) classesByName.get(0);
    }

    private void reload2(Map<ReferenceType, byte[]> map, String str) {
        synchronized (this.trigger) {
            startDaemon();
            this.newClassFiles = map;
            this.request.enable();
            this.trigger.doSwap();
            this.request.disable();
            if (this.newClassFiles != null) {
                this.newClassFiles = null;
                throw new RuntimeException("failed to reload: " + str);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [javassist.util.HotSwapper$1] */
    private void startDaemon() {
        new Thread() { // from class: javassist.util.HotSwapper.1
            private void errorMsg(Throwable th) {
                System.err.print("Exception in thread \"HotSwap\" ");
                th.printStackTrace(System.err);
            }

            /* JADX WARN: Code restructure failed: missing block: B:8:0x0019, code lost:
                r3.this$0.hotswap();
             */
            @Override // java.lang.Thread, java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() {
                /*
                    r3 = this;
                    r0 = 0
                    javassist.util.HotSwapper r1 = javassist.util.HotSwapper.this     // Catch: java.lang.Throwable -> L1f
                    com.sun.jdi.event.EventSet r0 = r1.waitEvent()     // Catch: java.lang.Throwable -> L1f
                    com.sun.jdi.event.EventIterator r1 = r0.eventIterator()     // Catch: java.lang.Throwable -> L1f
                Lb:
                    boolean r2 = r1.hasNext()     // Catch: java.lang.Throwable -> L1f
                    if (r2 == 0) goto L23
                    com.sun.jdi.event.Event r2 = r1.nextEvent()     // Catch: java.lang.Throwable -> L1f
                    boolean r2 = r2 instanceof com.sun.jdi.event.MethodEntryEvent     // Catch: java.lang.Throwable -> L1f
                    if (r2 == 0) goto Lb
                    javassist.util.HotSwapper r1 = javassist.util.HotSwapper.this     // Catch: java.lang.Throwable -> L1f
                    r1.hotswap()     // Catch: java.lang.Throwable -> L1f
                    goto L23
                L1f:
                    r1 = move-exception
                    r3.errorMsg(r1)
                L23:
                    if (r0 == 0) goto L2d
                    r0.resume()     // Catch: java.lang.Throwable -> L29
                    goto L2d
                L29:
                    r0 = move-exception
                    r3.errorMsg(r0)
                L2d:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: javassist.util.HotSwapper.AnonymousClass1.run():void");
            }
        }.start();
    }

    EventSet waitEvent() throws InterruptedException {
        return this.jvm.eventQueue().remove();
    }

    void hotswap() {
        this.jvm.redefineClasses(this.newClassFiles);
        this.newClassFiles = null;
    }
}
