package com.pnup.keziaprevia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TicketDetailActivity extends AppCompatActivity {
    LinearLayout btn_back_tiga;
    Button btn_buy_ticket;
    TextView title_ticket,location_ticket,short_tos_ticket;
    ImageView header_ticket_detail;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        btn_back_tiga = findViewById(R.id.btn_back_tiga);
        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);

        title_ticket = findViewById(R.id.title_ticket);
        location_ticket = findViewById(R.id.location_ticket);
        short_tos_ticket = findViewById(R.id.short_tos_ticket);

        header_ticket_detail = findViewById(R.id.header_ticket_detail);

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        //mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //menimpa data yang ada dengan yang baru
                title_ticket.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                location_ticket.setText(dataSnapshot.child("lokasi").getValue().toString());
                short_tos_ticket.setText(dataSnapshot.child("short_desc").getValue().toString());
                Picasso.with(TicketDetailActivity.this).load(dataSnapshot.child("url_thumbnail").getValue().toString()).centerCrop().fit().into(header_ticket_detail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gototicketcheckout = new Intent(TicketDetailActivity.this, TiketCheckoutActivity.class);
                gototicketcheckout.putExtra("jenis_tiket",jenis_tiket_baru);
                startActivity(gototicketcheckout);
            }
        });

        btn_back_tiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}