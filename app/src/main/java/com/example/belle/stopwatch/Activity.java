package com.example.belle.stopwatch;

public class Activity {

    private String name;
    private long time;
    private String uid;

    public Activity()
    {
        name = "NA";
        time = 0;
    }

    public Activity(String name, long time, String uid)
    {
        this.name = name;
        this.time = time;
        this.uid = uid;
    }

    public String getName()
    {
        return name;
    }

    public long getTime()
    {
        return time;
    }

    public String getUid()
    {
        return uid;
    }

}
