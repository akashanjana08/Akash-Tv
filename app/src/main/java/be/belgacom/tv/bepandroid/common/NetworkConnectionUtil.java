package be.belgacom.tv.bepandroid.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

import be.belgacom.tv.bepandroid.binding.NetworkApis;

/**
 * Created by Narendra.Kumar on 2/18/2016.
 */
public class NetworkConnectionUtil {

    private static String networkMessage=null;
    private static ConnectivityManager  connectivityManager=null;
    private static NetworkInfo networkInfo=null;
    private static NetworkApis networkApis;
    private static String macAddress;
    private static  String hpgURL;
    private static NetworkInfo isNetworkAvailable(final Context context) {
    Boolean isNetworkAvailable=Boolean.FALSE;
        String network_message=String.format(Constants.NETWORK_STATUS_MESSAGE,Constants.BLANK_STRING,Constants.BLANK_STRING,Constants.NOT_AVAILABLE);
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
         if(activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()){
             isNetworkAvailable=Boolean.TRUE;
             network_message=String.format(Constants.NETWORK_STATUS_MESSAGE,Constants.BLANK_STRING,Constants.BLANK_STRING,Constants.AVAILABLE);
             setConnectivityManager(connectivityManager);
             setNetworkInfo(activeNetworkInfo);
         }
        else{
             isNetworkAvailable=Boolean.TRUE;
         }
        setNetworkMessage(network_message);
        return activeNetworkInfo;
    }




    public static Boolean isEthernetConnected(final Context context)
    {
        Boolean isEthernetConnected=Boolean.FALSE;
        String network_message=String.format(Constants.NETWORK_STATUS_MESSAGE,Constants.ETHERNET_LAN,Constants.TYPE_NOT_CONNECTED, Constants.NOT_AVAILABLE);
        NetworkInfo activeNetworkInfo=isNetworkAvailable(context);

        if(activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()){
            int type = activeNetworkInfo.getType();

            if (type == ConnectivityManager.TYPE_ETHERNET)
            {
                isEthernetConnected=Boolean.TRUE;
                network_message=String.format(Constants.NETWORK_STATUS_MESSAGE,Constants.ETHERNET_LAN,String.valueOf(ConnectivityManager.TYPE_ETHERNET),Constants.AVAILABLE);
            }
        }
        setNetworkMessage(network_message);
        return isEthernetConnected;
    }





    public static Boolean isWifiConnected(final Context context){

        Boolean isWifiConnected=Boolean.FALSE;
        String network_message=String.format(Constants.NETWORK_STATUS_MESSAGE,Constants.WI_FI,Constants.TYPE_NOT_CONNECTED,Constants.NOT_AVAILABLE);
        NetworkInfo activeNetworkInfo=isNetworkAvailable(context);

        if(activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()){
            ConnectivityManager cm
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                isWifiConnected=Boolean.TRUE;
                network_message=String.format(Constants.NETWORK_STATUS_MESSAGE,Constants.WI_FI,String.valueOf(ConnectivityManager.TYPE_WIFI),Constants.AVAILABLE);
            }
        }
        setNetworkMessage(network_message);
        return isWifiConnected;
    }


    public static Boolean isMobileDataConnected(final Context context){

        Boolean isMobileDataConnected=Boolean.FALSE;
        String network_message=String.format(Constants.NETWORK_STATUS_MESSAGE,Constants.MOBILE_DATA,Constants.TYPE_NOT_CONNECTED,Constants.NOT_AVAILABLE);
        NetworkInfo activeNetworkInfo=isNetworkAvailable(context);

        if(activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()){

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                isMobileDataConnected=Boolean.TRUE;
                network_message=String.format(Constants.NETWORK_STATUS_MESSAGE,Constants.MOBILE_DATA,String.valueOf(ConnectivityManager.TYPE_MOBILE),Constants.AVAILABLE);
            }
        }
        setNetworkMessage(network_message);
        return isMobileDataConnected;
    }

    public static String getNetworkMessage() {
        return networkMessage;
    }

    public static void setNetworkMessage(String networkMessage) {
        NetworkConnectionUtil.networkMessage = networkMessage;
        Log.d(Constants.NETWORK_CONNECTION_DEBUG_TAG, NetworkConnectionUtil.networkMessage);
    }

    public static ConnectivityManager getConnectivityManager() {
        return connectivityManager;
    }

    public static void setConnectivityManager(ConnectivityManager connectivityManager) {
        NetworkConnectionUtil.connectivityManager = connectivityManager;
    }

    public static NetworkInfo getNetworkInfo() {
        return networkInfo;
    }

    public static void setNetworkInfo(NetworkInfo networkInfo) {
        NetworkConnectionUtil.networkInfo = networkInfo;
    }
    public static void isHPGAvailable(final Handler mHandler) {
        if(true){
            mHandler.sendEmptyMessage(Constants.HPG_AVAILABLE);
            return;
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                Boolean isHPGAvailable=Boolean.FALSE;
                try {
                    URL myUrl = new URL(  /*Constants.HPG_IS_AVAILABLE_URL*/"");
                    HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.connect();
                    if(connection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                        isHPGAvailable = Boolean.TRUE;
                        mHandler.sendEmptyMessage(Constants.HPG_AVAILABLE);
                    }
                    else{
                        mHandler.sendEmptyMessage(Constants.HPG_NOT_AVAILABLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(Constants.HPG_NOT_AVAILABLE);
                }
                Log.d(Constants.NETWORK_CONNECTION_DEBUG_TAG, "isHPGAvailable : "+isHPGAvailable);
            }
        }).start();
     }
}
