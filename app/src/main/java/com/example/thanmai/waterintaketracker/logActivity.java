package com.example.thanmai.waterintaketracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Thanmai on 12/21/2017.
 */

public class logActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_layout);
        TextView details=(TextView)findViewById(R.id.details);
        DBHelper db=new DBHelper(this);
        String str="";
        str=db.getlog();
        details.setText(str);
    }
}
