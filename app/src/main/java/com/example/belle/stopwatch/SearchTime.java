package com.example.belle.stopwatch;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchTime extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private ChildEventListener childEventListener;

    private ArrayList<Activity> activityList;
    private ArrayList<Activity> searchResults;

    private ActivityAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("activities");

        activityList = new ArrayList<>();
        searchResults = new ArrayList<>();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                activityList.add(dataSnapshot.getValue(Activity.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };

        myRef.addChildEventListener(childEventListener);

        listAdapter = new ActivityAdapter(this, searchResults);
        ListView results = findViewById(R.id.listViewResults);
        results.setAdapter(listAdapter);
    }

    public void searchTime(View v)
    {
        listAdapter.clear();
        boolean found = false;
        EditText text = findViewById(R.id.editTextName);
        String search = text.getText().toString();  // Extracts name from EditText
        for (Activity a : activityList)
        {
            if (a.getName().equalsIgnoreCase(search))
            {
                listAdapter.add(a);
                found = true;
            }
        }
        if (!found)
        {
            Toast.makeText(this, text.getText().toString() + " not found.", Toast.LENGTH_LONG).show();
        }
        text.setText("");
    }

    public void goHome(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
