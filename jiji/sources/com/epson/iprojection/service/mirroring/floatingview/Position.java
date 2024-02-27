package com.epson.iprojection.service.mirroring.floatingview;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Position.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u000e\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0000J\t\u0010\u0017\u001a\u00020\nHÖ\u0001J\u0011\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0000H\u0086\u0002J\u0011\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0000H\u0086\u0002J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f¨\u0006\u001c"}, d2 = {"Lcom/epson/iprojection/service/mirroring/floatingview/Position;", "", "fx", "", "fy", "(FF)V", "getFx", "()F", "getFy", "x", "", "getX", "()I", "y", "getY", "component1", "component2", "copy", "equals", "", "other", "getDistance", "p", "hashCode", "minus", "plus", "toString", "", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class Position {
    private final float fx;
    private final float fy;
    private final int x;
    private final int y;

    public static /* synthetic */ Position copy$default(Position position, float f, float f2, int i, Object obj) {
        if ((i & 1) != 0) {
            f = position.fx;
        }
        if ((i & 2) != 0) {
            f2 = position.fy;
        }
        return position.copy(f, f2);
    }

    public final float component1() {
        return this.fx;
    }

    public final float component2() {
        return this.fy;
    }

    public final Position copy(float f, float f2) {
        return new Position(f, f2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Position) {
            Position position = (Position) obj;
            return Float.compare(this.fx, position.fx) == 0 && Float.compare(this.fy, position.fy) == 0;
        }
        return false;
    }

    public int hashCode() {
        return (Float.hashCode(this.fx) * 31) + Float.hashCode(this.fy);
    }

    public String toString() {
        return "Position(fx=" + this.fx + ", fy=" + this.fy + ')';
    }

    public Position(float f, float f2) {
        this.fx = f;
        this.fy = f2;
        this.x = (int) f;
        this.y = (int) f2;
    }

    public final float getFx() {
        return this.fx;
    }

    public final float getFy() {
        return this.fy;
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    public final float getDistance(Position p) {
        Intrinsics.checkNotNullParameter(p, "p");
        double d = 2;
        return (float) Math.sqrt(((float) Math.pow(Math.abs(this.fx - p.fx), d)) + ((float) Math.pow(Math.abs(this.fy - p.fy), d)));
    }

    public final Position plus(Position p) {
        Intrinsics.checkNotNullParameter(p, "p");
        return new Position(this.fx + p.fx, this.fy + p.fy);
    }

    public final Position minus(Position p) {
        Intrinsics.checkNotNullParameter(p, "p");
        return new Position(this.fx - p.fx, this.fy - p.fy);
    }
}
