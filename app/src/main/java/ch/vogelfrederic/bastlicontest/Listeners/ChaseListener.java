package ch.vogelfrederic.bastlicontest.Listeners;

import android.widget.CompoundButton;

import ch.vogelfrederic.bastlicontest.Async.Chase;

/**
 * Created by vogelfr on 08.02.2016.
 * Project: BastliContest
 */
public class ChaseListener implements CompoundButton.OnCheckedChangeListener {
    private static Chase chase;
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            chase = new Chase(50);

            chase.start();
        } else {
            chase.interrupt();
        }
    }
}
