package com.serenegiant.system;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class StorageInfo implements Parcelable {
    public static final Parcelable.Creator<StorageInfo> CREATOR = new Parcelable.Creator<StorageInfo>() { // from class: com.serenegiant.system.StorageInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StorageInfo createFromParcel(Parcel parcel) {
            return new StorageInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StorageInfo[] newArray(int i) {
            return new StorageInfo[i];
        }
    };
    public long freeBytes;
    public long totalBytes;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public StorageInfo(long j, long j2) {
        this.totalBytes = j;
        this.freeBytes = j2;
    }

    protected StorageInfo(Parcel parcel) {
        this.totalBytes = parcel.readLong();
        this.freeBytes = parcel.readLong();
    }

    public String toString() {
        return "StorageInfo{totalBytes=" + this.totalBytes + ", freeBytes=" + this.freeBytes + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.totalBytes);
        parcel.writeLong(this.freeBytes);
    }
}
