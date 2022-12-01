package library.app.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String user_info = "";

    private TextView name;
    SessionManager sessionManager;

    private static final String TAG_ID = "id";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_PHONE = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Apply Session Manager with their classes
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        //Text View -> Username
        name = findViewById(R.id.txtHelloUser);

        HashMap<String, String> user = sessionManager.getUserDetail();
        String id = user.get(sessionManager.ID_USER);
        String username = user.get(sessionManager.USERNAME);
        String email = user.get(sessionManager.EMAIL);
        String phone = user.get(sessionManager.PHONE);

        name.setText("Hello, "+ username +"!");

        //Tempo Log-Out Button
        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });
    }
}