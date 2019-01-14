package demo.dagger.com.myview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hyk on 2019/1/2.
 */

public class ControlView extends View {

    private Paint mPaint;
    private Paint textPaint;
    private static final int DEFAULT_RADIUS = 200;
    private int mRadius = DEFAULT_RADIUS;//画的大圆的半径
    private int middleRadius = 100;
    Point circleCenter = new Point();

    public ControlView(Context context) {
        this(context, null);


    }

    public ControlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置页面画笔及颜色
        mPaint = new Paint();
        textPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public ControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wideSize = MeasureSpec.getSize(widthMeasureSpec);
        int wideMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width, height;
        if (wideMode == MeasureSpec.EXACTLY) { //精确值 或matchParent
            width = wideSize;
        } else {
            width = mRadius * 2 + getPaddingLeft() + getPaddingRight();
            if (wideMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, wideSize);
            }

        }

        if (heightMode == MeasureSpec.EXACTLY) { //精确值 或matchParent
            height = heightSize;
        } else {
            height = mRadius * 2 + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }

        }
        setMeasuredDimension(width, height);
        mRadius = (int) (Math.min(width - getPaddingLeft() - getPaddingRight(),
                height - getPaddingTop() - getPaddingBottom()) * 1.0f / 2);

        circleCenter.x = mRadius + getPaddingLeft();
        circleCenter.y = mRadius + getPaddingTop();
    }

    @Override
    protected void onDraw(Canvas mCanvas) {
        super.onDraw(mCanvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        textPaint.setTextSize(mRadius / 5);
        RectF oval = new RectF(0, 0, mRadius * 2, mRadius * 2);
        mCanvas.drawArc(oval, -45, 90, true, mPaint);
        mCanvas.drawText("+", mRadius, (mRadius - middleRadius) / 2, textPaint);

        //textPaint.setColor(Color.GREEN);
        mPaint.setColor(Color.GREEN);
        mCanvas.drawArc(oval, 45, 90, true, mPaint);
        mCanvas.drawText("-", mRadius, mRadius + (mRadius + middleRadius) / 2, textPaint);
        mPaint.setColor(Color.BLUE);
        mCanvas.drawArc(oval, 135, 90, true, mPaint);
        mCanvas.drawText("*", (mRadius - middleRadius) / 2, mRadius, textPaint);
        mPaint.setColor(Color.BLACK);
        mCanvas.drawArc(oval, 225, 90, true, mPaint);
        mCanvas.drawText("÷", mRadius + (mRadius + middleRadius) / 2, mRadius, textPaint);

        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL);
        mCanvas.drawCircle(mRadius, mRadius, middleRadius, mPaint);
        textPaint.setTextSize(80);
        mCanvas.drawText("OK", mRadius, mRadius + middleRadius / 4, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            //获取在当前控件中的坐标
            double x = (double) event.getX();
            double y = (double) event.getY();
            //如果点击的位置在圆内
            if ((x - circleCenter.x) * (x - circleCenter.x) + (y - circleCenter.y) * (y - circleCenter.y) <= mRadius * mRadius) {
                //如果在小圆内
                if ((x - circleCenter.x) * (x - circleCenter.x) + (y - circleCenter.y) * (y - circleCenter.y) <= middleRadius * middleRadius) {
                    if (listener != null) {
                        listener.onOKClick();
                        return true;
                    }
                } else {
                    //判断点所在的位置

                    if (x <= mRadius && y <= mRadius) {
                        if (listener != null) {
                            if (x > y) {
                                listener.onAddClick();
                            } else {
                                listener.onRideClick();
                            }
                        }
                    }
                    // 第二象限
                    if (x >= mRadius && y <= mRadius) {
                        if (listener != null) {
                            if (x < 2 * mRadius - y) {
                                listener.onAddClick();
                            } else {
                                listener.onExceptClick();
                            }
                        }
                    }
                    // 第三象限
                    if (x >= mRadius && y >= mRadius) {
                        if (listener != null) {
                            if (x > y) {
                                listener.onExceptClick();
                            } else {
                                listener.onReduceClick();
                            }
                        }
                    }
                    // 第四象限
                    if (x <= mRadius && y >= mRadius) {
                        if (listener != null) {
                            if (x < 2 * mRadius - y) {
                                listener.onRideClick();
                            } else {
                                listener.onReduceClick();
                            }
                        }
                    }

                }
            }
            return true;
        }

        return true;
    }


    private OnControlViewClickListener listener;

    public void setOnControlViewClickListener(OnControlViewClickListener clickListener) {
        this.listener = clickListener;
    }

    public interface OnControlViewClickListener {
        void onAddClick();

        void onReduceClick();

        void onRideClick();//乘

        void onExceptClick();//除

        void onOKClick();
    }
}
