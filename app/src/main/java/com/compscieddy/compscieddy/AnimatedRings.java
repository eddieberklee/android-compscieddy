package com.compscieddy.compscieddy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by elee on 12/11/15.
 */
public class AnimatedRings extends View {

  private static final String TAG = AnimatedRings.class.getSimpleName();
  private static final int RADIUS_INCREMENT = 6;
  private Context mContext;
  private Handler mHandler;
  private Runnable mRunnable;
  private Paint mPaint;
  private boolean mSingleRun; // whether or not to loop animation
  private boolean mAutoRun; // start animation on initialization?
  private int mRingStrokeWidth;
  private Set<Ring> mRings = new HashSet<>();

  public AnimatedRings(Context context, AttributeSet attrs) {
    super(context, attrs);
    mContext = context;

    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AnimatedRings, 0, 0);
    try {
      mSingleRun = a.getBoolean(R.styleable.AnimatedRings_singleRun, false);
      mAutoRun = a.getBoolean(R.styleable.AnimatedRings_autoRun, false);
      mRingStrokeWidth = a.getInt(R.styleable.AnimatedRings_ringStrokeWidth, 2);
      // TODO: add attribute for stroke width
    } finally {
      a.recycle();
    }

    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeWidth(mRingStrokeWidth);

    mHandler = new Handler();
    mRunnable = new Runnable() {
      @Override
      public void run() {
        invalidate();
        mHandler.postDelayed(mRunnable, 30);
      }
    };

    if (mAutoRun) {
      startAnimation();
    }
  }

  /** Calling this programmatically is most useful when "singleRun" is also switched on.
   * Then hook up a click listener or something like that. */
  public void startAnimation() {
    mRings.add(new Ring(60));
    mHandler.post(mRunnable);
  }

  private void stopAnimation() {
    mHandler.removeCallbacks(mRunnable);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    if (mRings.size() == 0) {
      stopAnimation();
    }

    Object[] ringsList = mRings.toArray(); // to protect against ConcurrentModificationException
    for (Object o : ringsList) {
      Ring r = (Ring) o;
      mPaint.setColor(r.color);

      int maxRingRadius = getWidth() / 2; // todo: do things break if I do getWidth() in class instantialization? yes it should, so then move to init?

      if (r.ringRadius + RADIUS_INCREMENT > maxRingRadius) {
        r.ringRadius = 0;
        if (mSingleRun) mRings.remove(r);
      } else {
        r.ringRadius += RADIUS_INCREMENT;
      }

      float progressPercentage = (float) r.ringRadius / maxRingRadius;

      mPaint.setAlpha(255 - Math.round((float) r.ringRadius / maxRingRadius * 255));

      canvas.drawCircle(maxRingRadius, getHeight() / 2, r.ringRadius, mPaint);
    }

  }



  private class Ring {
    public int ringRadius = 0;
    public int color = mContext.getResources().getColor(android.R.color.white);
    public Ring() {
    }
    public Ring(int ringRadius) {
      this.ringRadius = ringRadius;
    }
    public Ring(int ringRadius, int color) {
      this(ringRadius);
      this.color = color;
    }
  }

}
