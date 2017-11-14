package be.belgacom.tv.bepandroid.localserver.urlhandler;

import android.content.Context;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import be.belgacom.tv.bepandroid.mapjson.MapJson;

/**
 * Created by Akash.Sharma on 4/21/2017.
 */

public class STBAssignHandler extends BaseHandler
{

    Context mContext;
    private static final String STBAssign   = "/stb_subscription/subscribe_at_boot";
    public  STBAssignHandler(Context mContext)
    {
        this.mContext = mContext;
    }


    @Override
    public void doPutRequestHandle(HttpRequest request, HttpResponse response) {
        super.doPutRequestHandle(request, response);

        String jsonData = null;
        ResponseMap responseMap = ResponseMap.getInstance();
        String uri = request.getRequestLine().getUri();
        if (uri.equalsIgnoreCase(STBAssign)) {
            jsonData = MapJson.getJsonForMap(responseMap.getNetInfoConnectionStatus());
        }

        setHttpEntityForResponse(jsonData, response);
    }
}
