package com.compscieddy.compscieddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

  @Bind(R.id.eddie_face) ImageView mEddieFace;
  @Bind(R.id.eddie_title) TextView mEddieTitle;
  @Bind(R.id.greeting_message) TextView mGreetingMessage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);
  }

  @Override
  protected void onResume() {
    super.onResume();

    mEddieFace.setScaleX(0f);
    mEddieFace.setScaleY(0f);

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
