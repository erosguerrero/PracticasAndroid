package es.ucm.fdi.googlebooksclient;

/**
 *
 *
 * Clase que realiza la llamada http a la api
 *
 */

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class NetworkUtils {

    private final static String URL = "https://www.googleapis.com/books/v1/volumes?";
    private final static String API_KEY = "AIzaSyDEcNpRNMpVYiZ2Yb2vceISesLDlYBc1ig";
    //appendqueryparameter key

    private final static String QUERY_PARAM = "q";
    private final static String MAX = "maxResults";
    private final static String PRINT_TYPE = "printType";
    private final static String KEY = "key";

    private final static String TITLE = "intitle:";
    private final static String AUTHOR = "inauthor:";

    public static String getBookInfoJson(String title, String author, String printType){

        HttpURLConnection urlConnection = null;
        String bookJSONString = null;
        BufferedReader reader = null;


        Uri.Builder uriBuilder = Uri.parse(URL).buildUpon();

        if (title != null && author == null){
            uriBuilder.appendQueryParameter(QUERY_PARAM, TITLE + title);
        }
        else if (title == null && author != null){
            uriBuilder.appendQueryParameter(QUERY_PARAM, AUTHOR + author);
        }
        else if (title != null && author != null){
            uriBuilder.appendQueryParameter(QUERY_PARAM, TITLE + title + " " + AUTHOR + author);
        }

        Uri uri = uriBuilder
                .appendQueryParameter(MAX, "10")
                .appendQueryParameter(PRINT_TYPE, printType)
                .appendQueryParameter(KEY, API_KEY)
                .build();

        try{

            URL requestUrl = new URL(uri.toString());

            Log.d("MyUrl", requestUrl.toString());
            urlConnection = (HttpURLConnection) requestUrl.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);


            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null){
                builder.append(line);
                builder.append("\n");
            }

            if (builder.length() == 0)
                return null;

            bookJSONString = builder.toString();

            Log.i("ResultadoJSON", bookJSONString);

        }
        catch (UnknownHostException e){
            Log.e("Error", "ERROR HOST");
            e.printStackTrace();
        }
        catch (Exception e){

            e.printStackTrace();
        }
        finally {
            try {
                if (urlConnection != null) urlConnection.disconnect();
                if (reader != null) reader.close();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return bookJSONString;
    }





}
