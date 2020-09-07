package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2016/1/19.
 */
public class CustomMonitorMenu extends View {
    public final int R1 = 100;
    public final int R2 = 8;

    //每一格 cos y的差距
    public float width;
    public float height;
    public float screenWidth;
    public float screenHeight;

    public int startAngle = 235;  //新的角度 开始时30
    public float initialAngle = 0.1f;  //初始弧度30
    public int sweepAngle = 70;  //角度

    public double newX;
    public double newY;   //给小圆球提供位置 空间移动到这个位置上面去

    public Paint mPaint2;
    public Paint mPaint3;
    public Paint mPaint4;
    public boolean flag = false;
    public Paint mPaint;
    public Context mContext;
    public String Sangle = "0";
    public String Eangle = "120";

    /**
     * 一定一个接口
     */
    public interface ICoallBack {
        void getAngle(int angle);
    }

    /**
     * 初始化接口变量
     */
    ICoallBack icallBack = null;

    /**
     * 自定义控件的自定义事件
     */
    public void setEvent(ICoallBack iBack) {
        icallBack = iBack;
    }


    public CustomMonitorMenu(Context context) {
        super(context);
        this.mContext = context;
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        initView();
//      setWillNotDraw(false);
    }

    public CustomMonitorMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        initView();
//      setWillNotDraw(false);

    }

    public void setindex(int initAng, int viewW) {
        int viewX = (int) (viewW - screenWidth);
        if (viewX != 0) {
            initialAngle = Math.abs(initAng) * sweepAngle / viewX;
        }else {
            viewW = 1;
        }

        if (initialAngle >= 0 && initialAngle <= 70) {
            initialAngle = 35 - initialAngle;
        }
       // Log.e("hgz------->", " initAng = " + Math.abs(initAng) + " initialAngle = " + initialAngle + " viewX = " + viewX);
        invalidate();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2.0f);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG); // 抗锯齿
        mPaint2.setColor(Color.BLACK);
        mPaint2.setStrokeWidth(2.0f);
        mPaint2.setStrokeJoin(Paint.Join.ROUND); // 让画的线圆滑
        mPaint2.setStrokeCap(Paint.Cap.ROUND);

        mPaint3 = new Paint();
        mPaint3.setColor(Color.BLACK);
        mPaint3.setStrokeWidth(2.0f);
        mPaint3.setStyle(Paint.Style.STROKE);
        mPaint3.setAntiAlias(true);

        mPaint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint4.setStrokeWidth(1);
        mPaint4.setTextSize(16);
        mPaint4.setStyle(Paint.Style.STROKE);
        mPaint4.setColor(Color.BLACK);
// 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
//      mPaint4.setTextAlign(Paint.Align.CENTER);

//        根据屏幕的宽高确定圆心的坐标
        DisplayMetrics metric = new DisplayMetrics();
        ((MainActivity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
//		屏幕的宽高
        
        screenWidth = metric.widthPixels;
        screenHeight = metric.heightPixels;
//       圆心位置坐标
        
        width = metric.widthPixels / 2.0f;
        height = metric.heightPixels / 8.0f;

       // Log.e("aaa--->圆心的坐标：", width + "---" + height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF oval1 = new RectF(width - 100, height - 100, width + 100, height + 100);
        canvas.drawArc(oval1, startAngle, sweepAngle, false, mPaint);//小弧形
        //Log.e("ooo--->", "圆环的圆心的坐标 ： width " + width + "   height " + height);


//        getNewLocation(initialAngle); //根据判断 来移动小球 获得新的位置

        canvas.drawLine(width, (height - R1), width, (height - R1) + 10, mPaint3);


        canvas.drawText(Sangle, (float) (width - Math.sin(35 * Math.PI / 180) * R1) - 20, (float) (height - Math.cos(35 * Math.PI / 180) * R1) + 10, mPaint4);
        canvas.drawText(Eangle, (float) (width + Math.sin(35 * Math.PI / 180) * R1) + 8, (float) (height - Math.cos(35 * Math.PI / 180) * R1) + 10, mPaint4);

//        canvas.drawCircle((float) newX, (float) newY, R2, mPaint2);


        canvas.drawLine((float) (width - Math.sin(35 * Math.PI / 180) * R1), (float) (height - Math.cos(35 * Math.PI / 180) * R1),
                (float) (width - Math.sin(35 * Math.PI / 180) * R1) - 5.0f, (float) (height - Math.cos(35 * Math.PI / 180) * R1) - 6.0f, mPaint3);
        canvas.drawLine((float) (width - Math.sin(35 * Math.PI / 180) * R1), (float) (height - Math.cos(35 * Math.PI / 180) * R1),
                (float) (width - Math.sin(35 * Math.PI / 180) * R1) + 5.0f, (float) (height - Math.cos(35 * Math.PI / 180) * R1) + 6.0f, mPaint3);

        canvas.drawLine((float) (width + Math.sin(35 * Math.PI / 180) * R1), (float) (height - Math.cos(35 * Math.PI / 180) * R1),
                (float) (width + Math.sin(35 * Math.PI / 180) * R1) + 5.0f, (float) (height - Math.cos(35 * Math.PI / 180) * R1) - 6.0f, mPaint3);
        canvas.drawLine((float) (width + Math.sin(35 * Math.PI / 180) * R1), (float) (height - Math.cos(35 * Math.PI / 180) * R1),
                (float) (width + Math.sin(35 * Math.PI / 180) * R1) - 5.0f, (float) (height - Math.cos(35 * Math.PI / 180) * R1) + 6.0f, mPaint3);

//        canvas.drawCircle((float) (width - Math.sin(35 * Math.PI / 180) * R1), (float) (height - Math.cos(35 * Math.PI / 180) * R1), R2, mPaint2);

        canvas.drawCircle((float) (width - Math.sin(initialAngle * Math.PI / 180) * R1), (float) (height - Math.cos(initialAngle * Math.PI / 180) * R1), R2, mPaint2);

       // Log.e("hgz", "小球的坐标：" + (float) (width - Math.sin(initialAngle * Math.PI / 180) * R1) + "     " + (float) (height - Math.cos(initialAngle * Math.PI / 180) * R1));
//        canvas.drawCircle((float) (width + Math.sin(35 * Math.PI / 180) * R1), (float) (height - Math.cos(35 * Math.PI / 180) * R1), R2, mPaint2);
    }

}
