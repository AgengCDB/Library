package library.app.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookProfileActivity extends AppCompatActivity {

    Button whislist, rent;
    ListView listComment;
    ArrayList<HashMap<String,String>> list_comment;
    //String url_get_book_profile= "https://booktify123.000webhostapp.com/Library/function/test_book.php";
    String url_get_comment="";
    String the_id, the_title, the_type, the_author, the_borrowed, the_isbn, the_pages, the_status;
    TextView title, category, author, isbn, pages, borrowed, status;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_profile);

        list_comment = new ArrayList<>();

        Intent get = getIntent();
        the_id = get.getStringExtra("id");
        the_title = get.getStringExtra("title");
        the_type = get.getStringExtra("type");
        the_author = get.getStringExtra("author");
        the_borrowed = get.getStringExtra("borrowed");
        the_isbn = get.getStringExtra("isbn");
        the_pages = get.getStringExtra("pages");
        the_status = get.getStringExtra("status");

        //Declare button
        whislist = (Button) findViewById(R.id.btnWhislistBuku);
        rent = (Button) findViewById(R.id.btnPinjamBuku);

        //Declare textview
        title = (TextView) findViewById(R.id.textJudul);
        author = (TextView) findViewById(R.id.textAuthor);
        pages = (TextView) findViewById(R.id.textPages);
        isbn = (TextView) findViewById(R.id.textISBN);
        category = (TextView) findViewById(R.id.textKategori);
        borrowed = (TextView) findViewById(R.id.textBorrowed);
        status = (TextView) findViewById(R.id.textStatus);

        title.setText(the_title.toString());
        author.setText("Author: "+the_author.toString());
        pages.setText("Number of pages: "+the_pages.toString());
        isbn.setText("ISBN: "+ the_isbn.toString());
        category.setText(the_type.substring(0,1).toUpperCase() + the_type.substring(1));
        borrowed.setText("Borrowed: "+the_borrowed.toString()+ "x");
        status.setText(the_status.substring(0,1).toUpperCase() + the_status.substring(1));

        //Objek class dbHandler
        MyDBHandler dbHandler = new MyDBHandler(this);

        //Buka koneksi db
        try {
            dbHandler.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Button unutk memasukkan buku kedalam wishlist.
        whislist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhislistPinjamBuku whislist = new WhislistPinjamBuku();

                String id_buku = the_id;
                String judulBuku = title.getText().toString();
                String kategoriBuku = category.getText().toString();
                String book_isbn = isbn.getText().toString();
                String book_borrowed = borrowed.getText().toString();
                String book_pages = pages.getText().toString();
                String book_author = author.getText().toString();
                String book_status = status.getText().toString();

                //Cek apakah buku sudah ada dalam database SQLite
                if(dbHandler.hasObject(judulBuku)) {
                    Toast.makeText(BookProfileActivity.this, "Buku sudah di wishlist!", Toast.LENGTH_SHORT).show();
                } else {
                    dbHandler.createWhislist(id_buku, judulBuku, kategoriBuku, book_author, book_pages, book_isbn, book_borrowed, book_status);
                    Toast.makeText(BookProfileActivity.this, "Buku berhasil dimasukkan ke wishlist", Toast.LENGTH_SHORT).show();
                    Intent k = new Intent(getApplicationContext(), WhislistActivity.class);
                    startActivity(k);
                    BookProfileActivity.this.finish();
                    dbHandler.close();
                }
            }
        });

        //Button pinjam buku
        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_buku = the_id;
                String judulBuku = title.getText().toString();
                String kategoriBuku = category.getText().toString();
                String book_isbn = isbn.getText().toString();
                String book_borrowed = borrowed.getText().toString();
                String book_pages = pages.getText().toString();
                String book_author = author.getText().toString();
                String book_status = status.getText().toString();

                Intent i = new Intent(getApplicationContext(), FormPeminjamanActivity.class);
                i.putExtra("id", id_buku);
                i.putExtra("title", judulBuku);
                i.putExtra("type", kategoriBuku);
                i.putExtra("author", book_author);
                i.putExtra("isbn", book_isbn);
                i.putExtra("borrowed", book_borrowed);
                i.putExtra("pages", book_pages);
                i.putExtra("status", book_status);

                startActivity(i);
            }
        });
    }
}