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
import java.util.Map;

public class BookProfileActivity extends AppCompatActivity {

    Button whislist;
    ListView listComment;
    ArrayList<HashMap<String,String>> list_comment;
    String url_get_book_profile= "https://booktify123.000webhostapp.com/Library/function/book_profile_process.php";
    String url_get_comment="";
    TextView judul, kategori;
    TextView title, category, author, isbn, pages, borrowed;
    String book_title, book_type, book_author, book_isbn, book_pages, book_borrowed;
    SessionManager sessionManager;

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

        getBookProfile();
    }

    void getBookProfile() {
        RequestQueue queue = Volley.newRequestQueue(BookProfileActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_book_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int respond = jsonObject.getInt("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    title = findViewById(R.id.textJudul);
                    author = findViewById(R.id.textAuthor);
                    pages = findViewById(R.id.textPages);
                    isbn = findViewById(R.id.textISBN);
                    category = findViewById(R.id.textKategori);
                    borrowed = findViewById(R.id.textStatus);

                    if(respond == 1) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            book_title = object.getString("book_title");
                            book_author = object.getString("book_author");
                            book_pages = object.getString("book_pages");
                            book_isbn = object.getString("book_isbn");
//                            book_type = object1.getString("book_type");
                            book_borrowed = object.getString("book_borrowed");

                            title.setText(book_title);
                        }
                    }
                    else {
                        Toast.makeText(BookProfileActivity.this, "Buku Gagal Dibaca", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("BookProfileError", error.getMessage());
                Toast.makeText(BookProfileActivity.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                Intent get = getIntent();
                String the_id = get.getStringExtra("id");
                params.put("book_id", the_id);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(stringRequest);
    }
}