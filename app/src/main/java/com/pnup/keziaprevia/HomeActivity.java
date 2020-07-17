package com.pnup.keziaprevia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    LinearLayout btn_gear,btn_doc,btn_guide,btn_transport;
    CircleView btn_to_profile;
    ImageView photo_home_user,semeru,rinjani,jayawijaya;
    TextView user_balance,nama_lengkap,bio;

    DatabaseReference reference;

    String USERNAME_KEY="usernamekey";
    String username_key="";
    String username_key_new="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //langsung mulai untuk mangambil username lokal
        getUsernameLocal();

        photo_home_user = findViewById(R.id.photo_home_user);
        user_balance = findViewById(R.id.user_balance);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);

        btn_gear = findViewById(R.id.btn_gear);
        btn_doc = findViewById(R.id.btn_doc);
        btn_guide = findViewById(R.id.btn_guide);
        btn_transport = findViewById(R.id.btn_transport);
        btn_to_profile = findViewById(R.id.btn_to_profile);

        semeru = findViewById(R.id.semeru);
        rinjani = findViewById(R.id.rinjani);
        jayawijaya = findViewById(R.id.jayawijaya);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                user_balance.setText("Rp. "+dataSnapshot.child("user_balance").getValue().toString());

                Picasso.with(HomeActivity.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_home_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprofile = new Intent(HomeActivity.this,MyProfileActivity.class);
                startActivity(gotoprofile);
            }
        });

        btn_gear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotogear = new Intent(HomeActivity.this, GearActivity.class);
                startActivity(gotogear);
            }
        });

        btn_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodoc = new Intent(HomeActivity.this, DocumentationActivity.class);
                startActivity(gotodoc);
            }
        });

        btn_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoguide = new Intent(HomeActivity.this, GuideActivity.class);
                startActivity(gotoguide);
            }
        });

        btn_transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gototransport = new Intent(HomeActivity.this, TransportationActivity.class);
                startActivity(gototransport);
            }
        });

        semeru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotototicket = new Intent(HomeActivity.this, TicketDetailActivity.class);
                gotototicket.putExtra("jenis_tiket","Semeru");
                startActivity(gotototicket);
            }
        });

        rinjani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotototicket = new Intent(HomeActivity.this, TicketDetailActivity.class);
                gotototicket.putExtra("jenis_tiket","Rinjani");
                startActivity(gotototicket);
            }
        });

        jayawijaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotototicket = new Intent(HomeActivity.this, TicketDetailActivity.class);
                gotototicket.putExtra("jenis_tiket","Jayawijaya");
                startActivity(gotototicket);
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}