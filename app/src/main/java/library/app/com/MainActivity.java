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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

import library.app.com.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ViewPager mViewPager;

    String user_info = "";

    ImageButton btnPencarian, btnPengembalian, btnBookshelf, btnProfile, btnHome;

    private TextView name;
    private ImageButton btnH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.pager); //cari id pager untuk diassign ke mViewPager
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(2);
/*
        binding.bottomNavView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.btnHome:
                    mViewPager.setCurrentItem(2);
                    break;

                case R.id.btnBookshelf:
                    mViewPager.setCurrentItem(0);
                    break;

                case R.id.btnProfile:
                    mViewPager.setCurrentItem(4);
                    break;

                case R.id.btnReturnbook:
                    mViewPager.setCurrentItem(3);
                    break;

                case R.id.btnSearch:
                    mViewPager.setCurrentItem(1);
            }
            return true;
        });
*/


/*
        btnHome = findViewById(R.id.btnHome);
        btnPencarian = findViewById(R.id.btnPencarian);
        btnPengembalian = findViewById(R.id.btnPengembalianBuku);
        btnProfile = findViewById(R.id.btnProfile);
        btnBookshelf = findViewById(R.id.btnBookshelf);

 */

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
  //          @Override
    //        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
/*
            @Override
            public void onPageSelected(int position) {

                if (mViewPager.getCurrentItem() == 2) { //Page home
                    //Set imageButton image transparent
                    btnProfile.setImageResource(android.R.color.transparent);
                    btnHome.setImageResource(android.R.color.transparent);
                    btnPencarian.setImageResource(android.R.color.transparent);
                    btnPengembalian.setImageResource(android.R.color.transparent);
                    btnBookshelf.setImageResource(android.R.color.transparent);

                    //Set image for selected page
                    btnHome.setImageResource(R.drawable.home_nav);
                    btnProfile.setImageResource(R.drawable.profile_nav);
                    btnPencarian.setImageResource(R.drawable.search_nav);
                    btnPengembalian.setImageResource(R.drawable.return_nav);
                    btnBookshelf.setImageResource(R.drawable.bookshelf_nav);
                } else if (mViewPager.getCurrentItem() == 1) { //Page SearchBookFragment
                    //Set imageButton image transparent
                    btnProfile.setImageResource(android.R.color.transparent);
                    btnHome.setImageResource(android.R.color.transparent);
                    btnPencarian.setImageResource(android.R.color.transparent);
                    btnPengembalian.setImageResource(android.R.color.transparent);
                    btnBookshelf.setImageResource(android.R.color.transparent);

                    //Set image for selected page
                    btnHome.setImageResource(R.drawable.home_nav_notselected);
                    btnProfile.setImageResource(R.drawable.profile_nav);
                    btnPencarian.setImageResource(R.drawable.search_nav_selected);
                    btnPengembalian.setImageResource(R.drawable.return_nav);
                    btnBookshelf.setImageResource(R.drawable.bookshelf_nav);
                    btnBookshelf.setImageResource(R.drawable.bookshelf_nav);
                } else if (mViewPager.getCurrentItem() == 3) { //Page Return book
                    //Set imageButton image transparent
                    btnProfile.setImageResource(android.R.color.transparent);
                    btnHome.setImageResource(android.R.color.transparent);
                    btnPencarian.setImageResource(android.R.color.transparent);
                    btnPengembalian.setImageResource(android.R.color.transparent);
                    btnBookshelf.setImageResource(android.R.color.transparent);

                    //Set image for selected page
                    btnHome.setImageResource(R.drawable.home_nav_notselected);
                    btnProfile.setImageResource(R.drawable.profile_nav);
                    btnPencarian.setImageResource(R.drawable.search_nav);
                    btnPengembalian.setImageResource(R.drawable.return_book_selected);
                    btnBookshelf.setImageResource(R.drawable.bookshelf_nav);
                } else if (mViewPager.getCurrentItem() == 4) { //Page profile
                    //Set imageButton image transparent
                    btnProfile.setImageResource(android.R.color.transparent);
                    btnHome.setImageResource(android.R.color.transparent);
                    btnPencarian.setImageResource(android.R.color.transparent);
                    btnPengembalian.setImageResource(android.R.color.transparent);
                    btnBookshelf.setImageResource(android.R.color.transparent);

                    //Set image for selected page
                    btnHome.setImageResource(R.drawable.home_nav_notselected);
                    btnProfile.setImageResource(R.drawable.profile_selected_nav);
                    btnPencarian.setImageResource(R.drawable.search_nav);
                    btnPengembalian.setImageResource(R.drawable.return_nav);
                    btnBookshelf.setImageResource(R.drawable.bookshelf_nav);
                } else if (mViewPager.getCurrentItem() == 0) {
                    //Set imageButton image transparent
                    btnProfile.setImageResource(android.R.color.transparent);
                    btnHome.setImageResource(android.R.color.transparent);
                    btnPencarian.setImageResource(android.R.color.transparent);
                    btnPengembalian.setImageResource(android.R.color.transparent);
                    btnBookshelf.setImageResource(android.R.color.transparent);

                    //Set image for selected page
                    btnHome.setImageResource(R.drawable.home_nav_notselected);
                    btnProfile.setImageResource(R.drawable.profile_nav);
                    btnPencarian.setImageResource(R.drawable.search_nav);
                    btnPengembalian.setImageResource(R.drawable.return_nav);
                    btnBookshelf.setImageResource(R.drawable.bookshelf_selected);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

                 */


                // Fragment
                /*
        btnBookshelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
            }
        });

        btnPencarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(1);
            }
        });

        btnPengembalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(3);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(2);
            }
        });

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


                mViewPager.setCurrentItem(4);
            }
        });



    }

                 */

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        } //contructor untuk adapter ViewPager dan FragmentManager

        @Override
        public Fragment getItem(int position) {
            if (position == 2) {
                return new HomeFragment();
                //kalau posisi sedang ada di halaman pertama, maka tampilkan FragmentOne
            } else if (position == 1) {
                return new SearchBookFragment();
            } else if (position == 0) {
                return new BookshelfFragment();
            } else if (position == 3) {
                return new PengembalianFragment();
            } else {
                return new ProfileFragment();
            }
        }



        @Override
        public int getCount() {
            return 5;
        }
    }
    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }


            }