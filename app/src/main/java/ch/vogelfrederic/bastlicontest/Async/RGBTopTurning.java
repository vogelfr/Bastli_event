package ch.vogelfrederic.bastlicontest.Async;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import ch.vogelfrederic.bastlicontest.Util;

/**
 * Created by vogelfr on 07.02.2016.
 * Project: BastliContest
 */
public class RGBTopTurning extends Thread {

    public static boolean cancelRequested;
    private ArrayList<Integer> strip = new ArrayList<>(896);
    private int[] colors = new int[896];
    private int speed;

    public RGBTopTurning(int speed) {
        cancelRequested = false;
        this.speed = speed < 1? 1 : speed;
        colors = Util.gradient();
        for (int i = 0; i < 896; i++) {
            strip.add(colors[i]);
        }
    }

    public void run() {
        while (!cancelRequested  && !Util.off) {
            for (int i = 0; i < speed; i++) {
                strip.add(strip.remove(0));
            }

            Util.sendTop(strip);

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