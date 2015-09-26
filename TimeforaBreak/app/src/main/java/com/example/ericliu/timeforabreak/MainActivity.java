package com.example.ericliu.timeforabreak;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView hours_text;
    TextView minutes_text;
    TextView seconds_text;
    //Boolean isWorking;

    private int workInterval;
    private int breakInterval;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Sets the default values for the application on initial running of the app
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);

        //gets the default intervals from the settings
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String workString = sharedPref.getString("work_interval", "30");
        String breakString = sharedPref.getString("break_interval", "5");

        workInterval = Integer.parseInt(workString) * 60000;
        breakInterval = Integer.parseInt(breakString) * 60000;

        hours_text = (TextView)findViewById(R.id.hours_text);
        minutes_text = (TextView)findViewById(R.id.minutes_text);
        seconds_text = (TextView)findViewById(R.id.seconds_text);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // TEST COMMENT
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if ( id == R.id.action_settings ) {
//            getFragmentManager().beginTransaction()
//                    .replace(android.R.id.content, new SettingsFragment())
//                    .commit();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(android.R.id.content, new SettingsFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if ( id == R.id.action_browse_stretches ) {
            Intent intent = new Intent(this, BrowseStretchActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void startTimer(View view) {
        new CountDownTimer(workInterval, 1000) {

            public void onTick(long millisUntilFinished) {
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                // Updates 3 textViews
                updateMainClock(millisUntilFinished);
            }

            public void onFinish() {
                //mTextField.setText("done!");
                // Send notification or do stuff

            }
        }.start();
    }

    public void updateMainClock(long millisUntilFinished) {
        int total_hours, total_minutes, total_seconds;
        int hours, minutes, seconds;

        total_seconds = ((int) millisUntilFinished) / 1000;
        seconds = total_seconds % 60;
        total_minutes = (total_seconds - seconds) / 60;
        minutes = total_minutes % 60;
        total_hours = (total_minutes - minutes) / 60;
        hours = total_hours % 99;

        String seconds_string = Integer.toString(seconds);
        String minutes_string = Integer.toString(minutes);
        String hours_string = Integer.toString(hours);

        if(seconds_string.length() == 1) {
            seconds_string = "0" + seconds_string;
        }
        if(minutes_string.length() == 1) {
            minutes_string = "0" + minutes_string;
        }
        if(hours_string.length() == 1) {
            hours_string = "0" + hours_string;
        }

        hours_text.setText(hours_string);
        minutes_text.setText(minutes_string);
        seconds_text.setText(seconds_string);
    }

    //Notifies the user that they should take a break, starts a new time using the break time
    //
    public void notifyUser() {

    }


}