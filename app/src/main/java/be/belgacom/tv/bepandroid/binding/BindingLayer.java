package be.belgacom.tv.bepandroid.binding;

import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import be.belgacom.tv.bepandroid.IPTVHome;
import be.belgacom.tv.bepandroid.common.Player;
import be.belgacom.tv.bepandroid.common.PlayerImpl;


/**
 * Created by satendar.kumar on 12/15/2015.
 */
public class BindingLayer {
    private final String tag="BindingLayer";
    private Activity mActivity;
    private Player mPlayer;
    private NetworkApis networkApis;

    /**
     *
     */
    public BindingLayer(Activity mActivity){
        this.mActivity=mActivity;
        mPlayer= PlayerImpl.getInstance();
        networkApis=NetworkApisImpl.getInstance();

    }

    @JavascriptInterface
    public void showToast(String message, boolean lengthLong){
        Toast.makeText(mActivity, message, (lengthLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT)).show();
    }

    @JavascriptInterface
    public void animateTop(){
        Player mPlayer= PlayerImpl.getInstance();
        mPlayer.animateTop();
        Log.d(tag, "animateTop+");
    }

    @JavascriptInterface
    public void setVideoPosition(float x,float y,int width,int height){
        Log.d(tag, "setPosition+");
        mPlayer.setPosition(x, y, width, height);
        Log.d(tag, "setPosition+");
    }

    @JavascriptInterface
    public void scaleAndTranslate(float xBy,float yBy,float xSBy,float ySBy,int duration){
        Log.d(tag, "setPosition+");
        mPlayer.scaleAndTranslate(xBy, yBy, xSBy, ySBy, duration);
        Log.d(tag, "setPosition+");
    }

    @JavascriptInterface
    public void scaleVideoXYP(float fromX, float toX, float fromY, float toY, float pivotX, float pivotY,int duration){
        Log.d(tag, "scaleVideoXYP+");
        mPlayer.scaleXYP(fromX, toX, fromY, toY, pivotX, pivotY, duration);
        Log.d(tag, "scaleVideoXYP+");
    }

    @JavascriptInterface
    public void tuneChannel(String _sourceUrl,Object _extra_params){
        Log.d(tag, "tuneChannel+");
        mPlayer.playStream(_sourceUrl);
        Log.d(tag, "tuneChannel+");
    }

    @JavascriptInterface
    public String getMacAddress(Object _extra_params){
        Log.d(tag, "getMacAddress+");
        return networkApis.getMacAddress(_extra_params);
    }

    @JavascriptInterface
    public String getIpAddress(Object _extra_params){
        Log.d(tag, "getIpAddress+");
        return networkApis.getIpAddress(_extra_params);
    }

    @JavascriptInterface
     public String getSerialNumber(Object _extra_params){
        Log.d(tag, "getSerialNumber+");
        return networkApis.getSerialNumber(_extra_params);
    }

    @JavascriptInterface
    public String getSoftwareVersion(Object _extra_params){
        Log.d(tag, "getSoftwareVersion+");
        return networkApis.getSoftwareVersion(_extra_params);
    }

    @JavascriptInterface
    public String getHardwareVersion(Object _extra_params){
        Log.d(tag, "getHardwareVersion+");
        return networkApis.getHardwareVersion(_extra_params);
    }

    @JavascriptInterface
    public void onHpgLoaded(Object _extra_params){
        Log.d(tag, "onHpgLoaded+");
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((IPTVHome) mActivity).onHpgPageLoaded();
            }
        });
    }

}
