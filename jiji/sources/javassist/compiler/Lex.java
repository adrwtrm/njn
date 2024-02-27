package javassist.compiler;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.epson.iprojection.common.CommonDefine;

/* loaded from: classes2.dex */
public class Lex implements TokenId {
    private static final int[] equalOps = {TokenId.NEQ, 0, 0, 0, TokenId.MOD_E, TokenId.AND_E, 0, 0, 0, TokenId.MUL_E, TokenId.PLUS_E, 0, TokenId.MINUS_E, 0, TokenId.DIV_E, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, TokenId.LE, TokenId.EQ, TokenId.GE, 0};
    private static final KeywordTable ktable;
    private String input;
    private int maxlen;
    private int lastChar = -1;
    private StringBuffer textBuffer = new StringBuffer();
    private Token currentToken = new Token();
    private Token lookAheadTokens = null;
    private int position = 0;
    private int lineNumber = 0;

    private static boolean isBlank(int i) {
        return i == 32 || i == 9 || i == 12 || i == 13 || i == 10;
    }

    private static boolean isDigit(int i) {
        return 48 <= i && i <= 57;
    }

    public Lex(String str) {
        this.input = str;
        this.maxlen = str.length();
    }

    public int get() {
        Token token = this.lookAheadTokens;
        if (token == null) {
            return get(this.currentToken);
        }
        this.currentToken = token;
        this.lookAheadTokens = token.next;
        return token.tokenId;
    }

    public int lookAhead() {
        return lookAhead(0);
    }

