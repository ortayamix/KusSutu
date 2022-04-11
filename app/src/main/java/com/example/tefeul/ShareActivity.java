package com.example.tefeul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.widget.TextViewCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ShareActivity extends AppCompatActivity {

    ActionBar actionBar;

    AutoCompleteTextView autoCompleteTV;

    DisplayMetrics displayMetrics;
    int dpWidth, dpHeight;

    RelativeLayout rel_share_1;
    ViewGroup.LayoutParams params_tv, params_im, params;
    int widthPixels, heightPixels;

    ImageView im_share_0;
    ImageView im_share_1, im_share_2;
    TextView tv_share_0, tv_share_1, tv_share_2;

    ImageButton im_btn_share_to;

    View included, included2, included1_1, included2_1;
    ImageView imgV;
    ImageButton imbtn1, imbtn2, imbtn3, imbtn4, imbtn5, imbtn6;
    TextView tv, tv_page_nmbr;

    boolean only_text_bool = false;

    ViewStub layout_stub0, layout_stub0_1, layout_stub1, layout_stub2;
    View inflated0, inflated0_1, inflated1, inflated2;

    File imagePath;

    public static final String MyPREFERENCES__DISPLAY3 = "MyPrefs_display" ;
    SharedPreferences sharedpreferences_display3;
    SharedPreferences.Editor editor_display3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share2);

        sharedpreferences_display3 = this.getSharedPreferences(MyPREFERENCES__DISPLAY3, MODE_PRIVATE);
        editor_display3 = sharedpreferences_display3.edit();

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_left_24);
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true); // HIDE back button on bar

        im_btn_share_to = findViewById(R.id.im_btn_share_to);

        layout_stub1 = findViewById(R.id.layout_stub1);
        layout_stub1.setInflatedId(R.id.activity1);
        layout_stub1.setLayoutResource(R.layout.activity_kus_sutu);
        inflated1 = layout_stub1.inflate();

        layout_stub2 = findViewById(R.id.layout_stub2);
        layout_stub2.setInflatedId(R.id.activity2);
        layout_stub2.setLayoutResource(R.layout.infinite_pager_quote);
        inflated2 = layout_stub2.inflate();

        imbtn1 = findViewById(R.id.im_btn_list_arama_fav);
        imbtn3 = findViewById(R.id.im_btn_share_display);
        imbtn4 = findViewById(R.id.im_btn_tefeul_display);
        imbtn5 = findViewById(R.id.im_btn_fav_display);
        imbtn6 = findViewById(R.id.im_btn_backgr);
        imbtn1.setVisibility(View.INVISIBLE);

        imbtn3.setVisibility(View.INVISIBLE);
        imbtn4.setVisibility(View.INVISIBLE);
        imbtn5.setVisibility(View.INVISIBLE);
        imbtn6.setVisibility(View.INVISIBLE);

        imgV = findViewById(R.id.imageView);
        tv = findViewById(R.id.tv_display);
        tv_page_nmbr = findViewById(R.id.tv_page_nmbr);

        int picture = getIntent().getExtras().getInt("picture");
        imgV.setImageResource(picture);

        String get_str = getIntent().getStringExtra("quote_to_share");
        tv.setText(get_str);

        //Typeface typeface2 = ResourcesCompat.getFont(ShareActivity.this, sharedpreferences_display3.getInt("background_font", R.font.ubuntu_medium));
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), sharedpreferences_display3.getString("background_font", "ubuntu_medium.ttf"));
        tv.setTypeface(typeface2);


        int get_no = getIntent().getIntExtra("quote_no_to_share", 1);
        tv.setTextColor(Color.WHITE);   //beyaz dışında renk varsa getInt yapılmalı
        tv_page_nmbr.setText(get_no+"");


        rel_share_1 = findViewById(R.id.rel_share_1);
        /////////////////////////////////////////////// çözünürlük
        WindowManager w = getWindowManager();
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        widthPixels = metrics.widthPixels;
        heightPixels = metrics.heightPixels;
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
            } catch (Exception ignored) {}
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17)
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                widthPixels = realSize.x;
                heightPixels = realSize.y;
            } catch (Exception ignored) {}
        /////////////////////////////////////////////////
        System.out.println("cozunurluk: "+widthPixels+"-"+heightPixels);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            tv.setAutoSizeTextTypeUniformWithConfiguration(1, 100, 2, TypedValue.COMPLEX_UNIT_DIP);
        else
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tv, 1, 100, 1, TypedValue.COMPLEX_UNIT_DIP);

        params = rel_share_1.getLayoutParams();
        params.width = widthPixels;
        params.height = heightPixels;

        TextInputLayout txInptLay = findViewById(R.id.txInptLay);
        autoCompleteTV = findViewById(R.id.autoCompleteTV);

        String[] coznrlk_str = new String[]{
                "Whatsapp TamEkran",
                "Instagram 9:16",
                "Facebook 1:1",
                "Twitter 3:2",
                "Sadece yazı"
        };

        int[] coznrlk_int = new int[]{
                R.drawable.dropd_whatsapp_32,
                R.drawable.dropd_instagram_32,
                R.drawable.dropd_facebook_32,
                R.drawable.dropd_twitter_32,
                0
        };

        //ArrayAdapter adapter_DropDown = new my_DropdownAdapter(this, R.layout.dropdown_item, coznrlk_linkedHash);
        ArrayAdapter adapter_DropDown = new ArrayAdapter(this, android.R.layout.simple_list_item_1, coznrlk_str);
        //ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.activity_list_item, coznrlk_linkedHash);
        autoCompleteTV.setAdapter(adapter_DropDown);
        autoCompleteTV.setOnItemClickListener((adapterView, view, position, l) -> {
            txInptLay.setStartIconDrawable(coznrlk_int[position]);
            only_text_bool = false;
            imgV.setVisibility(View.VISIBLE);
            tv.setTextColor(Color.WHITE);   //beyaz dışında renk varsa getInt yapılmalı
            tv_page_nmbr.setVisibility(View.VISIBLE);
            params = rel_share_1.getLayoutParams();
            params.width = widthPixels;
            if (position == 0) {
                params.height = heightPixels;
            } else if (position == 1) {
                params.height = widthPixels*16/9;
            } else if (position == 2) {
                params.height = widthPixels;
            } else if (position == 3) {
                params.height = widthPixels*3/2;
            } else if (position == 4) {
                only_text_bool = true;
                imgV.setVisibility(View.INVISIBLE);
                tv.setTextColor(Color.parseColor("#10A3CD"));
                tv_page_nmbr.setVisibility(View.INVISIBLE);
            }
            rel_share_1.setLayoutParams(params);
        });

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String time = formatter.format(new Date());

        im_btn_share_to.setOnClickListener(view -> {

            if (!only_text_bool) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    tv.setAutoSizeTextTypeUniformWithConfiguration(1, 100, 2, TypedValue.COMPLEX_UNIT_DIP);
                } else {
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tv, 1, 100, 1, TypedValue.COMPLEX_UNIT_DIP);
                }

                Bitmap bitmap = takeScreenshot();
                try {
                    File file = new File(getApplicationContext().getExternalCacheDir(), File.separator + "my_image.jpg");
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);
                    final Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);

                    intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/jpg");

                    startActivity(Intent.createChooser(intent, "Share image via"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, tv.getText()+ " \n\nAhmed İhsan GENÇ, Kuş Sütü ("+tv_page_nmbr.getText()+".)");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }

    public Bitmap takeScreenshot() {
        //View rootView = rel_share_1;
        /*LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_kus_sutu, null);
        View rootView = v.findViewById(R.id.const_kussutu);*/

        View rootView = rel_share_1;
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}