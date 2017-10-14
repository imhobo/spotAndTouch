package com.example.avantika.clickit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button mode, settings, highScore;
        mode = (Button) findViewById(R.id.choose_mode);
        settings = (Button) findViewById(R.id.settings);
        highScore = (Button) findViewById(R.id.high_score);

        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(OptionsActivity.this, ChooseMode.class);
                OptionsActivity.this.startActivity(myIntent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(OptionsActivity.this, SettingsActivity.class);
                OptionsActivity.this.startActivity(myIntent);
            }
        });

        highScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OptionsActivity.this, "Go make your own.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
