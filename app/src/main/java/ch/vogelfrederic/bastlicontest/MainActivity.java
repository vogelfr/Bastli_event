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
    private Button selectColor;
    public static int color = Color.BLACK;
    public static int colorSelected = Color.WHITE;

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
        selectColor = (Button) findViewById(R.id.button_selectColor);

    }

    public void toggleLights(View v) {
        if (!toggle.isChecked()) {
            for (int i = 0; i < 3; i++) {
                color = Color.BLACK;
            }
        } else {
            color = colorSelected;
        }
        updateColor();
    }

    public void updateColor() {
        for (int i = 0; i < NUM_STRIPS; i++) {
            byte[] data;
            data = new byte[1+NUM_LEDS*3];
            data[0] = (byte) i;
            for (int j = 1; j < data.length; j++) {
                switch (j % 3) {
                    case 1:
                        data[j] = (byte) Color.red(color);
                        break;
                    case 2:
                        data[j] = (byte) Color.green(color);
                        break;
                    default:
                        data[j] = (byte) Color.blue(color);
                        break;
                }
            }

            Task task = new Task(datagramSocket, address, port);
            task.execute(data);
        }
    }

    public void pickColor(View v) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, color+0xFF000000, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Log.d("COLOR", String.valueOf(color));
                MainActivity.color = color;
                MainActivity.colorSelected = color;
                selectColor.setBackgroundColor(color);
                toggle.setChecked(true);
                updateColor();
            }
        });

        colorPicker.show();
    }

    //TODO: Random (each LED random, new color with x Hz)

    //TODO: Random (each strip one random color, new color with x Hz)



}
