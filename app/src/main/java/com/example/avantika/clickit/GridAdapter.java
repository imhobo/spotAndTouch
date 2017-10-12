package com.example.avantika.clickit;


import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class GridAdapter extends BaseAdapter {

    private Context context;
    private final String[] gridValues;

    //Constructor to initialize values
    public GridAdapter(Context context, String[ ] gridValues) {

        this.context        = context;
        this.gridValues     = gridValues;
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

            // get layout from grid_item.xml ( Defined Below )

            gridView = inflater.inflate( R.layout.grid_item , null);



            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            String arrLabel = gridValues[ position ];

            if (arrLabel.equals("Windows")) {

                imageView.setImageResource(R.drawable.sample2);

            } else if (arrLabel.equals("iOS")) {

                imageView.setImageResource(R.drawable.sample2);

            }  else if (arrLabel.equals("android")) {

                imageView.setImageResource(R.drawable.sample2);

            }
        } else {

            gridView = (View) convertView;
        }

        return gridView;
    }
}