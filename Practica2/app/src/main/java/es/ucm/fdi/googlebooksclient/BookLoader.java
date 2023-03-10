package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<BookInfo>> {

    private String queryString;
    private String printType;

    public BookLoader(Context context, String queryString, String printType) {
        super(context);
        this.queryString = queryString;
        this.printType = printType;
    }

    @Nullable
    @Override
    public List<BookInfo> loadInBackground() {
        String json = NetworkUtils.getBookInfoJson(queryString, printType);
        return fromJsonResponse(json);
    }

    private static List<BookInfo> fromJsonResponse(String s){
        ArrayList<BookInfo> res = new ArrayList<BookInfo>();

        try{
            JSONObject json = new JSONObject(s);

            JSONArray jBooks = json.getJSONArray("items");

            for (int i = 0; i < jBooks.length(); i++){

                JSONObject jBook = jBooks.getJSONObject(i);
                JSONObject info = jBook.getJSONObject("volumeInfo");

                URL url = new URL(jBook.getString("selfLink"));
                String title = info.getString("title");
                String authors = null;

                if (info.has("authors")){

                    JSONArray jAuthors = info.getJSONArray("authors");
                    StringBuilder sb = new StringBuilder();

                    for (int j = 0; j < jAuthors.length(); j++){
                        sb.append(jAuthors.getString(j));
                    }


                    authors = sb.toString();
                }

                res.add(new BookInfo(title, authors, url));

            }

        }
        catch(Exception e){
            Log.e("ParseJSON", "Error al intentar parsear el json de resultados");
            Log.e("ParseJSON", e.getMessage());
            res = null;
        }


        return res;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }


}
