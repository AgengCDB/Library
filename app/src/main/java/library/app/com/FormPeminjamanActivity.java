package library.app.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class FormPeminjamanActivity extends AppCompatActivity {

    String the_id, the_title, the_type, the_author, the_borrowed, the_isbn, the_pages, the_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_peminjaman);

        Intent i = getIntent();
        the_id = i.getStringExtra("id");
        the_title = i.getStringExtra("title");
        the_type = i.getStringExtra("type");
        the_author = i.getStringExtra("author");
        the_borrowed = i.getStringExtra("borrowed");
        the_isbn = i.getStringExtra("isbn");
        the_pages = i.getStringExtra("pages");
        the_status = i.getStringExtra("status");


    }
}