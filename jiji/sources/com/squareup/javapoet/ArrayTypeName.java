package com.squareup.javapoet;

import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.ArrayType;

/* loaded from: classes2.dex */
public final class ArrayTypeName extends TypeName {
    public final TypeName componentType;

    @Override // com.squareup.javapoet.TypeName
    public /* bridge */ /* synthetic */ TypeName annotated(List list) {
        return annotated((List<AnnotationSpec>) list);
    }

    private ArrayTypeName(TypeName typeName) {
        this(typeName, new ArrayList());
    }

    private ArrayTypeName(TypeName typeName, List<AnnotationSpec> list) {
        super(list);
        this.componentType = (TypeName) Util.checkNotNull(typeName, "rawType == null", new Object[0]);
    }

    @Override // com.squareup.javapoet.TypeName
    public ArrayTypeName annotated(List<AnnotationSpec> list) {
        return new ArrayTypeName(this.componentType, concatAnnotations(list));
    }

    @Override // com.squareup.javapoet.TypeName
    public TypeName withoutAnnotations() {
        return new ArrayTypeName(this.componentType);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.squareup.javapoet.TypeName
    public CodeWriter emit(CodeWriter codeWriter) throws IOException {
        return emit(codeWriter, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CodeWriter emit(CodeWriter codeWriter, boolean z) throws IOException {
        emitLeafType(codeWriter);
        return emitBrackets(codeWriter, z);
    }

    private CodeWriter emitLeafType(CodeWriter codeWriter) throws IOException {
        if (TypeName.asArray(this.componentType) != null) {
            return TypeName.asArray(this.componentType).emitLeafType(codeWriter);
        }
        return this.componentType.emit(codeWriter);
    }

    private CodeWriter emitBrackets(CodeWriter codeWriter, boolean z) throws IOException {
        if (isAnnotated()) {
            codeWriter.emit(" ");
            emitAnnotations(codeWriter);
        }
        if (TypeName.asArray(this.componentType) == null) {
            return codeWriter.emit(z ? "..." : "[]");
        }
        codeWriter.emit("[]");
        return TypeName.asArray(this.componentType).emitBrackets(codeWriter, z);
    }

    public static ArrayTypeName of(TypeName typeName) {
        return new ArrayTypeName(typeName);
    }

    public static ArrayTypeName of(Type type) {
        return of(TypeName.get(type));
    }

    public static ArrayTypeName get(ArrayType arrayType) {
        return get(arrayType, new LinkedHashMap());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ArrayTypeName get(ArrayType arrayType, Map<TypeParameterElement, TypeVariableName> map) {
        return new ArrayTypeName(get(arrayType.getComponentType(), map));
    }

    public static ArrayTypeName get(GenericArrayType genericArrayType) {
        return get(genericArrayType, (Map<Type, TypeVariableName>) new LinkedHashMap());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ArrayTypeName get(GenericArrayType genericArrayType, Map<Type, TypeVariableName> map) {
        return of(get(genericArrayType.getGenericComponentType(), map));
    }
}
