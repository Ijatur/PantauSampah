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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    int poin;

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

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        btnLogout = findViewById(R.id.logout);
        btnTukarPoin = findViewById(R.id.tukarPoin);
        btnRiwayat = findViewById(R.id.riwayat);
        tvHalo = findViewById(R.id.tvHalo);
        tvHalo2 = findViewById(R.id.tvHalo2);
        tvPoin = findViewById(R.id.poin);
        imgFoto = findViewById(R.id.imageFoto);
        cvBerita = findViewById(R.id.berita);

        user = mAuth.getCurrentUser();

        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            tvHalo.setText(user.getEmail());
            tvHalo2.setText(user.getDisplayName());
            imgFoto.setImageURI(user.getPhotoUrl());
            mDatabase.child("User detail").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"asdasasssssd", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            JSONObject obj = new JSONObject(valueOf(task.getResult().getValue()));
                            String  data = obj.getString("poin");
                            poin = Integer.parseInt(data);
                            tvPoin.setText(Integer.toString(poin));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }

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
        String poin = "130";

        DataClass dataClass = new DataClass(id, poin);

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