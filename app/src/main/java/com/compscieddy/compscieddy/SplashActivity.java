package com.compscieddy.compscieddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

  @Bind(R.id.eddie_face) ImageView mEddieFace;
  @Bind(R.id.eddie_title) TextView mEddieTitle;
  @Bind(R.id.greeting_message) TextView mGreetingMessage;
  @Bind(R.id.animated_rings) AnimatedRings mAnimatedRings;
  @Bind(R.id.click_meter) ClickMeter mClickMeter;
  @Bind(R.id.real_button)
  Button mRealButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);

    mEddieFace.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mAnimatedRings.startAnimation();
        mClickMeter.addProgress(20);
      }
    });

    mClickMeter.setFinishedAction(new Runnable() {
      @Override
      public void run() {
        mGreetingMessage.setText("Psyche! - here's the button:");
        mRealButton.setVisibility(View.VISIBLE);
      }
    });

  }

  @Override
  protected void onResume() {
    super.onResume();

    mEddieFace.animate()
        .setInterpolator(new OvershootInterpolator(2.3f))
        .scaleX(1.0f)
        .scaleY(1.0f)
        .setDuration(1000)
        .setStartDelay(1000);

    mEddieTitle.animate()
        .translationY(0)
        .alpha(1)
        .setInterpolator(new OvershootInterpolator(1.9f))
        .setDuration(700)
        .setStartDelay(1500);

    mGreetingMessage.animate()
        .alpha(1f)
        .setDuration(2500)
        .setStartDelay(2600);
  }
}
