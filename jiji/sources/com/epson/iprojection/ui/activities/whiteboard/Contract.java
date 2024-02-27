package com.epson.iprojection.ui.activities.whiteboard;

import kotlin.Metadata;

/* compiled from: Contract.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0002¨\u0006\u0003"}, d2 = {"Lcom/epson/iprojection/ui/activities/whiteboard/Contract;", "", "IDownloadComplitedListener", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public interface Contract {

    /* compiled from: Contract.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&¨\u0006\u0007"}, d2 = {"Lcom/epson/iprojection/ui/activities/whiteboard/Contract$IDownloadComplitedListener;", "", "onDownloadComplited", "", "fileName", "", "mimeType", "app_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public interface IDownloadComplitedListener {
        void onDownloadComplited(String str, String str2);
    }
}