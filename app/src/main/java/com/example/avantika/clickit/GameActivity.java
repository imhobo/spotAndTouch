package com.example.avantika.clickit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
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
    public static long totalTime = 15000;
    float progressMultiplier = 2f;
    CountDownTimer timer;
    long timeRemaining;
    LinearLayout maskLayout;
    LinearLayout gridLayout;
    RelativeLayout parentLayout;
    int tickTimer = 10;


    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //tv = (TextView) findViewById(R.id.tv);
        tvCount = (TextView) findViewById(R.id.countdown);
        tvScore = (TextView) findViewById(R.id.score);
        tvScore.setText(getResources().getString(R.string.score) + " " + Integer.toString(score));


        parentLayout = (RelativeLayout) findViewById(R.id.rlParent);
        maskLayout = (LinearLayout) findViewById(R.id.llmask);


        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setProgress(0);
        pb.setScaleY(pb.getScaleY() * progressMultiplier);

        gridLayout = (LinearLayout) findViewById(R.id.llgrid);
//        gridLayout.setBackgroundColor(Color.parseColor(backColor));
        gridLayout.setWeightSum(orderGrid);

        LinearLayout[] horLayout = new LinearLayout[orderGrid];
        verLayout = new LinearLayout[orderGrid][orderGrid];

        for (int i = 0; i < orderGrid; i++) {

            horLayout[i] = new LinearLayout(this);
            horLayout[i].setOrientation(LinearLayout.HORIZONTAL);
            horLayout[i].setWeightSum(orderGrid);
            horLayout[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            gridLayout.addView(horLayout[i]);

            for (int j = 0; j < orderGrid; j++) {
                verLayout[i][j] = new LinearLayout(this);
                verLayout[i][j].setId(i * orderGrid + j);
                verLayout[i][j].setOrientation(LinearLayout.VERTICAL);
                verLayout[i][j].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                horLayout[i].addView(verLayout[i][j]);
                verLayout[i][j].setBackgroundResource(R.drawable.custom_border_unselected);

                verLayout[i][j].setOnClickListener(this);
            }
        }

        disableGrid();
        findViewById(R.id.tvTap).setVisibility(View.VISIBLE);
        maskLayout.setOnClickListener(this);
    }

    // @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.llmask)
        {
            maskLayout.setOnClickListener(null);

            timeRemaining = totalTime;
            tvCount.setText("Seconds remaining: " + prettyFloat(timeRemaining));

            Random rand = new Random();
            cur = rand.nextInt(orderGrid * orderGrid);
            prev = cur;

            verLayout[cur/orderGrid][cur%orderGrid].setBackgroundColor(Color.parseColor(selectedColor));
            verLayout[cur/orderGrid][cur%orderGrid].setBackgroundResource(R.drawable.custom_border_selected);


            startTimer();
            findViewById(R.id.tvTap).setVisibility(View.GONE);
            enableGrid();
        }

        else if(id == cur)
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

        else if(id!=cur && id>=0 && id< orderGrid*orderGrid)
        {
            score--;
            tvScore.setText(getResources().getString(R.string.score) + " " + Integer.toString(score));
        }

    }

    void disableGrid(){
        maskLayout.setVisibility(View.VISIBLE);
    }

    void enableGrid(){
        maskLayout.setVisibility(View.GONE);
    }

    void resumeGame(){

        enableGrid();
        startTimer();

    }

    void pauseGame(){

        if(timer==null)return;
        timer.cancel();
        disableGrid();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Paused");
        builder.setMessage("Would you like to continue the game?");

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
                if(timer!=null)
                    timer.cancel();
                finish();
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void tapToStart(){
        timer.cancel();
        disableGrid();
        parentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                switch(action) {
                    case MotionEvent.ACTION_DOWN:
                        resumeGame();
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    String prettyFloat(long number)
    {

        Float floatValue = (float)number/1000;
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        floatValue = new Float(formatter.format(floatValue));
        return String.format ("%.2f", floatValue);

    }

    void startTimer()
    {
        timer = new CountDownTimer(timeRemaining, tickTimer) {

            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                tvCount.setText("Seconds remaining: " + prettyFloat(timeRemaining));
                float percentageProgressBar = (((float) totalTime - (float) timeRemaining)/totalTime) * 100;
                pb.setProgress((int) percentageProgressBar);
            }

            public void onFinish() {
                timeRemaining = 0;
                tvCount.setText("Seconds remaining: " + prettyFloat(timeRemaining));
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


    @Override
    public void onBackPressed() {
        pauseGame();
    }


}