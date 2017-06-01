package edu.sqchen.circleprogressview;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //自定义控件
    private CircleProgressView mProgressView;

    //已完成进度
    private int totalProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressView = (CircleProgressView) findViewById(R.id.circle_progress_view);
        //已完成90%
        totalProgress = 70;
        //创建一个子线程，在子线程中做耗时操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                //设置进度值从0开始变化
                mProgressView.setProgressSize(0);
                for(int i = 0; i < totalProgress; i++) {
                    mProgressView.setProgressSize(i + 1);
                    SystemClock.sleep(30);
                    //在子线程中刷新、重绘控件
                    mProgressView.postInvalidate();
                }
            }
        }).start();

    }
}
