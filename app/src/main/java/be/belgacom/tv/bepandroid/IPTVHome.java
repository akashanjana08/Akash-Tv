package be.belgacom.tv.bepandroid;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.MediaCodec;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.exoplayer.AspectRatioFrameLayout;
import com.google.android.exoplayer.ExoPlaybackException;
import com.google.android.exoplayer.MediaCodecTrackRenderer;
import be.belgacom.tv.bepandroid.binding.BindingLayer;
import be.belgacom.tv.bepandroid.binding.NetworkApis;
import be.belgacom.tv.bepandroid.binding.NetworkApisImpl;
import be.belgacom.tv.bepandroid.common.Constants;
import be.belgacom.tv.bepandroid.common.Player;
import be.belgacom.tv.bepandroid.common.PlayerImpl;
import be.belgacom.tv.bepandroid.listeners.NetworkStateReceiverListener;
import be.belgacom.tv.bepandroid.localserver.WebServer;
import be.belgacom.tv.bepandroid.localserver.recording.RecordingWebServer;
import be.belgacom.tv.bepandroid.mediaplayer.ExoPlayerImpl;
import be.belgacom.tv.bepandroid.mediaplayer.PlayerListner;
import be.belgacom.tv.bepandroid.screens.ConnectingErrorFragment;
import be.belgacom.tv.bepandroid.screens.ConnectingFragment;
import be.belgacom.tv.bepandroid.screens.ConnectionSuccessFragment;
import be.belgacom.tv.bepandroid.screens.SoftwareUpdateFragment;

public class IPTVHome extends Activity implements TextureView.SurfaceTextureListener, NetworkStateReceiverListener
{

    public static WebView webView;
    private TextureView mTextureView;
    private Player mPlayer;
    private Surface mSurfaceView;
    WebServer webServer;
    RecordingWebServer recordingWebServer;
    public static FrameLayout frameLayout;
    public  SurfaceView surfaceView;
    SurfaceCallback surfaceCallback = new SurfaceCallback();
    private String tag = "IPTVHome";
    private ProgressDialog mDialog;
    private NetworkApis networkApis;
    private String macAddress;
    private RelativeLayout fragmentHolder;
    private Fragment mSelectedFragment;
    ExoPlayerImpl exoMedPlayer;
    PlayerInterface listner;


    public ExoPlayerImpl getPLayerInstance()
    {
        return exoMedPlayer;
    }

    public void setStream(String streamLink)
    {
        exoMedPlayer.release();
        exoMedPlayer.init(streamLink, listner);
        exoMedPlayer.play();
    }


   public void pausePlayer()
    {
        exoMedPlayer.pause();
    }

    public void playPlayer()
    {
        exoMedPlayer.play();
    }

    public void seek(long timeUs) {

        exoMedPlayer.seek(timeUs);
    }



    private class CustomClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d(tag, "onPageFinished+");
            hideDil();
            webView.clearCache(true);

            webView.setBackgroundColor(Color.TRANSPARENT);

