package com.example.thanmai.waterintaketracker;

/**
 * Created by Thanmai on 12/16/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;

import com.jjoe64.graphview.series.DataPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WaterIntakeTracking.db";
    public static final String TABLE_WEIGHT = "weightTable";
    public static final String KEY_ID= "id";
    public static final String WEIGHT= "weight";
    public static final String TIMESTAMP = "timestamp";
    public static final String TABLE_INTERVAL="interval";
    public static final String HOUR1="hour1";
    public static final String MINUTE1="minute1";
    public static final String HOUR2="hour2";
    public static final String MINUTE2="minute2";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WEIGHT_TABLE="create table if not exists "+TABLE_WEIGHT+"( "+KEY_ID+" INTEGER PRIMARY KEY, "+WEIGHT+" INTEGER,"+TIMESTAMP+" TEXT)";
        String CREATE_INTERVAL_TABLE="create table if not exists "+TABLE_INTERVAL+"("+HOUR1+" INTEGER, "+MINUTE1+" INTEGER,"+ HOUR2 +" INTEGER,"+ MINUTE2+" INTEGER )";
        String CREATE_LOG_TABLE="create table if not exists logdetailstable (timestamp TEXT, logdate TEXT, weight INTEGER, amount INTEGER )";
        String CREATE_TABLE="create table if not exists reminder (rstatus INTEGER )";
String table="create table if not exists duration (rduration INTEGER)";
        db.execSQL(CREATE_WEIGHT_TABLE);
        db.execSQL(CREATE_INTERVAL_TABLE);
        db.execSQL(CREATE_LOG_TABLE);
        db.execSQL(CREATE_TABLE);
        db.execSQL(table);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public void addWeight(Weight weight)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(WEIGHT,weight.getWeight());
        values.put(TIMESTAMP,weight.getTime());
        db.insert(TABLE_WEIGHT,null,values);
        db.close();
    }
    public int getRemainder()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("select * from reminder",null);
        c.moveToLast();
        return c.getInt(0);
    }
    public void addRemainder(int x)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("rstatus",x);
        db.insert("reminder",null,values);
    }
    public String display()

    {
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor c=db.rawQuery("SELECT * FROM weightTable", null);
        if(c.getCount()==0)
        {
            return "No records found";
        }
        StringBuffer buffer=new StringBuffer();
        while(c.moveToNext())
        {
            buffer.append("Id "+c.getString(0)+"\n");
            buffer.append("Weight "+c.getString(1)+"\n");
            buffer.append("Time "+c.getString(2)+"\n\n");
        }
        return buffer.toString();
    }
    public int retriveWeight()
    {
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor c=db.rawQuery("SELECT * FROM weightTable", null);
        c.moveToLast();
        return c.getInt(1);
    }
    public int getCount()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM weightTable", null);
       return c.getCount();
    }

    public void addInterval(Interval i1, Interval i2) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(HOUR1,i1.getHour());
        values.put(MINUTE1,i1.getMinute());
        values.put(HOUR2,i2.getHour());
        values.put(MINUTE2,i2.getMinute());

        db.insert(TABLE_INTERVAL,null,values);
        db.close();

    }
    public int[] getInterval()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM interval", null);
        c.moveToLast();
        int[] v=new int[4];
        v[0]=c.getInt(0);
        v[1]=c.getInt(1);
        v[2]=c.getInt(2);
        v[3]=c.getInt(3);
return v;
    }
    public void addAmount(LogDetails l)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        SQLiteDatabase db2=this.getReadableDatabase();
        Cursor c=db2.rawQuery("select weight from weightTable",null);
        c.moveToLast();
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(l.getTime())*1000);
        String  logTableDate= DateFormat.format("dd-MM-yyyy",cal).toString();
        ContentValues values=new ContentValues();
        values.put("timestamp",l.getTime());
        values.put("logdate",logTableDate);
        values.put("weight",c.getInt(0));
        values.put("amount",l.getAmount());
        db.insert("logdetailstable",null,values);
        db.close();
    }
    public int getTarget() {
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor c=db.rawQuery("SELECT * FROM  logdetailstable", null);
        if(c.getCount()==0)
        {
            return 0;
        }
        int target=0;
        while(c.moveToNext())
        {
            Calendar cal2 = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(cal2.getTime());
            if(c.getString(1).equals(formattedDate))
            {
                target=target+c.getInt(3);
            }
        }
        return target;

    }


    public String getlog() {
        String str="";
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor c=db.rawQuery("SELECT * FROM  logdetailstable", null);
        while(c.moveToNext())
        {
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(c.getLong(0)*1000);
            SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat sdf2=new SimpleDateFormat("dd:MM:yyyy");
            Date d=(Date)cal.getTime();
            str=str+sdf2.format(d)+"\t\t\t\t\t\t"+sdf.format(d)+"\t\t\t\t\t\t\t"+c.getInt(3)+"\n";
        }

        return str;
    }

    public DataPoint[] getXYcoordinates()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select logdate,avg(weight),sum(amount),timestamp from logdetailstable group by logdate",null);
        DataPoint[] coordinates=new DataPoint[cursor.getCount()];
        int i=0;
        while(cursor.moveToNext())
        {

            SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");

           Calendar c= Calendar.getInstance();
            c.setTimeInMillis(cursor.getLong(3)*1000);
            Date d=(Date)c.getTime();

           String arr[]=new String[3];
            String date=cursor.getString(0);
            arr=date.split("-");

            float y=((cursor.getInt(2))/((float)(cursor.getInt(1)*0.033*1000)))*100;
            DataPoint dp=new DataPoint(d,(int)y);
            coordinates[i]=dp;
            i++;
        }
        return coordinates;
    }
    public boolean deleteAmount()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        SQLiteDatabase db1=this.getReadableDatabase();
        Cursor c=db1.rawQuery("select timestamp from logdetailstable",null);
        if(c.getCount()==0)
            return false;
        c.moveToLast();
        String ts=c.getString(0);
        db.delete("logdetailstable","timestamp="+ts,null);
        return true;
    }


    public int getRemainderCount() {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("select * from reminder",null);
        return c.getCount();
    }

    public void changeDuration(int time) {
    SQLiteDatabase db=this.getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put("rduration",time);
        db.insert("duration",null,c);
    }

    public int getDuration() {
    SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("select * from duration",null);
        if(c.getCount()==0)
        {
            return 30;
        }

        c.moveToLast();
        return c.getInt(0);
    }
}



