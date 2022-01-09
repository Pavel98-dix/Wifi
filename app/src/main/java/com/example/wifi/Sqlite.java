package com.example.wifi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Sqlite extends SQLiteOpenHelper {
    String table="CREATE TABLE  Red(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "idnetwork TEXT," +
            "ssid TEXT," +
            "bssid TEXT)";
    public Sqlite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Red");
        onCreate(db);
    }
}
