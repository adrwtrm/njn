package com.epson.iprojection.service.webrtc.core;

import com.serenegiant.media.AbstractAudioEncoder;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AudioDataProtocol.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001:\u0004\u0016\u0017\u0018\u0019B\u0005¢\u0006\u0002\u0010\u0002J3\u0010\u000b\u001a\u0004\u0018\u00010\f\"\u000e\b\u0000\u0010\r*\b\u0012\u0004\u0012\u0002H\r0\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\r0\u00102\u0006\u0010\u0011\u001a\u00020\f¢\u0006\u0002\u0010\u0012J\u0015\u0010\u0013\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0014\u001a\u00020\f¢\u0006\u0002\u0010\u0015R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol;", "", "()V", "mChannel", "Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol$eChannel;", "mCodec", "Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol$eCodec;", "mFrequency", "Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol$eFrequency;", "mQuantize", "Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol$eQuantize;", "getBit", "", "E", "", "enumClass", "Ljava/lang/Class;", "ordinal", "(Ljava/lang/Class;I)Ljava/lang/Integer;", "getChannelBit", "channel", "(I)Ljava/lang/Integer;", "eChannel", "eCodec", "eFrequency", "eQuantize", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class AudioDataProtocol {
    private final eChannel mChannel = eChannel.C_1_CHANNEL;
    private final eQuantize mQuantize = eQuantize.Q_8_BIT;
    private final eFrequency mFrequency = eFrequency.F_11025_KHZ;
    private final eCodec mCodec = eCodec.C_PCM;

    public final Integer getChannelBit(int i) {
        eChannel bitOf = eChannel.Companion.bitOf(i);
        if (bitOf != null) {
            return Integer.valueOf(bitOf.getBit());
        }
        return null;
    }

    public final <E extends Enum<E>> Integer getBit(Class<E> enumClass, int i) {
        Intrinsics.checkNotNullParameter(enumClass, "enumClass");
        enumClass.getEnumConstants();
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AudioDataProtocol.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\t\b\u0082\u0001\u0018\u0000 \u000b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000bB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007j\u0002\b\tj\u0002\b\n¨\u0006\f"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol$eChannel;", "", "channel", "", "bit", "(Ljava/lang/String;III)V", "getBit", "()I", "getChannel", "C_1_CHANNEL", "C_2_CHANNEL", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public enum eChannel {
        C_1_CHANNEL(1, 0),
        C_2_CHANNEL(2, 1);
        
        public static final Companion Companion = new Companion(null);
        private final int bit;
        private final int channel;

        eChannel(int i, int i2) {
            this.channel = i;
            this.bit = i2;
        }

        public final int getBit() {
            return this.bit;
        }

        public final int getChannel() {
            return this.channel;
        }

        /* compiled from: AudioDataProtocol.kt */
        @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006J5\u0010\u0007\u001a\u0004\u0018\u0001H\b\"\u0010\b\u0000\u0010\b*\n\u0012\u0004\u0012\u0002H\b\u0018\u00010\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\b0\u000b2\u0006\u0010\f\u001a\u00020\u0006¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol$eChannel$Companion;", "", "()V", "bitOf", "Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol$eChannel;", "valule", "", "fromOrdinal", "E", "", "enumClass", "Ljava/lang/Class;", "ordinal", "(Ljava/lang/Class;I)Ljava/lang/Enum;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
        /* loaded from: classes.dex */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final eChannel bitOf(int i) {
                return (eChannel) fromOrdinal(eChannel.class, i);
            }

            public final <E extends Enum<E>> E fromOrdinal(Class<E> enumClass, int i) {
                Intrinsics.checkNotNullParameter(enumClass, "enumClass");
                E[] enumConstants = enumClass.getEnumConstants();
                if (enumConstants != null) {
                    return enumConstants[i];
                }
                return null;
            }
        }
    }

    /* compiled from: AudioDataProtocol.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\t\b\u0082\u0001\u0018\u0000 \u000b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000bB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007j\u0002\b\tj\u0002\b\n¨\u0006\f"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol$eQuantize;", "", "quantize", "", "bit", "(Ljava/lang/String;III)V", "getBit", "()I", "getQuantize", "Q_8_BIT", "Q_16_BIT", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    private enum eQuantize {
        Q_8_BIT(8, 0),
        Q_16_BIT(16, 1);
        
        public static final Companion Companion = new Companion(null);
        private final int bit;
        private final int quantize;

        eQuantize(int i, int i2) {
            this.quantize = i;
            this.bit = i2;
        }

        public final int getBit() {
            return this.bit;
        }

        public final int getQuantize() {
            return this.quantize;
        }

        /* compiled from: AudioDataProtocol.kt */
        @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006J5\u0010\u0007\u001a\u0004\u0018\u0001H\b\"\u0010\b\u0000\u0010\b*\n\u0012\u0004\u0012\u0002H\b\u0018\u00010\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\b0\u000b2\u0006\u0010\f\u001a\u00020\u0006¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol$eQuantize$Companion;", "", "()V", "bitOf", "Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol$eQuantize;", "valule", "", "fromOrdinal", "E", "", "enumClass", "Ljava/lang/Class;", "ordinal", "(Ljava/lang/Class;I)Ljava/lang/Enum;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
        /* loaded from: classes.dex */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final eQuantize bitOf(int i) {
                return (eQuantize) fromOrdinal(eQuantize.class, i);
            }

            public final <E extends Enum<E>> E fromOrdinal(Class<E> enumClass, int i) {
                Intrinsics.checkNotNullParameter(enumClass, "enumClass");
                E[] enumConstants = enumClass.getEnumConstants();
                if (enumConstants != null) {
                    return enumConstants[i];
                }
                return null;
            }
        }
    }

    /* compiled from: AudioDataProtocol.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\b\u0082\u0001\u0018\u0000 \u00112\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0011B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007j\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010¨\u0006\u0012"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol$eFrequency;", "", "freq", "", "bit", "(Ljava/lang/String;III)V", "getBit", "()I", "getFreq", "F_11025_KHZ", "F_12000_KHZ", "F_16000_KHZ", "F_22050_KHZ", "F_24000_KHZ", "F_30000_KHZ", "F_44100_KHZ", "F_48000_KHZ", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    private enum eFrequency {
        F_11025_KHZ(11025, 0),
        F_12000_KHZ(12000, 1),
        F_16000_KHZ(16000, 2),
        F_22050_KHZ(22050, 3),
        F_24000_KHZ(24000, 4),
        F_30000_KHZ(30000, 5),
        F_44100_KHZ(AbstractAudioEncoder.DEFAULT_SAMPLE_RATE, 6),
        F_48000_KHZ(48000, 7);
        
        public static final Companion Companion = new Companion(null);
        private final int bit;
        private final int freq;

        eFrequency(int i, int i2) {
            this.freq = i;
            this.bit = i2;
        }

        public final int getBit() {
            return this.bit;
        }

        public final int getFreq() {
            return this.freq;
        }

        /* compiled from: AudioDataProtocol.kt */
        @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006J5\u0010\u0007\u001a\u0004\u0018\u0001H\b\"\u0010\b\u0000\u0010\b*\n\u0012\u0004\u0012\u0002H\b\u0018\u00010\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\b0\u000b2\u0006\u0010\f\u001a\u00020\u0006¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol$eFrequency$Companion;", "", "()V", "bitOf", "Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol$eFrequency;", "valule", "", "fromOrdinal", "E", "", "enumClass", "Ljava/lang/Class;", "ordinal", "(Ljava/lang/Class;I)Ljava/lang/Enum;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
        /* loaded from: classes.dex */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final eFrequency bitOf(int i) {
                return (eFrequency) fromOrdinal(eFrequency.class, i);
            }

            public final <E extends Enum<E>> E fromOrdinal(Class<E> enumClass, int i) {
                Intrinsics.checkNotNullParameter(enumClass, "enumClass");
                E[] enumConstants = enumClass.getEnumConstants();
                if (enumConstants != null) {
                    return enumConstants[i];
                }
                return null;
            }
        }
    }

    /* compiled from: AudioDataProtocol.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\b\b\u0082\u0001\u0018\u0000 \f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\fB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000b¨\u0006\r"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol$eCodec;", "", "codec", "", "bit", "", "(Ljava/lang/String;ILjava/lang/String;I)V", "getBit", "()I", "getCodec", "()Ljava/lang/String;", "C_PCM", "Companion", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    private enum eCodec {
        C_PCM("PCM", 0);
        
        public static final Companion Companion = new Companion(null);
        private final int bit;
        private final String codec;

        eCodec(String str, int i) {
            this.codec = str;
            this.bit = i;
        }

        public final int getBit() {
            return this.bit;
        }

        public final String getCodec() {
            return this.codec;
        }

        /* compiled from: AudioDataProtocol.kt */
        @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006J5\u0010\u0007\u001a\u0004\u0018\u0001H\b\"\u0010\b\u0000\u0010\b*\n\u0012\u0004\u0012\u0002H\b\u0018\u00010\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\b0\u000b2\u0006\u0010\f\u001a\u00020\u0006¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol$eCodec$Companion;", "", "()V", "bitOf", "Lcom/epson/iprojection/service/webrtc/core/AudioDataProtocol$eCodec;", "valule", "", "fromOrdinal", "E", "", "enumClass", "Ljava/lang/Class;", "ordinal", "(Ljava/lang/Class;I)Ljava/lang/Enum;", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
        /* loaded from: classes.dex */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final eCodec bitOf(int i) {
                return (eCodec) fromOrdinal(eCodec.class, i);
            }

            public final <E extends Enum<E>> E fromOrdinal(Class<E> enumClass, int i) {
                Intrinsics.checkNotNullParameter(enumClass, "enumClass");
                E[] enumConstants = enumClass.getEnumConstants();
                if (enumConstants != null) {
                    return enumConstants[i];
                }
                return null;
            }
        }
    }
}
