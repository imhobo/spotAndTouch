package com.example.avantika.clickit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String[] allowedGridSizes = { "2x2", "3x3", "5x5"  };
    String[] allowedTimes = {"5", "15", "30"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner gridSize = (Spinner) findViewById(R.id.grid_size);
        Spinner chooseTime = (Spinner) findViewById(R.id.time);
        gridSize.setOnItemSelectedListener(this);
        chooseTime.setOnItemSelectedListener(this);

        ArrayAdapter sizes = new ArrayAdapter(this,android.R.layout.simple_spinner_item,allowedGridSizes);
        sizes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gridSize.setAdapter(sizes);

        ArrayAdapter times = new ArrayAdapter(this,android.R.layout.simple_spinner_item,allowedTimes);
        times.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseTime.setAdapter(times);
        chooseTime.setSelection(1);


    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {

        Spinner spinner =(Spinner) arg0;
        if(spinner.getId() == R.id.grid_size) {
            switch (allowedGridSizes[position]) {
                case "2x2":
                    GameActivity.orderGrid = 2;
                    break;
                case "3x3":
                    GameActivity.orderGrid = 3;
                    break;
                case "5x5":
                    GameActivity.orderGrid = 5;
                    break;
            }
        }
        else if(spinner.getId() == R.id.time) {
            switch (allowedTimes[position]){
                case "5":
                    GameActivity.totalTime = 5;
                    break;
                case "15":
                    GameActivity.totalTime = 15;
                    break;
                case "30":
                    GameActivity.totalTime =30;
                    break;
            }
        }
        //Toast.makeText(getApplicationContext(),allowedGridSizes[position] ,Toast.LENGTH_LONG).show();
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
