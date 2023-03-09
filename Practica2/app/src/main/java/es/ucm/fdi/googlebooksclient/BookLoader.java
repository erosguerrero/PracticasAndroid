package es.ucm.fdi.googlebooksclient;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BookLoader extends AsyncTaskLoader<String> {

    private String queryString;
    private String printType;

    public BookLoader(Context context, String queryString, String printType) {
        super(context);
        this.queryString = queryString;
        this.printType = printType;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getBookInfoJson(queryString, printType);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


}
