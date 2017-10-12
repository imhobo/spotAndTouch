package com.example.avantika.clickit;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    GridView gridView;

    // This Data show in grid ( Used by adapter )

    static final String[ ] GRID_DATA = new String[] {
            "Windows" ,
            "iOS",
            "android",
            "Windows"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main );

        // Get gridview object from xml file

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setNumColumns(2);


        // Set custom adapter (GridAdapter) to gridview

        gridView.setAdapter(  new GridAdapter( this, GRID_DATA ) );

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        LinearLayout ll = (LinearLayout) findViewById(R.id.linera1);
        ImageView imageView = ll.findViewById(R.id.grid_item_image);
        imageView.getLayoutParams().height = height/2;
        imageView.requestLayout();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {


                Toast.makeText(MainActivity.this,"Hello", Toast.LENGTH_SHORT).show();

            }
        });

    }

}