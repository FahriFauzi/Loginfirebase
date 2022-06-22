package com.example.loginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Register extends AppCompatActivity {

    //membuat object ke firebasee realtime database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl
            ("https://console.firebase.google.com/project/masukdaftar-b6c54/database/masukdaftar-b6c54-default-rtdb/data/~2F");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText fullname = findViewById(R.id.fullname);
        final EditText email = findViewById(R.id.email);
        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final EditText conpassword = findViewById(R.id.conpassword);

        final Button registerBtn = findViewById(R.id.daftarBtn);
        final TextView loginNowbtn = findViewById(R.id.loginNow);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ambil data dari edit text ke string
                final String fullnameTxt = fullname.getText().toString();
                final String emailTxt = email.getText().toString();
                final String phoneTxt = phone.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String connpasswordTxt = conpassword.getText().toString();

                //cek jika user mengisi semua laman sebelum mengirim data ke firebaseeeeee
                if(fullnameTxt.isEmpty() || emailTxt.isEmpty() || phoneTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText( Register.this, "Tolong isi semua yaa", Toast.LENGTH_SHORT).show();
                }

                //cek kesamaan password
                // pami tidak sama muncul toast
                else if(passwordTxt.equals(connpasswordTxt)) {
                    Toast.makeText(Register.this, "Password tidak sama uhuhu", Toast.LENGTH_SHORT).show();
                }
                else{

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //cek no sudah terdaftar belum sebelumnya

                            if(snapshot.hasChild(phoneTxt)){
                                Toast.makeText(Register.this, "nomber sudah terdaftar yaah", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //kirim data ke firebaseeee
                                //phone no sebagai unique idetifier
                                databaseReference.child("users").child(phoneTxt).child("fullname").setValue(fullnameTxt);
                                databaseReference.child("users").child(phoneTxt).child("email").setValue(emailTxt);
                                databaseReference.child("users").child(phoneTxt).child("password").setValue(passwordTxt);

                                //munculkan pesan berhasil
                                Toast.makeText(Register.this, "Pendaftaran akun berhasil yeeee.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });
        loginNowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}