package be.belgacom.tv.bepandroid.localserver.urlhandler;

import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Akash.Sharma on 4/19/2017.
 */

public abstract class BaseHandler implements HttpRequestHandler {


    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_OPTIONS = "OPTIONS";
    String TAG = "BASE_HANDLER";


    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {

        Log.d(TAG, httpRequest.getRequestLine().getUri());
        String requestType = "";

        requestType = httpRequest.getRequestLine().getMethod();

        switch (requestType) {
            case METHOD_GET:
                doGetRequestHandle(httpRequest, httpResponse);
                break;
            case METHOD_PUT:
                doPutRequestHandle(httpRequest, httpResponse);
                break;
            case METHOD_POST:
                doPostRequestHandle(httpRequest, httpResponse);
                break;
            case METHOD_DELETE:
                doDeleteRequestHandle(httpRequest, httpResponse);
                break;
            case METHOD_OPTIONS:
                doGetRequestHandle(httpRequest, httpResponse);
                break;
            default:
        }
    }


    public void doPostRequestHandle(HttpRequest request, HttpResponse response) {

    }


    public void doGetRequestHandle(HttpRequest request, HttpResponse response) {

    }

    public void doPutRequestHandle(HttpRequest request, HttpResponse response) {

    }

    public void doDeleteRequestHandle(HttpRequest request, HttpResponse response) {

    }

    public void doOptionsRequestHandle(HttpRequest request, HttpResponse response) {

    }

    public void setHttpEntityForResponse(final String responseEntity, HttpResponse response)

    {
        HttpEntity httpEntity = new EntityTemplate
                (
                        new ContentProducer() {

                            public void writeTo(final OutputStream outstream)
                                    throws IOException {

                                OutputStreamWriter outputStreamWriter = new OutputStreamWriter
                                        (outstream, "UTF-8");

                                Log.d("BASE_HANDLER_RESPONSE", responseEntity);
                                outputStreamWriter.write(responseEntity);
                                outputStreamWriter.flush();
                            }
                        });


        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET , PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        //response.addHeader("Content-Type", "application/json");
        response.setEntity(httpEntity);

    }


    public void setHttpEntityForResponsePlayer(final String responseEntity, HttpResponse response) {
        HttpEntity httpEntity = new EntityTemplate
                (
                        new ContentProducer() {

                            public void writeTo(final OutputStream outstream)
                                    throws IOException {

                                OutputStreamWriter outputStreamWriter = new OutputStreamWriter
                                        (outstream, "UTF-8");

                                Log.d("BASE_HANDLER_RESPONSE", responseEntity);
                                outputStreamWriter.write(responseEntity);
                                outputStreamWriter.flush();
                            }
                        });


        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET , PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        response.addHeader("Content-Type", "application/json");
        response.setEntity(httpEntity);

    }

}
