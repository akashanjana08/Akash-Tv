package be.belgacom.tv.bepandroid.localserver.urlhandler;

import android.content.Context;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import be.belgacom.tv.bepandroid.mapjson.MapJson;

/**
 * Created by Akash.Sharma on 4/19/2017.
 */

public class SystemInfoHandler extends BaseHandler
{

    Context mContext;
    private static final String SYSTEM_INFO = "/sysdetails/info/";
    private static final String SYSTEM_SOFTWARE_VERSION = "/sysdetails/software_version/";
    private static final String DEVICE_SERIAL_NUMBER = "/devinfo/serial_number/";
    private static final String DEVICE_HARDWARE_VERSION = "/devinfo/hardware_version/";


    public SystemInfoHandler(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public void doGetRequestHandle(HttpRequest request, HttpResponse response) {

        String jsonData = null;
        ResponseMap responseMap = ResponseMap.getInstance();
        String uri = request.getRequestLine().getUri();
        if (uri.equalsIgnoreCase(SYSTEM_INFO)) {
            jsonData = MapJson.getJsonForMap(responseMap.getSystemInfo(mContext));
        } else if (uri.equalsIgnoreCase(SYSTEM_SOFTWARE_VERSION)) {
            jsonData = MapJson.getJsonForMap(responseMap.getSystemsoftware());
        } else if (uri.equalsIgnoreCase(DEVICE_SERIAL_NUMBER)) {
            jsonData = MapJson.getJsonForMap(responseMap.getDeviceSerialNumber());
        } else if (uri.equalsIgnoreCase(DEVICE_HARDWARE_VERSION)) {
            jsonData = MapJson.getJsonForMap(responseMap.getDeviceHardwareVersion());
        }

        setHttpEntityForResponse(jsonData, response);
    }

}
