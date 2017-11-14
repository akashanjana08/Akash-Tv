package be.belgacom.tv.bepandroid.localserver.urlhandler;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import be.belgacom.tv.bepandroid.IPTVHome;
import be.belgacom.tv.bepandroid.R;
import be.belgacom.tv.bepandroid.Test;
import be.belgacom.tv.bepandroid.mapjson.MapJson;
import be.belgacom.tv.bepandroid.model.PlayerAnimationSizeModel;
import be.belgacom.tv.bepandroid.model.PlayerStreamSizeModel;

/**
 * Created by Akash.Sharma on 4/21/2017.
 */

public class PlayerHandler extends BaseHandler {

    Context mContext;
    private static String instanceId = null;
    private static final String MEDIA_PLAYER = "/mediaplayer/";
    private static final String MEDIA_PLAYER_ZORDER_SET = "/mediaplayer/zorder/set";
    private static final String MEDIA_PLAYER_STREAMSIZE_SET = "/mediaplayer/stream_size/set";
    private static final String EVENTS = "/events/";
    private static final String MEDIA_PLAYER_ANIMATION_STREAMSIZE = "/mediaplayer/animation/start";
    private static String MEDIA_PLAYER_PLAY;
    private static String MEDIA_PLAYER_STOP;
    private static String MEDIA_PLAYER_PAUSE;
    private static String MEDIA_PLAYER_DELETE;
    private static String MEDIA_PLAYER_SPEED;
    String Tag = "PlayerHandler";
    ResponseMap responseMap;
    HttpEntity entity;
    android.widget.FrameLayout.LayoutParams params;
    int width = 0, height = 0;
    Activity activity;

    public PlayerHandler(Context mContext) {
        this.mContext = mContext;
        activity = (Activity) mContext;
        responseMap = ResponseMap.getInstance();
    }

    String urlvideoStream = null;

