package be.belgacom.tv.bepandroid.localserver;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by akash.sharma on 4/14/2017.
 */

public class DeviceUtill {

// Can Get These Figures Of Android
//    Log.i("TAG", "SERIAL: " + Build.SERIAL);
//    Log.i("TAG","MODEL: " + Build.MODEL);
//    Log.i("TAG","ID: " + Build.ID);
//    Log.i("TAG","Manufacture: " + Build.MANUFACTURER);
//    Log.i("TAG","brand: " + Build.BRAND);
//    Log.i("TAG","type: " + Build.TYPE);
//    Log.i("TAG","user: " + Build.USER);
//    Log.i("TAG","BASE: " + Build.VERSION_CODES.BASE);
//    Log.i("TAG","INCREMENTAL " + Build.VERSION.INCREMENTAL);
//    Log.i("TAG","SDK  " + Build.VERSION.SDK);
//    Log.i("TAG","BOARD: " + Build.BOARD);
//    Log.i("TAG","BRAND " + Build.BRAND);
//    Log.i("TAG","HOST " + Build.HOST);
//    Log.i("TAG","FINGERPRINT: "+Build.FINGERPRINT);
//    Log.i("TAG","Version Code: " + Build.VERSION.RELEASE);


    public static String getHardwareVersion() {
        return Build.MANUFACTURER;
    }

    public static String getMacAddress(Context mContext) {
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();
        return macAddress;
    }


    public static String getDeviceSerialNumber() {
        return Build.SERIAL;
    }


    public static String getSoftwareVersion() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }



    public static String getMacAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac==null) return "";
                StringBuilder buf = new StringBuilder();
                for (int idx=0; idx<mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
                return buf.toString();
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";

    }


}
