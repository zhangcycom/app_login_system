package com.example.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_STUDENT = "create table Student("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"stu_no text,"
            +"dor_no text,"
            +"password text)";

    public static final String CREATE_ADMIN = "create table Admin("
            +"ad_id integer primary key autoincrement,"
            +"ad_address text,"
            +"ad_passwd text)";


    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                            int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_ADMIN);
        Toast.makeText(mContext, "2 tables Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

