package com.example.pleasure.gridimagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pleasure on 2/19/2015.
 */
public class ImageResult implements Serializable {
    private static final long serialVersionUID = 2015_02_22_001L;
    public String fullUrl;
    public String thumbUrl;
    public String title;



    public ImageResult(JSONObject json) {
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
        ArrayList<ImageResult>  results = new ArrayList<ImageResult>();
        for (int i =0; i < array.length(); i++) {
            try {
                results.add(new ImageResult(array.getJSONObject(i)));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
