package com.example.avantika.clickit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    float progressMultiplier = 2f;
    CountDownTimer timer;
    long timeRemaining;


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
//

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setProgress(0);
        pb.setScaleY(pb.getScaleY()*progressMultiplier);

        LinearLayout parentLayout = (LinearLayout)findViewById(R.id.llgrid);
//        parentLayout.setBackgroundColor(Color.parseColor(backColor));
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

            for(int j = 0; j < orderGrid; j++) {
                verLayout[i][j] = new LinearLayout(this);
                verLayout[i][j].setId(i*orderGrid + j);
                verLayout[i][j].setOrientation(LinearLayout.VERTICAL);
                verLayout[i][j].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                horLayout[i].addView(verLayout[i][j]);
                verLayout[i][j].setBackgroundResource(R.drawable.custom_border_unselected);

                verLayout[i][j].setOnClickListener(this);
            }
        }
        Random rand = new Random();
        cur = rand.nextInt(orderGrid*orderGrid);
        prev = cur;
        verLayout[cur/orderGrid][cur%orderGrid].setBackgroundColor(Color.parseColor(selectedColor));
        verLayout[cur/orderGrid][cur%orderGrid].setBackgroundResource(R.drawable.custom_border_selected);

        timeRemaining = totalTime;
        tvCount.setText("Seconds remaining: " + Long.toString(timeRemaining));

        timer = new CountDownTimer(totalTime * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished / 1000;
                tvCount.setText("Seconds remaining: " + Long.toString(timeRemaining));
                float percentageProgressBar = (((float) totalTime - (float) timeRemaining)/totalTime) * 100;
                pb.setProgress((int) percentageProgressBar);
            }

            public void onFinish() {
                timeRemaining = 0;
                tvCount.setText("Seconds remaining: " + Long.toString(timeRemaining));
                float percentageProgressBar = (((float) totalTime - (float) timeRemaining)/totalTime) * 100;
                pb.setProgress((int) percentageProgressBar);
                Intent myIntent = new Intent(GameActivity.this, EndGame.class);
                myIntent.putExtra("score", Integer.toString(score)); //Optional parameters
                GameActivity.this.startActivity(myIntent);
                finish();
            }

        };

        timer.start();
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
            verLayout[cur/orderGrid][cur%orderGrid].setBackgroundResource(R.drawable.custom_border_selected);
//            verLayout[cur / orderGrid][cur % orderGrid].setBackground(getResources().getDrawable(R.drawable.sample2));
            verLayout[prev / orderGrid][prev % orderGrid].setBackgroundColor(Color.parseColor(backColor));
            verLayout[prev/orderGrid][prev%orderGrid].setBackgroundResource(R.drawable.custom_border_unselected);
//            verLayout[prev / orderGrid][prev % orderGrid].setBackgroundResource(0);
            prev=cur;
        }

    }

    void disableGrid(){
        for (int j = 0; j< orderGrid; j++){
            for (int k = 0; k < orderGrid; k++){
//                verLayout[j][k].setClickable(false);
                verLayout[j][k].setEnabled(false);
            }
        }
    }

    void enaableGrid(){
        for (int j = 0; j< orderGrid; j++){
            for (int k = 0; k < orderGrid; k++){
//                verLayout[j][k].setClickable(false);
                verLayout[j][k].setEnabled(true);
            }
        }
    }

    void resumeGame(){
        enaableGrid();

        timer = new CountDownTimer(timeRemaining * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished / 1000;
                tvCount.setText("Seconds remaining: " + Long.toString(timeRemaining));
                float percentageProgressBar = (((float) totalTime - (float) timeRemaining)/totalTime) * 100;
                pb.setProgress((int) percentageProgressBar);
            }

            public void onFinish() {
                timeRemaining = 0;
                tvCount.setText("Seconds remaining: " + Long.toString(timeRemaining));
                float percentageProgressBar = (((float) totalTime - (float) timeRemaining)/totalTime) * 100;
                pb.setProgress((int) percentageProgressBar);
                Intent myIntent = new Intent(GameActivity.this, EndGame.class);
                myIntent.putExtra("score", Integer.toString(score)); //Optional parameters
                GameActivity.this.startActivity(myIntent);
                finish();
            }

        };
        timer.start();
    }

    void pauseGame(){
        timer.cancel();
        disableGrid();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Paused");
        builder.setMessage("Would you like to continue game?");

        // add the buttons
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resumeGame();
            }
        });
        builder.setNegativeButton("Go Back", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent myIntent = new Intent(GameActivity.this, OptionsActivity.class);
                GameActivity.this.startActivity(myIntent);
                finish();
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        pauseGame();
    }


}