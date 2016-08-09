package com.example.moham.ticketyourtrain;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by moham on 1/26/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "TrainTracker12";
    private static int DB_VERSION = 2;
    // Tables Names
    public static String TABLE_USER = "user";
    public static String TABLE_CITIES = "city";
    public static String TABLE_TICKET = "ticket";
    // Common Columns
    public static String id = "_id";
    public static String name = "name";
    // columns User
    public static String phone = "phone";
    public static String image = "image";
    public static String birth_date = "birth";
    public static String gender = "gender";
    public static String balance = "balance";
    // Columns City
    public static String value = "value";
    // Columns Ticket
    public static String user_number_ref = "user_id";
    public static String source = "source";
    public static String destination = "destination";
    public static String type = "type";
    public static String date = "picking_date";
    public static String price = "price";


    public static String CREATE_USER = "create table "
            + TABLE_USER + "(" + id + " integer primary key autoincrement, " + name + " varchar unique not null," + phone + " varchar," + birth_date + " date" +
            "," + image + " BLOB," + gender + " TEXT, " + balance + " number not null);";
    public static String CREATE_CITIE = "create table "
            + TABLE_CITIES + "(" + id + " integer primary key autoincrement, " + name + " varchar not null," + value + " integer not null);";
    public static String CREATE_TICKET = "create table "
            + TABLE_TICKET + "(" + id + " integer primary key autoincrement, " + user_number_ref + " integer," + source + " text not null," + destination + " text not null" +
            "," + type + " text not null," + date + " date ," + price + " int, " + " FOREIGN KEY (" + user_number_ref + ") REFERENCES " + TABLE_USER + "(" + id + "));";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_CITIE);
        db.execSQL(CREATE_TICKET);
        insert_Citie(db, new CitiesModel("tanta", 1));
        insert_Citie(db, new CitiesModel("shebeen", 3));
        insert_Citie(db, new CitiesModel("mansoura", 5));
        insert_Citie(db, new CitiesModel("Alexandria", 10));
        insert_Citie(db, new CitiesModel("Cairo", 13));
        insert_Citie(db, new CitiesModel("Banha", 4));
        insert_Citie(db, new CitiesModel("Damnhor", -3));
    }

    public void insert_Citie(SQLiteDatabase db, CitiesModel t) {
        ContentValues value = new ContentValues();
        value.put(DBHelper.name, t.getName());
        value.put(DBHelper.value, t.getValue());
        db.insert(DBHelper.TABLE_CITIES, null, value);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_USER + "");
        db.execSQL("drop table " + TABLE_CITIES + "");
        db.execSQL("drop table " + TABLE_TICKET + "");
        onCreate(db);
    }
}
