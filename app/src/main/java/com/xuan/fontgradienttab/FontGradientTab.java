package com.xuan.fontgradienttab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

/**
 * com.xuan.fontgradienttab
 *
 * @author by xuan on 2018/5/17
 * @version [版本号, 2018/5/17]
 * @update by xuan on 2018/5/17
 * @descript
 */
public class FontGradientTab extends android.support.v7.widget.AppCompatTextView {
    private int defaultColor= ContextCompat.getColor(getContext(),R.color.colorAccent);
    private int gradientColor= ContextCompat.getColor(getContext(),R.color.colorPrimary);

    private Paint defaultPaint,gradientPaint;

    private float gradientPercentage=0.0f;

    private ORIENTATION orientation;

    public void setOrientation(ORIENTATION orientation){
        this.orientation=orientation;
    }

    public void setGradientPercentage(float gradientPercentage){
        this.gradientPercentage=gradientPercentage;
    }

    public void startPerform(float value) {
        this.gradientPercentage=value;
        invalidate();
    }

    public void setGradientColor(int gradientColor) {
        this.gradientColor=gradientColor;
        gradientPaint.setColor(gradientColor);
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultColor=defaultColor;
        defaultPaint.setColor(defaultColor);
    }

    public void setDefaultFontSize(int fontSize) {
        setTextSize(fontSize);
        defaultPaint.setTextSize(getTextSize());
    }

    public void setGradientFontSize(int fontSize) {
        setTextSize(fontSize);
        gradientPaint.setTextSize(getTextSize());
    }


    public enum ORIENTATION{
        ORIENTATION_LEFT/*从右边向左滑*/,ORIENTATION_RIGHT/*从左边向右滑*/
    }

    private String text;

    public FontGradientTab(Context context) {
        this(context,null);
    }

    public FontGradientTab(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FontGradientTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context,attrs);
        initPaint();
    }

    @SuppressLint("ResourceAsColor")
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.FontGradientTab);

        defaultColor=typedArray.getColor(R.styleable.FontGradientTab_defaultColor,defaultColor);
        gradientColor=typedArray.getColor(R.styleable.FontGradientTab_gradientColor,gradientColor);

        typedArray.recycle();
    }


    /**
     * 设置画笔
     */
    @SuppressLint("ResourceAsColor")
    private void initPaint() {

        defaultPaint = new Paint();
        defaultPaint.setAntiAlias(true);
        defaultPaint.setColor(defaultColor);
        // 防抖动
        defaultPaint.setDither(true);
        // 使用TextView自带的属性 如果使用自定义的属性可能会导致宽度无法正常显示 因为本身TextView已经setTextSize()计算好宽度
        defaultPaint.setTextSize(getTextSize());

        gradientPaint = new Paint();
        gradientPaint.setAntiAlias(true);
        gradientPaint.setColor(gradientColor);
        // 防抖动
        gradientPaint.setDither(true);
        // 使用TextView自带的属性 如果使用自定义的属性可能会导致宽度无法正常显示 因为本身TextView已经setTextSize()计算好宽度
        gradientPaint.setTextSize(getTextSize());



    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas); 去掉系统画的TextView方法
//
//        //计算颜色变化的实际值
//        int gradientProgress= (int) (gradientPercentage*getWidth());
//
//        String text=getText().toString();
//        //计算宽度 字体的长度有关
//        Rect bounds=new Rect();//矩形
//        //给矩形设置边界
//        defaultPaint.getTextBounds(text,0,text.length(),bounds);
//        //拿到字体高度
//        Paint.FontMetrics fontMetrics = defaultPaint.getFontMetrics();
//        int dy= (int) ((fontMetrics.bottom-fontMetrics.top)/2-fontMetrics.bottom);
//        int baseLine=getHeight()/2+dy;
//
//        /*
//        canvas.save(); canvas.restore(); 保存 恢复
//        为了做到 2次画文字都是 完整的 hello world
//        一个画左边hello裁剪右边 一个画右边world裁剪左边
//        */
//        canvas.save();
//        //裁剪右边 留下左边 hello world = hello
//        canvas.clipRect(0,0,gradientProgress,getHeight());
//        //画左边
//        canvas.drawText(text,getPaddingLeft(),baseLine,defaultPaint);
//        canvas.restore();
//
//        canvas.save();
//        //裁剪左边 留下右边 hello world =       world
//        canvas.clipRect(gradientProgress,0,getWidth(),getHeight());
//        //画右边
//        canvas.drawText(text,getPaddingLeft(),baseLine,gradientPaint);
//        canvas.restore();

        text = getText().toString();

        //计算颜色变化的实际值
        int gradientProgress = (int) (gradientPercentage*getWidth());

        //计算宽度 字体的长度有关
        Rect bounds=new Rect();//矩形
        //给矩形设置边界
        defaultPaint.getTextBounds(text,0,text.length(),bounds);
        //拿到字体高度
        Paint.FontMetrics fontMetrics = defaultPaint.getFontMetrics();
        int dy= (int) ((fontMetrics.bottom-fontMetrics.top)/2-fontMetrics.bottom);
        int baseLine=getHeight()/2+dy;

        /*
        canvas.save(); canvas.restore(); 保存 恢复
        为了做到 2次画文字都是 完整的 hello world
        一个画左边hello裁剪右边 一个画右边world裁剪左边
        */
        if(orientation==ORIENTATION.ORIENTATION_RIGHT){//从左到右
            //裁剪右边 留下左边 变色 hello world = hello
            drawClipRecText(canvas,gradientPaint,0,gradientProgress,baseLine);

            //裁剪左边 留下右边 原色 hello world =       world
            drawClipRecText(canvas,defaultPaint,gradientProgress,getWidth(),baseLine);
        } else if (orientation == ORIENTATION.ORIENTATION_LEFT) {//从右到左
            //裁剪左边 留下右边 变色 hello world =       world
            drawClipRecText(canvas,gradientPaint,getWidth()-gradientProgress,getWidth(),baseLine);

            //裁剪右边 留下左边 原色 hello world = hello
            drawClipRecText(canvas,defaultPaint,0,getWidth()-gradientProgress,baseLine);
        }else{
            //不变色 从左到右渐显
            drawClipRecText(canvas,defaultPaint,0,gradientProgress,baseLine);
        }

    }

    private void drawClipRecText(Canvas canvas,Paint paint,int left,int right,int baseLine){
        canvas.save();
        //切割
        canvas.clipRect(left,0,right,getHeight());
        canvas.drawText(text,getPaddingLeft(),baseLine,paint);
        canvas.restore();
    }



}
