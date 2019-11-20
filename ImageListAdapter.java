package com.Wallpaperscreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.secure_messaging.R;

import java.util.ArrayList;

/**
 * Created by Kshitij on 10/21/2018.
 */

public class ImageListAdapter extends ArrayAdapter {



    int resource;
    ArrayList<Integer> arrayList;
    Context context;

    public ImageListAdapter(Context context, int resource, ArrayList<Integer> arrayList) {
        super(context, resource, arrayList);
        this.resource = resource;
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, null, false);


        ImageView imageView = view.findViewById(R.id.imageview);
        imageView.setImageResource(arrayList.get(position));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          //      Toast.makeText(context, ""+(position+1), Toast.LENGTH_SHORT).show();

            }
        });



        return view;
    }

}
