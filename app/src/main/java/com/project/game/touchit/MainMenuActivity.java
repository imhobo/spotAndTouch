package com.project.game.touchit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    private static final String TAG = MainMenuActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button mode, settings, highScore;
        mode = (Button) findViewById(R.id.choose_mode);
        settings = (Button) findViewById(R.id.settings);
        highScore = (Button) findViewById(R.id.high_score);

        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainMenuActivity.this, ChooseModeActivity.class);
                MainMenuActivity.this.startActivity(myIntent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainMenuActivity.this, SettingsActivity.class);
                MainMenuActivity.this.startActivity(myIntent);
            }
        });

        // TODO :: Implement a high score activity
        highScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainMenuActivity.this, "Go make your own.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
