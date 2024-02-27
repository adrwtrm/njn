package com.serenegiant.gl;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class TextureWrapper implements GLConst, Parcelable {
    public static final Parcelable.Creator<TextureWrapper> CREATOR = new Parcelable.Creator<TextureWrapper>() { // from class: com.serenegiant.gl.TextureWrapper.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextureWrapper createFromParcel(Parcel parcel) {
            return new TextureWrapper(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextureWrapper[] newArray(int i) {
            return new TextureWrapper[i];
        }
    };
    public final int height;
    public final int texId;
    public final int texTarget;
    public final int texUnit;
    public final int width;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TextureWrapper(int i, int i2, int i3, int i4, int i5) {
        this.texTarget = i;
        this.texUnit = i2;
        this.texId = i3;
        this.width = i4;
        this.height = i5;
    }

    protected TextureWrapper(Parcel parcel) {
        this.texTarget = parcel.readInt();
        this.texUnit = parcel.readInt();
        this.texId = parcel.readInt();
        this.width = parcel.readInt();
        this.height = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.texTarget);
        parcel.writeInt(this.texUnit);
        parcel.writeInt(this.texId);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
    }
}
