package com.oathofdominion.grandarena;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View logo       = findViewById(R.id.splash_logo);
        TextView title  = findViewById(R.id.splash_title);
        TextView sub    = findViewById(R.id.splash_subtitle);

        // Start hidden
        logo.setAlpha(0f);  logo.setScaleX(0.5f); logo.setScaleY(0.5f);
        title.setAlpha(0f); title.setTranslationY(28f);
        sub.setAlpha(0f);   sub.setTranslationY(18f);

        // Animate logo in
        AnimatorSet s1 = new AnimatorSet();
        s1.playTogether(
            ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f).setDuration(700),
            ObjectAnimator.ofFloat(logo, "scaleX", 0.5f, 1f).setDuration(700),
            ObjectAnimator.ofFloat(logo, "scaleY", 0.5f, 1f).setDuration(700)
        );
        s1.setInterpolator(new AccelerateDecelerateInterpolator());
        s1.start();

        // Animate text in after logo
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            AnimatorSet s2 = new AnimatorSet();
            s2.playTogether(
                ObjectAnimator.ofFloat(title, "alpha", 0f, 1f).setDuration(550),
                ObjectAnimator.ofFloat(title, "translationY", 28f, 0f).setDuration(550),
                ObjectAnimator.ofFloat(sub, "alpha", 0f, 1f).setDuration(550),
                ObjectAnimator.ofFloat(sub, "translationY", 18f, 0f).setDuration(550)
            );
            s2.setInterpolator(new AccelerateDecelerateInterpolator());
            s2.start();
        }, 450);

        // Navigate to main after 2.4 seconds
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, 2400);
    }
}
