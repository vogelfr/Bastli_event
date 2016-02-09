package ch.vogelfrederic.bastlicontest.Async;

import android.graphics.Color;
import android.util.Log;

import ch.vogelfrederic.bastlicontest.Util;

/**
 * Created by vogelfr on 07.02.2016.
 * Project: BastliContest
 */
public class Chase_colored extends Thread {

    public static boolean cancelRequested;
    private int[] strip = new int[896];
    private int[] colors = new int[896];
    private int size;

    public Chase_colored(int size) {
        cancelRequested = false;
        this.size = size;
        colors = Util.gradient();
    }

    public void run() {
        int counter = 0;

        while (!cancelRequested  && !Util.off) {

            int base = colors[counter];
            double redStep = (Color.red(base)/size);
            double greenStep = (Color.green(base)/size);
            double blueStep = (Color.blue(base)/size);
            for (int i = 0; i < size; i++) {
                int current = Color.rgb((int)(Color.red(base) - redStep*i), (int)(Color.green(base) - greenStep*i), (int)(Color.blue(base) - blueStep*i));
                strip[(i+counter)%896] = current;
            }
            for (int i = size; i < 896; i++) {
                strip[(i+counter)%896] = 0;
            }

            Util.sendTop(strip);

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
            counter = (counter - 3) % 896;
            counter = (counter + 896) % 896;
        }
        Log.e("OUT LOOP", "Stopped");
    }
}