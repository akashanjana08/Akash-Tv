package be.belgacom.tv.bepandroid.screens;

import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.belgacom.tv.bepandroid.R;
import be.belgacom.tv.bepandroid.listeners.BroadcastNetworkReceiver;


/**
 * Created by satendar.kumar on 2/16/2016.
 */
public class ConnectingErrorFragment extends Fragment  {

    private static final String TAG="ConnectingErrorFragment";;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView+");
        inflater.inflate(R.layout.connecting_error, container);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
