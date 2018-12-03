package workshop.gzip;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GZipResponseStream extends ServletOutputStream {
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_ENCODING = "Content-Encoding";
    private static final String GZIP = "gzip";
    private ByteArrayOutputStream stream;
    private GZIPOutputStream gzipStream;
    protected HttpServletResponse response;
    protected ServletOutputStream output;

    public GZipResponseStream(HttpServletResponse response) throws IOException {
        super();
        this.response = response;
        this.output = response.getOutputStream();
        stream = new ByteArrayOutputStream();
        gzipStream = new GZIPOutputStream(stream);
    }

    @Override
    public void close() throws IOException {
        gzipStream.finish();
        byte[] bytes = stream.toByteArray();
        response.addHeader(CONTENT_LENGTH, Integer.toString(bytes.length));
        response.addHeader(CONTENT_ENCODING, GZIP);
        output.write(bytes);
        output.flush();
        output.close();
    }

    @Override
    public void flush() throws IOException {
        gzipStream.flush();
    }

    @Override
    public void write(int b) throws IOException {
        gzipStream.write((byte) b);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void write(byte b[]) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
        gzipStream.write(b, off, len);
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
    }

}
