package com.example.thanmai.waterintaketracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Thanmai on 12/21/2017.
 */



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class graphActivity extends AppCompatActivity {
    Button button;
    EditText xInput,yInput;
    GraphView graph;
    LineGraphSeries<DataPoint> series;
    DBHelper myHelper;
    SQLiteDatabase sqLiteDatabase;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);
        graph = (GraphView)findViewById(R.id.graph);

        // myHelper = new MyHelper(this);
        //sqLiteDatabase = myHelper.getWritableDatabase();
        exqButton();
    }


    private  void exqButton(){

        DataPoint[] dp=getData();
        series=new LineGraphSeries<DataPoint>(dp);
        graph.addSeries(series);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setLabelsSpace(10);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);
        graph.getGridLabelRenderer().setLabelVerticalWidth(100);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(100);
// set manual x bounds to have nice steps
       // graph.getViewport().setMinX(dp[0].getX());


        graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

    }

    private DataPoint[] getData(){
        DBHelper mh=new DBHelper(this);

        DataPoint[] dp=mh.getXYcoordinates();
        return dp;
    }

}

