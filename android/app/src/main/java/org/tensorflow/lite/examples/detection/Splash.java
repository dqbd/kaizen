package org.tensorflow.lite.examples.detection;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class Splash extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Button login = findViewById(R.id.login);

        login.setOnClickListener(v -> startActivity(new Intent(Splash.this, DetectorActivity.class)));
        login.setAlpha(0);
        login.setEnabled(false);

        LottieAnimationView animationView = findViewById(R.id.logo);
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(Splash.this, "Ended", Toast.LENGTH_LONG).show();
                login.setAlpha(1);
                login.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }
}
