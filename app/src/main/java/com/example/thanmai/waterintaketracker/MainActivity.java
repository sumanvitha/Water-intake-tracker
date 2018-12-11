package com.example.thanmai.waterintaketracker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
         DBHelper db=new DBHelper(this);
     final Interval i1=new Interval(9,0);
        final Interval i2=new Interval(21,0);
        int count= db.getCount();
        if(count==0) {
            setContentView(R.layout.activity_main);
            final TextView wakeup=(TextView)findViewById(R.id.wakeup);
            wakeup.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            if(selectedMinute<10)
                            {
                                wakeup.setText(selectedHour+":0"+selectedMinute);
                            }
                            else
                            {
                            wakeup.setText( selectedHour + ":" + selectedMinute);}
                            i1.setHour(selectedHour);
                            i1.setMinute(selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();

                }
            });
            final TextView sleep=(TextView)findViewById(R.id.sleep);
            sleep.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            if(selectedMinute<10)
                            {
                                sleep.setText(selectedHour+":0"+selectedMinute);
                            }
                            else
                            {
                            sleep.setText( selectedHour + ":" + selectedMinute);}
                             i2.setHour(selectedHour);
                            i2.setMinute(selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();

                }
            });
            final EditText weight = (EditText) findViewById(R.id.editText);
            final TextView t = (TextView) findViewById(R.id.textView);
            Button b = (Button) findViewById(R.id.button);
            final DBHelper dbh = new DBHelper(this);
            final DBHelper dbh2 = new DBHelper(this);
            b.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    //Toast.makeText(this,"h2",Toast.LENGTH_LONG).show();
                    if((weight.getText().toString()).equals(""))
                    {

                    }
                    int weigh = Integer.parseInt(weight.getText().toString());
                    Long tsLong = System.currentTimeMillis() / 1000;
                    String timestamp = tsLong.toString();
                    Weight w = new Weight();
                    w.setWeight(weigh);
                    w.setTime(timestamp);
                    dbh.addWeight(w);
dbh.addRemainder(1);
                dbh2.addInterval(i1, i2);

                    Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
                    startActivity(intent);

                    finish();

                }
            });


        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), HomeAlarmActivity.class);
            startActivity(intent);
            // Toast.makeText(this,"h2",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public Context get()
    {
        return this;
    }
}

