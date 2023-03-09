package es.ucm.fdi.googlebooksclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<BookInfo> mBooksData;
    RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recycler view
        recycler = findViewById(R.id.booksmainrecycler);
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        mBooksData = new ArrayList<BookInfo>();
        //False data
        for (int i = 1; i <= 20; i++) {
            mBooksData.add(new BookInfo("Libro "+i));
        }
        //End false data
        Log.i("lamo", "lamo"+mBooksData.size());
        BooksResultListAdapter adapter = new BooksResultListAdapter();
        adapter.setBooksData(mBooksData);

        recycler.setAdapter(adapter);
    }
}