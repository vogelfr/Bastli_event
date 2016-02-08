package ch.vogelfrederic.bastlicontest.Async;

import android.os.AsyncTask;

import java.net.DatagramPacket;

import ch.vogelfrederic.bastlicontest.Util;

/**
 * Created by vogelfr on 07.02.2016.
 * Project: BastliContest
 */
public class SendData extends AsyncTask<byte[], Void, Void>{

    @Override
    protected Void doInBackground(byte[]... params) {
        byte[] data = params[0];
        for (int t = 0; t < 1; t++) {
            try {
                DatagramPacket packet = new DatagramPacket(data, data.length, Util.address, Util.port);
                Util.datagramSocket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
