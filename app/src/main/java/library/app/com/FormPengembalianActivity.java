package library.app.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;

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

    String rentId, userId, bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pengembalian);

        sessionManager = new SessionManager(FormPengembalianActivity.this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        String userid = user.get(sessionManager.ID_USER);
        String username = user.get(sessionManager.USERNAME);
        String email = user.get(sessionManager.EMAIL);
        String phone = user.get(sessionManager.PHONE);

        Intent a = getIntent();
/*
        i.putExtra("rent_id", rentId);
        i.putExtra("book_id", bookId);
        i.putExtra("rentStart", rentStart);
        i.putExtra("rent_end", rentEnd);
        i.putExtra("overdue", overdueCost);
        i.putExtra("status", rentStatus);

 */

    }
}