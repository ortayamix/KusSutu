package com.example.tefeul;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.core.widget.TextViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.batoulapps.adhan.CalculationMethod;
import com.batoulapps.adhan.CalculationParameters;
import com.batoulapps.adhan.Coordinates;
import com.batoulapps.adhan.Madhab;
import com.batoulapps.adhan.PrayerTimes;
import com.batoulapps.adhan.data.DateComponents;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;

public class Kus_Sutu_display extends AppCompatActivity {

    ActionBar actionBar;

    ImageButton im_btn_list_arama_fav, im_btn_share_display, im_btn_tefeul_display, im_btn_fav_display, im_btn_fav_archieve, im_btn_backgr_display;
    boolean tefeul_bool = false;
    boolean arama_bool_KS = false;
    boolean fav_bool_KS = false;

    float say = -3;

    ViewPager pager;
    My_adapter_infinite my_adapter;

    int positionX = 0;

    TextView tv_title, tv_author, tv_page_nmbr, txt_logo, txt_author, txt_author_visibility;
    ArrayList<Kus_Sutu_Model> quoteList, quoteList_fav;
    ArrayList<Integer> noList_fav;
    ImageView imageView;

    GridView gridview;
    DisplayMetrics displayMetrics;
    int dpWidth, dpHeight;
    RelativeLayout rel_backgr_font, rel_bottom;
    ViewStub stub_display;
    View stub_view;

    boolean back_display_bool = true;

    LinearLayout lin_out_boyut;
    ConstraintLayout const_kussutu, const_manuel_auto;
    ViewGroup.LayoutParams params;
    int widthPixels, heightPixels;
    boolean only_text_bool = false;
    String[] coznrlk_str;
    boolean btnSetting_bool = false;

    MyFontAdapter myFontAdapter;

    String[] kussutu_font;
    Typeface[] typeface_display;
    Typeface typeface2;
    TextInputLayout txInptLay_font, txInptLayShare;
    AutoCompleteTextView autoCompleteTV_font, autoCompleteTVShare;
    ConstraintLayout const_check0, const_check1, const_check2, const_check3, const_share;
    TextView txt_pref_manuel, txt_pref_oto, txt_share;
    ScrollView scroll1, scroll2;
    ImageButton im_btnClose, im_btnSetting, im_btnShare, im_btnShare2, im_btnClose_scroll;

    TextView text_slider_size;
    ImageButton im_btn_minus, im_btn_add;
    Slider slider_size;
    TextView txt_renk_diyalog, txt_bckgr_opacity_dialog;
    String hexColor, fore_hexColor;
    ColorDrawable cd;

    float dX, dY;

    float our_text_size;
    CheckBox chkbx_auto1, chkbx_auto0, chkbx_auto2, chkbx_auto3, chkbx_auto4, chkbx_auto5, chkbx_auto6;

    ImageView im_popup_like;

    ImageFontAdapter im_fnt;

    // for swipe right/left
    int selector;
    int s = 0;

    MenuItem item_notify, item_search_display, item_options, item_text_size;

    Random randomNumber;

    AlarmManager alarmManager;

    public static final String MyPREFERENCES__DISPLAY = "MyPrefs_display" ;
    SharedPreferences sharedpreferences_display;
    SharedPreferences.Editor editor_display;

    public static final String MyPREFERENCES__DISPLAY2 = "MyPrefs_display" ;
    SharedPreferences sharedpreferences_display2;
    SharedPreferences.Editor editor_display2;

    private static final long DOUBLE_CLICK_TIME_DELTA = 300;//milliseconds

    @SuppressLint({"ClickableViewAccessibility", "WrongThread", "SetWorldReadable", "UseCompatLoadingForDrawables", "UseCompatLoadingForColorStateLists"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        //getSupportActionBar().hide(); //hide the title bar
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen

        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //show statusbar (deliberately commented for space above title bar)

        setContentView(R.layout.activity_kus_sutu);

        window_transparent_CUTOUT();
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); // (deliberately commented for space above title bar)

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_left_24);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9919485a")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar);
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(false); // HIDE back button on bar

        //Objects.requireNonNull(actionBar).hide(); //hide the title bar
        //window_transparent_CUTOUT();

        sharedpreferences_display = getSharedPreferences(MyPREFERENCES__DISPLAY, MODE_PRIVATE);
        editor_display = sharedpreferences_display.edit();

        hexColor = sharedpreferences_display.getString("color_picker_value", "#FFFFFF");
        fore_hexColor = sharedpreferences_display.getString("foreground_color_picker_value", "#00FFFFFF");

        our_text_size = (int) (sharedpreferences_display.getFloat("text_size", 30));


        const_kussutu = findViewById(R.id.const_kussutu);
        params = const_kussutu.getLayoutParams();
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

        im_btn_list_arama_fav = findViewById(R.id.im_btn_list_arama_fav);
        im_btn_share_display = findViewById(R.id.im_btn_share_display);
        im_btn_tefeul_display = findViewById(R.id.im_btn_tefeul_display);
        im_btn_fav_display = findViewById(R.id.im_btn_fav_display);
        im_btn_fav_archieve = findViewById(R.id.im_btn_fav_archieve);
        im_btn_backgr_display = findViewById(R.id.im_btn_backgr);
        //toolbar = findViewById(R.id.toolbar);
        rel_bottom = findViewById(R.id.rel_bottom);

        ImageButton imgbtn_actionBar = findViewById(R.id.imgbtn_actionBar);
        imgbtn_actionBar.setOnClickListener(view -> {
            my_notification();
        });
        TextView tv_actionBar = findViewById(R.id.tv_actionBar);
        tv_actionBar.setOnClickListener(view -> {
            startActivity(new Intent(Kus_Sutu_display.this, Hakkimizda.class));
            /*AlertDialog.Builder sayfa_builder = new AlertDialog.Builder(Kus_Sutu_display.this);

            Objects.requireNonNull(actionBar).hide(); //hide the title bar
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
            window_transparent_CUTOUT();

            LayoutInflater inflater = Kus_Sutu_display.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.alert_settings, null);
            sayfa_builder.setView(dialogView);

            ConstraintLayout const_set_notify = dialogView.findViewById(R.id.const_set_notify);
            ConstraintLayout const_set_about_us = dialogView.findViewById(R.id.const_set_about_us);

            const_set_notify.setOnClickListener(view13 -> {
            });
            const_set_about_us.setOnClickListener(view13 -> {
            });

            AlertDialog alertDialog = sayfa_builder.create();
            alertDialog.show();

            const_set_notify.setOnClickListener(view13 -> {
                my_notification();
            });

            const_set_about_us.setOnClickListener(view13 -> {
                startActivity(new Intent(Kus_Sutu_display.this, Hakkimizda.class));
            });

            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            */
        });


        displayMetrics = getResources().getDisplayMetrics();
        dpWidth = (int) (displayMetrics.widthPixels / displayMetrics.density);
        dpHeight = (int) (displayMetrics.heightPixels / displayMetrics.density);


        ////rel_backgr_font = findViewById(R.id.rel_backgr_font);
        //ViewGroup.LayoutParams layoutParams2 = rel_backgr_font.getLayoutParams();
        //layoutParams2.height = dpHeight;

        randomNumber = new Random();

