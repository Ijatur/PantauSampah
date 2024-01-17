package com.example.pantausampah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class TukarMinyak extends AppCompatActivity {

    private int poinSaya;

    ImageView btnBack;
    TextView tvSisaPoin;
    Button btnTukar;
    FirebaseUser user;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tukar_minyak);

        btnBack = findViewById(R.id.btnBack);
        btnTukar = findViewById(R.id.btnTukarMinyak);
        tvSisaPoin = findViewById(R.id.sisaPoinMinyak);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("User detail").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try {
                    JSONObject obj = new JSONObject((Map) snapshot.getValue());
                    String dbPoin = obj.getString("poin");
                    poinSaya = Integer.parseInt(dbPoin);
                    hitungPoin();
//                    tvSisaPoin.setText(String.valueOf(poinSaya));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnTukar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (poinSaya<1000){
                    Toast.makeText(getApplicationContext(),R.string.poin_tidak_mencukupi, Toast.LENGTH_SHORT).show();
                } else {
                    int poinBaru = poinSaya - 1000;
                    reference.child("poin").setValue(poinBaru);

                    Intent intent = new Intent(getApplicationContext(), SuccessTukarPoint.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void hitungPoin(){
        if (poinSaya<1000){
            tvSisaPoin.setText(R.string.poin_tidak_mencukupi);
        } else {
            int poinBaru = poinSaya - 1000;
            String sisaPoin = "Sisa Poin " + String.valueOf(poinBaru);
            tvSisaPoin.setText(sisaPoin);
        }
    }
}