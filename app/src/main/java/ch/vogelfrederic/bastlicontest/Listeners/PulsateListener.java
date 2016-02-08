package ch.vogelfrederic.bastlicontest.Listeners;

import android.widget.CompoundButton;

import ch.vogelfrederic.bastlicontest.Async.Chase_colored;
import ch.vogelfrederic.bastlicontest.Async.Pulsate;

/**
 * Created by vogelfr on 08.02.2016.
 * Project: BastliContest
 */
public class PulsateListener implements CompoundButton.OnCheckedChangeListener {
    private static Pulsate pulsate;
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            pulsate = new Pulsate(2.5, 2.5, 2.5);

            pulsate.start();
        } else {
            pulsate.interrupt();
        }
    }
}
