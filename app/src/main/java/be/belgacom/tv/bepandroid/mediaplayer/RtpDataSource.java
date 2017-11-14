package be.belgacom.tv.bepandroid.mediaplayer;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.util.Log;
import com.google.android.exoplayer.upstream.DataSpec;
import com.google.android.exoplayer.upstream.TransferListener;
import com.google.android.exoplayer.upstream.UriDataSource;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

public final class RtpDataSource implements UriDataSource {
    private static final int RTP_HEADER_LEN = 12;
    private static final int RTP = 0;
    private static final int UDP = 1;
    int mPaylen;
    byte[] mPayload;
    public long totalBytesRead;
    final String TAG;
    public static final int DEFAULT_MAX_PACKET_SIZE = 2000;
    private final TransferListener listener;
    private final DatagramPacket packet;
    private DataSpec dataSpec;
    private DatagramSocket socket;
    private MulticastSocket multicastSocket;
    private InetAddress address;
    private InetSocketAddress socketAddress;
    private boolean opened;
    private byte[] packetBuffer;
    private int packetRemaining;
    private int transportProtocol;

    public RtpDataSource(TransferListener listener) {
        this(listener, 2000);
    }

    public RtpDataSource(TransferListener listener, int maxPacketSize) {
        this.mPaylen = 0;
        this.mPayload = new byte[1400];
        this.totalBytesRead = 0L;
        this.TAG = "RtpDataSource";
        this.packetRemaining = 0;
        this.transportProtocol = 0;
        this.listener = listener;
        this.packetBuffer = new byte[maxPacketSize];
        this.packet = new DatagramPacket(this.packetBuffer, 0, maxPacketSize);
    }

    public long open(DataSpec dataSpec) throws RtpDataSourceException {
        this.dataSpec = dataSpec;
        Log.e("RtpDataSource", "TEST PRINT ExoPlayer Version 1.4");
        String uri = dataSpec.uri.toString();
        if(uri.contains("udp")) {
            Log.e("RtpDataSource", "Protocol Type UDP");
            this.transportProtocol = 1;
        }

        String host = uri.substring(uri.indexOf("://") + 3, uri.lastIndexOf(58));
        int port = Integer.parseInt(uri.substring(uri.lastIndexOf(58) + 1));
        //int port = 554;

        Log.e("RtpDataSource", "host" + host);
        Log.e("RtpDataSource", "port" + port);

        try {
            this.address = InetAddress.getByName(host);
            this.socketAddress = new InetSocketAddress(this.address, port);

            if(this.address.isMulticastAddress()) {
                this.multicastSocket = new MulticastSocket(port);
                this.multicastSocket.joinGroup(this.address);
                this.socket = this.multicastSocket;
            } else {
                this.socket = new DatagramSocket(this.socketAddress);
            }
        } catch (IOException var6) {
         //   throw new com.google.android.exoplayer.upstream.RtpDataSource.RtpDataSourceException(var6);
        }

        this.opened = true;
        if(this.listener != null) {
            this.listener.onTransferStart();
        }
        return -1L;
    }

    public int ParsertpPacket(byte[] rtp_packet, int packet_len) throws IOException {
        if(rtp_packet != null && packet_len > 12) {
            byte mVersion = (byte)(rtp_packet[0] >> 6 & 3);
            byte mPayloadType = (byte)(rtp_packet[0] & 2);
            byte mExtension = (byte)(rtp_packet[0] & 16);
            byte mCsrc = (byte)(rtp_packet[0] & 15);
            boolean mMarkerSet = (rtp_packet[1] & 128) == 128;
            int mTimeStamp = (rtp_packet[4] & 255) << 24 | (rtp_packet[5] & 255) << 16 | (rtp_packet[6] & 255) << 8 | rtp_packet[7] & 255;
            boolean mPrevTimeStamp = false;
            if(!mPrevTimeStamp) {
                ;
            }

            if(mMarkerSet) {
                ;
            }

            if(mVersion == 2 && mPayloadType == 0 && mExtension == 0 && mCsrc == 0) {
                this.mPaylen = packet_len - 12;

                for(int i = 12; i < packet_len; ++i) {
                    this.mPayload[i - 12] = rtp_packet[i];
                }

                return 0;
            } else {
                return -1;
            }
        } else {
            Log.e("RtpDataSource", "NULL RTP packet.");
            return -1;
        }
    }

    public int parserudpPacket(byte[] udp_packet, int length) {
        int sourcePort = udp_packet[0] & 255 | (udp_packet[1] & 255) << 8;
        int Datalength = udp_packet[4] & 255 | (udp_packet[5] & 255) << 8;
        return 0;
    }

    public int read(byte[] buffer, int offset, int readLength) throws IOException {
        if(!this.opened) {
            Log.e("RtpDataSource", "opened " + this.opened);
            return -1;
        } else {
            if(this.packetRemaining == 0) {
                try {
                    this.socket.receive(this.packet);
                } catch (IOException var6) {
                    Log.e("RtpDataSource", "RSI:Unhandled Exception");
                    return -1;
                } catch (Exception var7) {
                    Log.e("RtpDataSource", "RSI:Unhandled Exception");
                    return -1;
                }

                this.mPaylen = this.packet.getLength();
                this.mPayload = this.packetBuffer;
                if(this.transportProtocol == 0) {
                    this.ParsertpPacket(this.packetBuffer, this.packet.getLength());
                } else {
                    this.parserudpPacket(this.packetBuffer, this.packet.getLength());
                }

                this.packetRemaining = this.mPaylen;
                if(this.listener != null) {
                    this.listener.onBytesTransferred(this.packetRemaining);
                }
            }

            int packetOffset = this.mPaylen - this.packetRemaining;
            int bytesToRead = Math.min(this.packetRemaining, readLength);
            System.arraycopy(this.mPayload, packetOffset, buffer, offset, bytesToRead);
            this.packetRemaining -= bytesToRead;
            return bytesToRead;
        }
    }

    public void close() {
        Log.e("RtpDataSource", "RSI: Multicast socket close");
        if(this.multicastSocket != null || this.address != null) {
            try {
                this.multicastSocket.leaveGroup(this.address);
            } catch (IOException var2) {
                ;
            }

            this.multicastSocket = null;
        }

        if(this.socket != null) {
            this.socket.close();
            this.socket = null;
        }

        this.address = null;
        this.socketAddress = null;
        this.packetRemaining = 0;
        if(this.opened) {
            this.opened = false;
            if(this.listener != null) {
                this.listener.onTransferEnd();
            }
        }

    }

    public String getUri() {
        return this.dataSpec == null?null:this.dataSpec.uri.toString();
    }

    public static final class RtpDataSourceException extends IOException {
        public RtpDataSourceException(String message) {
            super(message);
        }

        public RtpDataSourceException(IOException cause) {
            super(cause);
        }
    }
}
