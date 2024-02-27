package com.epson.iprojection.engine.common;

/* loaded from: classes.dex */
public class EngineException extends Exception {
    private static final long serialVersionUID = 0;
    private int m_nErrorCode;

    public EngineException(String str, int i) {
        super(str);
        this.m_nErrorCode = i;
    }

    public int GetErrorCode() {
        return this.m_nErrorCode;
    }
}
