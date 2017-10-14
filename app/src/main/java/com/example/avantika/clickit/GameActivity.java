package com.example.avantika.clickit;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {


    int score = 0;
    TextView tvCount, tvScore;
    public static int orderGrid = 2;
    LinearLayout[][] verLayout;
    int cur;
    int prev;
    String selectedColor = "#FF0000";
    String backColor = "#FFFFFF";
    ProgressBar pb;
    public static int totalTime = 15;


    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_game);

        //tv = (TextView) findViewById(R.id.tv);
        tvCount = (TextView) findViewById(R.id.countdown);
        tvScore = (TextView) findViewById(R.id.score);
        tvScore.setText(getResources().getString(R.string.score) + " " + Integer.toString(score));


//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        final int height = size.y;

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setProgress(0);

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
        new CountDownTimer(totalTime * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                long timeRemaining = millisUntilFinished / 1000;
                tvCount.setText("Seconds remaining: " + Long.toString(timeRemaining));
                float percentageProgressBar = (((float) totalTime - (float) timeRemaining)/totalTime) * 100;
                pb.setProgress((int) percentageProgressBar);
            }

            public void onFinish() {
                long timeRemaining = 0;
                tvCount.setText("Seconds remaining: " + Long.toString(timeRemaining));
                float percentageProgressBar = (((float) totalTime - (float) timeRemaining)/totalTime) * 100;
                pb.setProgress((int) percentageProgressBar);
                Intent myIntent = new Intent(GameActivity.this, EndGame.class);
                myIntent.putExtra("score", Integer.toString(score)); //Optional parameters
                GameActivity.this.startActivity(myIntent);
                finish();
            }

        }.start();
    }

   // @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == cur)
        {
            score++;
            tvScore.setText(getResources().getString(R.string.score) + " " + Integer.toString(score));

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