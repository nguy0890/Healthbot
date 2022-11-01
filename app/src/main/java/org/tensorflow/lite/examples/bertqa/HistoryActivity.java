package org.tensorflow.lite.examples.bertqa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
        Button clear_hist = findViewById(R.id.clear_hist_btn);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

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

        clear_hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog;
                SharedPreferences.Editor dh_sp_edit = dh_sp.edit();
                builder.setTitle(R.string.delete_history);
                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        Log.i("Diagnosis History", "Confirm Deletion");
                        dh_sp_edit.clear();
                        dh_sp_edit.commit();
                        finish();
                        startActivity(getIntent());
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        Log.i("Diagnosis History", "Cancelled");
                    }
                });
                // Create the AlertDialog
                dialog = builder.create();
                dialog.show();
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