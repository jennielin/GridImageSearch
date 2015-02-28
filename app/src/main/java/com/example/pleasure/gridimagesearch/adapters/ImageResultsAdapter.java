package com.example.pleasure.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pleasure.gridimagesearch.R;
import com.example.pleasure.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Pleasure on 2/21/2015.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    // Has to have two methods
    /// This one is the constructor? Just need to pass data to the adapter
    public ImageResultsAdapter(Context context, List<ImageResult> images){
        super(context, android.R.layout.simple_list_item_1, images);
    }

    // Give the gridview the view of the image/title
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the item xml, inflate it, return to the gridview it is calling now
        // Ask for a view at this position, return it
        // return super.getView(position, convertView, parent);

        ImageResult imageInfo = getItem(position);
        // Ctrl-Shift-O to fix imports?
        // check if an existing view is reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        // clear out image from last time
        ivImage.setImageResource(0);
        //Populate title and remote download image url
        tvTitle.setText(Html.fromHtml(imageInfo.title));
        // Remotely download the image data in the background using Picasso
        Picasso.with(getContext()).load(imageInfo.thumbUrl).into(ivImage);
        //
        return convertView;

    }
}
