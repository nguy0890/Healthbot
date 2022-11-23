package org.tensorflow.lite.examples.bertqa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash_screen extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Hide action bar

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Delay for 2 seconds (2000 ms)

        handler = new Handler();
        handler.postDelayed(new Runnable () {
            public void run() {
                startActivity(new Intent(splash_screen.this, LoginActivity.class));
                finish();
            }
        }, 3000);
    }
}