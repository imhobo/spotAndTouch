package com.project.game.touchit;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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

    private static final String TAG = GameActivity.class.getName();

    private int score;
    private int orderGrid;
    private int cur;
    private int prev;
    private int totalTime;
    private int timeRemaining;
    private int tickTimer;

    private RelativeLayout parentLayout;
    private LinearLayout gridLayout;
    private LinearLayout maskLayout;
    private LinearLayout[] horLayout;
    private LinearLayout[][] verLayout;
    private TextView tvCount, tvScore;
    private TextView tvTapToStart;
    private ProgressBar pb;
    private CountDownTimer timer;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //load saved values and default values here
        loadValues();

        //initialize your views here
        initViews();

        //set values for your views here
        setViewProperties();

        //Ask user to click on the screen to start the game
        disableScreen();

    }


    public void loadValues()
    {
        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);

        // if not in shared pref orderGrid = Constants.defaultOrderGrid;
        orderGrid = prefs.getInt(Constants.PREFS_KEY_ORDERGRID, Constants.DEFAULT_ORDER_GRID);
        totalTime = prefs.getInt(Constants.PREFS_KEY_TIME, Constants.DEFAULT_TOTAL_TIME);
        timeRemaining = totalTime;

        score = Constants.SCORE;
        tickTimer = Constants.TICK_TIMER;

        //Assign any initial random value to cur and prev
        Random rand = new Random();
        cur = rand.nextInt(orderGrid*orderGrid);
        prev=cur;

        if(BuildConfig.DEBUG)
        {
            Log.i(TAG, "Values loaded");
        }

    }

    public void initViews()
    {

        parentLayout = (RelativeLayout) findViewById(R.id.rlParent);

        gridLayout = (LinearLayout) findViewById(R.id.llgrid);
        maskLayout = (LinearLayout) findViewById(R.id.llmask);

        pb = (ProgressBar) findViewById(R.id.progressBar);

        tvCount = (TextView) findViewById(R.id.countdown);
        tvScore = (TextView) findViewById(R.id.score);
        tvTapToStart = (TextView) findViewById(R.id.tvTap);

        horLayout = new LinearLayout[orderGrid];
        verLayout = new LinearLayout[orderGrid][orderGrid];

        if(BuildConfig.DEBUG)
        {
            Log.i(TAG, "Views initialized");
        }

    }


    public void setViewProperties()
    {
        //set score to default value
        tvScore.setText(getResources().getString(R.string.score) + " " + Integer.toString(score));
        //set time to total time
        tvCount.setText(getResources().getString(R.string.time) + " " +  prettyFloat(timeRemaining));

        pb.setProgress(Constants.PROGRESS_INITIAL_VALUE);
        pb.setScaleY(pb.getScaleY() * Constants.PROGRESS_MULTIPLIER);
        pb.getProgressDrawable().setColorFilter(ResourcesCompat.getColor(getResources(), R.color.colorTopLayout, null), android.graphics.PorterDuff.Mode.SRC_IN);

        gridLayout.setWeightSum(orderGrid);

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
                verLayout[i][j].setBackgroundResource(Constants.NOT_SELECTED_IMAGE);

                verLayout[i][j].setOnClickListener(this);
            }
        }

        maskLayout.setOnClickListener(this);

        if(BuildConfig.DEBUG)
        {
            Log.i(TAG, "Views set with values");

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            Log.d(TAG,"Screen width: " + Integer.toString(width));
            Log.d(TAG,"Screen height: " + Integer.toString(height));

        }
    }

    public void disableScreen()
    {
        tvTapToStart.setVisibility(View.VISIBLE);
        disableGridLayout();
    }

    public void enableScreen()
    {
        tvTapToStart.setVisibility(View.GONE);
        enableGridLayout();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        //Handling the first tap
        if(id == R.id.llmask)
        {

            if(BuildConfig.DEBUG)
            {
                Log.d(TAG, "Top layout width: " + Integer.toString(findViewById(R.id.lltop).getWidth()));
                Log.d(TAG, "Top layout height: " + Integer.toString(findViewById(R.id.lltop).getHeight()));

                Log.d(TAG, "Grid layout width: " + Integer.toString(gridLayout.getWidth()));
                Log.d(TAG, "Grid layout height: " + Integer.toString(gridLayout.getHeight()));
            }

            //Disabling future clicks
            maskLayout.setOnClickListener(null);


            updateNextLocation();
            showNextLocation();
            prev=cur;

            //Allow user to click on the screen
            enableScreen();
            startTimer();

        }

        //Correctly clicked on the required image
        else if(id == cur)
        {
            updateScore(++score);
            updateNextLocation();
            showNextLocation();
            prev=cur;
        }

        //Incorrect click
        else if(id!=cur && id>=0 && id< orderGrid*orderGrid)
        {
            updateScore(--score);
        }

    }

    void updateScore(int updated_score)
    {
        score = updated_score;
        tvScore.setText(getResources().getString(R.string.score) + " " + Integer.toString(score));
    }

    void updateNextLocation()
    {
        Random rand = new Random();
        while(prev==cur) {
            cur = rand.nextInt(orderGrid * orderGrid);
        }
    }

    void showNextLocation()
    {
//        verLayout[cur/orderGrid][cur%orderGrid].setBackgroundColor(Color.parseColor(selectedColor));
        verLayout[cur/orderGrid][cur%orderGrid].setBackgroundResource(Constants.SELECTED_IMAGE);

//        verLayout[prev / orderGrid][prev % orderGrid].setBackgroundColor(Color.parseColor(backColor));
        verLayout[prev/orderGrid][prev%orderGrid].setBackgroundResource(Constants.NOT_SELECTED_IMAGE);

    }
    void disableGridLayout(){
        maskLayout.setVisibility(View.VISIBLE);
    }

    void enableGridLayout(){
        maskLayout.setVisibility(View.GONE);
    }

    void resumeGame(){

        enableGridLayout();
        startTimer();

    }

    void pauseGame(){

        if(timer==null)return;
        timer.cancel();
        disableGridLayout();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.pause_title));
        builder.setMessage(getString(R.string.pause_message));

        // add the buttons
        builder.setPositiveButton(getString(R.string.pause_option_positive), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resumeGame();
            }
        });
        builder.setNegativeButton(getString(R.string.pause_option_negative), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent myIntent = new Intent(GameActivity.this, MainMenuActivity.class);
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


    String prettyFloat(long number)
    {

        Float floatValue = (float)number/Constants.MILLISECONDS_IN_SECONDS;
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        formatter.setMaximumFractionDigits(Constants.DECIMAL_DIGITS);
        formatter.setMinimumFractionDigits(Constants.DECIMAL_DIGITS);
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        floatValue = new Float(formatter.format(floatValue));
        return String.format ("%."+Constants.DECIMAL_DIGITS+"f", floatValue);

    }

    void startTimer()
    {
        timer = new CountDownTimer(timeRemaining, Constants.TICK_TIMER) {

            public void onTick(long millisUntilFinished) {
                timeRemaining = (int)millisUntilFinished;
                tvCount.setText(getResources().getString(R.string.time) + " " +  prettyFloat(timeRemaining));
                float percentageProgressBar = (((float) totalTime - (float) timeRemaining)/totalTime) * 100;
                pb.setProgress((int) percentageProgressBar);
            }

            public void onFinish() {
                timeRemaining = 0;
                tvCount.setText(getResources().getString(R.string.time) + " " +  prettyFloat(timeRemaining));
                float percentageProgressBar = (((float) totalTime - (float) timeRemaining)/totalTime) * 100;
                pb.setProgress((int) percentageProgressBar);
                Intent myIntent = new Intent(GameActivity.this, EndGameActivity.class);
                myIntent.putExtra(Constants.INTENT_PARAM_SCORE, Integer.toString(score)); //Optional parameters
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