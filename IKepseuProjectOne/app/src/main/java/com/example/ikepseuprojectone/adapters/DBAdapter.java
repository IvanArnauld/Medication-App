package com.example.ikepseuprojectone.adapters;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    static final String KEY_ROWID = "_id";
    static final String KEY_PATNAME = "patName";
    static final String KEY_MEDNAME = "medName";
    static final String KEY_MEDTYPE = "medType";
    static final String KEY_DATE = "date";
    static final String KEY_TIME = "time";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "medications";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE =
            "create table medications (_id integer primary key autoincrement, "
                    + "patName text not null, medName text not null, medType text not null, date text not null, time text not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS medications");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a medication record into the database---
    public long insertMedication(String patName, String medName, String medType, String date, String time)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PATNAME, patName);
        initialValues.put(KEY_MEDNAME, medName);
        initialValues.put(KEY_MEDTYPE, medType);
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_TIME, time);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular medication record---
    public boolean deleteMedication(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---deletes all medication records---
    public void deleteAllMedications()
    {
        db.execSQL("delete from "+ DATABASE_TABLE);
    }

    //---retrieves all the medication records ---
    public Cursor getAllMedications()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_PATNAME,
                KEY_MEDNAME, KEY_MEDTYPE, KEY_DATE, KEY_TIME}, null, null, null, null, null);
    }


}
