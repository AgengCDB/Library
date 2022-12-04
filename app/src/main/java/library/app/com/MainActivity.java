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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String user_info = "";

    ImageButton btnPencarian, btnPinjam;
    ImageButton btnPengembalian, btnHistory, btnProfile;

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
        String id_user = user.get(sessionManager.ID_USER);
        String username = user.get(sessionManager.USERNAME);
        String email = user.get(sessionManager.EMAIL);
        String phone = user.get(sessionManager.PHONE);

        name.setText("Hello, "+ username +"!");

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
        btnPencarian = findViewById(R.id.btnPencarian);
        btnPencarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PencarianActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        });

        btnPengembalian = findViewById(R.id.btnPengembalianBuku);
        btnPengembalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PengembalianActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        });

        btnProfile = findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                sessionManager.createSession(id_user, username, email, phone);
                //Creating Intent
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                i.putExtra("id_user", id_user);
                i.putExtra("username", username);
                i.putExtra("email", email);
                i.putExtra("phone", phone);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        });
    }
}