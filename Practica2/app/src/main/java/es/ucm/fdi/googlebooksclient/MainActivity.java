package es.ucm.fdi.googlebooksclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int BOOK_LOADER_ID = 0;
    private ArrayList<BookInfo> mBooksData;
    private TextView loadingText;

    private BooksResultListAdapter adapter = new BooksResultListAdapter();
    private BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks(this, adapter);

    private RadioGroup grupo;



    RecyclerView recycler;
    enum Tipo{LIBRO, REVISTA, AMBOS};
    Tipo tipoSeleccionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Oculta el teclado por defecto
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(BOOK_LOADER_ID) != null){
            loaderManager.initLoader(BOOK_LOADER_ID,null, bookLoaderCallbacks);
        }


        //Radio Buttons Config
        tipoSeleccionado = Tipo.AMBOS;
        grupo = findViewById(R.id.radioGroup);
        grupo.check(R.id.radioBothButton);

        //Recycler view
        recycler = findViewById(R.id.booksmainrecycler);
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));

        loadingText = findViewById(R.id.loadingText);
        adapter.setLoadingTextView(loadingText);



        recycler.setAdapter(adapter);

        mBooksData = new ArrayList<>();
      /*  for(int i = 0; i < 10; i++)
        {
            BookInfo b = new BookInfo("Libro " + i);
            mBooksData.add(b);
        }

        adapter.updateBooksResultList(mBooksData);*/

    }


    public void searchBooks(View view){

        // oculta el teclado con estas tres lineas
        ConstraintLayout mainLayout = findViewById(R.id.mainLayout);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

      //  adapter.restart();


        EditText titleEditText = findViewById(R.id.bookFinderTit);
        EditText autEditText = findViewById(R.id.bookFinderAut);




        String queryString = titleEditText.getText().toString();
        String printType = getPrintType(tipoSeleccionado);


        Log.i("MAIN","Estoy buscando " + tipoSeleccionado + " "+ queryString);
        loadingText = findViewById(R.id.loadingText);
        loadingText.setText("Cargando..."); //TODO poner var para que cambie con el idioma

        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);
        LoaderManager.getInstance(this).restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);
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

    private String getPrintType(Tipo tipo){
        String tipoString = null;
        switch (tipo){
            case LIBRO:
                tipoString = "books";
                break;
            case REVISTA:
                tipoString = "magazines";
                break;
            default:
                tipoString = "all";
        }
        return tipoString;
    }

}