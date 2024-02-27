package javassist.tools.rmi;

import com.google.common.base.Ascii;
import java.applet.Applet;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.URL;

/* loaded from: classes2.dex */
public class ObjectImporter implements Serializable {
    private static final Class<?>[] proxyConstructorParamTypes = {ObjectImporter.class, Integer.TYPE};
    private static final long serialVersionUID = 1;
    private int orgPort;
    private String orgServername;
    private int port;
    private String servername;
    private final byte[] endofline = {Ascii.CR, 10};
    protected byte[] lookupCommand = "POST /lookup HTTP/1.0".getBytes();
    protected byte[] rmiCommand = "POST /rmi HTTP/1.0".getBytes();

    public ObjectImporter(Applet applet) {
        URL codeBase = applet.getCodeBase();
        String host = codeBase.getHost();
        this.servername = host;
        this.orgServername = host;
        int port = codeBase.getPort();
        this.port = port;
        this.orgPort = port;
    }

    public ObjectImporter(String str, int i) {
        this.servername = str;
        this.orgServername = str;
        this.port = i;
        this.orgPort = i;
    }

    public Object getObject(String str) {
        try {
            return lookupObject(str);
        } catch (ObjectNotFoundException unused) {
            return null;
        }
    }

    public void setHttpProxy(String str, int i) {
        String str2 = "POST http://" + this.orgServername + ":" + this.orgPort;
        this.lookupCommand = (str2 + "/lookup HTTP/1.0").getBytes();
        this.rmiCommand = (str2 + "/rmi HTTP/1.0").getBytes();
        this.servername = str;
        this.port = i;
    }

    public Object lookupObject(String str) throws ObjectNotFoundException {
        try {
            Socket socket = new Socket(this.servername, this.port);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(this.lookupCommand);
            outputStream.write(this.endofline);
            outputStream.write(this.endofline);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeUTF(str);
            objectOutputStream.flush();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            skipHeader(bufferedInputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
            int readInt = objectInputStream.readInt();
            String readUTF = objectInputStream.readUTF();
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
            if (readInt >= 0) {
                return createProxy(readInt, readUTF);
            }
            throw new ObjectNotFoundException(str);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ObjectNotFoundException(str, e);
        }
    }

    private Object createProxy(int i, String str) throws Exception {
        return Class.forName(str).getConstructor(proxyConstructorParamTypes).newInstance(this, Integer.valueOf(i));
    }

    public Object call(int i, int i2, Object[] objArr) throws RemoteException {
        String readUTF;
        try {
            Socket socket = new Socket(this.servername, this.port);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
            bufferedOutputStream.write(this.rmiCommand);
            bufferedOutputStream.write(this.endofline);
            bufferedOutputStream.write(this.endofline);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
            objectOutputStream.writeInt(i);
            objectOutputStream.writeInt(i2);
            writeParameters(objectOutputStream, objArr);
            objectOutputStream.flush();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            skipHeader(bufferedInputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
            boolean readBoolean = objectInputStream.readBoolean();
            Object obj = null;
            if (readBoolean) {
                readUTF = null;
                obj = objectInputStream.readObject();
            } else {
                readUTF = objectInputStream.readUTF();
            }
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
            if (obj instanceof RemoteRef) {
                RemoteRef remoteRef = (RemoteRef) obj;
                obj = createProxy(remoteRef.oid, remoteRef.classname);
            }
            if (readBoolean) {
                return obj;
            }
            throw new RemoteException(readUTF);
        } catch (IOException e) {
            throw new RemoteException(e);
        } catch (ClassNotFoundException e2) {
            throw new RemoteException(e2);
        } catch (Exception e3) {
            throw new RemoteException(e3);
        }
    }

    private void skipHeader(InputStream inputStream) throws IOException {
        int i;
        do {
            i = 0;
            while (true) {
                int read = inputStream.read();
                if (read < 0 || read == 13) {
                    break;
                }
                i++;
            }
            inputStream.read();
        } while (i > 0);
    }

    private void writeParameters(ObjectOutputStream objectOutputStream, Object[] objArr) throws IOException {
        objectOutputStream.writeInt(objArr.length);
        for (Object obj : objArr) {
            if (obj instanceof Proxy) {
                objectOutputStream.writeObject(new RemoteRef(((Proxy) obj)._getObjectId()));
            } else {
                objectOutputStream.writeObject(obj);
            }
        }
    }
}
