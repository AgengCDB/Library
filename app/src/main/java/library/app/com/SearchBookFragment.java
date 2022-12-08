package library.app.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.HashMap;

public class SearchBookFragment extends Fragment {

    ListView lv;
    ArrayList<HashMap<String,String>> list_book;
    String url_get_book="http://booktify.my.id/QueryMobApp/function/all_book_process2.php";
    String url_search_book="http://booktify.my.id/QueryMobApp/function/all_book_process2.php";
    EditText txtSearch;
    Button btnSearch;
    ImageButton btnHome, btnProfile, btnPengembalian;
    String user_search_input;

    private static final String TAG_ID="id";
    private static final String TAG_TITLE="title";
    private static final String TAG_AUTHOR="author";
    private static final String TAG_TYPE="type";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_search_book_fragment, container, false);

        list_book = new ArrayList<>();
        lv = v.findViewById(R.id.listView);

        RequestQueue queue = Volley.newRequestQueue(getContext());
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
                                getContext(), list_book, R.layout.list_book,
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
                    Log.e("anyText",response);
                    Log.e("Error", ex.toString());
//                    progressBar2.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(getContext(), "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
        queue.add(stringRequest);

        return v;
    }
}