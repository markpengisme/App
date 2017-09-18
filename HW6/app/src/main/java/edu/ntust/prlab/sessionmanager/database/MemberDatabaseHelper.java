package edu.ntust.prlab.sessionmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class MemberDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "members.db";
    private static final int DATABASE_VERSION = 1;

    MemberDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * 建立資料表需要的SQL語法，有編號、名稱、性別、年齡等...
     */
    private static final String SQL_CREATE_TABLE_TASKS = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER, %s TEXT)",
            MemberContract.TABLE_NAME,
            MemberContract.MemberEntry._ID,
            MemberContract.MemberEntry.NAME,
            MemberContract.MemberEntry.GENDER,
            MemberContract.MemberEntry.AGE
    );

    /**
     * 需要建立資料庫的時候，呼叫此函式。
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TASKS);
    }

    /**
     * 資料庫版本升級的時候，呼叫此函式。
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MemberContract.TABLE_NAME);
        onCreate(db);
    }

}
