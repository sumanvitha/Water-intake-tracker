package com.example.thanmai.waterintaketracker;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Thanmai on 12/18/2017.
 */

public class MyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      //Toast.makeText(context,"hello",Toast.LENGTH_LONG).show();
        DBHelper db=new DBHelper(context);
        int amount=db.getTarget();
        int weight=db.retriveWeight();
        float target=(float)(0.033*weight*1000);
                Calendar c= Calendar.getInstance();
        int hour= c.get(Calendar.HOUR_OF_DAY);
        int minute=c.get(Calendar.MINUTE);
        int[] a=new int[4];
        a=db.getInterval();
        int duration=a[2]-a[0];
        duration=duration*(60/15);
       if((float)amount>=target||(hour>=a[2]&&minute>=a[3])||db.getRemainder()==0)
           cancelReapeatingAlarm(context);
        else {
            //float x = (((float) target) / duration) * (hour - a[0])*4;
           // if (db.getTarget() < x) {
                Intent rintent = new Intent(context, HomeAlarmActivity.class);
                rintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pi = PendingIntent.getActivity(context, 100, rintent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

                Notification notification = builder.setContentTitle("Water intake tracker")
                        .setContentText("Did you had any water??")
                        .setAutoCancel(true)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setSmallIcon(R.drawable.glass)
                        .setContentIntent(pi)
                        .build();

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(100, notification);
}    }

    public void cancelReapeatingAlarm(Context context)
    {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context,MyAlarm.class);
        PendingIntent pIntent=PendingIntent.getBroadcast(context,2,intent,0);
        am.cancel(pIntent);
        Toast.makeText(context,"inside cancel",Toast.LENGTH_LONG).show();
    }

}
