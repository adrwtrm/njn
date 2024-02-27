package com.serenegiant.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.serenegiant.common.R;
import com.serenegiant.system.BuildCheck;
import com.serenegiant.system.ContextUtils;
import com.serenegiant.widget.ProgressView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class ViewUtils {
    private static final boolean DEBUG = false;
    public static final float EPS = 0.1f;
    private static final String TAG = "ViewUtils";
    public static final float TO_DEGREE = 57.29578f;
    private static final Vector sPtInPoly_v1 = new Vector();
    private static final Vector sPtInPoly_v2 = new Vector();
    private static final int[] ICON_IDS = {R.id.thumbnail, 16908294, R.id.icon, R.id.image};
    private static final int[] TITLE_IDS = {R.id.title, R.id.content, 16908310, 16908308, 16908309};

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Orientation {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Rotation {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Visibility {
    }

    public static final float crossProduct(float f, float f2, float f3, float f4) {
        return (f * f4) - (f3 * f2);
    }

    public static final float dotProduct(float f, float f2, float f3, float f4) {
        return (f * f3) + (f2 * f4);
    }

    private static int rotation2Degrees(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return 0;
                }
                return ProgressView.DIRECTION_TOP_TO_BOTTOM;
            }
            return 180;
        }
        return 90;
    }

    private ViewUtils() {
    }

    public static void requestResize(View view, int i, int i2) {
        view.getLayoutParams().width = i;
        view.getLayoutParams().height = i2;
        view.requestLayout();
    }

    public static void setBackgroundAll(ViewGroup viewGroup, int i) {
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            childAt.setBackgroundColor(i);
            if (childAt instanceof ViewGroup) {
                setBackgroundAll((ViewGroup) childAt, i);
            }
        }
    }

    public static void setBackgroundAll(ViewGroup viewGroup, Drawable drawable) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.setBackground(drawable);
            if (childAt instanceof ViewGroup) {
                setBackgroundAll((ViewGroup) childAt, drawable);
            }
        }
    }

    public static LayoutInflater createCustomLayoutInflater(Context context, int i) {
        return createCustomLayoutInflater(context, LayoutInflater.from(context), i);
    }

    public static LayoutInflater createCustomLayoutInflater(LayoutInflater layoutInflater, int i) {
        return createCustomLayoutInflater(layoutInflater.getContext(), layoutInflater, i);
    }

    public static LayoutInflater createCustomLayoutInflater(Context context, LayoutInflater layoutInflater, int i) {
        return layoutInflater.cloneInContext(new ContextThemeWrapper(context, i));
    }

    public static int getRotation(View view) {
        if (BuildCheck.isAPI17()) {
            return view.getDisplay().getRotation();
        }
        return ((WindowManager) ContextUtils.requireSystemService(view.getContext(), WindowManager.class)).getDefaultDisplay().getRotation();
    }

    public static int getRotation(Context context) {
        return ((WindowManager) ContextUtils.requireSystemService(context, WindowManager.class)).getDefaultDisplay().getRotation();
    }

    public static int getRotationDegrees(View view) {
        return rotation2Degrees(getRotation(view));
    }

    public static int getRotationDegrees(Context context) {
        return rotation2Degrees(getRotation(context));
    }

    public static int getOrientation(Context context) {
        return context.getResources().getConfiguration().orientation;
    }

    public static final float crossProduct(Vector vector, Vector vector2) {
        return (vector.x * vector2.y) - (vector2.x * vector.y);
    }

    public static synchronized boolean ptInPoly(float f, float f2, float[] fArr) {
        synchronized (ViewUtils.class) {
            int length = fArr.length & Integer.MAX_VALUE;
            boolean z = false;
            if (length < 6) {
                return false;
            }
            int i = 0;
            while (true) {
                if (i >= length) {
                    z = true;
                    break;
                }
                Vector vector = sPtInPoly_v1;
                int i2 = i + 1;
                vector.set(f, f2).dec(fArr[i], fArr[i2]);
                int i3 = i + 2;
                if (i3 < length) {
                    sPtInPoly_v2.set(fArr[i3], fArr[i + 3]);
                } else {
                    sPtInPoly_v2.set(fArr[0], fArr[1]);
                }
                Vector vector2 = sPtInPoly_v2;
                vector2.dec(fArr[i], fArr[i2]);
                if (crossProduct(vector, vector2) > 0.0f) {
                    break;
                }
                i = i3;
            }
            return z;
        }
    }

    /* loaded from: classes2.dex */
    public static final class Vector {
        public float x;
        public float y;

        public Vector() {
        }

        public Vector(float f, float f2) {
            set(f, f2);
        }

        public Vector set(float f, float f2) {
            this.x = f;
            this.y = f2;
            return this;
        }

        public Vector sub(Vector vector) {
            return new Vector(this.x - vector.x, this.y - vector.y);
        }

        public Vector dec(float f, float f2) {
            this.x -= f;
            this.y -= f2;
            return this;
        }
    }

    /* loaded from: classes2.dex */
    public static final class LineSegment {
        public final Vector p1;
        public final Vector p2;

        public LineSegment(float f, float f2, float f3, float f4) {
            this.p1 = new Vector(f, f2);
            this.p2 = new Vector(f3, f4);
        }

        public LineSegment set(float f, float f2, float f3, float f4) {
            this.p1.set(f, f2);
            this.p2.set(f3, f4);
            return this;
        }
    }

    public static final boolean checkIntersect(LineSegment lineSegment, LineSegment[] lineSegmentArr) {
        int length = lineSegmentArr != null ? lineSegmentArr.length : 0;
        Vector sub = lineSegment.p2.sub(lineSegment.p1);
        boolean z = false;
        for (int i = 0; i < length; i++) {
            z = crossProduct(sub, lineSegmentArr[i].p1.sub(lineSegment.p1)) * crossProduct(sub, lineSegmentArr[i].p2.sub(lineSegment.p1)) < 0.1f;
            if (z) {
                Vector sub2 = lineSegmentArr[i].p2.sub(lineSegmentArr[i].p1);
                z = crossProduct(sub2, lineSegment.p1.sub(lineSegmentArr[i].p1)) * crossProduct(sub2, lineSegment.p2.sub(lineSegmentArr[i].p1)) < 0.1f;
                if (z) {
                    break;
                }
            }
        }
        return z;
    }

    public static ImageView findIconView(View view) {
        return (ImageView) findView(view, ICON_IDS, ImageView.class);
    }

    public static ImageView findIconView(View view, int[] iArr) {
        return (ImageView) findView(view, iArr, ImageView.class);
    }

    public static TextView findTitleView(View view) {
        return (TextView) findView(view, TITLE_IDS, TextView.class);
    }

    public static TextView findTitleView(View view, int[] iArr) {
        return (TextView) findView(view, iArr, TextView.class);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T extends View> T findView(View view, int[] iArr, Class<T> cls) {
        if (cls.isInstance(view)) {
            return view;
        }
        for (int i : iArr) {
            T t = (T) view.findViewById(i);
            if (cls.isInstance(t)) {
                return t;
            }
        }
        return null;
    }

    public static <T extends View> T findViewInParent(View view, int[] iArr, Class<T> cls) {
        for (int i : iArr) {
            if (i != -1) {
                T t = (T) view.findViewById(i);
                if (cls.isInstance(t)) {
                    return t;
                }
                for (ViewParent parent = view.getParent(); parent != null; parent = parent.getParent()) {
                    if (parent instanceof View) {
                        T t2 = (T) ((View) parent).findViewById(i);
                        if (cls.isInstance(t2)) {
                            return t2;
                        }
                    }
                }
                continue;
            }
        }
        return null;
    }
}
