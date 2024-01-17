package com.example.pantausampah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pantausampah.MainActivity;
import com.example.pantausampah.R;
import com.example.pantausampah.TukarBeras;
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

public class TukarPoint extends AppCompatActivity {

    ImageView btnBack;
    TextView tvPoint;
    CardView cvBeras, cvmiKardus, cvMinyak, cvMarjan, cvMi5;
    FirebaseUser user;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tukar_point);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("User detail").child(user.getUid());

        btnBack = findViewById(R.id.btnBack);
        cvBeras = findViewById(R.id.beras);
        cvmiKardus = findViewById(R.id.miKardus);
        cvMinyak = findViewById(R.id.minyak);
        cvMarjan = findViewById(R.id.marjan);
        cvMi5 = findViewById(R.id.mi5);
        tvPoint = findViewById(R.id.tvPoint);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try {
                    JSONObject obj = new JSONObject((Map) snapshot.getValue());
                    String dbPoin = obj.getString("poin");
                    tvPoint.setText(dbPoin);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cvBeras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TukarBeras.class);
                startActivity(intent);
            }
        });

        cvmiKardus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TukarMiDus.class);
                startActivity(intent);
            }
        });

        cvMinyak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TukarMinyak.class);
                startActivity(intent);
            }
        });

        cvMarjan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TukarSirop.class);
                startActivity(intent);
            }
        });

        cvMi5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TukarMi5.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}