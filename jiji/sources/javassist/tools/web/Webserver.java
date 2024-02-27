package javassist.tools.web;

import com.google.common.base.Ascii;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.Translator;

/* loaded from: classes2.dex */
public class Webserver {
    private static final byte[] endofline = {Ascii.CR, 10};
    private static final int typeClass = 2;
    private static final int typeGif = 3;
    private static final int typeHtml = 1;
    private static final int typeJpeg = 4;
    private static final int typeText = 5;
    private ClassPool classPool;
    public String debugDir;
    public String htmlfileBase;
    private ServerSocket socket;
    protected Translator translator;

    public static void main(String[] strArr) throws IOException {
        if (strArr.length == 1) {
            new Webserver(strArr[0]).run();
        } else {
            System.err.println("Usage: java javassist.tools.web.Webserver <port number>");
        }
    }

    public Webserver(String str) throws IOException {
        this(Integer.parseInt(str));
    }

    public Webserver(int i) throws IOException {
        this.debugDir = null;
        this.htmlfileBase = null;
        this.socket = new ServerSocket(i);
        this.classPool = null;
        this.translator = null;
    }

    public void setClassPool(ClassPool classPool) {
        this.classPool = classPool;
    }

    public void addTranslator(ClassPool classPool, Translator translator) throws NotFoundException, CannotCompileException {
        this.classPool = classPool;
        this.translator = translator;
        translator.start(classPool);
    }

    public void end() throws IOException {
        this.socket.close();
    }

    public void logging(String str) {
        System.out.println(str);
    }

    public void logging(String str, String str2) {
        System.out.print(str);
        System.out.print(" ");
        System.out.println(str2);
    }

    public void logging(String str, String str2, String str3) {
        System.out.print(str);
        System.out.print(" ");
        System.out.print(str2);
        System.out.print(" ");
        System.out.println(str3);
    }

    public void logging2(String str) {
        System.out.print("    ");
        System.out.println(str);
    }

    public void run() {
        System.err.println("ready to service...");
        while (true) {
            try {
                new ServiceThread(this, this.socket.accept()).start();
            } catch (IOException e) {
                logging(e.toString());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void process(Socket socket) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        String readLine = readLine(bufferedInputStream);
        logging(socket.getInetAddress().getHostName(), new Date().toString(), readLine);
        do {
        } while (skipLine(bufferedInputStream) > 0);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        try {
            doReply(bufferedInputStream, bufferedOutputStream, readLine);
        } catch (BadHttpRequest e) {
            replyError(bufferedOutputStream, e);
        }
        bufferedOutputStream.flush();
        bufferedInputStream.close();
        bufferedOutputStream.close();
        socket.close();
    }

    private String readLine(InputStream inputStream) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            int read = inputStream.read();
            if (read < 0 || read == 13) {
                break;
            }
            stringBuffer.append((char) read);
        }
        inputStream.read();
        return stringBuffer.toString();
    }

    private int skipLine(InputStream inputStream) throws IOException {
        int i = 0;
        while (true) {
            int read = inputStream.read();
            if (read < 0 || read == 13) {
                break;
            }
            i++;
        }
        inputStream.read();
        return i;
    }

    public void doReply(InputStream inputStream, OutputStream outputStream, String str) throws IOException, BadHttpRequest {
        InputStream resourceAsStream;
        if (str.startsWith("GET /")) {
            int i = 5;
            String substring = str.substring(5, str.indexOf(32, 5));
            if (substring.endsWith(".class")) {
                i = 2;
            } else if (substring.endsWith(".html") || substring.endsWith(".htm")) {
                i = 1;
            } else if (substring.endsWith(".gif")) {
                i = 3;
            } else if (substring.endsWith(".jpg")) {
                i = 4;
            }
            int length = substring.length();
            if (i == 2 && letUsersSendClassfile(outputStream, substring, length)) {
                return;
            }
            checkFilename(substring, length);
            String str2 = this.htmlfileBase != null ? this.htmlfileBase + substring : substring;
            if (File.separatorChar != '/') {
                str2 = str2.replace('/', File.separatorChar);
            }
            File file = new File(str2);
            if (file.canRead()) {
                sendHeader(outputStream, file.length(), i);
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bArr = new byte[4096];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read > 0) {
                        outputStream.write(bArr, 0, read);
                    } else {
                        fileInputStream.close();
                        return;
                    }
                }
            } else if (i == 2 && (resourceAsStream = getClass().getResourceAsStream("/" + substring)) != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr2 = new byte[4096];
                while (true) {
                    int read2 = resourceAsStream.read(bArr2);
                    if (read2 > 0) {
                        byteArrayOutputStream.write(bArr2, 0, read2);
                    } else {
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        sendHeader(outputStream, byteArray.length, 2);
                        outputStream.write(byteArray);
                        resourceAsStream.close();
                        return;
                    }
                }
            } else {
                throw new BadHttpRequest();
            }
        } else {
            throw new BadHttpRequest();
        }
    }

    private void checkFilename(String str, int i) throws BadHttpRequest {
        for (int i2 = 0; i2 < i; i2++) {
            char charAt = str.charAt(i2);
            if (!Character.isJavaIdentifierPart(charAt) && charAt != '.' && charAt != '/') {
                throw new BadHttpRequest();
            }
        }
        if (str.indexOf("..") >= 0) {
            throw new BadHttpRequest();
        }
    }

    private boolean letUsersSendClassfile(OutputStream outputStream, String str, int i) throws IOException, BadHttpRequest {
        if (this.classPool == null) {
            return false;
        }
        String replace = str.substring(0, i - 6).replace('/', '.');
        try {
            Translator translator = this.translator;
            if (translator != null) {
                translator.onLoad(this.classPool, replace);
            }
            CtClass ctClass = this.classPool.get(replace);
            byte[] bytecode = ctClass.toBytecode();
            String str2 = this.debugDir;
            if (str2 != null) {
                ctClass.writeFile(str2);
            }
            sendHeader(outputStream, bytecode.length, 2);
            outputStream.write(bytecode);
            return true;
        } catch (Exception e) {
            throw new BadHttpRequest(e);
        }
    }

    private void sendHeader(OutputStream outputStream, long j, int i) throws IOException {
        outputStream.write("HTTP/1.0 200 OK".getBytes());
        byte[] bArr = endofline;
        outputStream.write(bArr);
        outputStream.write("Content-Length: ".getBytes());
        outputStream.write(Long.toString(j).getBytes());
        outputStream.write(bArr);
        if (i == 2) {
            outputStream.write("Content-Type: application/octet-stream".getBytes());
        } else if (i == 1) {
            outputStream.write("Content-Type: text/html".getBytes());
        } else if (i == 3) {
            outputStream.write("Content-Type: image/gif".getBytes());
        } else if (i == 4) {
            outputStream.write("Content-Type: image/jpg".getBytes());
        } else if (i == 5) {
            outputStream.write("Content-Type: text/plain".getBytes());
        }
        outputStream.write(bArr);
        outputStream.write(bArr);
    }

    private void replyError(OutputStream outputStream, BadHttpRequest badHttpRequest) throws IOException {
        logging2("bad request: " + badHttpRequest.toString());
        outputStream.write("HTTP/1.0 400 Bad Request".getBytes());
        byte[] bArr = endofline;
        outputStream.write(bArr);
        outputStream.write(bArr);
        outputStream.write("<H1>Bad Request</H1>".getBytes());
    }
}
