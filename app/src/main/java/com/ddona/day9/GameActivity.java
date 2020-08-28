package com.ddona.day9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, Runnable {
    private static final long ROTATE_DURATION = 250;
    private static final long DOWN_TIME = 2000;
    private ImageView imgBall, imgSquare, imgStart;
    private Button btnLeft, btnRight;
    private TextView tvScore;
    private int degree = 0;
    private int squareColor = 0;
    private int ballColor = 0;
    private boolean isStarted = false;
    private Handler handler = new Handler();
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initViews();

    }

    private void initViews() {
        imgBall = findViewById(R.id.img_ball);
        imgSquare = findViewById(R.id.img_square);
        imgStart = findViewById(R.id.img_start);
        btnLeft = findViewById(R.id.btn_left);
        btnRight = findViewById(R.id.btn_right);
        tvScore = findViewById(R.id.tv_score);
        btnRight.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        imgStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                turnLeft();
                break;
            case R.id.btn_right:
                turnRight();
                break;
            case R.id.img_start:
                startGame();
                break;
        }
    }

    private void startGame() {
        score = 0;
        squareColor = 0;
        ballColor = 0;
        degree = 0;
        isStarted = true;
        imgSquare.clearAnimation();
        Thread thread = new Thread(this);
        thread.start();
    }

    private void turnLeft() {
        RotateAnimation animation = new RotateAnimation(
                degree, degree - 90,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(ROTATE_DURATION);
        animation.setFillAfter(true);
        imgSquare.startAnimation(animation);
        degree -= 90;
        squareColor++;
        if (squareColor > 3) {
            squareColor = 0;
        }
    }

    private void turnRight() {
        RotateAnimation animation = new RotateAnimation(
                degree, degree + 90,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(ROTATE_DURATION);
        animation.setFillAfter(true);
        imgSquare.startAnimation(animation);
        degree += 90;
        squareColor--;
        if (squareColor < 0) {
            squareColor = 3;
        }
    }

    private void ballFallDown() {
        Random random = new Random();
        ballColor = random.nextInt(4);
        imgBall.setImageResource(R.drawable.ball_0 + ballColor);
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0.9f
        );
        translateAnimation.setDuration(DOWN_TIME);
        imgBall.startAnimation(translateAnimation);
//        if(ballColor==0) {
//            imgBall.setImageResource(R.drawable.ball_0);
//        } else if(ballColor ==1) {
//            imgBall.setImageResource(R.drawable.ball_1);
//        }else if(ballColor ==2) {
//            imgBall.setImageResource(R.drawable.ball_2);
//        }else if(ballColor ==3) {
//            imgBall.setImageResource(R.drawable.ball_3);
//        }
    }

    @Override
    public void run() {
        while (true) {
            if (isStarted) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("doanpt", "start ball");
                        ballFallDown();
                    }
                });
                try {
                    Thread.sleep(DOWN_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (ballColor == squareColor) {
                    Log.d("doanpt", "true:" + ballColor + " - " + squareColor);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            score++;
                            tvScore.setText(score + "");
                        }
                    });
                } else {
                    Log.d("doanpt", "false:" + ballColor + " - " + squareColor);
                    return;
                }
            }
        }
    }
}
