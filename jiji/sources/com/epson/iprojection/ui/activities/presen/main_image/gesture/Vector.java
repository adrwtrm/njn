package com.epson.iprojection.ui.activities.presen.main_image.gesture;

/* loaded from: classes.dex */
public class Vector {
    public float x;
    public float y;

    public Vector() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public Vector(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public float getLength() {
        float f = this.x;
        float f2 = this.y;
        return (float) Math.sqrt((f * f) + (f2 * f2));
    }
}
