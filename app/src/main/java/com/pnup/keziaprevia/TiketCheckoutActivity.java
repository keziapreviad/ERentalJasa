package com.pnup.keziaprevia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TiketCheckoutActivity extends AppCompatActivity {
    LinearLayout btn_back_lima;
    Button btn_pay_ticket, btnmines, btnplus;
    TextView textjumlahtiket, texttotalharga, textMyBalance, nama_wisata,lokasi,ketentuan;
    Integer valueJumlahTiket = 1;
    Integer myBalance = 0;
    Integer valuetotalHarga = 0;
    Integer valuehargaTiket = 0;
    ImageView notice_uang;
    Integer sisa_balance = 0;

    DatabaseReference reference,reference2,reference3,reference4;

    String USERNAME_KEY="usernamekey";
    String username_key = "";
    String username_key_new = "";

    String date_wisata = "";
    String time_wisata = "";

    Integer nomor_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket_checkout);

        getUsernameLocal();

        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        btn_back_lima = findViewById(R.id.btn_back_lima);
        btn_pay_ticket = findViewById(R.id.btn_pay_ticket);
        btnmines = findViewById(R.id.btnmines);
        btnplus = findViewById(R.id.btnplus);
        notice_uang = findViewById(R.id.notice_uang);

        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        textjumlahtiket = findViewById(R.id.textjumlahtiket);
        texttotalharga = findViewById(R.id.texttotalharga);

        textMyBalance = findViewById(R.id.textMyBalance);

        //setting value baru untuk beberapa komponen
        textjumlahtiket.setText(valueJumlahTiket.toString());

        //secara default, btnmines akan dihide
        btnmines.animate().alpha(0).setDuration(300).start();
        btnmines.setEnabled(false);
        notice_uang.setVisibility(View.GONE);

        //mengambil data user dari firebase
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myBalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textMyBalance.setText("Rp. "+myBalance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //menimpa data yang ada dengan yang baru
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());

                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();

                valuehargaTiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());

                valuetotalHarga=valuehargaTiket*valueJumlahTiket;
                texttotalharga.setText("Rp.  "+valuetotalHarga+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueJumlahTiket+=1;
                textjumlahtiket.setText(valueJumlahTiket.toString());
                if(valueJumlahTiket>1){
                    btnmines.animate().alpha(1).setDuration(300).start();
                    btnmines.setEnabled(true);
                }

                valuetotalHarga=valuehargaTiket*valueJumlahTiket;
                texttotalharga.setText("Rp.  "+valuetotalHarga+"");

                if(valuetotalHarga>myBalance){
                    btn_pay_ticket.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_pay_ticket.setEnabled(false);
                    textMyBalance.setTextColor(Color.parseColor("#D1206B"));
                    notice_uang.setVisibility(View.VISIBLE);
                }
            }
        });

        btnmines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueJumlahTiket-=1;
                textjumlahtiket.setText(valueJumlahTiket.toString());
                if(valueJumlahTiket<2){
                    btnmines.animate().alpha(0).setDuration(300).start();
                    btnmines.setEnabled(false);
                }

                valuetotalHarga=valuehargaTiket*valueJumlahTiket;
                texttotalharga.setText("Rp.  "+valuetotalHarga+"");

                if(valuetotalHarga<=myBalance){
                    btn_pay_ticket.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_pay_ticket.setEnabled(true);
                    textMyBalance.setTextColor(Color.parseColor("#203DD1"));
                    notice_uang.setVisibility(View.GONE);
                }
            }
        });

        btn_pay_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String convNomorTransaksi = String.valueOf(nomor_transaksi);
                final String hasil = nama_wisata.getText().toString()+convNomorTransaksi;

                // menyimpan data user kepada firebase dan membuat tabel baru "MyTickets"
                reference3 = FirebaseDatabase.getInstance()
                        .getReference().child("MyTickets")
                        .child(username_key_new).child(nama_wisata.getText().toString());
                Log.d("Failure ",hasil);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(hasil);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(valueJumlahTiket.toString());
                        reference3.getRef().child("date_wisata").setValue(getLocalDate());
                        reference3.getRef().child("time_wisata").setValue(time_wisata);

                        Intent gotosuccessticket = new
                                Intent(TiketCheckoutActivity.this,SuccessBuyTicketActivity.class);
                        startActivity(gotosuccessticket);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //update sisa balance pada firebase
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_balance = myBalance - valuetotalHarga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btn_back_lima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }

    public String getLocalDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
}