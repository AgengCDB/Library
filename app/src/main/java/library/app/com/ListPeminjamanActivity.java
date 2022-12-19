package library.app.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListPeminjamanActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<HashMap<String,String>> list_peminjaman;
    String url_get_rent ="https://booktify.my.id/QueryMobApp/function/all_book_process.php";

    EditText txtSearch;
    Button btnSearch;
    ImageButton btnHome, btnProfile, btnPengembalian;
    String user_search_input;

    private static final String TAG_RENTID="rent_id";
    private static final String TAG_USERID="id_user";
    private static final String TAG_BOOKID="book_id";
    private static final String TAG_RENTSTART="rend_start_date";
    private static final String TAG_RENTEND="rend_end_date";
    private static final String TAG_OVERDUECOST="overdue_cost";
    private static final String TAG_STATUS="rent_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_peminjaman);

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        String id = user.get(sessionManager.ID_USER);
        String username = user.get(sessionManager.USERNAME);
        String email = user.get(sessionManager.EMAIL);
        String phone = user.get(sessionManager.PHONE);

        list_peminjaman = new ArrayList<>();
        lv = findViewById(R.id.listView);
        btnSearch = findViewById(R.id.btnSearch);
        txtSearch = findViewById(R.id.search_book);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_rent, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray("data");

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject a = member.getJSONObject(i);
                        String rent_id = a.getString(TAG_RENTID);
                        String book_id = a.getString(TAG_BOOKID);
                        String rent_start = a.getString(TAG_RENTSTART);
                        String rent_end = a.getString(TAG_RENTEND);
                        String overdue = a.getString(TAG_OVERDUECOST);
                        String status = a.getString(TAG_STATUS);

//                        Log.e("JSON", title + "||" + author + "||" + type);

                        HashMap<String, String> map = new HashMap<>();
                        map.put("rent_id", rent_id);
                        map.put("user_id", id);
                        map.put("book_id", book_id);
                        map.put("rent_start", rent_start);
                        map.put("rent_end", rent_end);
                        map.put("overdue", overdue);
                        map.put("rent_status", status);

                        list_peminjaman.add(map);
                        String[] from = {"title", "author", "type"};
                        int[] to = {R.id.txtJudul, R.id.txtAuthor, R.id.txtType};

                        ListAdapter adapter = new SimpleAdapter(
                                getApplicationContext(), list_peminjaman, R.layout.list_book,
                                from, to);
                        lv.setAdapter(adapter);
                        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                                String books_id = list_peminjaman.get(position).get(TAG_BOOKID);

                                String book_status = list_peminjaman.get(position).get(TAG_STATUS);
/*
                                Intent i = new Intent(getApplicationContext(), BookProfileActivity.class);
                                i.putExtra("id", books_id);
                                i.putExtra("title", book_title);
                                i.putExtra("type", book_type);
                                i.putExtra("author", book_author);
                                i.putExtra("isbn", book_isbn);
                                i.putExtra("borrowed", book_borrowed);
                                i.putExtra("pages", book_pages);
                                i.putExtra("status", book_status);
                                startActivity(i);

 */

                                return true;
                            }
                        });
                    }
                }
                catch (Exception ex) {
                    Log.e("anyText",response);
                    Log.e("Error", ex.toString());
//                    progressBar2.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(getApplicationContext(), "Long click", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue.add(stringRequest);

        //Koding untuk tombol Search
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_peminjaman.clear();
                //Adding Button Timeout Avoiding Application Bug
                //Bug -> Repeating when repeat button click event
                btnSearch.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnSearch.setEnabled(true);
                    }
                }, 2000);
                //Get Text
                user_search_input = txtSearch.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_rent, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jObj = new JSONObject(response);
                            JSONArray member = jObj.getJSONArray("data");

                            for (int i = 0; i < member.length(); i++) {
                                JSONObject a = member.getJSONObject(i);
                                String id = a.getString(TAG_ID);
                                String title = a.getString(TAG_TITLE);
                                String author = a.getString(TAG_AUTHOR);
                                String type = a.getString(TAG_TYPE);
                                String pages = a.getString(TAG_PAGES);
                                String borrowed = a.getString(TAG_BORROWED);
                                String isbn = a.getString(TAG_ISBN);
                                String status = a.getString(TAG_STATUS);

//                        Log.e("JSON", title + "||" + author + "||" + type);

                                HashMap<String, String> map = new HashMap<>();
                                map.put("id", id);
                                map.put("title", title);
                                map.put("author",author);
                                map.put("type", type);
                                map.put("pages", pages);
                                map.put("isbn", isbn);
                                map.put("borrowed", borrowed);
                                map.put("status", status);

                                list_peminjaman.add(map);
                                String[] from = {"title", "author", "type"};
                                int[] to = {R.id.txtJudul, R.id.txtAuthor, R.id.txtType};

                                ListAdapter adapter = new SimpleAdapter(
                                        getContext(), list_peminjaman, R.layout.list_book,
                                        from, to);
                                lv.setAdapter(adapter);
                                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                        String books_id = list_peminjaman.get(position).get(TAG_ID);
                                        String book_title = list_peminjaman.get(position).get(TAG_TITLE);
                                        String book_type = list_peminjaman.get(position).get(TAG_TYPE);
                                        String book_author = list_peminjaman.get(position).get(TAG_AUTHOR);
                                        String book_isbn = list_peminjaman.get(position).get(TAG_ISBN);
                                        String book_borrowed = list_peminjaman.get(position).get(TAG_BORROWED);
                                        String book_pages = list_peminjaman.get(position).get(TAG_PAGES);
                                        String book_status = list_peminjaman.get(position).get(TAG_STATUS);

                                        Intent i = new Intent(v.getContext(), BookProfileActivity.class);
                                        i.putExtra("id", books_id);
                                        i.putExtra("title", book_title);
                                        i.putExtra("type", book_type);
                                        i.putExtra("author", book_author);
                                        i.putExtra("isbn", book_isbn);
                                        i.putExtra("borrowed", book_borrowed);
                                        i.putExtra("pages", book_pages);
                                        i.putExtra("status", book_status);
                                        startActivity(i);
                                        return true;
                                    }
                                });
                            }
                        }
                        catch (Exception ex) {
                            Log.e("Error", ex.toString());
//                    progressBar2.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.getMessage());
                        Toast.makeText(getApplicationContext(), "Silahkan cek koneksi anda", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("user_input", user_search_input);
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
        });
        return v;
    }
}
    }
}