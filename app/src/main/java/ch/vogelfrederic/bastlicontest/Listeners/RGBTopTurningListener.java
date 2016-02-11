package ch.vogelfrederic.bastlicontest.Listeners;

import android.widget.CompoundButton;

import ch.vogelfrederic.bastlicontest.Async.RGBTopTurning;

/**
 * Created by vogelfr on 08.02.2016.
 * Project: BastliContest
 */
public class RGBTopTurningListener implements CompoundButton.OnCheckedChangeListener {
    private static RGBTopTurning rgbTopTurning;
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            rgbTopTurning = new RGBTopTurning(1);

            rgbTopTurning.start();
        } else {
            rgbTopTurning.interrupt();
        }
    }
}
