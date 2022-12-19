package library.app.com;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class FormPeminjamanActivity extends AppCompatActivity {

    String the_id, the_title, the_type, the_author, the_borrowed, the_isbn, the_pages, the_status;
    TextView txtBookID, txtUserID, txtUsername, txtTitle, txtAuthor, rentStart;
    private Button rentEnd;
    private DatePickerDialog datePickerDialog;

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