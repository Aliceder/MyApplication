package com.example.administrator.myapplication.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLHelper extends SQLiteOpenHelper {

    public SQLHelper(Context context){
        super(context,"Shopping.db",null,2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE Shop(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(20), price VARCHAR(20), i VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
