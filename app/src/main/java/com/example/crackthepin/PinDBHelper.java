package com.example.crackthepin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.crackthepin.PinContract.*;

import androidx.annotation.Nullable;

public class PinDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "pinlist.db";
    public static final int DATABASE_VERSION = 1;

    public PinDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PINLIST_TABLE = "CREATE TABLE " +
                PinEntry.TABLE_NAME + " (" +
                PinEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PinEntry.COLUMN_INPUT + " TEXT NOT NULL, " +
                PinEntry.COLUMN_OUTPUT + " TEXT NOT NULL, " +
                PinEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_PINLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PinEntry.TABLE_NAME);
        onCreate(db);
    }
}
