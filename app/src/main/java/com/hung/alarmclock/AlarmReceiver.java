package com.hung.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       Log.e("We are in the Receiver","Hello");

        //fetch extra string from the intent
        String string = intent.getExtras().getString("extra");
        Log.e("You Passed the key",string);
        Intent myIntent = new Intent(context, MusicService.class);
        myIntent.putExtra("extra", string);
        context.startService(myIntent);

    }
}
