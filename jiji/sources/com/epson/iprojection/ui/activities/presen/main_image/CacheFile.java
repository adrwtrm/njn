package com.epson.iprojection.ui.activities.presen.main_image;

import android.graphics.Bitmap;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.PathGetter;
import com.epson.iprojection.ui.common.exception.BitmapMemoryException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/* loaded from: classes.dex */
public class CacheFile {
    String _path;
    MappedByteBuffer _buffer = null;
    Bitmap.Config _bitmapConfig = null;
    int _w = 0;
    int _h = 0;

    public CacheFile(int i, String str) {
        this._path = PathGetter.getIns().getCacheDirPath() + "/" + str + "_task" + i + ".raw";
    }

    public void save(Bitmap bitmap) {
        MappedByteBuffer mappedByteBuffer;
        if (bitmap == null) {
            return;
        }
        StringBuilder sb = new StringBuilder("セーブ [");
        String str = this._path;
        Lg.d(sb.append(str.substring(str.lastIndexOf(47) + 1)).append("]").toString());
        if (this._w == bitmap.getWidth() && this._h == bitmap.getHeight() && this._bitmapConfig == bitmap.getConfig() && (mappedByteBuffer = this._buffer) != null) {
            mappedByteBuffer.position(0);
        } else {
            this._buffer = null;
            this._w = bitmap.getWidth();
            this._h = bitmap.getHeight();
            this._bitmapConfig = bitmap.getConfig();
            MappedByteBuffer mappedBufferForBitmap = getMappedBufferForBitmap(bitmap.getRowBytes() * this._h);
            this._buffer = mappedBufferForBitmap;
            if (mappedBufferForBitmap == null) {
                return;
            }
        }
        bitmap.copyPixelsToBuffer(this._buffer);
    }

    public Bitmap load() throws BitmapMemoryException {
        if (this._buffer == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder("ロード [");
        String str = this._path;
        Lg.d(sb.append(str.substring(str.lastIndexOf(47) + 1)).append("]").toString());
        this._buffer.position(0);
        Bitmap createBitmap = Bitmap.createBitmap(this._w, this._h, Bitmap.Config.RGB_565);
        createBitmap.copyPixelsFromBuffer(this._buffer);
        return createBitmap;
    }

    public void delete() {
        MappedByteBuffer mappedByteBuffer = this._buffer;
        if (mappedByteBuffer != null) {
            mappedByteBuffer.clear();
            this._buffer = null;
        }
    }

    private MappedByteBuffer getMappedBufferForBitmap(long j) {
        try {
            File file = new File(this._path);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            MappedByteBuffer map = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0L, j);
            randomAccessFile.close();
            file.delete();
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public MappedByteBuffer createMappedBuffer(String str, long j) {
        try {
            File file = new File(str);
            Lg.d("Create catche file:" + file);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0L, j);
            channel.close();
            randomAccessFile.close();
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
