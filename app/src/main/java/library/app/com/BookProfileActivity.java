package library.app.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class BookProfileActivity extends AppCompatActivity {

    ListView listComment;
    ArrayList<HashMap<String,String>> list_comment;
    String url_get_book_profile="";
    String url_get_comment="";
    TextView judul, kategori;

    Button whislist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_profile);

        list_comment = new ArrayList<>();

        //Declare button
        whislist = (Button) findViewById(R.id.btnWhislistBuku);

        //Declare textview
        judul = (TextView) findViewById(R.id.textJudul);
        kategori = (TextView) findViewById(R.id.textKategori);

        //Objek class dbHandler
        MyDBHandler dbHandler = new MyDBHandler(this);

        //Buka koneksi db
        try {
            dbHandler.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        whislist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhislistPinjamBuku whislist = new WhislistPinjamBuku();
                String judulBuku = judul.getText().toString();
                String kategoriBuku = kategori.getText().toString();

                if(dbHandler.hasObject(judulBuku)) {
                    Toast.makeText(BookProfileActivity.this, "Buku sudah di whislist!", Toast.LENGTH_SHORT).show();
                } else {
                    dbHandler.createWhislist(judulBuku, kategoriBuku);
                    Toast.makeText(BookProfileActivity.this, "Buku berhasil dimasukkan ke whislist", Toast.LENGTH_SHORT).show();
                }

                Intent k = new Intent(getApplicationContext(), WhislistActivity.class);
                startActivity(k);
                BookProfileActivity.this.finish();
                dbHandler.close();
            }
        });
    }
}