package com.squareup.javapoet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Modifier;
import kotlin.text.Typography;

/* loaded from: classes2.dex */
final class Util {
    private Util() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> Map<K, List<V>> immutableMultimap(Map<K, List<V>> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                linkedHashMap.put(entry.getKey(), immutableList(entry.getValue()));
            }
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> Map<K, V> immutableMap(Map<K, V> map) {
        return Collections.unmodifiableMap(new LinkedHashMap(map));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void checkArgument(boolean z, String str, Object... objArr) {
        if (!z) {
            throw new IllegalArgumentException(String.format(str, objArr));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> T checkNotNull(T t, String str, Object... objArr) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void checkState(boolean z, String str, Object... objArr) {
        if (!z) {
            throw new IllegalStateException(String.format(str, objArr));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> List<T> immutableList(Collection<T> collection) {
        return Collections.unmodifiableList(new ArrayList(collection));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Set<T> immutableSet(Collection<T> collection) {
        return Collections.unmodifiableSet(new LinkedHashSet(collection));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Set<T> union(Set<T> set, Set<T> set2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.addAll(set);
        linkedHashSet.addAll(set2);
        return linkedHashSet;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void requireExactlyOneOf(Set<Modifier> set, Modifier... modifierArr) {
        int i = 0;
        for (Modifier modifier : modifierArr) {
            if (set.contains(modifier)) {
                i++;
            }
        }
        checkArgument(i == 1, "modifiers %s must contain one of %s", set, Arrays.toString(modifierArr));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String characterLiteralWithoutSingleQuotes(char c) {
        if (c != '\f') {
            if (c != '\r') {
                if (c != '\"') {
                    if (c != '\'') {
                        if (c != '\\') {
                            switch (c) {
                                case '\b':
                                    return "\\b";
                                case '\t':
                                    return "\\t";
                                case '\n':
                                    return "\\n";
                                default:
                                    return Character.isISOControl(c) ? String.format("\\u%04x", Integer.valueOf(c)) : Character.toString(c);
                            }
                        }
                        return "\\\\";
                    }
                    return "\\'";
                }
                return "\"";
            }
            return "\\r";
        }
        return "\\f";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String stringLiteralWithDoubleQuotes(String str, String str2) {
        StringBuilder sb = new StringBuilder(str.length() + 2);
        sb.append(Typography.quote);
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '\'') {
                sb.append("'");
            } else if (charAt == '\"') {
                sb.append("\\\"");
            } else {
                sb.append(characterLiteralWithoutSingleQuotes(charAt));
                if (charAt == '\n' && i + 1 < str.length()) {
                    sb.append("\"\n").append(str2).append(str2).append("+ \"");
                }
            }
        }
        sb.append(Typography.quote);
        return sb.toString();
    }
}
