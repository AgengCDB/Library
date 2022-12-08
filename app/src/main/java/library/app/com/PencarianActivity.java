package library.app.com;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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

public class PencarianActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<HashMap<String,String>> list_book;
    String url_get_book="http://192.168.88.13/Library/function/all_book_process.php";
    String url_search_book="http://booktify.my.id/QueryMobApp/function/all_book_process2.php";
    EditText txtSearch;
    Button btnSearch;
    ImageButton btnHome, btnProfile, btnPengembalian;
    String user_search_input;

    private static final String TAG_ID="id";
    private static final String TAG_TITLE="title";
    private static final String TAG_AUTHOR="author";
    private static final String TAG_TYPE="type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarian);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        list_book = new ArrayList<>();
        lv = findViewById(R.id.listView);

        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        });
        btnProfile = findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(PencarianActivity.this, ProfileActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        });

        btnPengembalian = findViewById(R.id.btnPengembalianBuku);
        btnPengembalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PengembalianActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        });

        btnSearch = findViewById(R.id.btnSearch);
        RequestQueue queue = Volley.newRequestQueue(PencarianActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_book, new Response.Listener<String>() {
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

//                        Log.e("JSON", title + "||" + author + "||" + type);

                        HashMap<String, String> map = new HashMap<>();
                        map.put("id", id);
                        map.put("title", title);
                        map.put("author",author);
                        map.put("type", type);

                        list_book.add(map);
                        String[] from = {"title", "author", "type"};
                        int[] to = {R.id.txtJudul, R.id.txtAuthor, R.id.txtType};

                        ListAdapter adapter = new SimpleAdapter(
                                PencarianActivity.this, list_book, R.layout.list_book,
                                from, to);
                        lv.setAdapter(adapter);
                        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                                String nomor = list_book.get(position).get(TAG_ID);
//                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                                i.putExtra("id", nomor);
//                                startActivity(i);
//
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
                Toast.makeText(PencarianActivity.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue.add(stringRequest);

        //Search Button
        /*
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_book.clear();
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
                txtSearch = findViewById(R.id.search_book);
                user_search_input = txtSearch.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(PencarianActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_search_book, new Response.Listener<String>() {
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

//                        Log.e("JSON", title + "||" + author + "||" + type);

                                HashMap<String, String> map = new HashMap<>();
                                map.put("id", id);
                                map.put("title", title);
                                map.put("author",author);
                                map.put("type", type);

                                list_book.add(map);
                                String[] from = {"title", "author", "type"};
                                int[] to = {R.id.txtJudul, R.id.txtAuthor, R.id.txtType};

                                ListAdapter adapter = new SimpleAdapter(
                                        PencarianActivity.this, list_book, R.layout.list_book,
                                        from, to);
                                lv.setAdapter(adapter);
                                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                                String nomor = list_book.get(position).get(TAG_ID);
//                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                                i.putExtra("id", nomor);
//                                startActivity(i);
//
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
                        Log.e("Errr", error.getMessage());
                        Toast.makeText(PencarianActivity.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
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

         */
    }
    void clearList() {
        lv.setAdapter(null);
    }
}