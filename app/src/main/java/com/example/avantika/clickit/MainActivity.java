package com.example.avantika.clickit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {

    GridView gridView;
    public int score = 0;
    TextView tv;

    // This Data show in grid ( Used by adapter )

    static final String[ ] GRID_DATA = new String[] {
            "Windows" ,
            "iOS",
            "android",
            "Windows"
    };


    void changeGrid(final int height, final int n, final TextView tv){

        gridView.setAdapter(  new GridAdapter( this, GRID_DATA, height, n ) );

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {


                if (position == n) {
                    score++;
                    tv.setText(Integer.toString(score));
                    Log.d("DEBUG","correct");
                    Random rand = new Random();
                    int next = rand.nextInt(4);
                    while(next == n) {
                        next = rand.nextInt(4);
                    }
                    changeGrid(height, next, tv);
                }
                else {
                    Log.d("DEBUG", "incorrect");
                }
            }
        });
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main );

        // Get gridview object from xml file

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setNumColumns(2);

        tv = (TextView) findViewById(R.id.tv);

        // Set custom adapter (GridAdapter) to gridview

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        final int height = size.y;
        Random rand = new Random();
        int n = rand.nextInt(4);
        changeGrid(height, n, tv);


    }

}