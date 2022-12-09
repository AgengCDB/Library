package library.app.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class BookProfileActivity extends AppCompatActivity {

    ListView listComment;
    ArrayList<HashMap<String,String>> list_comment;
    String url_get_book_profile="";
    String url_get_comment="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_profile);

        list_comment = new ArrayList<>();

    }
}