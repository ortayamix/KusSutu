package com.example.tefeul;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class Kus_Sutu_Model {

    /*Bu “KUŞ SÜTÜ” öyle bir kitaptır ki günlerce aylarca yıllarca birer, ikişer, üçer hikmetli cümleler olarak (altmış yıllık ömre yayılmış) düşünülüp yazılmıştır. Meşrebi ve mesleği ne olursa olsun her insanı ilgilendiren mefhumlar içinde vardır. Zararsız istifadelere göz yumulmasın. (26.05.2004) \n\n
DİKKAT =>Not: Bu yazı kitabın dış kapağı içindir.*/

    public static final String MyPREFERENCES_STORAGE = "MyPrefs_storage" ;
    SharedPreferences sharedpreferences_storage;
    SharedPreferences.Editor sharedpreferences_storage_edit;

    Gson gson = new Gson();

    private Context mContext;
    private String quote;
    private int no;
    private boolean isFavorite;
    private int no_of_Bookmark;

    public Kus_Sutu_Model(Context mContext, String quote, int no, boolean isFavorite, int no_of_Bookmark) {
        sharedpreferences_storage = mContext.getSharedPreferences(MyPREFERENCES_STORAGE, MODE_PRIVATE);
        sharedpreferences_storage_edit = sharedpreferences_storage.edit();

        this.mContext = mContext;
        this.quote = quote;
        this.isFavorite = isFavorite;
        this.no_of_Bookmark = no_of_Bookmark;
        this.no = no;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        //sharedpreferences_storage_edit.putBoolean("fav", favorite).commit();
        isFavorite = favorite;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @NonNull
    @Override
    public String toString() {
        return quote+"";
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getNo_of_Bookmark() {
        return no_of_Bookmark;
    }

    public void setNo_of_Bookmark(int no_of_Bookmark) {
        this.no_of_Bookmark = no_of_Bookmark;
    }
}
