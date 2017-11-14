package be.belgacom.tv.bepandroid.common;

import android.app.Activity;
import android.media.MediaCodec;
import android.net.Uri;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.FrameworkSampleSource;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecVideoTrackRenderer;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;
import com.google.android.exoplayer.upstream.Allocator;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.upstream.TransferListener;

import be.belgacom.tv.bepandroid.mediaplayer.RtpDataSource;


/**
 * Created by satendar.kumar on 12/24/2015.
 */
public class PlayerImpl implements Player {

    //private SurfaceView mSurfaceView;
    private Surface mSurface;
    private TextureView mTextureView;
    private ExoPlayer mPlayer = null;
    private String tag="PlayerImpl";
    private static PlayerImpl mInstace;
    private Activity mActivity;
    private final int RENDER_COUNT = 2;
    private static final int BUFFER_SEGMENT_SIZE = 188;
    private static final int BUFFER_SEGMENT_COUNT = 65535;
    TransferListener listner = null;

    public static PlayerImpl getInstance(){
        if(mInstace==null){
            mInstace=new PlayerImpl();
        }

        return mInstace;
    }

    @Override
    public void init(Surface surfaceView,TextureView mTextureView,Activity activity) {
        if(mPlayer == null) {
            Log.e(tag, "PlayerImpl Init");
            mPlayer = ExoPlayer.Factory.newInstance(RENDER_COUNT);
        }
        this.mSurface = surfaceView;
        this.mActivity = activity;
        this.mTextureView=mTextureView;
    }

    @Override
    public void playStream(String mSource) {
        if(this.mTextureView!=null&&this.mTextureView.getAlpha()==0){
            this.mTextureView.setAlpha(1);
        }
        if(mPlayer == null){
            Log.i(tag, "Player not initialized");
            return;
        }
        /*Enable this to test multicast */
        //mSource="rtp://239.67.190.2:1102";
        Uri uri = Uri.parse(mSource);
        if((mSource.substring(0,3).equals("rtp"))) {
            Allocator allocator = new DefaultAllocator(BUFFER_SEGMENT_SIZE);
            DataSource dataSource = new RtpDataSource(listner,BUFFER_SEGMENT_COUNT);
            ExtractorSampleSource sampleSource = new ExtractorSampleSource(
                    uri, dataSource, allocator, BUFFER_SEGMENT_COUNT * BUFFER_SEGMENT_SIZE);
            MediaCodecVideoTrackRenderer videoRenderer = new MediaCodecVideoTrackRenderer(mActivity.getApplicationContext(), sampleSource, MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
            MediaCodecAudioTrackRenderer audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource);
            mPlayer.prepare(videoRenderer, audioRenderer);
            mPlayer.sendMessage(videoRenderer, MediaCodecVideoTrackRenderer.MSG_SET_SURFACE, mSurface);
            mPlayer.setPlayWhenReady(true);
        }
        else if(mSource.substring(0,4).equals("http")) {
            try {
                if (mPlayer != null && mActivity != null) {
                    FrameworkSampleSource sampleSource = new FrameworkSampleSource(mActivity.getApplicationContext(), uri, null);
                    MediaCodecVideoTrackRenderer videoRenderer = new MediaCodecVideoTrackRenderer(mActivity.getApplicationContext(), sampleSource, MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                    MediaCodecAudioTrackRenderer audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource);
                    mPlayer.prepare(videoRenderer, audioRenderer);
                    mPlayer.sendMessage(videoRenderer, MediaCodecVideoTrackRenderer.MSG_SET_SURFACE, mSurface);
                    mPlayer.setPlayWhenReady(true);
                }
            } catch (Exception exception) {
                Log.e(tag, "Error while initializing player" + exception.getMessage());
            }
        }

    }

    @Override
    public void animateTop() {
        this.mTextureView.animate().translationXBy(-200);
    }

    @Override
    public void animateCenter() {
        //TODO
    }

    @Override
    public void scaleAndTranslate(float xBy,float yBy,float xSBy,float ySBy,int duration) {
        Log.d(tag,"Animation already exists "+this.mTextureView.getAnimation());
        this.mTextureView.animate().scaleXBy(xSBy).scaleYBy(ySBy).xBy(xBy).yBy(yBy).setDuration(duration).start();
    }

    @Override
    public void scaleXYP(float fromX, float toX, float fromY, float toY, float pivotX, float pivotY, int duration) {
        ScaleAnimation scaleAnimation=new ScaleAnimation(fromX,toX,fromY,toY,pivotX,pivotY);
        scaleAnimation.setDuration(duration);
        this.mTextureView.startAnimation(scaleAnimation);
    }

    @Override
    public void setPosition(final float x,final float y,final int width,final int height) {
        this.mTextureView.setX(x);
        this.mTextureView.setY(y);

        final TextureView textureView =mTextureView;

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams=textureView.getLayoutParams();
                layoutParams.width=width;
                layoutParams.height=height;
                textureView.setX(x);
                textureView.setY(y);
                textureView.setLayoutParams(layoutParams);
            }
        });

        //
    }
}
