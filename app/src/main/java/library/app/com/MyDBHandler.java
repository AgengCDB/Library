package library.app.com;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

    //Deklarasi variabel konstanta pembuatan database, tabel dan kolom yang
    //diperlukan
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "whislist_buku.db";
    private static final String TABLE_NAME = "whislist";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAMABUKU = "nama_buku";
    private static final String COLUMN_KATEGORIBUKU = "kategori_buku";

    //Constructor untuk class MyDBHandler
    public MyDBHandler (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Method untuk create database


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_WHISLIST = "CREATE TABLE " +TABLE_NAME+ "("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAMABUKU + " VARCHAR(50), NOT NULL, " +
                COLUMN_KATEGORIBUKU +" VARCHAR(50) NOT NULL)";

        sqLiteDatabase.execSQL(CREATE_TABLE_WHISLIST);
    }

    //Method untuk upgrade table

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    /*---- Insert, Select, Update, Delete ----*/
    private SQLiteDatabase database;

    //Open database conn
    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }



}
