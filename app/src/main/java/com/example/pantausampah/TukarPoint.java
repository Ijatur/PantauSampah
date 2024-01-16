package com.example.pantausampah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.pantausampah.MainActivity;
import com.example.pantausampah.R;
import com.example.pantausampah.TukarBeras;

public class TukarPoint extends AppCompatActivity {

    ImageView btnBack;
    CardView cvBeras, cvmiKardus, cvMinyak, cvMarjan, cvMi5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tukar_point);

        btnBack = findViewById(R.id.btnBack);
        cvBeras = findViewById(R.id.beras);
        cvmiKardus = findViewById(R.id.miKardus);
        cvMinyak = findViewById(R.id.minyak);
        cvMarjan = findViewById(R.id.marjan);
        cvMi5 = findViewById(R.id.mi5);

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