package ch.vogelfrederic.bastlicontest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    public static InetAddress address;
    public static InetSocketAddress socketAddress;
    public static DatagramPacket packet;
    public static DatagramSocket datagramSocket;
    public static final int NUM_STRIPS = 15;
    public static final int NUM_LEDS = 112;
    public byte[] data;
    public static AsyncTask<Void, Void, Void> async;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        byte[] IP={10,6,66,10};
        int port = 1337;

        try {
            address = InetAddress.getByAddress(IP);
            Log.d("address", address.toString());}
        catch (Exception e) {Log.d("1", "Hello");}
        try{
            socketAddress = new InetSocketAddress(address, port);}
        catch (Exception e) {Log.d("2", "Hello");}
        try{
            datagramSocket = new DatagramSocket();
            Log.d("UDP", "Socket exists");}
        catch (Exception e) {Log.d("3", "Hello");}

    }

    public void allOff(View v) {
        data = new byte[1+NUM_LEDS*3];
        for (int i = 0; i < NUM_STRIPS; i++) {
            data[0] = (byte) i;
            for (int j = 1; j < 3*NUM_LEDS; j++) {
                data[j] = 0;
            }

            async = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    for (int t = 0; t < 10000; t++) {
                        try {
                            packet = new DatagramPacket(data, data.length, address, 1337);
                            if (packet.equals(null)) {
                                Log.d("NOPE", "");
                            } else {
                                Log.d("UDP", "Sending data (length: " + data.length + ")");
                                datagramSocket.send(packet);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    return null;
                }
            };

            async.execute();

        }

    }

}
