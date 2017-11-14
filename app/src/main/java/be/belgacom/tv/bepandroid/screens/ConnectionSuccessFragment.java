package be.belgacom.tv.bepandroid.screens;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.belgacom.tv.bepandroid.IPTVHome;
import be.belgacom.tv.bepandroid.R;
import be.belgacom.tv.bepandroid.common.Constants;
import be.belgacom.tv.bepandroid.common.NetworkConnectionUtil;

/**
 * Created by Narendra.Kumar on 2/22/2016.
 */
public class ConnectionSuccessFragment extends Fragment {

    private static final String TAG="ConnectionSuccess";;
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView+");
        Boolean isHPGAvailable=Boolean.FALSE;
        mView=inflater.inflate(R.layout.connection_success, container);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                launchHpgHidden();
        }
        }, 1000);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void launchHpgHidden(){
        ((IPTVHome) getActivity()).enableWebView();
    }


}
