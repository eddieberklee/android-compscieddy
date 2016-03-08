package com.compscieddy.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.compscieddy.R;
import com.compscieddy.Util;

/**
 * Created by elee on 12/13/15.
 */
public class SkittlesPie extends FrameLayout {

  private static final String TAG = SkittlesPie.class.getSimpleName();

  private Context mContext;
  private Handler mHandler;

  private int mNumButtons = 1;
  private final int mPlusButtonWidth;
  private final int mPlusButtonHeight;
  private final int mLightGrey2Color;

  private View[] mButtons = new View[mNumButtons];
  private Runnable mButtonRunnable;

  public SkittlesPie(Context context, AttributeSet attrs) {
    super(context, attrs);
    mContext = context;

    mHandler = new Handler();

    mLightGrey2Color = getResources().getColor(R.color.flatui_lightgrey_2);

    mPlusButtonWidth = getResources().getDimensionPixelOffset(R.dimen.skittle_pie_open_button_width);
    mPlusButtonHeight = getResources().getDimensionPixelOffset(R.dimen.skittle_pie_open_button_height);
    View plusButtonView = inflate(mContext, R.layout.skittle_plus_button, null);
    Util.applyColorFilter(plusButtonView.getBackground(), getResources().getColor(R.color.flatui_blue_1));
    plusButtonView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        openAnimation();
      }
    });
    this.addView(plusButtonView,
        new FrameLayout.LayoutParams(mPlusButtonWidth, mPlusButtonHeight, Gravity.CENTER_VERTICAL));

    int smallerButtonSize = getResources().getDimensionPixelOffset(R.dimen.skittle_pie_smaller_button_size);
    for (int i = 0; i < mNumButtons; i++) {
      TextView buttonView = (TextView) inflate(mContext, R.layout.skittle_plus_button, null);
      mButtons[i] = buttonView;
      buttonView.setText("A");
      Util.applyColorFilter(buttonView.getBackground(), getResources().getColor(R.color.flatui_yellow_1));
      buttonView.setScaleX(0);
      buttonView.setScaleY(0);
      this.addView(buttonView,
          new FrameLayout.LayoutParams(smallerButtonSize, smallerButtonSize));
    }
    mButtonRunnable = new Runnable() {
      @Override
      public void run() {
        mButtons[0].animate()
            .scaleX(1.0f)
            .scaleY(1.0f)
            .setInterpolator(new OvershootInterpolator(3.0f));
      }
    };

  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    getLayoutParams().width = Util.dpToPx(400);
    // width is always the same but height changes depending on the number of buttons fanned out
    getLayoutParams().height = Util.dpToPx(20 + mNumButtons * 80 + 20);
  }

  private void openAnimation() {
    this.addView(new SkittlesFan(mContext));
  }


  public class SkittlesFan extends View {

    private float mFanOutX = mPlusButtonWidth * 0.4f;

    public SkittlesFan(Context context) {
      super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);

      Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
      p.setStyle(Paint.Style.FILL);
      p.setColor(mLightGrey2Color);

      getLayoutParams().height = Util.dpToPx(20);
      getLayoutParams().width = Util.dpToPx(20);

      int arcWidth = Util.dpToPx(20);
      int arcHeight = Util.dpToPx(60);
//      canvas.drawArc(arcOval, 0, 180, false, p);

      RectF oval = new RectF(20, 20, getWidth() - 20, getHeight() - 20);
      RectF oval2 = new RectF(0, 0, getWidth(), getHeight());

      Path path = new Path();
      path.setFillType(Path.FillType.EVEN_ODD);

//      Log.d(TAG, "mFanOutX:"+mFanOutX);
      int fanOutArcWidth = mPlusButtonWidth / 2;

      int fanStartingX = mPlusButtonWidth / 2 + Util.dpToPx(10); // offset it a slight amount
      int fanStartingY = getHeight() / 2;

      int topMargin = Util.dpToPx(20);

      mFanOutX++; // - haha cool to see this animation
      Log.d(TAG, "mFanOutX: " + mFanOutX);
      if (mFanOutX == 130) {
        mHandler.post(mButtonRunnable);
      }
      invalidate();

      int fanOutY = fanStartingY - topMargin;
      int fanOutRadius = (int) Math.sqrt(Math.pow(mFanOutX, 2) + Math.pow(fanOutY, 2)); // r = sqrt(x^2 + y^2)

      path.moveTo(fanStartingX, fanStartingY);

      path.lineTo(mFanOutX, fanOutY);

//      RectF arcOval = new RectF(fanOutX, 0, fanOutX + fanOutArcWidth * 2, getHeight());
      RectF arcOval = new RectF(fanStartingX - fanOutRadius, fanStartingY - fanOutRadius, fanStartingX + fanOutRadius, fanStartingY + fanOutRadius);

//      Log.d(TAG, "fan out y: " + fanOutY);
//      Log.d(TAG, "fan out x: " + mFanOutX);
//      Log.d(TAG, "Angle: " + Math.toDegrees(Math.atan(fanOutY / mFanOutX)));
      /*                /|
       *               / | y
       *              /  |
       *             /___|    tan (y / x) gives the angle in radians
       *               x
       */
      float angleFromOrigin = (float) (Math.toDegrees(Math.atan(fanOutY / mFanOutX)));
      path.addArc(arcOval, 360 - angleFromOrigin , angleFromOrigin * 2);

      path.lineTo(fanStartingX, fanStartingY);
//      path.addArc(oval, 0, 180);

//      path.addArc(oval2, 0, 180);

//      float y=20+oval.height()/2;
//      float x=20;
//      path.moveTo(x,y);
//      path.lineTo(x - 20, y);
//
//      x=oval.width()+20;
//      path.moveTo(x,y);
//      path.lineTo(x+20,y);

//      path.lineTo(centerX, fanStartingY);

      path.close();

      canvas.drawPath(path, p);

    }
  }




}
