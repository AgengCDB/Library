package library.app.com;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FormPeminjamanActivity extends AppCompatActivity {

    String the_id, the_title, the_type, the_author, the_borrowed, the_isbn, the_pages, the_status;
    TextView txtBookID, txtUserID, txtUsername, txtTitle, txtAuthor, rentStart;
    private Button rentEnd, btnRent;
    private DatePickerDialog datePickerDialog;

    String borrow_book = "https://booktify.my.id/QueryMobApp/function/rent_register_process.php";
    String get_bookid, get_userid, get_rentstart, get_rentend;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_peminjaman);
        initDatePicker();

        sessionManager = new SessionManager(FormPeminjamanActivity.this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        String userid = user.get(sessionManager.ID_USER);
        String username = user.get(sessionManager.USERNAME);
        String email = user.get(sessionManager.EMAIL);
        String phone = user.get(sessionManager.PHONE);

        Intent i = getIntent();
        the_id = i.getStringExtra("id");
        the_title = i.getStringExtra("title");
        the_type = i.getStringExtra("type");
        the_author = i.getStringExtra("author");
        the_borrowed = i.getStringExtra("borrowed");
        the_isbn = i.getStringExtra("isbn");
        the_pages = i.getStringExtra("pages");
        the_status = i.getStringExtra("status");

        txtBookID = findViewById(R.id.txtBookID);
        txtBookID.setText("BookID : " + the_id);

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle. setText(the_title);

        txtAuthor = findViewById(R.id.txtAuthor);
        txtAuthor.setText(the_author);

        txtUserID = findViewById(R.id.txtUserID);
        txtUserID.setText("UserID : " + userid);

        txtUsername = findViewById(R.id.txtUsername);
        txtUsername.setText("Username : " + username);

        rentStart = findViewById(R.id.rentStart);
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        rentStart.setText(currentDate);
        rentStart.setEnabled(false);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        int cday = calendar.get(Calendar.DAY_OF_MONTH);
        int cmonth = calendar.get(Calendar.MONTH);
        int cyear = calendar.get(Calendar.YEAR);

        rentEnd = findViewById(R.id.rentEnd);
        rentEnd.setText(makeDateString(cday, cmonth, cyear));

        btnRent = findViewById(R.id.btnRent);
        btnRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting Text into String
                get_bookid = the_id;
                get_userid = userid;
                get_rentstart = rentStart.getText().toString();
                get_rentend = rentEnd.getText().toString();

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, borrow_book, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if (success == 0) {
                                Toast.makeText(getApplicationContext(), "Peminjaman Berhasil Dilakukan", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "Peminjaman Gagal Dilakukan", Toast.LENGTH_SHORT).show();
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
                        params.put("id_user", get_userid);
                        params.put("book_id", get_bookid);
                        params.put("start_date", get_rentstart);
                        params.put("end_date", get_rentend);
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

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                rentEnd.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        cal.add(Calendar.DAY_OF_MONTH, 13);
        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}