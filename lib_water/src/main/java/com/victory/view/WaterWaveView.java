package com.victory.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;


/**
 * describe：
 *
 * @author ：鲁宇峰 on 2018/10/23 18：55
 *         email：luyufengc@enn.cn
 */
public class WaterWaveView extends View {
    private String mExampleString;
    private int mExampleColor = Color.RED;

    private float mExampleDimension = 0;
    private Drawable mExampleDrawable;
    private int backColor;

    private int mWidth;
    private int mHeight;

    private Point startPoint;
    private Path wavePath1;//第一条wavePath
    private Path wavePath2;
    private Paint mPaint;

    private int waveLength;//波长
    private int waveAmplitude;//振幅
    private int waveLevel; //偏距//水位
    private long period = 8000;//周期


    private PathRunnable pathRunnable;
    private CosPathRunnable cosPathRunnable;
    private int dx = 5;//每次移动像素

    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;

    private RectF rectF;

    public WaterWaveView(Context context) {
        super(context);
        init(null, 0);
    }

    public WaterWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public WaterWaveView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.WaterWaveView, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.WaterWaveView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.WaterWaveView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.WaterWaveView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.WaterWaveView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.WaterWaveView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        wavePath1 = new Path();
        wavePath2 = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getBackColor());
        mPaint.setStrokeWidth(2);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();
        waveLength = mWidth - paddingLeft - paddingRight;
        waveAmplitude = (mHeight - paddingTop - paddingBottom) / 80;
        waveLevel = (mHeight + paddingTop - paddingBottom) / 2;
        startPoint = new Point(paddingLeft - waveLength, waveLevel);
        rectF = new RectF(paddingLeft, waveLevel, mWidth - paddingRight, mHeight - paddingBottom);
//        pathRunnable = new PathRunnable();
//        pathRunnable.run();
        cosPathRunnable = new CosPathRunnable();
        cosPathRunnable.run();
        //   anim();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        wavePath1.reset();
//        wavePath2.reset();
//
//
//        wavePath1.moveTo(startPoint.x + moveDistance1, waveLevel);
//        wavePath2.moveTo(mWidth - paddingRight + waveLength - moveDistance2, waveLevel);
//
//        for (int x = startPoint.x; x < mWidth - paddingRight; x += waveLength) {
//            wavePath1.rQuadTo(waveLength / 4, -waveAmplitude / 2, waveLength / 2, 0);
//            wavePath1.rQuadTo(waveLength / 4, waveAmplitude / 2, waveLength / 2, 0);
//        }
//        wavePath1.lineTo(mWidth - getPaddingRight(), mHeight - getPaddingBottom());
//        wavePath1.lineTo(getPaddingLeft(), mHeight - getPaddingBottom());
//        wavePath1.close();
//
//        for (int x = mWidth - paddingRight + waveLength; x >= paddingLeft; x -= waveLength) {
//            wavePath2.rQuadTo(-waveLength / 4, waveAmplitude, -waveLength / 2, 0);
//            wavePath2.rQuadTo(-waveLength / 4, -waveAmplitude, -waveLength / 2, 0);
//        }
//        wavePath2.lineTo(getPaddingLeft(), mHeight - getPaddingBottom());
//        wavePath2.lineTo(mWidth - getPaddingRight(), mHeight - getPaddingBottom());
//        wavePath2.close();
        mPaint.setColor(getBackColor());
        canvas.drawPath(wavePath1, mPaint);
        canvas.drawPath(wavePath2, mPaint);
//        postDelayed(pathRunnable, 0);
        postDelayed(cosPathRunnable, 0);
    }

    private ValueAnimator valueAnimator;
    private int moveDistance1;
    private int moveDistance2;

    private void anim() {
        valueAnimator = ValueAnimator.ofInt(0, waveLength);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(period);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                moveDistance1 = dx;
                moveDistance2 = dx;
                invalidate();
            }
        });
        valueAnimator.start();
    }


    private class PathRunnable implements Runnable {

        @Override
        public void run() {
            moveDistance1 += dx;
            moveDistance2 += dx * 2;
            if (moveDistance1 >= waveLength)
                moveDistance1 = 0;
            if (moveDistance2 >= waveLength)
                moveDistance2 = 0;
            wavePath1.reset();
            wavePath2.reset();


            wavePath1.moveTo(startPoint.x + moveDistance1, waveLevel);
            wavePath2.moveTo(mWidth - paddingRight + waveLength - moveDistance2, waveLevel);

            for (int x = startPoint.x; x < mWidth - paddingRight; x += waveLength) {
                wavePath1.rQuadTo(waveLength / 4, -waveAmplitude / 2, waveLength / 2, 0);
                wavePath1.rQuadTo(waveLength / 4, waveAmplitude / 2, waveLength / 2, 0);
            }
            wavePath1.lineTo(mWidth - getPaddingRight(), mHeight - getPaddingBottom());
            wavePath1.lineTo(getPaddingLeft(), mHeight - getPaddingBottom());
            wavePath1.close();

            for (int x = mWidth - paddingRight + waveLength; x >= paddingLeft; x -= waveLength) {
                wavePath2.rQuadTo(-waveLength / 4, waveAmplitude, -waveLength / 2, 0);
                wavePath2.rQuadTo(-waveLength / 4, -waveAmplitude, -waveLength / 2, 0);
            }
            wavePath2.lineTo(getPaddingLeft(), mHeight - getPaddingBottom());
            wavePath2.lineTo(mWidth - getPaddingRight(), mHeight - getPaddingBottom());
            wavePath2.close();
            invalidate();
        }
    }

    private class CosPathRunnable implements Runnable {

        @Override
        public void run() {
            wavePath1.reset();
            wavePath2.reset();
            moveDistance1 += dx;
            moveDistance2 += dx * 2;
            if (moveDistance1 >= waveLength) {
                moveDistance1 = 0;
            }
            if (moveDistance2 >= waveLength) {
                moveDistance2 = 0;
            }


            wavePath1.moveTo(paddingLeft, waveLevel);
            wavePath2.moveTo(paddingLeft, waveLevel);
            for (int i = paddingLeft; i < waveLength; i++) {
                wavePath1.lineTo(i, (float) (waveLevel + waveAmplitude * Math.cos((float) (i + moveDistance1) / waveLength * Math.PI * 2)));
                wavePath2.lineTo(i, (float) (waveLevel - waveAmplitude / 2 * Math.cos((float) (i + moveDistance2) / waveLength * Math.PI * 2)));
            }
//            wavePath1.addRect(paddingLeft,waveLevel,mWidth-paddingRight,mHeight-paddingBottom, Path.Direction.CCW);
//            wavePath2.addRect(paddingLeft,waveLevel,mWidth-paddingRight,mHeight-paddingBottom, Path.Direction.CCW);

            wavePath1.lineTo(mWidth - getPaddingRight(), mHeight - getPaddingBottom());
            wavePath1.lineTo(getPaddingLeft(), mHeight - getPaddingBottom());
            wavePath1.close();
            wavePath2.lineTo(mWidth - getPaddingRight(), mHeight - getPaddingBottom());
            wavePath2.lineTo(getPaddingLeft(), mHeight - getPaddingBottom());
            wavePath2.close();
//            wavePath1.close();
//            wavePath2.close();
            invalidate();
        }
    }

    public int getBackColor() {
        if (backColor == 0) {
            backColor = getResources().getColor(R.color.color_4D0780ed);
        }
        return backColor;
    }

    public void setBackColor(int backColor) {
        this.backColor = backColor;
        invalidate();
    }
}
