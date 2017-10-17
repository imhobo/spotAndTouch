package com.example.avantika.clickit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import java.util.logging.Logger;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {


    int score = 0;
    TextView tvCount, tvScore;
    int orderGrid;
    LinearLayout[][] verLayout;
    int cur;
    int prev;
    ProgressBar pb;
    int totalTime;
    CountDownTimer timer;
    int timeRemaining;
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


        // if not in shared pref orderGrid = Constants.defaultOrderGrid;
        parentLayout = (RelativeLayout) findViewById(R.id.rlParent);
        maskLayout = (LinearLayout) findViewById(R.id.llmask);

        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        orderGrid = prefs.getInt(Constants.ORDER_GRID_KEY, Constants.DEFAULT_ORDER_GRID);
        totalTime = prefs.getInt(Constants.TOTAL_TIME_KEY, Constants.DEFAULT_TOTAL_TIME);


        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setProgress(Constants.INITIAL_PROGRESS);
        pb.setScaleY(pb.getScaleY() * Constants.PROGRESS_MULTIPLIER);

        gridLayout = (LinearLayout) findViewById(R.id.llgrid);
        gridLayout.setWeightSum(orderGrid);

        LinearLayout[] horLayout = new LinearLayout[orderGrid];
        verLayout = new LinearLayout[orderGrid][orderGrid];

        for (int i = 0; i < orderGrid; i++) {

            horLayout[i] = new LinearLayout(this);
            horLayout[i].setOrientation(LinearLayout.HORIZONTAL);
            horLayout[i].setWeightSum(orderGrid);
            horLayout[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    0, 1.0f));
            gridLayout.addView(horLayout[i]);

            for (int j = 0; j < orderGrid; j++) {
                verLayout[i][j] = new LinearLayout(this);
                verLayout[i][j].setId(i * orderGrid + j);
                verLayout[i][j].setOrientation(LinearLayout.VERTICAL);
                verLayout[i][j].setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                horLayout[i].addView(verLayout[i][j]);
//                verLayout[i][j].setBackgroundResource(R.drawable.custom_border_unselected);
                verLayout[i][j].setBackgroundResource(R.drawable.open_window);

                verLayout[i][j].setOnClickListener(this);
            }
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        Log.d("SCREENW",Integer.toString(width));
        Log.d("SCREENH",Integer.toString(height));

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


            Log.d("TOPLAYOUTW",Integer.toString(findViewById(R.id.lltop).getWidth()));
            Log.d("TOPLAYOUTH",Integer.toString(findViewById(R.id.lltop).getHeight()));

            Log.d("GRIDLAYOUTW",Integer.toString(gridLayout.getWidth()));
            Log.d("GRIDLAYOUTH",Integer.toString(gridLayout.getHeight()));

            
            maskLayout.setOnClickListener(null);

            // is not in shared pref: totalTime = Constants.defaultTotalTime;
            timeRemaining = totalTime;
            tvCount.setText("Time: " + prettyFloat(timeRemaining));

            Random rand = new Random();
            cur = rand.nextInt(orderGrid * orderGrid);
            prev = cur;


//            verLayout[cur/orderGrid][cur%orderGrid].setBackgroundColor(Color.parseColor(selectedColor));
            verLayout[cur/orderGrid][cur%orderGrid].setBackgroundResource(R.drawable.close_window);

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
//            verLayout[cur/orderGrid][cur%orderGrid].setBackgroundColor(Color.parseColor(selectedColor));
            verLayout[cur/orderGrid][cur%orderGrid].setBackgroundResource(R.drawable.close_window);


//            verLayout[prev / orderGrid][prev % orderGrid].setBackgroundColor(Color.parseColor(backColor));
            verLayout[prev/orderGrid][prev%orderGrid].setBackgroundResource(R.drawable.open_window);

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
        dialog.setCancelable(false);
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

        Float floatValue = (float)number/Constants.MILLISECONDS_IN_SECONDS;
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        formatter.setMaximumFractionDigits(Constants.DECIMAL_DIGITS);
        formatter.setMinimumFractionDigits(Constants.DECIMAL_DIGITS);
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        floatValue = new Float(formatter.format(floatValue));
        return String.format ("%.2f", floatValue);

    }

    void startTimer()
    {
        timer = new CountDownTimer(timeRemaining, Constants.TICK_TIMER) {

            public void onTick(long millisUntilFinished) {
                timeRemaining = (int)millisUntilFinished;
                tvCount.setText("Time: " + prettyFloat(timeRemaining));
                float percentageProgressBar = (((float) totalTime - (float) timeRemaining)/totalTime) * 100;
                pb.setProgress((int) percentageProgressBar);
            }

            public void onFinish() {
                timeRemaining = 0;
                tvCount.setText("Time: " + prettyFloat(timeRemaining));
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


    Drawable getScaledDrawable(int drawable, int width, int height) {
        Drawable dr = ContextCompat.getDrawable(this, drawable);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, width, height, true));
        return d;
    }

    void setBackgroundDrawable(LinearLayout layout, Drawable drawable)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            layout.setBackground(drawable);
        }
        else
        {
            layout.setBackgroundDrawable(drawable);
        }
    }

    @Override
    public void onBackPressed() {
        pauseGame();
    }


}