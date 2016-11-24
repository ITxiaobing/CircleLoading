package com.demo.it.circleloading;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


public class LineLoadingView extends View {

    private float mLoadingWidth;
    private float mLoadingHeight;
    private Context mContext;
    private int mPadding = 40;
    private int mBgColor;
    private int mLoadingColor;
    private Paint mSmallPaint;
    private Paint mBigPaint;
    private Paint mTextPaint;
    private float mProgress;
    private float mPreProgress;
    private Path mPath;
    private int mProgressNumber;

    public LineLoadingView(Context context) {
        this(context, null);
    }

    public LineLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.loading_circle);
        mBgColor = typedArray.getColor(R.styleable.loading_circle_circle_background, getResources().getColor(R.color.colorAccent));
        mLoadingColor = typedArray.getColor(R.styleable.loading_circle_circle_loading, Color.GREEN);
        typedArray.recycle();
        initPaint(context);
    }

    private void initPaint(Context context) {
        mContext = context;
        mBigPaint = new Paint();
        mBigPaint.setColor(mBgColor);
        mBigPaint.setStyle(Paint.Style.FILL);
        mSmallPaint = new Paint();
        mSmallPaint.setStyle(Paint.Style.FILL);
        mSmallPaint.setColor(mLoadingColor);
        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(30);
        mPath = new Path();
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
            mLoadingWidth = PhoneInfoUtils.getScreenWidth(mContext) - mPadding;
        }
        //获取高度
        if (heightMode == MeasureSpec.EXACTLY) {
            mLoadingHeight = heightSize;
        } else {
            mLoadingHeight = 60;
        }
        mPreProgress = mLoadingWidth / 100f;
        setMeasuredDimension((int) mLoadingWidth, (int) mLoadingHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectFBg = new RectF(0, 40, mLoadingWidth, mLoadingHeight);
        canvas.drawRoundRect(rectFBg, 5, 5, mBigPaint);
        RectF rectFTop = new RectF(0, 40, mProgress, mLoadingHeight);
        canvas.drawRoundRect(rectFTop, 5, 5, mSmallPaint);
        mPath.reset();
        mPath.moveTo(mProgress, 40);
        mPath.lineTo(mProgress + 5, 30);
        mPath.lineTo(mProgress - 5, 30);
        mPath.close();
        canvas.drawPath(mPath, mSmallPaint);
        //画出百分比进度
        Rect rect = new Rect();
        String number = String.valueOf(mProgressNumber) + "%";
        mTextPaint.getTextBounds(number, 0, number.length(), rect);
        canvas.drawText(number, mProgress - rect.width() / 2, 30, mTextPaint);
    }

    public void setProgress(int index) {
        mProgressNumber = index;
        mProgress = index * mPreProgress;
        invalidate();
    }
}
