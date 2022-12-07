package library.app.com;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ViewPager mViewPager;

    String user_info = "";

    ImageButton btnPencarian, btnPinjam;
    ImageButton btnPengembalian, btnHistory, btnProfile;

    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.pager); //cari id pager untuk diassign ke mViewPager
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

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
/*
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                sessionManager.createSession(id_user, username, email, phone);
                //Creating Intent
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                i.putExtra("id_user", id_user);
                i.putExtra("username", username);
                i.putExtra("email", email);
                i.putExtra("phone", phone);

 */
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        });


    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        } //contructor untuk adapter ViewPager dan FragmentManager

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeFragment();
                //kalau posisi sedang ada di halaman pertama, maka tampilkan FragmentOne
            } else {
                return new SearchBookFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}