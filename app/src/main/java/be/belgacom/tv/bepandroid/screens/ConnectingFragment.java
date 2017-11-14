package be.belgacom.tv.bepandroid.screens;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import be.belgacom.tv.bepandroid.IPTVHome;
import be.belgacom.tv.bepandroid.R;
import be.belgacom.tv.bepandroid.common.Constants;
import be.belgacom.tv.bepandroid.common.NetworkConnectionUtil;

/**
 * Created by satendar.kumar on 2/16/2016.
 */
public class ConnectingFragment extends Fragment {

    private static final String TAG="ConnectingFragment";;
    private RelativeLayout mConnectionChecking;
    private RelativeLayout mConnectionSuccess;
    private View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView+");
        Boolean isHPGAvailable=Boolean.FALSE;
        mView=inflater.inflate(R.layout.connecting, container);

        final Boolean isEthernetConnected=NetworkConnectionUtil.isEthernetConnected(getActivity());

        if(isEthernetConnected){
            NetworkConnectionUtil.isHPGAvailable(new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Log.i(TAG, "HPG call message " + msg.what);
                    if(isEthernetConnected && msg.what==Constants.HPG_AVAILABLE){
                        connectSuccess();
                    }else{
                        connectFail();
                    }
                }
            });
        }else{
            connectFail();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    /**
     *
     */
    private void connectSuccess(){
        Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((IPTVHome)getActivity()).setFragment(Constants.CONNECTION_SUCCESS_FRAGMENT);
                }
            }, 1000);
    }

    /**
     *
     */
    private void connectFail(){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((IPTVHome) getActivity()).setFragment(Constants.CONNECTING_ERROR_FRAGMENT);
            }
        }, 1000);
    }

}
