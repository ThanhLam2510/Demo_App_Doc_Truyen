package com.example.app_doc_truyen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.demo_app_doc_truyen.R;

import java.util.Timer;
import java.util.TimerTask;

public class Logo_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Logo_Activity.this, Login_Activity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}