package com.example.thanmai.waterintaketracker;

/**
 * Created by Thanmai on 12/16/2017.
 */


public class Interval {
    int hour;
    int minute;
    Interval(int h,int min)
    {
        hour=h;
        minute=min;
    }
    void setHour(int hour)
    {
        this.hour=hour;
    }
    void setMinute(int min)
    {
        this.minute=min;
    }
    int getHour()
    {
        return hour;
    }
    int getMinute()
    {
        return minute;
    }
}

