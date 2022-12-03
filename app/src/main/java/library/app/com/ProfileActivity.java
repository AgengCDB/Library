package library.app.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtPhone, edtPassword;
    Button btnEdit, btnHapus;
    TextView btnChangePw;
    SessionManager sessionManager;

    private static final String TAG_ID = "id";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_PHONE = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPhone = (EditText) findViewById(R.id.edtPhoneNumber);

        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnHapus = (Button) findViewById(R.id.btnHapus);
        btnChangePw = (TextView) findViewById(R.id.btnChangePassword);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        String id_user = user.get(sessionManager.ID_USER);
        String username = user.get(sessionManager.USERNAME);
        String email = user.get(sessionManager.EMAIL);
        String phone = user.get(sessionManager.PHONE);

        edtUsername.setText(username);
        edtEmail.setText(email);
        edtPhone.setText(phone);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnChangePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}