package com.serenegiant.mediaeffect;

import android.media.effect.EffectContext;
import android.text.TextUtils;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes2.dex */
public class MediaEffectBackDropper extends MediaEffect {
    public MediaEffectBackDropper(EffectContext effectContext, String str) {
        super(effectContext, "android.media.effect.effects.BackDropperEffect");
        setParameter(str);
    }

    public MediaEffectBackDropper setParameter(String str) {
        if (!TextUtils.isEmpty(str)) {
            setParameter(FirebaseAnalytics.Param.SOURCE, str);
        }
        return this;
    }
}
