package ch.vogelfrederic.bastlicontest.Listeners;

import android.widget.CompoundButton;

import ch.vogelfrederic.bastlicontest.Async.Chase_colored;

/**
 * Created by vogelfr on 08.02.2016.
 * Project: BastliContest
 */
public class ColoredChaseListener implements CompoundButton.OnCheckedChangeListener {
    private static Chase_colored chase;
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            chase = new Chase_colored(50);

            chase.start();
        } else {
            chase.interrupt();
        }
    }
}
