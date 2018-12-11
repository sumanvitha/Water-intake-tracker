package com.example.thanmai.waterintaketracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Thanmai on 12/28/2017.
 */

public class AlarmActivity extends AppCompatActivity
{
@Override
    protected void onCreate(Bundle savedInstanceState)
{
    super.onCreate(savedInstanceState);

DBHelper db=new DBHelper(this);
    int x=db.getRemainder();
    if(x==1)
    {setalarm();}
    else
        cancel();
    int c=db.getRemainderCount();
    if(c==1)
    {Intent in=new Intent(getApplicationContext(),HomeAlarmActivity.class);
    startActivity(in);
    finish();}
    else
        finish();
}

    public void setalarm() {
        DBHelper db=new DBHelper(this);
        int[] i=new int[4];
        Calendar ch = Calendar.getInstance();

        ch.set(
                ch.get(Calendar.YEAR),
                ch.get(Calendar.MONTH),
                ch.get(Calendar.DAY_OF_MONTH),
                i[0],i[1], 0
        );

        AlarmManager am = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, DailyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, ch.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        //Toast.makeText(this,"alarm is set",Toast.LENGTH_SHORT).show();
    }
    public void cancel()
    {
        AlarmManager am=(AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent in=new Intent(this,DailyAlarm.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,in,0);
        am.cancel(pendingIntent);
        Toast.makeText(this,"Remainder off",Toast.LENGTH_LONG).show();
    }
}
