package be.belgacom.tv.bepandroid.localserver.urlhandler;

import android.content.Context;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import be.belgacom.tv.bepandroid.mapjson.MapJson;


/**
 * Created by akash.sharma on 4/18/2017.
 */


public class StandbyHandler extends BaseHandler {

    Context mContext;
    private static final String BOARD_STATUS_STATNDBY_INIT_AT_BOOT = "/board_status/standby/init_at_boot";
    private static final String BOARD_STATUS_ACK_STATE_CHANGE = "/board_status/ack_state_change/";
    private static final String BOARD_STATUS_STANDBY_DISABLE = "/board_status/standby/disable";
    private static final String BOARD_STATUS_STANDBY_ENABLE = "/board_status/standby/enable";
    private static final String BOARD_STATUS_GET = "/board_status/get";                            // Get Request URL
    private static final String BOARD_STATUS_SET_STATE = "/board_status/set_state";
    private static final String BOARD_STATUS_GET_SCREEN_VALUES = "/board_status/standby/get_screen_values";      // Get Request URL
    private static final String BOARD_STATUS_SET_SCREEN_VALUES = "/board_status/standby/set_screen_values";

    ResponseMap responseMap;

    public StandbyHandler(Context mContext) {
        this.mContext = mContext;
        responseMap = ResponseMap.getInstance();
    }


    @Override
    public void doPutRequestHandle(HttpRequest request, HttpResponse response) {
        super.doPutRequestHandle(request, response);

        String jsonData = null;

        String uri = request.getRequestLine().getUri();
        if (uri.equalsIgnoreCase(BOARD_STATUS_STATNDBY_INIT_AT_BOOT)) {
            jsonData = MapJson.getJsonForMap(responseMap.getInitAtBoot());

        } else if (uri.equalsIgnoreCase(BOARD_STATUS_ACK_STATE_CHANGE)) {
            //jsonData = MapJson.getJsonForMap(responseMap.getInitAtBoot());
        } else if (uri.equalsIgnoreCase(BOARD_STATUS_GET)) {
            jsonData = MapJson.getJsonForMap(responseMap.getBoardStatusGet());
        }


        if (jsonData != null) {
            setHttpEntityForResponse(jsonData, response);
        } else {
            setHttpEntityForResponse("", response);
        }

    }


    @Override
    public void doGetRequestHandle(HttpRequest request, HttpResponse response)
    {
        super.doGetRequestHandle(request, response);

        String uri = request.getRequestLine().getUri();
        String jsonData = null;

        if (uri.equalsIgnoreCase(BOARD_STATUS_GET)) {
            jsonData = MapJson.getJsonForMap(responseMap.getBoardStatusGet());
        }

        if (jsonData != null) {
            setHttpEntityForResponse(jsonData, response);
        } else {
            setHttpEntityForResponse("", response);
        }
    }


    @Override
    public void doOptionsRequestHandle(HttpRequest request, HttpResponse response) {
        super.doOptionsRequestHandle(request, response);

        String jsonData = null;

        String uri = request.getRequestLine().getUri();
        if (uri.equalsIgnoreCase(BOARD_STATUS_STATNDBY_INIT_AT_BOOT)) {
            jsonData = MapJson.getJsonForMap(responseMap.getInitAtBoot());

        } else if (uri.equalsIgnoreCase(BOARD_STATUS_ACK_STATE_CHANGE)) {
            //jsonData = MapJson.getJsonForMap(responseMap.getInitAtBoot());
        } else if (uri.equalsIgnoreCase(BOARD_STATUS_GET)) {
            jsonData = MapJson.getJsonForMap(responseMap.getBoardStatusGet());
        }


        if (jsonData != null) {
            setHttpEntityForResponse(jsonData, response);
        } else {
            setHttpEntityForResponse("", response);
        }
    }
}