    public int lookAhead(int i) {
        Token token = this.lookAheadTokens;
        if (token == null) {
            token = this.currentToken;
            this.lookAheadTokens = token;
            token.next = null;
            get(token);
        }
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                if (token.next == null) {
                    Token token2 = new Token();
                    token.next = token2;
                    get(token2);
                }
                token = token.next;
                i = i2;
            } else {
                this.currentToken = token;
                return token.tokenId;
            }
        }
    }

    public String getString() {
        return this.currentToken.textValue;
    }

    public long getLong() {
        return this.currentToken.longValue;
    }

    public double getDouble() {
        return this.currentToken.doubleValue;
    }

    private int get(Token token) {
        int readLine;
        do {
            readLine = readLine(token);
        } while (readLine == 10);
        token.tokenId = readLine;
        return readLine;
    }

    private int readLine(Token token) {
        int nextNonWhiteChar = getNextNonWhiteChar();
        if (nextNonWhiteChar < 0) {
            return nextNonWhiteChar;
        }
        if (nextNonWhiteChar == 10) {
            this.lineNumber++;
            return 10;
        } else if (nextNonWhiteChar == 39) {
            return readCharConst(token);
        } else {
            if (nextNonWhiteChar == 34) {
                return readStringL(token);
            }
            if (48 > nextNonWhiteChar || nextNonWhiteChar > 57) {
                if (nextNonWhiteChar == 46) {
                    int cVar = getc();
                    if (48 <= cVar && cVar <= 57) {
                        StringBuffer stringBuffer = this.textBuffer;
                        stringBuffer.setLength(0);
                        stringBuffer.append('.');
                        return readDouble(stringBuffer, cVar, token);
                    }
                    ungetc(cVar);
                    return readSeparator(46);
                } else if (Character.isJavaIdentifierStart((char) nextNonWhiteChar)) {
                    return readIdentifier(nextNonWhiteChar, token);
                } else {
                    return readSeparator(nextNonWhiteChar);
                }
            }
            return readNumber(nextNonWhiteChar, token);
        }
    }

    private int getNextNonWhiteChar() {
        int cVar;
        do {
            cVar = getc();
            if (cVar == 47) {
                int cVar2 = getc();
                if (cVar2 == 47) {
                    do {
                        cVar = getc();
                        if (cVar == 10 || cVar == 13) {
                            break;
                        }
                    } while (cVar != -1);
                } else if (cVar2 == 42) {
                    while (true) {
                        cVar = getc();
                        if (cVar == -1) {
                            break;
                        } else if (cVar == 42) {
                            int cVar3 = getc();
                            if (cVar3 == 47) {
                                cVar = 32;
                                break;
                            }
                            ungetc(cVar3);
                        }
                    }
                } else {
                    ungetc(cVar2);
                    cVar = 47;
                }
            }
        } while (isBlank(cVar));
        return cVar;
    }

    private int readCharConst(Token token) {
        int i = 0;
        while (true) {
            int cVar = getc();
            if (cVar == 39) {
                token.longValue = i;
                return 401;
            } else if (cVar == 92) {
                i = readEscapeChar();
            } else if (cVar < 32) {
                if (cVar == 10) {
                    this.lineNumber++;
                    return 500;
                }
                return 500;
            } else {
                i = cVar;
            }
        }
    }

    private int readEscapeChar() {
        int cVar = getc();
        if (cVar == 110) {
            return 10;
        }
        if (cVar == 116) {
            return 9;
        }
        if (cVar == 114) {
            return 13;
        }
        if (cVar == 102) {
            return 12;
        }
        if (cVar == 10) {
            this.lineNumber++;
            return cVar;
        }
        return cVar;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0023, code lost:
        r5.lineNumber++;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x002b, code lost:
        return 500;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int readStringL(javassist.compiler.Token r6) {
        /*
            r5 = this;
            java.lang.StringBuffer r0 = r5.textBuffer
            r1 = 0
            r0.setLength(r1)
        L6:
            int r1 = r5.getc()
            r2 = 10
            r3 = 34
            if (r1 == r3) goto L2c
            r3 = 92
            if (r1 != r3) goto L19
            int r1 = r5.readEscapeChar()
            goto L1e
        L19:
            if (r1 == r2) goto L23
            if (r1 >= 0) goto L1e
            goto L23
        L1e:
            char r1 = (char) r1
            r0.append(r1)
            goto L6
        L23:
            int r6 = r5.lineNumber
            int r6 = r6 + 1
            r5.lineNumber = r6
            r6 = 500(0x1f4, float:7.0E-43)
            return r6
        L2c:
            int r1 = r5.getc()
            if (r1 != r2) goto L39
            int r1 = r5.lineNumber
            int r1 = r1 + 1
            r5.lineNumber = r1
            goto L2c
        L39:
            boolean r4 = isBlank(r1)
            if (r4 != 0) goto L2c
            if (r1 == r3) goto L6
            r5.ungetc(r1)
            java.lang.String r0 = r0.toString()
            r6.textValue = r0
            r6 = 406(0x196, float:5.69E-43)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: javassist.compiler.Lex.readStringL(javassist.compiler.Token):int");
    }

    private int readNumber(int i, Token token) {
        int cVar;
        long j;
        int i2;
        int i3;
        int cVar2;
        int cVar3 = getc();
        if (i == 48) {
            if (cVar3 == 88 || cVar3 == 120) {
                long j2 = 0;
                while (true) {
                    cVar = getc();
                    if (48 > cVar || cVar > 57) {
                        if (65 <= cVar && cVar <= 70) {
                            j = j2 * 16;
                            i2 = cVar - 65;
                        } else if (97 > cVar || cVar > 102) {
                            break;
                        } else {
                            j = j2 * 16;
                            i2 = cVar - 97;
                        }
                        i3 = i2 + 10;
                    } else {
                        j = j2 * 16;
                        i3 = cVar - 48;
                    }
                    j2 = j + i3;
                }
                token.longValue = j2;
                if (cVar == 76 || cVar == 108) {
                    return 403;
                }
                ungetc(cVar);
                return 402;
            } else if (48 <= cVar3 && cVar3 <= 55) {
                long j3 = cVar3 - 48;
                while (true) {
                    cVar2 = getc();
                    if (48 > cVar2 || cVar2 > 55) {
                        break;
                    }
                    j3 = (j3 * 8) + (cVar2 - 48);
                }
                token.longValue = j3;
                if (cVar2 == 76 || cVar2 == 108) {
                    return 403;
                }
                ungetc(cVar2);
                return 402;
            }
        }
        long j4 = i - 48;
        while (48 <= cVar3 && cVar3 <= 57) {
            j4 = ((j4 * 10) + cVar3) - 48;
            cVar3 = getc();
        }
        token.longValue = j4;
        if (cVar3 == 70 || cVar3 == 102) {
            token.doubleValue = j4;
            return TokenId.FloatConstant;
        } else if (cVar3 == 69 || cVar3 == 101 || cVar3 == 68 || cVar3 == 100 || cVar3 == 46) {
            StringBuffer stringBuffer = this.textBuffer;
            stringBuffer.setLength(0);
            stringBuffer.append(j4);
            return readDouble(stringBuffer, cVar3, token);
        } else if (cVar3 == 76 || cVar3 == 108) {
            return 403;
        } else {
            ungetc(cVar3);
            return 402;
        }
    }

    private int readDouble(StringBuffer stringBuffer, int i, Token token) {
        if (i != 69 && i != 101 && i != 68 && i != 100) {
            stringBuffer.append((char) i);
            while (true) {
                i = getc();
                if (48 > i || i > 57) {
                    break;
                }
                stringBuffer.append((char) i);
            }
        }
        if (i == 69 || i == 101) {
            stringBuffer.append((char) i);
            i = getc();
            if (i == 43 || i == 45) {
                stringBuffer.append((char) i);
                i = getc();
            }
            while (48 <= i && i <= 57) {
                stringBuffer.append((char) i);
                i = getc();
            }
        }
        try {
            token.doubleValue = Double.parseDouble(stringBuffer.toString());
            if (i == 70 || i == 102) {
                return TokenId.FloatConstant;
            }
            if (i == 68 || i == 100) {
                return TokenId.DoubleConstant;
            }
            ungetc(i);
            return TokenId.DoubleConstant;
        } catch (NumberFormatException unused) {
            return 500;
        }
    }

    static {
        KeywordTable keywordTable = new KeywordTable();
        ktable = keywordTable;
        keywordTable.append("abstract", 300);
        keywordTable.append(TypedValues.Custom.S_BOOLEAN, 301);
        keywordTable.append("break", 302);
        keywordTable.append("byte", 303);
        keywordTable.append("case", 304);
        keywordTable.append("catch", 305);
        keywordTable.append("char", 306);
        keywordTable.append("class", 307);
        keywordTable.append("const", 308);
        keywordTable.append("continue", 309);
        keywordTable.append("default", 310);
        keywordTable.append("do", 311);
        keywordTable.append("double", 312);
        keywordTable.append("else", 313);
        keywordTable.append("extends", 314);
        keywordTable.append(CommonDefine.FALSE, TokenId.FALSE);
        keywordTable.append("final", 315);
        keywordTable.append("finally", 316);
        keywordTable.append(TypedValues.Custom.S_FLOAT, 317);
        keywordTable.append("for", 318);
        keywordTable.append("goto", TokenId.GOTO);
        keywordTable.append("if", TokenId.IF);
        keywordTable.append("implements", TokenId.IMPLEMENTS);
        keywordTable.append("import", TokenId.IMPORT);
        keywordTable.append("instanceof", TokenId.INSTANCEOF);
        keywordTable.append("int", TokenId.INT);
        keywordTable.append("interface", TokenId.INTERFACE);
        keywordTable.append("long", TokenId.LONG);
        keywordTable.append("native", TokenId.NATIVE);
        keywordTable.append("new", TokenId.NEW);
        keywordTable.append("null", TokenId.NULL);
        keywordTable.append("package", TokenId.PACKAGE);
        keywordTable.append("private", TokenId.PRIVATE);
        keywordTable.append("protected", TokenId.PROTECTED);
        keywordTable.append("public", TokenId.PUBLIC);
        keywordTable.append("return", TokenId.RETURN);
        keywordTable.append("short", TokenId.SHORT);
        keywordTable.append("static", TokenId.STATIC);
        keywordTable.append("strictfp", TokenId.STRICT);
        keywordTable.append("super", TokenId.SUPER);
        keywordTable.append("switch", TokenId.SWITCH);
        keywordTable.append("synchronized", TokenId.SYNCHRONIZED);
        keywordTable.append("this", TokenId.THIS);
        keywordTable.append("throw", TokenId.THROW);
        keywordTable.append("throws", TokenId.THROWS);
        keywordTable.append("transient", TokenId.TRANSIENT);
        keywordTable.append(CommonDefine.TRUE, TokenId.TRUE);
        keywordTable.append("try", TokenId.TRY);
        keywordTable.append("void", TokenId.VOID);
        keywordTable.append("volatile", TokenId.VOLATILE);
        keywordTable.append("while", TokenId.WHILE);
    }

    private int readSeparator(int i) {
        int cVar;
        if (33 <= i && i <= 63) {
            int i2 = equalOps[i - 33];
            if (i2 == 0) {
                return i;
            }
            cVar = getc();
            if (i == cVar) {
                if (i != 38) {
                    if (i != 43) {
                        if (i != 45) {
                            switch (i) {
                                case 60:
                                    int cVar2 = getc();
                                    if (cVar2 == 61) {
                                        return TokenId.LSHIFT_E;
                                    }
                                    ungetc(cVar2);
                                    return TokenId.LSHIFT;
                                case 61:
                                    return TokenId.EQ;
                                case 62:
                                    int cVar3 = getc();
                                    if (cVar3 == 61) {
                                        return TokenId.RSHIFT_E;
                                    }
                                    if (cVar3 == 62) {
                                        int cVar4 = getc();
                                        if (cVar4 == 61) {
                                            return TokenId.ARSHIFT_E;
                                        }
                                        ungetc(cVar4);
                                        return TokenId.ARSHIFT;
                                    }
                                    ungetc(cVar3);
                                    return TokenId.RSHIFT;
                            }
                        }
                        return TokenId.MINUSMINUS;
                    }
                    return TokenId.PLUSPLUS;
                }
                return TokenId.ANDAND;
            } else if (cVar == 61) {
                return i2;
            }
        } else if (i != 94) {
            if (i == 124) {
                cVar = getc();
                if (cVar == 61) {
                    return TokenId.OR_E;
                }
                if (cVar == 124) {
                    return TokenId.OROR;
                }
            }
            return i;
        } else {
            cVar = getc();
            if (cVar == 61) {
                return TokenId.EXOR_E;
            }
        }
        ungetc(cVar);
        return i;
    }

    private int readIdentifier(int i, Token token) {
        StringBuffer stringBuffer = this.textBuffer;
        stringBuffer.setLength(0);
        do {
            stringBuffer.append((char) i);
            i = getc();
        } while (Character.isJavaIdentifierPart((char) i));
        ungetc(i);
        String stringBuffer2 = stringBuffer.toString();
        int lookup = ktable.lookup(stringBuffer2);
        if (lookup >= 0) {
            return lookup;
        }
        token.textValue = stringBuffer2;
        return TokenId.Identifier;
    }

    private void ungetc(int i) {
        this.lastChar = i;
    }

    public String getTextAround() {
        int i = this.position;
        int i2 = i - 10;
        if (i2 < 0) {
            i2 = 0;
        }
        int i3 = i + 10;
        int i4 = this.maxlen;
        if (i3 > i4) {
            i3 = i4;
        }
        return this.input.substring(i2, i3);
    }

    private int getc() {
        int i = this.lastChar;
        if (i < 0) {
            int i2 = this.position;
            if (i2 < this.maxlen) {
                String str = this.input;
                this.position = i2 + 1;
                return str.charAt(i2);
            }
            return -1;
        }
        this.lastChar = -1;
        return i;
    }
}
