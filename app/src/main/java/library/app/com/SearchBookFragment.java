package library.app.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class SearchBookFragment extends Fragment {

    ListView lv;
    ArrayList<HashMap<String,String>> list_book;
    String url_get_book="https://booktify123.000webhostapp.com/Library/function/all_book_process.php";
    String url_search_book="http://booktify123.000webhostapp.com/Library/function/all_book_process2.php";
    EditText txtSearch;
    Button btnSearch;
    ImageButton btnHome, btnProfile, btnPengembalian;
    String user_search_input;

    private static final String TAG_ID="id";
    private static final String TAG_TITLE="title";
    private static final String TAG_AUTHOR="author";
    private static final String TAG_TYPE="type";
    private static final String TAG_PAGES="pages";
    private static final String TAG_ISBN="isbn";
    private static final String TAG_BORROWED="borrowed";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_search_book_fragment, container, false);

        list_book = new ArrayList<>();
        lv = v.findViewById(R.id.listView);
        btnSearch = v.findViewById(R.id.btnSearch);
        txtSearch = v.findViewById(R.id.search_book);

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
                        String pages = a.getString(TAG_PAGES);
                        String borrowed = a.getString(TAG_BORROWED);
                        String isbn = a.getString(TAG_ISBN);

//                        Log.e("JSON", title + "||" + author + "||" + type);

                        HashMap<String, String> map = new HashMap<>();
                        map.put("id", id);
                        map.put("title", title);
                        map.put("author",author);
                        map.put("type", type);
                        map.put("pages", pages);
                        map.put("isbn", isbn);
                        map.put("borrowed", borrowed);

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

                                String books_id = list_book.get(position).get(TAG_ID);
                                String book_title = list_book.get(position).get(TAG_TITLE);
                                String book_type = list_book.get(position).get(TAG_TYPE);
                                String book_author = list_book.get(position).get(TAG_AUTHOR);
                                String book_isbn = list_book.get(position).get(TAG_ISBN);
                                String book_borrowed = list_book.get(position).get(TAG_BORROWED);
                                String book_pages = list_book.get(position).get(TAG_PAGES);


                                Intent i = new Intent(v.getContext(), BookProfileActivity.class);
                                i.putExtra("id", books_id);
                                i.putExtra("title", book_title);
                                i.putExtra("type", book_type);
                                i.putExtra("author", book_author);
                                i.putExtra("isbn", book_isbn);
                                i.putExtra("borrowed", book_borrowed);
                                i.putExtra("pages", book_pages);
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
                Toast.makeText(getContext(), "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
        queue.add(stringRequest);

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
                user_search_input = txtSearch.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(getContext());
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
                                        getContext(), list_book, R.layout.list_book,
                                        from, to);
                                lv.setAdapter(adapter);
                                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                String nomor = list_book.get(position).get(TAG_ID);
                                Intent i = new Intent(getContext(), BookProfileActivity.class);
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
                        Toast.makeText(getContext(), "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
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