package com.hung.alarmclock;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import com.hung.alarmclock.adapter.AlarmAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Create an alarm manager
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    private Button turnOnAlarm;
    private Button turnOffAlarm;
    private Button addTime;
    private Button deleteTime;
    private TimePicker timePicker;
    private ListView list_View;
    private Context context;
    MainActivity inst;
    AlarmAdapter customAdapter;
    List<Long> timeArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;
        listViewAlarm();
        intWidget();
    }

    public void intWidget() {
        //initialize our alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //initialize our TimePicker
        timePicker = (TimePicker) findViewById(R.id.time_pick);
        //initialize start button
        turnOnAlarm = (Button) findViewById(R.id.alarm_on);
        //set click listener on start button
        turnOnAlarm.setOnClickListener(this);
        //initialize stop button
        turnOffAlarm = (Button) findViewById(R.id.alarm_off);
        //set click listener on start button
        turnOffAlarm.setOnClickListener(this);
        //set onclick listener for add time alarm
        addTime = (Button) findViewById(R.id.add_time);
        addTime.setOnClickListener(this);
        deleteTime = (Button) findViewById(R.id.delete);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alarm_on:
                Log.d("MainActivity", "onClick: turn on alarm");
                setAlarm();
                break;
            case R.id.alarm_off:
                Log.d("MainActivity", "onClick: turn off alarm");
                final Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                alarmManager.cancel(pendingIntent);
                //put extra string intent tells the clock that we pressed "alarm off" Button
                intent.putExtra("extra", "off");
                sendBroadcast(intent);
                break;
            case R.id.add_time:
                Log.d("MainActivity", "onClick: AddTime");
                //setting calendar instance with the hour and minute that we picked
                //on the TimePicker
                final Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                //timeArrayList.add(calendar.getTimeInMillis());
                customAdapter.add(calendar.getTimeInMillis());
                customAdapter.notifyDataSetChanged();
                break;

        }
    }


    public void setAlarm() {
        //create an instance of a calender
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        final Intent intent = new Intent(this.context, AlarmReceiver.class);
        //put extra string intent tells the clock that we pressed "alarm on" Button
        intent.putExtra("extra", "on");
        //create a pending intent that delays the intent
        //until the specified calendar time
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //set the alarm manager
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    //Create list view alarm to display on list view
    public void listViewAlarm() {
        list_View = (ListView) findViewById(R.id.list_alarm);
        customAdapter = new AlarmAdapter(this, R.layout.activity_main, timeArrayList);
        list_View.setAdapter(customAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MyActivity", "on destroy called");


    }


}
