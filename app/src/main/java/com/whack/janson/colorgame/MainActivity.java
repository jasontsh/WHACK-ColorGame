package com.whack.janson.colorgame;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView start, title, highscore;
    GameTask gameTask;
    final MainActivity activity = this;
    ImageView questionCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionCircle = (ImageView) findViewById(R.id.question_circle);
        start = (TextView) findViewById(R.id.start);
        final ImageView circlel = (ImageView) findViewById(R.id.circle_l);
        final ImageView circler = (ImageView) findViewById(R.id.circle_r);
        final View left = findViewById(R.id.left);
        final View right = findViewById(R.id.right);
        final TextView score = (TextView) findViewById(R.id.score);
        final TextView timer = (TextView) findViewById(R.id.timer);
        final TextView life = (TextView) findViewById(R.id.life);
        title = (TextView) findViewById(R.id.title);
        highscore = (TextView) findViewById(R.id.highscore);
        SharedPreferences sp = activity.getSharedPreferences("default", MODE_PRIVATE);
        if (!sp.contains("high_score")) {
            highscore.setVisibility(View.INVISIBLE);
        } else {
            highscore.setText("High score: " + sp.getInt("high_score", 0));
        }
        questionCircle.setVisibility(View.INVISIBLE);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameTask = new GameTask(left, right, circlel, circler, questionCircle, timer, score, life, activity);
                gameTask.execute();
                title.setVisibility(View.INVISIBLE);
                start.setVisibility(View.GONE);
                questionCircle.setVisibility(View.VISIBLE);
            }
        });
    }

    public void handleRestart() {
        questionCircle.setVisibility(View.INVISIBLE);
        start.setVisibility(View.VISIBLE);
        SharedPreferences sp = activity.getSharedPreferences("default", MODE_PRIVATE);
        highscore.setText("High score: " + sp.getInt("high_score", 0));
        highscore.setVisibility(View.VISIBLE);
    }
}
