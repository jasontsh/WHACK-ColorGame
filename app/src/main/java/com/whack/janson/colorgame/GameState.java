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
        rand_margin = 80;
        life = 3;
        fullTime = startTime * 1000;
    }

    public void makeQuestion() {
        int red = 0, blue = 0, green = 0;
        int lred, lgreen, lblue;
        boolean directionr = Math.random() > 0.5;
        boolean directiong = Math.random() > 0.5;
        boolean directionb = Math.random() > 0.5;
        while (red + green + blue < 50) {
            red = (int) (255 * Math.random());
            blue = (int) (255 * Math.random());
            green = (int) (255 * Math.random());
            colorQuestion = Color.rgb(red, green, blue);
        }
        lred = (int) (red + ((directionr ? 1 : -1) * Math.random() * rand_margin)) % 255 ;
        lgreen = (int) (green + ((directiong ? 1 : -1) * Math.random() * rand_margin)) % 255 ;
        lblue = (int) (blue + ((directionb ? 1 : -1) * Math.random() * rand_margin)) % 255 ;
        colorl = Color.rgb(lred, lgreen, lblue);
        int distance = Math.abs(lred - red) + Math.abs(lgreen - green) + Math.abs(lblue - blue);
        int rred, rgreen, rblue;
        rred = (lred + ((directionr ? 1 : -1) * dist_margin)) % 255;
        rgreen = (lgreen + ((directionr ? 1 : -1) * dist_margin)) % 255;
        rblue = (lblue + ((directionr ? 1 : -1) * dist_margin)) % 255;
        colorr = Color.rgb(rred, rgreen, rblue);
        int distancer = Math.abs(rred - red) + Math.abs(rgreen - green) + Math.abs(rblue - blue);
        isLeft = Math.random() > 0.5;
        if (distancer < distance) {
            isLeft = !isLeft;
        }
        if (!isLeft) {
            int temp = colorr;
            colorr = colorl;
            colorl = temp;
        }
        milliLeft = fullTime;
        displayTimeLeft = (milliLeft / 1000) + "";
        newQuestion = true;
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
