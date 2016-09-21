package com.hp.alirezas.weather01.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alireza s on 6/25/2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATAbASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE_DATE_INFO);
        db.execSQL(Constants.CREATE_TABLE_CITY_INFO);
        db.execSQL(Constants.CREATE_TABLE_STATUS);
        db.execSQL(Constants.CREATE_TABLE_FORECAST);
        db.execSQL(Constants.CREATE_TABLE_PRAYER);
        db.execSQL(Constants.CREATE_TABLE_CITY_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
