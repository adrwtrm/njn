package com.serenegiant.glutils;

import com.serenegiant.egl.EGLBase;
import com.serenegiant.gl.EffectDrawer2D;
import com.serenegiant.gl.GLDrawer2D;
import com.serenegiant.glutils.AbstractRendererHolder;
import com.serenegiant.glutils.IRendererHolder;

/* loaded from: classes2.dex */
public class EffectRendererHolder extends AbstractRendererHolder implements IEffectRendererHolder {
    private static final boolean DEBUG = false;
    private static final int REQUEST_CHANGE_EFFECT = 100;
    private static final int REQUEST_SET_PARAMS = 101;
    private static final String TAG = "EffectRendererHolder";

    public EffectRendererHolder(int i, int i2, IRendererHolder.RenderHolderCallback renderHolderCallback) {
        this(i, i2, 3, null, 2, renderHolderCallback);
    }

    public EffectRendererHolder(int i, int i2, int i3, EGLBase.IContext<?> iContext, int i4, IRendererHolder.RenderHolderCallback renderHolderCallback) {
        super(i, i2, i3, iContext, i4, renderHolderCallback);
    }

    @Override // com.serenegiant.glutils.AbstractRendererHolder
    protected AbstractRendererHolder.BaseRendererTask createRendererTask(int i, int i2, int i3, EGLBase.IContext<?> iContext, int i4) {
        return new MyRendererTask(this, i, i2, i3, iContext, i4);
    }

    @Override // com.serenegiant.glutils.IEffectRendererHolder
    public void changeEffect(int i) {
        ((MyRendererTask) this.mRendererTask).changeEffect(i);
    }

    @Override // com.serenegiant.glutils.IEffectRendererHolder
    public int getCurrentEffect() {
        GLDrawer2D drawer = this.mRendererTask.getDrawer();
        if (drawer instanceof EffectDrawer2D) {
            return ((EffectDrawer2D) drawer).getCurrentEffect();
        }
        return 0;
    }

    @Override // com.serenegiant.glutils.IEffectRendererHolder
    public void setParams(float[] fArr) {
        ((MyRendererTask) this.mRendererTask).setParams(-1, fArr);
    }

    @Override // com.serenegiant.glutils.IEffectRendererHolder
    public void setParams(int i, float[] fArr) throws IllegalArgumentException {
        if (i > 0) {
            ((MyRendererTask) this.mRendererTask).setParams(i, fArr);
            return;
        }
        throw new IllegalArgumentException("invalid effect number:" + i);
    }

    protected void handleDefaultEffect(int i, GLDrawer2D gLDrawer2D) {
        gLDrawer2D.resetShader();
    }

    /* loaded from: classes2.dex */
    protected static final class MyRendererTask extends AbstractRendererHolder.BaseRendererTask {
        public MyRendererTask(AbstractRendererHolder abstractRendererHolder, int i, int i2, int i3, EGLBase.IContext<?> iContext, int i4) {
            super(abstractRendererHolder, i, i2, i3, iContext, i4, new GLDrawer2D.DrawerFactory() { // from class: com.serenegiant.glutils.EffectRendererHolder.MyRendererTask.1
                @Override // com.serenegiant.gl.GLDrawer2D.DrawerFactory
                public GLDrawer2D create(boolean z, boolean z2) {
                    return new EffectDrawer2D(z, z2);
                }
            });
        }

        public void changeEffect(int i) {
            checkFinished();
            offer(100, i);
        }

        public void setParams(int i, float[] fArr) {
            checkFinished();
            offer(101, i, 0, fArr);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.serenegiant.glutils.AbstractDistributeTask
        public Object handleRequest(int i, int i2, int i3, Object obj) {
            if (i == 100) {
                handleChangeEffect(i2);
            } else if (i == 101) {
                handleSetParam(i2, (float[]) obj);
            } else {
                return super.handleRequest(i, i2, i3, obj);
            }
            return null;
        }

        private void handleChangeEffect(int i) {
            GLDrawer2D drawer = getDrawer();
            if (drawer instanceof EffectDrawer2D) {
                ((EffectDrawer2D) drawer).setEffect(i);
            }
        }

        private void handleSetParam(int i, float[] fArr) {
            GLDrawer2D drawer = getDrawer();
            if (drawer instanceof EffectDrawer2D) {
                ((EffectDrawer2D) drawer).setParams(i, fArr);
            }
        }
    }
}
