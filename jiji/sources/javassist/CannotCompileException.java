package javassist;

import javassist.compiler.CompileError;

/* loaded from: classes2.dex */
public class CannotCompileException extends Exception {
    private static final long serialVersionUID = 1;
    private String message;
    private Throwable myCause;

    @Override // java.lang.Throwable
    public synchronized Throwable getCause() {
        Throwable th;
        th = this.myCause;
        if (th == this) {
            th = null;
        }
        return th;
    }

    @Override // java.lang.Throwable
    public synchronized Throwable initCause(Throwable th) {
        this.myCause = th;
        return this;
    }

    public String getReason() {
        String str = this.message;
        return str != null ? str : toString();
    }

    public CannotCompileException(String str) {
        super(str);
        this.message = str;
        initCause(null);
    }

    public CannotCompileException(Throwable th) {
        super("by " + th.toString());
        this.message = null;
        initCause(th);
    }

    public CannotCompileException(String str, Throwable th) {
        this(str);
        initCause(th);
    }

    public CannotCompileException(NotFoundException notFoundException) {
        this("cannot find " + notFoundException.getMessage(), notFoundException);
    }

    public CannotCompileException(CompileError compileError) {
        this("[source error] " + compileError.getMessage(), compileError);
    }

    public CannotCompileException(ClassNotFoundException classNotFoundException, String str) {
        this("cannot find " + str, classNotFoundException);
    }

    public CannotCompileException(ClassFormatError classFormatError, String str) {
        this("invalid class format: " + str, classFormatError);
    }
}
