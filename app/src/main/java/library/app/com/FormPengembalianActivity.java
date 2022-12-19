package library.app.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class FormPengembalianActivity extends AppCompatActivity {

    SessionManager sessionManager;

    private static final String TAG_ID="id";
    private static final String TAG_TITLE="title";
    private static final String TAG_AUTHOR="author";
    private static final String TAG_TYPE="type";
    private static final String TAG_PAGES="pages";
    private static final String TAG_ISBN="isbn";
    private static final String TAG_BORROWED="borrowed";
    private static final String TAG_STATUS="status";

    String url_return_book = "https://booktify.my.id/QueryMobApp/function/return_book_process.php";
    String rentID, bookID, rentStart, rentEnd, overdue, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pengembalian);

        sessionManager = new SessionManager(FormPengembalianActivity.this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        String userid = user.get(sessionManager.ID_USER);;

        Intent a = getIntent();

        rentID = a.getStringExtra("rent_id");
        bookID = a.getStringExtra("book_id");
        rentStart = a.getStringExtra("rentStart");
        rentEnd = a.getStringExtra("rent_end");
        overdue =  a.getStringExtra("overdue");
        status = a.getStringExtra("status");

        TextView book_id = (TextView) findViewById(R.id.txtBookID);
        TextView rent_id = (TextView) findViewById(R.id.txtRentsID);
        TextView user_id = (TextView) findViewById(R.id.txtUserID);
        TextView rent_date = (TextView) findViewById(R.id.txtRentDate);
//
        book_id.setText(bookID.toString());
        rent_id.setText(rentID.toString());
        user_id.setText(userid.toString());
        rent_date.setText(rentStart.toString());

        Button btn = (Button) findViewById(R.id.btnReturn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_return_book, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if (success == 1) {
                                Toast.makeText(getApplicationContext(), "Pengembalian Berhasil Dilakukan", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "Pengembalian Gagal Dilakukan", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("rent_id", rentID);
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        return params;
                    }
                };
                requestQueue.getCache().clear();
                requestQueue.add(stringRequest);
            }
        });
    }
}