            super.onPageFinished(view, url);
            Log.d(tag, "onPageFinished-");
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(tag, "onPageStarted+" + url);
            super.onPageStarted(view, url, favicon);
            Log.d(tag, "onPageStarted-");
        }
    }


    protected void onCreate(Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        setContentView(R.layout.web_view);

        mDialog = new ProgressDialog(this);
        webView = (WebView) findViewById(R.id.htmlClientView);

        surfaceView = (SurfaceView)findViewById(R.id.surfaceview);

        surfaceView.setVisibility(View.INVISIBLE);
        Surface surface = surfaceView.getHolder().getSurface();
        surfaceView.getHolder().addCallback(surfaceCallback);

        exoMedPlayer = new ExoPlayerImpl(surface,this.getApplicationContext(),2);
        listner = new PlayerInterface(exoMedPlayer);


        frameLayout = (FrameLayout)findViewById(R.id.frame_layout);
      //  fragmentHolder = (RelativeLayout) findViewById(R.id.nativeScreenHolder);

        networkApis = NetworkApisImpl.getInstance();
        networkApis.setmHome(this);

        //setFragment(Constants.DEFAULT_FRAGMENT);
         enableWebView();
        //AppListeners.getInstance().registerNetworkListener(this);


        webServer = WebServer.getInstance(this);
        Thread serverThread = new Thread(webServer);
        serverThread.start();

        recordingWebServer = RecordingWebServer.getInstance(this);
        Thread recserverThread = new Thread(recordingWebServer);
        recserverThread.start();

        super.onCreate(savedInstanceState);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        int screenWidth = metrics.widthPixels;

        //Toast.makeText(this,screenHeight +" , "+screenWidth,Toast.LENGTH_LONG).show();
    }

    public void setFragment(String fragmentId) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (fragmentId) {
            case Constants.DEFAULT_FRAGMENT:
                mSelectedFragment = new SoftwareUpdateFragment();
                break;

            case Constants.CONNECTING_FRAGMENT:
                mSelectedFragment = new ConnectingFragment();
                break;
            case Constants.CONNECTION_SUCCESS_FRAGMENT:
                mSelectedFragment = new ConnectionSuccessFragment();
                break;
            case Constants.CONNECTING_ERROR_FRAGMENT:
                mSelectedFragment = new ConnectingErrorFragment();
                break;

            default:
                break;
        }
       // fragmentTransaction.replace(R.id.nativeScreenHolder, mSelectedFragment, null);
       // fragmentTransaction.commit();
    }

    private void removeNativeFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(mSelectedFragment);
    }


    public void enableWebView() {
        removeNativeFragment();

        WebSettings webSettings = webView.getSettings();

        //Akash
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);


        //Akash
        webView.setWebChromeClient(new WebChromeClient() {
        });
        if (Build.VERSION.SDK_INT < 8) {
            //webView.getSettings().setPluginsEnabled(true);
        } else {
            webView.getSettings().setPluginState(WebSettings.PluginState.ON.ON);
        }


        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        /*webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);*/
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setDomStorageEnabled(true);
        webView.setInitialScale(1);
        webView.setVisibility(View.VISIBLE);
        webView.requestFocus();


        BindingLayer bl = new BindingLayer(this);
        webView.addJavascriptInterface(bl, "BL_IPTV");

        webView.setWebViewClient(new CustomClient());

        macAddress = networkApis.getMacAddress(null);
        //  webView.loadUrl(HPG_HOME_URL + "?MACAddress=" + "602AD05B724E");
        //  webView.loadUrl(Constants.HPG_HOME_URL + "&MACAddress=" + macAddress);
        webView.loadUrl(Constants.HPG_HOME_URL);
    }

    /**
     * This method must be called when HPG server page is loaded
     */
    public void onHpgPageLoaded()
    {
        //Call back to do
        //Call back when HPG is up
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // webView.goBack();
        if (event.getAction() == KeyEvent.ACTION_DOWN)
        {

            //Toast.makeText(this,""+keyCode,Toast.LENGTH_LONG).show();

            switch (keyCode)
            {
                case KeyEvent.KEYCODE_BACK:
                    Log.d("IPTV Home ","Back called ");
                    webView.loadUrl("javascript:goBackAndroid('Hello World!')");
                     return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showDil(String message) {
        mDialog.setMessage(message);
        mDialog.show();
    }

    public void hideDil() {
        Log.d(tag, mDialog + "");
        Log.d(tag, mDialog.isShowing() + "");

        if (mDialog != null && mDialog.isShowing()) {
            Log.d(tag, "hiding it ");
            mDialog.hide();
            mDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        hideDil();
        super.onDestroy();
        webServer.stopServer();
        Log.d(tag, "On destroy called ");


    }

    private void initPlayer() {
        mPlayer = PlayerImpl.getInstance();
        mPlayer.init(mSurfaceView, mTextureView, this);
        webView.requestFocus();
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mSurfaceView = new Surface(surface);
        initPlayer();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }


    @Override
    public void onNetworkConnected() {
        Log.d(tag, "onNetworkConnected");
        removeNativeFragment();
        webView.setVisibility(View.VISIBLE);
        webView.requestFocus();
    }

    @Override
    public void onNetworkDisconnected() {
        Log.d(tag, "onNetworkDisconnected");
        webView.setVisibility(View.GONE);
        setFragment(Constants.CONNECTING_ERROR_FRAGMENT);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //PlayMediaSurface.playMediaSurface = null;
        //PlayerHandler.playMediaSurface = null;
        //Toast.makeText(this,"PlayerSurface Destro" , Toast.LENGTH_LONG).show();
        Log.d("Destroy","Destroy Activity");

    }



    private final class SurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Test.makeTest().showToast("surfaceChanged",getApplicationContext());
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

            Test.makeTest().showToast("surfaceDestroyed",getApplicationContext());
        }
    }




    public  class PlayerInterface implements PlayerListner {
        ExoPlayerImpl iplayer;

        String TAG = "TestPlayer";
        public PlayerInterface(ExoPlayerImpl player) {
            iplayer = player;
        }

        @Override
        public void onPlayerStateChanged(boolean b, int i) {
            Log.e(TAG, "int i"+i);

            Test.makeTest().showToast("onPlayerStateChanged",getApplicationContext());

//           if(i == 3 && flag == true) {
//               Log.e(TAG, "Test Point 1");
//               iplayer.play();
//           }
//            if(i == 4) {
//
//                sv.getHolder().setFixedSize(800,480);
//           }

        }

        @Override
        public void onPlayWhenReadyCommitted() {
            Log.e(TAG, "onPlayWhenReadyCommitted");


        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {
            Test.makeTest().showToast("onPlayerError",getApplicationContext());
        }

        @Override
        public void onDroppedFrames(int i, long l) {

            Test.makeTest().showToast("onDroppedFrames",getApplicationContext());

        }

        @Override
        public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
            Log.e(TAG, "width "+width);
            Log.e(TAG, "height "+height);
            Log.e(TAG, "unappliedRotationDegrees "+unappliedRotationDegrees);
            Log.e(TAG, "pixelWidthHeightRatio "+pixelWidthHeightRatio);

            Test.makeTest().showToast("onVideoSizeChanged",getApplicationContext());

        }

        @Override
        public void onDrawnToSurface(Surface surface)
        {
            Test.makeTest().showToast("onDrawnToSurface",getApplicationContext());
        }

        @Override
        public void onDecoderInitializationError(MediaCodecTrackRenderer.DecoderInitializationException e) {
            Log.e(TAG, "onDecoderInitializationError");
            Test.makeTest().showToast("onDecoderInitializationError",getApplicationContext());
        }

        @Override
        public void onCryptoError(MediaCodec.CryptoException e) {

            Test.makeTest().showToast("onCryptoError",getApplicationContext());
        }

        @Override
        public void onDecoderInitialized(String s, long l, long l1) {

            Test.makeTest().showToast("onDecoderInitialized",getApplicationContext());
        }

        @Override
        public void onTransferStart() {
            Test.makeTest().showToast("onTransferStart",getApplicationContext());

        }

        @Override
        public void onBytesTransferred(int i) {

            Test.makeTest().showToast("onBytesTransferred",getApplicationContext());
        }

        @Override
        public void onTransferEnd() {
            Test.makeTest().showToast("onTransferEnd",getApplicationContext());

        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        // Unicode is only used for 0-9 digits in case of Hot key, it returns 0
        int Unicode   = event.getUnicodeChar();

        // KeyCode is used for hot Keys
        int keyCode   = event.getKeyCode();

        Log.d("ANDROI_KEY_CODES","KEYCODE : "+keyCode +" and  Unicode : "+Unicode);

        if (event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if(keyCode >=7 &&  keyCode <=16)
            {
                keyCode = Unicode;
            }
            if(keyCode == 186)
            {
                // finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            webView.loadUrl("javascript:androidKeyEvent("+keyCode+")");
        }
        return true;
    }

}
