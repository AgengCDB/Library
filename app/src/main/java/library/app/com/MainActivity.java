package library.app.com;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

import library.app.com.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ViewPager mViewPager;
    BottomNavigationView bottomNavigationView;

    String user_info = "";

    private TextView name;
    private ImageButton btnH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.pager); //cari id pager untuk diassign ke mViewPager
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(2);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView);
        bottomNavigationView.setSelectedItemId(R.id.btnHome);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (mViewPager.getCurrentItem() == 2) { //Page home
                    //Set imageButton image transparent
                    bottomNavigationView.setSelectedItemId(R.id.btnHome);
                } else if (mViewPager.getCurrentItem() == 1) { //Page SearchBookFragment
                    //Set imageButton image transparent
                    bottomNavigationView.setSelectedItemId(R.id.btnSearch);
                } else if (mViewPager.getCurrentItem() == 3) { //Page Return book
                    //Set imageButton image transparent
                    bottomNavigationView.setSelectedItemId(R.id.btnReturnbook);
                } else if (mViewPager.getCurrentItem() == 4) { //Page profile
                    //Set imageButton image transparent
                    bottomNavigationView.setSelectedItemId(R.id.btnProfile);
                } else if (mViewPager.getCurrentItem() == 0) {
                    //Set imageButton image transparent
                    bottomNavigationView.setSelectedItemId(R.id.btnBookshelf);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                switch (menuItem.getItemId()){
                    case R.id.btnBookshelf:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.btnSearch:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.btnHome:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.btnReturnbook:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.btnProfile:
                        mViewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });
    }

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