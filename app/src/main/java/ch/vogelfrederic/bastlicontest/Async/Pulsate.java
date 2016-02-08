package ch.vogelfrederic.bastlicontest.Async;

import android.graphics.Color;
import android.util.Log;

import ch.vogelfrederic.bastlicontest.Util;

/**
 * Created by vogelfr on 07.02.2016.
 * Project: BastliContest
 */
public class Pulsate extends Thread {

    public static boolean cancelRequested;
    private double redStep, greenStep, blueStep;

    public Pulsate(double redStep, double greenStep, double blueStep) {
        cancelRequested = false;
        this.redStep = redStep;
        this.greenStep = greenStep;
        this.blueStep = blueStep;
    }

    public void run() {
        while (!cancelRequested && !interrupted() && !Util.off) {

            Log.e("Pulsate", "Color: " + Util.color);

            Log.e("Pulsate", "Darker");
            int startColor = Util.color;
            for (int i = 0; i < 60; i++) {
                int red = Color.red(startColor) - (int) (redStep * i);
                int green = Color.green(startColor) - (int) (greenStep * i);
                int blue = Color.blue(startColor) - (int) (blueStep * i);
                Util.color = Color.rgb(red, green, blue);
                Util.updateColor();
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    cancelRequested = true;
                    break;
                }
            }

            Log.e("Pulsate", "Color: " + Util.color);

            Log.e("Pulsate", "Lighter");
            startColor = Util.color;
            for (int i = 0; i < 60; i++) {
                int red = Color.red(startColor) + (int) (redStep * i);
                int green = Color.green(startColor) + (int) (greenStep * i);
                int blue = Color.blue(startColor) + (int) (blueStep * i);
                Util.color = Color.rgb(red, green, blue);
                Util.updateColor();
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    cancelRequested = true;
                    break;
                }
            }
        }
        Log.e("OUT LOOP", "Stopped");
    }
}
