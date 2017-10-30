package com.example.levelviewlibray;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

/**
 * Created by fly on 2017/10/28.
 */

public class LevelViewBaseGroup extends ViewGroup {

    private Context context;
    //当前有多少列 例：colum=4 表示当前为0,1,2,3这4列
    private int colum = 0;
    //当前列数
    private int lineNumber = 0;
    //第一列有多少个
    private int totalFirstLine = 0;
    //第一列是否是2的幂
    private boolean isFormate = false;
    //子视图的宽高 通过子视图得到
    //子视图横向展示时宽800
    public final static int CHILD_VIEW_WIDTH = 320;
    public final static int CHILD_VIEW_HEIGHT = 250;
    //要求画出几列
    private int needLineNumber = 0;
    //数据错误时的监听
    private GroupListen listener;
    //有没有季军争夺的集合
    private boolean isTwoList = false;

    private String levelBaseViewTextColor, levelBaseViewLineColor, levelBaseViewCountColor;

    private int iconBgWhite, iconBgTag;

    public LevelViewBaseGroup(Context context) {
        this(context, null);

    }

    public LevelViewBaseGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LevelViewBaseGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
//        setBackgroundColor(Color.rgb(35, 35, 35));
    }

    public void paseBaseColors(String levelBaseViewTextColor, String levelBaseViewLineColor, String levelBaseViewCountColor, int iconBgWhite, int iconBgTag) {
        this.levelBaseViewTextColor = levelBaseViewTextColor;
        this.levelBaseViewLineColor = levelBaseViewLineColor;
        this.levelBaseViewCountColor = levelBaseViewCountColor;
        if (iconBgTag == 0) {
            this.iconBgTag = R.drawable.ic_launcher;
        } else {
            this.iconBgTag = iconBgTag;
        }
        if (iconBgWhite == 0) {
            this.iconBgWhite = R.drawable.level_view_default;
        } else {
            this.iconBgWhite = iconBgWhite;
        }
    }

    /**
     * 添加数据
     *
     * @param size   第一列有多少个
     * @param i      需要展示几列
     * @param list   需要添加的数据集
     * @param isChou 是否是抽签模式
     */
    public void addData(@NonNull int size, int i, @Nullable List<LevelViewBaseData> list, @Nullable List<LevelViewBaseData> list2, boolean isChou) {

        needLineNumber = i;
        //判断有多少列
        double n = (Math.log(size) / Math.log(2));

        //判断是否是整数
        if (n % 1 == 0) {
            totalFirstLine = size;
//            isFormate = true;
            colum = (int) n + 1;
            addData(totalFirstLine, list, list2, (int) n, 0, isChou);
        } else {
            totalFirstLine = (int) Math.pow(2.0, (int) n + 1.0);
//            isFormate = false;
            colum = (int) n + 1 + 1;
            addData(totalFirstLine, list, list2, (int) n + 1, 0, isChou);
        }
    }

    /**
     * 确定每列有多少个元素
     *
     * @param size   第一行的个数
     * @param i      列数
     * @param j      0表示刚好是2的幂  1表示不是2的幂
     * @param isChou 是否是抽签模式
     */
    private void addData(int size, List<LevelViewBaseData> list, @Nullable List<LevelViewBaseData> list2, int i, int j, boolean isChou) {
        switch (j) {
            case 0:
                for (int c = i; c >= 0; c--) {
                    for (int h = 0; h < Math.pow(2, c); h++) {
                        LevelViewBaseView bean = new LevelViewBaseView(context);
                        bean.setNumber(colum - 1 - c);
                        bean.setLevelBaseViewColor(levelBaseViewTextColor,levelBaseViewLineColor,levelBaseViewCountColor);
                        if (needLineNumber == 0) {
                            bean.setMaxRow(i);
                        } else {
                            bean.setMaxRow(needLineNumber - 1);
                        }
                        addView(bean);
                    }
                }

                break;
            case 1:
                addLine(size);
                break;
            default:
                return;
        }
        if (list == null) {
            if (listener != null) {
                listener.error("主数据集为空！！");
                throw new IllegalArgumentException("主数据集为空！！！！！");
            }
            return;
        }
        //该条件表明数据大于对战图展示的内容，有错
        if (list.size() > getChildCount()) {
            if (listener != null) {
                listener.error("主数据集中数据超过主视图的最大容量！！");
                throw new IllegalArgumentException("主数据集中数据超过主视图的最大容量！！！！！");
            }
            return;
        }
        //设置子view中的信息
        for (int child = 0; child < list.size(); child++) {
            ((LevelViewBaseView) getChildAt(child)).setName(list.get(child).getName());
            updateIcon(child, list, 1, isChou);
            ((LevelViewBaseView) getChildAt(child)).setCount(list.get(child).getCount());
            ((LevelViewBaseView) getChildAt(child)).setData(list.get(child));
            ((LevelViewBaseView) getChildAt(child)).setIconPath(list.get(child).getIcon());
            if (needLineNumber != 0) {
                ((LevelViewBaseView) getChildAt(child)).setMaxRow(needLineNumber - 1);
            }
            if (isChou) {
                ((LevelViewBaseView) getChildAt(child)).setIconNumber(String.valueOf(child + 1));
            }
            ((LevelViewBaseView) getChildAt(child)).setLevelBaseViewColor(levelBaseViewTextColor,levelBaseViewLineColor,levelBaseViewCountColor);


        }
        //第二个集合数据添加
        if (list2 == null || list2.isEmpty() || list2.size() != 3) {
            return;
        }
        isTwoList = true;
        for (int n = 0; n < list2.size(); n++) {
            LevelViewBaseView bean = new LevelViewBaseView(context);
            bean.setNumber(0);

            if (n == 2) {
                bean.setNumber(1);
                bean.setMaxRow(1);
            }
            addView(bean);
        }
        for (int child = 0; child < list2.size(); child++) {
            ((LevelViewBaseView) getChildAt(getChildCount() - 3 + child)).setName(list2.get(child).getName());
            updateIcon(child, list2, 2, isChou);
            ((LevelViewBaseView) getChildAt(getChildCount() - 3 + child)).setCount(list2.get(child).getCount());
            ((LevelViewBaseView) getChildAt(getChildCount() - 3 + child)).setData(list2.get(child));
            ((LevelViewBaseView) getChildAt(getChildCount() - 3 + child)).setLevelBaseViewColor(levelBaseViewTextColor,levelBaseViewLineColor,levelBaseViewCountColor);

        }
    }

    private void updateIcon(final int child, final List<LevelViewBaseData> list, int type, boolean isChou) {
        int position = child;
        switch (type) {
            case 1:
                position = child;
                break;
            case 2:
                position = getChildCount() - 3 + child;
                break;
            default:
        }
        try {
            String icon = list.get(child).getIcon();
            if (icon.isEmpty()) {
                if (list.get(child).getName().isEmpty()) {
                    ((LevelViewBaseView) getChildAt(position)).setIcon(BitmapFactory.decodeResource(context.getResources(), iconBgWhite));
                    ((LevelViewBaseView) getChildAt(position)).setLevelBaseViewColor(levelBaseViewTextColor,levelBaseViewLineColor,levelBaseViewCountColor);
                    return;
                }
                ((LevelViewBaseView) getChildAt(position)).setIcon(BitmapFactory.decodeResource(context.getResources(), iconBgTag));
                ((LevelViewBaseView) getChildAt(position)).setLevelBaseViewColor(levelBaseViewTextColor,levelBaseViewLineColor,levelBaseViewCountColor);
                return;
            }
            final int finalPosition = position;
            SimpleTarget target = new SimpleTarget<Bitmap>(100, 100) {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    ((LevelViewBaseView) getChildAt(finalPosition)).setIcon(resource);
                    ((LevelViewBaseView) getChildAt(finalPosition)).setLevelBaseViewColor(levelBaseViewTextColor,levelBaseViewLineColor,levelBaseViewCountColor);
                    ((LevelViewBaseView) getChildAt(finalPosition)).onRefresh();
                }
            };
            Glide.with(context).asBitmap()
                    .load(icon)
                    .into(target);

        } catch (Exception e) {
            e.printStackTrace();
            ((LevelViewBaseView) getChildAt(position)).setIcon(BitmapFactory.decodeResource(context.getResources(), iconBgTag));
            ((LevelViewBaseView) getChildAt(position)).setLevelBaseViewColor(levelBaseViewTextColor,levelBaseViewLineColor,levelBaseViewCountColor);

            postInvalidate();
        }

