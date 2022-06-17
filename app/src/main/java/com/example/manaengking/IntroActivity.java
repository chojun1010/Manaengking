//--- IntroActivity

package com.example.manaengking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        com.example.manaengking.IntroThread introThread = new com.example.manaengking.IntroThread(handler);
        introThread.start();
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Intent intent = new Intent(IntroActivity.this, com.example.manaengking.MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}