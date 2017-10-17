package com.project.game.touchit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    private static final String TAG = EndGameActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Intent intent = getIntent();
        String score = intent.getStringExtra(Constants.INTENT_PARAM_SCORE);

        TextView tvScore = (TextView) findViewById(R.id.endgame_score);
        tvScore.setText(getResources().getString(R.string.score) + " " + score);

        Button playAgain = (Button) findViewById(R.id.play_again);
        Button goBack = (Button) findViewById(R.id.go_back);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(EndGameActivity.this, GameActivity.class);
                EndGameActivity.this.startActivity(myIntent);
                finish();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(EndGameActivity.this, MainMenuActivity.class);
                EndGameActivity.this.startActivity(myIntent);
                finish();
            }
        });
    }
}
