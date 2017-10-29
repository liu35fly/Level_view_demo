package com.example.levelviewlibray;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fly on 2017/10/28.
 */

public class LevelViewBaseView extends View {

    private Paint paintRect, paintLine, paintText, paintIcon, paintCount, paintNumber;
    //是否被点击
    private boolean isClik = false;
    //行数
    private int number = 0;

    private String name = "";
    //分数
    private String count = "";

    //绘制的文字长度
    private int TEXT_SIZE = 10;
    //
    //横向线条长度
    private int lineSizeHORIZONTAL = 45;
    //控件的宽高
    private int HEIGHT = 250;
    //横向展示时宽为800
    private int WIDTH = 300;
    //文字大小
    private float textSize = 25F;
    //得分text的字体大小
    private float countSize = 50F;
    //抽签序号字体大小
    private float numberSize = 50F;
    //矩形的高，是高度的一半
    private int rectHeight = 182;
    //矩形的宽度
    private int rectWidth = 500 + 100;
    //线条颜色
    private int colorLine = Color.rgb(218, 158, 83);
    //矩形边框颜色
    private int colorRect = Color.rgb(218, 158, 83);
    //字体颜色
    private int colorFont = Color.rgb(218, 158, 83);
    //分数颜色
    private int colorCount = Color.rgb(204, 204, 204);
    //抽签时序号颜色
    private int colorNumber = Color.rgb(25, 25, 25);
    //需要绘制的图片区域、绘制到画布的区域
    private Rect mSrcRect, mDestRect;
    private Bitmap icon;

    private LevelViewBaseData data;
    //抽签编号
    private String iconNumber = "?";

    private String iconPath = "";

    private int maxRow = -1;

    private String levelBaseViewTextColor, levelBaseViewLineColor, levelBaseViewCountColor;

    public LevelViewBaseView(Context context) {
        this(context, null);
    }

