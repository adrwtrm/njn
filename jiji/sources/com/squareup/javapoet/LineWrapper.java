package com.squareup.javapoet;

import java.io.IOException;

/* loaded from: classes2.dex */
final class LineWrapper {
    private boolean closed;
    private final int columnLimit;
    private final String indent;
    private FlushType nextFlush;
    private final RecordingAppendable out;
    private final StringBuilder buffer = new StringBuilder();
    private int column = 0;
    private int indentLevel = -1;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public enum FlushType {
        WRAP,
        SPACE,
        EMPTY
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LineWrapper(Appendable appendable, String str, int i) {
        Util.checkNotNull(appendable, "out == null", new Object[0]);
        this.out = new RecordingAppendable(appendable);
        this.indent = str;
        this.columnLimit = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public char lastChar() {
        return this.out.lastChar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void append(String str) throws IOException {
        int length;
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (this.nextFlush != null) {
            int indexOf = str.indexOf(10);
            if (indexOf == -1 && this.column + str.length() <= this.columnLimit) {
                this.buffer.append(str);
                this.column += str.length();
                return;
            }
            flush(indexOf == -1 || this.column + indexOf > this.columnLimit ? FlushType.WRAP : this.nextFlush);
        }
        this.out.append(str);
        int lastIndexOf = str.lastIndexOf(10);
        if (lastIndexOf != -1) {
            length = (str.length() - lastIndexOf) - 1;
        } else {
            length = str.length() + this.column;
        }
        this.column = length;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void wrappingSpace(int i) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        FlushType flushType = this.nextFlush;
        if (flushType != null) {
            flush(flushType);
        }
        this.column++;
        this.nextFlush = FlushType.SPACE;
        this.indentLevel = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void zeroWidthSpace(int i) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (this.column == 0) {
            return;
        }
        FlushType flushType = this.nextFlush;
        if (flushType != null) {
            flush(flushType);
        }
        this.nextFlush = FlushType.EMPTY;
        this.indentLevel = i;
    }

    void close() throws IOException {
        FlushType flushType = this.nextFlush;
        if (flushType != null) {
            flush(flushType);
        }
        this.closed = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.squareup.javapoet.LineWrapper$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$squareup$javapoet$LineWrapper$FlushType;

        static {
            int[] iArr = new int[FlushType.values().length];
            $SwitchMap$com$squareup$javapoet$LineWrapper$FlushType = iArr;
            try {
                iArr[FlushType.WRAP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$squareup$javapoet$LineWrapper$FlushType[FlushType.SPACE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$squareup$javapoet$LineWrapper$FlushType[FlushType.EMPTY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private void flush(FlushType flushType) throws IOException {
        int i;
        int i2 = AnonymousClass1.$SwitchMap$com$squareup$javapoet$LineWrapper$FlushType[flushType.ordinal()];
        if (i2 == 1) {
            this.out.append('\n');
            int i3 = 0;
            while (true) {
                i = this.indentLevel;
                if (i3 >= i) {
                    break;
                }
                this.out.append(this.indent);
                i3++;
            }
            int length = i * this.indent.length();
            this.column = length;
            this.column = length + this.buffer.length();
        } else if (i2 == 2) {
            this.out.append(' ');
        } else if (i2 != 3) {
            throw new IllegalArgumentException("Unknown FlushType: " + flushType);
        }
        this.out.append(this.buffer);
        StringBuilder sb = this.buffer;
        sb.delete(0, sb.length());
        this.indentLevel = -1;
        this.nextFlush = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class RecordingAppendable implements Appendable {
        private final Appendable delegate;
        char lastChar = 0;

        RecordingAppendable(Appendable appendable) {
            this.delegate = appendable;
        }

        @Override // java.lang.Appendable
        public Appendable append(CharSequence charSequence) throws IOException {
            int length = charSequence.length();
            if (length != 0) {
                this.lastChar = charSequence.charAt(length - 1);
            }
            return this.delegate.append(charSequence);
        }

        @Override // java.lang.Appendable
        public Appendable append(CharSequence charSequence, int i, int i2) throws IOException {
            return append(charSequence.subSequence(i, i2));
        }

        @Override // java.lang.Appendable
        public Appendable append(char c) throws IOException {
            this.lastChar = c;
            return this.delegate.append(c);
        }
    }
}
