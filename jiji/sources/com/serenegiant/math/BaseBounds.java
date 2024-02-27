package com.serenegiant.math;

import java.io.Serializable;

/* loaded from: classes2.dex */
public abstract class BaseBounds implements Serializable {
    private static final long serialVersionUID = 5504958491886331189L;
    public final Vector angle;
    public final Vector position;
    public float radius;

    public abstract boolean ptInBounds(float f, float f2, float f3);

    public BaseBounds() {
        this.position = new Vector();
        this.angle = new Vector();
    }

    public BaseBounds(BaseBounds baseBounds) {
        this.position = new Vector();
        this.angle = new Vector();
        set(baseBounds);
    }

    public BaseBounds(float f, float f2, float f3) {
        Vector vector = new Vector();
        this.position = vector;
        this.angle = new Vector();
        vector.set(f, f2);
        this.radius = f3;
    }

    public BaseBounds(float f, float f2, float f3, float f4) {
        Vector vector = new Vector();
        this.position = vector;
        this.angle = new Vector();
        vector.set(f, f2, f3);
        this.radius = f4;
    }

    public BaseBounds set(BaseBounds baseBounds) {
        this.position.set(baseBounds.position);
        this.angle.set(baseBounds.angle);
        this.radius = baseBounds.radius;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean ptInBoundsSphere(float f, float f2, float f3, float f4) {
        return this.position.distSquared(f, f2, f3) < f4 * f4;
    }

    public boolean ptInBounds(float f, float f2) {
        return ptInBounds(f, f2, this.position.z);
    }

    public boolean ptInBounds(Vector vector) {
        return ptInBounds(vector.x, vector.y, vector.z);
    }

    public BaseBounds move(float f, float f2) {
        this.position.add(f, f2);
        return this;
    }

    public BaseBounds move(float f, float f2, float f3) {
        this.position.add(f, f2, f3);
        return this;
    }

    public BaseBounds move(Vector vector) {
        this.position.add(vector);
        return this;
    }

    public BaseBounds setPosition(Vector vector) {
        this.position.set(vector);
        return this;
    }

    public BaseBounds setPosition(float f, float f2) {
        this.position.set(f, f2);
        return this;
    }

    public BaseBounds setPosition(float f, float f2, float f3) {
        this.position.set(f, f2, f3);
        return this;
    }

    public void centerX(float f) {
        this.position.x = f;
    }

    public float centerX() {
        return this.position.x;
    }

    public void centerY(float f) {
        this.position.y = f;
    }

    public float centerY() {
        return this.position.y;
    }

    public void centerZ(float f) {
        this.position.z = f;
    }

    public float centerZ() {
        return this.position.z;
    }

    public void rotate(Vector vector) {
        this.angle.set(vector.x, vector.y, vector.z);
    }

    public void rotate(float f, float f2, float f3) {
        this.angle.set(f, f2, f3);
    }

    public void rotateX(float f) {
        this.angle.x = f;
    }

    public void rotateY(float f) {
        this.angle.y = f;
    }

    public void rotateZ(float f) {
        this.angle.z = f;
    }
}
