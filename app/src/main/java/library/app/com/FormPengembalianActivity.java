package library.app.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FormPengembalianActivity extends AppCompatActivity {

    private static final String TAG_ID="id";
    private static final String TAG_TITLE="title";
    private static final String TAG_AUTHOR="author";
    private static final String TAG_TYPE="type";
    private static final String TAG_PAGES="pages";
    private static final String TAG_ISBN="isbn";
    private static final String TAG_BORROWED="borrowed";
    private static final String TAG_STATUS="status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pengembalian);
    }
}