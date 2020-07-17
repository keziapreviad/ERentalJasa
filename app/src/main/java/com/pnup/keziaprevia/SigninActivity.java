package com.pnup.keziaprevia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninActivity extends AppCompatActivity {
    TextView btn_new_account;
    Button btn_sign_in;
    EditText xusername, xpassword;

    DatabaseReference reference;

    String USERNAME_KEY = "username_key";
    String username_key="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        btn_new_account =findViewById(R.id.btn_new_account);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);

        final String USERNAME_KEY="usernamekey";
        final String username_key = "";

        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregisterOne = new Intent(SigninActivity.this,RegisterOneActivity.class);
                startActivity(gotoregisterOne);
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ubah state menjadi loading
                btn_sign_in.setEnabled(false);
                btn_sign_in.setText("Loading ...");

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                if(username.isEmpty() && password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Username dan password anda kosong",Toast.LENGTH_SHORT).show();
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");
                }

                else if(username.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Username anda kosong",Toast.LENGTH_SHORT).show();
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");

                }
                else{
                    if(password.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Password anda kosong",Toast.LENGTH_SHORT).show();
                        btn_sign_in.setEnabled(true);
                        btn_sign_in.setText("SIGN IN");
                    }
                    else{
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);

                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){

                                    //data password diambil dari firebase database
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    //validasi password dengan password firebase
                                    if(password.equals(passwordFromFirebase)){
                                        // simpan username (key) kepada local
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, xusername.getText().toString());
                                        editor.apply();

                                        //pindah activity
                                        Intent gotohome = new Intent(SigninActivity.this, HomeActivity.class);
                                        startActivity(gotohome);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Password salah",Toast.LENGTH_SHORT).show();
                                        btn_sign_in.setEnabled(true);
                                        btn_sign_in.setText("SIGN IN");
                                    }
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Username tidak ada",Toast.LENGTH_SHORT).show();
                                    btn_sign_in.setEnabled(true);
                                    btn_sign_in.setText("SIGN IN");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(),"Database Error",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        });
    }
}