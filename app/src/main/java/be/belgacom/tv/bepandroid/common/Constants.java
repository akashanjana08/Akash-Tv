package be.belgacom.tv.bepandroid.common;

/**
 * Created by satendar.kumar on 2/16/2016.
 */
public interface Constants
{
      //public String HPG_HOME_URL="http://10.131.126.147:8080/bepweb/boot?deviceType=technicolor";
     //public String HPG_HOME_URL="http://10.131.126.147:8080/bepweb/boot?deviceType=miniv5webkit";
     public String HPG_HOME_URL="http://10.131.126.147:8080/bepweb/boot?deviceType=v6";            /************************* Local Url *********************  */
     // public String HPG_HOME_URL="http://10.131.126.60:8080/bepweb/boot?deviceType=v6";
     //public String HPG_HOME_URL="http://hpg.nat.myrio.net:8080/bepweb-1.0/boot?deviceType=v6";
     //public String HPG_HOME_URL="http://hpg.nat.myrio.net/boot_v6.html";                             /* *********************** Live URL **********************  */

   // public String HPG_HOME_URL="http://10.0.0.2:8080/bepweb/boot?deviceType=miniv5webkit";
    //public String HPG_HOME_URL="http://hpg.nat.myrio.net:8080/bepweb/boot?deviceType=miniv5webkit";

    //public String HPG_IS_AVAILABLE_URL="http://10.67.186.41:8080/bepweb-1.0/isAvailable";
    public String DEFAULT_FRAGMENT="DEFAULT_FRAGMENT";
    public String CONNECTING_FRAGMENT="CONNECTING_FRAGMENT";
    public String CONNECTION_SUCCESS_FRAGMENT="CONNECTION_SUCCESS_FRAGMENT";
    public String CONNECTING_ERROR_FRAGMENT="CONNECTING_ERROR_FRAGMENT";
    public String NETWORK_CONNECTION_DEBUG_TAG = "NETWORK CONNECTION";
    public String NETWORK_STATUS_MESSAGE ="%s(%s) Network :  %s ";
    public String AVAILABLE="Available";
    public String NOT_AVAILABLE="Not Available";
    public String MESSAGE ="";
    public String BLANK_STRING="";
    public String ETHERNET_LAN="Ethernet LAN";
    public String WI_FI="WI-FI";
    public String MOBILE_DATA="Mobile Data";
    public String TYPE_NOT_CONNECTED = "0";
    int HPG_AVAILABLE=1;
    int HPG_NOT_AVAILABLE=2;
}
