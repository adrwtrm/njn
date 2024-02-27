package com.serenegiant.mediastore;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import java.io.IOException;
import java.util.Locale;
import org.webrtc.MediaStreamTrack;

/* loaded from: classes2.dex */
public class MediaInfo implements Parcelable {
    public static final Parcelable.Creator<MediaInfo> CREATOR = new Parcelable.Creator<MediaInfo>() { // from class: com.serenegiant.mediastore.MediaInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaInfo createFromParcel(Parcel parcel) {
            return new MediaInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaInfo[] newArray(int i) {
            return new MediaInfo[i];
        }
    };
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaInfo";
    public String data;
    public String displayName;
    public int height;
    public long id;
    public int mediaType;
    public String mime;
    public int orientation;
    public String title;
    public int width;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MediaInfo() {
    }

    public MediaInfo(MediaInfo mediaInfo) {
        this.id = mediaInfo.id;
        this.data = mediaInfo.data;
        this.title = mediaInfo.title;
        this.mime = mediaInfo.mime;
        this.displayName = mediaInfo.displayName;
        this.mediaType = mediaInfo.mediaType;
        this.width = mediaInfo.width;
        this.height = mediaInfo.height;
        this.orientation = mediaInfo.orientation;
    }

    protected MediaInfo(Parcel parcel) {
        this.id = parcel.readLong();
        this.data = parcel.readString();
        this.title = parcel.readString();
        this.mime = parcel.readString();
        this.displayName = parcel.readString();
        this.mediaType = parcel.readInt();
        this.width = parcel.readInt();
        this.height = parcel.readInt();
        this.orientation = parcel.readInt();
    }

    MediaInfo(Cursor cursor) {
        this(cursor, cursor.getInt(7));
    }

    MediaInfo(Cursor cursor, int i) {
        this.id = cursor.getLong(0);
        this.data = cursor.getString(3);
        this.title = cursor.getString(1);
        this.mime = cursor.getString(2);
        this.displayName = cursor.getString(4);
        this.mediaType = i;
        try {
            this.width = cursor.getInt(5);
            this.height = cursor.getInt(6);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MediaInfo loadFromCursor(Cursor cursor) {
        return loadFromCursor(cursor, cursor.getInt(7));
    }

    MediaInfo loadFromCursor(Cursor cursor, int i) {
        this.id = cursor.getLong(0);
        this.data = cursor.getString(3);
        this.title = cursor.getString(1);
        this.mime = cursor.getString(2);
        this.displayName = cursor.getString(4);
        this.mediaType = i;
        try {
            this.width = cursor.getInt(5);
            this.height = cursor.getInt(6);
        } catch (Exception unused) {
        }
        return this;
    }

    public MediaInfo set(MediaInfo mediaInfo) {
        this.id = mediaInfo.id;
        this.data = mediaInfo.data;
        this.title = mediaInfo.title;
        this.mime = mediaInfo.mime;
        this.displayName = mediaInfo.displayName;
        this.mediaType = mediaInfo.mediaType;
        this.width = mediaInfo.width;
        this.height = mediaInfo.height;
        this.orientation = mediaInfo.orientation;
        return this;
    }

    public Uri getUri() {
        return MediaStoreUtils.getUri(this.mediaType, this.id);
    }

    public boolean canRead(ContentResolver contentResolver) {
        Uri uri = getUri();
        if (uri != null) {
            try {
                ParcelFileDescriptor openFileDescriptor = contentResolver.openFileDescriptor(uri, "r");
                if (openFileDescriptor != null) {
                    openFileDescriptor.close();
                    return true;
                }
                return false;
            } catch (IOException unused) {
                return false;
            }
        }
        return false;
    }

    public void checkError(ContentResolver contentResolver) throws IOException {
        Uri uri = getUri();
        if (uri != null) {
            ParcelFileDescriptor openFileDescriptor = contentResolver.openFileDescriptor(uri, "r");
            if (openFileDescriptor != null) {
                try {
                    if (openFileDescriptor.getFd() == 0) {
                        throw new IOException("Failed to get fd");
                    }
                    openFileDescriptor.checkError();
                    return;
                } finally {
                    openFileDescriptor.close();
                }
            }
            throw new IOException("Failed to open uri");
        }
        throw new IOException("Wrong uri");
    }

    public String toString() {
        return "MediaInfo{id=" + this.id + ", data='" + this.data + "', title='" + this.title + "', mime='" + this.mime + "', displayName='" + this.displayName + "', mediaType=" + this.mediaType + "(" + mediaType(this.mediaType) + "), width=" + this.width + ", height=" + this.height + ", orientation=" + this.orientation + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MediaInfo) {
            MediaInfo mediaInfo = (MediaInfo) obj;
            if (this.id == mediaInfo.id && this.mediaType == mediaInfo.mediaType && this.width == mediaInfo.width && this.height == mediaInfo.height && this.orientation == mediaInfo.orientation) {
                String str = this.data;
                if (str == null ? mediaInfo.data == null : str.equals(mediaInfo.data)) {
                    String str2 = this.title;
                    if (str2 == null ? mediaInfo.title == null : str2.equals(mediaInfo.title)) {
                        String str3 = this.mime;
                        if (str3 == null ? mediaInfo.mime == null : str3.equals(mediaInfo.mime)) {
                            String str4 = this.displayName;
                            String str5 = mediaInfo.displayName;
                            return str4 != null ? str4.equals(str5) : str5 == null;
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        long j = this.id;
        int i = ((int) (j ^ (j >>> 32))) * 31;
        String str = this.data;
        int hashCode = (i + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.title;
        int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.mime;
        int hashCode3 = (hashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        String str4 = this.displayName;
        return ((((((((hashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31) + this.mediaType) * 31) + this.width) * 31) + this.height) * 31) + this.orientation;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.id);
        parcel.writeString(this.data);
        parcel.writeString(this.title);
        parcel.writeString(this.mime);
        parcel.writeString(this.displayName);
        parcel.writeInt(this.mediaType);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeInt(this.orientation);
    }

    private static String mediaType(int i) {
        switch (i) {
            case 0:
                return "none";
            case 1:
                return "image";
            case 2:
                return MediaStreamTrack.AUDIO_TRACK_KIND;
            case 3:
                return MediaStreamTrack.VIDEO_TRACK_KIND;
            case 4:
                return "playlist";
            case 5:
                return "subtitle";
            case 6:
                return "document";
            default:
                return String.format(Locale.US, "unknown:%d", Integer.valueOf(i));
        }
    }
}
