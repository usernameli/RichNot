package com.cvte.note.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.cvte.note.acvivity.BaseActivity;
import com.cvte.note.model.INoteModel;
import com.cvte.note.utils.DrawUtils;

import java.io.InputStream;
import java.util.Stack;

public class RichImageView extends View {
    private Paint paint = null; //
    private Bitmap originalBitmap = null;//原始图
    private Bitmap new1Bitmap = null;
    private Stack<String> doStatck;//操作栈
    private Canvas mCanvas;
    private float clickX = 0;
    private float clickY = 0;
    private float startX = 0;
    private float startY = 0;
    private INoteModel Realxiangpi;
    private boolean isXiangpi = false;
    private boolean isClear = false;
    private int color = Color.RED;//默认画笔颜色
    private float strokeWidth = 20f;//默认画笔宽度
    Path mPath;
    private Context context;
    private int r;

    public RichImageView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public RichImageView(Context context, AttributeSet atts) {
        this(context, atts, 0);
        // TODO Auto-generated constructor stub
    }

    @SuppressWarnings("static-access")
    public RichImageView(Context context, AttributeSet atts, int defStyle) {
        super(context, atts, defStyle);
        // TODO Auto-generated constructor stub
        this.context = context;
        ColorDrawable drawable = new ColorDrawable(Color.BLACK);
        Bitmap bitmaps = Bitmap.createBitmap(DrawUtils.getInstance().getWidth(), DrawUtils.getInstance().getHeigth(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmaps);
        drawable.draw(canvas);
        doStatck = new Stack<>();
        //  originalBitmap = BitmapFactory.decodeResource(899,600,getResources(), R.drawable.default_bgs).copy(Bitmap.Config.ARGB_8888, true);//白色的画板
        originalBitmap = bitmaps;
        new1Bitmap = originalBitmap.createBitmap(originalBitmap);
        mPath = new Path();
    }
    public void setXiangpi(INoteModel xiangpi){
        this.Realxiangpi = xiangpi;
    }

    //清楚
    @SuppressWarnings("static-access")
    public void clear() {
        isClear = true;
        if (doStatck.isEmpty()) {
            ((BaseActivity) context).showMessage(false, "不能再后退了呢");
        } else {
            InputStream inputStream = DrawUtils.getInstance().getBitmapInputStreamFromSDCard(doStatck.pop());
            Bitmap rawBitmap2 = BitmapFactory.decodeStream(inputStream).copy(Bitmap.Config.ARGB_8888, true);
            new1Bitmap = originalBitmap.createBitmap(rawBitmap2);
            invalidate();//重置
        }
    }

    public void setStrokeWidth(float width) {
        this.strokeWidth = width;
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        this.mCanvas = canvas;
        if (DrawUtils.getInstance().getSelectStatue() == INoteModel.YUANXING) {
            canvas.drawBitmap(writerCircle(new1Bitmap), 0, 0, null);
        } else {
            canvas.drawBitmap(writer(new1Bitmap), 0, 0, null);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (DrawUtils.getInstance().getSelectStatue()) {
            case INoteModel.BI:
                setColor(DrawUtils.getInstance().getPaintColor());
                PaintPen(event);
                break;
            case INoteModel.XIANGPI:
                setColor(Color.WHITE);
                PaintPen(event);
                break;
            case INoteModel.XIAN:
                setColor(DrawUtils.getInstance().getPaintColor());
                PaintLine(event);
                break;
            case INoteModel.YUANXING:
                setColor(DrawUtils.getInstance().getPaintColor());
                PaintCircle(event);
                break;
            case INoteModel.JUXING:
                setColor(DrawUtils.getInstance().getPaintColor());
                PaintJuxing(event);
                break;
        }

        return true;
    }

    private void PaintJuxing(MotionEvent event) {
        clickX = event.getX();
        clickY = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = clickX;
            startY = clickY;
            mPath.reset();
            mPath.moveTo(clickX, clickY);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //  float dx = Math.abs(clickX - startX);
            //   float dy = Math.abs(clickY - startY);
            float dx = event.getX();
            float dy = event.getY();
//   if(dx>=3||dy>=3){
            //设置贝塞尔曲线的操作点为起点和终点的一半
            // float cX = (clickX + startX);
            // float cY = (clickY + startY);
            mPath.lineTo(dx, startY);
            mPath.lineTo(dx, dy);
            mPath.lineTo(startX, dy);
            mPath.lineTo(startX, startY);
            invalidate();
            //根据Path进行绘制，绘制五角星
            //canvas.drawPath(path2, paint);
            String fileName = System.currentTimeMillis() + ".jpg";
            DrawUtils.getInstance().compressAndSaveBitmapToSDCard(new1Bitmap, fileName, 100);
            doStatck.push(fileName);
        }

    }

    private void PaintCircle(MotionEvent event) {
        clickX = event.getX();
        clickY = event.getY();
        //   initPaint();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = clickX;
            startY = clickY;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            float dx = Math.abs(event.getX() - startX);
            float dy = Math.abs(event.getY() - startY);
            r = (int) Math.sqrt(dx * dx + dy * dy);
            //    mCanvas.drawBitmap(writerCircle(new1Bitmap), 0, 0, null);
            invalidate();
            String fileName = System.currentTimeMillis() + ".jpg";
            DrawUtils.getInstance().compressAndSaveBitmapToSDCard(new1Bitmap, fileName, 100);
            doStatck.push(fileName);
        }

    }


