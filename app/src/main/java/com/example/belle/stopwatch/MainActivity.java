package com.example.belle.stopwatch;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private boolean running;
    private boolean paused;
    private long diff;
    private long pauseBuffer;

    private DatabaseReference myRef;
    private FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("activities");

    }

    public void startTime(View v)
    {
        if (paused)
        {
            diff = SystemClock.elapsedRealtime() - pauseBuffer;
        }

        if (!running)
        {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseBuffer);
            chronometer.start();
            running = true;
        }
    }

    public void pauseTime(View v)
    {
        paused = true;
        if (running)
        {
            chronometer.stop();
            pauseBuffer = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void resetTime(View v)
    {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseBuffer = 0;
        running = false;
    }

    public void addTime(View v)
    {
        long time;
        String timeString = chronometer.getText().toString();
        String chronoArray[] = timeString.split(":");
        time =(Long.parseLong(chronoArray[0]) * 60) + Long.parseLong(chronoArray[1]);

        EditText editText = findViewById(R.id.editTextName);
        String name = editText.getText().toString();

        String key = myRef.push().getKey();
        Activity a = new Activity(name, time, key);
        myRef.child(key).setValue(a);
        Toast.makeText(this, a.getName() + " successfully added.", Toast.LENGTH_LONG).show();

        editText.setText("");
    }

    public void searchTime(View v)
    {
        Intent intent = new Intent(this, SearchTime.class);
        startActivity(intent);
    }

    public void deleteTime(View v)
    {
        Intent intent = new Intent(this, DeleteTime.class);
        startActivity(intent);
    }
}
