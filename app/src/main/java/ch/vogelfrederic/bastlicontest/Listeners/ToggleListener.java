package ch.vogelfrederic.bastlicontest.Listeners;

import android.graphics.Color;
import android.widget.CompoundButton;

import ch.vogelfrederic.bastlicontest.Util;

/**
 * Created by vogelfr on 08.02.2016.
 * Project: BastliContest
 */
public class ToggleListener implements CompoundButton.OnCheckedChangeListener {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Util.off = false;
            Util.color = Util.colorSelected;
            Util.updateColor();
        } else {
            Util.off = true;
            Util.color = Color.BLACK;
            Util.updateColor();
        }
    }
}
