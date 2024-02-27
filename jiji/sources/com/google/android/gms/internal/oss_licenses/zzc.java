package com.google.android.gms.internal.oss_licenses;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class zzc implements Parcelable, Comparable<zzc> {
    public static final Parcelable.Creator<zzc> CREATOR = new zzd();
    private final String zzae;
    private final long zzaf;
    private final int zzag;
    private final String zzah;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzc zza(String str, long j, int i, String str2) {
        return new zzc(str, j, i, str2);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.zzae);
        parcel.writeLong(this.zzaf);
        parcel.writeInt(this.zzag);
        parcel.writeString(this.zzah);
    }

    public final String toString() {
        return this.zzae;
    }

    private zzc(String str, long j, int i, String str2) {
        this.zzae = str;
        this.zzaf = j;
        this.zzag = i;
        this.zzah = str2;
    }

    private zzc(Parcel parcel) {
        this.zzae = parcel.readString();
        this.zzaf = parcel.readLong();
        this.zzag = parcel.readInt();
        this.zzah = parcel.readString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final long zze() {
        return this.zzaf;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int zzf() {
        return this.zzag;
    }

    public final String getPath() {
        return this.zzah;
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(zzc zzcVar) {
        return this.zzae.compareToIgnoreCase(zzcVar.zzae);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzc(Parcel parcel, zzd zzdVar) {
        this(parcel);
    }
}
