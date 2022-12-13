package library.app.com;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class WhislistActivity extends ListActivity implements AdapterView.OnItemLongClickListener {

    private MyDBHandler dbHandler;
    private ArrayList<WhislistPinjamBuku> values;
    private Button btnEdit;
    private Button btnDelete;
    private ListView listWhislist;
    Context context = this;

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Menampilkan dialog
        Intent j = new Intent(getApplicationContext(), BookProfileActivity.class);
        startActivity(j);

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whislist);

        //Object class MyDBHandler
        dbHandler = new MyDBHandler(this);

        //Membuka koneksi database
        try {
            dbHandler.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        values = dbHandler.getAllWhislist();

        ArrayAdapter<WhislistPinjamBuku> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);

        setListAdapter(adapter);

        listWhislist = (ListView) findViewById(android.R.id.list);
        listWhislist.setOnItemLongClickListener(this);
    }


}