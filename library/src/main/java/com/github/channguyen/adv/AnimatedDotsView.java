package com.github.channguyen.adv;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

public class AnimatedDotsView extends LinearLayout {

  private static final String TAG = AnimatedDotsView.class.getSimpleName();

  private static final int DEFAULT_NEUTRAL_COLOR = Color.parseColor("#777777");

  private static final int DEFAULT_BLINKING_COLOR = Color.parseColor("#FF00FF00");

  private static final int DEFAULT_DOT_COUNT = 5;

  private static final int DEFAULT_DOT_RADIUS = 20;

  private static final long DURATION_DIFF = 100L;

  private static final Interpolator DOT_INTERPOLATOR = new AccelerateInterpolator(2.0f);

  protected CircleView[] dotViews;

  protected AnimatorSet animatorSet;

  protected boolean stop = false;

  protected int dotCount;

  protected int blinkingColor = DEFAULT_BLINKING_COLOR;

  protected int neutralColor = DEFAULT_NEUTRAL_COLOR;

  protected int dotRadius = DEFAULT_DOT_RADIUS;

  public AnimatedDotsView(Context context) {
    this(context, null);
  }

  public AnimatedDotsView(Context context, AttributeSet attrs) {
    this(context, attrs, -1);
  }

  public AnimatedDotsView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    inflate(context, R.layout.v_animated_dots, this);
    if (attrs != null) {
      TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AnimatedDotsView);
      try {
        dotRadius = a.getDimensionPixelSize(
          R.styleable.AnimatedDotsView_adv___dotRadius, DEFAULT_DOT_RADIUS);
        dotCount = a.getInt(
          R.styleable.AnimatedDotsView_adv___dotCount, DEFAULT_DOT_COUNT);
        blinkingColor = a.getColor(
          R.styleable.AnimatedDotsView_adv___dotBlinkingColor, DEFAULT_BLINKING_COLOR);
        neutralColor = a.getColor(
          R.styleable.AnimatedDotsView_adv___dotNeutralColor, DEFAULT_NEUTRAL_COLOR);
      } finally {
        a.recycle();
      }
    }

    setOrientation(HORIZONTAL);
    if (dotCount < 1 || dotCount > 10) {
      throw new IllegalArgumentException("The number of dot should be between [1, 10]");
    }
    addCircleViews();
  }

  private AnimatorSet prepareAnimators() {
    ObjectAnimator[] animators = new ObjectAnimator[dotCount];
    int half = dotCount >> 1;
    long d = DURATION_DIFF;
    /**
     * Assign duration increasing -> decreasing
     * <p>
     *   N even:
     *      [d][d + 1]...[d + 1][d]
     *
     *  N odd:
     *      [d][d + 1][d + 2][d + 1][d]
     * </p>
     */

    /** Walk forward with increasing duration  */
    for (int i = 0; i < half; ++i) {
      animators[i] = createAnimator(dotViews[i], d);
      d += DURATION_DIFF;
    }

    /** If dot count is odd, the middle dot has the longest duration */
    if (dotCount % 2 == 1) {
      animators[half] = createAnimator(dotViews[half], d);
      half++;
    }

    /** Walk backward with decreasing duration */
    for (int i = half; i < dotCount; ++i) {
      d -= DURATION_DIFF;
      animators[i] = createAnimator(dotViews[i], d);
    }

    final AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playSequentially(animators);
    animatorSet.setInterpolator(new AccelerateInterpolator(2.0f));
    animatorSet.addListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animation) {

      }

      @Override
      public void onAnimationEnd(Animator animation) {
        if (!stop) {
          animatorSet.start();
        }
      }

      @Override
      public void onAnimationCancel(Animator animation) {

      }

      @Override
      public void onAnimationRepeat(Animator animation) {

      }
    });
    return animatorSet;
  }

  private void addCircleViews() {
    dotViews = new CircleView[dotCount];
    final Context context = getContext();
    for (int i = 0; i < dotCount; ++i) {
      Log.e(TAG, "add view: " + i);
      dotViews[i] = new CircleView(context);
      dotViews[i].setRadius(dotRadius);
      dotViews[i].setColor(neutralColor);
      addView(
          dotViews[i],
          0,
          new LinearLayout.LayoutParams(
              ViewGroup.LayoutParams.WRAP_CONTENT,
              ViewGroup.LayoutParams.WRAP_CONTENT
          )
      );
    }
    animatorSet = prepareAnimators();
  }

  public void startAnimation() {
    stop = false;
    animatorSet.start();
  }

  public ObjectAnimator createAnimator(final CircleView v, long duration) {
    final ObjectAnimator animator = ObjectAnimator.ofObject(
      v,
      "color",
      new ArgbEvaluator(),
      neutralColor,
      blinkingColor
    );
    animator.setDuration(duration);
    animator.setRepeatCount(1);
    animator.setInterpolator(DOT_INTERPOLATOR);
    animator.addListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animation) {

      }

      @Override
      public void onAnimationEnd(Animator animation) {
        v.setColor(neutralColor);
      }

      @Override
      public void onAnimationCancel(Animator animation) {

      }

      @Override
      public void onAnimationRepeat(Animator animation) {

      }
    });
    return animator;
  }

  public void stopAnimation() {
    stop = true;
    animatorSet.end();
  }

  public int getDotCount() {
    return dotCount;
  }

  public int getDotRadius() {
    return dotRadius;
  }

  public int getNeutralColor() {
    return neutralColor;
  }

  public boolean isStop() {
    return stop;
  }

  public int getBlinkingColor() {
    return blinkingColor;
  }
}
