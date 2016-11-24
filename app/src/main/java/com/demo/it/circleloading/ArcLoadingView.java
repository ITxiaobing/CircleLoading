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
 * Created by admin on 2016/11/24.
 */

public class ArcLoadingView extends View {
    private int mArcBgColor;
    private int mArcLoadingColor;
    private Paint mBgPaint;
    private Paint mLoadingPaint;
    private int mLoadingWidth;
    private int mLoadingHeight;
    private float mCircleWidth = 20;
    private float mPreAngle;
    private float mProgress;
    private Paint mTextPaint;
    private int mProgressNumber;

    public ArcLoadingView(Context context) {
        this(context, null);
    }

    public ArcLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.loading_circle);
        mArcBgColor = typedArray.getColor(R.styleable.loading_circle_circle_background, getResources().getColor(R.color.colorAccent));
        mArcLoadingColor = typedArray.getColor(R.styleable.loading_circle_circle_loading, Color.GREEN);
        typedArray.recycle();
        initPaint();
    }

    private void initPaint() {
        mBgPaint = new Paint();
        mBgPaint.setColor(mArcBgColor);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setStrokeWidth(mCircleWidth);
        mLoadingPaint = new Paint();
        mLoadingPaint.setColor(mArcLoadingColor);
        mLoadingPaint.setStyle(Paint.Style.STROKE);
        mLoadingPaint.setStrokeWidth(mCircleWidth);
        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(40);
        Rect rect = new Rect();
        String number = String.valueOf(2) + "%";
        mTextPaint.getTextBounds(number, 0, number.length(), rect);
        Log.e("text", rect.width() + "===" + rect.height());
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
        //获取度数进度值
        mPreAngle = 360 / 100f;
        setMeasuredDimension(mLoadingWidth, mLoadingHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画底部背景
        RectF rectF = new RectF((int) mCircleWidth, (int) mCircleWidth, mLoadingWidth - (int) mCircleWidth, mLoadingHeight - (int) mCircleWidth);
        canvas.drawArc(rectF, 0, 360, false, mBgPaint);
        canvas.drawArc(rectF, 0, mProgress, false, mLoadingPaint);
        Rect rect = new Rect();
        String number = String.valueOf(mProgressNumber) + "%";
        mTextPaint.getTextBounds(number, 0, number.length(), rect);
        canvas.drawText(number, mLoadingWidth / 2-rect.width()/2, mLoadingHeight /2+rect.height()/2, mTextPaint);

    }

    public void setEndSweep(int endSweep) {
        mProgress = endSweep * mPreAngle;
        mProgressNumber = endSweep;
        invalidate();
    }
}
