package javassist.tools.web;

/* loaded from: classes2.dex */
public class BadHttpRequest extends Exception {
    private static final long serialVersionUID = 1;
    private Exception e;

    public BadHttpRequest() {
        this.e = null;
    }

    public BadHttpRequest(Exception exc) {
        this.e = exc;
    }

    @Override // java.lang.Throwable
    public String toString() {
        Exception exc = this.e;
        if (exc == null) {
            return super.toString();
        }
        return exc.toString();
    }
}
