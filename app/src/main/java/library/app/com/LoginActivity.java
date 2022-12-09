package library.app.com;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    //.
    //Variable
    EditText txtEmail;
    EditText txtPassword;
    Button btnLogin, sEmail, sPhone;
    TextView txt;
    String email, password;

    //Link Login
    String user_login_email = "http://172.21.95.182/Library/function/login_email_process.php";

    //Session Manager
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txt = (TextView) findViewById(R.id.txtCreateAccount);

        // switch email & phone
        sEmail = (Button) findViewById(R.id.switchEmail);
        sPhone = (Button) findViewById(R.id.switchPhone);

        sEmail.setBackgroundResource(R.drawable.button_login_email_off);
        sEmail.setTextColor(getResources().getColor(R.color.white));
        sPhone.setTextColor(getResources().getColor(R.color.black));
        sPhone.setBackgroundResource(R.drawable.button_login_phone_on);

        sEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtEmail.setHint("Email");
                txtEmail.setInputType(InputType.TYPE_CLASS_TEXT);
                sEmail.setBackgroundResource(R.drawable.button_login_email_off);
                sPhone.setBackgroundResource(R.drawable.button_login_phone_on);
                sEmail.setTextColor(getResources().getColor(R.color.white));
                sPhone.setTextColor(getResources().getColor(R.color.black));
            }
        });
        sPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtEmail.setHint("Phone");
                txtEmail.setInputType(InputType.TYPE_CLASS_NUMBER);
                sEmail.setBackgroundResource(R.drawable.button_login_email_on);
                sPhone.setBackgroundResource(R.drawable.button_login_phone_off);
                sEmail.setTextColor(getResources().getColor(R.color.black));
                sPhone.setTextColor(getResources().getColor(R.color.white));
            }
        });

        //Masuk Ke Halaman Login Dengan Intent
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        loginEmail();
    }

    public void loginEmail() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting User Input
                email = txtEmail.getText().toString();
                password = txtPassword.getText().toString();

                //Apply Volley to this Activity
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

                //Entering Link URL
                StringRequest stringRequest = new StringRequest(Request.Method.POST, user_login_email, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //Getting int from Success on PHP
                            int respond = jsonObject.getInt("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            //if clause condition
                            if (respond == 1) {
                                //Fetching Row and put them into Session
                                for (int i = 0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id_user = object.getString("id_user").trim();
                                    String username = object.getString("username").trim();
                                    String email = object.getString("email").trim();
                                    String phone = object.getString("phone").trim();

                                    Log.e("testJS", id_user);

                                    //Apply Create Session
                                    sessionManager.createSession(id_user, username, email, phone);
                                    //Creating Intent
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("id_user", id_user);
                                    intent.putExtra("username", username);
                                    intent.putExtra("email", email);
                                    intent.putExtra("phone", phone);

                                    startActivity(intent);
                                    finish();
                                }
                            } else if (respond == -1) {
                                Toast.makeText(LoginActivity.this, "Email Anda Belum Terdaftar, Silahkan Register Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                            } else if (respond == -2) {
                                Toast.makeText(LoginActivity.this, "Akun anda belum terverifikasii", Toast.LENGTH_SHORT).show();
                            }
                            else {
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
    }
}