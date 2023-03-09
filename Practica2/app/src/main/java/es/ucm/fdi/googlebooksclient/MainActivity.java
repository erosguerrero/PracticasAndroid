package es.ucm.fdi.googlebooksclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<BookInfo> mBooksData;
    RecyclerView recycler;
    enum Tipo{LIBRO, REVISTA, AMBOS};
    Tipo tipoSeleccionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void searchButton(View view){
        EditText textView = (EditText ) findViewById(R.id.bookFinder);
        String toSearch = textView.getText().toString();
        Log.i("MAIN","Estoy buscando " + tipoSeleccionado + " "+toSearch);

    }
}