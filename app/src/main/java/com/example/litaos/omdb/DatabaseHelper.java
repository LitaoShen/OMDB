package com.example.litaos.omdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_COLUMN_IMDBID = "imdbid";
    public static final String DB_COLUMN_TITLE = "title";
    public static final String DB_COLUMN_POSTER = "poster";
    public static final String DB_COLUMN_YEAR = "year";
    public static final String DB_COLUMN_RUNTIME = "runtime";
    public static final String DB_COLUMN_GENRE = "genre";
    public static final String DB_COLUMN_PLOT = "plot";

    public static final String CREATE_MOVIE = "create table Movie ("
            + DB_COLUMN_IMDBID + " text, "
            + DB_COLUMN_TITLE + " text, "
            + DB_COLUMN_POSTER + " text, "
            + DB_COLUMN_YEAR + " integer, "
            + DB_COLUMN_RUNTIME + " text, "
            + DB_COLUMN_GENRE + " text, "
            + DB_COLUMN_PLOT + " text);";


    private Context context;

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOVIE);
        Toast.makeText(context, "Create db succeeded ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