    private void PaintLine(MotionEvent event) {
        clickX = event.getX();
        clickY = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = clickX;
            startY = clickY;
            mPath.reset();
            mPath.moveTo(clickX, clickY);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //   float dx = Math.abs(clickX - startX);
            //   float dy = Math.abs(clickY - startY);
//   if(dx>=3||dy>=3){
            //设置贝塞尔曲线的操作点为起点和终点的一半
            // float cX = (clickX + startX);
            // float cY = (clickY + startY);
            mPath.quadTo(startX, startY, event.getX(), event.getY());
            invalidate();
            String fileName = System.currentTimeMillis() + ".jpg";
            DrawUtils.getInstance().compressAndSaveBitmapToSDCard(new1Bitmap, fileName, 100);
            doStatck.push(fileName);
        }

    }

    private void PaintPen(MotionEvent event) {
        clickX = event.getX();
        clickY = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //手指点下屏幕时触发
            startX = clickX;
            startY = clickY;
            mPath.reset();
            mPath.moveTo(clickX, clickY);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //手指移动时触发
            Realxiangpi.SetXiangPiLocation(clickX,clickY,startX,startX);
            float dx = Math.abs(clickX - startX);
            float dy = Math.abs(clickY - startY);
//   if(dx>=3||dy>=3){
            //设置贝塞尔曲线的操作点为起点和终点的一半
            float cX = (clickX + startX) / 2;
            float cY = (clickY + startY) / 2;
            mPath.quadTo(startX, startY, cX, cY);

            startX = clickX;
            startY = clickY;
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            String fileName = System.currentTimeMillis() + ".jpg";
            DrawUtils.getInstance().compressAndSaveBitmapToSDCard(new1Bitmap, fileName, 100);
            doStatck.push(fileName);
        }
        invalidate();
    }

    /**
     * @param @param  pic
     * @param @return 设定文件
     * @return Bitmap 返回类型
     * @throws
     * @Title: writer
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public Bitmap writer(Bitmap pic) {
        initPaint();

        Canvas canvas = null;
        if (isClear) {
            canvas = new Canvas(new1Bitmap);
            isClear = false;
        } else {
            canvas = new Canvas(pic);
        }
        canvas.drawPath(mPath, paint);
        if (isClear) {

            return new1Bitmap;
        }
        // doStatck.push(DrawUtils.copy(pic));
        return pic;
    }


    public Bitmap writerCircle(Bitmap pic) {
        initPaint();

        Canvas canvas = null;
        if (isClear) {
            canvas = new Canvas(new1Bitmap);
        } else {
            canvas = new Canvas(pic);
        }

        //canvas.drawLine(startX, startY, clickX, clickY, paint);//画线
        canvas.drawCircle(startX, startY, r, paint);
        if (isClear) {
            //    doStatck.push(new1Bitmap);
            return new1Bitmap;
        }
        //  doStatck.push(pic);
        return pic;
    }

    private void initPaint() {
        if (paint == null)
            paint = new Paint();//初始化画笔

        paint.setStyle(Style.STROKE);//设置为画线

        paint.setAntiAlias(true);//设置画笔抗锯齿

        paint.setColor(color);//设置画笔颜色

        paint.setStrokeWidth(strokeWidth);//设置画笔宽度
    }


    /**
     * @param @param color 设定文件
     * @return void 返回类型
     * @throws
     * @Title: setColor
     * @Description: TODO(设置画笔颜色)
     */
    public void setColor(int color) {

        this.color = color;
        initPaint();
    }

    public Bitmap getPaint() {
        return new1Bitmap;
    }
}
