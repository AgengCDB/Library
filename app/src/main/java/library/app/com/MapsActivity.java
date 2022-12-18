package library.app.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;

public class MapsActivity extends AppCompatActivity {

    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        MapsFragment map = new MapsFragment();
        map.setArguments(getIntent().getExtras());

        FragmentManager frgManager = getSupportFragmentManager();
        FragmentTransaction frgTrans = frgManager.beginTransaction();

        frgTrans.add(R.id.frame1, map);
        frgTrans.commit();


        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}