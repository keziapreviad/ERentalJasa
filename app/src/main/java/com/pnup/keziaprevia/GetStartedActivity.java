package com.pnup.keziaprevia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStartedActivity extends AppCompatActivity {

    ImageView emblem_app;
    TextView intro_app;
    Button btn_sign_in, button_cretae_account;
    Animation ttb,btt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        //load animation
        ttb = AnimationUtils.loadAnimation(this,R.anim.ttb);
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);

        btn_sign_in=findViewById(R.id.btn_sign_in);
        button_cretae_account=findViewById(R.id.button_create_account);
        emblem_app = findViewById(R.id.emblem_app);
        intro_app = findViewById(R.id.intro_app);

        //run animation
        emblem_app.startAnimation(ttb);
        intro_app.startAnimation(ttb);
        btn_sign_in.startAnimation(btt);
        button_cretae_account.startAnimation(btt);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosign = new Intent(GetStartedActivity.this,SigninActivity.class);
                startActivity(gotosign);
            }
        });

        button_cretae_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregisterOne = new Intent(GetStartedActivity.this,RegisterOneActivity.class);
                startActivity(gotoregisterOne);
            }
        });
    }
}