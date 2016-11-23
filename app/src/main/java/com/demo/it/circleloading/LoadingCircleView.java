package com.demo.it.circleloading;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 2016/11/17.
 */

public class LoadingCircleView extends View {

    private Paint mBigPaint;
    private Paint mSmallPaint;
    private float mScreenWidth;
    private float smallRadius;
    private int mBgColor;
    private int mLoadingColor;
    private float mPreRadius;

    public LoadingCircleView(Context context) {
        this(context, null);
    }

    public LoadingCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.loading_circle);
        mBgColor = typedArray.getColor(R.styleable.loading_circle_circle_background, getResources().getColor(R.color.colorAccent));
        mLoadingColor = typedArray.getColor(R.styleable.loading_circle_circle_loading, Color.GREEN);
        typedArray.recycle();
        initPaint(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) mScreenWidth, (int) mScreenWidth);
    }


    private void initPaint(Context context) {
        mScreenWidth = PhoneInfoUtils.getScreenWidth(context);
        mPreRadius = mScreenWidth / 2 / 100f;
        //big
        mBigPaint = new Paint();
        mBigPaint.setColor(mBgColor);
        mBigPaint.setStyle(Paint.Style.FILL);
        mSmallPaint = new Paint();
        mSmallPaint.setStyle(Paint.Style.FILL);
        mSmallPaint.setColor(mLoadingColor);
    }

    public void setRadius(int radius) {
        smallRadius = radius * mPreRadius;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mScreenWidth / 2, mScreenWidth / 2, mScreenWidth / 2, mBigPaint);
        canvas.drawCircle(mScreenWidth / 2, mScreenWidth / 2, smallRadius, mSmallPaint);
    }
}
