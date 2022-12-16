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

    Button btnWhislist, btnHistory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_bookshelf_fragment, container, false);

        btnWhislist = v.findViewById(R.id.btnWhislist);
        btnHistory = v.findViewById(R.id.btnHistory);

        btnWhislist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), WhislistActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(0, 0);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), HistoryActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(0, 0);
            }
        });

        return v;
    }
}