//            }
//        }).start();
    }

    /**
     * 不是2的幂时逐列添加数据
     *
     * @param size
     */

    private void addLine(int size) {

        if (size == 1) {
            LevelViewBaseView bean = new LevelViewBaseView(context);
            bean.setNumber(colum - 1);
            bean.setLevelBaseViewColor(levelBaseViewTextColor,levelBaseViewLineColor,levelBaseViewCountColor);
//            bean.setInternetCafeName("");
            addView(bean);
            return;
        }
        for (int i = 0; i < size; i++) {
            LevelViewBaseView bean = new LevelViewBaseView(context);
            bean.setNumber(lineNumber);
            bean.setLevelBaseViewColor(levelBaseViewTextColor,levelBaseViewLineColor,levelBaseViewCountColor);
            addView(bean);
        }
        lineNumber++;
        if (size % 2 == 0) {
            addLine(size / 2);
        } else {
            addLine((size + 1) / 2);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = this.getChildCount();
//        if (isFormate) {
        onDrawFormate(childCount);
//            return;
//        }
//        onDrawNotFormate(childCount);


    }

    /**
     * 第一列是2的幂的布局
     *
     * @param childCountAll
     */
    private void onDrawFormate(int childCountAll) {
        int h = colum - 1;
        int number = totalFirstLine;
        //当前的列数
        int lines = 0;
        int number1 = 0;
        int number2 = 0;
        int m = 0;
        int line = 0;
        int childCount = 0;
        if (isTwoList) {
            childCount = childCountAll - 3;
        } else {
            childCount = childCountAll;
        }

        for (int i = 0; i < childCount; i++) {
            View childView = this.getChildAt(i);

            if (i < number) {
                number1 = i - number2;
                childView.layout((colum - 1 - h) * CHILD_VIEW_WIDTH, CHILD_VIEW_HEIGHT * (int) Math.pow(2, colum - 1 - h) * number1, (colum - 1 - h) * CHILD_VIEW_WIDTH + CHILD_VIEW_WIDTH, CHILD_VIEW_HEIGHT * (int) Math.pow(2, colum - 1 - h) * number1 + CHILD_VIEW_HEIGHT * (int) Math.pow(2, line));

            } else if (i == number) {

                lines++;
                if (needLineNumber != 0) {
                    if (needLineNumber == lines) {
                        return;
                    }
                }

                if (h - 1 < 0) {

                } else {
                    number += (int) Math.pow(2, h - 1);
                    number2 += (int) (Math.pow(2, h));
                    number1 = i - number2;
                    m = h - 1;
                    line = colum - 1 - m;
                    childView.layout((colum - 1 - m) * CHILD_VIEW_WIDTH, CHILD_VIEW_HEIGHT * (int) Math.pow(2, colum - 1 - h) * number1, (colum - 1 - m) * CHILD_VIEW_WIDTH + CHILD_VIEW_WIDTH, CHILD_VIEW_HEIGHT * (int) Math.pow(2, colum - 1 - h) * number1 + CHILD_VIEW_HEIGHT * (int) Math.pow(2, line));
                    h = h - 1;

                }

            }
            setChildOnClik((LevelViewBaseView) childView, i);

        }
        if (!isTwoList) {
            return;
        }
        //判断有多少列
        double n = (Math.log(totalFirstLine) / Math.log(2));

        int heightTop = 0;

        //判断是否是整数
        if (n % 1 == 0) {
            heightTop = (int) Math.pow(2, (int) n) * CHILD_VIEW_HEIGHT * 3 / 4 + CHILD_VIEW_HEIGHT / 2;
        } else {
            heightTop = (int) Math.pow(2, (int) n + 1) * CHILD_VIEW_HEIGHT * 3 / 4 + CHILD_VIEW_HEIGHT / 2;
        }

        for (int i = 0; i < 3; i++) {
            View childView = this.getChildAt(getChildCount() - 3 + i);
            if (i < 2) {
                childView.layout((colum - 2) * CHILD_VIEW_WIDTH, heightTop + CHILD_VIEW_HEIGHT * i, (colum - 2) * CHILD_VIEW_WIDTH + CHILD_VIEW_WIDTH, heightTop + CHILD_VIEW_HEIGHT * i + CHILD_VIEW_HEIGHT);
            } else {
                childView.layout((colum - 1) * CHILD_VIEW_WIDTH, heightTop + CHILD_VIEW_HEIGHT - CHILD_VIEW_HEIGHT, (colum - 1) * CHILD_VIEW_WIDTH + CHILD_VIEW_WIDTH, heightTop + CHILD_VIEW_HEIGHT + CHILD_VIEW_HEIGHT);

            }
            setChildOnClik((LevelViewBaseView) childView, i);

        }
    }

    /**
     * 子view的点击事件
     *
     * @param childView 子wiew
     */
    private void setChildOnClik(final LevelViewBaseView childView, final int i) {
        if (childView.getData() == null) {
            return;
        }
        if (childView.getData().getName().isEmpty()) {
            return;
        }
        childView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null) {
                    return;
                }
                listener.onItemClik(childView, i);
            }
        });

        childView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener == null) {
                    return true;
                }
                listener.onItemLongClik(childView, i);
//                LogUtils.showToast(context, "onlongclik is  " + i);
                return true;
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public void setListener(GroupListen listener) {
        this.listener = listener;
    }

    public interface GroupListen {
        void error(String error);

        void onItemClik(LevelViewBaseView childView, int i);

        void onItemLongClik(LevelViewBaseView childView, int i);
    }
}
