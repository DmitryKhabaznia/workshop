package workshop.gzip;

import org.apache.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class GZipResponseWrapper extends HttpServletResponseWrapper {

    private static final Logger LOG = Logger.getLogger(GZipResponseWrapper.class);
    private static final String UTF_8 = "UTF-8";
    private HttpServletResponse origResponse;
    protected ServletOutputStream stream = null;
    private PrintWriter writer = null;

    public GZipResponseWrapper(HttpServletResponse response) {
        super(response);
        origResponse = response;
    }

    public void flushBuffer() throws IOException {
        stream.flush();
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (stream != null) {
            return stream;
        }
        stream = createOutputStream();
        return stream;
    }

    public PrintWriter getWriter() throws IOException {
        if (writer != null) {
            return writer;
        }
        stream = createOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(stream, UTF_8));
        return (writer);
    }

    public void closeWriter() {
        try {
            if (writer != null) {
                writer.close();
            } else if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            LOG.debug("IOException was occurred.");
        }
    }

    private ServletOutputStream createOutputStream() throws IOException {
        return (new GZipResponseStream(origResponse));
    }

}
