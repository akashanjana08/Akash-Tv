package be.belgacom.tv.bepandroid.mediaplayer;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Surface;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecVideoTrackRenderer;
import com.google.android.exoplayer.TrackRenderer;
import com.google.android.exoplayer.extractor.Extractor;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;

import com.google.android.exoplayer.upstream.Allocator;
import com.google.android.exoplayer.upstream.DefaultAllocator;

import com.google.android.exoplayer.upstream.TransferListener;
import com.google.android.exoplayer.upstream.cache.CacheDataSource;

import be.belgacom.tv.bepandroid.common.Player;


public final class ExoPlayerImpl {
    private static final String TAG = "Player";
    private static final int BUFFER_SEGMENT_SIZE = 188;
    private static final int BUFFER_SEGMENT_COUNT = 100;
    public int RENDER_COUNT = 2;
    public static final int UNKNOWN_STATE = -1;
    public static final int NORMAL_PLAY = 0;
    public static final int PAUSED = 1;
    public static final int STOPED = 2;
    public static final int TRICK_MODE = 3;
    public static final int MULTICAST_LIVE = 4;
    public static final int UNICAST_LIVE = 5;
    public static final int BUFFERING = 6;
    public static Player mPlayer = null;
    ExoPlayer mExoPlayer = null;
    ExtractorSampleSource mSampleSource = null;
    Allocator allocator = null;
    TrackRenderer videoRenderer = null;
    TrackRenderer audioRenderer = null;
    private Surface mSurface = null;
    Context mContext;
    RtpDataSource dataSource;
    public int playbackStatus = -1;

    public static Player getInstance() {
        if(mPlayer == null) {
            Log.e("Player", "Player is not intialized yet");
            return null;
        } else {
            return mPlayer;
        }
    }

    public ExoPlayerImpl(Surface surface, Context context, int RenderCount) {
        Log.e("Player", "Player ++");
        this.mSurface = surface;
        this.mContext = context;
        if(RenderCount >= 0) {
            this.mExoPlayer = ExoPlayer.Factory.newInstance(RenderCount);
        } else {
            this.mExoPlayer = ExoPlayer.Factory.newInstance(this.RENDER_COUNT);
        }

    }

    public void init(String source, PlayerListner listner) {

        Log.e("Player", "init++");
        this.mExoPlayer = ExoPlayer.Factory.newInstance(this.RENDER_COUNT);
        Uri uri = Uri.parse(source);
        this.allocator = new DefaultAllocator(188,100);
        this.dataSource = new RtpDataSource((TransferListener)null, 100000);
        this.mSampleSource = new ExtractorSampleSource(uri, this.dataSource, this.allocator, 1048576, new Extractor[0]);
        this.videoRenderer = new MediaCodecVideoTrackRenderer(this.mContext, this.mSampleSource, MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        this.audioRenderer = new MediaCodecAudioTrackRenderer(this.mSampleSource);
        this.mExoPlayer.prepare(new TrackRenderer[]{this.videoRenderer, this.audioRenderer});
        this.mExoPlayer.sendMessage(this.videoRenderer, 1, this.mSurface);

        if(listner != null) {
            this.mExoPlayer.addListener(listner);
        }

        Log.e("Player", "init--");
    }

    public void start(String source, PlayerListner listner) {
        Log.e("Player", "init++");
        Uri uri = Uri.parse(source);
        Log.e("Player", "start");
        //this.mSampleSource.uri = uri;
        this.mSampleSource = new ExtractorSampleSource(uri, this.dataSource, this.allocator, 1048576, new Extractor[0]);
        this.videoRenderer = new MediaCodecVideoTrackRenderer(this.mContext, this.mSampleSource, 2);
        this.mExoPlayer.prepare(new TrackRenderer[]{this.videoRenderer});
        this.mExoPlayer.sendMessage(this.videoRenderer, 1, this.mSurface);
        if(listner != null) {
            this.mExoPlayer.addListener(listner);
        }
    }

    public void seek(long timeUs) {
        Log.e("Player", "seek");
        this.mExoPlayer.seekTo(timeUs);
    }

    public void play() {
        Log.e("Player", "play");
        this.playbackStatus = 0;
        this.mExoPlayer.setPlayWhenReady(true);
    }

    public void pause() {
        Log.e("Player", "pause");
        this.playbackStatus = 1;
        this.mExoPlayer.setPlayWhenReady(false);
    }

    public void stop() {
        Log.e("Player", "stop");
        this.playbackStatus = 2;
        this.mExoPlayer.stop();
    }

    public void release() {
        Log.e("Player", "release");
        this.mExoPlayer.release();
    }

    public boolean isPlaying() {
        Log.e("Player", "isPlaying");
        return this.playbackStatus == 0;
    }
}
