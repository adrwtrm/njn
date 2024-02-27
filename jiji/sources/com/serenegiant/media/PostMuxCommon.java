package com.serenegiant.media;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.text.TextUtils;
import android.util.Log;
import com.epson.iprojection.ui.activities.remote.RemotePrefUtils;
import com.serenegiant.io.ChannelHelper;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class PostMuxCommon {
    static final String AUDIO_NAME = "audio.raw";
    private static final boolean DEBUG = false;
    private static final byte[] RESERVED = new byte[40];
    private static final String TAG = "PostMuxCommon";
    static final int TYPE_AUDIO = 1;
    static final int TYPE_VIDEO = 0;
    static final String VIDEO_NAME = "video.raw";

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final void writeFormat(DataOutputStream dataOutputStream, MediaFormat mediaFormat, MediaFormat mediaFormat2) throws IOException {
        String asString = asString(mediaFormat);
        String asString2 = asString(mediaFormat2);
        writeHeader(dataOutputStream, 0, 0, -1L, (TextUtils.isEmpty(asString) ? 0 : asString.length()) + (TextUtils.isEmpty(asString2) ? 0 : asString2.length()), 0);
        dataOutputStream.writeUTF(asString);
        dataOutputStream.writeUTF(asString2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final void writeFormat(ByteChannel byteChannel, MediaFormat mediaFormat, MediaFormat mediaFormat2) throws IOException {
        String asString = asString(mediaFormat);
        String asString2 = asString(mediaFormat2);
        writeHeader(byteChannel, 0, 0, -1L, (TextUtils.isEmpty(asString) ? 0 : asString.length()) + (TextUtils.isEmpty(asString2) ? 0 : asString2.length()), 0);
        ChannelHelper.write(byteChannel, asString);
        ChannelHelper.write(byteChannel, asString2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static MediaFormat readFormat(DataInputStream dataInputStream) {
        try {
            readHeader(dataInputStream);
            dataInputStream.readUTF();
            return asMediaFormat(dataInputStream.readUTF());
        } catch (IOException e) {
            Log.e(TAG, "readFormat:", e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static MediaFormat readFormat(ByteChannel byteChannel) {
        try {
            readHeader(byteChannel);
            ChannelHelper.readString(byteChannel);
            return asMediaFormat(ChannelHelper.readString(byteChannel));
        } catch (IOException e) {
            Log.e(TAG, "readFormat:", e);
            return null;
        }
    }

    static final String asString(MediaFormat mediaFormat) {
        String str;
        JSONObject jSONObject = new JSONObject();
        try {
            if (mediaFormat.containsKey("mime")) {
                str = "aac-profile";
                jSONObject.put("mime", mediaFormat.getString("mime"));
            } else {
                str = "aac-profile";
            }
            if (mediaFormat.containsKey("width")) {
                jSONObject.put("width", mediaFormat.getInteger("width"));
            }
            if (mediaFormat.containsKey("height")) {
                jSONObject.put("height", mediaFormat.getInteger("height"));
            }
            if (mediaFormat.containsKey("bitrate")) {
                jSONObject.put("bitrate", mediaFormat.getInteger("bitrate"));
            }
            if (mediaFormat.containsKey("color-format")) {
                jSONObject.put("color-format", mediaFormat.getInteger("color-format"));
            }
            if (mediaFormat.containsKey("frame-rate")) {
                jSONObject.put("frame-rate", mediaFormat.getInteger("frame-rate"));
            }
            if (mediaFormat.containsKey("i-frame-interval")) {
                jSONObject.put("i-frame-interval", mediaFormat.getInteger("i-frame-interval"));
            }
            if (mediaFormat.containsKey("repeat-previous-frame-after")) {
                jSONObject.put("repeat-previous-frame-after", mediaFormat.getLong("repeat-previous-frame-after"));
            }
            if (mediaFormat.containsKey("max-input-size")) {
                jSONObject.put("max-input-size", mediaFormat.getInteger("max-input-size"));
            }
            if (mediaFormat.containsKey("durationUs")) {
                jSONObject.put("durationUs", mediaFormat.getInteger("durationUs"));
            }
            if (mediaFormat.containsKey("channel-count")) {
                jSONObject.put("channel-count", mediaFormat.getInteger("channel-count"));
            }
            if (mediaFormat.containsKey("sample-rate")) {
                jSONObject.put("sample-rate", mediaFormat.getInteger("sample-rate"));
            }
            if (mediaFormat.containsKey("channel-mask")) {
                jSONObject.put("channel-mask", mediaFormat.getInteger("channel-mask"));
            }
            String str2 = str;
            if (mediaFormat.containsKey(str2)) {
                jSONObject.put(str2, mediaFormat.getInteger(str2));
            }
            if (mediaFormat.containsKey("aac-sbr-mode")) {
                jSONObject.put("aac-sbr-mode", mediaFormat.getInteger("aac-sbr-mode"));
            }
            if (mediaFormat.containsKey("max-input-size")) {
                jSONObject.put("max-input-size", mediaFormat.getInteger("max-input-size"));
            }
            if (mediaFormat.containsKey("is-adts")) {
                jSONObject.put("is-adts", mediaFormat.getInteger("is-adts"));
            }
            if (mediaFormat.containsKey("what")) {
                jSONObject.put("what", mediaFormat.getInteger("what"));
            }
            if (mediaFormat.containsKey("csd-0")) {
                jSONObject.put("csd-0", asString(mediaFormat.getByteBuffer("csd-0")));
            }
            if (mediaFormat.containsKey("csd-1")) {
                jSONObject.put("csd-1", asString(mediaFormat.getByteBuffer("csd-1")));
            }
            if (mediaFormat.containsKey("csd-2")) {
                jSONObject.put("csd-2", asString(mediaFormat.getByteBuffer("csd-2")));
            }
        } catch (JSONException e) {
            Log.e(TAG, "writeFormat:", e);
        }
        return jSONObject.toString();
    }

    static final MediaFormat asMediaFormat(String str) {
        MediaFormat mediaFormat = new MediaFormat();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("mime")) {
                mediaFormat.setString("mime", (String) jSONObject.get("mime"));
            }
            if (jSONObject.has("width")) {
                mediaFormat.setInteger("width", ((Integer) jSONObject.get("width")).intValue());
            }
            if (jSONObject.has("height")) {
                mediaFormat.setInteger("height", ((Integer) jSONObject.get("height")).intValue());
            }
            if (jSONObject.has("bitrate")) {
                mediaFormat.setInteger("bitrate", ((Integer) jSONObject.get("bitrate")).intValue());
            }
            if (jSONObject.has("color-format")) {
                mediaFormat.setInteger("color-format", ((Integer) jSONObject.get("color-format")).intValue());
            }
            if (jSONObject.has("frame-rate")) {
                mediaFormat.setInteger("frame-rate", ((Integer) jSONObject.get("frame-rate")).intValue());
            }
            if (jSONObject.has("i-frame-interval")) {
                mediaFormat.setInteger("i-frame-interval", ((Integer) jSONObject.get("i-frame-interval")).intValue());
            }
            if (jSONObject.has("repeat-previous-frame-after")) {
                mediaFormat.setLong("repeat-previous-frame-after", ((Long) jSONObject.get("repeat-previous-frame-after")).longValue());
            }
            if (jSONObject.has("max-input-size")) {
                mediaFormat.setInteger("max-input-size", ((Integer) jSONObject.get("max-input-size")).intValue());
            }
            if (jSONObject.has("durationUs")) {
                mediaFormat.setInteger("durationUs", ((Integer) jSONObject.get("durationUs")).intValue());
            }
            if (jSONObject.has("channel-count")) {
                mediaFormat.setInteger("channel-count", ((Integer) jSONObject.get("channel-count")).intValue());
            }
            if (jSONObject.has("sample-rate")) {
                mediaFormat.setInteger("sample-rate", ((Integer) jSONObject.get("sample-rate")).intValue());
            }
            if (jSONObject.has("channel-mask")) {
                mediaFormat.setInteger("channel-mask", ((Integer) jSONObject.get("channel-mask")).intValue());
            }
            if (jSONObject.has("aac-profile")) {
                mediaFormat.setInteger("aac-profile", ((Integer) jSONObject.get("aac-profile")).intValue());
            }
            if (jSONObject.has("aac-sbr-mode")) {
                mediaFormat.setInteger("aac-sbr-mode", ((Integer) jSONObject.get("aac-sbr-mode")).intValue());
            }
            if (jSONObject.has("max-input-size")) {
                mediaFormat.setInteger("max-input-size", ((Integer) jSONObject.get("max-input-size")).intValue());
            }
            if (jSONObject.has("is-adts")) {
                mediaFormat.setInteger("is-adts", ((Integer) jSONObject.get("is-adts")).intValue());
            }
            if (jSONObject.has("what")) {
                mediaFormat.setInteger("what", ((Integer) jSONObject.get("what")).intValue());
            }
            if (jSONObject.has("csd-0")) {
                mediaFormat.setByteBuffer("csd-0", asByteBuffer((String) jSONObject.get("csd-0")));
            }
            if (jSONObject.has("csd-1")) {
                mediaFormat.setByteBuffer("csd-1", asByteBuffer((String) jSONObject.get("csd-1")));
            }
            if (jSONObject.has("csd-2")) {
                mediaFormat.setByteBuffer("csd-2", asByteBuffer((String) jSONObject.get("csd-2")));
                return mediaFormat;
            }
            return mediaFormat;
        } catch (JSONException e) {
            Log.e(TAG, "writeFormat:" + str, e);
            return null;
        }
    }

    static final String asString(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[16];
        StringBuilder sb = new StringBuilder();
        int limit = byteBuffer != null ? byteBuffer.limit() : 0;
        if (limit > 0) {
            byteBuffer.rewind();
            int min = Math.min(limit, 16);
            while (true) {
                limit -= min;
                if (min <= 0) {
                    break;
                }
                byteBuffer.get(bArr, 0, min);
                for (int i = 0; i < min; i++) {
                    sb.append((int) bArr[i]).append(',');
                }
                min = Math.min(limit, 16);
            }
        }
        return sb.toString();
    }

    static final ByteBuffer asByteBuffer(String str) {
        String[] split = str.split(RemotePrefUtils.SEPARATOR);
        int length = split.length;
        byte[] bArr = new byte[length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (!TextUtils.isEmpty(split[i2])) {
                bArr[i] = (byte) Integer.parseInt(split[i2]);
                i++;
            }
        }
        if (i > 0) {
            return ByteBuffer.wrap(bArr, 0, i);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class MediaFrameHeader {
        public int flags;
        public int frameNumber;
        public long presentationTimeUs;
        public int sequence;
        public int size;

        public MediaCodec.BufferInfo asBufferInfo() {
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            bufferInfo.set(0, this.size, this.presentationTimeUs, this.flags);
            return bufferInfo;
        }

        public MediaCodec.BufferInfo asBufferInfo(MediaCodec.BufferInfo bufferInfo) {
            bufferInfo.set(0, this.size, this.presentationTimeUs, this.flags);
            return bufferInfo;
        }

        public void writeTo(DataOutputStream dataOutputStream) throws IOException {
            dataOutputStream.writeInt(this.sequence);
            dataOutputStream.writeInt(this.frameNumber);
            dataOutputStream.writeLong(this.presentationTimeUs);
            dataOutputStream.writeInt(this.size);
            dataOutputStream.writeInt(this.flags);
            dataOutputStream.write(PostMuxCommon.RESERVED, 0, 40);
        }

        public void writeTo(ByteChannel byteChannel) throws IOException {
            ChannelHelper.write(byteChannel, this.sequence);
            ChannelHelper.write(byteChannel, this.sequence);
            ChannelHelper.write(byteChannel, this.frameNumber);
            ChannelHelper.write(byteChannel, this.presentationTimeUs);
            ChannelHelper.write(byteChannel, this.size);
            ChannelHelper.write(byteChannel, this.flags);
            ChannelHelper.write(byteChannel, PostMuxCommon.RESERVED);
        }

        public String toString() {
            return String.format(Locale.US, "MediaFrameHeader(sequence=%d,frameNumber=%d,presentationTimeUs=%d,size=%d,flags=%d)", Integer.valueOf(this.sequence), Integer.valueOf(this.frameNumber), Long.valueOf(this.presentationTimeUs), Integer.valueOf(this.size), Integer.valueOf(this.flags));
        }
    }

    static void writeHeader(DataOutputStream dataOutputStream, int i, int i2, long j, int i3, int i4) throws IOException {
        dataOutputStream.writeInt(i);
        dataOutputStream.writeInt(i2);
        dataOutputStream.writeLong(j);
        dataOutputStream.writeInt(i3);
        dataOutputStream.writeInt(i4);
        dataOutputStream.write(RESERVED, 0, 40);
    }

    static void writeHeader(ByteChannel byteChannel, int i, int i2, long j, int i3, int i4) throws IOException {
        ChannelHelper.write(byteChannel, i);
        ChannelHelper.write(byteChannel, i2);
        ChannelHelper.write(byteChannel, j);
        ChannelHelper.write(byteChannel, i3);
        ChannelHelper.write(byteChannel, i4);
        ChannelHelper.write(byteChannel, RESERVED);
    }

    static MediaFrameHeader readHeader(DataInputStream dataInputStream, MediaFrameHeader mediaFrameHeader) throws IOException {
        mediaFrameHeader.size = 0;
        mediaFrameHeader.sequence = dataInputStream.readInt();
        mediaFrameHeader.frameNumber = dataInputStream.readInt();
        mediaFrameHeader.presentationTimeUs = dataInputStream.readLong();
        mediaFrameHeader.size = dataInputStream.readInt();
        mediaFrameHeader.flags = dataInputStream.readInt();
        dataInputStream.skipBytes(40);
        return mediaFrameHeader;
    }

    static MediaFrameHeader readHeader(DataInputStream dataInputStream) throws IOException {
        return readHeader(dataInputStream, new MediaFrameHeader());
    }

    static MediaFrameHeader readHeader(ByteChannel byteChannel, MediaFrameHeader mediaFrameHeader) throws IOException {
        mediaFrameHeader.size = 0;
        mediaFrameHeader.sequence = ChannelHelper.readInt(byteChannel);
        mediaFrameHeader.frameNumber = ChannelHelper.readInt(byteChannel);
        mediaFrameHeader.presentationTimeUs = ChannelHelper.readLong(byteChannel);
        mediaFrameHeader.size = ChannelHelper.readInt(byteChannel);
        mediaFrameHeader.flags = ChannelHelper.readInt(byteChannel);
        ChannelHelper.readByteArray(byteChannel);
        return mediaFrameHeader;
    }

    static MediaFrameHeader readHeader(ByteChannel byteChannel) throws IOException {
        return readHeader(byteChannel, new MediaFrameHeader());
    }

    static int readFrameSize(DataInputStream dataInputStream) throws IOException {
        return readHeader(dataInputStream).size;
    }

    static int readFrameSize(ByteChannel byteChannel) throws IOException {
        return readHeader(byteChannel).size;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final void writeStream(DataOutputStream dataOutputStream, int i, int i2, MediaCodec.BufferInfo bufferInfo, ByteBuffer byteBuffer, byte[] bArr) throws IOException {
        byteBuffer.position(bufferInfo.offset);
        byteBuffer.get(bArr, 0, bufferInfo.size);
        writeHeader(dataOutputStream, i, i2, bufferInfo.presentationTimeUs, bufferInfo.size, bufferInfo.flags);
        dataOutputStream.write(bArr, 0, bufferInfo.size);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final void writeStream(ByteChannel byteChannel, int i, int i2, MediaCodec.BufferInfo bufferInfo, ByteBuffer byteBuffer) throws IOException {
        byteBuffer.position(bufferInfo.offset);
        byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
        writeHeader(byteChannel, i, i2, bufferInfo.presentationTimeUs, bufferInfo.size, bufferInfo.flags);
        ChannelHelper.write(byteChannel, byteBuffer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ByteBuffer readStream(DataInputStream dataInputStream, MediaFrameHeader mediaFrameHeader, ByteBuffer byteBuffer, byte[] bArr) throws IOException {
        readHeader(dataInputStream, mediaFrameHeader);
        if (byteBuffer == null || mediaFrameHeader.size > byteBuffer.capacity()) {
            byteBuffer = ByteBuffer.allocateDirect(mediaFrameHeader.size);
        }
        byteBuffer.clear();
        int min = Math.min(bArr.length, mediaFrameHeader.size);
        int i = mediaFrameHeader.size;
        while (i > 0) {
            int read = dataInputStream.read(bArr, 0, Math.min(i, min));
            if (read <= 0) {
                break;
            }
            byteBuffer.put(bArr, 0, read);
            i -= read;
        }
        byteBuffer.flip();
        return byteBuffer;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ByteBuffer readStream(ByteChannel byteChannel, MediaFrameHeader mediaFrameHeader, ByteBuffer byteBuffer) throws IOException {
        readHeader(byteChannel, mediaFrameHeader);
        if (byteBuffer == null || mediaFrameHeader.size > byteBuffer.capacity()) {
            byteBuffer = ByteBuffer.allocateDirect(mediaFrameHeader.size);
        }
        byteBuffer.clear();
        ChannelHelper.readByteBuffer(byteChannel, byteBuffer);
        return byteBuffer;
    }
}
