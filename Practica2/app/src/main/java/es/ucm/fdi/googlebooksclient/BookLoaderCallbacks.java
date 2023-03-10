package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>>{

    private Context context;
    public static final String EXTRA_QUERY = "queryString";
    public static final String EXTRA_PRINT_TYPE = "printType";

    private BooksResultListAdapter adapter;
    public BookLoaderCallbacks(Context context, BooksResultListAdapter adapter){
        this.context = context;
        this.adapter = adapter;
    }

    @NonNull
    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, @Nullable Bundle args) {

        String queryString = "";
        String printType = "";

        if (args != null){
            queryString = args.getString(EXTRA_QUERY);
            printType = args.getString(EXTRA_PRINT_TYPE);
        }

        return new BookLoader(context, queryString, printType);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<BookInfo>> loader, List<BookInfo> data) {
        Log.d("Carga", "Carga completada");
        adapter.updateBooksResultList(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<BookInfo>> loader) {

    }
}
