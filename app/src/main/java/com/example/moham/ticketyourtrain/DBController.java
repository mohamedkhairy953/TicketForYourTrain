package com.example.moham.ticketyourtrain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moham on 1/26/2016.
 */
public class DBController {
    DBHelper helper;
    Context c2;
    SQLiteDatabase database;

    public DBController(Context c) {
        c2 = c;
        helper = new DBHelper(c);
    }

    public DBController open() {
        database = helper.getWritableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public int insert_user(UserModel u) {
        int insert = 0;
            open();
            ContentValues value = new ContentValues();
            value.put(DBHelper.name, u.getName());
            value.put(DBHelper.phone, u.getPhone());
            value.put(DBHelper.image, u.getImage());
            value.put(DBHelper.gender, u.getType());
            value.put(DBHelper.balance, u.getBalance());
            value.put(DBHelper.birth_date, u.getBirth_date());
            insert = (int) database.insertOrThrow(DBHelper.TABLE_USER, null, value);

            close();
        return insert;
    }

    public String insert_Ticket(TicketModel t, UserModel user) {
        long insert = 0;
        int update = 0;
        if (user.getBalance() - t.getPrice() >= 0) {
            open();
            ContentValues value = new ContentValues();
            value.put(DBHelper.user_number_ref, user.getId());
            value.put(DBHelper.source, t.getSource());
            value.put(DBHelper.destination, t.getDestination());
            value.put(DBHelper.type, t.getType());
            value.put(DBHelper.date, t.getBooking_date());
            value.put(DBHelper.price, t.getPrice());
            insert = database.insert(DBHelper.TABLE_TICKET, null, value);
            ContentValues value2 = new ContentValues();
            value2.put(DBHelper.name, user.getName());
            value2.put(DBHelper.phone, user.getPhone());
            value2.put(DBHelper.image, user.getImage());
            value2.put(DBHelper.gender, user.getType());
            value2.put(DBHelper.balance, (user.getBalance() - t.getPrice()));
            value2.put(DBHelper.birth_date, user.getBirth_date());
            update = database.update(DBHelper.TABLE_USER, value2, DBHelper.id + " = " + user.getId(), null);
            close();
        }
        if (insert == 0) {
            return "check your balance";
        }
        return "id inserted =" + insert + " row affected " + update;
    }

    public String AddBalance(UserModel user, double new_balnce) {
        open();
        int update = 0;
        ContentValues value2 = new ContentValues();
        value2.put(DBHelper.name, user.getName());
        value2.put(DBHelper.phone, user.getPhone());
        value2.put(DBHelper.image, user.getImage());
        value2.put(DBHelper.gender, user.getType());
        value2.put(DBHelper.balance, (user.getBalance() + new_balnce));
        value2.put(DBHelper.birth_date, user.getBirth_date());
        update = database.update(DBHelper.TABLE_USER, value2, DBHelper.id + " = " + user.getId(), null);
        close();
        return "row affected " + update;
    }


    public List<CitiesModel> GetAllCities() {
        List<CitiesModel> cities = new ArrayList<CitiesModel>();
        open();
        Cursor c = database.rawQuery("select * from " + DBHelper.TABLE_CITIES + ";", null);
        if (c.moveToFirst()) {
            do {
                CitiesModel city = new CitiesModel();
                city.setName(c.getString(c.getColumnIndex(DBHelper.name)));
                city.setValue(c.getInt(c.getColumnIndex(DBHelper.value)));
                cities.add(city);
            } while (c.moveToNext());
        }
        close();
        return cities;
    }


    public ArrayList<TicketModel> GetUserTickets(int user) {
        ArrayList<TicketModel> tickets = new ArrayList<>();
        open();
        Cursor cursor = database.rawQuery("select * from " + DBHelper.TABLE_TICKET + " where " + DBHelper.user_number_ref + "=" + user + ";", null);
        if (cursor.moveToFirst()) {
            do {
                TicketModel tick = new TicketModel();
                tick.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.id)));
                tick.setBooking_date(cursor.getString(cursor.getColumnIndex(DBHelper.date)));
                tick.setDestination(cursor.getString(cursor.getColumnIndex(DBHelper.destination)));
                tick.setSource(cursor.getString(cursor.getColumnIndex(DBHelper.source)));
                tick.setType(cursor.getString(cursor.getColumnIndex(DBHelper.type)));
                tick.setPrice(cursor.getInt(cursor.getColumnIndex(DBHelper.price)));
                tickets.add(tick);
            } while (cursor.moveToNext());
        }
        close();
        return tickets;

    }

    public TicketModel Geticket(int ticket_id) {
        TicketModel tick = new TicketModel();
        if (ticket_id > 0) {
            open();
            Cursor cursor = database.rawQuery("select * from " + DBHelper.TABLE_TICKET + " where " + DBHelper.id + "=" + ticket_id + ";", null);
            if (cursor.moveToFirst()) {
                tick.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.id)));
                tick.setBooking_date(cursor.getString(cursor.getColumnIndex(DBHelper.date)));
                tick.setDestination(cursor.getString(cursor.getColumnIndex(DBHelper.destination)));
                tick.setSource(cursor.getString(cursor.getColumnIndex(DBHelper.source)));
                tick.setType(cursor.getString(cursor.getColumnIndex(DBHelper.type)));
                tick.setPrice(cursor.getInt(cursor.getColumnIndex(DBHelper.price)));
            }
            close();
            return tick;
        } else {
            return tick;
        }
    }

    public UserModel getuser(String name) {
        UserModel user = new UserModel();
        open();
        Cursor cursor = database.rawQuery("select * from " + DBHelper.TABLE_USER + " where " + DBHelper.name + "='" + name + "';", null);
        if (cursor.moveToFirst()) {
            user.setType(cursor.getString(cursor.getColumnIndex(DBHelper.gender)));
            user.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.id)));
            user.setBirth_date(cursor.getString(cursor.getColumnIndex(DBHelper.birth_date)));
            try {
                user.setName(cursor.getString(cursor.getColumnIndex(DBHelper.name)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(DBHelper.phone)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.setImage(cursor.getBlob(cursor.getColumnIndex(DBHelper.image)));
            user.setBalance(cursor.getDouble(cursor.getColumnIndex(DBHelper.balance)));
        }
        close();
        return user;

    }
    public void delete(int id) {
        open();
        database.delete(DBHelper.TABLE_TICKET, DBHelper.id + "=" + id, null);
        close();
    }


}
 /* public void drop() {

        open();
        database.execSQL("drop table " + DBHelper.TABLE + ";");
        close();

    }*/

   /* public void update(String name, String phone, int duration, String account_link, byte[] image, String remind, String birth, int id) {
        open();
        ContentValues value = new ContentValues();
        value.put(DBHelper.name, name);
        value.put(DBHelper.phone, phone);
        value.put(DBHelper.max_duration, duration);
        value.put(DBHelper.account_link, account_link);
        value.put(DBHelper.image, image);
        value.put(DBHelper.remind_me, remind);
        value.put(DBHelper.birth, birth);
        database.update(DBHelper.TABLE, value, DBHelper.id + " = " + id, null);
        close();

    }*/


    /*public void delete(int id) {
        open();
        database.delete(DBHelper.TABLE, DBHelper.id + "=" + id, null);
        close();
    }*/
