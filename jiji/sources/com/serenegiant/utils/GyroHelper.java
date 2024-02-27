package com.serenegiant.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import com.serenegiant.system.ContextUtils;
import java.lang.ref.WeakReference;
import java.util.List;

/* loaded from: classes2.dex */
public class GyroHelper {
    private static final boolean DEBUG = false;
    private static final int[] SENSOR_TYPES = {2, 9, 1, 4};
    private static final String TAG = "GyroHelper";
    private final float[] mAccelValues;
    private final float[] mAzimuthValues;
    private final float[] mGravityValues;
    private final float[] mGyroValues;
    private final float[] mMagnetValues;
    private boolean mRegistered;
    private int mRotation;
    private final SensorEventListener mSensorEventListener;
    private SensorManager mSensorManager;
    private final Object mSensorSync;
    private final Object mSync;
    private final WeakReference<Context> mWeakContext;

    public GyroHelper(Context context) {
        Object obj = new Object();
        this.mSync = obj;
        this.mSensorSync = new Object();
        this.mMagnetValues = new float[3];
        this.mGravityValues = new float[3];
        this.mAzimuthValues = new float[3];
        this.mAccelValues = new float[3];
        this.mGyroValues = new float[3];
        this.mSensorEventListener = new SensorEventListener() { // from class: com.serenegiant.utils.GyroHelper.1
            private static final float TO_DEGREE = 57.29578f;
            private final float[] outR = new float[16];
            private final float[] outR2 = new float[16];
            private final float[] mRotateMatrix = new float[16];
            private final float[] mInclinationMatrix = new float[16];

            @Override // android.hardware.SensorEventListener
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            private void highPassFilter(float[] fArr, float[] fArr2, float f) {
                float f2 = 1.0f - f;
                fArr[0] = (fArr[0] * f) + (fArr2[0] * f2);
                fArr[1] = (fArr[1] * f) + (fArr2[1] * f2);
                fArr[2] = (f * fArr[2]) + (f2 * fArr2[2]);
            }

            private void getOrientation(float[] fArr, float[] fArr2) {
                int i = GyroHelper.this.mRotation;
                if (i == 0) {
                    SensorManager.getOrientation(fArr, fArr2);
                    return;
                }
                if (i == 1) {
                    SensorManager.remapCoordinateSystem(fArr, 2, 129, this.outR);
                } else if (i == 2) {
                    SensorManager.remapCoordinateSystem(fArr, 2, 129, this.outR2);
                    SensorManager.remapCoordinateSystem(this.outR2, 2, 129, this.outR);
                } else if (i == 3) {
                    float[] fArr3 = this.outR;
                    SensorManager.remapCoordinateSystem(fArr3, 130, 129, fArr3);
                }
                SensorManager.getOrientation(this.outR, fArr2);
            }

            @Override // android.hardware.SensorEventListener
            public void onSensorChanged(SensorEvent sensorEvent) {
                float[] fArr = sensorEvent.values;
                int type = sensorEvent.sensor.getType();
                if (type == 1) {
                    synchronized (GyroHelper.this.mSensorSync) {
                        System.arraycopy(fArr, 0, GyroHelper.this.mAccelValues, 0, 3);
                        System.arraycopy(fArr, 0, GyroHelper.this.mGravityValues, 0, 3);
                    }
                } else if (type != 2) {
                    if (type == 4) {
                        synchronized (GyroHelper.this.mSensorSync) {
                            System.arraycopy(fArr, 0, GyroHelper.this.mGyroValues, 0, 3);
                        }
                    } else if (type != 9) {
                    } else {
                        synchronized (GyroHelper.this.mSensorSync) {
                            System.arraycopy(fArr, 0, GyroHelper.this.mGravityValues, 0, 3);
                        }
                    }
                } else {
                    synchronized (GyroHelper.this.mSensorSync) {
                        highPassFilter(GyroHelper.this.mMagnetValues, fArr, 0.8f);
                        System.arraycopy(fArr, 0, GyroHelper.this.mMagnetValues, 0, 3);
                        SensorManager.getRotationMatrix(this.mRotateMatrix, this.mInclinationMatrix, GyroHelper.this.mGravityValues, GyroHelper.this.mMagnetValues);
                        getOrientation(this.mRotateMatrix, GyroHelper.this.mAzimuthValues);
                        float[] fArr2 = GyroHelper.this.mAzimuthValues;
                        fArr2[0] = fArr2[0] * 57.29578f;
                        float[] fArr3 = GyroHelper.this.mAzimuthValues;
                        fArr3[1] = fArr3[1] * 57.29578f;
                        float[] fArr4 = GyroHelper.this.mAzimuthValues;
                        fArr4[2] = fArr4[2] * 57.29578f;
                    }
                }
            }
        };
        this.mWeakContext = new WeakReference<>(context);
        synchronized (obj) {
            this.mSensorManager = (SensorManager) ContextUtils.requireSystemService(context, SensorManager.class);
        }
    }

