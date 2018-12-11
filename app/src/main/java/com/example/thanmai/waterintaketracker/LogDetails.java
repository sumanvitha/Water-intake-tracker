package com.example.thanmai.waterintaketracker;

/**
 * Created by Thanmai on 12/18/2017.
 */

public class LogDetails {
    int amount;
    String time;
    void setAmount(int amount)
    {
        this.amount=amount;
    }
    void setTime(String time)
    {
        this.time=time;
    }
    float getAmount()
    {
        return amount;
    }
    String getTime()
    {
        return time;
    }
}
