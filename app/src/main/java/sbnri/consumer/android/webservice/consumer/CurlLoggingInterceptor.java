package sbnri.consumer.android.webservice.consumer;

import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class CurlLoggingInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private String curlOptions;

    public CurlLoggingInterceptor() {

    }

    /**
     * Set any additional curl command options (see 'curl --help').
     */
    public void setCurlOptions(String curlOptions) {
        this.curlOptions = curlOptions;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        boolean compressed = false;

        String curlCmd = "curl";
        if (curlOptions != null) {
            curlCmd += " " + curlOptions;
        }
        curlCmd += " -X " + request.method();

        Headers headers = request.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            String name = headers.name(i);
            String value = headers.value(i);
            if ("Accept-Encoding".equalsIgnoreCase(name) && "gzip".equalsIgnoreCase(value)) {
                compressed = true;
            }
            curlCmd += " -H " + "\"" + name + ": " + value + "\"";
        }

        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            // try to keep to a single line and use a subshell to preserve any line breaks
            curlCmd += " --data $'" + buffer.readString(charset).replace("\n", "\\n") + "'";
        }

        curlCmd += ((compressed) ? " --compressed " : " ") + request.url();

        Logger.d("╭--- cURL (" + request.url() + ")" + "\n" + curlCmd + "\n" + "╰--- (copy and paste the above line to a terminal)");

        return chain.proceed(request);
    }

}
