package com.example.thanmai.waterintaketracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Thanmai on 12/20/2017.
 */

public class DailyAlarm extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
      // Toast.makeText(context,"second",Toast.LENGTH_LONG).show();
DBHelper db=new DBHelper(context);
        int x=db.getDuration();
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent in=new Intent(context,MyAlarm.class);
        PendingIntent pI=PendingIntent.getBroadcast(context, 2, in, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+1000*60,x*1000*60, pI);
       // Toast.makeText(context,x+"second alarm",Toast.LENGTH_LONG).show();

    }

}
