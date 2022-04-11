package com.example.tefeul;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class Hakkimizda extends AppCompatActivity {

    ConstraintLayout const_contact, const_website, const_website2, const_rate_us;
    TextView tv_about_us;

    @SuppressLint("QueryPermissionsNeeded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hakkimizda);

        setTitle("Hakkımızda");

        tv_about_us = findViewById(R.id.tv_about_us);
        tv_about_us.setOnClickListener(view -> {
            AlertDialog.Builder build_tefeul = new AlertDialog.Builder(Hakkimizda.this);
            LayoutInflater inflater = Hakkimizda.this.getLayoutInflater();
            View dialogView_tefeul = inflater.inflate(R.layout.alert_hakkimizda, null);
            build_tefeul.setView(dialogView_tefeul);
            build_tefeul.setTitle("HAKKIMIZDA");

            TextView tv_hakkimizda = dialogView_tefeul.findViewById(R.id.tv_hakkimizda);

            tv_hakkimizda.setText(getResources().getString(R.string.hakkimizda));

            //build_tefeul.setMessage(R.string.tefeul_nedir);

            AlertDialog alertDialog = build_tefeul.create();
            alertDialog.show();
        });

        const_contact = findViewById(R.id.const_contact);
        const_website = findViewById(R.id.const_website);
        const_website2 = findViewById(R.id.const_website2);
        const_rate_us = findViewById(R.id.const_rate_us);

        const_contact.setOnClickListener(view -> {
            Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
            selectorIntent.setData(Uri.parse("mailto:"));

            final Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"aig.medya.kitap@gmail.com"});
            //emailIntent.putExtra(Intent.EXTRA_SUBJECT, "The subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Görüşleriniz bizim için değerli");
            emailIntent.setSelector( selectorIntent );

            startActivity(Intent.createChooser(emailIntent, "Gönderiliyor..."));
        });

        Intent intent = new Intent(Hakkimizda.this, WebviewAct.class);
        const_website.setOnClickListener(view -> {
            intent.putExtra("link", "http://ahmedihsan.com");
            startActivity(intent);
        });

        const_website2.setOnClickListener(view -> {
            intent.putExtra("link", "https://www.refrefdergisi.com");
            startActivity(intent);
        });

        const_rate_us.setOnClickListener(view -> {
            //
        });

    }
}