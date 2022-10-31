package org.tensorflow.lite.examples.bertqa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "HistoryActivity";
    protected ArrayList<String> diagnosis_history = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "in onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        HistoryAdapter diagnosisAdapter = new HistoryAdapter(this);
        ListView history_listView = findViewById(R.id.history_listView);
        // temporary button to add diagnosis to diagnosis history
        Button temp_button = findViewById(R.id.add_diagnosis_btn);
        // get date for diagnosis
        Date currentTime = Calendar.getInstance().getTime();

        history_listView.setAdapter(diagnosisAdapter);

        SharedPreferences dh_sp = getSharedPreferences("history_sp", MODE_PRIVATE);

        // array containing all shared preference keys
        Map<String, ?> keys = dh_sp.getAll();

        // if no keys (no saved data) then set text to "No Previous Diagnosis",
        // else, loop through keys
        if (keys.size() == 0) {
            diagnosis_history.add("No Previous Diagnosis");
            diagnosisAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/
        } else {
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                diagnosis_history.add(entry.getKey() + ": \n" +
                        entry.getValue().toString());
                diagnosisAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/
            }
        }

        // temporary button to add diagnosis to shared preferences and reload activity
        // in final iteration, this will be done automatically in chat-bot activity
        temp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor s_prefs_edit = dh_sp.edit();
                s_prefs_edit.putString(currentTime.toString(), "Diagnosis Description");
                s_prefs_edit.commit();
                finish();
                startActivity(getIntent());
            }
        });
    }

    private class HistoryAdapter extends ArrayAdapter<String> {
        protected static final String ACTIVITY_NAME = "HistoryAdapter";

        public HistoryAdapter(Context ctx) {
            super(ctx, 0);
            Log.i(ACTIVITY_NAME, "in constructor");
        }

        public int getCount(){
            Log.i(ACTIVITY_NAME, "in onCount()");
            return diagnosis_history.size();
        }

        public String getItem(int position) {
            Log.i(ACTIVITY_NAME, "in getItem()");
            return diagnosis_history.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            Log.i(ACTIVITY_NAME, "in getView()");
            LayoutInflater inflater = HistoryActivity.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.history_diagnosis_item, null); ;
            TextView message = (TextView)result.findViewById(R.id.diagnosis_text);
            message.setText(   getItem(position)  ); // get the string at position
            return result;
        }
    }
}