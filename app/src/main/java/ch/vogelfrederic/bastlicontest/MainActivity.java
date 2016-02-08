package ch.vogelfrederic.bastlicontest;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import ch.vogelfrederic.bastlicontest.Async.Chase;
import ch.vogelfrederic.bastlicontest.Async.Chase_colored;
import ch.vogelfrederic.bastlicontest.Async.Pulsate;
import ch.vogelfrederic.bastlicontest.Async.SendData;
import ch.vogelfrederic.bastlicontest.Listeners.ChaseListener;
import ch.vogelfrederic.bastlicontest.Listeners.ColoredChaseListener;
import ch.vogelfrederic.bastlicontest.Listeners.PulsateListener;
import ch.vogelfrederic.bastlicontest.Listeners.ToggleListener;
import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    private ToggleButton toggle_off;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Switch switch_coloredChase;
        Switch switch_chase;
        Switch switch_pulsate;

        toggle_off = (ToggleButton) findViewById(R.id.toggle_off);
        toggle_off.setOnCheckedChangeListener(new ToggleListener());
        switch_coloredChase = (Switch) findViewById(R.id.switch_coloredChase);
        switch_coloredChase.setOnCheckedChangeListener(new ColoredChaseListener());
        switch_chase = (Switch) findViewById(R.id.switch_chase);
        switch_chase.setOnCheckedChangeListener(new ChaseListener());
        switch_pulsate = (Switch) findViewById(R.id.switch_pulsate);
        switch_pulsate.setOnCheckedChangeListener(new PulsateListener());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_color:
                AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, Util.color+0xFF000000, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        Log.d("COLOR", String.valueOf(color));
                        Util.color = color;
                        Util.colorSelected = color;
                        toolbar.setBackgroundColor(color);
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
        int[] colors = Util.gradient();

        Util.sendTop(colors);
    }



}
