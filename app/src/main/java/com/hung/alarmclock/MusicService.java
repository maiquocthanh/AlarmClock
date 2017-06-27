package com.hung.alarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;

import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Random;

public class MusicService extends Service {
    MediaPlayer mediaPlayer;
    private int startid;
    private boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MainActivity", "in the Service");

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //fetch the extra string values
        String state = intent.getExtras().getString("extra");
        Log.d("MainActivity", "Ringtone state is " + state);
        //notification
        //set up the notification service
        NotificationManager notify_manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        //setup an intent that goes to the MainActivity
        Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);
        //set up a pending intent
        PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this
                , 0, intent_main_activity, 0);
        //make the notification parameters

        Notification notification_popup = new Notification.Builder(this)
                .setContentTitle("An alarm is going off")
                .setContentText("Click me")
                .setContentIntent(pending_intent_main_activity)
                .setSmallIcon(R.drawable.ic_action_call)
                .setAutoCancel(true).build();


        assert state != null;
        switch (state) {
            case "on":
                startId = 1;
                break;
            case "off":
                startId = 0;
                Log.e("start id is ", state);
                break;
            default:
                startId = 0;
                break;
        }


        if (!this.isRunning && startId == 1) {
            Log.e("if there was not sound", "you want to start");

            //create random number song music for the Ringtone
            int min = 1;
            int max = 8;
            Random rd = new Random();
            int random_number = rd.nextInt(max - min + 1) + min;
            Log.e("Random number is", String.valueOf(random_number));

            if (random_number == 1) {
                // create an instance of the media player
                mediaPlayer = MediaPlayer.create(this, R.raw.number_1);
                //start ringtone

            } else if (random_number == 2) {
                mediaPlayer = MediaPlayer.create(this, R.raw.number_2);

            } else if (random_number == 3) {
                mediaPlayer = MediaPlayer.create(this, R.raw.number_3);

            } else if (random_number == 4) {
                mediaPlayer = MediaPlayer.create(this, R.raw.number_4);

            } else if (random_number == 5) {
                mediaPlayer = MediaPlayer.create(this, R.raw.number_5);

            } else if (random_number == 6) {
                mediaPlayer = MediaPlayer.create(this, R.raw.number_6);

            } else if (random_number == 7) {
                mediaPlayer = MediaPlayer.create(this, R.raw.number_7);

            } else if (random_number == 8) {
                mediaPlayer = MediaPlayer.create(this, R.raw.number_8);

            }

            mediaPlayer.start();
            this.isRunning = true;
            this.startid = 0;
            //set up the notification start command
            notify_manager.notify(0, notification_popup);

        }

        // if there is music playing, and the user pressed "alarm off"
        // music should stop playing
        else if (this.isRunning && startId == 0) {
            Log.e("if there is  sound", "and you want to end");
            //stop the ringtone
            mediaPlayer.stop();
            mediaPlayer.reset();
            this.isRunning = false;
            this.startid = 0;

        }

        // these are if the user presses random buttons
        // just to bug-proof the app
        // if there is no music playing, and the user pressed "alarm off"
        // do nothing
        else if (!this.isRunning && startId == 0) {
            Log.e("if there was not sound", "you want to end");
            this.isRunning = false;
            this.startid = 0;


        }
        //if there is music playing and the user pressed "alarm on"
        //do nothing
        else if (this.isRunning && startId == 1) {
            Log.e("if there is sound", "and you want to start");
            this.isRunning = true;
            this.startid = 1;

        }


        Log.e("I am in the Service", "Hello");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        //tell the user we stopped
        Log.e("JSLog", "on Destroy called");

        super.onDestroy();
        this.isRunning = false;
    }
}

