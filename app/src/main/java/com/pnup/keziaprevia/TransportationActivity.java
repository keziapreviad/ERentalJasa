package com.pnup.keziaprevia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TransportationActivity extends AppCompatActivity {

    Button btn_back_from_transport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);

        btn_back_from_transport = findViewById(R.id.btn_back_from_transport);

        btn_back_from_transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}