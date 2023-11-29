package com.example.socialdisable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class first_screen extends AppCompatActivity {
    private static final long SPLASH_DELAY = 2000; // 4 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_first);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(first_screen.this, second_Screen.class);
            startActivity(intent);
            finish();
        }, SPLASH_DELAY);


    }
}