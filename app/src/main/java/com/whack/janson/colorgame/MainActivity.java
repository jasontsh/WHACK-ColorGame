package com.whack.janson.colorgame;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView questionCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionCircle = (ImageView) findViewById(R.id.question_circle);
        questionCircle.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.CLEAR);
        questionCircle.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.ADD);
    }
}
