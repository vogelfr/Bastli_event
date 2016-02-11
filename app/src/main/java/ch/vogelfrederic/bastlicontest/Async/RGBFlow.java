package ch.vogelfrederic.bastlicontest.Async;

import android.graphics.Color;
import android.util.Log;

import ch.vogelfrederic.bastlicontest.Util;

/**
 * Created by vogelfr on 07.02.2016.
 * Project: BastliContest
 */
public class RGBFlow extends Thread {

    public static boolean cancelRequested;
    private int[] colors = new int[896];

    public RGBFlow() {
        cancelRequested = false;
        colors = Util.gradient();
    }

    public void run() {
        int counter = 0;

        while (!cancelRequested  && !Util.off) {

            Util.color = colors[counter];
            Util.updateColor();

            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                cancelRequested = true;
                break;
            }

            if(cancelRequested) {
                Log.e("LOOP", "Stopped");
                break;
            }
            counter = (counter + 1) % 896;
        }
        Log.e("OUT LOOP", "Stopped");
    }
}