package com.example.thanmai.waterintaketracker;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Thanmai on 12/18/2017.
 */

public class HomeAlarmActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);
        TextView target = (TextView) findViewById(R.id.target);
         DBHelper db = new DBHelper(this);
        int weight = db.retriveWeight();
        final float litres = (float) ((0.033) * (weight)*1000);
        DBHelper db1=new DBHelper(this);
        int targetAmount=db1.getTarget();
        if((litres-targetAmount)<=0)
        {
            Toast.makeText(this,"Target Acheived",Toast.LENGTH_LONG).show();
            target.setText("Target : 0.0ml\nConsumed : "+targetAmount+"ml");
        }
        else{
        target.setText("Target : " +(litres-(targetAmount)) + "ml\nConsumed : "+targetAmount+"ml");}
        final ProgressBar pb=(ProgressBar)findViewById(R.id.progressBar2);
        float percent=((targetAmount)/(litres))*100 ;
        pb.setProgress((int)percent);


        ImageButton glass=(ImageButton)findViewById(R.id.glass);
        ImageButton cup=(ImageButton)findViewById(R.id.cup);
        ImageButton bottle=(ImageButton)findViewById(R.id.bottle);
        ImageButton log=(ImageButton)findViewById(R.id.log);
        ImageButton graph=(ImageButton)findViewById(R.id.graph);
        ImageButton settings=(ImageButton)findViewById(R.id.settings);
        glass.setOnClickListener(this);
        cup.setOnClickListener(this);
        bottle.setOnClickListener(this);
        log.setOnClickListener(this);
        graph.setOnClickListener(this);
        settings.setOnClickListener(this);
        FloatingActionButton fb=(FloatingActionButton)findViewById(R.id.addWater);
        FloatingActionButton fb1=(FloatingActionButton)findViewById(R.id.delete);
        fb.setOnClickListener(this);
        fb1.setOnClickListener(this);
        //target.setText("sucess");
    }
@Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.glass:
                        setCurrentProgress(250);
                        break;
                    case R.id.cup:
                        setCurrentProgress(100);
                        break;
                    case R.id.bottle:
                        setCurrentProgress(500);
                        break;
                    case R.id.addWater:
                        addManually();
                        break;
                    case R.id.delete:
                        deleteManually();
                        break;
                    case R.id.graph:
                        Intent gintent;
                        gintent = new Intent(this,graphActivity.class);
                        startActivity(gintent);
                        break;
                    case R.id.log:
                        Intent lintent=new Intent(this,logActivity.class);
                        startActivity(lintent);
                        break;
                    case R.id.settings:
                        Intent sintent=new Intent(this,settingsActivity.class);
                        startActivity(sintent);
                        break;

                }

            }

    private void setTarget() {
        Toast.makeText(this,"vrfdj",Toast.LENGTH_LONG).show();
        DBHelper db=new DBHelper(this);
        float l=(float)(0.033*db.retriveWeight()*1000);
        TextView t=(TextView)findViewById(R.id.target);
        ProgressBar pb=(ProgressBar)findViewById(R.id.progressBar2);
        int amt=db.getTarget();
        float percent=((amt)/(l))*100;
        pb.setProgress((int)percent);
        t.setText("Target : "+(l-(amt))+"ml");

    }

    private void addManually() {
//   requestWindowFeature(Window.FEATURE_NO_TITLE);
       /*final Dialog d=new Dialog(getApplicationContext());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_layout);
        Button b=(Button)d.findViewById(R.id.ok);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper dbh=new DBHelper(getApplicationContext());
                LogDetails log=new LogDetails();
                EditText e=(EditText)d.findViewById(R.id.amount);
                int amount=Integer.parseInt(e.getText().toString());
                log.setAmount(amount);
                Long tsLong=System.currentTimeMillis()/1000;
                String timestamp=tsLong.toString();
                log.setTime(timestamp);
                dbh.addAmount(log);
                d.dismiss();
                setTarget();

            }
        });
        d.show();*/
       DialogueActivity d=new DialogueActivity(this);
        d.show();

            }



   private void deleteManually()
   {
     DBHelper db=new DBHelper(this);
      boolean status= db.deleteAmount();
       if(!status)
       {
           Toast.makeText(this,"Add amount",Toast.LENGTH_LONG);
       }
       else
       {
           int weight=db.retriveWeight();
           float litres=(float)(0.033*weight*1000);
           ProgressBar pb=(ProgressBar)findViewById(R.id.progressBar2);
           TextView target=(TextView)findViewById(R.id.target);
           int amt=db.getTarget();
           float percent=((amt)/(litres))*100;
           pb.setProgress((int)percent);
           if((litres-amt)<=0)
           {
               //Toast.makeText(this,"Target Reached",Toast.LENGTH_LONG).show();
               target.setText("Target : 0ml\nConsumed : "+amt+"ml");
           }
           else {
               target.setText("Target : " + (litres - (amt)) + "ml\nConsumed : " + amt + "ml");
           }

       }
   }
    private void setCurrentProgress(int i) {
        DBHelper dbh=new DBHelper(this);
        LogDetails l=new LogDetails();
        l.setAmount(i);
        Long tsLong=System.currentTimeMillis()/1000;
        String timestamp=tsLong.toString();
        l.setTime(timestamp);
        dbh.addAmount(l);
        int weight=dbh.retriveWeight();
        float litres=(float)(0.033*weight*1000);
        ProgressBar pb=(ProgressBar)findViewById(R.id.progressBar2);
        TextView target=(TextView)findViewById(R.id.target);
        int amt=dbh.getTarget();
        float percent=((amt)/(litres))*100;
        pb.setProgress((int)percent);
        if((litres-amt)<=0)
        {
            Toast.makeText(this,"Target Reached",Toast.LENGTH_LONG).show();
            target.setText("Target : 0ml\nConsumed : "+amt+"ml");
        }
        else {
            target.setText("Target : " + (litres - (amt)) + "ml\nConsumed : " + amt + "ml");
        }
        Toast.makeText(this,i+"ml added",Toast.LENGTH_LONG).show();
    }
}
