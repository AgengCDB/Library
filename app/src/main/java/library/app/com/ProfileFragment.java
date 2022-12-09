package library.app.com;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    EditText edtUsername, edtEmail, edtPhone, edtPassword;
    Button btnEdit, btnHapus;
    SessionManager sessionManager;
    String update_url = "http://172.21.95.182/Library/function/update_profile_process.php";
    String delete_url = "http://172.21.95.182/Library/function/delete_profile_process.php";
    String input_id, input_name, input_email, input_phone;
    TextView changePW;

    ImageButton btnHome, btnPencarian, btnPengembalian;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_profile_fragment, container, false);
//Apply Session Manager with their classes
        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        String id = user.get(sessionManager.ID_USER);
        String username = user.get(sessionManager.USERNAME);
        String email = user.get(sessionManager.EMAIL);
        String phone = user.get(sessionManager.PHONE);

        //Declare EditText
        edtUsername = (EditText) v.findViewById(R.id.edtUsername);
        edtEmail = (EditText) v.findViewById(R.id.edtEmail);
        edtPhone = (EditText) v.findViewById(R.id.edtPhoneNumber);

        //Declare ChangePW
        changePW = (TextView)v.findViewById(R.id.btnChangePassword);

        //Declare Button
        btnEdit = (Button) v.findViewById(R.id.btnEdit);
        btnHapus = (Button) v.findViewById(R.id.btnHapus);

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

                RequestQueue queue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, update_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            int success = jsonObject.getInt("success");
                            if (success == 1) {
                                Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                            }else if (success == -1) {
                                Toast.makeText(getContext(), "Email perubahan anda sudah terdaftar", Toast.LENGTH_SHORT).show();
                            }else if (success == -2 ) {
                                Toast.makeText(getContext(), "Nomor Handphone baru anda sudah terdaftar", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), "Profile Gagal Di Update", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Log.e("Error", ex.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.getMessage());
                        Toast.makeText(getContext(), "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
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
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Apakah anda ingin hapus akun anda?")
                        .setMessage("Jika memilih 'Yes', semua data anda akan terhapus secara permanen")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RequestQueue queue = Volley.newRequestQueue(getContext());
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, delete_url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try{
                                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                            int success = jsonObject.getInt("success");
                                            if (success == 1) {
                                                sessionManager.logout();
                                            } else {
                                                Toast.makeText(getContext(), "Data tidak terhapus", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("Errors", error.getMessage());
                                        Toast.makeText(getContext(), "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
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

        changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(0, 0);
            }
        });


        return v;
    }
}