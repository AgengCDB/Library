package library.app.com;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtPhone, edtPassword;
    Button btnEdit, btnHapus;
    SessionManager sessionManager;
    String update_url = "http://booktify.my.id/QueryMobApp/function/update_profile_process.php";
    String delete_url = "http://booktify.my.id/QueryMobApp/function/delete_profile_process.php";
    String input_id, input_name, input_email, input_phone;

    ImageButton btnHome, btnPencarian, btnPengembalian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Apply Session Manager with their classes
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        HashMap<String, String> user = sessionManager.getUserDetail();
        String id = user.get(sessionManager.ID_USER);
        String username = user.get(sessionManager.USERNAME);
        String email = user.get(sessionManager.EMAIL);
        String phone = user.get(sessionManager.PHONE);

        //Declare EditText
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPhone = (EditText) findViewById(R.id.edtPhoneNumber);

        //Declare Button
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnHapus = (Button) findViewById(R.id.btnHapus);

        //Set Text to EditText from Session Manager
        edtUsername.setText(username);
        edtEmail.setText(email);
        edtPhone.setText(phone);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting Text into String
                input_name = edtUsername.getText().toString();
                input_email = edtEmail.getText().toString();
                input_phone = edtPhone.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, update_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            int success = jsonObject.getInt("success");
                            if (success == 1) {
                                Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            }else if (success == -1) {
                                Toast.makeText(ProfileActivity.this, "Email perubahan anda sudah terdaftar", Toast.LENGTH_SHORT).show();
                            }else if (success == -2 ) {
                                Toast.makeText(ProfileActivity.this, "Nomor Handphone baru anda sudah terdaftar", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ProfileActivity.this, "Profile Gagal Di Update", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Log.e("Error", ex.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.getMessage());
                        Toast.makeText(ProfileActivity.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("id_user", id);
                        params.put("username", input_name);
                        params.put("email", input_email);
                        params.put("phone", input_phone);
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
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ProfileActivity.this);
                alert.setTitle("Apakah anda ingin hapus akun anda?")
                        .setMessage("Jika memilih 'Yes', semua data anda akan terhapus secara permanen")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, delete_url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try{
                                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                            int success = jsonObject.getInt("success");
                                            if (success == 1) {
                                                sessionManager.logout();
                                            } else {
                                                Toast.makeText(ProfileActivity.this, "Data tidak terhapus", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("Errors", error.getMessage());
                                        Toast.makeText(ProfileActivity.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("id_user", id);
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
                        }).setNeutralButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
        btnPencarian = findViewById(R.id.btnPencarian);
        btnPencarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PencarianActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        });

        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
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
    }
}