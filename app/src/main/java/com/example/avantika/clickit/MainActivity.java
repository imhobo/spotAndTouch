package com.example.avantika.clickit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    int score = 0;
    TextView tv;
    int orderGrid = 2;
    LinearLayout[][] verLayout;
    int cur;
    int prev;
    String selectedColor = "#FF0000";
    String backColor = "#FFFFFF";

    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main );

        tv = (TextView) findViewById(R.id.tv);
        tv.setText(getResources().getString(R.string.score) + " " + Integer.toString(score));


//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        final int height = size.y;
//


        LinearLayout parentLayout = (LinearLayout)findViewById(R.id.llgrid);
        parentLayout.setBackgroundColor(Color.parseColor(backColor));
        parentLayout.setWeightSum(orderGrid);

        LinearLayout[] horLayout = new LinearLayout[orderGrid];
        verLayout = new LinearLayout[orderGrid][orderGrid];

        for(int i = 0; i < orderGrid; i++) {

            horLayout[i] = new LinearLayout(this);
            horLayout[i].setOrientation(LinearLayout.HORIZONTAL);
            horLayout[i].setWeightSum(orderGrid);
            horLayout[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            parentLayout.addView(horLayout[i]);

            for(int j = 0; j < orderGrid; j++)
            {
                verLayout[i][j] = new LinearLayout(this);
                verLayout[i][j].setId(i*orderGrid + j);
                verLayout[i][j].setOrientation(LinearLayout.VERTICAL);
                verLayout[i][j].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                horLayout[i].addView(verLayout[i][j]);
                verLayout[i][j].setOnClickListener(this);
            }
        }

        Random rand = new Random();
        cur = rand.nextInt(orderGrid*orderGrid);
        prev = cur;
        verLayout[cur/orderGrid][cur%orderGrid].setBackgroundColor(Color.parseColor(selectedColor));
        //Change this line
//        verLayout[cur / orderGrid][cur % orderGrid].setBackground(getResources().getDrawable(R.drawable.sample2));

    }

   // @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == cur)
        {
            score++;
            tv.setText(getResources().getString(R.string.score) + " " + Integer.toString(score));

            Random rand = new Random();
            while(prev==cur) {
                cur = rand.nextInt(orderGrid * orderGrid);
            }
            verLayout[cur/orderGrid][cur%orderGrid].setBackgroundColor(Color.parseColor(selectedColor));
//            verLayout[cur / orderGrid][cur % orderGrid].setBackground(getResources().getDrawable(R.drawable.sample2));
            verLayout[prev / orderGrid][prev % orderGrid].setBackgroundColor(Color.parseColor(backColor));
//            verLayout[prev / orderGrid][prev % orderGrid].setBackgroundResource(0);
            prev=cur;
        }

    }

}