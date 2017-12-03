package com.whack.janson.colorgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by MLH-Admin on 11/11/2017.
 */

public class GameTask extends AsyncTask<Void, GameState, Integer> {

    boolean isClickl, isClickr;
    ImageView left, right, question;
    TextView timer, score, life;
    MainActivity activity;

    public GameTask(View l, View r, ImageView left, ImageView right, ImageView question,
                    TextView timer, TextView score, TextView life, final MainActivity activity) {
        this.left = left;
        this.right = right;
        this.question = question;
        this.timer = timer;
        this.score = score;
        this.activity = activity;
        this.life = life;

        isClickl = false;
        isClickr = false;

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.start();
                isClickl = true;
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.start();
                isClickr = true;
            }
        });
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        GameState gameState = new GameState(8);
        while (gameState.getLife() > 0) {
            gameState.makeQuestion();
            publishProgress(gameState);
            Log.d("TAG", gameState.getMilliLeft() + "");
            while(gameState.getMilliLeft() > 0) {
                try{
                    Thread.sleep(100);
                    gameState.pastTime(100);
                    if (isClickl || isClickr) {
                        gameState.setAnswer(isClickl);
                        isClickl = false;
                        isClickr = false;
                        break;
                    }
                    publishProgress(gameState);
                } catch (InterruptedException e){
                }
            }
            if (gameState.getMilliLeft() == 0) {
                gameState.timesUp();
            }
        }
        return gameState.getScore();
    }

    @Override
    protected void onProgressUpdate(GameState... values) {
        GameState gs = values[values.length-1];
        timer.setText(gs.getDisplayTimeLeft());
        if (gs.getNewQuestion()) {
            life.setText("Life: " + gs.getLife());
            score.setText("Score: " + gs.getScore());
            gs.updatedQuestion();
            left.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.CLEAR);
            left.getDrawable().setColorFilter(gs.getColorl(), PorterDuff.Mode.ADD);
            right.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.CLEAR);
            right.getDrawable().setColorFilter(gs.getColorr(), PorterDuff.Mode.ADD);
            question.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.CLEAR);
            question.getDrawable().setColorFilter(gs.getQuestionColor(), PorterDuff.Mode.ADD);
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        SharedPreferences sp = activity.getSharedPreferences("default", MODE_PRIVATE);
        if (sp.getInt("high_score", 0) < integer) {
            SharedPreferences.Editor ed = sp.edit();
            ed.putInt("high_score", integer);
            ed.apply();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Game over!")
                .setMessage("You got " + integer + " this game!")
                .setPositiveButton("YAY", null).show();
        activity.handleRestart();
    }
}
