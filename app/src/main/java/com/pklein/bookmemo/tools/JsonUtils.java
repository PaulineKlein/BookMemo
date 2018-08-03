package com.pklein.bookmemo.tools;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class JsonUtils {

    private static final String TAG= JsonUtils.class.getSimpleName();

    private static final String JSON_QUERY = "query";
    private static final String JSON_PAGES = "pages";
    private static final String JSON_EXTRACT = "extract";

    /**
     * This method returns a list of Movie described inside a JSON file
     *
     * @param json  The Json with the list of the movies to parse
     * @return List<Movie> : a list of Movie objects
     */
    public static String parseWikiJson(String json) throws JSONException {

        Log.i(TAG, "Start parseWikiJson");

        JSONObject WikiJson = new JSONObject(json);
        String result = "";

        if (WikiJson.has(JSON_QUERY)) {

            JSONObject JSON_query =  WikiJson.getJSONObject(JSON_QUERY);

            if (JSON_query.has(JSON_PAGES)) {
                JSONObject JSON_pages =  JSON_query.getJSONObject(JSON_PAGES);

                // Help of https://stackoverflow.com/questions/33531041/jsonobject-get-value-of-first-node-regardless-of-name?rq=1
                Iterator<String> keys = JSON_pages.keys();
                String str_Name=keys.next();

                if(JSON_pages.has(str_Name)){

                    JSONObject JSON_page =  JSON_pages.getJSONObject(str_Name);
                    if(JSON_page.has(JSON_EXTRACT)){
                        Log.e(TAG,JSON_page.toString());
                        result =  JSON_page.optString(JSON_EXTRACT);
                    }
                }
            }
        }

        Log.i(TAG, result);
        Log.i(TAG, "End parseWikiJson");
        return result;
    }

}
