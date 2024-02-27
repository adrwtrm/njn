package com.serenegiant.media;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.ParcelFileDescriptor;
import androidx.documentfile.provider.DocumentFile;
import com.serenegiant.system.BuildCheck;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public interface IMuxer {

    /* loaded from: classes2.dex */
    public interface IMuxerFactory {
        IMuxer createMuxer(Context context, boolean z, DocumentFile documentFile) throws IOException;

        @Deprecated
        IMuxer createMuxer(boolean z, int i) throws IOException;

        @Deprecated
        IMuxer createMuxer(boolean z, String str) throws IOException;
    }

    int addTrack(MediaFormat mediaFormat);

    boolean isStarted();

    void release();

    void start();

    void stop();

    void writeSampleData(int i, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo);

    /* loaded from: classes2.dex */
    public static class DefaultFactory implements IMuxerFactory {
        @Override // com.serenegiant.media.IMuxer.IMuxerFactory
        public IMuxer createMuxer(boolean z, String str) throws IOException {
            if (z && BuildCheck.isAPI18()) {
                return new MediaMuxerWrapper(str, 0);
            }
            throw new IOException("Unsupported muxer type");
        }

        @Override // com.serenegiant.media.IMuxer.IMuxerFactory
        public IMuxer createMuxer(boolean z, int i) throws IOException {
            if (z && BuildCheck.isAPI18()) {
                if (BuildCheck.isAPI29()) {
                    throw new UnsupportedOperationException("createMuxer from fd does not support on API29 and later devices");
                }
                if (BuildCheck.isAPI26()) {
                    return new MediaMuxerWrapper(ParcelFileDescriptor.fromFd(i).getFileDescriptor(), 0);
                }
                throw new UnsupportedOperationException("createMuxer from fd does not support on API<26");
            }
            throw new IOException("Unsupported muxer type");
        }

        @Override // com.serenegiant.media.IMuxer.IMuxerFactory
        public IMuxer createMuxer(Context context, boolean z, DocumentFile documentFile) throws IOException {
            documentFile.getUri();
            MediaMuxerWrapper newInstance = (z && BuildCheck.isAPI18()) ? MediaMuxerWrapper.newInstance(context, documentFile, 0) : null;
            if (newInstance != null) {
                return newInstance;
            }
            throw new IOException("Unsupported muxer type");
        }
    }
}
