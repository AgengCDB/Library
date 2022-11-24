package library.app.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText edtEmail;
    EditText edtPhone;
    EditText edtPassword;
    EditText edtUsername;
    Button btnRegister;
    String email, phone, username, password;

    String url_tambah_anggota = "http://booktify.my.id/function/register_process.php";// ganti dengan url kalian sendiri

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtEmail = (EditText) findViewById(R.id.txtEmail2);
        edtPhone = (EditText) findViewById(R.id.txtPhoneNumber);
        edtPassword = (EditText) findViewById(R.id.txtPassword2);
        edtUsername = (EditText) findViewById(R.id.txtUsername);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();
                phone = edtPhone.getText().toString();
                username = edtUsername.getText().toString();
                //progressBar.setVisibility(View.VISIBLE);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_tambah_anggota, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            int sukses = jObj.getInt("success");
                            if (sukses == 1) {
                                Toast.makeText(RegisterActivity.this, "Data User berhasil disimpan", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Data User gagal disimpan", Toast.LENGTH_SHORT).show();
                            }
                            //progressBar.setVisibility(View.GONE);
                        } catch (Exception ex) {
                            Log.e("Error", ex.toString());
                            //progressBar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.getMessage());
                        Toast.makeText(RegisterActivity.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                        //progressBar.setVisibility(View.GONE);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        params.put("password", password);
                        params.put("phone", phone);
                        params.put("username", username);
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