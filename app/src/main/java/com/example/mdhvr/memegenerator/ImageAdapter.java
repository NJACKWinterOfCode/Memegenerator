package com.example.mdhvr.memegenerator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ishita sharma on 12/21/2017.
 */

public class ImageAdapter extends ArrayAdapter<String> {

    private Context context;

    public ImageAdapter(Context context, List<String> stringList){
        super(context,0,stringList);
        this.context=context;
    }


    @Override
    public View getView(int i, View view, ViewGroup parent) {

        if(view==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.image_layout,parent,false);

        }

        String image_uri= getItem(i);
        ImageView imageView= view.findViewById(R.id.searched_image_item);

        Picasso.with(context).load(image_uri).into(imageView);

        return view;
    }
}
