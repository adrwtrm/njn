package javassist.compiler;

import java.util.HashMap;

/* loaded from: classes2.dex */
public final class KeywordTable extends HashMap<String, Integer> {
    private static final long serialVersionUID = 1;

    public int lookup(String str) {
        if (containsKey(str)) {
            return get(str).intValue();
        }
        return -1;
    }

    public void append(String str, int i) {
        put(str, Integer.valueOf(i));
    }
}
