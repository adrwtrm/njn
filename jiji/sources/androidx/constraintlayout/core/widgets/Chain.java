package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Chain {
    private static final boolean DEBUG = false;
    public static final boolean USE_CHAIN_OPTIMIZATION = false;

    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, ArrayList<ConstraintWidget> arrayList, int i) {
        int i2;
        ChainHead[] chainHeadArr;
        int i3;
        if (i == 0) {
            i2 = constraintWidgetContainer.mHorizontalChainsSize;
            chainHeadArr = constraintWidgetContainer.mHorizontalChainsArray;
            i3 = 0;
        } else {
            i2 = constraintWidgetContainer.mVerticalChainsSize;
            chainHeadArr = constraintWidgetContainer.mVerticalChainsArray;
            i3 = 2;
        }
        for (int i4 = 0; i4 < i2; i4++) {
            ChainHead chainHead = chainHeadArr[i4];
            chainHead.define();
            if (arrayList == null || (arrayList != null && arrayList.contains(chainHead.mFirst))) {
                applyChainConstraints(constraintWidgetContainer, linearSystem, i, i3, chainHead);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0037, code lost:
        if (r2.mHorizontalChainStyle == 2) goto L325;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x004a, code lost:
        if (r2.mVerticalChainStyle == 2) goto L325;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x004c, code lost:
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x004e, code lost:
        r5 = false;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0386  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x03dd  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x03fd  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x04d9  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x050e  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x052d  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x0538  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x053d  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x0543  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x0548  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x054c  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x055d  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x0560  */
    /* JADX WARN: Removed duplicated region for block: B:330:0x03df A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static void applyChainConstraints(androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r38, androidx.constraintlayout.core.LinearSystem r39, int r40, int r41, androidx.constraintlayout.core.widgets.ChainHead r42) {
        /*
            Method dump skipped, instructions count: 1418
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.Chain.applyChainConstraints(androidx.constraintlayout.core.widgets.ConstraintWidgetContainer, androidx.constraintlayout.core.LinearSystem, int, int, androidx.constraintlayout.core.widgets.ChainHead):void");
    }
}