    public LevelViewBaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LevelViewBaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
//        icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.level_view_default);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(WIDTH, HEIGHT);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (name.length() < TEXT_SIZE) {
            TEXT_SIZE = name.length();
        }
        drawCircle(canvas);
    }

    public void setLevelBaseViewColor(String levelBaseViewTextColor, String levelBaseViewLineColor, String levelBaseViewCountColor) {
        if (levelBaseViewTextColor == null || levelBaseViewTextColor.isEmpty()) {

        } else {
            colorFont = Color.parseColor(levelBaseViewTextColor);
            if (paintText != null) {
                paintText.setColor(colorFont);
            }
        }
        if (levelBaseViewLineColor == null || levelBaseViewLineColor.isEmpty()) {
        } else {
            colorLine = Color.parseColor(levelBaseViewLineColor);
            colorRect = Color.parseColor(levelBaseViewLineColor);
            if (paintRect != null) {
                paintRect.setColor(colorRect);
            }
            if (paintLine != null) {
                paintLine.setColor(colorLine);
            }
        }
        if (levelBaseViewCountColor == null || levelBaseViewCountColor.isEmpty()) {
        } else {
            colorCount = Color.parseColor(levelBaseViewCountColor);
            if (paintCount != null) {
                paintCount.setColor(colorCount);
            }
        }
    }

    /**
     * 表示在第几列
     * 取值范围 大于等于0 从0开始
     *
     * @param number
     */
    public void setNumber(@Nullable int number) {
        if (number < 0) {
            return;
        }
        this.number = number;
    }

    /**
     * 设置名称
     *
     * @param s
     */
    public void setName(@Nullable String s) {
        if (s == null) {
            return;
        }
        if (s.isEmpty()) {
            return;
        }
        name = s;

    }

    public void setCount(@Nullable String count) {
        this.count = count;
    }

    public void setIcon(@Nullable Bitmap icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getCount() {
        return count;
    }

    public void setData(LevelViewBaseData data) {
        this.data = data;
    }

    public LevelViewBaseData getData() {
        return data;
    }

    /**
     * 抽签时的编号
     *
     * @param iconNumber
     */
    public void setIconNumber(String iconNumber) {
        this.iconNumber = iconNumber;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    /**
     * 一共要画几列(从0开始计)
     *
     * @param maxRow
     */
    public void setMaxRow(int maxRow) {
        this.maxRow = maxRow;
    }

    private void initPaint() {
        paintRect = new Paint();
        paintRect.setColor(colorRect);
        paintRect.setAntiAlias(true);
        paintRect.setStrokeWidth(3);
        paintRect.setStyle(Paint.Style.STROKE);

        paintLine = new Paint();
        paintLine.setColor(colorLine);
        paintLine.setAntiAlias(true);
        paintLine.setStrokeWidth(3);

        paintText = new Paint();
        paintText.setTextSize(textSize);
        paintText.setColor(colorFont);
        paintText.setFakeBoldText(true);

        paintCount = new Paint();
        paintCount.setTextSize(countSize);
        paintCount.setColor(colorCount);
        paintCount.setFakeBoldText(true);

        paintIcon = new Paint();
        paintIcon.setAntiAlias(true);

        paintNumber = new Paint();
        paintNumber.setTextSize(numberSize);
        paintNumber.setColor(colorNumber);
        paintNumber.setFakeBoldText(true);
    }

    /**
     * 画纵向展示的圆形图
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        float radius = 75F;
        int iconWidth = 135;
        int distanceFromIconToText = 45;
        int distanceFromLineToCircle = 15;
        float circleCenterPointX = lineSizeHORIZONTAL + distanceFromLineToCircle + radius;
        float circleCentrePonitY = (float) (HEIGHT * (int) Math.pow(2, number) / 2);

        //画空圈
        canvas.drawCircle(circleCenterPointX, circleCentrePonitY, radius, paintRect);

        float textWidth = paintText.measureText(name, 0, TEXT_SIZE);
        //名称
        canvas.drawText(name, 0, TEXT_SIZE, circleCenterPointX - textWidth / 2, HEIGHT * (int) Math.pow(2, number) / 2 + radius + distanceFromIconToText, paintText);
        //比分
        canvas.drawText(count, circleCenterPointX + radius + 20, HEIGHT * (int) Math.pow(2, number) / 2 + 20, paintCount);

        //没有达到最大列时，画出右边的横线
        if (number != maxRow) {
            canvas.drawLine(lineSizeHORIZONTAL + radius * 2 + distanceFromLineToCircle * 2 + 70, HEIGHT * (int) Math.pow(2, number) / 2, lineSizeHORIZONTAL + radius * 2 + distanceFromLineToCircle * 2 + lineSizeHORIZONTAL + 50, HEIGHT * (int) Math.pow(2, number) / 2, paintLine);
        }

        if (icon != null) {
            //画中间的图标
            Bitmap cache = BitmapToRoundUtils.toRoundBitmap(icon);
            mSrcRect = new Rect(0, 0, icon.getWidth(), icon.getHeight());
            mDestRect = new Rect((int) (circleCenterPointX) - iconWidth / 2, (int) (circleCentrePonitY) - iconWidth / 2, (int) (circleCenterPointX) + iconWidth / 2, (int) (circleCentrePonitY) + iconWidth / 2);
            canvas.drawBitmap(cache, mSrcRect, mDestRect, paintIcon);
            mDestRect = null;
            mSrcRect = null;
            cache.recycle();
        }

        //画默认的？ 或者 当抽签时的数字
        if (iconPath == null || iconPath.isEmpty()) {
            if (name == null || name.isEmpty()) {
                float iconNumberWidth = paintNumber.measureText(iconNumber, 0, iconNumber.length());
                canvas.drawText(iconNumber, (int) (circleCenterPointX) - iconNumberWidth / 2, (int) (circleCentrePonitY) + 20, paintNumber);
            }
        }

        //不是第一列时画出左边的横线
        if (number != 0) {
            canvas.drawLine(0, HEIGHT * (int) Math.pow(2, number - 1) / 2, 0, HEIGHT * (int) Math.pow(2, number - 1) / 2 + HEIGHT * (int) Math.pow(2, number) / 2, paintLine);
            canvas.drawLine(0, HEIGHT * (int) Math.pow(2, number) / 2, lineSizeHORIZONTAL, HEIGHT * (int) Math.pow(2, number) / 2, paintLine);

        }
    }

    public void onRefresh() {
        invalidate();
    }


}
