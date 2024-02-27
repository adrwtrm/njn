package com.serenegiant.gl;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.util.Log;
import androidx.core.content.res.ResourcesCompat;
import com.serenegiant.system.Stacktrace;
import com.serenegiant.utils.AssetsHelper;
import java.io.IOException;
import java.nio.FloatBuffer;

@Deprecated
/* loaded from: classes2.dex */
public final class GLHelper implements GLConst {
    private static final boolean DEBUG = false;
    private static final String TAG = "GLHelper";

    private GLHelper() {
    }

    public static void checkGlError(String str) {
        int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            Log.e(TAG, str + ": glError 0x" + Integer.toHexString(glGetError));
            Stacktrace.print();
        }
    }

    public static int initTex(int i, int i2, int i3) {
        return initTex(i, i2, i3, i3, 33071);
    }

    public static int initTex(int i, int i2, int i3, int i4, int i5) {
        int[] iArr = new int[1];
        GLES20.glActiveTexture(i2);
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(i, iArr[0]);
        GLES20.glTexParameteri(i, 10242, i5);
        GLES20.glTexParameteri(i, 10243, i5);
        GLES20.glTexParameteri(i, 10241, i3);
        GLES20.glTexParameteri(i, 10240, i4);
        Log.d(TAG, "initTex:texId=" + iArr[0]);
        return iArr[0];
    }

    public static int[] initTexes(int i, int i2, int i3) {
        return initTexes(new int[i], i2, i3, i3, 33071);
    }

    public static int[] initTexes(int[] iArr, int i, int i2) {
        return initTexes(iArr, i, i2, i2, 33071);
    }

    public static int[] initTexes(int i, int i2, int i3, int i4, int i5) {
        return initTexes(new int[i], i2, i3, i4, i5);
    }

    public static int[] initTexes(int[] iArr, int i, int i2, int i3, int i4) {
        int[] iArr2 = new int[1];
        GLES20.glGetIntegerv(34930, iArr2, 0);
        Log.v(TAG, "GL_MAX_TEXTURE_IMAGE_UNITS=" + iArr2[0]);
        int min = Math.min(iArr.length, iArr2[0]);
        for (int i5 = 0; i5 < min; i5++) {
            iArr[i5] = initTex(i, ShaderConst.TEX_NUMBERS[i5], i2, i3, i4);
        }
        return iArr;
    }

    public static int[] initTexes(int i, int i2, int i3, int i4, int i5, int i6) {
        return initTexes(new int[i], i2, i3, i4, i5, i6);
    }

    public static int[] initTexes(int[] iArr, int i, int i2, int i3) {
        return initTexes(iArr, i, i2, i3, i3, 33071);
    }

    public static int[] initTexes(int[] iArr, int i, int i2, int i3, int i4, int i5) {
        int[] iArr2 = new int[1];
        GLES20.glGetIntegerv(34930, iArr2, 0);
        int min = Math.min(iArr.length, iArr2[0]);
        for (int i6 = 0; i6 < min; i6++) {
            iArr[i6] = initTex(i, i2, i3, i4, i5);
        }
        return iArr;
    }

    public static void deleteTex(int i) {
        GLES20.glDeleteTextures(1, new int[]{i}, 0);
    }

    public static void deleteTex(int[] iArr) {
        GLES20.glDeleteTextures(iArr.length, iArr, 0);
    }

    public static int loadTextureFromResource(Context context, int i) {
        return loadTextureFromResource(context, i, null);
    }

    public static int loadTextureFromResource(Context context, int i, Resources.Theme theme) {
        Bitmap createBitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawARGB(0, 0, 255, 0);
        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), i, theme);
        drawable.setBounds(0, 0, 256, 256);
        drawable.draw(canvas);
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(GLConst.GL_TEXTURE_2D, iArr[0]);
        GLES20.glTexParameterf(GLConst.GL_TEXTURE_2D, 10241, 9728.0f);
        GLES20.glTexParameterf(GLConst.GL_TEXTURE_2D, 10240, 9729.0f);
        GLES20.glTexParameterf(GLConst.GL_TEXTURE_2D, 10242, 10497.0f);
        GLES20.glTexParameterf(GLConst.GL_TEXTURE_2D, 10243, 10497.0f);
        android.opengl.GLUtils.texImage2D(GLConst.GL_TEXTURE_2D, 0, createBitmap, 0);
        createBitmap.recycle();
        return iArr[0];
    }

    public static int createTextureWithTextContent(String str) {
        return createTextureWithTextContent(str, 33984);
    }

    public static int createTextureWithTextContent(String str, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawARGB(0, 0, 255, 0);
        Paint paint = new Paint();
        paint.setTextSize(32.0f);
        paint.setAntiAlias(true);
        paint.setARGB(255, 255, 255, 255);
        canvas.drawText(str, 16.0f, 112.0f, paint);
        int initTex = initTex(GLConst.GL_TEXTURE_2D, i, 9728, 9729, 10497);
        android.opengl.GLUtils.texImage2D(GLConst.GL_TEXTURE_2D, 0, createBitmap, 0);
        createBitmap.recycle();
        return initTex;
    }

    public static int loadShader(Context context, String str, String str2) {
        try {
            return loadShader(AssetsHelper.loadString(context.getAssets(), str), AssetsHelper.loadString(context.getAssets(), str));
        } catch (IOException unused) {
            return 0;
        }
    }

    public static int loadShader(String str, String str2) {
        int loadShader = loadShader(35633, str);
        if (loadShader == 0) {
            Log.d(TAG, "loadShader:failed to compile vertex shader,\n" + str);
            return 0;
        }
        int loadShader2 = loadShader(35632, str2);
        if (loadShader2 == 0) {
            Log.d(TAG, "loadShader:failed to compile fragment shader,\n" + str2);
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        checkGlError("glCreateProgram");
        if (glCreateProgram == 0) {
            Log.e(TAG, "Could not create program");
        }
        GLES20.glAttachShader(glCreateProgram, loadShader);
        checkGlError("glAttachShader");
        GLES20.glAttachShader(glCreateProgram, loadShader2);
        checkGlError("glAttachShader");
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] != 1) {
            String str3 = TAG;
            Log.e(str3, "Could not link program: ");
            Log.e(str3, GLES20.glGetProgramInfoLog(glCreateProgram));
            GLES20.glDeleteProgram(glCreateProgram);
            return 0;
        }
        return glCreateProgram;
    }

    public static int loadShader(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        checkGlError("glCreateShader type=" + i);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] == 0) {
            String str2 = TAG;
            Log.e(str2, "Could not compile shader " + i + ":");
            Log.e(str2, " " + GLES20.glGetShaderInfoLog(glCreateShader));
            GLES20.glDeleteShader(glCreateShader);
            return 0;
        }
        return glCreateShader;
    }

    public static void checkLocation(int i, String str) {
        if (i < 0) {
            throw new RuntimeException("Unable to locate '" + str + "' in program");
        }
    }

    public static int createBuffer(int i, FloatBuffer floatBuffer, int i2) {
        int[] iArr = new int[1];
        GLES20.glGenBuffers(1, iArr, 0);
        checkGlError("glGenBuffers");
        GLES20.glBindBuffer(i, iArr[0]);
        checkGlError("glBindBuffer");
        GLES20.glBufferData(i, floatBuffer.limit() * 4, floatBuffer, i2);
        checkGlError("glBufferData");
        GLES20.glBindBuffer(i, 0);
        return iArr[0];
    }

    public static void deleteBuffer(int i) {
        deleteBuffer(new int[]{i});
    }

    public static void deleteBuffer(int[] iArr) {
        GLES20.glDeleteBuffers(iArr.length, iArr, 0);
    }
}