        //setSupportActionBar(toolbar);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            //alarm_prepare_set(alarmManager);
        }

        String[] kus_sutleri = getResources().getStringArray(R.array.kus_sutu);
        quoteList = new ArrayList<>();
        quoteList_fav = new ArrayList<>();
        noList_fav = new ArrayList<>();

        for (int i=0; i<kus_sutleri.length; i++) {
            String quote = kus_sutleri[i];
            Kus_Sutu_Model kus_sutu_quotes = new Kus_Sutu_Model(this, quote, i+1, sharedpreferences_display.getBoolean("fav_"+i, false), sharedpreferences_display.getInt("last_saved_position", 0));
            //kus_sutu_quotes.isFavorite();
            quoteList.add(kus_sutu_quotes);

            if (sharedpreferences_display.getBoolean("fav_"+i, false)) {
                quoteList_fav.add(kus_sutu_quotes);
                noList_fav.add(i);
            }
        }

        //display = findViewById(R.id.display);

        tefeul_bool = false;
        arama_bool_KS = false;
        fav_bool_KS = false;
        if (getIntent().getBooleanExtra("fav_archieve", false)) {
            fav_bool_KS = true;
            selector = noList_fav.get(0);
            Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true); // SHOW back button on bar
        }
        else if (getIntent().getBooleanExtra("tefeul", false)) {
            tefeul_bool = true;
            im_btn_tefeul_display.setVisibility(View.GONE);
            selector = randomNumber.nextInt(quoteList.size());;
            //selector = getIntent().getIntExtra("tefeul_no", 0);


            Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true); // SHOW back button on bar
            Objects.requireNonNull(actionBar).hide(); //hide the title bar
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
            window_transparent_CUTOUT();

            im_btn_list_arama_fav.setVisibility(View.INVISIBLE);
            im_btn_share_display.setVisibility(View.INVISIBLE);
            im_btn_tefeul_display.setVisibility(View.INVISIBLE);
            im_btn_fav_display.setVisibility(View.INVISIBLE);
            im_btn_fav_archieve.setVisibility(View.INVISIBLE);
            im_btn_backgr_display.setVisibility(View.INVISIBLE);

            rel_bottom.setBackgroundColor(Color.parseColor("#8010A3CD"));
        }
        else if (getIntent().getBooleanExtra("normal okuma", false)) {
            selector = sharedpreferences_display.getInt("kalınan_yer", 0);
            Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true); // SHOW back button on bar
        }
        else if (getIntent().getBooleanExtra("item_clicked_arama", false)) {
            arama_bool_KS = true;
            selector = getIntent().getIntExtra("item_no", 0);
            Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true); // SHOW back button on bar
        }
        else if (getIntent().getBooleanExtra("item_clicked_fav", false)) {
            fav_bool_KS = true;
            selector = getIntent().getIntExtra("item_no", 0);
            Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true); // SHOW back button on bar
        }
        else if (getIntent().getBooleanExtra("bildirim_bool", false)) {
            selector = getIntent().getIntExtra("bildirim", 0);
        }
        else { // MAIN
            selector = sharedpreferences_display.getInt("last_saved_position", 0);
        }


        //tv_title = findViewById(R.id.tv_title);
        tv_author = findViewById(R.id.tv_author);

        im_popup_like = findViewById(R.id.im_popup_like);
        imageView = findViewById(R.id.imageView);
        //imageView.setImageDrawable(getAssetImg(backgr_images[0]));
        try {
            //imageView.setImageResource(sharedpreferences_display.getInt("background_image", R.drawable.b29));
            //imageView.setImageDrawable(getAssetImg(960, sharedpreferences_display.getString("background_image", backgr_images[0])));
            //imageView.setImageDrawable(getAssetImgBackg(sharedpreferences_display.getString("background_image", backgr_images[0])));
            imageView.setImageBitmap(getAssetBmp(true, 0, sharedpreferences_display.getString("background_image", backgr_images[0])));
        } catch (Exception e) {
            //imageView.setImageResource(R.drawable.b29);
            //imageView.setImageDrawable(getAssetImgBackg(backgr_images[0]));
            imageView.setImageBitmap(getAssetBmp(true, 0, backgr_images[0]));

        }

        try {
            InputStream a = getResources().getAssets().open("abril_fatface_regular.ttf");
        } catch (IOException e) {
            e.printStackTrace();
        }

        txt_logo = findViewById(R.id.txt_logo);
        txt_author = findViewById(R.id.txt_author);
        txt_logo.setTypeface(Typeface.createFromAsset(getAssets(), sharedpreferences_display.getString("manual_text_font", "font/ubuntu_medium.ttf")));
        txt_author.setTypeface(Typeface.createFromAsset(getAssets(), sharedpreferences_display.getString("manual_text_font", "font/ubuntu_medium.ttf")));
        //txt_logo.setTypeface(ResourcesCompat.getFont(Kus_Sutu_display.this, sharedpreferences_display.getInt("manual_text_font", R.font.ubuntu_medium)));
        //txt_author.setTypeface(ResourcesCompat.getFont(Kus_Sutu_display.this, sharedpreferences_display.getInt("manual_text_font", R.font.ubuntu_medium)));
        tv_page_nmbr = findViewById(R.id.tv_page_nmbr);
        tv_page_nmbr.setText((selector+1)+"");  //(selector+1)+"/2769"
        if (quoteList.get(selector).isFavorite())
            im_btn_fav_display.setImageResource(R.drawable.ic_baseline_favorite_red_36);
        else
            im_btn_fav_display.setImageResource(R.drawable.ic_baseline_favorite_red_border_36);

        //HorizontalInfiniteCycleViewPager pager = findViewById(R.id.hori_cycle);
        pager = findViewById(R.id.view_pager);
        //pager.setClipToPadding(false);
        //pager.setPadding(left,0,right,0);


        if (fav_bool_KS) {  //fav ın özel druumunda dolayı bir selector daha eklendi
            for (int i=0; i<noList_fav.size(); i++) {
                if (noList_fav.get(i) == selector) {  //once ever
                    selector = i;
                    break;
                }
            }
            my_adapter = new My_adapter_infinite(quoteList_fav, selector, Kus_Sutu_display.this);
            //tv_page_nmbr.setBackgroundResource(R.drawable.roundcorner_sayi_tv_colored); //else gerek yok
            rel_bottom.setBackgroundColor(Color.parseColor("#80FF0000"));
        }
        else { // fav dışındakiler için adaptör listesi
            my_adapter = new My_adapter_infinite(quoteList, selector, Kus_Sutu_display.this);
        }

        if (tefeul_bool) {
            my_adapter.setLocked(true, selector);
        }

        pager.setAdapter(my_adapter);
        //pager.setOffscreenPageLimit(1); // veya 0
        pager.setOffscreenPageLimit(2);
        pager.setCurrentItem(selector);
        my_adapter.notifyDataSetChanged();

        final int[] old_position = {0};
        final int[] fark = {0};

        positionX = selector;
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int position) {
                positionX = position;
                fark[0] = position - old_position[0];

                if (fav_bool_KS) {
                    tv_page_nmbr.setText((noList_fav.get(position)+1)+"");
                    if (quoteList_fav.get(position).isFavorite()) {
                        im_btn_fav_display.setImageResource(R.drawable.ic_baseline_favorite_red_36);
                    }
                    else {
                        im_btn_fav_display.setImageResource(R.drawable.ic_baseline_favorite_red_border_36);
                    }
                    my_adapter.display.setText(quoteList_fav.get((position)).getQuote());
                }
                else {  //arama_bool dan gelenler, açılır-açılmaz MAIN den gelenler, tfl
                    tv_page_nmbr.setText((position+1)+""); //(selector+1)+"/2769"
                    if (quoteList.get(position).isFavorite())
                        im_btn_fav_display.setImageResource(R.drawable.ic_baseline_favorite_red_36);
                    else
                        im_btn_fav_display.setImageResource(R.drawable.ic_baseline_favorite_red_border_36);

                    try {
                        my_adapter.display.setText(quoteList.get((position)).getQuote());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });

        im_fnt = new ImageFontAdapter(Kus_Sutu_display.this);
        Kus_Sutu_display.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ////gridview.setAdapter(im_fnt);
            }
        });

        cd = new ColorDrawable(Color.parseColor(fore_hexColor));
        //imageView.setForeground(cd);

        im_btn_list_arama_fav.setOnClickListener(view -> {
            Intent intent_toArama = new Intent(Kus_Sutu_display.this, Arama_Fav_Activity.class);
            intent_toArama.putExtra("display_to_aramaFav", true);
            intent_toArama.putExtra("shift_to_same_quote", positionX);
            startActivity(intent_toArama);
            //finish();
        });

        //display_layout_func();



        //SHARE
        const_share = findViewById(R.id.const_share);
        const_share.setVisibility(View.INVISIBLE);
        txInptLayShare = findViewById(R.id.txInptLayShare);
        im_btnClose = findViewById(R.id.im_btnClose);
        im_btnSetting = findViewById(R.id.im_btnSetting);
        autoCompleteTVShare = findViewById(R.id.autoCompleteTVShare);
        im_btnShare = findViewById(R.id.im_btnShare);

        coznrlk_str = new String[]{
                "Tam Ekran (bu cihaz)",
                "Kare 1:1",
                "16:9",
                "4:3",
                "3:2",
                "9:16",
                "3:4",
                "2:3",
                "Sadece yazı"
        };

        MyAutoCompleteAdapter autoCompleteAdapter = new MyAutoCompleteAdapter(this, android.R.layout.simple_list_item_1, coznrlk_str);
        Kus_Sutu_display.this.runOnUiThread(() -> {
            autoCompleteTVShare.setAdapter(autoCompleteAdapter);
        });
        //autoCompleteTVShare.setAdapter(autoCompleteAdapter);
        autoCompleteTVShare.setText(coznrlk_str[0], false);
        autoCompleteTVShare.setOnItemClickListener((adapterView, view, position, l) -> {
            autocompleteText_cozunurluk(position);
        });

        im_btnClose.setOnClickListener(view -> {
            //btnSetting_bool = false;
            share_close(coznrlk_str[0]);
        });

        im_btnSetting.setOnClickListener(view -> {
            btnSetting_bool = true;

            hide_screen_things();
            const_share.setVisibility(View.INVISIBLE);

            if (stub_display == null) {
                stub_display = findViewById(R.id.stub_display);
                stub_view = Objects.requireNonNull(stub_display).inflate();
                display_onCreate(stub_view);
            }
            stub_display.setVisibility(View.VISIBLE);
            txt_share.setVisibility(View.VISIBLE);
            im_btnShare2.setVisibility(View.VISIBLE);
        });

        im_btnShare.setOnClickListener(view -> {
            share_button();
        });

        /*Intent sendIntent2 = new Intent(Kus_Sutu_display.this, ShareActivity.class);
        Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();*/

        im_btn_share_display.setOnClickListener(view -> {
            const_share.setVisibility(View.VISIBLE);
            hide_screen_things();

            //eski-den
            /*Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);

            sendIntent.putExtra(Intent.EXTRA_TEXT, quoteList.get(selector).getQuote()+ " \n\nAhmed İhsan GENÇ, Kuş Sütü ("+tv_page_nmbr.getText()+".)");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);*/

            //yeni-den
            /*
            String set_str;
            int set_no;
            if (fav_bool_KS) {
                set_str = quoteList_fav.get(positionX).getQuote();
                set_no = quoteList_fav.get(positionX).getNo();
            }
            else {
                set_str = quoteList.get(positionX).getQuote();
                set_no = quoteList.get(positionX).getNo();
            }

            //sendIntent2.putExtra("background_to_share", bytes);
            //sendIntent2.putExtra("picture", R.drawable.b27);
            sendIntent2.putExtra("picture", im_fnt.backgr_images[sharedpreferences_display.getInt("back_image_no",5)]);
            sendIntent2.putExtra("quote_to_share", set_str);
            sendIntent2.putExtra("quote_no_to_share", set_no);
            startActivity(sendIntent2);*/
        });

        im_btn_tefeul_display.setOnClickListener(view -> {
            Intent intent_tfl = new Intent(Kus_Sutu_display.this, Fatiha_ihlas.class);
            intent_tfl.putExtra("tefeul", true);
            startActivity(intent_tfl);
        });

        im_btn_fav_display.setOnClickListener(view -> {
            if (fav_bool_KS) {
                if (quoteList_fav.get(positionX).isFavorite()) {
                    im_btn_fav_display.setImageResource(R.drawable.ic_baseline_favorite_red_border_36);
                    quoteList_fav.get(positionX).setFavorite(false);
                    editor_display.putBoolean("fav_" + (noList_fav.get(positionX)), false);
                } else {
                    im_btn_fav_display.setImageResource(R.drawable.ic_baseline_favorite_red_36);
                    quoteList_fav.get(positionX).setFavorite(true);
                    editor_display.putBoolean("fav_" + (noList_fav.get(positionX)), true);
                }
            }
            else {
                if (quoteList.get(positionX).isFavorite()) {
                    im_btn_fav_display.setImageResource(R.drawable.ic_baseline_favorite_red_border_36);
                    quoteList.get(positionX).setFavorite(false);
                    editor_display.putBoolean("fav_" + (positionX), false);
                } else {
                    im_btn_fav_display.setImageResource(R.drawable.ic_baseline_favorite_red_36);
                    quoteList.get(positionX).setFavorite(true);
                    editor_display.putBoolean("fav_" + (positionX), true);
                }
            }

            editor_display.apply();
            my_adapter.notifyDataSetChanged();
        });

        im_btn_fav_archieve.setVisibility(View.INVISIBLE);

        Intent intent = new Intent(Kus_Sutu_display.this, Kus_Sutu_display.class);
        im_btn_fav_display.setOnLongClickListener(view -> {
            if (!tefeul_bool) {
                im_btn_fav_archieve.setVisibility(View.VISIBLE);

                if (fav_bool_KS) {
                    intent.putExtra("fav_archieve", false);
                    im_btn_fav_archieve.setImageResource(R.drawable.ic_favorite_unarchieve_36);
                }
                else {
                    intent.putExtra("fav_archieve", true);
                    im_btn_fav_archieve.setImageResource(R.drawable.ic_favorite_archieve_36);
                }

                if (!noList_fav.isEmpty()) {
                    finish();
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Favori listeniz boş", Toast.LENGTH_SHORT).show();

                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            im_btn_fav_archieve.setVisibility(View.INVISIBLE);
                        }
                    }, 1000);

                }
            } else {
                //Toast.makeText(this, "dgfdf", Toast.LENGTH_SHORT).show();
                FancyToast.makeText(Kus_Sutu_display.this, "Favorilere buradan erişemezsiniz", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
            }


            return true; //No other click events will be notified.
        });

        im_btn_fav_archieve.setOnClickListener(view -> {
            //restart activity
            finish();
            startActivity(intent);
        });

        im_btn_backgr_display.setOnClickListener(view -> {
            back_display_bool = true;

            //my_adapter.const_bool = false;
            hide_screen_things();

            if (stub_display == null) {
                stub_display = findViewById(R.id.stub_display);
                stub_view = Objects.requireNonNull(stub_display).inflate();
                display_onCreate(stub_view);


            }
            stub_display.setVisibility(View.VISIBLE);
            txt_share.setVisibility(View.INVISIBLE);
            im_btnShare2.setVisibility(View.INVISIBLE);
        });

        rel_bottom.setOnClickListener(view -> {
            if (fav_bool_KS || tefeul_bool)
                FancyToast.makeText(Kus_Sutu_display.this, "Söze Git e buradan erişemezsiniz", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            else {
                AlertDialog.Builder sayfa_builder = new AlertDialog.Builder(Kus_Sutu_display.this);

                Objects.requireNonNull(actionBar).hide(); //hide the title bar
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
                window_transparent_CUTOUT();

                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_save__go, null);
                sayfa_builder.setView(dialogView);
                sayfa_builder.setTitle("Söze Git");

                ConstraintLayout const_ayrac = dialogView.findViewById(R.id.const_ayrac);
                ImageButton im_bookmark = dialogView.findViewById(R.id.im_bookmark);
                Button btn_go_saved = dialogView.findViewById(R.id.btn_go_saved);
                EditText editText = dialogView.findViewById(R.id.ed_sayfa);
                TextView tv_cancel = dialogView.findViewById(R.id.tv_cancel);
                Button btn_go = dialogView.findViewById(R.id.btn_go);

                btn_go.setOnClickListener(view13 -> {
                });

                tv_cancel.setOnClickListener(view13 -> {
                });

                AlertDialog alertDialog = sayfa_builder.create();
                alertDialog.show();


                btn_go.setEnabled(false);
                btn_go.setBackgroundTintList(getResources().getColorStateList(R.color.colorGray_my));
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        btn_go.setEnabled(true);
                        btn_go.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary_my));
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!editText.getText().toString().equals("")) {
                            int list_len = quoteList.size();
                            if (list_len == 0)
                                list_len = 2769;
                            if (Integer.parseInt(editText.getText().toString()) > list_len) {
                                FancyToast.makeText(Kus_Sutu_display.this, list_len + " dan fazla sayı giremezsiniz!", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                                btn_go.setEnabled(false);
                                btn_go.setBackgroundTintList(getResources().getColorStateList(R.color.colorGray_my));
                            }
                        } else {
                            btn_go.setEnabled(false);
                            btn_go.setBackgroundTintList(getResources().getColorStateList(R.color.colorGray_my));
                        }
                    }
                });

                tv_cancel.setOnClickListener(view13 -> {
                    Objects.requireNonNull(actionBar).hide(); //hide the title bar
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
                    window_transparent_CUTOUT();
                    alertDialog.dismiss();
                });

                btn_go.setOnClickListener(view13 -> {
                    Objects.requireNonNull(actionBar).hide(); //hide the title bar
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
                    window_transparent_CUTOUT();

                    pager.setCurrentItem(Integer.parseInt(editText.getText().toString()) - 1);
                    alertDialog.dismiss();
                });

                Kus_Sutu_Model quote = quoteList.get(positionX);

                //im_bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_36);
                if (sharedpreferences_display.getInt("last_saved_position", 0) == quote.getNo() - 1)
                    im_bookmark.setImageResource(R.drawable.ic_baseline_bookmarked_36);
                else {
                    quote.setNo_of_Bookmark(0);
                    im_bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_36);
                }

                const_ayrac.setOnClickListener(view12 -> {
                    if (sharedpreferences_display.getInt("last_saved_position", 0) == quote.getNo() - 1) {
                        System.out.println("here bookmarked: " + (quote.getNo() - 1));
                        im_bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_36);
                        editor_display.putInt("last_saved_position", 0).apply();
                    } else {
                        System.out.println("here none_bookmark");
                        quote.setNo_of_Bookmark(0);
                        im_bookmark.setImageResource(R.drawable.ic_baseline_bookmarked_36);
                        editor_display.putInt("last_saved_position", (quote.getNo() - 1)).apply();
                        FancyToast.makeText(Kus_Sutu_display.this, quote.getNo() + ". sözde kaldınız", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                    }
                    editor_display.apply();

                    window_transparent_CUTOUT();
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.dismiss();
                        }
                    }, 500);
                });

                btn_go_saved.setOnClickListener(v1 -> {
                    window_transparent_CUTOUT();

                    int last_saved_ps = sharedpreferences_display.getInt("last_saved_position", 0);
                    pager.setCurrentItem(last_saved_ps);
                    Toast.makeText(Kus_Sutu_display.this, "Son kalınan yer: " + (last_saved_ps + 1) + ". söz", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                });

            /*if (fav_bool_KS) {
                FancyToast.makeText(Kus_Sutu_display.this, "Burada istenen numaraya gidemeyebilirsiniz", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                btn_go_saved.setEnabled(false);
                btn_go_saved.setBackgroundTintList(getResources().getColorStateList(R.color.colorGray_my));
            }
            else {
                btn_go_saved.setEnabled(true);
                btn_go_saved.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary_my));
            }*/
            }
        });

    }

    public static ViewStub deflate(View view) {
        ViewParent viewParent = view.getParent();
        if (viewParent != null && viewParent instanceof ViewGroup) {
            int index = ((ViewGroup) viewParent).indexOfChild(view);
            int inflatedId = view.getId();

            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            ((ViewGroup) viewParent).removeView(view);

            Context context = ((ViewGroup) viewParent).getContext();

            ViewStub viewStub = new ViewStub(context);
            viewStub.setInflatedId(inflatedId);
            viewStub.setLayoutParams(layoutParams);
            ((ViewGroup) viewParent).addView(viewStub, index);

            return viewStub;
        } else {
            throw new IllegalStateException("Inflated View has not a parent");
        }
    }

    private void display_onCreate(View view) {
        im_btnClose_scroll = view.findViewById(R.id.im_btnClose_scroll);

        txInptLay_font = view.findViewById(R.id.txInptLay_font);
        autoCompleteTV_font = view.findViewById(R.id.autoCompleteTV_font);
        typeface_display = new Typeface[1];

        lin_out_boyut = view.findViewById(R.id.lin_out_boyut);
        const_manuel_auto = view.findViewById(R.id.const_manuel_auto);
        text_slider_size = view.findViewById(R.id.text_slider_size);
        im_btn_minus = view.findViewById(R.id.im_btn_minus);
        im_btn_add = view.findViewById(R.id.im_btn_add);
        slider_size = view.findViewById(R.id.slider_size);
        txt_renk_diyalog = view.findViewById(R.id.txt_renk_diyalog);
        txt_bckgr_opacity_dialog = view.findViewById(R.id.txt_bckgr_opacity_dialog);
        const_check0 = view.findViewById(R.id.const_check0);
        const_check1 = view.findViewById(R.id.const_check1);
        const_check2 = view.findViewById(R.id.const_check2);
        const_check3 = view.findViewById(R.id.const_check3);
        chkbx_auto0 = view.findViewById(R.id.chkbx_auto0);
        chkbx_auto1 = view.findViewById(R.id.chkbx_auto1);
        chkbx_auto2 = view.findViewById(R.id.chkbx_auto2);
        chkbx_auto3 = view.findViewById(R.id.chkbx_auto3);
        chkbx_auto4 = view.findViewById(R.id.chkbx_auto4);
        chkbx_auto5 = view.findViewById(R.id.chkbx_auto5);
        chkbx_auto6 = view.findViewById(R.id.chkbx_auto6);
        scroll1 = view.findViewById(R.id.scroll1);
        scroll2 = view.findViewById(R.id.scroll2);
        txt_pref_manuel = view.findViewById(R.id.txt_pref_manuel);
        txt_pref_oto = view.findViewById(R.id.txt_pref_oto);
        txt_share = view.findViewById(R.id.txt_share);
        im_btnShare2 = view.findViewById(R.id.im_btnShare2);

        gridview = view.findViewById(R.id.gridview);

        kussutu_font = new String[]{
                "Kuş Sütü 1", "Kuş Sütü 2", "Kuş Sütü 3", "Kuş Sütü 4",
                "Kuş Sütü 5", "Kuş Sütü 6", "Kuş Sütü 7", "Kuş Sütü 8",
                "Kuş Sütü 9", "Kuş Sütü 10","Kuş Sütü 11","Kuş Sütü 12",
                "Kuş Sütü 13","Kuş Sütü 14","Kuş Sütü 15","Kuş Sütü 16",
                "Kuş Sütü 17","Kuş Sütü 18","Kuş Sütü 19","Kuş Sütü 20",
                "Kuş Sütü 21","Kuş Sütü 22","Kuş Sütü 23", "Kuş Sütü 24",
                "Kuş Sütü 27","Kuş Sütü 28","Kuş Sütü 29","Kuş Sütü 30",
                "Kuş Sütü 31","Kuş Sütü 32","Kuş Sütü 33"
        };

        myFontAdapter = new MyFontAdapter(this, android.R.layout.simple_list_item_1, kussutu_font);

        Kus_Sutu_display.this.runOnUiThread(() -> {
            gridview.setAdapter(im_fnt);
            //gridview.setAdapter(new ImageFontAdapter(Kus_Sutu_display.this));
        });

        display_layout_func();

    }

    private void display_layout_func() {

        im_btnShare2.setOnClickListener(view -> {
            share_button();
        });

        im_btnClose_scroll.setOnClickListener(view -> {
            stub_display.setVisibility(View.GONE);
            my_adapter.const_bool = false;
        });

        Kus_Sutu_display.this.runOnUiThread(() -> {
            autoCompleteTV_font.setAdapter(myFontAdapter);
        });
        //autoCompleteTV_font.setAdapter(myFontAdapter);

        autoCompleteTV_font.setTypeface(Typeface.createFromAsset(getAssets(), sharedpreferences_display.getString("manual_text_font", "font/ubuntu_medium.ttf")));
        //autoCompleteTV_font.setTypeface(ResourcesCompat.getFont(Kus_Sutu_display.this, sharedpreferences_display.getInt("manual_text_font", R.font.ubuntu_medium)));
        autoCompleteTV_font.setText(sharedpreferences_display.getString("manual_text", "Kuş Sütü"), false);
        autoCompleteTV_font.setOnItemClickListener((adapterView, view, position, l) -> {
            //typeface[0] = ResourcesCompat.getFont(getApplicationContext(), im_fnt.tv_fonts[position]);
            //typeface[0] = Typeface.createFromAsset(getAssets(), im_fnt.tv_fonts[position]);
            typeface_display[0] = Typeface.createFromAsset(getAssets(), tv_fonts[position]);

            autoCompleteTV_font.setTypeface(typeface_display[0]);
            my_adapter.display.setTypeface(typeface_display[0]);
            txt_logo.setTypeface(typeface_display[0]);
            txt_author.setTypeface(typeface_display[0]);

            editor_display.putString("manual_text", kussutu_font[position]);
            editor_display.putString("manual_text_font", tv_fonts[position]);
            editor_display.apply();
            Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();
        });

        our_text_size = (int) (sharedpreferences_display.getFloat("text_size", slider_size.getValue()));
        slider_size.setValue(our_text_size);
        text_slider_size.setText("Boyut = " + (int) our_text_size);
        im_btn_minus.setOnClickListener(view -> {
            our_text_size =  (int) slider_size.getValue();
            if (our_text_size >= 15) {
                our_text_size -= 5;
                slider_size.setValue(our_text_size);
                text_slider_size.setText("Boyut = " + (int) our_text_size);
                my_adapter.display.setTextSize(TypedValue.COMPLEX_UNIT_DIP, our_text_size);
                editor_display.putFloat("text_size", our_text_size).apply();

                lin_out_boyut.setVisibility(View.VISIBLE);
                const_manuel_auto.setVisibility(View.VISIBLE);
                stub_view.setBackgroundColor(Color.parseColor("#F53780A2"));
                Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();

            }
        });
        im_btn_add.setOnClickListener(view -> {
            our_text_size =  (int) slider_size.getValue();
            if (our_text_size <= 75) {
                our_text_size += 5;
                slider_size.setValue(our_text_size);
                text_slider_size.setText("Boyut = " + (int) our_text_size);
                my_adapter.display.setTextSize(TypedValue.COMPLEX_UNIT_DIP, our_text_size);
                editor_display.putFloat("text_size", our_text_size).apply();

                float px2 = my_adapter.display.getTextSize();
                float sp2 = px2 / getResources().getDisplayMetrics().scaledDensity;

                lin_out_boyut.setVisibility(View.VISIBLE);
                const_manuel_auto.setVisibility(View.VISIBLE);
                stub_view.setBackgroundColor(Color.parseColor("#F53780A2"));
                Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();

            }
        });

        slider_size.addOnChangeListener((slider, value, fromUser) -> {
            our_text_size = value;
            text_slider_size.setText("Boyut = " + (int) our_text_size);
            try {
                my_adapter.display.setTextSize(TypedValue.COMPLEX_UNIT_SP, our_text_size);
            } catch (Exception e) {
                e.printStackTrace();
            }
            editor_display.putFloat("text_size", our_text_size).apply();

            //stub_display.setVisibility(View.GONE);
            lin_out_boyut.setVisibility(View.INVISIBLE);
            const_manuel_auto.setVisibility(View.INVISIBLE);
            stub_view.setBackgroundColor(Color.parseColor("#003780A2"));
            Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();
        });

        slider_size.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
            }
            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                our_text_size = slider.getValue();
                text_slider_size.setText("Boyut = " + (int) our_text_size);
                my_adapter.display.setTextSize(TypedValue.COMPLEX_UNIT_SP, our_text_size);
                editor_display.putFloat("text_size", our_text_size).apply();

                //stub_display.setVisibility(View.VISIBLE);
                lin_out_boyut.setVisibility(View.VISIBLE);
                const_manuel_auto.setVisibility(View.VISIBLE);
                stub_view.setBackgroundColor(Color.parseColor("#F53780A2"));
                Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();
            }
        });

        //alertleri kısalt tek bir fonsiyon çatısında birleştir..
        txt_renk_diyalog.setBackgroundColor(Color.parseColor(hexColor));
        txt_renk_diyalog.setOnClickListener(view -> {
            AlertDialog.Builder build_colorPicker = new AlertDialog.Builder(Kus_Sutu_display.this);
            LayoutInflater inflater = Kus_Sutu_display.this.getLayoutInflater();
            View dialogView_colorPicker = inflater.inflate(R.layout.my_color_picker, null);
            build_colorPicker.setView(dialogView_colorPicker);

            ColorPicker picker = dialogView_colorPicker.findViewById(R.id.picker);
            SVBar svbar = dialogView_colorPicker.findViewById(R.id.svbar);
            OpacityBar opacitybar = dialogView_colorPicker.findViewById(R.id.opacitybar);

            RelativeLayout rel_alert = dialogView_colorPicker.findViewById(R.id.rel_alert);

            picker.setOldCenterColor(Color.parseColor(hexColor));

            AlertDialog alertDialog = build_colorPicker.create();
            alertDialog.show();

            Window window = alertDialog.getWindow();


            picker.addSVBar(svbar);
            picker.addOpacityBar(opacitybar);

            picker.setColor(Color.parseColor(hexColor));
            picker.setOnColorChangedListener(color -> {
                back_display_bool = true;

                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                stub_display.setVisibility(View.GONE);

                hexColor = String.format("#%08X", (color));
                editor_display.putString("color_picker_value", hexColor).apply();
                txt_renk_diyalog.setBackgroundColor(Color.parseColor(hexColor));

                my_adapter.display.setTextColor(Color.parseColor(hexColor));
                Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();
            });

            picker.setOnColorSelectedListener(color -> {
                window.setBackgroundDrawableResource(R.drawable.roundcorner_bildirim);
                stub_display.setVisibility(View.VISIBLE);
            });

            rel_alert.setOnClickListener(view1 -> {
                window.setBackgroundDrawableResource(R.drawable.roundcorner_bildirim);
                stub_display.setVisibility(View.VISIBLE);
            });



            window.setBackgroundDrawableResource(R.drawable.roundcorner_bildirim);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);
        });

        txt_bckgr_opacity_dialog.setBackgroundColor(Color.parseColor(fore_hexColor));
        txt_bckgr_opacity_dialog.setOnClickListener(view -> {
            AlertDialog.Builder build_colorPicker = new AlertDialog.Builder(Kus_Sutu_display.this);
            LayoutInflater inflater = Kus_Sutu_display.this.getLayoutInflater();
            View dialogView_colorPicker = inflater.inflate(R.layout.my_color_picker, null);
            build_colorPicker.setView(dialogView_colorPicker);

            ColorPicker picker = dialogView_colorPicker.findViewById(R.id.picker);
            SVBar svbar = dialogView_colorPicker.findViewById(R.id.svbar);
            OpacityBar opacitybar = dialogView_colorPicker.findViewById(R.id.opacitybar);

            RelativeLayout rel_alert = dialogView_colorPicker.findViewById(R.id.rel_alert);

            picker.setOldCenterColor(Color.parseColor(fore_hexColor));


            AlertDialog alertDialog = build_colorPicker.create();
            alertDialog.show();

            Window window = alertDialog.getWindow();


            picker.addSVBar(svbar);
            picker.addOpacityBar(opacitybar);

            picker.setColor(Color.parseColor(fore_hexColor));
            picker.setOnColorChangedListener(color -> {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                stub_display.setVisibility(View.GONE);

                fore_hexColor = String.format("#%08X", (color));
                editor_display.putString("foreground_color_picker_value", fore_hexColor).apply();
                txt_bckgr_opacity_dialog.setBackgroundColor(Color.parseColor(fore_hexColor));

                cd = new ColorDrawable(Color.parseColor(fore_hexColor));
                imageView.setForeground(cd);
                Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();
            });

            picker.setOnColorSelectedListener(color -> {
                window.setBackgroundDrawableResource(R.drawable.roundcorner_bildirim);
                stub_display.setVisibility(View.VISIBLE);
            });

            rel_alert.setOnClickListener(view1 -> {
                window.setBackgroundDrawableResource(R.drawable.roundcorner_bildirim);
                stub_display.setVisibility(View.VISIBLE);
            });


            window.setBackgroundDrawableResource(R.drawable.roundcorner_bildirim);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);
        });


        chkbx_auto0.setChecked(sharedpreferences_display.getBoolean("check_bool_0", true)); // BOYUT
        if (chkbx_auto0.isChecked())
            const_check1.setVisibility(View.INVISIBLE);
        else
            const_check1.setVisibility(View.VISIBLE);

        chkbx_auto0.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                TextViewCompat.setAutoSizeTextTypeWithDefaults(my_adapter.display, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                float px = my_adapter.display.getTextSize();
                our_text_size = (int) px / getResources().getDisplayMetrics().scaledDensity;
                //slider_size.setValue((int) our_text_size);

                editor_display.putBoolean("check_bool_0", true);
                const_check1.setVisibility(View.INVISIBLE);
            }
            else {
                our_text_size = (int) (sharedpreferences_display.getFloat("text_size", slider_size.getValue()));

                editor_display.putBoolean("check_bool_0", false);
                const_check1.setVisibility(View.VISIBLE);
            }

            text_slider_size.setText("Boyut = " + (int) our_text_size);
            editor_display.apply();
            Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();
        });

        chkbx_auto1.setChecked(sharedpreferences_display.getBoolean("check_bool_1", true)); // FONT
        if (chkbx_auto1.isChecked())
            const_check0.setVisibility(View.INVISIBLE);
        else
            const_check0.setVisibility(View.VISIBLE);

        chkbx_auto1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                editor_display.putBoolean("check_bool_1", true);
                const_check0.setVisibility(View.INVISIBLE);
            } else {
                editor_display.putBoolean("check_bool_1", false);
                const_check0.setVisibility(View.VISIBLE);
            }
            editor_display.apply();
            Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();
        });

        chkbx_auto2.setChecked(sharedpreferences_display.getBoolean("check_bool_2", true)); // TEXT COLOR
        if (chkbx_auto2.isChecked())
            const_check2.setVisibility(View.INVISIBLE);
        else
            const_check2.setVisibility(View.VISIBLE);

        chkbx_auto2.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                editor_display.putBoolean("check_bool_2", true);
                const_check2.setVisibility(View.INVISIBLE);
                hexColor = "#FFFFFF";
            } else {
                editor_display.putBoolean("check_bool_2", false);
                const_check2.setVisibility(View.VISIBLE);
                hexColor = sharedpreferences_display.getString("color_picker_value", "#FFFFFF");
            }
            editor_display.apply();
            Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();
        });

        chkbx_auto3.setChecked(sharedpreferences_display.getBoolean("check_bool_3", true)); // FOREGROUND
        if (chkbx_auto3.isChecked())
            const_check3.setVisibility(View.INVISIBLE);
        else
            const_check3.setVisibility(View.VISIBLE);

        chkbx_auto3.setOnCheckedChangeListener((compoundButton, b) -> {
            back_display_bool = true;
            if (b) {
                editor_display.putBoolean("check_bool_3", true);
                const_check3.setVisibility(View.INVISIBLE);
                fore_hexColor = "#00FFFFFF";
            } else {
                editor_display.putBoolean("check_bool_3", false);
                const_check3.setVisibility(View.VISIBLE);
                fore_hexColor = sharedpreferences_display.getString("foreground_color_picker_value", "#00FFFFFF");
            }
            editor_display.apply();
            Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();
        });

        chkbx_auto4.setChecked(sharedpreferences_display.getBoolean("check_bool_4", false)); // MOVE
        chkbx_auto4.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                editor_display.putBoolean("check_bool_4", true);
            } else {
                editor_display.putBoolean("check_bool_4", false);
            }
            editor_display.apply();
            Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();
        });

        chkbx_auto5.setChecked(sharedpreferences_display.getBoolean("check_bool_5", false)); // HIDE NMBR
        chkbx_auto5.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                tv_page_nmbr.setVisibility(View.INVISIBLE);
                editor_display.putBoolean("check_bool_5", true);
            } else {
                tv_page_nmbr.setVisibility(View.VISIBLE);
                editor_display.putBoolean("check_bool_5", false);
            }
            editor_display.apply();
            Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();
        });

        chkbx_auto6.setChecked(sharedpreferences_display.getBoolean("check_bool_6", false)); // HIDE AUTHOR
        chkbx_auto6.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                txt_author.setVisibility(View.INVISIBLE);
                editor_display.putBoolean("check_bool_6", true);
            } else {
                txt_author.setVisibility(View.VISIBLE);
                editor_display.putBoolean("check_bool_6", false);
            }
            editor_display.apply();
            Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();
        });


        txt_pref_manuel.setOnClickListener(view -> {
            txt_pref_manuel.setBackgroundResource(R.drawable.roundcorner_left_colored);
            txt_pref_oto.setBackgroundResource(R.drawable.roundcorner_right);
            scroll2.setVisibility(View.VISIBLE);
            scroll1.setVisibility(View.INVISIBLE);
        });
        txt_pref_oto.setOnClickListener(view -> {
            txt_pref_oto.setBackgroundResource(R.drawable.roundcorner_right_colored);
            txt_pref_manuel.setBackgroundResource(R.drawable.roundcorner_left);
            scroll2.setVisibility(View.INVISIBLE);
            scroll1.setVisibility(View.VISIBLE);
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    private void display_layout_instantiate() {
        /*if (sharedpreferences_display.getBoolean("check_bool_0", false))
            TextViewCompat.setAutoSizeTextTypeWithDefaults(my_adapter.display, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        else
            my_adapter.display.setTextSize(TypedValue.COMPLEX_UNIT_SP, our_text_size);

        if (sharedpreferences_display.getBoolean("check_bool_1", false))
            //txt_logo.setTypeface(Typeface.createFromAsset(getAssets(), sharedpreferences_display.getString("manual_text_font", "ubuntu_medium.ttf")));
            typeface2 = Typeface.createFromAsset(getAssets(), sharedpreferences_display2.getString("background_font", "font/ubuntu_medium.ttf"));
        else
            typeface2 = Typeface.createFromAsset(getAssets(), sharedpreferences_display2.getString("manual_text_font", "font/ubuntu_medium.ttf"));

        if (sharedpreferences_display.getBoolean("check_bool_2", false))
            my_adapter.display.setTextColor(Color.parseColor("#FFFFFF")); // veya her bckgr için özel renk kodu neyse o!
        else
            my_adapter.display.setTextColor(Color.parseColor(hexColor));*/

        if (sharedpreferences_display.getBoolean("check_bool_3", false))
            cd = new ColorDrawable(Color.parseColor("#66222f3e"));
        else
            cd = new ColorDrawable(Color.parseColor(fore_hexColor));

        imageView.setForeground(cd);

        if (sharedpreferences_display.getBoolean("check_bool_4", false)) {
            my_adapter.display.setOnTouchListener((view13, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view13.getX() - event.getRawX();
                        dY = view13.getY() - event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        view13.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        break;

                    default:
                        return false;
                }
                return true;
            });
        }
        else
            my_adapter.display.setOnTouchListener(null);

        if (sharedpreferences_display.getBoolean("check_bool_5", false))
            tv_page_nmbr.setVisibility(View.INVISIBLE);
        else
            tv_page_nmbr.setVisibility(View.VISIBLE);

        if (sharedpreferences_display.getBoolean("check_bool_6", false))
            txt_author.setVisibility(View.INVISIBLE);
        else
            txt_author.setVisibility(View.VISIBLE);
    }

    private void share_close(String itemCznrlk) {
        autoCompleteTVShare.setText(itemCznrlk, false);

        params.height = heightPixels;
        const_kussutu.setLayoutParams(params);

        const_share.setVisibility(View.INVISIBLE);
        my_adapter.const_bool = false;

        only_text_bool = false;
        imageView.setVisibility(View.VISIBLE);
        rel_bottom.setVisibility(View.VISIBLE);
    }

    private void autocompleteText_cozunurluk(int position) {
        only_text_bool = false;
        //imgV.setVisibility(View.VISIBLE);
        //tv.setTextColor(Color.WHITE);   //beyaz dışında renk varsa getInt yapılmalı
        tv_page_nmbr.setVisibility(View.VISIBLE);

        params = const_kussutu.getLayoutParams();
        params.width = widthPixels;

        only_text_bool = false;
        imageView.setVisibility(View.VISIBLE);
        rel_bottom.setVisibility(View.VISIBLE);
        if (position == 0) { // tam ekran
            params.height = heightPixels;
        } else if (position == 1) {             // kare
            params.height = widthPixels;
        } else if (position == 2) {             // 16:9
            params.height = widthPixels/16*9;
        } else if (position == 3) {             // 4:3
            params.height = widthPixels/4*3;
        } else if (position == 4) {             // 3:2
            params.height = widthPixels/3*2;
        } else if (position == 5) {             // 9:16
            params.height = widthPixels/9*16;
        } else if (position == 6) {             // 3:4
            params.height = widthPixels/3*4;
        } else if (position == 7) {             // 2:3
            params.height = widthPixels/2*3;
        } else if (position == 8) {             // sadece yazı
            only_text_bool = true;
            imageView.setVisibility(View.INVISIBLE);
            rel_bottom.setVisibility(View.INVISIBLE);
        }
        const_kussutu.setLayoutParams(params);

        Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();
    }

    private void share_button() {
        if (!only_text_bool) {
            // autosize ???

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
            if (fav_bool_KS)
                sendIntent.putExtra(Intent.EXTRA_TEXT, quoteList_fav.get((positionX)).getQuote()+ " \n\nAhmed İhsan GENÇ, Kuş Sütü ("+tv_page_nmbr.getText()+".)");
            else
                sendIntent.putExtra(Intent.EXTRA_TEXT, quoteList.get((positionX)).getQuote()+ " \n\nAhmed İhsan GENÇ, Kuş Sütü ("+tv_page_nmbr.getText()+".)");
            //sendIntent.putExtra(Intent.EXTRA_TEXT, my_adapter.display.getText()+ " \n\nAhmed İhsan GENÇ, Kuş Sütü ("+tv_page_nmbr.getText()+".)");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }

    private Bitmap takeScreenshot() {
        //View rootView = rel_share_1;
        /*LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_kus_sutu, null);
        View rootView = v.findViewById(R.id.const_kussutu);*/

        const_kussutu.invalidate();
        View rootView = const_kussutu;
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);

        item_search_display = menu.findItem(R.id.item_search_display);
        item_notify = menu.findItem(R.id.item_notify);
        item_options = menu.findItem(R.id.item_options);

        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_options) {
            /*AlertDialog.Builder alert_settings = new AlertDialog.Builder(Kus_Sutu_display.this);

            Objects.requireNonNull(actionBar).hide(); //hide the title bar
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
            window_transparent_CUTOUT();

            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.alert_settings, null);

            alert_settings.setView(dialogView);
            //bildirim_builder.setTitle("Bildirim Sayısı");
            AlertDialog alertDialog = alert_settings.create();
            alertDialog.show();

            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/

        }
        if (item.getItemId() == R.id.item_search_display) {
            Intent intent_toAra = new Intent(Kus_Sutu_display.this, Arama_Fav_Activity.class);
            intent_toAra.putExtra("display_to_aramaFav", true);
            intent_toAra.putExtra("shift_to_same_quote", positionX);
            intent_toAra.putExtra("search_boolean", true);
            startActivity(intent_toAra);
        }

        if (item.getItemId() == R.id.item_notify) {
            my_notification();
        }

        return super.onOptionsItemSelected(item);
    }

    private void my_notification() {
        Toast.makeText(this, "Şu an sadece İstanbul için geçerli", Toast.LENGTH_LONG).show();
        AlertDialog.Builder bildirim_builder = new AlertDialog.Builder(Kus_Sutu_display.this);

        Objects.requireNonNull(actionBar).hide(); //hide the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
        window_transparent_CUTOUT();

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_bildirim, null);
        NumberPicker numberPicker = dialogView.findViewById(R.id.numberPicker);
        Button btn_notify_saved = dialogView.findViewById(R.id.btn_notify_saved);
        Button btn_notify_cancel = dialogView.findViewById(R.id.btn_notify_cancel);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(10);

        numberPicker.setValue(sharedpreferences_display.getInt("numberPicker_Value",0));


        createNotificationChannel();

        btn_notify_saved.setOnClickListener(view -> {
        });

        btn_notify_cancel.setOnClickListener(view -> {
        });

        bildirim_builder.setView(dialogView);
        //bildirim_builder.setTitle("Bildirim Sayısı");
        AlertDialog alertDialog = bildirim_builder.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btn_notify_saved.setOnClickListener(view -> {
            alarm_prepare_set(alarmManager);

            editor_display.putInt("numberPicker_Value", numberPicker.getValue()).apply();
            //Toast.makeText(Kus_Sutu_display.this, numberPicker.getValue()+" bildirim eklendi", Toast.LENGTH_SHORT).show();
            Toast.makeText(Kus_Sutu_display.this, "5 bildirim eklendi", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        });

        btn_notify_cancel.setOnClickListener(view -> {
            //for (int id=0; id<numberPicker.getValue(); id++) {
            for (int id=0; id<5; id++) { // 5 == hrs.length
                @SuppressLint("UnspecifiedImmutableFlag")
                PendingIntent pendingIntent1 = PendingIntent.getBroadcast(Kus_Sutu_display.this,
                        id,
                        new Intent(Kus_Sutu_display.this, NotificationB.class),
                        PendingIntent.FLAG_ONE_SHOT);

                if (alarmManager!= null) {
                    alarmManager.cancel(pendingIntent1);
                }
            }
            //numberPicker.setValue(0);
            //editor_display.putInt("numberPicker_Value", numberPicker.getValue()).apply();
            Toast.makeText(Kus_Sutu_display.this, "Bildirimler iptal edildi", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //26
            CharSequence name = "LemubitReminderChannel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            String description = "Channel for Lemubit Reminder";

            NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(R.color.colorPrimary_my);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void alarm_prepare_set(AlarmManager alarmManager) {
        //calc params
        CalculationParameters params = CalculationMethod.MUSLIM_WORLD_LEAGUE.getParameters();
        params.madhab = Madhab.HANAFI;
        params.adjustments.fajr = 2;
        //date
        //DateComponents date = new DateComponents(2015, 11, 1);
        DateComponents date = DateComponents.from(new Date());
        //coordinates
        Coordinates coordinates = new Coordinates(40.99467838829218, 29.23762479300658);

        //all in one
        PrayerTimes prayerTimes = new PrayerTimes(coordinates, date, params);

        //to get data
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        formatter.setTimeZone(TimeZone.getDefault());

        String[] sunrise_time = formatter.format(prayerTimes.sunrise).split(":");
        int sunrise_time_hr = Integer.parseInt(sunrise_time[0]);
        int sunrise_time_min = Integer.parseInt(sunrise_time[1]);

        String[] sunrise_dhuhr = formatter.format(prayerTimes.dhuhr).split(":");
        int sunrise_dhuhr_hr = Integer.parseInt(sunrise_dhuhr[0]);
        int sunrise_dhuhr_min = Integer.parseInt(sunrise_dhuhr[1]);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat f = new SimpleDateFormat("hh:mm a");
        f.setTimeZone(TimeZone.getDefault());
        if (f.format(prayerTimes.dhuhr).split(" ")[1].equals("ÖS")) {
            sunrise_dhuhr_hr = 12+Integer.parseInt(sunrise_dhuhr[0]);
        }
        int finalSunrise_dhuhr_hr = sunrise_dhuhr_hr;

        String[] sunrise_asr = formatter.format(prayerTimes.asr).split(":");
        int sunrise_asr_hr = 12+Integer.parseInt(sunrise_asr[0]);
        int sunrise_asr_min = Integer.parseInt(sunrise_asr[1]);

        String[] sunrise_maghrib = formatter.format(prayerTimes.maghrib).split(":");
        int sunrise_maghrib_hr = 12+Integer.parseInt(sunrise_maghrib[0]);
        int sunrise_maghrib_min = Integer.parseInt(sunrise_maghrib[1]);

        String[] sunrise_isha = formatter.format(prayerTimes.isha).split(":");
        int sunrise_isha_hr = 12+Integer.parseInt(sunrise_isha[0]);
        int sunrise_isha_min = Integer.parseInt(sunrise_isha[1]);

        int[] hrs = {sunrise_time_hr, finalSunrise_dhuhr_hr, sunrise_asr_hr, sunrise_maghrib_hr, sunrise_isha_hr};
        int[] mins = {sunrise_time_min, sunrise_dhuhr_min, sunrise_asr_min, sunrise_maghrib_min, sunrise_isha_min};

        for (int id=0; id<hrs.length; id++) {
            System.out.println("prayer_times: "+hrs[id]+"--"+mins[id]);
            Calendar c = Calendar.getInstance(TimeZone.getDefault());
            c.set(Calendar.HOUR_OF_DAY, hrs[id]);
            c.set(Calendar.MINUTE, mins[id]);
            c.set(Calendar.SECOND, 0);

            @SuppressLint("UnspecifiedImmutableFlag")
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(Kus_Sutu_display.this,
                    id,
                    new Intent(Kus_Sutu_display.this, NotificationB.class),
                    PendingIntent.FLAG_ONE_SHOT);

//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
//                    c.getTimeInMillis(),
//                    pendingIntent1); // battery-killer

            /** battery-saver
             * seçenek olarak konulur
             * 1-10 arası isteğe bağlı sayıda günlük olarak ...
             */
            /*alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    c.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent1);*/

            /** battery-killer like an ALARM
             * eğer olursa seçenek olarak NAMAZ VAKİTLERİNDE BİLDİRİM diye konulabilir
             * Not olarak da dikkat bataryayı sömürebilir, diye eklenebilir.
             */
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    c.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent1);

            /*
            Intent intent_notfy2 = new Intent(Kus_Sutu_display.this, NotificationB.class);
            //intent_notfy2.putExtra("bildirim_2", 1);
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(Kus_Sutu_display.this, 1, intent_notfy2, PendingIntent.FLAG_ONE_SHOT);

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c2.getTimeInMillis(), pendingIntent2);
            */
        }

        /*for (int id=0; id<numberPicker.getValue(); id++) {
            Calendar c = Calendar.getInstance(TimeZone.getDefault());
            c.set(Calendar.HOUR_OF_DAY, randomNumber.nextInt(12));
            c.set(Calendar.MINUTE, randomNumber.nextInt(60));

            @SuppressLint("UnspecifiedImmutableFlag")
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(Kus_Sutu_display.this,
                    id,
                    new Intent(Kus_Sutu_display.this, NotificationB.class),
                    PendingIntent.FLAG_ONE_SHOT);

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    c.getTimeInMillis(),
                    pendingIntent1);
        }*/

    }

    private void hide_screen_things() {
        Objects.requireNonNull(actionBar).hide(); //hide the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen

        window_transparent_CUTOUT();

        im_btn_list_arama_fav.setVisibility(View.INVISIBLE);
        im_btn_share_display.setVisibility(View.INVISIBLE);
        if (tefeul_bool)
            im_btn_tefeul_display.setVisibility(View.GONE);
        else
            im_btn_tefeul_display.setVisibility(View.INVISIBLE);

        im_btn_fav_display.setVisibility(View.INVISIBLE);
        im_btn_fav_archieve.setVisibility(View.INVISIBLE);
        im_btn_backgr_display.setVisibility(View.INVISIBLE);
        if (!fav_bool_KS && !tefeul_bool)
            rel_bottom.setBackground(null);
    }


    public class ImageFontAdapter extends BaseAdapter {

        private Context context;
        Typeface typeface1;

        ImageView image;
        TextView text;
        RelativeLayout rel_resolution;

        ViewHolder2 viewHolder2;
        ViewGroup.LayoutParams layoutParams;

        public ImageFontAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return backgr_images.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            if (view == null) {
                //view = LayoutInflater.from(context).inflate(R.layout.bckgr_fnt_item, viewGroup, false);
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bckgr_fnt_item, viewGroup, false);
                viewHolder2 = new ViewHolder2(view);
                view.setTag(viewHolder2);
            } else {
                viewHolder2 = (ViewHolder2) view.getTag();
            }

            ///resolution
            layoutParams = rel_resolution.getLayoutParams();
            layoutParams.width = dpWidth/4;
            layoutParams.height = dpHeight/4;
            ///


            //final Typeface[] typeface = {ResourcesCompat.getFont(getApplicationContext(), 0)};
            //typeface[0] = ResourcesCompat.getFont(getApplicationContext(), tv_fonts[position]);
            //textView.setTypeface(typeface[position]);

            typeface1 = Typeface.createFromAsset(getAssets(), tv_fonts[position]);
            text.setTypeface(typeface1);

            //image.setImageResource(backgr_images[0]);
            ///BitmapFactory.Options options = new BitmapFactory.Options();
            ///options.inSampleSize = 8;
            ///Bitmap preview_bitmap = BitmapFactory.decodeStream(is, null, options);

            /*
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inPurgeable = true;
            Bitmap.createScaledBitmap(srcBitmap, 80, 80, false);
            */

            //image.setImageResource(backgr_images[position]);
            //System.out.println("imagenumber: "+say);
            if (say <= backgr_images.length) {
                //System.out.println("imagenumber2:"+say+"-"+position);
                //image.setImageResource(backgr_images[position]);
                say += 1;
                Kus_Sutu_display.this.runOnUiThread(() -> {
                    //image.setImageDrawable(getAssetImg(360, backgr_images[position]));
                    image.setImageBitmap(getAssetBmp(false, 360, backgr_images[position]));
                });

            }
            image.setOnClickListener(view1 -> {
                chkbx_auto1.setChecked(true);
                chkbx_auto0.setChecked(true);
                chkbx_auto2.setChecked(true);
                chkbx_auto3.setChecked(true);

                //imageView.setImageResource(backgr_images[position]);
                //imageView.setImageDrawable(getAssetImg(960, backgr_images[position]));
                //imageView.setImageDrawable(getAssetImgBackg(backgr_images[position]));
                imageView.setImageBitmap(getAssetBmp(true, 0, backgr_images[position]));

                editor_display.putInt("back_image_no", position).apply();

                //Typeface typeface2 = ResourcesCompat.getFont(getApplicationContext(), tv_fonts[position]);
                //typeface1 = ResourcesCompat.getFont(getApplicationContext(), tv_fonts[position]);
                typeface1 = Typeface.createFromAsset(getAssets(), tv_fonts[position]);
                my_adapter.display.setTypeface(typeface1);

                //editor_display.putInt("background_image", backgr_images[position]);
                editor_display.putString("background_image", backgr_images[position]);
                editor_display.putString("background_font", tv_fonts[position]);
                editor_display.apply();

                Objects.requireNonNull(pager.getAdapter()).notifyDataSetChanged();
            });

            return view;
        }

        public class ViewHolder2 {
            ViewHolder2(View v) {
                image = v.findViewById(R.id.imgV_bckg);
                //image.setImageDrawable(getAssetImg(360, backgr_images[0]));
                image.setImageBitmap(getAssetBmp(false, 360, backgr_images[0]));
                text = v.findViewById(R.id.tv_font);
                rel_resolution = v.findViewById(R.id.rel_lay_recyle3);
            }
        }
    }

    public Drawable getAssetImgBackg(String filename) {
        try {
            return Drawable.createFromStream(getAssets().open(filename), null);
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    public static Drawable getAssetImage(Context context, String filename) throws IOException {
        //InputStream buffer = new BufferedInputStream((context.getResources().getAssets().open("drawable/" + filename + ".png")));
        InputStream is = new BufferedInputStream((context.getResources().getAssets().open(filename)));
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    //private Bitmap decodeFile(Context context, File f) throws IOException {
    private Drawable getAssetImg(int IMAGE_MAX_SIZE, String filename){
        Bitmap b = null;

        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            InputStream fis = getAssets().open(filename);

            /*AssetFileDescriptor assetFileDescriptor = getAssets().openFd(filename);
            FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
            //FileInputStream fis = fileDescriptor.createInputStream();
            FileInputStream fis = new FileInputStream(fileDescriptor);

            //FileInputStream fis = new FileInputStream(f);*/
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();

            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int)Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE /
                        (double) Math.max(o.outHeight, o.outWidth)) /
                        Math.log(0.5)));
            }

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            //fis = new FileInputStream(fileDescriptor);
            fis = getAssets().open(filename);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();

            //return b;
            System.out.println("heyheyy 1");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("heyheyy 2");
        }

        return new BitmapDrawable(getResources(), b);
    }

    private Bitmap getAssetBmp(boolean real, int IMAGE_MAX_SIZE, String filename) {
        Bitmap bmp = null;

        try {
            BitmapFactory.Options o;
            BitmapFactory.Options o2;
            InputStream fis = getAssets().open(filename);

            o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            if (real) { //Decode image size
                bmp = BitmapFactory.decodeStream(fis);
            }
            else {      //Decode with inSampleSize
                BitmapFactory.decodeStream(fis, null, o);
                int scale = 1;
                if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                    scale = (int)Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE /
                            (double) Math.max(o.outHeight, o.outWidth)) /
                            Math.log(0.5)));
                }

                fis = getAssets().open(filename);
                o2 = new BitmapFactory.Options();
                o2.inSampleSize = scale;

                bmp = BitmapFactory.decodeStream(fis, null, o2);
            }

            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bmp;
    }


    /*public Integer[] backgr_images = {
        R.drawable.b29, R.drawable.b30, R.drawable.b33, R.drawable.b34,
        R.drawable.b37, R.drawable.b40, R.drawable.b1, R.drawable.b3,
        R.drawable.b4, R.drawable.b5, R.drawable.b6, R.drawable.b7,
        R.drawable.b8, R.drawable.b10, R.drawable.b11, R.drawable.b12,
        R.drawable.b13, R.drawable.b14, R.drawable.b15, R.drawable.b16,
        R.drawable.b17, R.drawable.b18, R.drawable.b20, R.drawable.b21,
        R.drawable.b22, R.drawable.b23, R.drawable.b24, R.drawable.b25,
        R.drawable.b26, R.drawable.b27, R.drawable.b28, R.drawable.b31,
        R.drawable.b32
    };*/

    public String[] backgr_images = {
        "images/b29.jpeg", "images/b30.jpeg", "images/b33.jpeg", "images/b34.jpeg",
        "images/b37.jpeg", "images/b40.jpeg", "images/b1.jpeg", "images/b3.jpeg",
        "images/b4.jpeg", "images/b5.jpeg", "images/b6.jpeg", "images/b7.jpeg",
        "images/b8.jpeg", "images/b10.jpeg", "images/b11.jpeg", "images/b12.jpeg",
        "images/b13.jpeg", "images/b14.jpeg", "images/b15.jpeg", "images/b16.jpeg",
        "images/b17.jpeg", "images/b18.jpeg", "images/b20.jpeg", "images/b21.jpeg",
        "images/b22.jpeg", "images/b23.jpeg", "images/b24.jpeg", "images/b25.jpeg",
        "images/b26.jpeg", "images/b27.jpeg", "images/b28.jpeg", "images/b31.jpeg",
        "images/b32.jpeg"
    };

    public String[] tv_fonts = {
        "font/roboto_serif_vrb.ttf",
        "font/comfortaa_vrb.ttf",
        "font/abril_fatface_regular.ttf",
        "font/alfa_slab_one_regular.ttf",
        "font/caveat_vrb.ttf",
        "font/exo_italic_vrb.ttf",
        "font/exo_vrb.ttf",
        "font/play_bold.ttf",
        "font/righteous_regular.ttf",
        "font/sansita_swashed_vrb.ttf",
        "font/aclonica.ttf",
        "font/ebgaramond_variablefont_wght.ttf",
        "font/jetbrainsmono_variablefont_wght.ttf",
        "font/lobster_regular.ttf",
        "font/baloo.ttf",
        "font/merriweather_light.ttf",
        "font/montserrat__extra_light.ttf",
        "font/ptserif_regular.ttf",
        "font/pushster_regular.ttf",
        "font/roboto_regular.ttf",
        "font/titilliumweb_regular.ttf",
        "font/ubuntu_bold.ttf",
        "font/ubuntu_italic.ttf",
        "font/ubuntu_light.ttf",
        "font/ubuntu_bold_italic.ttf",
        "font/ubuntu_light_italic.ttf",
        "font/ubuntu_medium_italic.ttf",
        "font/ubuntu_regular.ttf",
        "font/vujahday_script_regular.ttf",
        "font/waterfall_regular.ttf",
        "font/anton_regular.ttf",
        "font/roboto_mono_italic.ttf",
        "font/roboto_mono_vrb.ttf",
        "font/roboto_serif_italic.ttf",
        "font/league_gothic_regular.ttf",
        "font/shadows_into_light_regular.ttf"
    };

    public class MyFontAdapter extends ArrayAdapter<String> {

        //ImageFontAdapter imageFontAdapter = new ImageFontAdapter(Kus_Sutu_display.this);

        public MyFontAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            //Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), imageFontAdapter.tv_fonts[position]);
            //Typeface typeface = Typeface.createFromAsset(getAssets(), imageFontAdapter.tv_fonts[position]);
            Typeface typeface = Typeface.createFromAsset(getAssets(), tv_fonts[position]);
            view.setTypeface(typeface);
            return view;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            //Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), imageFontAdapter.tv_fonts[position]);
            //Typeface typeface = Typeface.createFromAsset(getAssets(), imageFontAdapter.tv_fonts[position]);
            Typeface typeface = Typeface.createFromAsset(getAssets(), tv_fonts[position]);
            view.setTypeface(typeface);
            return view;
        }

        @NonNull
        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    return null;
                }
                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                }
            };
        }
    }

    public class MyAutoCompleteAdapter extends ArrayAdapter<String> {
        public MyAutoCompleteAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
            super(context, resource, objects);
        }
        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    return null;
                }
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                }
            };
        }
    }

    public class My_adapter_infinite extends PagerAdapter {

        ArrayList<Kus_Sutu_Model> lstQuotes;
        int selectorX;
        Context context;
        LayoutInflater layoutInflater;
        Activity activity;

        TextView display;
        boolean locked = false;
        int pageIndex;

        View view;
        TextView tv_number;
        RelativeLayout rel_backgr_font;
        long clickTime;
        long[] lastClickTime;

        ConstraintLayout const_viewpager;
        boolean const_bool = true;


        public My_adapter_infinite(ArrayList<Kus_Sutu_Model> lstQuotes, int selectorX, Activity activity/* Context context*/) {
            this.lstQuotes = lstQuotes;
            this.selectorX = selectorX;
            this.activity = activity;
            //this.layoutInflater = LayoutInflater.from(context);
            this.layoutInflater = LayoutInflater.from(this.activity);

            if (tefeul_bool)
                const_bool = false;

            //sharedpreferences_display2 = context.getSharedPreferences(MyPREFERENCES__DISPLAY2, MODE_PRIVATE);
            sharedpreferences_display2 = this.activity.getSharedPreferences(MyPREFERENCES__DISPLAY2, MODE_PRIVATE);
            editor_display2 = sharedpreferences_display2.edit();

        }

        public void setLocked(boolean locked, int page) {
            this.locked = locked;
            pageIndex = page;
        }

        @Override
        public int getCount() {
            if (locked)
                return 1;
            return lstQuotes.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            //return super.getItemPosition(object);
            return POSITION_NONE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view.equals(object);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @SuppressLint("ClickableViewAccessibility")
        @RequiresApi(api = Build.VERSION_CODES.O)
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            view = layoutInflater.inflate(R.layout.infinite_pager_quote, container, false);

            display = view.findViewById(R.id.tv_display);

            if (back_display_bool) {
                display_layout_instantiate();
                back_display_bool = false;
            }

            ////
            if (only_text_bool) {
                imageView.setVisibility(View.INVISIBLE);
                display.setTextColor(Color.WHITE);
            }

            display.setText(lstQuotes.get((/*selectorX+*/position)).getQuote());
            if (sharedpreferences_display.getBoolean("check_bool_2", false))
                hexColor = "#FFFFFF"; // veya her bckgr için özel renk kodu neyse o!
            else
                display.setTextColor(Color.parseColor(hexColor));

            if (sharedpreferences_display.getBoolean("check_bool_0", false))
                TextViewCompat.setAutoSizeTextTypeWithDefaults(my_adapter.display, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            else
                display.setTextSize(TypedValue.COMPLEX_UNIT_SP, our_text_size);

            if (sharedpreferences_display.getBoolean("check_bool_1", false))
                //txt_logo.setTypeface(Typeface.createFromAsset(getAssets(), sharedpreferences_display.getString("manual_text_font", "ubuntu_medium.ttf")));
                typeface2 = Typeface.createFromAsset(getAssets(), sharedpreferences_display2.getString("background_font", "font/ubuntu_medium.ttf"));
            else
                typeface2 = Typeface.createFromAsset(getAssets(), sharedpreferences_display2.getString("manual_text_font", "font/ubuntu_medium.ttf"));
            /////
            display.setTypeface(typeface2);
            txt_logo.setTypeface(typeface2);
            txt_author.setTypeface(typeface2);


            const_viewpager = view.findViewById(R.id.const_viewpager);

            if (tefeul_bool) {
                display.setText(lstQuotes.get((selectorX)).getQuote());
                ///const_viewpager.setBackgroundResource(R.drawable.roundcorner_tefeul);
            }  //const_viewpager.setBackgroundResource(null);

            tv_number = activity.findViewById(R.id.tv_page_nmbr);
            ////rel_backgr_font = activity.findViewById(R.id.rel_backgr_font);

            lastClickTime = new long[]{0};

            const_viewpager.setOnClickListener(view12 -> {
                say = 0;
                clickTime = System.currentTimeMillis();

                if (clickTime - lastClickTime[0] < DOUBLE_CLICK_TIME_DELTA){ // Double Click
                    //Toast.makeText(Kus_Sutu_display.this, "çift tıklama", Toast.LENGTH_SHORT).show();
                    lastClickTime[0] = 0;
                }
                else { //Single Click
                    if (stub_display != null)
                        stub_display.setVisibility(View.GONE);

                    if (const_bool) { //HIDE
                        const_bool = false;
                        hide_screen_things();
                        if (btnSetting_bool) {
                            btnSetting_bool = false;
                            share_close(coznrlk_str[0]);
                        }
                    }
                    else { //SHOW
                        System.out.println("tekrar tıklandı");
                        const_bool = true;
                        Objects.requireNonNull(actionBar).show(); //show the title bar
                        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //show statusbar (deliberately commented for space above title bar)

                        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //(deliberately commented for space above title bar)

                        im_btn_list_arama_fav.setVisibility(View.VISIBLE);
                        im_btn_share_display.setVisibility(View.VISIBLE);
                        if (tefeul_bool) {
                            im_btn_tefeul_display.setVisibility(View.GONE);
                            //const_viewpager.setBackgroundResource(R.drawable.roundcorner_tefeul);
                        }
                        else {
                            im_btn_tefeul_display.setVisibility(View.VISIBLE);
                        }

                        im_btn_fav_display.setVisibility(View.VISIBLE);
                        im_btn_backgr_display.setVisibility(View.VISIBLE);
                        if (!fav_bool_KS && !tefeul_bool)
                            rel_bottom.setBackgroundColor(Color.parseColor("#9919485a"));
                    }
                }
                lastClickTime[0] = clickTime;
            });

            const_viewpager.setOnTouchListener((view14, motionEvent) -> {
                if (tefeul_bool)
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                        //case MotionEvent.ACTION_MOVE:
                            const_viewpager.setBackgroundResource(R.drawable.roundcorner_tefeul);
                            break;
                        case MotionEvent.ACTION_UP:
                            const_viewpager.setBackgroundResource(0);
                            break;
                        default:
                            break;
                    }
                return false;   //false means Click and LongClick also can work too.
            });

            const_viewpager.setOnLongClickListener(view1 -> {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("clip_label", lstQuotes.get(position).getQuote()+ " \n\nAhmed İhsan GENÇ, Kuş Sütü ("+lstQuotes.get(position).getNo()+".)");
                clipboard.setPrimaryClip(clip);

                Toast.makeText(Kus_Sutu_display.this, "Kuş Sütü kopyalandı.", Toast.LENGTH_SHORT).show();
                return true;
            });

            if (lstQuotes.get(position).isFavorite()) {
                //im_btn_save.setImageResource(R.drawable.ic_baseline_favorite_red_36);
            } else {
                //im_btn_save.setImageResource(R.drawable.ic_baseline_favorite_36);
            }

            container.addView(view);
            return view;
        }

    }

    private void window_transparent_CUTOUT() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void onBackPressed() {
        /*if (!tefeul_bool) {
            new AlertDialog.Builder(this)
                    //.setIcon(android.R.drawable.ic_dialog_alert)
                    //.setTitle("Çıkış")
                    //.setCancelable(false)
                    .setMessage("Uygulamadan çıkmak istediğinizden emin misiniz?")
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("Hayır", null)
                    .show();
        } else*/
            super.onBackPressed();

    }
}