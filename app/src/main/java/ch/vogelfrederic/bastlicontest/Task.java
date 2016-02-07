package ch.vogelfrederic.bastlicontest;

import android.os.AsyncTask;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by vogelfr on 07.02.2016.
 */
public class Task extends AsyncTask<byte[], Void, Void>{
    private DatagramSocket datagramSocket;
    private InetAddress address;
    private int port;

    public Task (DatagramSocket datagramSocket, InetAddress address, int port) {
        this.datagramSocket = datagramSocket;
        this.address = address;
        this.port = port;
    }

    @Override
    protected Void doInBackground(byte[]... params) {
        byte[] data = params[0];
        for (int t = 0; t < 1; t++) {
            try {
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                datagramSocket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
