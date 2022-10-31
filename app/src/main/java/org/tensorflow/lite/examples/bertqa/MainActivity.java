package org.tensorflow.lite.examples.bertqa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.app.Activity;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MainActivity"; //debugging message

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In onResume()");
        setContentView(R.layout.activity_main);

        //Declare button
        final Button aboutButton = findViewById(R.id.aboutButton);


        //AboutUs button on click
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked About Us Button");
                Intent intent = new Intent(MainActivity.this, AboutUs.class);
                startActivityForResult(intent, 10);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}