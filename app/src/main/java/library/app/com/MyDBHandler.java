package library.app.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {

    //Deklarasi variabel konstanta pembuatan database, tabel dan kolom yang
    //diperlukan
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "whislist_buku.db";
    private static final String TABLE_NAME = "whislist";

    private static final String COLUMN_ID = "book_id";
    private static final String COLUMN_NAMABUKU = "book_title";
    private static final String COLUMN_KATEGORIBUKU = "book_type";
    private static final String COLUMN_BOOKAUTHOR = "book_author";
    private static final String COLUMN_BOOKPAGES = "book_pages";
    private static final String COLUMN_BORROWED = "book_borrowed";
    private static final String COLUMN_BOOKISBN = "book_isbn";
    private static final String COLUMN_BOOKSTATUS = "book_status";


    //Constructor untuk class MyDBHandler
    public MyDBHandler (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Method untuk create database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_WHISLIST = "CREATE TABLE " +TABLE_NAME+ "("+COLUMN_ID+" INTEGER PRIMARY KEY, " +
                COLUMN_NAMABUKU + " VARCHAR(50) NOT NULL, " +
                COLUMN_KATEGORIBUKU +" VARCHAR(50) NOT NULL," +
                COLUMN_BOOKAUTHOR + " VARCHAR(50) NOT NULL," +
                COLUMN_BOOKISBN + " VARCHAR(50) NOT NULL," +
                COLUMN_BOOKPAGES + " VARCHAR(50) NOT NULL," +
                COLUMN_BOOKSTATUS + " VARCHAR(50) NOT NULL," +
                COLUMN_BORROWED + " VARCHAR(50) NOT NULL)";

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

    //Inisialisasi semua kolom di tabel database
    private String[] allColumns = {COLUMN_ID, COLUMN_NAMABUKU, COLUMN_KATEGORIBUKU, COLUMN_BOOKAUTHOR, COLUMN_BOOKISBN, COLUMN_BOOKPAGES, COLUMN_BORROWED, COLUMN_BOOKSTATUS};

    //  Method untuk memindahkan isi cursor ke objek buku
    private WhislistPinjamBuku cursorToWhislist(Cursor cursor){
        WhislistPinjamBuku whislist = new WhislistPinjamBuku();

        whislist.setBook_id(cursor.getInt(0));
        whislist.setBook_title(cursor.getString(1));
        whislist.setBook_type(cursor.getString(2));
        whislist.setBook_author(cursor.getString(3));
        whislist.setBook_isbn(cursor.getString(4));
        whislist.setBook_pages(cursor.getString(5));
        whislist.setBook_borrowed(cursor.getString(6));
        whislist.setBook_status(cursor.getString(7));

        return whislist;
    }

    //Method untuk tambah buku kedalam whislist
    public void createWhislist(String id, String judul, String kategori, String author, String pages, String isbn, String borrowed, String status) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAMABUKU, judul);
        values.put(COLUMN_KATEGORIBUKU, kategori);
        values.put(COLUMN_BOOKAUTHOR, author);
        values.put(COLUMN_BOOKISBN, isbn);
        values.put(COLUMN_BOOKPAGES, pages);
        values.put(COLUMN_BORROWED, borrowed);
        values.put(COLUMN_BOOKSTATUS, status);

        database.insert(TABLE_NAME, null, values);
    }

    //Method untuk mendapatkan detail per buku
    public WhislistPinjamBuku getWhislist(int id){
        WhislistPinjamBuku whislist = new WhislistPinjamBuku();

        Cursor cursor = database.query(TABLE_NAME, allColumns, "book_id=" +id, null, null, null, null);
        cursor.moveToFirst();
        whislist = cursorToWhislist(cursor);
        cursor.close();

        return whislist;
    }

    //Method untuk mendapatkan semua barang di whislist
    public ArrayList<WhislistPinjamBuku> getAllWhislist() {
        ArrayList<WhislistPinjamBuku> daftarWhislist = new ArrayList<WhislistPinjamBuku>();

        Cursor cursor = database.query(TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            WhislistPinjamBuku whislist = cursorToWhislist(cursor);
            daftarWhislist.add(whislist);
            cursor.moveToNext();
        }
        cursor.close();
        return daftarWhislist;
    }

    //Method untuk update whislist
    public void updateWhislist(WhislistPinjamBuku whislist){
        String filter = "_id" + whislist.getBook_id();

        ContentValues args = new ContentValues();
        args.put(COLUMN_NAMABUKU, whislist.getBook_title());
        args.put(COLUMN_KATEGORIBUKU, whislist.getBook_type());
        args.put(COLUMN_BOOKAUTHOR, whislist.getBook_author());
        args.put(COLUMN_BOOKPAGES, whislist.getBook_pages());
        args.put(COLUMN_BOOKISBN, whislist.getBook_isbn());
        args.put(COLUMN_BORROWED, whislist.getBook_borrowed());

        database.update(TABLE_NAME, args, filter, null);
    }

    //Method untuk hapus whislist
    public void deleteWhislist(int id) {
        String filter = "book_id="+id;

        database.delete(TABLE_NAME, filter, null);
    }

    public boolean hasObject(String namaBuku) {
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAMABUKU + " = ?";

        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectString, new String[] {namaBuku});

        boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;

            //region if you had multiple records to check for, use this region.

            int count = 0;
            while(cursor.moveToNext()){
                count++;
            }
            //here, count is records found
            Log.d("Error", String.format("%d records found", count));

            //endregion
        }

        //cursor.close();          // Dont forget to close your cursor
        //db.close();              //AND your Database!
        return hasObject;
    }
}
