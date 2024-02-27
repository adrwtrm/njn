package javassist;

import java.io.InputStream;
import java.net.URL;

/* loaded from: classes2.dex */
public interface ClassPath {
    URL find(String str);

    InputStream openClassfile(String str) throws NotFoundException;
}
