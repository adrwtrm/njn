package org.objectweb.asm;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class Context {
    Attribute[] attributePrototypes;
    char[] charBuffer;
    int currentFrameLocalCount;
    int currentFrameLocalCountDelta;
    Object[] currentFrameLocalTypes;
    int currentFrameOffset;
    int currentFrameStackCount;
    Object[] currentFrameStackTypes;
    int currentFrameType;
    Label[] currentLocalVariableAnnotationRangeEnds;
    int[] currentLocalVariableAnnotationRangeIndices;
    Label[] currentLocalVariableAnnotationRangeStarts;
    int currentMethodAccessFlags;
    String currentMethodDescriptor;
    Label[] currentMethodLabels;
    String currentMethodName;
    int currentTypeAnnotationTarget;
    TypePath currentTypeAnnotationTargetPath;
    int parsingOptions;
}
