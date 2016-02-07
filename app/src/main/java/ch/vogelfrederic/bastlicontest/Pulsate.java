package ch.vogelfrederic.bastlicontest;

import android.graphics.Color;
import android.util.Log;

/**
 * Created by vogelfr on 07.02.2016.
 */
public class Pulsate extends Thread {
    private int change;

    public static boolean cancelRequested;
    private double redStep, greenStep, blueStep;

    public Pulsate(double redStep, double greenStep, double blueStep) {
        cancelRequested = false;
        this.redStep = redStep;
        this.greenStep = greenStep;
        this.blueStep = blueStep;
    }

    public Pulsate (int change) {
        this.change = change;
    }

    public void run() {
        while (!cancelRequested) {

            Log.e("Pulsate", "Color: " + MainActivity.color);

            Log.e("Pulsate", "Darker");
            int startColor = MainActivity.color;
            for (int i = 0; i < 60; i++) {
                int red = Color.red(startColor) - (int) (redStep * i);
                int green = Color.green(startColor) - (int) (greenStep * i);
                int blue = Color.blue(startColor) - (int) (blueStep * i);
                MainActivity.color = Color.rgb(red, green, blue);
                MainActivity.updateColor();
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    cancelRequested = true;
                    break;
                }
            }

            Log.e("Pulsate", "Color: " + MainActivity.color);

            Log.e("Pulsate", "Lighter");
            startColor = MainActivity.color;
            for (int i = 0; i < 60; i++) {
                int red = Color.red(startColor) + (int) (redStep * i);
                int green = Color.green(startColor) + (int) (greenStep * i);
                int blue = Color.blue(startColor) + (int) (blueStep * i);
                MainActivity.color = Color.rgb(red, green, blue);
                MainActivity.updateColor();
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    cancelRequested = true;
                    break;
                }
            }

            if(cancelRequested) {
                Log.e("LOOP", "Stopped");
                break;
            }
        }
        Log.e("OUT LOOP", "Stopped");
    }
}
