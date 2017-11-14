package be.belgacom.tv.bepandroid.binding;


import android.app.Activity;
import android.os.Handler;

/**
 * Created by satendar.kumar on 1/14/2016.
 */
public interface NetworkApis {
    public String getMacAddress(Object _params);
    public String getSerialNumber(Object _params);
    public String getIpAddress(Object _params);
    public String getHardwareVersion(Object _params);
    public String getSoftwareVersion(Object _params);
    public void setmHome(Activity mHome);

}
