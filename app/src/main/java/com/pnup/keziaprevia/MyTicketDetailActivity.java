package com.pnup.keziaprevia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyTicketDetailActivity extends AppCompatActivity {
    LinearLayout btn_back_enam;
    TextView xdate_wisata, xtime_wisata, xketentuan, xnama_wisata, xlokasi;

    DatabaseReference reference;

    String USERNAME_KEY="usernamekey";
    String username_key="";
    String username_key_new="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket_detail);

        getUsernameLocal();

        Bundle bundle = getIntent().getExtras();
        final String nama_wisata_baru = bundle.getString("nama_wisata");

        btn_back_enam = findViewById(R.id.btn_back_enam);

        xdate_wisata = findViewById(R.id.xxdate_wisata);
        xtime_wisata = findViewById(R.id.xxtime_wisata);
        xketentuan = findViewById(R.id.xxketentuan);
        xlokasi = findViewById(R.id.xxlokasi);
        xnama_wisata = findViewById(R.id.xxnama_wisata);

        Log.d("Failure ",nama_wisata_baru);

        reference = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key_new).child(nama_wisata_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xnama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                xlokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                xtime_wisata.setText(dataSnapshot.child("time_wisata").getValue().toString());
                xdate_wisata.setText(dataSnapshot.child("date_wisata").getValue().toString());
                xketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_back_enam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}