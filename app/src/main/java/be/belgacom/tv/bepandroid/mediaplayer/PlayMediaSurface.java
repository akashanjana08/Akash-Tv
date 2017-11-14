//package be.belgacom.tv.bepandroid.mediaplayer;

/* For Custum Surface View */

//import android.app.Activity;
//import android.content.Context;
//import android.graphics.PixelFormat;
//import android.graphics.drawable.Drawable;
//import android.media.MediaCodec;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.util.Log;
//import android.view.Surface;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//import com.google.android.exoplayer.ExoPlaybackException;
//import com.google.android.exoplayer.MediaCodecTrackRenderer;
//import com.google.android.exoplayer.player.Player;
//import com.google.android.exoplayer.player.PlayerListner;
//import java.io.IOException;
//import be.belgacom.tv.bepandroid.IPTVHome;
//import be.belgacom.tv.bepandroid.common.PlayerImpl;
//
///**
// * Created by Akash.Sharma on 5/1/2017.
// */
//
//public class PlayMediaSurface extends SurfaceView
//{
//    public static PlayMediaSurface playMediaSurface;
//    PlayerInterface listner;
//    Context mContext;
//    static Player mPlayer = null;
//    String TAG = "PlayMedia";
//    SurfaceCallback surfaceCallback = new SurfaceCallback();
//
//    public static PlayMediaSurface getInstance(Context mContext) {
//        if (playMediaSurface == null)
//        {
//            playMediaSurface = new PlayMediaSurface(mContext);
//
//            IPTVHome.frameLayout.addView(playMediaSurface);
//            return playMediaSurface;
//        } else {
//            return playMediaSurface;
//        }
//    }
//
//
//    private PlayMediaSurface(final Context mContext)
//    {
//        super(mContext);
//        this.mContext = mContext;
//
//        Surface surface = getHolder().getSurface();
//        getHolder().addCallback(surfaceCallback);
//        initExoplayer(surface);
//
//
//    }
//
//
//    void initExoplayer(Surface surface)
//    {
//        mPlayer = new Player(surface,mContext,2);
//        listner = new PlayerInterface(mPlayer);
//        Log.d(TAG,"initExoplayer");
//
//    }
//
//
//    public void playMediaPlayer(String videoSrc)
//    {
//
//        try {
//            mPlayer.init(videoSrc, listner);
//            mPlayer.play();
//            Log.d(TAG,"Play Player");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    public static  Player getPlayerInstance()
//    {
//        return  mPlayer;
//    }
//
//
//    public void destroyPlayMediaSurfaceInstance()
//    {
//        playMediaSurface = null;
//    }
//
//    public void stopPlayer()
//    {
//        mPlayer.stop();
//    }
//
//    public void pausePlayer()
//    {
//        mPlayer.pause();
//    }
//
//    public boolean isPlaying()
//    {
//       return mPlayer.isPlaying();
//    }
//
//    public void release(){
//
//        mPlayer.release();
//    }
//
//
//    private final class SurfaceCallback implements SurfaceHolder.Callback {
//        @Override
//        public void surfaceCreated(SurfaceHolder holder) {
//            Log.e(TAG, "OnSurface Created");
//
//        }
//
//        @Override
//        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//            Log.e(TAG, "OnSurface Changed");
//
//        }
//
//        @Override
//        public void surfaceDestroyed(SurfaceHolder holder) {
//            Log.e(TAG, "surfaceDestroyed");
//
//        }
//    }
//
//
//    public  class PlayerInterface implements PlayerListner{
//        Player iplayer;
//
//        String TAG = "TestPlayer";
//        public PlayerInterface(Player player) {
//            iplayer = player;
//        }
//
//        @Override
//        public void onPlayerStateChanged(boolean b, int i) {
//            Log.e(TAG, "int i"+i);
////           if(i == 3 && flag == true) {
////               Log.e(TAG, "Test Point 1");
////               iplayer.play();
////           }
////            if(i == 4) {
////
////                sv.getHolder().setFixedSize(800,480);
////           }
//
//
//        }
//
//        @Override
//        public void onPlayWhenReadyCommitted() {
//            Log.e(TAG, "onPlayWhenReadyCommitted");
//
//        }
//
//        @Override
//        public void onPlayerError(ExoPlaybackException e) {
//
//        }
//
//        @Override
//        public void onDroppedFrames(int i, long l) {
//
//        }
//
//        @Override
//        public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
//            Log.e(TAG, "width "+width);
//            Log.e(TAG, "height "+height);
//            Log.e(TAG, "unappliedRotationDegrees "+unappliedRotationDegrees);
//            Log.e(TAG, "pixelWidthHeightRatio "+pixelWidthHeightRatio);
//
//        }
//
//        @Override
//        public void onDrawnToSurface(Surface surface) {
//
//        }
//
//        @Override
//        public void onDecoderInitializationError(MediaCodecTrackRenderer.DecoderInitializationException e) {
//            Log.e(TAG, "onDecoderInitializationError");
//        }
//
//        @Override
//        public void onCryptoError(MediaCodec.CryptoException e) {
//
//        }
//
//        @Override
//        public void onDecoderInitialized(String s, long l, long l1) {
//
//        }
//
//        @Override
//        public void onTransferStart() {
//
//        }
//
//        @Override
//        public void onBytesTransferred(int i) {
//
//        }
//
//        @Override
//        public void onTransferEnd() {
//
//        }
//    }
//
//}
