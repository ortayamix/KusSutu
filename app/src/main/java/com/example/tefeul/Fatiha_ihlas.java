package com.example.tefeul;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Fatiha_ihlas extends AppCompatActivity {

    ValueAnimator animator;
    ProgressBar progressBar;
    TextView text_count;
    Button btn_tfl;
    ImageButton btnSkip, btnInfoTfl, btnControl;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatiha_ihlas);

        setTitle("Tefe'ül");

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_left_24);
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true); // back button on bar

        String[] kst = getResources().getStringArray(R.array.kus_sutu);
        ArrayList<Kus_Sutu_Model> quoteList = new ArrayList<>();
        for (int i=0; i<kst.length; i++)
            quoteList.add(new Kus_Sutu_Model(this, null, i+1, false, 0));


        progressBar = findViewById(R.id.progressBar);
        text_count = findViewById(R.id.text_count);
        btnInfoTfl = findViewById(R.id.btnInfoTfl);
        btn_tfl = findViewById(R.id.btn_tfl);
        btnControl = findViewById(R.id.btnControl);

        TextView tv_animate = findViewById(R.id.tv_animate);

        btnInfoTfl.setOnClickListener(view -> {
            AlertDialog.Builder build_tefeul = new AlertDialog.Builder(Fatiha_ihlas.this);
            LayoutInflater inflater = Fatiha_ihlas.this.getLayoutInflater();
            View dialogView_tefeul = inflater.inflate(R.layout.alert_tefeul_about, null);
            build_tefeul.setView(dialogView_tefeul);
            build_tefeul.setTitle("TEFE\'UL NEDİR?");

            TextView tv_detay_open = dialogView_tefeul.findViewById(R.id.tv_detay_open);
            TextView tv_detay = dialogView_tefeul.findViewById(R.id.tv_detay);

            final boolean[] detay_open = {true};

            tv_detay_open.setOnClickListener(view1 -> {
                if (detay_open[0]) {
                    detay_open[0] = false;
                    tv_detay.setVisibility(View.VISIBLE);
                    tv_detay_open.setText(getResources().getString(R.string.tefeul_detay_hide));
                } else {
                    detay_open[0] = true;
                    tv_detay.setVisibility(View.GONE);
                    tv_detay_open.setText(getResources().getString(R.string.tefeul_detay_open));
                }
            });

            //build_tefeul.setMessage(R.string.tefeul_nedir);

            AlertDialog alertDialog = build_tefeul.create();
            alertDialog.show();

        });

        btn_tfl.setVisibility(View.INVISIBLE);
        btn_tfl.setOnClickListener(v -> {
            Intent intent_tfl = new Intent(Fatiha_ihlas.this, Kus_Sutu_display.class);
            intent_tfl.putExtra("tefeul", true);

            /*Random randomNumber = new Random();
            int slc = randomNumber.nextInt(quoteList.size());

            ValueAnimator vAnimator = ValueAnimator.ofInt(0, slc+1);
            vAnimator.addUpdateListener(valueAnimator -> {
                tv_animate.setText(valueAnimator.getAnimatedValue().toString());
            });

            vAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    intent_tfl.putExtra("tefeul_no", slc);
                    startActivity(intent_tfl);
                    finish();
                    super.onAnimationEnd(animation);
                }
            });
            vAnimator.setDuration((long) (slc*2));
            vAnimator.start();*/

            startActivity(intent_tfl);
            finish();
        });

        final Handler handler = new Handler();

        final boolean[] play = {true};
        final boolean[] pause = {false};
        final boolean[] restart = {false};

        Runnable my_run = new Runnable() {
            @Override
            public void run() {
                if (i <= 20) { //20sn
                    //text_count.setText("" + i);
                    progressBar.setProgress(i);
                    i++;
                    handler.postDelayed(this, 1000);
                } else {
                    btn_tfl.setVisibility(View.VISIBLE);
                    text_count.setVisibility(View.INVISIBLE);
                    handler.removeCallbacks(this);
                    pause[0] = false;
                    restart[0] = true;
                    btnControl.setImageResource(R.drawable.ic_baseline_restart_alt_36);
                }
            }
        };

        btnControl.setOnClickListener(view -> {
            if (play[0]) {
                play[0] = false;
                pause[0] = true;
                restart[0] = false;
                btnControl.setImageResource(R.drawable.ic_baseline_pause_36);
                handler.postDelayed(my_run, 1000);
            }
            else if (pause[0]) {
                handler.removeCallbacks(my_run);
                play[0] = true;
                pause[0] = false;
                restart[0] = false;
                btnControl.setImageResource(R.drawable.ic_baseline_play_arrow_36);
            }
            else if (restart[0]) {
                i = 0;
                btn_tfl.setVisibility(View.INVISIBLE);
                text_count.setVisibility(View.VISIBLE);
                play[0] = false;
                pause[0] = true;
                restart[0] = false;
                btnControl.setImageResource(R.drawable.ic_baseline_pause_36);
                handler.postDelayed(my_run, 1000);
            }
        });


        btnSkip = findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(v -> {
            handler.removeCallbacksAndMessages(null);
            progressBar.setProgress(20);
            btn_tfl.setVisibility(View.VISIBLE);
            text_count.setVisibility(View.INVISIBLE);
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}