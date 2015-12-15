package com.compscieddy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by elee on 12/11/15.
 */
public class ClickMeter extends View {

  private Context mContext;
  private Handler mHandler;
  private Runnable mRunnable;
  private Paint mPaint;
  private int mProgress;
  private int mContainerStrokeWidth;

  public ClickMeter(Context context, AttributeSet attrs) {
    super(context, attrs);
    mContext = context;

    mHandler = new Handler();
    mRunnable = new Runnable() {
      @Override
      public void run() {
        invalidate();
        mHandler.postDelayed(mRunnable, 24);
      }
    };
    startAnimation();

    mPaint = new Paint();
    mPaint.setColor(mContext.getResources().getColor(R.color.flatui_red_1));

    mContainerStrokeWidth = 4;
  }

  public void addProgress(int amount) {
    if (mProgress < getHeight()) {
      mProgress += amount;
    }
  }

  public void startAnimation() {
    mHandler.post(mRunnable);
  }

  private Runnable mFinishedActionRunnable;
  public void setFinishedAction(Runnable r) {
    mFinishedActionRunnable = r;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (mProgress > 0) {
      mProgress -= 2; // TODO: rename cause progress suggests a 0-1 scale
    }
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeWidth(mContainerStrokeWidth);
    mPaint.setColor(mContext.getResources().getColor(R.color.flatui_grey_1));
    canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    if ((float) mProgress / getHeight() < 0.33) {
      mPaint.setColor(mContext.getResources().getColor(R.color.flatui_red_1));
    } else if ((float) mProgress / getHeight() < 0.66) {
      mPaint.setColor(mContext.getResources().getColor(R.color.flatui_yellow_1));
    } else {
      mPaint.setColor(mContext.getResources().getColor(R.color.flatui_teal_1));
    }

    if (mProgress >= getHeight()) {
      mHandler.removeCallbacks(mRunnable);
      mHandler.post(mFinishedActionRunnable);
    }

    mPaint.setStyle(Paint.Style.FILL);
    canvas.drawRect(mContainerStrokeWidth, mContainerStrokeWidth + getHeight() - mProgress,
        getWidth() - mContainerStrokeWidth, getHeight() - mContainerStrokeWidth, mPaint); // height - progress to keep the progress at 0 for minimum (easier to conceptually internalize)
  }
}
