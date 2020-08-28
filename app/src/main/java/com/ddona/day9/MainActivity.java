package com.ddona.day9;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//https://developer.android.com/guide/topics/resources/animation-resource#java
public class MainActivity extends AppCompatActivity {
    private TextView tvAlpha;
    private ImageView imgCircle, imgHand, imgFly;
    private Button btnStart, btnTranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.btn_start);
        btnTranslate = findViewById(R.id.btn_translate);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValueAnimator animator = ValueAnimator.ofFloat(0, 1000);
                animator.setDuration(3000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float current = (float) valueAnimator.getAnimatedValue();
                        btnTranslate.setY(current);
                        btnTranslate.setWidth((int) current);
                        Log.d("doanpt","update" + current);
                    }
                });
                animator.start();

//                ObjectAnimator objectAnimator = ObjectAnimator
//                        .ofFloat(btnTranslate, "y",0, 1000 );
//                objectAnimator.setDuration(100000);
//                animator.start();

            }

        });

        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You're so handsome",
                        Toast.LENGTH_SHORT).show();
            }
        });

        tvAlpha = findViewById(R.id.tv_alpha);
        imgCircle = findViewById(R.id.img_circle);
        imgHand = findViewById(R.id.img_hand);
        imgHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this,
                        R.anim.scale_hand);
                imgHand.startAnimation(animation);
            }
        });

        imgFly = findViewById(R.id.img_fly);
        imgFly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Animation animation = AnimationUtils.loadAnimation(MainActivity.this,
//                        R.anim.translate_fly);
//                imgFly.startAnimation(animation);
                startFlyAnimation();
            }
        });
        imgCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this,
                        R.anim.rotate_circle);
                animation.setDuration(1000);
                imgCircle.startAnimation(animation);
            }
        });
        tvAlpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation alphaAnimation = AnimationUtils.loadAnimation(
                        MainActivity.this,
                        R.anim.alpha_warring);
                alphaAnimation.setDuration(1000);
                alphaAnimation.setInterpolator(new AccelerateInterpolator());
                tvAlpha.startAnimation(alphaAnimation);
            }
        });
    }

    private void startFlyAnimation() {
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_PARENT, 0.5f,
                Animation.RELATIVE_TO_PARENT, -0.5f);
        animation.setDuration(2000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        imgFly.startAnimation(animation);
    }
}
