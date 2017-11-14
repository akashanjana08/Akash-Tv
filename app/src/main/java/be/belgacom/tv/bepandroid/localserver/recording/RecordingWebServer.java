package be.belgacom.tv.bepandroid.localserver.recording;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpException;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.DefaultHttpServerConnection;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpRequestHandlerRegistry;
import org.apache.http.protocol.HttpService;
import org.apache.http.protocol.ResponseConnControl;
import org.apache.http.protocol.ResponseDate;
import org.apache.http.protocol.ResponseServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import be.belgacom.tv.bepandroid.localserver.urlhandler.DeviceInfoHandler;
import be.belgacom.tv.bepandroid.localserver.urlhandler.NetworkHandler;
import be.belgacom.tv.bepandroid.localserver.urlhandler.PlayerHandler;
import be.belgacom.tv.bepandroid.localserver.urlhandler.STBAssignHandler;
import be.belgacom.tv.bepandroid.localserver.urlhandler.StandbyHandler;
import be.belgacom.tv.bepandroid.localserver.urlhandler.SystemInfoHandler;


/**
 * @author satendar.kumar
 */
public class RecordingWebServer implements Runnable {

    private String tag = "WebServer";
    public static boolean RUNNING = false;
    public static int serverPort;


    private static final String SYSTEM_INFO   = "/sysdetails/*";
    private static final String DEVICE_INFO   = "/devinfo/*";
    private static final String STAND_BY      = "/board_status/*";
    private static final String NETWORK       = "/netinfo/*";
    private static final String STBAssign     = "/stb_subscription/*";
    private static final String PLAYER        = "/mediaplayer/*";
    private static final String PLAYER_EVENT  = "/events/";


    private static RecordingWebServer instance;
    private BasicHttpProcessor httpproc = null;
    private BasicHttpContext httpContext = null;
    private HttpService httpService = null;
    private HttpRequestHandlerRegistry registry = null;


    public RecordingWebServer(Context mContext) {
        httpproc = new BasicHttpProcessor();
        httpContext = new BasicHttpContext();

        httpproc.addInterceptor(new ResponseDate());
        httpproc.addInterceptor(new ResponseServer());
        httpproc.addInterceptor(new ResponseConnControl());

        httpService = new HttpService(httpproc, new DefaultConnectionReuseStrategy(), new DefaultHttpResponseFactory());
        registry = new HttpRequestHandlerRegistry();


        registry.register(SYSTEM_INFO, new SystemInfoHandler(mContext));
        registry.register(DEVICE_INFO, new DeviceInfoHandler(mContext));
        registry.register(STAND_BY, new StandbyHandler(mContext));
        registry.register(NETWORK, new NetworkHandler(mContext));
        registry.register(STBAssign, new STBAssignHandler(mContext));
        registry.register(PLAYER, new PlayerHandler(mContext));
        registry.register(PLAYER_EVENT, new PlayerHandler(mContext));


        httpService.setHandlerResolver(registry);
    }

    private ServerSocket serverSocket;

    public void run() {
        try {
            RUNNING = true;
            serverSocket = new ServerSocket(9002);
            serverPort = serverSocket.getLocalPort();


            Log.e(tag, "Server port :" + serverPort);

            Log.e(tag, "IP :" + serverSocket.getInetAddress().getHostAddress());


            serverSocket.setReuseAddress(true);
            DefaultHttpServerConnection serverConnection = null;
            while (RUNNING) {
                try {
                    Log.e(tag, "Recording Socket Start");
                    final Socket socket = serverSocket.accept();
                    serverConnection = new DefaultHttpServerConnection();
                    serverConnection.bind(socket, new BasicHttpParams());
                    httpService.handleRequest(serverConnection, httpContext);
                    serverConnection.shutdown();
                } catch (IOException e) {
                    try {
                        serverConnection.shutdown();
                    } catch (Exception e2) {
                        Log.e(tag, "e2 error at shutdown" + e2);
                    }

                } catch (HttpException e) {
                    try {
                        serverConnection.shutdown();
                    } catch (Exception e2) {
                        Log.e(tag, "e2 error" + e2);
                    }

                }

            }
            serverSocket.close();
            Log.e(tag, "Socket Close");
        } catch (SocketException e) {
            Log.e(tag, "Socket error" + e);
        } catch (IOException e) {
            Log.e(tag, "IO error" + e);
        }

        RUNNING = false;
    }

	/*public synchronized void startServer() {
        RUNNING = true;
		runServer();
	}*/

    public synchronized void stopServer() {
        Log.d(tag, "stopServer +");
        RUNNING = false;
        if (serverSocket != null) {
            try {
                serverSocket.close();
                /*if(googleResponse!=null){
                    googleResponse.disconnect();
				}*/
            } catch (IOException e) {
                Log.e(tag, e.getMessage());
            }
        }
        Log.d(tag, "stopServer -");
    }

    public static RecordingWebServer getInstance(Context mContext) {
        if (instance == null) {
            instance = new RecordingWebServer(mContext);
        }

        return instance;
    }
}
