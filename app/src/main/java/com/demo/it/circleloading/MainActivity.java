package com.demo.it.circleloading;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private int index;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            index++;
            if (index > 100) {
                index = 0;
            }
            mLoadingCircle.setRadius(index);
        }
    };
    private LoadingCircleView mLoadingCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取
        mLoadingCircle = (LoadingCircleView) findViewById(R.id.loading_circle);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    SystemClock.sleep(300);
                    mHandler.sendEmptyMessage(0);
                }
            }
        }).start();
    }
}
