package javassist.tools.web;

import java.io.IOException;
import java.net.Socket;

/* compiled from: Webserver.java */
/* loaded from: classes2.dex */
class ServiceThread extends Thread {
    Socket sock;
    Webserver web;

    public ServiceThread(Webserver webserver, Socket socket) {
        this.web = webserver;
        this.sock = socket;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            this.web.process(this.sock);
        } catch (IOException unused) {
        }
    }
}
