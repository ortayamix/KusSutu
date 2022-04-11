package com.example.tefeul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button_tfl, btn_normal_oku, button_arama, button_favoriler;
    Intent intent_fth, intent_normal, intent_arama, intent_favori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_tfl = findViewById(R.id.button_tfl);
        btn_normal_oku = findViewById(R.id.btn_normal_oku);
        button_arama = findViewById(R.id.button_arama);
        button_favoriler = findViewById(R.id.button_favoriler);

        button_tfl.setOnClickListener(v -> {
            intent_fth = new Intent(MainActivity.this, Fatiha_ihlas.class);
            intent_fth.putExtra("tefeul", true);
            startActivity(intent_fth);
        });
        btn_normal_oku.setOnClickListener(v -> {
            intent_normal = new Intent(MainActivity.this, Kus_Sutu_display.class);
            intent_normal.putExtra("normal okuma", true);
            startActivity(intent_normal);
        });

        button_arama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_arama = new Intent(MainActivity.this, Arama_Fav_Activity.class);
                intent_arama.putExtra("arama_act", true);
                startActivity(intent_arama);
            }
        });

        button_favoriler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_favori = new Intent(MainActivity.this, Arama_Fav_Activity.class);
                intent_favori.putExtra("favori_act", true);
                startActivity(intent_favori);
            }
        });

    }
}