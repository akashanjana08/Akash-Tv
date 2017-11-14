package be.belgacom.tv.bepandroid.mediaplayer;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecVideoTrackRenderer;
import com.google.android.exoplayer.upstream.TransferListener;

public interface PlayerListner extends ExoPlayer.Listener,MediaCodecVideoTrackRenderer.EventListener, TransferListener {

}
