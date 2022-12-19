package library.app.com;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class WhislistActivity extends ListActivity implements AdapterView.OnItemLongClickListener {

    //Deklarasi Komponen
    private MyDBHandler dbHandler;
    private ArrayList<WhislistPinjamBuku> values;
    private Button btnCancel, btnHapus;
    private ListView listWhislist;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whislist);

        //Object class MyDBHandler
        dbHandler = new MyDBHandler(this);

        //Membuka koneksi database
        try {
            dbHandler.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        values = dbHandler.getAllWhislist();
        ArrayAdapter<WhislistPinjamBuku> adapter = new ArrayAdapter<WhislistPinjamBuku>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        listWhislist = (ListView) findViewById(android.R.id.list);
        listWhislist.setOnItemLongClickListener(WhislistActivity.this);

        listWhislist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final WhislistPinjamBuku whislist = (WhislistPinjamBuku) getListAdapter().getItem(i);
                final int id = whislist.getBook_id();
                dbHandler.getWhislist(id);

                Intent in=new Intent(getApplicationContext(),BookProfileActivity.class);
                in.putExtra("id", whislist.getBook_id());
                in.putExtra("title", whislist.getBook_title());
                in.putExtra("type", whislist.getBook_type());
                in.putExtra("author", whislist.getBook_author());
                in.putExtra("isbn", whislist.getBook_isbn());
                in.putExtra("borrowed", whislist.getBook_borrowed());
                in.putExtra("pages", whislist.getBook_pages());
                in.putExtra("status", whislist.getBook_status());

                startActivity(in);
            }
        });
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Menampilkan dialog dan mengambil layout dari dialog.xml
        //Menampilkan dialog dan mengambil layout dari dialog.xml
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Pilih Aksi");
        dialog.show();

        final WhislistPinjamBuku whislist = (WhislistPinjamBuku) getListAdapter().getItem(i);
        final int id = whislist.getBook_id();

        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnHapus = dialog.findViewById(R.id.btnHapusWhislist);

        //Method button edit
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //Method button delete
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder konfirm = new AlertDialog.Builder(context);
                konfirm.setTitle("Hapus Barang");
                konfirm.setMessage("Anda yakin akan menghapus barang ini?");
                konfirm.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dbHandler.deleteWhislist(id);
                        // Menutup activity dan buka lagi untuk refresh isi konten
                        finish();
                        startActivity(getIntent());
                        overridePendingTransition(0,0);

                        Toast.makeText(WhislistActivity.this, "Barang berhasil dihapus", Toast.LENGTH_LONG).show();
                    }
                });
                konfirm.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });
                konfirm.show();
                dialog.dismiss();
            }
        });
        return true;
    }
}