package be.belgacom.tv.bepandroid.localserver.urlhandler;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import be.belgacom.tv.bepandroid.localserver.DeviceUtill;
import be.belgacom.tv.bepandroid.localserver.NetworkUtill;


/**
 * Created by akash.sharma on 4/17/2017.
 */
public class ResponseMap {
    static ResponseMap responseMap;

    public Map<String, Object> getInitAtBoot() {
        Log.e("Handler_Map", "InitAtBoot");

        Map<String, Object> systemMap = new HashMap<String, Object>();
        systemMap.put("global_state", new Integer(1));
        return systemMap;
    }


    public Map<String, Object> getSystemInfo(Context mContext)
    {
        String mac = DeviceUtill.getMacAddress("eth0");
        Log.e("Handler_Map", "SystemInfoo");
        Map<String, Object> systemMap = new HashMap<String, Object>();


        systemMap.put("serial_number", DeviceUtill.getDeviceSerialNumber());
        //systemMap.put("serial_number", "unknown");
        systemMap.put("ip_address", NetworkUtill.getIpAddress());
        //systemMap.put("ip_address", "10.67.194.111");
        //systemMap.put("hardware_version", DeviceUtill.getHardwareVersion());
        systemMap.put("hardware_version", "IPTV.CISCO.IPV5001");
        systemMap.put("software_version", DeviceUtill.getSoftwareVersion());

        if (mac != null) {
            systemMap.put("mac_address", mac);
            //systemMap.put("mac_address", "44:32:c8:9f:c1:05");
        } else {
            systemMap.put("mac_address", "44:32:c8:9f:c1:05");
        }
        return systemMap;
    }


    public Map<String, Object> getSystemsoftware() {
        Log.e("Handler_Map", "Systemsoftware");
        Map<String, Object> systemMap = new HashMap<String, Object>();
        systemMap.put("sw_version", DeviceUtill.getSoftwareVersion());
        Log.e("Handler_Map", "Systemsoftware");
        return systemMap;
    }


    public Map<String, Object> getDeviceSerialNumber() {
        Log.e("Handler_Map", "DeviceSerialNumber");
        Map<String, Object> systemMap = new HashMap<String, Object>();
        systemMap.put("serial_number", DeviceUtill.getDeviceSerialNumber());
        return systemMap;
    }


    public Map<String, Object> getDeviceHardwareVersion() {
        Log.e("Handler_Map", "DeviceHardwareVersion");
        Map<String, Object> systemMap = new HashMap<String, Object>();
        systemMap.put("hw_version", "IPTV.CISCO.IPV5001");
        return systemMap;
    }

    public Map<String, Object> getBoardStatusGet() {
        Log.e("Handler_Map", "BoardStatusGet");
        Map<String, Object> systemMap = new HashMap<String, Object>();
        systemMap.put("status", "awake");
        return systemMap;
    }


    public Map<String, Object> getNetInfodetails() {
        Log.e("Handler_Map", "getNetInfoConnectionStatus");
        Map<String, Object> systemMap = new HashMap<String, Object>();
        systemMap.put("domain", "nat.myrio.net");
        systemMap.put("broadcast_address", "10.67.196.255");
        systemMap.put("dns2", "0.0.0.0");
        systemMap.put("dns1", "10.67.196.30");
        systemMap.put("interface", "eth0");
        systemMap.put("bootFile", "CVT/1/239.0.0.1:10+SA=239.0.0.1:10+SAP/3/239.67.196.3:9875");
        systemMap.put("network_type", "802.3");
        systemMap.put("ntpServers", "10.67.196.30");
        systemMap.put("connectivity", "normal");
        systemMap.put("netmask", "255.255.255.0");
        systemMap.put("active_network", "");
        systemMap.put("gateway", "10.67.196.254");
        systemMap.put("mac_address", "2c:ab:a4:52:de:df");
        systemMap.put("active", "true");
        systemMap.put("ip_address", "10.67.196.238");
        systemMap.put("carrier", "true");
        return systemMap;
    }


    public Map<String, Object> getNetInfoConnectionStatus() {
        Log.e("Handler_Map", "BoardStatusGet");
        Map<String, Object> systemMap = new HashMap<String, Object>();
        systemMap.put("connection_status", "connected");
        return systemMap;
    }


    public Map<String, Object> getStbSubscriptionSubscribeAtBoot() {
        Log.e("Handler_Map", "BoardStatusGet");
        Map<String, Object> systemMap = new HashMap<String, Object>();
        systemMap.put("pincode", "261001");
        systemMap.put("account_number", "980022331144");
        return systemMap;
    }


    public Map<String, Object> getMediaplayer() {
        Log.e("Handler_Map", "mediaplayer");
        Map<String, Object> systemMap = new HashMap<String, Object>();
        systemMap.put("href", "/mediaplayer/4/");
        systemMap.put("user_name", "8510809230");
        return systemMap;
    }


    public Map<String, Object> getMediaplayerZorderSet() {
        Log.e("Handler_Map", "MediaplayerZorderSet");
        Map<String, Object> systemMap = new HashMap<String, Object>();
        systemMap.put("zorder", new Integer(3));

        return systemMap;
    }


    //  '{"x":10, "y":10, "width":500, "height":200}'
    public Map<String, Object> getMediaplayerstreamsizeSet() {
        Log.e("Handler_Map", "MediaplayerZorderSet");
        Map<String, Object> systemMap = new HashMap<String, Object>();
        systemMap.put("x", new Integer(10));
        systemMap.put("y", new Integer(10));
        systemMap.put("width", new Integer(500));
        systemMap.put("height", new Integer(200));
        return systemMap;
    }

    public Map<String, Object> initMediaplayer(String mediaplayer_uuid)
    {
        Log.e("Handler_Map", "Init Media Player");
        Map<String, Object> systemMap = new HashMap<String, Object>();

        String urlRes = "/mediaplayer/"+mediaplayer_uuid+"/";


        systemMap.put("href", urlRes);

        return systemMap;
    }

    public Map<String, Object> getCurrentInstanceMediaplayer(String mediaplayer_uuid)
    {
        Log.e("Handler_Map", "CurrentInstanceMediaplayer");
        Map<String, Object> systemMap = new HashMap<String, Object>();

        String urlRes = "/mediaplayer/"+mediaplayer_uuid+"/";

        systemMap.put("href", urlRes);
        systemMap.put("user_name", "sheel");

        return systemMap;
    }


    public static ResponseMap getInstance() {

        if (responseMap != null) {
            return responseMap;
        } else {
            return new ResponseMap();
        }
    }


}
