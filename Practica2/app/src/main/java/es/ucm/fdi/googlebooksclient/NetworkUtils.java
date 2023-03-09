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

    public static String getBookInfoJson(String queryString, String printType){

        HttpURLConnection urlConnection = null;
        String bookJSONString = null;
        InputStream inputStream = null;

        Uri uri = Uri.parse(URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(MAX, "5")
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

            inputStream = urlConnection.getInputStream();



            return convertInputToString(inputStream, 500);


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
                if (inputStream != null) inputStream.close();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }

    public static String convertInputToString(InputStream stream, int len)
        throws IOException, UnsupportedEncodingException {
        Log.i("Pepinillo", "dentro de convert");
        Reader reader = null;
        reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);

    }



}
