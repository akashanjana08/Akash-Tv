package be.belgacom.tv.bepandroid.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import be.belgacom.tv.bepandroid.R;

/**
 * Created by Narendra.Kumar on 2/24/2016.
 */
public class GIFView extends ImageView {
    private InputStream gifInputStream;
    private Movie gifMovie;
    private int movieWidth, movieHeight;
    private long movieDuration;
    private long mMovieStart;
    private static final boolean DECODE_STREAM = true;

    public GIFView(Context context) {
        super(context);
        init(context, null);
    }

    public GIFView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GIFView(Context context, AttributeSet attrs,
                   int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    private void init(Context context){
        setFocusable(true);
        gifInputStream = context.getResources()
                .openRawResource(+R.drawable.activityscreenstart);

        gifMovie = Movie.decodeStream(gifInputStream);
        movieWidth = gifMovie.width();
        movieHeight = gifMovie.height();
        movieDuration = gifMovie.duration();
    }

    private void init(final Context context, AttributeSet attrs){
        setFocusable(true);

        if(attrs == null){
            gifMovie = null;
            movieWidth = 0;
            movieHeight = 0;
            movieDuration = 0;
        }else{

            int gifResource = attrs.getAttributeResourceValue(
                    "http://schemas.android.com/apk/res/android",
                    "src",
                    0);

            if(gifResource == 0){
                gifMovie = null;
                movieWidth = 0;
                movieHeight = 0;
                movieDuration = 0;
            }else{
                gifInputStream = context.getResources().openRawResource(gifResource);

                if(DECODE_STREAM){
                    gifMovie = Movie.decodeStream(gifInputStream);
                }else{
                    byte[] array = streamToBytes(gifInputStream);
                    gifMovie = Movie.decodeByteArray(array, 0, array.length);
                }

                movieWidth = gifMovie.width();
                movieHeight = gifMovie.height();
                movieDuration = gifMovie.duration();
            }
        }
    }

    private static byte[] streamToBytes(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = is.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } catch (java.io.IOException e) {
        }
        return os.toByteArray();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,
                             int heightMeasureSpec) {
        setMeasuredDimension(movieWidth, movieHeight);
    }

    public int getMovieWidth(){
        return movieWidth;
    }

    public int getMovieHeight(){
        return movieHeight;
    }

    public long getMovieDuration(){
        return movieDuration;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        long now = android.os.SystemClock.uptimeMillis();
        if (mMovieStart == 0) {
            mMovieStart = now;
        }

        if (gifMovie != null) {

            int dur = gifMovie.duration();
            if (dur == 0) {
                dur = 1000;
            }

            int relTime = (int)((now - mMovieStart) % dur);

            gifMovie.setTime(relTime);

            gifMovie.draw(canvas, 0, 0);
            invalidate();

        }

    }
}