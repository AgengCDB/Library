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
    ArrayList<HashMap<String,String>> listHistory;
    String url_get_history="https://booktify.my.id/QueryMobApp/function/show_rent_status_ongoing.php";

    private static final String TAG_RENTID="rent_id";
    private static final String TAG_USERID="id_user";
    private static final String TAG_BOOKID="book_id";
    private static final String TAG_RENTSTART="rent_start_date";
    private static final String TAG_RENTEND="rent_due_date";
    private static final String TAG_OVERDUECOST="overdue_cost";
    private static final String TAG_STATUS="rent_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_peminjaman);

        //Get User ID
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        String session_id = user.get(sessionManager.ID_USER);

        listHistory = new ArrayList<>();
        lv = findViewById(R.id.list_Peminjaman);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_history, new Response.Listener<String>() {
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
                        map.put("user_id", session_id);
                        map.put("book_id", book_id);
                        map.put("rent_start_date", rent_start);
                        map.put("rent_due_date", rent_end);
                        map.put("overdue_cost", overdue);
                        map.put("rent_status", status);

                        listHistory.add(map);
                        String[] from = {"rent_id", "user_id", "book_id", "rent_due_date", "overdue_cost"};
                        int[] to = {R.id.txtRentID, R.id.txtSessionID, R.id.txtBooksID, R.id.textEndDate, R.id.txtCost};

                        ListAdapter adapter = new SimpleAdapter(
                                getApplicationContext(), listHistory, R.layout.list_history,
                                from, to);
                        lv.setAdapter(adapter);
                        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                                String rentId = listHistory.get(position).get(TAG_RENTID);
                                String bookId= listHistory.get(position).get(TAG_BOOKID);
                                String rentStart = listHistory.get(position).get(TAG_RENTSTART);
                                String rentEnd = listHistory.get(position).get(TAG_RENTEND);
                                String overdueCost = listHistory.get(position).get(TAG_OVERDUECOST);
                                String rentStatus = listHistory.get(position).get(TAG_STATUS);

                                Intent i = new Intent(getApplicationContext(), FormPengembalianActivity.class);
                                i.putExtra("rent_id", rentId);
                                i.putExtra("book_id", bookId);
                                i.putExtra("rentStart", rentStart);
                                i.putExtra("rent_end", rentEnd);
                                i.putExtra("overdue", overdueCost);
                                i.putExtra("status", rentStatus);
                                startActivity(i);

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
                Toast.makeText(getApplicationContext(), "Silahkan cek koneksi anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", "3");
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