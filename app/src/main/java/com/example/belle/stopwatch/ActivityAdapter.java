package com.example.belle.stopwatch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdapter extends ArrayAdapter<Activity> {
    private Context mContext;
    private List<Activity> activityList;

    public ActivityAdapter(Context context, ArrayList<Activity> list)
    {
        super(context, 0, list);
        mContext = context;
        activityList = list;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null)
        {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_view,parent,false);
        }

        Activity currentActivity = activityList.get(position);

        TextView activity = listItem.findViewById(R.id.textView_Name);
        activity.setText(currentActivity.getName());

        TextView seconds = listItem.findViewById(R.id.textView_Number);
        seconds.setText(Long.toString(currentActivity.getTime()) + " seconds");

        return listItem;
    }
}
