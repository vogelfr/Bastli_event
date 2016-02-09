package ch.vogelfrederic.bastlicontest;

import android.graphics.Color;
import android.util.Log;

import java.net.DatagramSocket;
import java.net.InetAddress;

import ch.vogelfrederic.bastlicontest.Async.SendData;

/**
 * Created by vogelfr on 08.02.2016.
 * Project: BastliContest
 */
public class Util {

    public static final int NUM_STRIPS = 15;
    public static final int NUM_LEDS = 112;

    public static int color = Color.BLACK;
    public static int colorSelected = Color.WHITE;

    public static boolean off = false;

    public static DatagramSocket datagramSocket;
    public static InetAddress address;
    protected static final String IP = "10.6.66.10";
    public static final int port = 1337;

    public static void init() {
        try {
            address = InetAddress.getByName(IP);
            datagramSocket = new DatagramSocket();
            Log.d("UDP", "Setup succeeded");
        } catch (Exception e) {
            Log.e("UDP", "Setup failed");
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

            SendData sendData = new SendData();
            sendData.execute(data);
        }
    }

    public static void sendData(byte[] tosend, int strip) {
        byte[] data;
        data = new byte[1+NUM_LEDS*3];
        data[0] = (byte) strip;

        int length = tosend.length > 336? 336 : tosend.length;
        System.arraycopy(tosend, 0, data, 1, length);

        for (int i = 0; i < (NUM_LEDS * 3) - length; i++) {
            data[length + 1 + i] = 0;
        }

        SendData sendData = new SendData();
        sendData.execute(data);
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
}
