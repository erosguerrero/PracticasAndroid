package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>>{

    private Context context;
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_AUTHOR = "author";
    public static final String EXTRA_PRINT_TYPE = "printType";
   // private TextView loadingText;

    private BooksResultListAdapter adapter;
    public BookLoaderCallbacks(Context context, BooksResultListAdapter adapter){
        this.context = context;
        this.adapter = adapter;
       // this.loadingText = loadingText;
    }

    @NonNull
    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, @Nullable Bundle args) {

        String title = null;
        String author = null;
        String printType = null;

        if (args != null){
            title = args.getString(EXTRA_TITLE).trim();
            if (title.equals("")) title = null;

            author = args.getString(EXTRA_AUTHOR).trim();
            if(author.equals("")) author = null;

            printType = args.getString(EXTRA_PRINT_TYPE);
            adapter.restart();
        }

        return new BookLoader(context, title, author, printType);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<BookInfo>> loader, List<BookInfo> data) {
        Log.d("Carga", "Carga completada");
        adapter.updateBooksResultList(data);


       // loadingText.setText("");

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<BookInfo>> loader) {

    }
}
