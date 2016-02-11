package ch.vogelfrederic.bastlicontest.Listeners;

import android.widget.CompoundButton;

import ch.vogelfrederic.bastlicontest.Async.Chase_colored;
import ch.vogelfrederic.bastlicontest.Async.RGBFlow;
import ch.vogelfrederic.bastlicontest.R;

/**
 * Created by vogelfr on 08.02.2016.
 * Project: BastliContest
 */
public class RGBFlowListener implements CompoundButton.OnCheckedChangeListener {
    private static RGBFlow rgbFlow;
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            rgbFlow = new RGBFlow();

            rgbFlow.start();
        } else {
            rgbFlow.interrupt();
        }
    }
}
