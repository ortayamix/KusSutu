package com.example.tefeul;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.text.style.RelativeSizeSpan;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Arama_Fav_Activity extends AppCompatActivity {

    private SearchView search;
    CharacterStyle cs;
    int start, end;
    String search_query = "";

    ImageButton im_setting, im_fav, im_tfl, im_quote, im_btn_back;
    MenuItem word_case_item;
    boolean word_case_bool;
    boolean arama_bool = false;
    boolean fav_bool = false;

    ColorStateList spanColor;

    private RecyclerView recyclerView;
    private AdapterArama adapter;
    private ArrayList<Kus_Sutu_Model> kus_sutu_quotesArrayList, kus_sutu_quotesArrayList_fav;

    private LinearLayoutManager mLayoutManager;
    int lastPosition;

    ImageButton fab_go_page;
    TextView tv_fav_empty;
    Button btn_fav_explore;

    private static final int VERY_SHORT_DELAY = 100; // 100 miliseconds

    int LAUNCH_FAV_ACTIVITY = 1;


    public static final String MyPREFERENCES__DISPLAY = "MyPrefs_display" ;
    SharedPreferences sharedpreferences_display;
    SharedPreferences.Editor editor_display;

    @SuppressLint({"NotifyDataSetChanged", "UseCompatLoadingForColorStateLists"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arama_fav);
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar

        sharedpreferences_display = getSharedPreferences(MyPREFERENCES__DISPLAY, MODE_PRIVATE);
        editor_display = sharedpreferences_display.edit();

        search = findViewById(R.id.search);
        im_setting = findViewById(R.id.im_setting);
        im_fav = findViewById(R.id.im_fav);
        im_tfl = findViewById(R.id.im_tfl);
        im_quote = findViewById(R.id.im_quote);
        im_btn_back = findViewById(R.id.im_btn_back);
        recyclerView = findViewById(R.id.RV_quotes);
        fab_go_page = findViewById(R.id.fab_go_page);
        tv_fav_empty = findViewById(R.id.tv_fav_empty);
        btn_fav_explore = findViewById(R.id.btn_fav_explore);

        fab_go_page.setVisibility(View.VISIBLE);
        tv_fav_empty.setVisibility(View.INVISIBLE);
        btn_fav_explore.setVisibility(View.INVISIBLE);

        word_case_bool = sharedpreferences_display.getBoolean("word_case", false);
        if (getIntent().getBooleanExtra("favori_act", false)) {
            fav_bool = true;
            arama_bool = false;
            im_quote.setVisibility(View.VISIBLE);
            im_fav.setVisibility(View.GONE);
            //rel_lay_recyle.setBackgroundColor(Color.parseColor("#a08b4a"));
        }
        else /*if (getIntent().getBooleanExtra("arama_act", false)) */{
            arama_bool = true;
            fav_bool = false;
            im_fav.setVisibility(View.VISIBLE);
            im_quote.setVisibility(View.GONE);
            //rel_lay_recyle.setBackgroundColor(Color.BLUE);//99cccc
        }

        // builder
        String[] kus_sutleri = getResources().getStringArray(R.array.kus_sutu);
        kus_sutu_quotesArrayList = new ArrayList<>();
        kus_sutu_quotesArrayList_fav = new ArrayList<>();
        for (int i=0; i<kus_sutleri.length; i++) {
            String quote = kus_sutleri[i];
            Kus_Sutu_Model kus_sutu_quotes = new Kus_Sutu_Model(this, quote, i+1, sharedpreferences_display.getBoolean("fav_"+i, false), sharedpreferences_display.getInt("last_saved_position", 0));

            if (arama_bool)
                kus_sutu_quotesArrayList.add(kus_sutu_quotes);
            else if (fav_bool && (sharedpreferences_display.getBoolean("fav_"+i, false))) {
                kus_sutu_quotesArrayList_fav.add(kus_sutu_quotes);
            }
        }

        if (arama_bool) {
            adapter = new AdapterArama(kus_sutu_quotesArrayList, Arama_Fav_Activity.this, null);

            //spanColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.RED}); //0xffa10901
        }
        else if (fav_bool) {
            adapter = new AdapterArama(kus_sutu_quotesArrayList_fav, Arama_Fav_Activity.this, null);

            if (kus_sutu_quotesArrayList_fav.isEmpty()) {
                tv_fav_empty.setVisibility(View.VISIBLE);
                btn_fav_explore.setVisibility(View.VISIBLE);
            }
            //spanColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.GREEN}); //0xffa10901
        }
        spanColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.BLUE}); //0xffa10901


        new ItemTouchHelper(itemTouchAddDeleteFav).attachToRecyclerView(recyclerView);
        new ItemTouchHelper(itemTouchShare).attachToRecyclerView(recyclerView);

        //new ItemTouchHelper(itemTouchShare).attachToRecyclerView(recyclerView);
        recyclerView.setHasFixedSize(true);
        Arama_Fav_Activity.this.runOnUiThread(() -> {
            recyclerView.setAdapter(adapter);
        });
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        if (arama_bool)
            //recyclerView.scrollToPosition(sharedpreferences_display.getInt("last_saved_position", 0));
            recyclerView.scrollToPosition(getIntent().getIntExtra("shift_to_same_quote", sharedpreferences_display.getInt("last_saved_position", 0)));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                lastPosition = mLayoutManager.findFirstVisibleItemPosition();
            }

        });

        if (getIntent().getBooleanExtra("search_boolean", false)) {
            //search_popup();
            search.setIconified(false);
        }

        search.setOnSearchClickListener(view -> {
            search_popup();
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(Arama_Fav_Activity.this, adapter.getItemCount()+" sonuç bulundu.", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //filter(newText);
                search_query = newText.toLowerCase();
                adapter.getFilter().filter(newText);
                //FancyToast.makeText(Arama_Fav_Activity.this, adapter.getItemCount()+" sonuç bulundu.",50, FancyToast.INFO, false).show();
                //Toast.makeText(Arama_Fav_Activity.this, adapter.getItemCount()+" sonuç bulundu.", 50).show();
                return false;
            }
        });
        search.setOnCloseListener(() -> {
            //InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            //imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

            search.setFocusable(false);
            fab_go_page.setVisibility(View.VISIBLE);
            //search.clearFocus();

            mLayoutManager.scrollToPositionWithOffset(sharedpreferences_display.getInt("last_saved_position", 0), 0);

            //search.clearFocus();
            //search.setIconified(true); // niye hata veriyor???
            return false;  // burası false olması gerekiyor. true olunca icon olmadı, return false, olması acaba ikonun default olan true değerini mi çağırıyor.
        });

        /*int searchCloseButtonId = search.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = this.search.findViewById(searchCloseButtonId);
        closeButton.setOnClickListener(v -> {
            if (v != null) {
                search.setQuery("", false);

                if (!search.isIconified()) {
                    search.setIconified(true);
                }
                search.setFocusable(false);
                //search.clearFocus();


                mLayoutManager.scrollToPositionWithOffset(sharedpreferences_display.getInt("last_saved_position", 0), 0);


                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


                //recyclerView.scrollToPosition(sharedpreferences_display.getInt("last_saved_position", 0));

            }
        });*/

        im_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_fav_explore.setOnClickListener(v -> {
            finish();
            //startActivity(new Intent(Arama_Fav_Activity.this, Arama_Fav_Activity.class));
        }); // IF FAV EMPTY

        im_btn_back.setOnClickListener(view ->
                Arama_Fav_Activity.super.onBackPressed()
        );

        im_quote.setOnClickListener(v -> {
            //Intent intent_returnToQuote = new Intent();
            //setResult(Activity.RESULT_CANCELED, intent_returnToQuote);
            finish();
            //startActivity(new Intent(Arama_Fav_Activity.this, Arama_Fav_Activity.class));
        });

        im_fav.setOnClickListener(v -> {
            //finish();
            Intent intent_f = new Intent(Arama_Fav_Activity.this, Arama_Fav_Activity.class);
            intent_f.putExtra("favori_act", true);
            //startActivityForResult(intent_f, LAUNCH_FAV_ACTIVITY);
            startActivity(intent_f);
        });

        im_tfl.setOnClickListener(v -> {
            startActivity(new Intent(Arama_Fav_Activity.this, Fatiha_ihlas.class));
        });


        // filtrelendiğinde gitmesi zor
        // favorilerde getNo ya göre arayamıyor
        if (fav_bool)
            fab_go_page.setVisibility(View.INVISIBLE);
        else {
            fab_go_page.setVisibility(View.VISIBLE);
            fab_go_page.setOnClickListener(v -> {
                AlertDialog.Builder sayfa_builder = new AlertDialog.Builder(Arama_Fav_Activity.this);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_save__go, null);
                sayfa_builder.setView(dialogView);
                sayfa_builder.setTitle("Söze Git");

                ImageButton im_bookmark = dialogView.findViewById(R.id.im_bookmark);
                TextView tx_bookmark = dialogView.findViewById(R.id.tx_bookmark);
                Button btn_go_saved = dialogView.findViewById(R.id.btn_go_saved);
                EditText editText = dialogView.findViewById(R.id.ed_sayfa);
                TextView tv_cancel = dialogView.findViewById(R.id.tv_cancel);
                Button btn_go = dialogView.findViewById(R.id.btn_go);
                im_bookmark.setVisibility(View.GONE);
                tx_bookmark.setVisibility(View.GONE);

                btn_go.setOnClickListener(view -> {
                });

                tv_cancel.setOnClickListener(view -> {
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
                            int list_len = kus_sutu_quotesArrayList.size();
                            if (list_len == 0)
                                list_len = 2769;
                            if (Integer.parseInt(editText.getText().toString()) > list_len) {
                                FancyToast.makeText(Arama_Fav_Activity.this, list_len + " dan fazla sayı giremezsiniz!", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                                btn_go.setEnabled(false);
                                btn_go.setBackgroundTintList(getResources().getColorStateList(R.color.colorGray_my));
                            }
                        } else {
                            btn_go.setEnabled(false);
                            btn_go.setBackgroundTintList(getResources().getColorStateList(R.color.colorGray_my));
                        }
                    }
                });

            /*if (fav_bool) {
                FancyToast.makeText(Arama_Fav_Activity.this, "Burada istenen numaraya gidemeyebilirsiniz", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                btn_go_saved.setEnabled(false);
                btn_go_saved.setBackgroundTintList(getResources().getColorStateList(R.color.colorGray_my));
            }
            else {
                btn_go_saved.setEnabled(true);
                btn_go_saved.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary_my));
            }*/

                btn_go_saved.setOnClickListener(v1 -> {
                    search.setQuery("", false);
                    search.clearFocus();

                    int last_saved_ps = sharedpreferences_display.getInt("last_saved_position", 0);
                    recyclerView.scrollToPosition(last_saved_ps);
                    Toast.makeText(Arama_Fav_Activity.this, "Son kalınan yer: "+(last_saved_ps+1)+". söz", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                });

                tv_cancel.setOnClickListener(view -> {
                    alertDialog.dismiss();
                });

                btn_go.setOnClickListener(view -> {
                    recyclerView.scrollToPosition(Integer.parseInt(editText.getText().toString())-1);
                    alertDialog.dismiss();
                });

            });
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void search_popup() {
        fab_go_page.setVisibility(View.INVISIBLE);

        PopupMenu popup = new PopupMenu(Arama_Fav_Activity.this, search);
        popup.getMenuInflater().inflate(R.menu.pop_up_search, popup.getMenu());
        word_case_item = popup.getMenu().findItem(R.id.item_word_begin);

        word_case_item.setChecked(sharedpreferences_display.getBoolean("word_case", false));
        word_case_item.setOnMenuItemClickListener(item -> {
            if (word_case_item.isChecked()) {
                word_case_item.setChecked(false);
                editor_display.putBoolean("word_case", false);
                word_case_bool = false;
            } else {
                word_case_item.setChecked(true);
                editor_display.putBoolean("word_case", true);
                word_case_bool = true;
            }
            editor_display.apply();

            adapter.getFilter().filter(search_query);
            adapter.notifyDataSetChanged();
            //Toast.makeText(Arama_Fav_Activity.this, adapter.getItemCount()+" sonuç bulundu.", Toast.LENGTH_SHORT).show();
            return true;
        });

        popup.setGravity(Gravity.START);
        popup.show();
    }

    private void filter (String text) {
        ArrayList<Kus_Sutu_Model> filteredList = new ArrayList<>();

        for (Kus_Sutu_Model item : kus_sutu_quotesArrayList) {
            if (item.getQuote().toLowerCase().contains(text.toLowerCase()) || String.valueOf(item.getNo()).contains(text)) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(Arama_Fav_Activity.this, "Bulunamadı..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredList);
        }

    }

    public class AdapterArama extends RecyclerView.Adapter<AdapterArama.ViewHolder> implements Filterable {

        private ArrayList<Kus_Sutu_Model> quotesArrayList;
        private ArrayList<Kus_Sutu_Model> quotesArrayListFiltered;
        private Context context;
        View.OnClickListener listener;


        public AdapterArama(ArrayList<Kus_Sutu_Model> quotesArrayList, Context context, View.OnClickListener listener) {
            this.quotesArrayList = quotesArrayList;
            this.quotesArrayListFiltered = quotesArrayList;
            this.context = context;
            this.listener = listener;
        }

        @SuppressLint("NotifyDataSetChanged")
        public void filterList(ArrayList<Kus_Sutu_Model> filter_list) {
            quotesArrayList = filter_list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public AdapterArama.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcycle_item, parent, false);

            return new ViewHolder(view);
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onBindViewHolder(@NonNull AdapterArama.ViewHolder holder, int position) {
            Kus_Sutu_Model quotes = quotesArrayListFiltered.get(position);
            holder.tv_name.setText(quotes.getQuote());

            ///// spannable search result
            String originalText = quotes.getQuote();
            SpannableStringBuilder wordSpan = new SpannableStringBuilder(originalText);
            Pattern p = Pattern.compile(search_query, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(originalText.toLowerCase());

            try {
                while (m.find()) {
                    int wordStart = m.start();
                    int wordEnd = m.end();

                    TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, spanColor, null);

                    if (word_case_bool) {
                        if (wordStart == 0) {
                            wordSpan.setSpan(highlightSpan, wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            //wordSpan.setSpan(new BackgroundColorSpan(0xFFFFFF48), wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            wordSpan.setSpan(new RelativeSizeSpan(1.0f), wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            wordSpan.setSpan(cs, start, end, 1);
                        } else {
                            if (!Character.isLetter(originalText.charAt(wordStart - 1))) {
                                wordSpan.setSpan(highlightSpan, wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                //wordSpan.setSpan(new BackgroundColorSpan(0xFFFFFF48), wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                wordSpan.setSpan(new RelativeSizeSpan(1.0f), wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                wordSpan.setSpan(cs, start, end, 1);
                            }
                        }

                    } else {
                        wordSpan.setSpan(highlightSpan, wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        //wordSpan.setSpan(new BackgroundColorSpan(0xFFFFFF48), wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        wordSpan.setSpan(new RelativeSizeSpan(1.0f), wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        wordSpan.setSpan(cs, start, end, 1);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.tv_name.setText(wordSpan, TextView.BufferType.SPANNABLE);
            holder.tv_description.setText(wordSpan, TextView.BufferType.SPANNABLE);
            ///


            /*if (scroll_bool) {
                System.out.println("here bool"+ quotes.getNo());
                search.setFocusable(false);
                search.clearFocus();
                recyclerView.scrollToPosition(sharedpreferences_display.getInt("last_saved_position", 0));
                new Handler().postDelayed(() -> {
                    recyclerView.smoothScrollToPosition(sharedpreferences_display.getInt("last_saved_position", 0));
                }, 50); //sometime not working, need some delay
                scroll_bool = false;
            }*/


            if (sharedpreferences_display.getInt("last_saved_position", 0) == quotes.getNo()-1) {
                System.out.println("here bookmarked: "+(quotes.getNo()-1));
                holder.img_btn_save_fav.setImageResource(R.drawable.ic_baseline_bookmarked_24);
            }
            else {
                System.out.println("here none_bookmark");
                quotes.setNo_of_Bookmark(0);
                holder.img_btn_save_fav.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
            }

            holder.img_btn_save_fav.setOnClickListener(v -> {
                if (sharedpreferences_display.getInt("last_saved_position", 0) == quotes.getNo()-1) {
                    System.out.println("here bookmarked: "+(quotes.getNo()-1));
                    holder.img_btn_save_fav.setImageResource(R.drawable.ic_baseline_bookmarked_24);
                }
                else {
                    System.out.println("here none_bookmark");
                    quotes.setNo_of_Bookmark(0);
                    holder.img_btn_save_fav.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
                }

                if (quotes.getNo_of_Bookmark() == (quotes.getNo()-1)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Arama_Fav_Activity.this, R.style.MyAlertDialog);
                    builder.setIcon(android.R.drawable.stat_sys_warning);
                    builder.setTitle("Emin misiniz...");
                    builder.setMessage("Kayıtlı yeri sıfırlamak mı istiyorsunuz?");
                    builder.setPositiveButton("EVET", (dialog, which) -> {
                        holder.img_btn_save_fav.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
                        quotes.setNo_of_Bookmark(0);
                        editor_display.putInt("last_saved_position", 0).apply();
                        Toast.makeText(Arama_Fav_Activity.this, "Kayıtlı yer yok", Toast.LENGTH_SHORT).show();
                    });
                    builder.setNegativeButton("Hayır", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    alertDialog.getWindow().getDecorView().getBackground().setColorFilter(new LightingColorFilter(0xFF000000, 0x1A1A1A));
                    alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#6aa84f"));//10A3CD
                }
                else {
                    adapter.notifyItemChanged(sharedpreferences_display.getInt("last_saved_position", 0));
                    holder.img_btn_save_fav.setImageResource(R.drawable.ic_baseline_bookmarked_24);
                    quotes.setNo_of_Bookmark(quotes.getNo()-1);
                    editor_display.putInt("last_saved_position", (quotes.getNo()-1)).apply();

                    FancyToast.makeText(Arama_Fav_Activity.this, quotes.getNo()+". sözde kaldınız", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                }
                /*for (int i=0; i<quotesArrayList.size(); i++) {
                    System.out.println("here getnobookm: "+quotesArrayList.get(i).getNo_of_Bookmark());
                }*/
                //editor_display.putBoolean("bookmarked_" + position, false);
                adapter.notifyDataSetChanged();
            });

            holder.tv_description.setText("" + quotes.getNo());
            if (arama_bool) {
                if (quotes.isFavorite()) {
                    //holder.img_btn_search_fav.setImageResource(R.drawable.ic_baseline_favorite_red_24);
                    holder.tv_name.setBackgroundColor(Color.parseColor("#f22534")); //6aa84f
                    holder.rel_lay_recyle2.setBackgroundColor(Color.parseColor("#D9f22534")); //6aa84f
                }
                else {
                    //holder.img_btn_search_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                    holder.tv_name.setBackgroundColor(Color.parseColor("#6aa84f"));//a08b4a
                    holder.rel_lay_recyle2.setBackgroundColor(Color.parseColor("#D96aa84f"));//a08b4a %95--F2 %90--E6 %85--D9 ...%60--99
                }
            }

            /*holder.img_btn_search_fav.setOnClickListener(v -> {
                if (quotes.isFavorite()) {
                    holder.img_btn_search_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                    quotes.setFavorite(false);
                    editor_display.putBoolean("fav_" + position, false);
                    Toast.makeText(Arama_Fav_Activity.this, "kaldırıldı", Toast.LENGTH_SHORT).show();
                } else {
                    holder.img_btn_search_fav.setImageResource(R.drawable.ic_baseline_favorite_red_24);
                    quotes.setFavorite(true);
                    editor_display.putBoolean("fav_" + position, true);
                    Toast.makeText(Arama_Fav_Activity.this, "eklendi", Toast.LENGTH_SHORT).show();
                }
                editor_display.apply();
            });*/
            /*holder.img_btn_delete_fav.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(Arama_Fav_Activity.this, R.style.MyAlertDialog);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Emin misiniz...");
                builder.setMessage("Seçtiğiniz sözü favorilerden kaldırmak istediğinizden emin misiniz?");
                builder.setPositiveButton("SİL", (dialog, which) -> {
                    removeAt(position);
                    editor_display.putBoolean("fav_"+(quotes.getNo()-1), false).apply();
                    kus_sutu_quotesArrayList_fav.remove(quotes.getQuote());
                    Toast.makeText(Arama_Fav_Activity.this, quotes.getNo()+". söz silindi.", Toast.LENGTH_SHORT).show();

                    if (kus_sutu_quotesArrayList_fav.isEmpty()) {
                        tv_fav_empty.setVisibility(View.VISIBLE);
                        btn_fav_explore.setVisibility(View.VISIBLE);
                    }
                    System.out.println("here quote_fav: "+kus_sutu_quotesArrayList_fav.size());
                });
                builder.setNegativeButton("Vazgeç", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                alertDialog.getWindow().getDecorView().getBackground().setColorFilter(new LightingColorFilter(0xFF000000, 0x1A1A1A));
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#F22534"));//10A3CD
            });*/

            /*holder.img_btn_search_copy.setOnClickListener(v -> {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("clip_label", holder.tv_name.getText()+ " \n\nAhmed İhsan GENÇ, Kuş Sütü ("+quotes.getNo()+".)");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(Arama_Fav_Activity.this, "Panoya kopyalandı -> " + quotes.getNo(), Toast.LENGTH_SHORT).show();
            });*/
            /*holder.img_btn_search_share.setOnClickListener(v -> {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, holder.tv_name.getText()+ " \n\nAhmed İhsan GENÇ, Kuş Sütü ("+quotes.getNo()+".)");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            });*/

            /*holder.rel_lay_recyle.setOnClickListener(view -> {
                //.tv_name kullanıldı yerine
            });*/

            holder.tv_name.setOnClickListener(view -> {
                Intent intent_one_to_Quote = new Intent(Arama_Fav_Activity.this, Kus_Sutu_display.class);
                //intent_one_to_Quote.putExtra("item_clicked", true);
                if (arama_bool)
                    intent_one_to_Quote.putExtra("item_clicked_arama", true);
                else if (fav_bool)
                    intent_one_to_Quote.putExtra("item_clicked_fav", true);

                intent_one_to_Quote.putExtra("item_no", quotes.getNo()-1);
                startActivity(intent_one_to_Quote);
                //finish();
            });


            holder.tv_name.setOnLongClickListener(view -> {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("clip_label", holder.tv_name.getText()+ " \n\nAhmed İhsan GENÇ, Kuş Sütü ("+quotes.getNo()+".)");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(Arama_Fav_Activity.this, "Panoya kopyalandı -> " + quotes.getNo(), Toast.LENGTH_SHORT).show();
                return true;
            });
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return quotesArrayListFiltered.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public View view123;
            public TextView tv_name, tv_description;
            private ImageButton img_btn_search_fav, img_btn_search_share, img_btn_save_fav;
            public RelativeLayout rel_lay_recyle, rel_lay_recyle2;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                rel_lay_recyle = itemView.findViewById(R.id.rel_lay_recyle);
                rel_lay_recyle2 = itemView.findViewById(R.id.rel_lay_recyle_2);

                tv_name = itemView.findViewById(R.id.tv_rcyc_name);
                tv_description = itemView.findViewById(R.id.tv_recy_descr);
                //img_btn_search_copy = itemView.findViewById(R.id.img_btn_search_copy);
                //img_btn_search_share = itemView.findViewById(R.id.img_btn_search_share);
                //img_btn_search_fav = itemView.findViewById(R.id.img_btn_search_fav);
                //img_btn_delete_fav = itemView.findViewById(R.id.img_btn_delete_fav);
                img_btn_save_fav = itemView.findViewById(R.id.img_btn_save_fav);

                if (arama_bool) {
                    img_btn_save_fav.setVisibility(View.VISIBLE);
                    //img_btn_search_fav.setVisibility(View.VISIBLE);
                    //img_btn_delete_fav.setVisibility(View.INVISIBLE);
                    tv_name.setBackgroundColor(Color.parseColor("#6aa84f"));//a08b4a
                    rel_lay_recyle2.setBackgroundColor(Color.parseColor("#D96aa84f"));//a08b4a %95--F2 %90--E6 %85--D9 ...%60--99
                } else if (fav_bool) {
                    img_btn_save_fav.setVisibility(View.INVISIBLE);
                    //img_btn_search_fav.setVisibility(View.INVISIBLE);
                    //img_btn_delete_fav.setVisibility(View.VISIBLE);
                    tv_name.setBackgroundColor(Color.parseColor("#f22534")); //6aa84f
                    rel_lay_recyle2.setBackgroundColor(Color.parseColor("#D9f22534")); //6aa84f
                }
            }
        }

        @Override
        public Filter getFilter() {
            Filter filterX = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();

                    if (constraint == null || constraint.length() == 0) {
                        filterResults.count = quotesArrayList.size();
                        filterResults.values = quotesArrayList;
                        System.out.println("here boşş");
                    } else {
                        String searchStr = constraint.toString().toLowerCase();
                        ArrayList<Kus_Sutu_Model> resultFilteredData = new ArrayList<>();

                        for (Kus_Sutu_Model item : quotesArrayList) {
                            if (!word_case_bool && (item.getQuote().toLowerCase().contains(searchStr) || String.valueOf(item.getNo()).contains(searchStr))) {
                                if (!resultFilteredData.contains(item))
                                    resultFilteredData.add(item);
                            }

                            if (word_case_bool) {
                                //String g= item.getQuote().substring(0, item.getQuote().indexOf(' '));
                                //String[] strgs = item.getQuote().split(" ");
                                String[] strgs = item.getQuote().split("\\s+");
                                for (String str : strgs) {
                                    if (str.toLowerCase().startsWith(searchStr) || String.valueOf(item.getNo()).startsWith(searchStr)) {
                                        if (!resultFilteredData.contains(item))
                                            resultFilteredData.add(item);
                                    }
                                }
                            }
                            /*if ((item.getQuote().toLowerCase().startsWith(searchStr) || String.valueOf(item.getNo()).startsWith(searchStr))) {
                                resultFilteredData.add(item);
                            }*/

                            filterResults.count = resultFilteredData.size();
                            filterResults.values = resultFilteredData;
                        }
                    }
                    return filterResults;
                }

                @SuppressLint("NotifyDataSetChanged")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    quotesArrayListFiltered = (ArrayList<Kus_Sutu_Model>) results.values;
                    notifyDataSetChanged();
                }
            };
            return filterX;
        }

        public void removeAt(int position) {
            quotesArrayListFiltered.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, quotesArrayListFiltered.size());
        }

    }

    ItemTouchHelper.SimpleCallback itemTouchAddDeleteFav = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Kus_Sutu_Model selected_item = adapter.quotesArrayListFiltered.get(position);

            if (arama_bool) {
                TextView tv_name_swipe = viewHolder.itemView.findViewById(R.id.tv_rcyc_name);
                RelativeLayout rel_lay_recyle2_swipe = viewHolder.itemView.findViewById(R.id.rel_lay_recyle_2);

                if (sharedpreferences_display.getBoolean("fav_"+(selected_item.getNo()-1), false)) {
                    editor_display.putBoolean("fav_"+(selected_item.getNo()-1), false).apply();
                    selected_item.setFavorite(false);

                    tv_name_swipe.setBackgroundColor(Color.parseColor("#6aa84f"));//a08b4a
                    rel_lay_recyle2_swipe.setBackgroundColor(Color.parseColor("#D96aa84f"));
                }
                else {
                    editor_display.putBoolean("fav_"+(selected_item.getNo()-1), true).apply();
                    selected_item.setFavorite(true);

                    tv_name_swipe.setBackgroundColor(Color.parseColor("#f22534")); //6aa84f
                    rel_lay_recyle2_swipe.setBackgroundColor(Color.parseColor("#D9f22534")); //6aa84f
                }

            }
            else if (fav_bool) {
                editor_display.putBoolean("fav_"+(selected_item.getNo()-1), false).apply();
                //kus_sutu_quotesArrayList_fav.remove(selected_item.getQuote());
                selected_item.setFavorite(false);

                //adapter.notifyItemRemoved(position);
                adapter.quotesArrayListFiltered.remove(position);

                Snackbar.make(recyclerView, "Kaldırdığınız sözü favorilere geri alabilirsiniz", 7000)
                        .setAction("Geri Al", view -> {
                            adapter.quotesArrayListFiltered.add(position, selected_item);
                            //adapter.notifyItemInserted(position);
                            editor_display.putBoolean("fav_"+(selected_item.getNo()-1), true).apply();
                            adapter.notifyDataSetChanged();
                        }).show();

                if (kus_sutu_quotesArrayList_fav.isEmpty()) {
                    tv_fav_empty.setVisibility(View.VISIBLE);
                    btn_fav_explore.setVisibility(View.VISIBLE);
                }
            }

            adapter.notifyDataSetChanged();


        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            int position = viewHolder.getAdapterPosition();
            Kus_Sutu_Model selected_item = adapter.quotesArrayListFiltered.get(position);

            Paint paint = new Paint();
            paint.setTextSize(28);
            paint.setTextAlign(Paint.Align.CENTER);
            int x_pos = viewHolder.itemView.getRight() - 150;
            int y_pos = (int) (viewHolder.itemView.getTop() + viewHolder.itemView.getHeight()/2 - (paint.descent() + paint.ascent())/1.5f);

            if (arama_bool) {
                RecyclerViewSwipeDecorator r = new RecyclerViewSwipeDecorator(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                if (sharedpreferences_display.getBoolean("fav_"+(selected_item.getNo()-1), false)) {
                    paint.setColor(Color.RED);
                    r.setSwipeLeftBackgroundColor(ContextCompat.getColor(Arama_Fav_Activity.this, R.color.white));
                    r.setBackgroundColor(ContextCompat.getColor(Arama_Fav_Activity.this, R.color.color_GREEN_my));
                    r.setActionIconId(R.drawable.ic_baseline_favorite_red_24);
                    r.decorate();
                    c.drawText("KALDIR", x_pos, y_pos, paint);
                } else {
                    paint.setColor(Color.WHITE);
                    r.setSwipeLeftBackgroundColor(ContextCompat.getColor(Arama_Fav_Activity.this, R.color.white));
                    r.setBackgroundColor(ContextCompat.getColor(Arama_Fav_Activity.this, R.color.color_RED_my));
                    r.setActionIconId(R.drawable.ic_baseline_favorite_24);
                    r.decorate();
                    c.drawText("EKLE", x_pos, y_pos, paint);
                }

            }
            else if (fav_bool) {

                paint.setColor(Color.WHITE);

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(Arama_Fav_Activity.this, R.color.colorPrimary_my))
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_36)
                        .addBackgroundColor(ContextCompat.getColor(Arama_Fav_Activity.this, R.color.colorPrimaryStatus_my))
                        .addActionIcon(R.drawable.ic_baseline_delete_36)
                        .create()
                        .decorate();

                c.drawText("SİL", x_pos, y_pos, paint);
            }


            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    ItemTouchHelper.SimpleCallback itemTouchShare = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Kus_Sutu_Model share_item;
            int position = viewHolder.getAdapterPosition();

            share_item = adapter.quotesArrayListFiltered.get(position);

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, share_item.getQuote()+ " \n\nAhmed İhsan GENÇ, Kuş Sütü ("+share_item.getNo()+".)");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

            adapter.notifyDataSetChanged();
            //adapter.notifyItemChanged(viewHolder.getAdapterPosition());
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    //.addSwipeRightBackgroundColor(ContextCompat.getColor(Arama_Fav_Activity.this, R.color.green_500))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_share_24)
                    //.addBackgroundColor(ContextCompat.getColor(Arama_Fav_Activity.this, R.color.green_500))
                    //.addActionIcon(R.drawable.ic_baseline_share_24)
                    .create()
                    .decorate();

            Paint paint = new Paint();
            paint.setColor(ContextCompat.getColor(Arama_Fav_Activity.this, R.color.colorPrimary_my));
            paint.setTextSize(32);
            paint.setTextAlign(Paint.Align.CENTER);
            int x_pos = viewHolder.itemView.getLeft() + 150;
            int y_pos = (int) (viewHolder.itemView.getTop() + viewHolder.itemView.getHeight()/2 - (paint.descent() + paint.ascent())/2);
            c.drawText("PAYLAŞ", x_pos, y_pos, paint);

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_FAV_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                adapter.notifyDataSetChanged();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                adapter.notifyDataSetChanged();
            }
            Toast.makeText(Arama_Fav_Activity.this, "Sistem geri yükleme ??", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (arama_bool/* & !getIntent().getBooleanExtra("display_to_aramaFav", false)*/) {
            /*new AlertDialog.Builder(this)
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
                    .show();*/
        }
        //else
            super.onBackPressed();
    }
}