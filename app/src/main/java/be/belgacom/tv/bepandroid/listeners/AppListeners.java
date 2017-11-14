package be.belgacom.tv.bepandroid.listeners;

import java.util.ArrayList;

import be.belgacom.tv.bepandroid.listeners.NetworkStateReceiverListener;

/**
 * Created by satendar.kumar on 2/19/2016.
 */
public class AppListeners {

    private static AppListeners mInstance;

    private ArrayList<NetworkStateReceiverListener> networkListeners=new ArrayList<>();

    public void registerNetworkListener(NetworkStateReceiverListener listener){
        networkListeners.add(listener);
    }

    public ArrayList<NetworkStateReceiverListener> getNetworkListeners(){
        return this.networkListeners;
    }



    public static AppListeners getInstance(){
        if(mInstance==null){
            mInstance=new AppListeners();
        }
        return mInstance;
    }
}
