package com.compscieddy.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.compscieddy.Util;

/**
 * Created by elee on 12/18/15.
 */
public class ExpandableHorizontalScrollView extends HorizontalScrollView {

  private static final String TAG = ExpandableHorizontalScrollView.class.getSimpleName();

  private Context mContext;
  private float mWidth;
  private float mHeight;

  public ExpandableHorizontalScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);

    mContext = context;

    setOnDragListener(new OnDragListener() {
      @Override
      public boolean onDrag(View v, DragEvent event) {
        Log.d(TAG, "Did you even get in here yo?");
        switch (event.getAction()) {
          case DragEvent.ACTION_DRAG_STARTED:
            Util.showToast(mContext, "action_drag_started");
            Log.d(TAG, "ACTION_DRAG_STARTED");
            break;
          case DragEvent.ACTION_DRAG_ENTERED:
            Log.d(TAG, "ACTION_DRAG_ENTERED");
            break;
          case DragEvent.ACTION_DRAG_EXITED:
            Log.d(TAG, "ACTION_DRAG_EXITED");
            break;
          case DragEvent.ACTION_DRAG_LOCATION:
            Log.d(TAG, "ACTION_DRAG_LOCATION");
            break;
          case DragEvent.ACTION_DRAG_ENDED:
            Log.d(TAG, "ACTION_DRAG_ENDED");
            break;
          case DragEvent.ACTION_DROP:
            Log.d(TAG, "ACTION_DROP");
            break;
          default:
            break;
        }
        Log.d(TAG, "argh");
        return true;
      }
    });

    setOnTouchListener(new OnTouchListener() {
      private boolean hasStartedDrag = false;
      private float y;
      private final float MAX_DRAGGING_DISTANCE = Util.dpToPx(300);

      @Override
      public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG, "onTouch()");
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            hasStartedDrag = true;
            y = event.getY();
            Log.d(TAG, "action_down");
            break;
          case MotionEvent.ACTION_UP: {
            hasStartedDrag = false;
            float yDifference = Math.abs(y - event.getY());
            boolean isGoingDown = (event.getY() - y) > 0;

            Log.d(TAG, "action_up");
            break;
          }
          case MotionEvent.ACTION_MOVE: {
            Log.d(TAG, "action_move");

            float yDifference = Math.abs(y - event.getY());
            boolean isGoingDown = (event.getY() - y) > 0;

            ViewGroup scrollViewChildrenContainer = (ViewGroup) getChildAt(0);
            for (int i = 0; i < scrollViewChildrenContainer.getChildCount(); i++) {
              View childView = scrollViewChildrenContainer.getChildAt(i);
              ViewGroup.LayoutParams params = childView.getLayoutParams();
              if (yDifference < MAX_DRAGGING_DISTANCE) {
                params.width = (int) (yDifference);
                params.height = (int) (yDifference);
              }
              childView.setLayoutParams(params);
              Log.d(TAG, "view height:" + params.height);
            }

            ViewGroup.LayoutParams params = getLayoutParams();
            if (yDifference < MAX_DRAGGING_DISTANCE) {
              params.height = (int) yDifference;
            }
            setLayoutParams(params);
            Log.d(TAG, "scrollview height:" + params.height);
//            Log.d(TAG, "v:" + isGoingDown + " y:" + y + " event.getRawY():" + event.getRawY() + " event.getY():" + event.getY() + "yDifference:" + yDifference + " height:" + getLayoutParams().height);
            break;
          }
          default:
            break;
        }
        return true;
      }
    });

  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    mWidth = getWidth();
    mHeight = getHeight();
  }

  @Override
  public boolean onDragEvent(DragEvent event) {
    Log.d(TAG, "onDragEvent");
    switch (event.getAction()) {
      case DragEvent.ACTION_DRAG_STARTED:
        Util.showToast(mContext, "action_drag_started");
        Log.d(TAG, "ACTION_DRAG_STARTED");
        break;
      case DragEvent.ACTION_DRAG_ENTERED:
        Log.d(TAG, "ACTION_DRAG_ENTERED");
        break;
      case DragEvent.ACTION_DRAG_EXITED:
        Log.d(TAG, "ACTION_DRAG_EXITED");
        break;
      case DragEvent.ACTION_DRAG_LOCATION:
        Log.d(TAG, "ACTION_DRAG_LOCATION");
        break;
      case DragEvent.ACTION_DRAG_ENDED:
        Log.d(TAG, "ACTION_DRAG_ENDED");
        break;
      case DragEvent.ACTION_DROP:
        Log.d(TAG, "ACTION_DROP");
        break;
      default:
        break;
    }
    Log.d(TAG, "argh");
    return super.onDragEvent(event);
  }
}
