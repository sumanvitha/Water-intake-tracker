package com.example.thanmai.waterintaketracker;

/**
 * Created by Thanmai on 12/24/2017.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Sumanvitha on 21-12-2017.
 */

public class DialogueActivity extends Dialog implements View.OnClickListener {
    public Activity c;
    public Button yes;
    public  Button no;
    public DialogueActivity(Activity a)
    {
        super(a);
        this.c=a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_layout);
        Button b=(Button)findViewById(R.id.ok);
        b.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.ok:
                //database code
                DBHelper dbh=new DBHelper(getContext());
                LogDetails log=new LogDetails();
                EditText e=(EditText)findViewById(R.id.amount);
                int amount=Integer.parseInt(e.getText().toString());
                log.setAmount(amount);
                Long tsLong=System.currentTimeMillis()/1000;
                String timestamp=tsLong.toString();
                log.setTime(timestamp);
                dbh.addAmount(log);

                break;
            default:break;
        }
        dismiss();
    }
}
