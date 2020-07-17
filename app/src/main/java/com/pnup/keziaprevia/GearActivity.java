package com.pnup.keziaprevia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GearActivity extends AppCompatActivity {
    Button btn_back_from_gear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gear);

        btn_back_from_gear = findViewById(R.id.btn_back_from_gear);

        btn_back_from_gear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}