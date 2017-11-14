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

/**
 * Created by satendar.kumar on 2/16/2016.
 */
public class SoftwareUpdateFragment extends Fragment
{

    private static final String TAG="SoftwareUpdateFragment";;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView+");
        inflater.inflate(R.layout.software_update, container);
        Handler handler=new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((IPTVHome)getActivity()).setFragment(Constants.CONNECTING_FRAGMENT);
            }
        },1000);


        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
