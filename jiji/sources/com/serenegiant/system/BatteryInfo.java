package com.serenegiant.system;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class BatteryInfo implements Parcelable {
    public static final int BATTERY_PLUGGED_ANY = 7;
    public static final Parcelable.Creator<BatteryInfo> CREATOR = new Parcelable.Creator<BatteryInfo>() { // from class: com.serenegiant.system.BatteryInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BatteryInfo createFromParcel(Parcel parcel) {
            return new BatteryInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BatteryInfo[] newArray(int i) {
            return new BatteryInfo[i];
        }
    };
    public float battery;
    public int health;
    public int level;
    public int plugged;
    public int scale;
    public int status;
    public String technology;
    public float temperature;
    public float voltage;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface BatteryHealth {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface BatteryPlugged {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface BatteryStatus {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BatteryInfo() {
        clear();
    }

    public BatteryInfo(BatteryInfo batteryInfo) {
        this.level = batteryInfo.level;
        this.scale = batteryInfo.scale;
        this.temperature = batteryInfo.temperature;
        this.battery = batteryInfo.battery;
        this.voltage = batteryInfo.voltage;
        this.technology = batteryInfo.technology;
        this.status = batteryInfo.status;
        this.health = batteryInfo.health;
        this.plugged = batteryInfo.plugged;
    }

    public BatteryInfo(Intent intent) {
        this.level = intent.getIntExtra(FirebaseAnalytics.Param.LEVEL, 0);
        this.scale = intent.getIntExtra("scale", 100);
        this.temperature = intent.getIntExtra("temperature", 0) / 10.0f;
        float f = this.level * 100.0f;
        int i = this.scale;
        this.battery = f / (i != 0 ? i : 100);
        this.voltage = intent.getIntExtra("voltage", 0) / 1000.0f;
        this.technology = intent.getStringExtra("technology");
        this.status = intent.getIntExtra(NotificationCompat.CATEGORY_STATUS, 1);
        this.health = intent.getIntExtra("health", 1);
        this.plugged = intent.getIntExtra("plugged", 0);
    }

    protected BatteryInfo(Parcel parcel) {
        this.level = parcel.readInt();
        this.scale = parcel.readInt();
        this.temperature = parcel.readFloat();
        this.battery = parcel.readFloat();
        this.voltage = parcel.readFloat();
        this.technology = parcel.readString();
        this.status = parcel.readInt();
        this.health = parcel.readInt();
        this.plugged = parcel.readInt();
    }

    public void set(BatteryInfo batteryInfo) {
        if (batteryInfo != null) {
            this.level = batteryInfo.level;
            this.scale = batteryInfo.scale;
            this.temperature = batteryInfo.temperature;
            this.battery = batteryInfo.battery;
            this.voltage = batteryInfo.voltage;
            this.technology = batteryInfo.technology;
            this.status = batteryInfo.status;
            this.health = batteryInfo.health;
            this.plugged = batteryInfo.plugged;
            return;
        }
        clear();
    }

    public void set(Intent intent) {
        this.level = intent.getIntExtra(FirebaseAnalytics.Param.LEVEL, this.level);
        this.scale = intent.getIntExtra("scale", this.scale);
        this.temperature = intent.getIntExtra("temperature", Math.round(this.temperature * 10.0f)) / 10.0f;
        float f = this.level * 100.0f;
        int i = this.scale;
        if (i == 0) {
            i = 100;
        }
        this.battery = f / i;
        this.voltage = intent.getIntExtra("voltage", Math.round(this.voltage * 1000.0f)) / 1000.0f;
        this.technology = intent.getStringExtra("technology");
        this.status = intent.getIntExtra(NotificationCompat.CATEGORY_STATUS, this.status);
        this.health = intent.getIntExtra("health", this.health);
        this.plugged = intent.getIntExtra("plugged", this.plugged);
    }

    public void clear() {
        this.level = 0;
        this.scale = 100;
        this.temperature = 0.0f;
        this.battery = 0.0f;
        this.voltage = 0.0f;
        this.technology = null;
        this.status = 1;
        this.health = 1;
        this.plugged = 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.level);
        parcel.writeInt(this.scale);
        parcel.writeFloat(this.temperature);
        parcel.writeFloat(this.battery);
        parcel.writeFloat(this.voltage);
        parcel.writeString(this.technology);
        parcel.writeInt(this.status);
        parcel.writeInt(this.health);
        parcel.writeInt(this.plugged);
    }

    public String toString() {
        return "BatteryInfo{level=" + this.level + ", scale=" + this.scale + ", temperature=" + this.temperature + ", battery=" + this.battery + ", voltage=" + this.voltage + ", technology=" + this.technology + ", status=" + this.status + ", health=" + this.health + ", plugged=" + this.plugged + '}';
    }

    public boolean isCharging() {
        return this.status == 2;
    }
}