    public void release() {
        synchronized (this.mSync) {
            this.mSensorManager = null;
        }
    }

    public void start() {
        int[] iArr;
        synchronized (this.mSync) {
            Context context = this.mWeakContext.get();
            if (this.mSensorManager == null || context == null) {
                throw new IllegalStateException("already released");
            }
            for (int i = 0; i < 3; i++) {
                float[] fArr = this.mMagnetValues;
                float[] fArr2 = this.mGravityValues;
                this.mAzimuthValues[i] = 0.0f;
                fArr2[i] = 0.0f;
                fArr[i] = 0.0f;
                float[] fArr3 = this.mAccelValues;
                this.mGyroValues[i] = 0.0f;
                fArr3[i] = 0.0f;
            }
            this.mRegistered = true;
            boolean z = false;
            for (int i2 : SENSOR_TYPES) {
                List<Sensor> sensorList = this.mSensorManager.getSensorList(i2);
                if (sensorList == null || sensorList.size() <= 0) {
                    Log.i(TAG, String.format("no sensor for sensor type %d", Integer.valueOf(i2)));
                } else {
                    if (i2 == 9) {
                        Log.i(TAG, "hasGravity");
                        z = true;
                    }
                    if (!z || i2 != 1) {
                        this.mSensorManager.registerListener(this.mSensorEventListener, sensorList.get(0), 1);
                    }
                }
            }
        }
    }

    public void stop() {
        SensorManager sensorManager;
        synchronized (this.mSync) {
            if (this.mRegistered && (sensorManager = this.mSensorManager) != null) {
                try {
                    sensorManager.unregisterListener(this.mSensorEventListener);
                } catch (Exception unused) {
                }
            }
            this.mRegistered = false;
        }
    }

    public void setScreenRotation(int i) {
        this.mRotation = i;
    }

    public float getAzimuth() {
        float f;
        synchronized (this.mSensorSync) {
            f = this.mAzimuthValues[0];
        }
        return f;
    }

    public float getPan() {
        float f;
        synchronized (this.mSensorSync) {
            f = this.mAzimuthValues[1];
        }
        return f;
    }

    public float getTilt() {
        float f;
        synchronized (this.mSensorSync) {
            f = this.mAzimuthValues[2];
        }
        return f;
    }

    public float getAccelX() {
        float f;
        synchronized (this.mSensorSync) {
            f = this.mAccelValues[0];
        }
        return f;
    }

    public float getAccelY() {
        float f;
        synchronized (this.mSensorSync) {
            f = this.mAccelValues[1];
        }
        return f;
    }

    public float getAccelZ() {
        float f;
        synchronized (this.mSensorSync) {
            f = this.mAccelValues[2];
        }
        return f;
    }

    public float[] getAccel(float[] fArr) {
        if (fArr == null || fArr.length <= 3) {
            fArr = new float[3];
        }
        synchronized (this.mSensorSync) {
            System.arraycopy(this.mAccelValues, 0, fArr, 0, 3);
        }
        return fArr;
    }

    public float getGyroX() {
        float f;
        synchronized (this.mSensorSync) {
            f = this.mGyroValues[0];
        }
        return f;
    }

    public float getGyroY() {
        float f;
        synchronized (this.mSensorSync) {
            f = this.mGyroValues[1];
        }
        return f;
    }

    public float getGyroZ() {
        float f;
        synchronized (this.mSensorSync) {
            f = this.mGyroValues[2];
        }
        return f;
    }

    public float[] getGyro(float[] fArr) {
        if (fArr == null || fArr.length <= 3) {
            fArr = new float[3];
        }
        synchronized (this.mSensorSync) {
            System.arraycopy(this.mGyroValues, 0, fArr, 0, 3);
        }
        return fArr;
    }
}
