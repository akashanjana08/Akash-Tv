package be.belgacom.tv.bepandroid.binding;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by satendar.kumar on 1/14/2016.
 */
public class NetworkApisImpl implements NetworkApis {
    private static NetworkApis networkApis;
    private static String tag = "NetworkApisImpl";
    private String macAddress;
    private String serialNumber;
    private Activity mHome;

    public static NetworkApis getInstance() {
        if (networkApis == null) {
            networkApis = new NetworkApisImpl();
        }
        return networkApis;
    }

    @Override
    public String getMacAddress(Object _params) {
        Log.d(tag,"getMacAddress+");
        String macAddress = "";
        try {

            //TODO reuse this
            WifiManager wifiManager = (WifiManager) mHome.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wInfo = wifiManager.getConnectionInfo();

            macAddress=wInfo.getMacAddress();
            macAddress= macAddress.replaceAll(":", "");
            macAddress=macAddress.toUpperCase();

        } catch (Exception unExp) {
            Log.e(tag, unExp + "");
        }
        return macAddress;
    }

    @Override
    public String getSerialNumber(Object _params) {
        Log.d(tag,"getSerialNumber+");
        serialNumber=System.currentTimeMillis()+"";
        serialNumber=android.os.Build.SERIAL;
        Log.d(tag,"serialNumber "+android.os.Build.SERIAL);
        return serialNumber;
    }

    @Override
    public void setmHome(Activity mHome) {
        this.mHome = mHome;
    }
    public String getIpAddress(Object _params) {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces =
                    NetworkInterface.getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces.nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    String ipAddress = "";
                    if (inetAddress.isLoopbackAddress()) {
                        ipAddress = "LoopbackAddress: ";
                    } else if (inetAddress.isSiteLocalAddress()) {
                        ipAddress = "SiteLocalAddress: ";
                        ip = inetAddress.getHostAddress();
                    } else if (inetAddress.isLinkLocalAddress()) {
                        ipAddress = "LinkLocalAddress: ";
                    } else if (inetAddress.isMulticastAddress()) {
                        ipAddress = "MulticastAddress: ";
                    }

                }

            }
        } catch (SocketException e) {
            Log.d(tag, e.getMessage());
            e.printStackTrace();
        }
        Log.d(tag, " Device IP Address " + ip);
        return ip;
    }

    @Override
    public String getHardwareVersion(Object _params) {
        Log.d(tag,"getHardwareVersion+");
        return Build.VERSION.SDK_INT+"";
    }

    @Override
    public String getSoftwareVersion(Object _params) {
        Log.d(tag,"getSoftwareVersion+");
        return Build.VERSION.RELEASE;
    }
}
