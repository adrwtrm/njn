package com.epson.iprojection.customer_satisfaction.entities;

import kotlin.Metadata;

/* compiled from: EContractNumber.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lcom/epson/iprojection/customer_satisfaction/entities/EContractNumber;", "", "number", "", "(Ljava/lang/String;II)V", "getNumber", "()I", "USED_COUNT", "TRY_CORRECT_COUNT", "ELAPSED_DAYS", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public enum EContractNumber {
    USED_COUNT(2),
    TRY_CORRECT_COUNT(3),
    ELAPSED_DAYS(60);
    
    private final int number;

    EContractNumber(int i) {
        this.number = i;
    }

    public final int getNumber() {
        return this.number;
    }
}
