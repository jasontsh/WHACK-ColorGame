package com.whack.janson.colorgame;

import android.graphics.Color;

/**
 * Created by MLH-Admin on 11/11/2017.
 */

public class GameState {

    public static final double DECAY_FACTOR = 0.95;

    private long milliLeft;
    private String displayTimeLeft;
    private int score;
    private int colorQuestion;
    private int colorl, colorr;
    private int dist_margin, rand_margin;
    private boolean isLeft = false;
    private int life;
    private int fullTime;
    private boolean newQuestion;

    public GameState(int startTime) {
        milliLeft = startTime * 1000;
        score = 0;
        dist_margin = 80;
        rand_margin = 40;
        life = 3;
        fullTime = startTime;
    }

    public void makeQuestion() {
        int red, blue, green;
        red = (int) (255 * Math.random());
        blue = (int) (255 * Math.random());
        green = (int) (255 * Math.random());
        int lred = 0, lgreen = 0, lblue = 0;
        while (lred + lgreen + lblue < 80) {
            colorQuestion = Color.rgb(red, green, blue);
            lred = (red + (Math.random() > 0.5 ? dist_margin : -dist_margin) + randomCentered(rand_margin)) % 255;
            lgreen = (green + (Math.random() > 0.5 ? dist_margin : -dist_margin) + randomCentered(rand_margin)) % 255;
            lblue = (blue + (Math.random() > 0.5 ? dist_margin : -dist_margin) + randomCentered(rand_margin)) % 255;
        }
        int distance = Math.abs(lred - red) + Math.abs(lgreen - green) + Math.abs(lblue - blue);
        colorl = Color.rgb(lred, lgreen, lblue);
        int rred = 0, rgreen = 0, rblue = 0;
        while (rred + rgreen + rblue < 80) {
            rred = (red + (Math.random() > 0.5 ? dist_margin : -dist_margin) + randomCentered(rand_margin)) % 255;
            rgreen = (green + (Math.random() > 0.5 ? dist_margin : -dist_margin) + randomCentered(rand_margin)) % 255;
            rblue = (blue + (Math.random() > 0.5 ? dist_margin : -dist_margin) + randomCentered(rand_margin)) % 255;
        }
        int distancer = Math.abs(rred - red) + Math.abs(rgreen - green) + Math.abs(rblue - blue);
        if (distance == distancer) {
            rred += 1;
            rred %= 255;
            distancer += 1;
        }
        colorr = Color.rgb(rred, rgreen, rblue);
        isLeft = distance < distancer;
        milliLeft = fullTime * 1000;
        displayTimeLeft = (milliLeft / 1000) + "";
        newQuestion = true;
    }

    private int randomCentered(int r) {
        return (int) (2 * r * Math.random()) - r;
    }

    public int getQuestionColor() {
        return colorQuestion;
    }

    public int getColorl() {
        return colorl;
    }

    public int getColorr() {
        return colorr;
    }

    public void setAnswer(boolean isLeft) {
        if (isLeft == this.isLeft) {
            score++;
            dist_margin *= DECAY_FACTOR;
            rand_margin *= DECAY_FACTOR;
            fullTime *= DECAY_FACTOR;
        } else {
            life--;
        }
    }

    public int getScore() {
        return score;
    }

    public int getLife() {
        return life;
    }

    public long getMilliLeft() {
        return milliLeft;
    }

    public String getDisplayTimeLeft() {
        return "Time left: " + (milliLeft / 1000);
    }

    public void updatedQuestion(){
        newQuestion = false;
    }

    public boolean getNewQuestion(){
        return newQuestion;
    }

    public void timesUp(){
        life--;
    }

    public void pastTime(int milli) {
        milliLeft -= milli;
    }

    public boolean isLeft() {
        return isLeft;
    }
}
