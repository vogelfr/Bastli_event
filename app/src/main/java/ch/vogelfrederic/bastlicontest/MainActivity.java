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

    protected static final String IP = "10.6.66.10";
    protected static final int port = 1337;
    protected static InetAddress address;
    protected static InetSocketAddress socketAddress;
    public static DatagramSocket datagramSocket;
    public static final int NUM_STRIPS = 15;
    public static final int NUM_LEDS = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            address = InetAddress.getByName(IP);
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
        for (int i = 0; i < NUM_STRIPS; i++) {
            byte[] data;
            data = new byte[1+NUM_LEDS*3];
            data[0] = (byte) i;
            for (int j = 1; j < data.length; j++) {
                data[j] = 0;
            }

            AsyncTask<byte[], Void, Void> async = new AsyncTask<byte[], Void, Void>() {
                @Override
                protected Void doInBackground(byte[]... params) {
                    byte[] data = params[0];
                    for (int t = 0; t < 1; t++) {
                        try {
                            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                            Log.d("UDP", "Sending data (length: " + data.length + ")");
                            datagramSocket.send(packet);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }
            };

            async.execute(data);

        }

    }

}
