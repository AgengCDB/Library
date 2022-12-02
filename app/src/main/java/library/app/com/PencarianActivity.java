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

public class PencarianActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<HashMap<String,String>> list_book;
    String url_get_book="http://booktify.my.id/QueryMobApp/function/all_book_process.php";

    private static final String TAG_ID="id";
    private static final String TAG_TITLE="title";
    private static final String TAG_AUTHOR="author";
    private static final String TAG_TYPE="type";
    private static final String TAG_ISBN="isbn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarian);

        list_book = new ArrayList<>();
        lv = findViewById(R.id.listView);

        RequestQueue queue = Volley.newRequestQueue(PencarianActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_book, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray("data");

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject a = member.getJSONObject(i);
                        String title = a.getString(TAG_TITLE);
                        String author = a.getString(TAG_AUTHOR);
                        String type = a.getString(TAG_TYPE);

//                        Log.e("JSON", title + "||" + author + "||" + type);

                        HashMap<String, String> map = new HashMap<>();
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
                                String nomor = list_book.get(position).get(TAG_ID);
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                i.putExtra("id", nomor);
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
                Toast.makeText(PencarianActivity.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue.add(stringRequest);
    }
}