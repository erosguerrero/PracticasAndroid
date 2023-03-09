package es.ucm.fdi.googlebooksclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int BOOK_LOADER_ID = 0;
    private ArrayList<BookInfo> mBooksData;
    private BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks(this);

    RecyclerView recycler;
    enum Tipo{LIBRO, REVISTA, AMBOS};
    Tipo tipoSeleccionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(BOOK_LOADER_ID) != null){
            loaderManager.initLoader(BOOK_LOADER_ID,null, bookLoaderCallbacks);
        }


        //Radio Buttons Config
        tipoSeleccionado = Tipo.AMBOS;
        RadioGroup grupo = findViewById(R.id.radioGroup);
        grupo.check(R.id.radioBothButton);

        //Recycler view
        recycler = findViewById(R.id.booksmainrecycler);
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        mBooksData = new ArrayList<BookInfo>();
        //False data
        for (int i = 1; i <= 20; i++) {
            mBooksData.add(new BookInfo("Libro "+i));
        }
        //End false data
        BooksResultListAdapter adapter = new BooksResultListAdapter();
        adapter.setBooksData(mBooksData);

        recycler.setAdapter(adapter);

    }


    public void searchBooks(View view){

        String queryString = ((EditText) findViewById(R.id.bookFinder)).getText().toString();
        Log.i("MAIN","Estoy buscando " + tipoSeleccionado + " "+ queryString);


        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, "books" );
        LoaderManager.getInstance(this)
                .restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);
    }
    public void setType(View view){
        switch(view.getId()) {
            case R.id.radioBookButton: {
                tipoSeleccionado = Tipo.LIBRO;
                break;
            }
            case R.id.radioMagazineButton: {
                tipoSeleccionado = Tipo.REVISTA;
                break;
            }
            default:{
                tipoSeleccionado = Tipo.AMBOS;
            }
        }
    }

}