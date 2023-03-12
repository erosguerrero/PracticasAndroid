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

    private String title;
    private String printType;

    private String author;

    public BookLoader(Context context, String title, String author, String printType) {
        super(context);
        this.title = title;
        this.author = author;
        this.printType = printType;
    }

    @Nullable
    @Override
    public List<BookInfo> loadInBackground() {

        if (title != null || author != null){
            String json = NetworkUtils.getBookInfoJson(title, author, printType);
            return fromJsonResponse(json);
        }
        else return new ArrayList<BookInfo>();
    }

    private static List<BookInfo> fromJsonResponse(String s){
        ArrayList<BookInfo> res = new ArrayList<BookInfo>();

        try{
            JSONObject json = new JSONObject(s);

           if(json.getInt("totalItems") == 0)
               return new ArrayList<BookInfo>();

            JSONArray jBooks = json.getJSONArray("items");

            for (int i = 0; i < jBooks.length(); i++){

                JSONObject jBook = jBooks.getJSONObject(i);
                JSONObject info = jBook.getJSONObject("volumeInfo");

                URL url = new URL(jBook.getString("selfLink"));
                String title = info.getString("title");
                String authors = null;
                ArrayList<String> authorList = new ArrayList<>();

                if (info.has("authors")){

                    JSONArray jAuthors = info.getJSONArray("authors");
                    //StringBuilder sb = new StringBuilder();

                    for (int j = 0; j < jAuthors.length(); j++){
                        //sb.append(jAuthors.getString(j));
                        authorList.add(jAuthors.get(j).toString());
                    }


                    //authors = sb.toString();
                }

                res.add(new BookInfo(title, authorList, url));

            }

            Log.i("MAIN", "Terminado de parsear el JSON");

        }
        catch(Exception e){
            Log.e("MAIN", "Error al intentar parsear el json de resultados");
            Log.e("MAIN", e.getMessage());
            res = null;
        }



        return res;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }


}
