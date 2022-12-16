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
    String url_get_book_profile= "https://booktify123.000webhostapp.com/Library/function/test_book.php";
    String url_get_comment="";
    String the_id, the_title, the_type, the_author, the_borrowed, the_isbn, the_pages;
    TextView judul, kategori;
    TextView title, category, author, isbn, pages, borrowed;
    String book_title, book_type, book_author, book_isbn, book_pages, book_borrowed;
    SessionManager sessionManager;

    private static final String TAG_DATA="data";
    private static final String TAG_ID="book_id";
    private static final String TAG_TITLE="book_title";
    private static final String TAG_AUTHOR="book_author";
    private static final String TAG_TYPE="book_type";

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

        //Declare button
        whislist = (Button) findViewById(R.id.btnWhislistBuku);

        //Declare textview
        title = (TextView) findViewById(R.id.textJudul);
        author = (TextView) findViewById(R.id.textAuthor);
        pages = (TextView) findViewById(R.id.textPages);
        isbn = (TextView) findViewById(R.id.textISBN);
        category = (TextView) findViewById(R.id.textKategori);
        borrowed = (TextView) findViewById(R.id.textStatus);

        title.setText(the_title.toString());
        author.setText("Author: "+the_author.toString());
        pages.setText("Number of pages: "+the_pages.toString());
        isbn.setText("ISBN: "+ the_isbn.toString());
        category.setText(the_type.substring(0,1).toUpperCase() + the_type.substring(1));
        borrowed.setText("Borrowed: "+the_borrowed.toString()+ "x");
/* Ga bisa ambil dari php gatau kenapa
        RequestQueue queue = Volley.newRequestQueue(BookProfileActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_book_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    //int respond = jsonObject.getInt("success");
                    JSONArray jsonArray = jsonObject.getJSONArray(TAG_DATA);
                    JSONObject object = jsonArray.getJSONObject(0);

                    title.setText(object.optString(TAG_TITLE));


                    Toast.makeText(BookProfileActivity.this, object.toString(), Toast.LENGTH_SHORT).show();

                    /*
                    if(respond == 1) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            book_title = object.getString("book_title");
                            book_author = object.getString("book_author");
                            book_pages = object.getString("book_pages");
                            book_isbn = object.getString("book_isbn");
//                            book_type = object1.getString("book_type");
                            book_borrowed = object.getString("book_borrowed");

                            Toast.makeText(BookProfileActivity.this, "Buku berhasil di Baca", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(BookProfileActivity.this, "Buku Gagal Dibaca", Toast.LENGTH_SHORT).show();
                    }



                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                    Toast.makeText(BookProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

                params.put("id", the_id);
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

 */

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
                String judulBuku = title.getText().toString();
                String kategoriBuku = category.getText().toString();

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

    /* Ga bisa ambil dari php gatau kenapa
    void getBookProfile() {
        RequestQueue queue = Volley.newRequestQueue(BookProfileActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_book_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int respond = jsonObject.getInt("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(respond == 1) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            book_title = object.getString("book_title");
                            book_author = object.getString("book_author");
                            book_pages = object.getString("book_pages");
                            book_isbn = object.getString("book_isbn");
//                            book_type = object1.getString("book_type");
                            book_borrowed = object.getString("book_borrowed");

                            Toast.makeText(BookProfileActivity.this, "Buku berhasil di Baca", Toast.LENGTH_SHORT).show();
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

                params.put("id", the_id);
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

     */
}