package com.compscieddy.compscieddy;

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
  private int mProgress; // max is getHeight()

  public ClickMeter(Context context, AttributeSet attrs) {
    super(context, attrs);
    mContext = context;

    mHandler = new Handler();
    mRunnable = new Runnable() {
      @Override
      public void run() {
        invalidate();
        mHandler.postDelayed(mRunnable, 30);
      }
    };
    startAnimation();

    mPaint = new Paint();
    mPaint.setColor(mContext.getResources().getColor(R.color.flatui_red_1));

  }

  public void startAnimation() {
    mHandler.post(mRunnable);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeWidth(4);
    mPaint.setColor(mContext.getResources().getColor(R.color.flatui_grey_1));
    canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    mPaint.setColor(mContext.getResources().getColor(R.color.flatui_red_1));
    mPaint.setStyle(Paint.Style.FILL);
    canvas.drawRect(0, getHeight() - mProgress, getWidth(), getHeight(), mPaint); // height - progress to keep the progress at 0 for minimum (easier to conceptually internalize)
  }
}
