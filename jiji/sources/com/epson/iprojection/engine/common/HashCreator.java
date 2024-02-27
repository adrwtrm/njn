package com.epson.iprojection.engine.common;

import com.serenegiant.utils.HashUtils;
import java.security.MessageDigest;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Charsets;

/* compiled from: HashCreator.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/epson/iprojection/engine/common/HashCreator;", "", "()V", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class HashCreator {
    public static final Companion Companion = new Companion(null);

    @JvmStatic
    public static final byte[] CreateHashForSecuredEscvp(String str, String str2) {
        return Companion.CreateHashForSecuredEscvp(str, str2);
    }

    /* compiled from: HashCreator.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0007J\u0014\u0010\b\u001a\u0004\u0018\u00010\u00062\b\u0010\t\u001a\u0004\u0018\u00010\u0004H\u0002J\u0012\u0010\n\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000b\u001a\u00020\u0006H\u0002¨\u0006\f"}, d2 = {"Lcom/epson/iprojection/engine/common/HashCreator$Companion;", "", "()V", "CreateHashForSecuredEscvp", "", "password", "", "nonce", "convertStringToHexString", "bytes", "createHash", "text", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final byte[] CreateHashForSecuredEscvp(String password, String nonce) {
            Intrinsics.checkNotNullParameter(password, "password");
            Intrinsics.checkNotNullParameter(nonce, "nonce");
            String convertStringToHexString = convertStringToHexString(createHash("ESC/VP.net:" + password));
            if (convertStringToHexString == null) {
                return null;
            }
            return createHash(convertStringToHexString + ':' + nonce);
        }

        private final byte[] createHash(String str) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(HashUtils.HASH_ALGORITHM_SHA256);
                byte[] bytes = str.getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
                messageDigest.update(bytes);
                return messageDigest.digest();
            } catch (Exception unused) {
                return null;
            }
        }

        private final String convertStringToHexString(byte[] bArr) {
            if (bArr == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder(bArr.length * 2);
            for (byte b : bArr) {
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String format = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf((byte) (b & (-1)))}, 1));
                Intrinsics.checkNotNullExpressionValue(format, "format(format, *args)");
                sb.append(format);
            }
            return sb.toString();
        }
    }
}
