package de.kunee.divelog.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper implements DiveLogContract.Dives {

    private static final String DATABASE_NAME = "divelog.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_DIVE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DIVE_NO + " INTEGER NOT NULL, " +
            DIVE_DATE + " INTEGER NOT NULL, " +
            LOCATION + " TEXT, " +
            TIME_IN + " TEXT, " +
            DURATION_BOTTOM_TIME + " INTEGER, " +
            DURATION_SAFETY_STOP + " INTEGER, " +
            TANK_PRESSURE_BEFORE + " REAL, " +
            TANK_PRESSURE_AFTER + " REAL, " +
            DEPTH_MAX + " REAL, " +
            TEMPERATURE_AIR + " REAL, " +
            TEMPERATURE_SURFACE + " REAL, " +
            TEMPERATURE_BOTTOM + " REAL, " +
            VISIBILITY + " REAL, " +
            WEIGHT + " REAL, " +
            DIVE_SKIN + " INTEGER, " +
            WET_SUIT + " INTEGER, " +
            DRY_SUIT + " INTEGER, " +
            HOODED_VEST + " INTEGER, " +
            GLOVES + " INTEGER, " +
            BOOTS + " INTEGER, " +
            COMMENTS + " TEXT" +
            ")";

    private static final String SQL_INSERT_INTO_DIVE = "INSERT INTO " + TABLE_NAME + " (" +
            DIVE_NO + ", " +
            DIVE_DATE + ", " +
            LOCATION +
            ") VALUES (%s, %s, '%s')";

    private static final String SQL_DROP_TABLE_DIVE = "DROP TABLE " + TABLE_NAME;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_DIVE);
        db.execSQL(String.format(SQL_INSERT_INTO_DIVE, 1, System.currentTimeMillis(), "Hawaii"));
        db.execSQL(String.format(SQL_INSERT_INTO_DIVE, 2, System.currentTimeMillis(), "Bahamas"));
        db.execSQL(String.format(SQL_INSERT_INTO_DIVE, 3, System.currentTimeMillis(), "Maldives"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_DIVE);
        onCreate(db);
    }
}
