package com.lengkap.pos.kode.indonesia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DBLOCATION = "";
    public static final String DBNAME = "kode.db";
    public static final String KEY_DESA = "desa";
    public static final String KEY_JENIS = "jenis";
    public static final String KEY_KABUPATEN = "kabupaten";
    public static final String KEY_KODEPOS = "kodepos";
    public static final String KEY_PROVINSI = "provinsi";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 5);
        if (VERSION.SDK_INT >= 17) {
            DBLOCATION = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DBLOCATION = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void openDatabase() {
        String dbPath = this.mContext.getDatabasePath(DBNAME).getPath();
        if (this.mDatabase == null || !this.mDatabase.isOpen()) {
            this.mDatabase = SQLiteDatabase.openDatabase(dbPath, null, 0);
        }
    }

    public void closeDatabase() {
        if (this.mDatabase != null) {
            this.mDatabase.close();
        }
    }
}