package com.serenegiant.camera;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

/* loaded from: classes2.dex */
public interface CameraConst {
    public static final int DEFAULT_HEIGHT = 480;
    public static final int DEFAULT_WIDTH = 640;
    public static final int FACING_BACK = 0;
    public static final int FACING_FRONT = 1;
    public static final int FACING_UNSPECIFIED = -1;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface FaceType {
    }

    /* loaded from: classes2.dex */
    public static final class CameraInfo {
        public int face;
        public int height;
        public String id;
        public int orientation;
        public int width;

        public CameraInfo() {
        }

        public CameraInfo(String str, int i, int i2, int i3, int i4) {
            this.id = str;
            this.face = i;
            this.orientation = i2;
            this.width = i3;
            this.height = i4;
        }

        public CameraInfo(String str, int i, int i2) {
            this.id = str;
            this.face = i;
            this.orientation = i2;
        }

        public void set(String str, int i, int i2, int i3, int i4) {
            this.id = str;
            this.face = i;
            this.orientation = i2;
            this.width = i3;
            this.height = i4;
        }

        public void set(CameraInfo cameraInfo) {
            this.id = cameraInfo.id;
            this.face = cameraInfo.face;
            this.orientation = cameraInfo.orientation;
            this.width = cameraInfo.width;
            this.height = cameraInfo.height;
        }

        public void set(int i) {
            this.face = i;
            this.id = String.format(Locale.US, "FACE_%d", Integer.valueOf(i));
        }

        public String toString() {
            return String.format(Locale.US, "Size(%dx%d),face=%d, id=%s, orientation=%d", Integer.valueOf(this.width), Integer.valueOf(this.height), Integer.valueOf(this.face), this.id, Integer.valueOf(this.orientation));
        }
    }
}
