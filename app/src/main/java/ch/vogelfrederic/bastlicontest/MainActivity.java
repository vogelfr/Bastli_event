package ch.vogelfrederic.bastlicontest;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    protected static final String IP = "10.6.66.10";
    protected static final int port = 1337;
    protected static InetAddress address;
    protected static InetSocketAddress socketAddress;
    public static DatagramSocket datagramSocket;
    public static final int NUM_STRIPS = 15;
    public static final int NUM_LEDS = 112;
    private ToggleButton toggle;
    private Button update;
    private Button selectColor;
    private int[] colors = {0,0,0};

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

        toggle = (ToggleButton) findViewById(R.id.toggleButton_toggle);
        update = (Button) findViewById(R.id.button_update);
        selectColor = (Button) findViewById(R.id.button_selectColor);

    }

    public void toggleLights(View v) {
        for (int i = 0; i < NUM_STRIPS; i++) {
            byte[] data;
            data = new byte[1+NUM_LEDS*3];
            data[0] = (byte) i;
            for (int j = 1; j < data.length; j++) {
                data[j] = toggle.isChecked()? (byte) 255 : 0;
            }

            Task task = new Task(datagramSocket, address, port);
            task.execute(data);
        }
    }

    public void updateColor(View v) {
        for (int i = 0; i < NUM_STRIPS; i++) {
            byte[] data;
            data = new byte[1+NUM_LEDS*3];
            data[0] = (byte) i;
            for (int j = 1; j < data.length; j++) {
                switch (j % 3) {
                    case 1:
                        data[j] = (byte) colors[0];
                        break;
                    case 2:
                        data[j] = (byte) colors[1];
                        break;
                    default:
                        data[j] = (byte) colors[2];
                        break;
                }
            }

            Task task = new Task(datagramSocket, address, port);
            task.execute(data);
        }
    }

    public void pickColor(View v) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, 0xff0000ff, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Log.d("COLOR", String.valueOf(color));
                colors[0] = Color.red(color);
                colors[1] = Color.green(color);
                colors[2] = Color.blue(color);
                selectColor.setBackgroundColor(color);
            }
        });

        colorPicker.show();
    }



}
