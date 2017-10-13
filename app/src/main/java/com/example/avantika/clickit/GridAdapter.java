package com.example.avantika.clickit;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.avantika.clickit.R.drawable.red;


public class GridAdapter extends BaseAdapter {

    private Context context;
    private final String[] gridValues;
    private int heightOfScreen;
    private int randNum;

    //Constructor to initialize values
    public GridAdapter(Context context, String[ ] gridValues, int height, int n) {

        this.context        = context;
        this.gridValues     = gridValues;
        this.heightOfScreen = height;
        this.randNum = n;
    }

    @Override
    public int getCount() {

        // Number of times getView method call depends upon gridValues.length
        return gridValues.length;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }


    // Number of times getView method call depends upon gridValues.length

    public View getView(int position, View convertView, ViewGroup parent) {

        // LayoutInflator to call external grid_item.xml file

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            gridView = inflater.inflate( R.layout.grid_item , null);



            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, heightOfScreen/2));
            imageView.setLayoutParams(lp);



            if (position == randNum){
                imageView.setImageResource(red);
            } else {
                imageView.setImageResource(R.drawable.white);
            }

        } else {

            gridView = (View) convertView;
        }

        return gridView;
    }
}