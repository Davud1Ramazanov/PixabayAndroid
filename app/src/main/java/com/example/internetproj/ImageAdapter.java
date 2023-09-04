package com.example.internetproj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> imageUrls;

    public ImageAdapter(Context context, ArrayList<String> imageUrls) {
        super(context, 0, imageUrls);
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_main, parent, false);
        }

        String imageUrl = getItem(position);
        ImageView imageView = listItemView.findViewById(R.id.imageView);
        Picasso.get().load(imageUrl).into(imageView);

        return listItemView;
    }
}
