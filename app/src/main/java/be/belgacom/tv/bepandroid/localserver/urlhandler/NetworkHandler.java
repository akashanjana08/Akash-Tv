package be.belgacom.tv.bepandroid.localserver.urlhandler;

import android.content.Context;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import be.belgacom.tv.bepandroid.mapjson.MapJson;


/**
 * Created by Akash.Sharma on 4/19/2017.
 */

public class NetworkHandler extends BaseHandler {

    Context mContext;
    private static final String CONNECTION_STATUS = "/netinfo/connection/status";
    private static final String NETWORK_DEVICE_DETAILS = "/netinfo/details/<device_name>/";


    public NetworkHandler(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public void doGetRequestHandle(HttpRequest request, HttpResponse response) {

        String jsonData = null;
        ResponseMap responseMap = ResponseMap.getInstance();
        String uri = request.getRequestLine().getUri();
        if (uri.equalsIgnoreCase(CONNECTION_STATUS)) {
            jsonData = MapJson.getJsonForMap(responseMap.getNetInfoConnectionStatus());
        } else if (uri.equalsIgnoreCase(NETWORK_DEVICE_DETAILS)) {
            jsonData = MapJson.getJsonForMap(responseMap.getNetInfodetails());
        }

        setHttpEntityForResponse(jsonData, response);
    }
}

