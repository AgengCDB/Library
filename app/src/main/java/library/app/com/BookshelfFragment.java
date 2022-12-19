package library.app.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BookshelfFragment extends Fragment {

    //Deklarasi Komponen
    Button btnWhislist, btnHistory, btnKembalian;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_bookshelf_fragment, container, false);

        //Deklarasi button
        btnWhislist = v.findViewById(R.id.btnWhislist);
        btnHistory = v.findViewById(R.id.btnSeeHistory);
        btnKembalian = v.findViewById(R.id.btnReturnBook);

        //Button Lihat Wishlist Buku
        btnWhislist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), WhislistActivity.class);
                startActivity(i);
            }
        });

        //Button Lihat History Buku
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), HistoryActivity.class);
                startActivity(i);
            }
        });

        //Button Kembalikan Buku
        btnKembalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), FormPengembalianActivity.class);
                startActivity(i);
            }
        });
        return v;
    }
}

