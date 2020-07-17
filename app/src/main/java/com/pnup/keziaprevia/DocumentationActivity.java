package com.pnup.keziaprevia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DocumentationActivity extends AppCompatActivity {

    Button back_from_doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);

        back_from_doc = findViewById(R.id.btn_back_from_doc);
        back_from_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}