package ch.vogelfrederic.bastlicontest.IO;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import ch.vogelfrederic.bastlicontest.Listeners.ChaseListener;
import ch.vogelfrederic.bastlicontest.Listeners.ColoredChaseListener;
import ch.vogelfrederic.bastlicontest.Listeners.PulsateListener;
import ch.vogelfrederic.bastlicontest.Listeners.RGBFlowListener;
import ch.vogelfrederic.bastlicontest.Listeners.RGBTopTurningListener;
import ch.vogelfrederic.bastlicontest.Listeners.ToggleListener;
import ch.vogelfrederic.bastlicontest.R;
import ch.vogelfrederic.bastlicontest.Util;
import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    private ToggleButton toggle_off;
    private Toolbar toolbar;
    private MenuItem action_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Switch switch_coloredChase;
        Switch switch_chase;
        Switch switch_pulsate;
        Switch switch_rgbFlow;
        Switch switch_rgbTopTurning;

        toggle_off = (ToggleButton) findViewById(R.id.toggle_off);
        toggle_off.setOnCheckedChangeListener(new ToggleListener());
        switch_coloredChase = (Switch) findViewById(R.id.switch_coloredChase);
        switch_coloredChase.setOnCheckedChangeListener(new ColoredChaseListener());
        switch_chase = (Switch) findViewById(R.id.switch_chase);
        switch_chase.setOnCheckedChangeListener(new ChaseListener());
        switch_pulsate = (Switch) findViewById(R.id.switch_pulsate);
        switch_pulsate.setOnCheckedChangeListener(new PulsateListener());
        switch_rgbFlow = (Switch) findViewById(R.id.switch_rgbFlow);
        switch_rgbFlow.setOnCheckedChangeListener(new RGBFlowListener());
        switch_rgbTopTurning = (Switch) findViewById(R.id.switch_rgbTopTurning);
        switch_rgbTopTurning.setOnCheckedChangeListener(new RGBTopTurningListener());

        Util.init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        action_color = (MenuItem) findViewById(R.id.action_color);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        action_color = item;

        switch (id) {
            case R.id.action_color:
                AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, Util.color + 0xFF000000, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        Log.d("COLOR", String.valueOf(color));
                        Util.color = color;
                        Util.colorSelected = color;
                        toolbar.setBackgroundColor(color);
                        if (Math.min(Color.red(color), Math.min(Color.green(color), Color.blue(color))) > 128) {
                            toolbar.setTitleTextColor(Color.BLACK);
                            action_color.setIcon(R.drawable.ic_format_paint_black_36dp);
                        } else {
                            toolbar.setTitleTextColor(Color.WHITE);
                            action_color.setIcon(R.drawable.ic_format_paint_white_36dp);
                        }

                        toggle_off.setChecked(true);
                        Util.updateColor();
                    }
                });

                colorPicker.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    //TODO: Random (each LED random, new color with x Hz)

    //TODO: Random (each strip one random color, new color with x Hz)


    public void gradient(View v) {
        Toast.makeText(this, "Showing gradient", Toast.LENGTH_SHORT).show();
        int[] colors = Util.gradient();

        Util.sendTop(colors);
    }


}
