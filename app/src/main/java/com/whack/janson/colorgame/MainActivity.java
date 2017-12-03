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
    TextView start, title, highscore, score, timer, life;
    GameTask gameTask;
    final MainActivity activity = this;
    ImageView questionCircle, circlel, circler;
    View left, right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionCircle = (ImageView) findViewById(R.id.question_circle);
        start = (TextView) findViewById(R.id.start);
        circlel = (ImageView) findViewById(R.id.circle_l);
        circler = (ImageView) findViewById(R.id.circle_r);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        score = (TextView) findViewById(R.id.score);
        timer = (TextView) findViewById(R.id.timer);
        life = (TextView) findViewById(R.id.life);
        title = (TextView) findViewById(R.id.title);
        highscore = (TextView) findViewById(R.id.highscore);
        SharedPreferences sp = activity.getSharedPreferences("default", MODE_PRIVATE);
        if (!sp.contains("high_score")) {
            highscore.setVisibility(View.INVISIBLE);
        } else {
            highscore.setText("High score: " + sp.getInt("high_score", 0));
        }
        questionCircle.setVisibility(View.INVISIBLE);

        findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
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

    public void start() {
        if (start.getVisibility() != View.VISIBLE) {
            return;
        }
        gameTask = new GameTask(left, right, circlel, circler, questionCircle, timer, score, life, activity);
        gameTask.execute();
        title.setVisibility(View.INVISIBLE);
        start.setVisibility(View.GONE);
        questionCircle.setVisibility(View.VISIBLE);
    }
}
