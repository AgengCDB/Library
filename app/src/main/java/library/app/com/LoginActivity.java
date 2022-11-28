package library.app.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    //.
    //Variable
    EditText txtEmail;
    EditText txtPassword;
    Button btnLogin;
    TextView txt;
    String email, password;

    //Link Login
    String user_login = "http://booktify.my.id/function/login_email_process.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txt = (TextView) findViewById(R.id.txt);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Getting User Input
                email = txtEmail.getText().toString();
                password = txtPassword.getText().toString();

                //Apply Volley to this Activity
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

                //Entering Link URL
                StringRequest stringRequest = new StringRequest(Request.Method.POST, user_login, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //Getting int from Success on PHP
                            int respond = jsonObject.getInt("success");

                            //if clause condition
                            if (respond == 1) {
                                Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            } else if (respond == -1) {
                                Toast.makeText(LoginActivity.this, "Email Anda Belum Terdaftar, Silahkan Register Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                            } else if (respond == -2){
                                Toast.makeText(LoginActivity.this, "Anda belum terdaftar", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Password Anda Salah", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e) {
                            Log.e("Error", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.getMessage());
                        Toast.makeText(LoginActivity.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        params.put("password", password);
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        return params;
                    }
                };
                queue.getCache().clear();
                queue.add(stringRequest);
            }
        });
        //Masuk Ke Halaman Login Dengan Intent
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }
}