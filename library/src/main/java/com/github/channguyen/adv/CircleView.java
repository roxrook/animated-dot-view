package com.github.channguyen.adv;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {

  private static final String TAG = CircleView.class.getSimpleName();

  private static final float DEFAULT_STROKE_WIDTH = 3.0f;

  private static final int DEFAULT_RADIUS = 20;

  private static final int DEFAULT_COLOR = Color.parseColor("#FF0000");

  protected float radius;

  protected float strokeWidth = DEFAULT_STROKE_WIDTH;

  protected Paint paint;

  private int color = DEFAULT_COLOR;

  protected boolean filled = true;

  public CircleView(Context context) {
    this(context, null);
  }

  public CircleView(Context context, AttributeSet attrs) {
    this(context, attrs, -1);
  }

  public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    if (attrs != null) {
      TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
      try {
        radius = a.getDimensionPixelSize(
          R.styleable.CircleView_cv___radius, DEFAULT_RADIUS);
        color = a.getInt(
          R.styleable.CircleView_cv___color, DEFAULT_COLOR);
        filled = a.getBoolean(
          R.styleable.CircleView_cv___filled, true);
      } finally {
        a.recycle();
      }
    }

    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setStrokeWidth(strokeWidth);
    paint.setColor(color);
    if (filled) {
      paint.setStyle(Paint.Style.FILL);
    } else {
      paint.setStyle(Paint.Style.STROKE);
    }
  }

  public void setRadius(int radius) {
    this.radius = radius;
    invalidate();
  }

  public void setColor(int color) {
    this.color = color;
    this.paint.setColor(color);
    invalidate();
  }

  public void setFilled(boolean filled) {
    this.filled = filled;
    paint.setStyle(this.filled ? Paint.Style.FILL : Paint.Style.STROKE);
  }

  public boolean isFilled() {
    return filled;
  }

  public float getRadius() {
    return radius;
  }

  public float getStrokeWidth() {
    return strokeWidth;
  }

  public int getColor() {
    return color;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
  }

  /**
   * Measures height according to the passed measure spec
   *
   * @param measureSpec int measure spec to use
   * @return int pixel size
   */
  protected int measureHeight(int measureSpec) {
    int specMode = MeasureSpec.getMode(measureSpec);
    int specSize = MeasureSpec.getSize(measureSpec);
    int result;
    if (specMode == MeasureSpec.EXACTLY) {
      result = specSize;
    } else {
      result = (int) (2 * radius) + getPaddingTop() + getPaddingBottom() + (int) (2 * strokeWidth);
      if (specMode == MeasureSpec.AT_MOST) {
        result = Math.min(result, specSize);
      }
    }
    return result;
  }

  /**
   * Measures width according to the passed measure spec
   *
   * @param measureSpec int measure spec to use
   * @return int pixel size
   */
  protected int measureWidth(int measureSpec) {
    int specMode = MeasureSpec.getMode(measureSpec);
    int specSize = MeasureSpec.getSize(measureSpec);
    int result;
    if (specMode == MeasureSpec.EXACTLY) {
      result = specSize;
    } else {
      result = (int) (2 * radius) + getPaddingLeft() + getPaddingRight() + (int) (2 * strokeWidth);
      if (specMode == MeasureSpec.AT_MOST) {
        result = Math.min(result, specSize);
      }
    }
    return result;
  }

  @Override
  public void onDraw(Canvas canvas) {
    final int x = getWidth() >> 1;
    final int y = getHeight() >> 1;
    canvas.drawCircle(x, y, radius - strokeWidth, paint);
    super.onDraw(canvas);
  }
}
