package org.webrtc;

import java.util.Locale;

/* loaded from: classes.dex */
public class SessionDescription {
    public final String description;
    public final Type type;

    /* loaded from: classes.dex */
    public enum Type {
        OFFER,
        PRANSWER,
        ANSWER,
        ROLLBACK;

        public String canonicalForm() {
            return name().toLowerCase(Locale.US);
        }

        public static Type fromCanonicalForm(String str) {
            return (Type) valueOf(Type.class, str.toUpperCase(Locale.US));
        }
    }

    public SessionDescription(Type type, String str) {
        this.type = type;
        this.description = str;
    }

    String getDescription() {
        return this.description;
    }

    String getTypeInCanonicalForm() {
        return this.type.canonicalForm();
    }
}
