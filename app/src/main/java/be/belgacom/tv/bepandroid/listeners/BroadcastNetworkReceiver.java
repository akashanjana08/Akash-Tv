package be.belgacom.tv.bepandroid.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import be.belgacom.tv.bepandroid.common.NetworkConnectionUtil;

/**
 * Created by Narendra.Kumar on 2/18/2016.
 */
public class BroadcastNetworkReceiver extends BroadcastReceiver {

    private Context context=null;
    private String tag="BroadcastNetworkReceiver";


    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.d(tag,"onReceive+");
        this.context=context;
        if (intent == null || intent.getExtras() == null)
            return;
        notifyListeners();
        Log.d(tag,"onReceive-");
    }

    /*private Boolean checkStateChanged() {
        Log.d(tag,"checkStateChanged+");
        Boolean isEthernetConnectedPrev=isEthernetConnected;
        isEthernetConnected = NetworkConnectionUtil.isEthernetConnected(context);
        Log.d(tag,"isEthernetConnected "+isEthernetConnected);
        Log.d(tag,"checkStateChanged-");
        return isEthernetConnectedPrev != isEthernetConnected;
    }*/

    private void notifyListeners() {
        for (NetworkStateReceiverListener listener : AppListeners.getInstance().getNetworkListeners()) {
            notifyState(listener);
        }
    }

    private void notifyState(NetworkStateReceiverListener networkStateReceiverListener) {
        if (networkStateReceiverListener != null) {
            Boolean isEthernetConnected = NetworkConnectionUtil.isEthernetConnected(context);
            if (isEthernetConnected) {
                networkStateReceiverListener.onNetworkConnected();
            } else {
                //Disabled network connection notification.
                //networkStateReceiverListener.onNetworkDisconnected();
            }
        }
    }

}