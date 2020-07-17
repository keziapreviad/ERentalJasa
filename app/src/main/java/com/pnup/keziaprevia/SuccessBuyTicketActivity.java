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

public class SuccessBuyTicketActivity extends AppCompatActivity {
    Button btn_dashboard,btn_view_ticket;
    Animation app_splash, btt, ttb;
    ImageView icon_success;
    TextView app_title, app_subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        btn_dashboard = findViewById(R.id.btn_dashboard);
        btn_view_ticket = findViewById(R.id.btn_view_ticket);
        icon_success = findViewById(R.id.icon_success);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);

        //load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this,R.anim.ttb);

        //run animation
        icon_success.startAnimation(app_splash);
        app_title.startAnimation(ttb);
        app_subtitle.startAnimation(ttb);
        btn_dashboard.startAnimation(btt);
        btn_view_ticket.startAnimation(btt);

        btn_view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprofile = new Intent(SuccessBuyTicketActivity.this,MyProfileActivity.class);
                startActivity(gotoprofile);
                finish();
            }
        });

        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(SuccessBuyTicketActivity.this,HomeActivity.class);
                startActivity(gotohome);
                finish();
            }
        });
    }
}