    @Override
    public void doPutRequestHandle(HttpRequest request, HttpResponse response) {
        super.doPutRequestHandle(request, response);

        String jsonData = null;
        try {

            String uri = request.getRequestLine().getUri();
            entity = ((BasicHttpEntityEnclosingRequest) request).getEntity();
            String body = EntityUtils.toString(entity);

            if (uri.equalsIgnoreCase(MEDIA_PLAYER_ZORDER_SET)) {

                Log.d(Tag, MEDIA_PLAYER_ZORDER_SET);
                jsonData = MapJson.getJsonForMap(responseMap.getMediaplayerZorderSet());


            } else if (uri.equalsIgnoreCase(MEDIA_PLAYER_STREAMSIZE_SET)) {                            // ************** Set Meadia Player Size ***********************************
                try {
                    Gson gsonObject = new Gson();
                    final PlayerStreamSizeModel playerStreamSizeModel = gsonObject.fromJson(body, PlayerStreamSizeModel.class);

                    if (playerStreamSizeModel.getHeight() == 0) {

                        Log.d(Tag, MEDIA_PLAYER_STREAMSIZE_SET);
                        height = width = ViewGroup.LayoutParams.MATCH_PARENT;
                        // height = width = 300;
                        params = new android.widget.FrameLayout.LayoutParams(width, height);
                        params.setMargins(0, 0, 0, 0);
                        overLayChildViews(IPTVHome.webView);
                        setSurfaceLayputParams();
                    } else {
                        // For Temparary use
                        setStreamsize(body);
                        Test.makeTest().showToast("setStreamsize", mContext);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (uri.equalsIgnoreCase(MEDIA_PLAYER_PLAY)) {                                     // ************** Play Meadia Player  **************************************
                Log.d(Tag, "Play Media Player");

                try {

                    JSONObject jsonObject = new JSONObject(body);
                    String videoStream = jsonObject.getString("play_info");
                    urlvideoStream = videoStream.substring(3);

                    // rtsp stream rtsp://10.67.190.129:554/302.TF1_HD.ts.VPP.ts
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            ((IPTVHome) activity).surfaceView.setVisibility(View.VISIBLE);
                            ((IPTVHome) activity).setStream(urlvideoStream);

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (uri.equalsIgnoreCase(MEDIA_PLAYER_STOP)) {                                      // ************** STOP Meadia Player  ****************************************************************
                Log.d(Tag, "Stop Media Player");

                try {
                    ((IPTVHome) activity).pausePlayer();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (uri.equalsIgnoreCase(MEDIA_PLAYER_PAUSE)) {                                      // ************** PAUSE Meadia Player  ***************************************************************
                Log.d(Tag, "Pause Media Player");

                try {

                    ((IPTVHome) activity).pausePlayer();
                    // playMediaSurface.pausePlayer();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (uri.equalsIgnoreCase(MEDIA_PLAYER_SPEED)) {                                      // ************** SPEED CONTROL Meadia Player  *******************************************************
                Log.d(Tag, "Pause Media Player");
                String seekSpeed = null;
                try {
                    body = body;
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        seekSpeed = jsonObject.getString("speed");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ((IPTVHome) activity).seek(new Double(seekSpeed).intValue());
                    ((IPTVHome) activity).playPlayer();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (uri.equalsIgnoreCase(MEDIA_PLAYER_ANIMATION_STREAMSIZE)) {                         // ************** Animation and Margin CONTROL Meadia Player  ***************************************
                Log.d(Tag, "Pause Media Player" + "PUT VALUE " + body);

                try {
                    setAnimationSize(body);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (jsonData != null) {
            setHttpEntityForResponse(jsonData, response);
        } else {
            setHttpEntityForResponse("", response);
        }
    }


    @Override
    public void doGetRequestHandle(HttpRequest request, HttpResponse response) {
        super.doGetRequestHandle(request, response);

        String uri = request.getRequestLine().getUri();
        String jsonData = null;

        if (uri.equalsIgnoreCase(EVENTS)) {
            Log.d(Tag, EVENTS);
            jsonData = MapJson.getJsonForMap(responseMap.getBoardStatusGet());
        } else if (uri.equalsIgnoreCase(MEDIA_PLAYER)) {
            Log.d(Tag, MEDIA_PLAYER);

            jsonData = MapJson.getJsonForMap(responseMap.getCurrentInstanceMediaplayer("12345"));
            jsonData = jsonData.replace("\\/", "/");
            activity.runOnUiThread(new Runnable() {
                public void run() {

                }
            });
        }

        if (jsonData != null) {
            setHttpEntityForResponse(jsonData, response);
        } else {
            setHttpEntityForResponse("", response);
        }
    }


    @Override
    public void doPostRequestHandle(HttpRequest request, HttpResponse response) {
        super.doOptionsRequestHandle(request, response);

        String jsonData = null;
        String uri = request.getRequestLine().getUri();
        if (uri.equalsIgnoreCase(MEDIA_PLAYER)) {
            Log.d(Tag, MEDIA_PLAYER);
            try {
                entity = ((HttpEntityEnclosingRequest) request).getEntity();
                String body = EntityUtils.toString(entity);
                instanceId = ((IPTVHome) activity).getPLayerInstance().hashCode() + "";
                // ************************  init MediaPlayer and Surface  *****************************
                jsonData = MapJson.getJsonForMap(responseMap.initMediaplayer(instanceId));

                // ************************  init PLAYER INSTANCE URL  *********************************
                initMediaPlayerInstance(instanceId);
                jsonData = jsonData.replace("\\/", "/");

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        if (jsonData != null) {
            setHttpEntityForResponsePlayer(jsonData, response);
        } else {
            setHttpEntityForResponsePlayer("", response);
        }
    }


    @Override
    public void doDeleteRequestHandle(HttpRequest request, HttpResponse response) {
        super.doDeleteRequestHandle(request, response);
        String uri = request.getRequestLine().getUri();
        if (uri.equalsIgnoreCase(MEDIA_PLAYER_DELETE)) {
            Log.d(Tag, "Delete Media Player");

            try {

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //****** Resize surfaceView on Calling of Stream Api ******
    void setStreamsize(String reqBody)
    {

        Gson gsonObject = new Gson();
        final PlayerStreamSizeModel playerStreamSizeModel = gsonObject.fromJson(reqBody, PlayerStreamSizeModel.class);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                if (playerStreamSizeModel.getWidth() == 0) {

                    height = width = ViewGroup.LayoutParams.MATCH_PARENT;
                    // height = width = 300;
                    params = new android.widget.FrameLayout.LayoutParams(width, height);
                    params.setMargins(0, 0, 0, 0);

                } else {
                    ((IPTVHome) activity).surfaceView.bringToFront();
                    height = playerStreamSizeModel.getHeight();
                    width = playerStreamSizeModel.getWidth();

                    DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
                    int screenHeight = displayMetrics.heightPixels;
                    int screenWidth = displayMetrics.widthPixels;


                    params = new android.widget.FrameLayout.LayoutParams(width, height);


                    if (playerStreamSizeModel.getX() == 0 && playerStreamSizeModel.getWidth() != 0) {
                        int leftmargin = (screenWidth - (width + playerStreamSizeModel.getY()));
                        params.setMargins(leftmargin, playerStreamSizeModel.getY(), 0, 0);   // for pipCordinates

                        ((IPTVHome) activity).surfaceView.startAnimation(AnimationUtils.loadAnimation(mContext,
                                R.anim.right_to_left_slide));

                    } else {

                        params.setMargins(playerStreamSizeModel.getX(), playerStreamSizeModel.getY(), 0, 0);
                        // ((IPTVHome)activity).surfaceView.startAnimation(AnimationUtils.loadAnimation(mContext,
                        //     R.anim.left_to_right_slide));
                    }

                }

                ((IPTVHome) activity).surfaceView.setLayoutParams(params);
            }
        });
    }


    //****** Resize surfaceView on Calling of animation Api ******
    void setAnimationSize(String reqBody) {

        Gson gsonObject = new Gson();
        final PlayerAnimationSizeModel playerAnimationSizeModel = gsonObject.fromJson(reqBody, PlayerAnimationSizeModel.class);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                if (playerAnimationSizeModel.getWidth() == 0) {
                    height = width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params = new android.widget.FrameLayout.LayoutParams(width, height);
                    params.setMargins(0, 0, 0, 0);
                    overLayChildViews(IPTVHome.webView);
                    setSurfaceLayputParams();

                } else {
                    ((IPTVHome) activity).surfaceView.bringToFront();
                    height = playerAnimationSizeModel.getHeight();
                    width = playerAnimationSizeModel.getWidth();

                    DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
                    int screenHeight = displayMetrics.heightPixels;
                    int screenWidth = displayMetrics.widthPixels;

                    params = new android.widget.FrameLayout.LayoutParams(width, height);


//                    if (playerStreamSizeModel.getX() == 0 && playerStreamSizeModel.getWidth() != 0) {
//                        int leftmargin = (screenWidth - (width + playerStreamSizeModel.getY()));
//                        params.setMargins(leftmargin, playerStreamSizeModel.getY(), 0, 0);   // for pipCordinates
//
//                        playMediaSurface.startAnimation(AnimationUtils.loadAnimation(mContext,
//                                R.anim.right_to_left_slide));
//
//                    } else {
//
//                    }

                    params.setMargins(playerAnimationSizeModel.getDst_pos_x(), playerAnimationSizeModel.getDst_pos_y(), 0, 0);
                    ((IPTVHome) activity).surfaceView.startAnimation(AnimationUtils.loadAnimation(mContext,
                            R.anim.left_to_right_slide));

                }

                setSurfaceLayputParams();

            }
        });
    }


    private void initMediaPlayerInstance(String instanceId) {
        MEDIA_PLAYER_PLAY    = "/mediaplayer/" + instanceId + "/play";
        MEDIA_PLAYER_STOP    = "/mediaplayer/" + instanceId + "/stop";
        MEDIA_PLAYER_PAUSE   = "/mediaplayer/" + instanceId + "/pause";
        MEDIA_PLAYER_DELETE  = "/mediaplayer/" + instanceId + "/";
        MEDIA_PLAYER_SPEED   = "/mediaplayer/" + instanceId + "/speed/";
    }


    void overLayChildViews(final View view) {

        activity.runOnUiThread(new Runnable() {
            public void run() {
                view.bringToFront();
                view.setBackgroundColor(Color.TRANSPARENT);

            }
        });
    }


    void setSurfaceLayputParams() {
        activity.runOnUiThread(new Runnable() {
            public void run() {

                ((IPTVHome) activity).surfaceView.setVisibility(View.VISIBLE);
                ((IPTVHome) activity).surfaceView.setLayoutParams(params);

            }
        });

    }

}