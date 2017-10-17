package com.project.game.touchit;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = SettingsActivity.class.getName();

    String[] allowedGridSizes = { "2x2", "3x3", "5x5"  };
    String[] allowedTimes = {"5", "15", "30"};
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Spinner gridSize, chooseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        editor = prefs.edit();

        gridSize = (Spinner) findViewById(R.id.grid_size);
        chooseTime = (Spinner) findViewById(R.id.time);
        gridSize.setOnItemSelectedListener(this);
        chooseTime.setOnItemSelectedListener(this);

        ArrayAdapter sizes = new ArrayAdapter(this,android.R.layout.simple_spinner_item,allowedGridSizes);
        sizes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gridSize.setAdapter(sizes);


        ArrayAdapter times = new ArrayAdapter(this,android.R.layout.simple_spinner_item,allowedTimes);
        times.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseTime.setAdapter(times);

        setDefaultSelections();

    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {

        Spinner spinner =(Spinner) arg0;
        if(spinner.getId() == R.id.grid_size) {
            switch (allowedGridSizes[position]) {
                case "2x2":
                    editor = prefs.edit();
                    editor.putInt(Constants.PREFS_KEY_ORDERGRID, 2);
                    editor.apply();
                    break;
                case "3x3":
                    editor = prefs.edit();
                    editor.putInt(Constants.PREFS_KEY_ORDERGRID, 3);
                    editor.apply();
                    break;
                case "5x5":
                    editor = prefs.edit();
                    editor.putInt(Constants.PREFS_KEY_ORDERGRID, 5);
                    editor.apply();
                    break;
            }
        }
        else if(spinner.getId() == R.id.time) {
            switch (allowedTimes[position]){
                case "5":
                    editor = prefs.edit();
                    editor.putInt(Constants.PREFS_KEY_TIME, 5000);
                    editor.apply();
                    break;
                case "15":
                    editor = prefs.edit();
                    editor.putInt(Constants.PREFS_KEY_TIME, 15000);
                    editor.apply();
                    break;
                case "30":
                    editor = prefs.edit();
                    editor.putInt(Constants.PREFS_KEY_TIME, 30000);
                    editor.apply();
                    break;
            }
        }
        //Toast.makeText(getApplicationContext(),allowedGridSizes[position] ,Toast.LENGTH_LONG).show();
    }

    void setDefaultSelections() {
        int selectedOrderGrid = prefs.getInt(Constants.PREFS_KEY_ORDERGRID, Constants.DEFAULT_ORDER_GRID);
        int selectedTotalTime = prefs.getInt(Constants.PREFS_KEY_TIME, Constants.DEFAULT_TOTAL_TIME);

        switch (selectedOrderGrid) {
            case 2: gridSize.setSelection(0);
                break;
            case 3: gridSize.setSelection(1);
                break;
            case 5: gridSize.setSelection(2);
                break;
        }

        switch (selectedTotalTime) {
            case 5000: chooseTime.setSelection(0);
                break;
            case 15000: chooseTime.setSelection(1);
                break;
            case 30000: chooseTime.setSelection(2);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
