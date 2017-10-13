package com.example.avantika.clickit;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        gridView.setAdapter(  new GridAdapter( this, GRID_DATA, height ) );


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {


                Toast.makeText(MainActivity.this, Integer.toString(position), Toast.LENGTH_SHORT).show();

            }
        });

    }

}