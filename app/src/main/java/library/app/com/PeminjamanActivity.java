package library.app.com;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PeminjamanActivity extends AppCompatActivity {

    EditText tanggalPengembalian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman);

        tanggalPengembalian = (EditText) findViewById(R.id.txtTanggalPengembalian);
        tanggalPengembalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}