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
import java.util.Deque;

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
    private ToggleButton toggle_pulsate;
    public static int color = Color.BLACK;
    public static int colorSelected = Color.WHITE;
    private ToggleButton randomButton;

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
        toggle_pulsate = (ToggleButton) findViewById(R.id.toggleButton_pulsate);
        randomButton = (ToggleButton) findViewById(R.id.toggleButton_randomButton);

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

    public static void updateColor() {
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

    public static void sendData(byte[] tosend, int strip) {
        byte[] data;
        data = new byte[1+NUM_LEDS*3];
        data[0] = (byte) strip;

        int length = tosend.length > 336? 336 : tosend.length;
        for (int i = 0; i < length; i++) {
            data[i+1] = tosend[i];
        }

        for (int i = 0; i < (NUM_LEDS * 3) - length; i++) {
            data[length + 1 + i] = 0;
        }

        Task task = new Task(datagramSocket, address, port);
        task.execute(data);
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

    public static void sendTop(int[] strip) {
        int[] indices = {1,4,9,12,5,6,7,8};
        boolean[] directions = {false, false, false, false, true, true, true, true};

        for (int s = 0; s < indices.length; s++) {
            byte[] data = new byte[112*3];
            for (int i = 0; i < 112; i++) {
                if (directions[s]) {
                    data[i*3] = (byte) Color.red(strip[s*112 + i]);
                    data[i*3 + 1] = (byte) Color.green(strip[s*112 + i]);
                    data[i*3 + 2] = (byte) Color.blue(strip[s*112 + i]);
                } else {
                    data[336 - (i+1)*3] = (byte) Color.red(strip[s*112 + i]);
                    data[336 - ((i+1)*3 - 1)] = (byte) Color.green(strip[s*112 + i]);
                    data[336 - ((i+1)*3 - 2)] = (byte) Color.blue(strip[s*112 + i]);
                }
            }
            sendData(data, indices[s]);
        }
    }

    public static void sendTop(Integer[] strip) {
        int[] indices = {1,4,9,12,5,6,7,8};
        boolean[] directions = {false, false, false, false, true, true, true, true};

        for (int s = 0; s < indices.length; s++) {
            byte[] data = new byte[112*3];
            for (int i = 0; i < 112; i++) {
                if (directions[s]) {
                    data[i*3] = (byte) Color.red(strip[s*112 + i]);
                    data[i*3 + 1] = (byte) Color.green(strip[s*112 + i]);
                    data[i*3 + 2] = (byte) Color.blue(strip[s*112 + i]);
                } else {
                    data[336 - (i+1)*3] = (byte) Color.red(strip[s*112 + i]);
                    data[336 - ((i+1)*3 - 1)] = (byte) Color.green(strip[s*112 + i]);
                    data[336 - ((i+1)*3 - 2)] = (byte) Color.blue(strip[s*112 + i]);
                }
            }
            sendData(data, indices[s]);
        }
    }

    //TODO: Random (each LED random, new color with x Hz)

    //TODO: Random (each strip one random color, new color with x Hz)

    public void pulsate(View v) {
        Log.e("PULSATE", "Toggle pressed");
        double redStep = ((double) Color.red(color)) / 120.0;
        double greenStep = ((double) Color.green(color)) / 120.0;
        double blueStep = ((double) Color.blue(color)) / 120.0;

        Pulsate t = new Pulsate(redStep, greenStep, blueStep);

        if (toggle_pulsate.isChecked()) {
            t.start();
        } else {
            Log.e("THREAD", "Stopped");
            Pulsate.cancelRequested = true;
            t.interrupt();
            color = colorSelected;
            updateColor();


        }
    }


    public void chase(View v) {
        Chase_colored chase = new Chase_colored(100);

        if (toggle_pulsate.isChecked()) {
            chase.start();
        } else {
            Log.e("THREAD", "Stopped");
            Chase_colored.cancelRequested = true;
            chase.interrupt();
            color = colorSelected;
            updateColor();
        }
    }

    public static int[] gradient() {
        int[] colors = new int[896];
        int red = 255;
        int green = 0;
        int blue = 0;
        double step = 255/149;
        double current = 0.0;
        for (int i = 0; i < 894; i++) {
            if (i < 149) {
                current += step;
                green = (int) current;
            } else if (i < 298) {
                current -= step;
                red = (int) current;
            } else if (i < 447) {
                current += step;
                blue = (int) current;
            } else if (i < 596) {
                current -= step;
                green = (int) current;
            } else if (i < 745) {
                current += step;
                red = (int) current;
            } else if (i < 894) {
                current -= step;
                blue = (int) current;
            }
            colors[i] = Color.rgb(red, green, blue);
        }

        colors[894] = Color.rgb(red, green, blue);
        colors[895] = Color.rgb(red, green, blue);

        return colors;
    }

    public void gradient(View v) {
        int[] colors = gradient();

        sendTop(colors);
    }



}
