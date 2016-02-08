package ch.vogelfrederic.bastlicontest;

import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by vogelfr on 07.02.2016.
 */
public class Chase extends Thread {

    public boolean cancelRequested;
    private Deque<Integer> strip = new ArrayDeque<>(896);

    public Chase(int size) {
        cancelRequested = false;
        int base = MainActivity.colorSelected;
        for (int i = 0; i < size; i++) {
            double redStep = (Color.red(base)/size);
            double greenStep = (Color.green(base)/size);
            double blueStep = (Color.blue(base)/size);
            base = Color.rgb((int) (Color.red(base) - redStep*i),(int) (Color.green(base) - greenStep*i), (int) (Color.blue(base) - blueStep*i));
            strip.add(Integer.valueOf(base));
        }
        for (int i = size; i < 896; i++) {
            strip.add(0);
        }
    }

    public void run() {
        while (!cancelRequested) {

            strip.add(strip.poll());

            MainActivity.sendTop(strip.toArray(new Integer[896]));

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
        }
        Log.e("OUT LOOP", "Stopped");
    }
}
;