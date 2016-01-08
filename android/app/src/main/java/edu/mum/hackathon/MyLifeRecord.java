package edu.mum.hackathon;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by ayaskanti on 12/27/15.
 */
public class MyLifeRecord extends SQLiteOpenHelper {
    private final static String TABLE_NAME = "MyLifeRecord";
    private final static String COL_ACTIVITY_TYPE = "ACTIVITY_TYPE";
    private final static String COL_START = "START";
    private final static String COL_DURATION = "DURATION";
    private final static String COL_UPLOADED = "UPLOADED";
    private final static String DATABASE_NAME = "MyLifeRecord.db";
    private final static int DATABASE_VERSION = 1;
    private final static String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + " RID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_ACTIVITY_TYPE + " INTEGER,"
                    + COL_START + " DATETIME,"
                    + COL_DURATION + " INTEGER ,"
                    + COL_UPLOADED + " INTEGER DEFAULT 0"
            + " )";
    private final static String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public MyLifeRecord(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insert(int type/* TODO make it into enum */, Date start, long duration) {
        ContentValues values = new ContentValues();
        values.put(COL_ACTIVITY_TYPE, type);
        values.put(COL_START, type);
        values.put(COL_DURATION, type);
        getWritableDatabase().insert(TABLE_NAME, COL_UPLOADED, values);
    }
}
