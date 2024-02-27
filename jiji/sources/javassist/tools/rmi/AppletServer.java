package javassist.tools.rmi;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import javassist.tools.web.Webserver;

/* loaded from: classes2.dex */
public class AppletServer extends Webserver {
    private static final byte[] okHeader = "HTTP/1.0 200 OK\r\n\r\n".getBytes();
    private Map<String, ExportedObject> exportedNames;
    private List<ExportedObject> exportedObjects;
    private StubGenerator stubGen;

    public AppletServer(String str) throws IOException, NotFoundException, CannotCompileException {
        this(Integer.parseInt(str));
    }

    public AppletServer(int i) throws IOException, NotFoundException, CannotCompileException {
        this(ClassPool.getDefault(), new StubGenerator(), i);
    }

    public AppletServer(int i, ClassPool classPool) throws IOException, NotFoundException, CannotCompileException {
        this(new ClassPool(classPool), new StubGenerator(), i);
    }

    private AppletServer(ClassPool classPool, StubGenerator stubGenerator, int i) throws IOException, NotFoundException, CannotCompileException {
        super(i);
        this.exportedNames = new Hashtable();
        this.exportedObjects = new Vector();
        this.stubGen = stubGenerator;
        addTranslator(classPool, stubGenerator);
    }

    @Override // javassist.tools.web.Webserver
    public void run() {
        super.run();
    }

    public synchronized int exportObject(String str, Object obj) throws CannotCompileException {
        ExportedObject exportedObject;
        Class<?> cls = obj.getClass();
        exportedObject = new ExportedObject();
        exportedObject.object = obj;
        exportedObject.methods = cls.getMethods();
        this.exportedObjects.add(exportedObject);
        exportedObject.identifier = this.exportedObjects.size() - 1;
        if (str != null) {
            this.exportedNames.put(str, exportedObject);
        }
        try {
            this.stubGen.makeProxyClass(cls);
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
        return exportedObject.identifier;
    }

    @Override // javassist.tools.web.Webserver
    public void doReply(InputStream inputStream, OutputStream outputStream, String str) throws IOException, BadHttpRequest {
        if (str.startsWith("POST /rmi ")) {
            processRMI(inputStream, outputStream);
        } else if (str.startsWith("POST /lookup ")) {
            lookupName(str, inputStream, outputStream);
        } else {
            super.doReply(inputStream, outputStream, str);
        }
    }

    private void processRMI(InputStream inputStream, OutputStream outputStream) throws IOException {
        Object obj;
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        int readInt = objectInputStream.readInt();
        int readInt2 = objectInputStream.readInt();
        Exception exc = null;
        try {
            ExportedObject exportedObject = this.exportedObjects.get(readInt);
            obj = convertRvalue(exportedObject.methods[readInt2].invoke(exportedObject.object, readParameters(objectInputStream)));
        } catch (Exception e) {
            logging2(e.toString());
            exc = e;
            obj = null;
        }
        outputStream.write(okHeader);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        if (exc != null) {
            objectOutputStream.writeBoolean(false);
            objectOutputStream.writeUTF(exc.toString());
        } else {
            try {
                objectOutputStream.writeBoolean(true);
                objectOutputStream.writeObject(obj);
            } catch (InvalidClassException e2) {
                logging2(e2.toString());
            } catch (NotSerializableException e3) {
                logging2(e3.toString());
            }
        }
        objectOutputStream.flush();
        objectOutputStream.close();
        objectInputStream.close();
    }

    private Object[] readParameters(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        int readInt = objectInputStream.readInt();
        Object[] objArr = new Object[readInt];
        for (int i = 0; i < readInt; i++) {
            Object readObject = objectInputStream.readObject();
            if (readObject instanceof RemoteRef) {
                readObject = this.exportedObjects.get(((RemoteRef) readObject).oid).object;
            }
            objArr[i] = readObject;
        }
        return objArr;
    }

    private Object convertRvalue(Object obj) throws CannotCompileException {
        if (obj == null) {
            return null;
        }
        String name = obj.getClass().getName();
        return this.stubGen.isProxyClass(name) ? new RemoteRef(exportObject(null, obj), name) : obj;
    }

    private void lookupName(String str, InputStream inputStream, OutputStream outputStream) throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        String readUTF = DataInputStream.readUTF(objectInputStream);
        ExportedObject exportedObject = this.exportedNames.get(readUTF);
        outputStream.write(okHeader);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        if (exportedObject == null) {
            logging2(readUTF + "not found.");
            objectOutputStream.writeInt(-1);
            objectOutputStream.writeUTF("error");
        } else {
            logging2(readUTF);
            objectOutputStream.writeInt(exportedObject.identifier);
            objectOutputStream.writeUTF(exportedObject.object.getClass().getName());
        }
        objectOutputStream.flush();
        objectOutputStream.close();
        objectInputStream.close();
    }
}
