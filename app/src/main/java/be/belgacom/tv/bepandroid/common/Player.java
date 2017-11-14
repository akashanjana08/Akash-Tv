package be.belgacom.tv.bepandroid.common;

import android.app.Activity;
import android.view.Surface;
import android.view.TextureView;

/**
 * Created by satendar.kumar on 12/24/2015.
 */
public interface Player {
    public void playStream(String source);
    public void init(Surface surfaceView, TextureView textureView, Activity activity);
    public void animateTop();
    public void animateCenter();
    public void scaleAndTranslate(float xBy, float yBy, float xSBy, float ySBy, int duration);
    public void scaleXYP(float fromX, float toX, float fromY, float toY, float pivotX, float pivotY, int duration);
    public void setPosition(float x, float y, int width, int height);
}
