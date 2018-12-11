package com.example.thanmai.waterintaketracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Thanmai on 12/21/2017.
 */

public class settingsActivity extends AppCompatActivity{
    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        final DBHelper db=new DBHelper(this);
        int weight=db.retriveWeight();
        int[] a=new int[4];
        a=db.getInterval();
        final Interval i1=new Interval(a[0],a[1]);
        final Interval i2=new Interval(a[2],a[3]);
        final Switch reminder=(Switch)findViewById(R.id.rswitch);
        if(db.getRemainder()==1)
        {
            reminder.setChecked(true);
        }
        else
            reminder.setChecked(false);
        final Boolean x= reminder.isChecked();
        final TextView display=(TextView)findViewById(R.id.displaytar);
        float l=(float)(0.033*1000*weight);
        display.setText("Target : "+l+"ml");
        Button b=(Button)findViewById(R.id.button2);
        final EditText ed=(EditText)findViewById(R.id.sweight);
        ed.setText(""+weight);
        EditText retime=(EditText)findViewById(R.id.duration);
   retime.setText(""+db.getDuration());
        final TextView t=(TextView)findViewById(R.id.swakeup);
        if(a[1]<10)
        t.setText(a[0]+":0"+a[1]);
        else
            t.setText(a[0]+":"+a[1]);
        final TextView t2=(TextView)findViewById(R.id.ssleep);
        final Button update=(Button)findViewById(R.id.weightUpdate);
        final Button time=(Button)findViewById(R.id.button3);
        if(a[3]<10)
            t2.setText(a[2]+":0"+a[3]);
        else
        t2.setText(a[2]+":"+a[3]);
        t.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(settingsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                       if(selectedMinute<10)
                           t.setText( selectedHour + ":0" + selectedMinute);
                        else
                        t.setText( selectedHour + ":" + selectedMinute);
                        i1.setHour(selectedHour);
                        i1.setMinute(selectedMinute);

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Change Time");
                mTimePicker.show();

            }
        });
        t2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(settingsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedMinute<10)
                        t2.setText( selectedHour + ":0" + selectedMinute);
                        else
                            t2.setText( selectedHour + ":" + selectedMinute);
                        i2.setHour(selectedHour);
                        i2.setMinute(selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Change Time");
                mTimePicker.show();

            }
        });
b.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        db.addInterval(i1,i2);
Intent in=new Intent(getApplicationContext(),AlarmActivity.class);
        startActivity(in);
        Toast.makeText(getApplicationContext(),"interval changed",Toast.LENGTH_LONG).show();

   }
});
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int w=Integer.parseInt(ed.getText().toString());
               // Toast.makeText(getApplicationContext(),""+w,Toast.LENGTH_LONG).show();
                Weight ww=new Weight();
                ww.setWeight(w);
                float l=(float)(0.033*1000*w);
                Long tsLong = System.currentTimeMillis() / 1000;
                String timestamp = tsLong.toString();
                ww.setTime(timestamp);
                db.addWeight(ww);

                display.setText("Target : "+l+"ml");
                Toast.makeText(getApplicationContext(),"Weight updated",Toast.LENGTH_LONG).show();
            }
        });
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reminder.isChecked())
                {

                 if(x)
                 {
                     Toast.makeText(getApplicationContext(),"already set",Toast.LENGTH_LONG).show();
                 }
                 else
                 {
                     db.addRemainder(1);
                     Toast.makeText(getApplicationContext(),"Reminder set ",Toast.LENGTH_LONG).show();
                     Intent in=new Intent(getApplicationContext(),AlarmActivity.class);
                     startActivity(in);
                 }
                }
                else
                {
                    if(x) {
                        db.addRemainder(0);
                        reminder.setChecked(false);
                        Toast.makeText(getApplicationContext(),"reminder cancelled ",Toast.LENGTH_LONG).show();
                        Intent in=new Intent(getApplicationContext(),AlarmActivity.class);
                        startActivity(in);
                    }
                }
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText duration=(EditText)findViewById(R.id.duration);
                int time=Integer.parseInt(duration.getText().toString());
               db.changeDuration(time);
                Intent in=new Intent(getApplicationContext(),AlarmActivity.class);
                startActivity(in);
                Toast.makeText(getApplicationContext(),"Duration changed to "+time+"min",Toast.LENGTH_LONG).show();
            }
        });
    }

}
