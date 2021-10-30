package com.example.myapplication2.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication2.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int VERSION = 1;

    private static final String NAME = "BaiTap";
    private static final String CONTACT_TABLE = "contacts";
    private static final String ID = "id";
    private static final String CONTACT_NAME = "contact";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + CONTACT_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CONTACT_NAME + " TEXT)";
    private SQLiteDatabase db;


    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE);

        onCreate(db);
    }

    public void openDatabase() {

        db = this.getWritableDatabase();

    }

    public void insertContact(Contact contact) {

        ContentValues cv = new ContentValues();

        cv.put(CONTACT_NAME, contact.getContactName());

        db.insert(CONTACT_TABLE, null, cv);

    }

    public int deleteContact(int id) {

        return db.delete(CONTACT_TABLE, ID + "=?", new String[]{ String.valueOf(id)} );

    }

    public List<Contact> getAllContacts() {
        List<Contact> list = new ArrayList<>();

        Cursor cur = null;
        db.beginTransaction();

        try {
            cur = db.query(CONTACT_TABLE, null, null, null, null, null, ID);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        Contact contact = new Contact();
                        contact.setId(cur.getInt(cur.getColumnIndex(ID)));
                        contact.setContactName(cur.getString(cur.getColumnIndex(CONTACT_NAME)));
                        list.add(contact);

                    } while (cur.moveToNext());
                }


            }
        } finally {
            db.endTransaction();
            cur.close();
        }

        return list;
    }


}
