package com.squareup.javapoet;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.lang.model.SourceVersion;

/* loaded from: classes2.dex */
public final class NameAllocator implements Cloneable {
    private final Set<String> allocatedNames;
    private final Map<Object, String> tagToName;

    public NameAllocator() {
        this(new LinkedHashSet(), new LinkedHashMap());
    }

    private NameAllocator(LinkedHashSet<String> linkedHashSet, LinkedHashMap<Object, String> linkedHashMap) {
        this.allocatedNames = linkedHashSet;
        this.tagToName = linkedHashMap;
    }

    public String newName(String str) {
        return newName(str, UUID.randomUUID().toString());
    }

    public String newName(String str, Object obj) {
        Util.checkNotNull(str, "suggestion", new Object[0]);
        Util.checkNotNull(obj, "tag", new Object[0]);
        String javaIdentifier = toJavaIdentifier(str);
        while (true) {
            if (!SourceVersion.isKeyword(javaIdentifier) && this.allocatedNames.add(javaIdentifier)) {
                break;
            }
            javaIdentifier = javaIdentifier + "_";
        }
        String put = this.tagToName.put(obj, javaIdentifier);
        if (put == null) {
            return javaIdentifier;
        }
        this.tagToName.put(obj, put);
        throw new IllegalArgumentException("tag " + obj + " cannot be used for both '" + put + "' and '" + javaIdentifier + "'");
    }

    public static String toJavaIdentifier(String str) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            int codePointAt = str.codePointAt(i);
            if (i == 0 && !Character.isJavaIdentifierStart(codePointAt) && Character.isJavaIdentifierPart(codePointAt)) {
                sb.append("_");
            }
            sb.appendCodePoint(Character.isJavaIdentifierPart(codePointAt) ? codePointAt : 95);
            i += Character.charCount(codePointAt);
        }
        return sb.toString();
    }

    public String get(Object obj) {
        String str = this.tagToName.get(obj);
        if (str != null) {
            return str;
        }
        throw new IllegalArgumentException("unknown tag: " + obj);
    }

    /* renamed from: clone */
    public NameAllocator m291clone() {
        return new NameAllocator(new LinkedHashSet(this.allocatedNames), new LinkedHashMap(this.tagToName));
    }
}
