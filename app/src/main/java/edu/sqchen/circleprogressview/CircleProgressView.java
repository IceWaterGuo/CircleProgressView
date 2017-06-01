package edu.sqchen.circleprogressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/6/1.
 */

public class CircleProgressView extends View {

    //圆环的宽度
    private int ringWidth;

    //圆环填充颜色
    private int ringColor;

    //进度条填充颜色
    private int progressColor;

    //文字大小
    private int textSize;

    //文字颜色
    private int textColor;

    //画笔
    private Paint mPaint;

    //当前进度值
    private int progressSize;

    //控件本身的宽度
    private int mWidth;

    /**
     *
     * @param context
     */
    public CircleProgressView(Context context) {
        this(context,null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //获取属性值
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.CircleProgressView);
        //第二个参数是当我们没有给这个控件对应的属性赋值时采用的默认值
        ringWidth = (int) ta.getDimension(R.styleable.CircleProgressView_ringWidth,20);
        ringColor = ta.getColor(R.styleable.CircleProgressView_ringColor, Color.GRAY);
        progressColor = ta.getColor(R.styleable.CircleProgressView_progressColor,Color.BLUE);
        textSize = (int) ta.getDimension(R.styleable.CircleProgressView_textSize,60);
        textColor = ta.getColor(R.styleable.CircleProgressView_textColor,Color.BLACK);
        progressSize = ta.getInteger(R.styleable.CircleProgressView_progressSize,60);
        //回收TypedArray
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
        mWidth = getMeasuredWidth();
    }

    /**
     * 对宽度进行判断
     * @param widthMeasureSpec
     * @return
     */
    private int measureWidth(int widthMeasureSpec) {
        int resultWidth = 0;
        //获取设置的测量模式和大小
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        //如果是精确值模式，则宽度等于用户设置的宽度
        if(specMode == MeasureSpec.EXACTLY) {
            resultWidth = specSize;
        } else {
            //否则，设置默认值为400个像素，如果是最大值模式，则取用户设置的值和默认值中较小的一个
            resultWidth = 400;
            if(specMode == MeasureSpec.AT_MOST) {
                resultWidth = Math.min(resultWidth,specSize);
            }
        }
        return resultWidth;
    }

    /**
     * 对高度进行判断
     * @param heightMeasureSpec
     * @return
     */
    private int measureHeight(int heightMeasureSpec) {
        int resultHeight = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        if(specMode == MeasureSpec.EXACTLY) {
            resultHeight = specSize;
        } else {
            resultHeight = 400;
            if(specMode == MeasureSpec.AT_MOST) {
                resultHeight = Math.min(resultHeight,specSize);
            }
        }
        return resultHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取圆心坐标及半径
        float circleX = mWidth / 2;
        float circleY = mWidth / 2;
        float radius = mWidth / 2 - ringWidth / 2;
        //绘制圆环
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(ringWidth);
        mPaint.setColor(ringColor);
        canvas.drawCircle(circleX,circleY,radius,mPaint);

        //绘制圆弧，填充进度
        //RectF用于构造一个矩形区域，作为传入的椭圆对象
        RectF oval = new RectF(ringWidth / 2,ringWidth / 2,mWidth - ringWidth / 2,mWidth - ringWidth / 2);
        mPaint.setColor(progressColor);
        //drawArc()方法参数：
        //1、圆弧所在的椭圆对象
        //2、圆弧的起始角度
        //3、圆弧的角度
        //4、是否显示半径连线
        //5、绘制时采用的画笔
        canvas.drawArc(oval,0,progressSize * 360 / 100,false,mPaint);

        //绘制文本
        String progressText = progressSize + "%";
        //设置画笔颜色和文字大小
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        //重置画笔宽度，因为前面绘制圆环和圆弧时用到的画笔宽度不一样
        mPaint.setStrokeWidth(0);
        //构造一个矩形区域，用于放置文本
        Rect bound = new Rect();
        mPaint.getTextBounds(progressText,0,progressText.length(),bound);
        canvas.drawText(progressText,mWidth / 2 - bound.width() / 2,mWidth / 2 + bound.height() / 2,mPaint);
    }

    /**
     * 获取进度值
     * @return
     */
    public int getProgressSize() {
        return progressSize;
    }

    /**
     * 设置进度值
     * @param progressSize
     */
    public void setProgressSize(int progressSize) {
        this.progressSize = progressSize;
    }
}
