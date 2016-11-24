package com.demo.it.circleloading;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by admin on 2016/11/17.
 */

public class LoadingCircleView extends View {

    private Paint mBigPaint;
    private Paint mSmallPaint;
    private int mLoadingWidth;
    private int mLoadingHeight;
    private float mOuterRadius;
    private int mBgColor;
    private int mLoadingColor;
    private float mPreRadius;
    private float smallRadius;
    private int mProgressNumber;
    private Paint mTextPaint;

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
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //获取宽度
        if (widthMode == MeasureSpec.EXACTLY) {
            mLoadingWidth = widthSize;
        } else {
            mLoadingWidth = 100;
        }
        //获取高度
        if (heightMode == MeasureSpec.EXACTLY) {
            mLoadingHeight = heightSize;
        } else {
            mLoadingHeight = 100;
        }
        mOuterRadius = Math.min(mLoadingWidth, mLoadingHeight) / 2;
        mPreRadius = mOuterRadius / 100f;
        setMeasuredDimension(mLoadingWidth, mLoadingHeight);
    }


    private void initPaint() {
        //big
        mBigPaint = new Paint();
        mBigPaint.setColor(mBgColor);
        mBigPaint.setStyle(Paint.Style.FILL);
        mSmallPaint = new Paint();
        mSmallPaint.setStyle(Paint.Style.FILL);
        mSmallPaint.setColor(mLoadingColor);
        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(60);
    }

    public void setRadius(int radius) {
        mProgressNumber = radius;
        smallRadius = radius * mPreRadius;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mLoadingWidth / 2, mLoadingHeight / 2, mOuterRadius, mBigPaint);
        canvas.drawCircle(mLoadingWidth / 2, mLoadingHeight / 2, smallRadius, mSmallPaint);
        Rect rect = new Rect();
        String number = String.valueOf(mProgressNumber) + "%";
        mTextPaint.getTextBounds(number, 0, number.length(), rect);
        canvas.drawText(number, mLoadingWidth / 2 - rect.width()/2, mLoadingHeight / 2+rect.height()/2, mTextPaint);
    }
}
