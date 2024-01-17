package com.example.pantausampah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    int poin;
    String nama;

    Button btnLogout;
    TextView tvHalo, tvHalo2, btnTukarPoin, btnRiwayat, tvPoin;
    CardView cvBerita;
    ImageView imgFoto;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("User detail").child(user.getUid());

        btnLogout = findViewById(R.id.logout);
        btnTukarPoin = findViewById(R.id.tukarPoin);
        btnRiwayat = findViewById(R.id.riwayat);
        tvHalo = findViewById(R.id.tvHalo);
        tvHalo2 = findViewById(R.id.tvHalo2);
        tvPoin = findViewById(R.id.poin);
        imgFoto = findViewById(R.id.imageFoto);
        cvBerita = findViewById(R.id.berita);


        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    try {
                        JSONObject obj = new JSONObject((Map) snapshot.getValue());
                        String dbPoin = obj.getString("poin");
                        String dbNama = obj.getString("nama");
                        poin = Integer.parseInt(dbPoin);
                        nama = dbNama;
                        tvHalo2.setText(nama);
                        tvPoin.setText(String.valueOf(poin));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            tvHalo.setText(user.getEmail());
//            tvHalo2.setText(user.getEmail());
            imgFoto.setImageURI(user.getPhotoUrl());
        }

        reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

//                    JSONObject obj = new JSONObject((Map) snapshot.getValue());
//                    try {
//                        String dbPoin = obj.getString("poin");
//                        poin = Integer.parseInt(dbPoin);
//                        tvPoin.setText(dbPoin);
//                    } catch (JSONException e) {
////                        throw new RuntimeException(e);
//                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        cvBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bacaBerita();
            }
        });

        btnTukarPoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TukarPoint.class);
                startActivity(intent);
            }
        });

        btnRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Riwayat.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void bacaBerita(){

        String id = user.getUid();
        int tambahpoin = 125;
        String nama = user.getDisplayName();

        String poinBaru = Integer.toString(poin + tambahpoin);

        DataClass dataClass = new DataClass(id, poinBaru, nama);

        FirebaseDatabase.getInstance().getReference("User detail").child(id)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Mendapatkan 125 poin",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}