package com.example.pleasure.gridimagesearch.activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.example.pleasure.gridimagesearch.adapters.ImageResultsAdapter;
import com.example.pleasure.gridimagesearch.models.EndlessScrollListener;
import com.example.pleasure.gridimagesearch.models.ImageResult;
import com.example.pleasure.gridimagesearch.R;
import com.example.pleasure.gridimagesearch.models.SearchOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {
    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private SearchOptions imageSearchSettings;
    private static final int SET_OPTIONS_REQUEST = 1;
    private static String query="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupViews();

        // Create the data source
        imageResults = new ArrayList<ImageResult>();

        // Attach the data source to an adapter, getBaseContext() not as good??
        aImageResults = new ImageResultsAdapter(this, imageResults);

        // Set default options
        imageSearchSettings = new SearchOptions();
        imageSearchSettings.size = SearchOptions.Size.any;
        imageSearchSettings.type = SearchOptions.Type.any;
        imageSearchSettings.color = SearchOptions.Color.any;
        imageSearchSettings.site = "";

        // Link the adapter to the adapterview (gridView)
        gvResults.setAdapter(aImageResults);

        // Attach the listener to the AdapterView onCreate
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
    }

    // Append more data into the adapter
        public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter


            getNextImages(offset);

    }

    private void getNextImages(int offset) {

        String query = etQuery.getText().toString();
        //Toast.makeText(this, "Search for: " + query, Toast.LENGTH_SHORT).show();

        AsyncHttpClient client = new AsyncHttpClient();
        // https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android&rsz=8
        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&start=" + offset*8 + "&rsz=8&q=" + query;
        searchUrl += imageSearchSettings.getQueryParams();
        Log.d("DEBUG", searchUrl);
        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Log.d("DEBUG", response.toString());
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    //imageResults.clear();
                    // modify the underlying data as well
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void setupViews() {
        // Get the handles to the views
        etQuery = (EditText)findViewById(R.id.etQuery);
        gvResults = (GridView)findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the image display activity
                // Create an intent - between activities
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                // Get the image result to display
                ImageResult result = imageResults.get(position);
                // Pass image result into the intent
                i.putExtra("result", result); // serializable or parcelable


                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // button:onClick
    public void onImageSearch(View v) {

        imageResults.clear();

        getNextImages(0);
        customLoadMoreDataFromApi(1);

    }


    public void onInvokingSettings(MenuItem item){

        // construct intent
        Intent i = new Intent(this, OptionsActivity.class);
        // Pass arguments
        i.putExtra("imageSearchSettings", imageSearchSettings);
        //Execute Intent StartACtivityForResult
        startActivityForResult(i, SET_OPTIONS_REQUEST);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == SET_OPTIONS_REQUEST) {
            if (resultCode == RESULT_OK) {
                // Here we will just display it to the user.
                this.imageSearchSettings = (SearchOptions)data.getSerializableExtra("imageSearchSettings");
                //imageResults.clear();


                etQuery.setText(query);
                //getNextImages(0);

                etQuery.setSelection(etQuery.getText().length());
                customLoadMoreDataFromApi(0);

                //startActivity(new Intent(Intent.ACTION_VIEW, data));
            }
        }
    }



}


/*
 * Use ViewHolder to save time on find Views or inflation
 * SwipeToRefresh, add the container layout
 * Action Bar Progress Bar
 */