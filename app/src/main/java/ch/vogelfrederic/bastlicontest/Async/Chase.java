package ch.vogelfrederic.bastlicontest.Async;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;

import ch.vogelfrederic.bastlicontest.Util;

/**
 * Created by vogelfr on 07.02.2016.
 * Project: BastliContest
 */
public class Chase extends Thread {

    public boolean cancelRequested;
    private Deque<Integer> strip = new ArrayDeque<>(896);

    public Chase(int size) {
        cancelRequested = false;
        int base = Util.colorSelected;
        for (int i = 0; i < size; i++) {
            double redStep = (Color.red(base)/size);
            double greenStep = (Color.green(base)/size);
            double blueStep = (Color.blue(base)/size);
            base = Color.rgb((int) (Color.red(base) - redStep*i),(int) (Color.green(base) - greenStep*i), (int) (Color.blue(base) - blueStep*i));
            strip.add(base);
        }
        for (int i = size; i < 896; i++) {
            strip.add(0);
        }
    }

    public void run() {
        while (!cancelRequested && !interrupted() && !Util.off) {

            strip.add(strip.poll());

            Util.sendTop(strip.toArray(new Integer[896]));

            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                cancelRequested = true;
                break;
            }
        }
        Log.e("OUT LOOP", "Stopped");
    }
}