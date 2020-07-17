package com.pnup.keziaprevia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    Animation app_splash,btt;
    ImageView app_logo;
    TextView app_subtitle;
    ProgressBar pBar;
    private int i = 0;

    String USERNAME_KEY="usernamekey";
    String username_key="";
    String username_key_new="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //load animasi ke activity
        app_splash = AnimationUtils.loadAnimation(this,R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);

        //load element
        app_logo = findViewById(R.id.app_logo);
        app_subtitle = findViewById(R.id.app_subtitle);
        pBar = findViewById(R.id.pBar);

        //run animation
        app_logo.startAnimation(app_splash);
        app_subtitle.startAnimation(btt);

        pBar.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pBar.setVisibility(View.VISIBLE);
            }
        }, 1000);

        new Thread(new Runnable() {
            public void run() {
                while (i < 10) {
                    i++;
                    handler.post(new Runnable() {
                        public void run() {
                            pBar.setProgress(i);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        getUsernameLocal();
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");

        if(username_key_new.isEmpty()){
            //setting timer untuk 3.5 detik
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, GetStartedActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 3500);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 3500);
        }
    }
}