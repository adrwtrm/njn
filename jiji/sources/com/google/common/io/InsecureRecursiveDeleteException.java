package com.google.common.io;

import java.nio.file.FileSystemException;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes2.dex */
public final class InsecureRecursiveDeleteException extends FileSystemException {
    public InsecureRecursiveDeleteException(@CheckForNull String str) {
        super(str, null, "unable to guarantee security of recursive delete");
    }
}
