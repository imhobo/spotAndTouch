package com.example.avantika.clickit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EndGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Intent intent = getIntent();
        String score = intent.getStringExtra("score");

        TextView tvScore = (TextView) findViewById(R.id.endgame_score);
        tvScore.setText("Score: "+ score);

        Button playAgain = (Button) findViewById(R.id.play_again);
        Button goBack = (Button) findViewById(R.id.go_back);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(EndGame.this, GameActivity.class);
                EndGame.this.startActivity(myIntent);
                finish();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(EndGame.this, OptionsActivity.class);
                EndGame.this.startActivity(myIntent);
                finish();
            }
        });
    }
}
