package library.app.com;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

        name.setText("Hello, "+ id +"!");

        //Tempo Log-Out Button
        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Apakah anda yakin ingin logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sessionManager.logout();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });

        // Intent
        ImageButton btnPencarian = (ImageButton) findViewById(R.id.btnPencarian);
        btnPencarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PencarianActivity.class);
                startActivity(i);
            }
        });

        ImageButton btnPinjamBuku = (ImageButton) findViewById(R.id.btnPinjamBuku);
        btnPinjamBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PeminjamanActivity.class);
                startActivity(i);
            }
        });

        ImageButton btnProfile = (ImageButton) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(i);
            }
        });
    }
}