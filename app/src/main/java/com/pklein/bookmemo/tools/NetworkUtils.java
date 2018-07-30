package com.pklein.bookmemo.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.pklein.bookmemo.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String BEFORE_TITLE_URL = "?action=query&titles=";
    private static final String AFTER_TITLE_URL = "&prop=extracts&exintro=true&format=json";


    /**
     * Builds the URL used to talk to the API of wikipedia
     * this URL will return all the informations used to display book informations
     *
     * @param title The title of the book we are looking for
     * @return The URL to use to query the themoviedb server.
     */
    public static URL buildListUrl(String title, Context context) {
        Uri builtUri = Uri.parse(context.getString(R.string.Wiki_URL)+BEFORE_TITLE_URL+title+AFTER_TITLE_URL).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * This method allows to know whether the phone is connected to internet or not
     *
     * @param cm keep informations of phone connections
     * @return returns true if the phone is connected to internet, false otherwhise
     */
    public static boolean isconnected(ConnectivityManager cm){

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean phoneConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return phoneConnected;
    }

}
