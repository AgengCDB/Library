package library.app.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class PeminjamanActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<HashMap<String,String>> list_book;
    String url_get_book="http://booktify.my.id/QueryMobApp/function/all_book_process.php";
    private static final String TAG_ID="id";
    private static final String TAG_JUDUL="judul";
    private static final String TAG_AUTHOR="author";
    private static final String TAG_TYPE="type";
    private static final String TAG_ISBN="isbn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman);

        list_book = new ArrayList<>();
        lv = findViewById(R.id.listView);

        RequestQueue queue = Volley.newRequestQueue(PeminjamanActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_book, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_JUDUL);

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject a = member.getJSONObject(i);
                        String id = a.getString(TAG_ID);
                        String judul = a.getString(TAG_JUDUL);
                        String author = a.getString(TAG_AUTHOR);
                        String type = a.getString(TAG_TYPE);

                        HashMap<String, String> map = new HashMap<>();
                        map.put("id", id);
                        map.put("judul", judul);
                        map.put("author",author);
                        map.put("type", type);

                        list_book.add(map);
                        String[] from = {"judul", "author", "type"};
                        int[] to = {R.id.txtJudul, R.id.txtAuthor, R.id.txtType};

                        ListAdapter adapter = new SimpleAdapter(
                                PeminjamanActivity.this, list_book, R.layout.list_book,
                                from, to);
                        lv.setAdapter(adapter);
//                        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                            @Override
//                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                                String nomor = list_book.get(position).get(TAG_ID);
//                                Intent i = new Intent(getApplicationContext(), xxx.class);
//                                i.putExtra("id", nomor);
//                                startActivity(i);
//
//                                return true;
//                            }
//                        });
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
                Toast.makeText(PeminjamanActivity.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue.add(stringRequest);
    }
}