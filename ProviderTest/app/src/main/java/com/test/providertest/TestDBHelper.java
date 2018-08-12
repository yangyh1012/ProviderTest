package com.test.providertest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Yangyihui
 * @create 2018/8/12
 * @Describe
 */
public class TestDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;//数据库版本号
    private static final String DATABASE_NAME = "ttest.db";//共享数据的数据库名称

    public TestDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_CONTACT_TABLE = "CREATE TABLE " + TestContract.ProviderEntry.TABLE_NAME + "( "
                + TestContract.ProviderEntry._ID + " TEXT PRIMARY KEY, "
                + TestContract.ProviderEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + TestContract.ProviderEntry.COLUMN_VALUE + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TestContract.ProviderEntry.TABLE_NAME);
        onCreate(db);
    }
}
