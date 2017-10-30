package com.example.levelviewlibray;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fly on 2017/10/28.
 */

public class LevelView extends HorizontalScrollView {

    private GestureDetector mGestureDetector;

    private Context context;

    //列数
    private int numberWidth = 0;
    //行数
    private int numberHeight = 0;
    //单个item宽度
    private int WIDTH = LevelViewBaseGroup.CHILD_VIEW_WIDTH;
    //单个item高度
    private int HEIGHT = LevelViewBaseGroup.CHILD_VIEW_HEIGHT;
    //顶部指示器高度
    private int titleHeight = 100;

    private LevelViewListener levelViewListener;

    private String titleBgColor, titleTextColor, levelViewBg;

    private String levelBaseViewTextColor, levelBaseViewLineColor, levelBaseViewCountColor;

    private int iconBgWhite, iconBgTag;
//    private boolean isDraw = false;

    public void setScrollListener(levelViewScrollListen scrollListener) {
        this.scrollListener = scrollListener;
    }

    private levelViewScrollListen scrollListener;

    public LevelView(Context context) {
        this(context, null);
    }

    public LevelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LevelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mGestureDetector = new GestureDetector(context, new HScrollDetector());
        setFadingEdgeLength(0);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LevelView);
//        bgColor = typedArray.getInt(R.styleable.LevelView_levelViewBackgroundColor,0xffffff);
        titleBgColor = typedArray.getString(R.styleable.LevelView_levelViewTitleBackgroundColor);
        if (titleBgColor == null || titleBgColor.isEmpty()) {
            titleBgColor = "#ffffff";
        }
        titleTextColor = typedArray.getString(R.styleable.LevelView_levelViewTitleTextColor);
        if (titleTextColor == null || titleTextColor.isEmpty()) {
            titleTextColor = "#000000";
        }
        levelViewBg = typedArray.getString(R.styleable.LevelView_levelViewBackgroundColor);
        if (levelViewBg == null || levelViewBg.isEmpty()) {
            levelViewBg = "#000000";
        }
        levelBaseViewTextColor = typedArray.getString(R.styleable.LevelView_levelViewTextColor);
        levelBaseViewLineColor = typedArray.getString(R.styleable.LevelView_levelViewLineColor);
        levelBaseViewCountColor = typedArray.getString(R.styleable.LevelView_levelViewRightTextColor);
        iconBgWhite = typedArray.getInt(R.styleable.LevelView_levelViewIconBg, 0);
        iconBgTag = typedArray.getInt(R.styleable.LevelView_levelViewIconBgTag, 0);
//        int textAttr = ta.getInteger(R.styleable.test_text, -1);

        typedArray.recycle();
//        setBackgroundColor(bg);
//        setBackgroundColor(Color.rgb(35, 35, 35));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (scrollListener != null) {
            scrollListener.onScroll();
        }
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }

    /**
     * 空的对阵图
     *
     * @param size 最底层item的个数
     */
    public void addChildView(@IntRange(from = 1, to = Integer.MAX_VALUE) int size) {
        addChildView(size, 0, null, null, null, false);
    }

    /**
     * 空的对阵图
     *
     * @param size   最底层item的个数
     * @param isChou 是否是序列模式
     */
    public void addChildView(@IntRange(from = 1, to = Integer.MAX_VALUE) int size, boolean isChou) {
        addChildView(size, 0, null, null, null, isChou);
    }


    /**
     * 设置子视图
     *
     * @param size                  最底层item个数
     * @param LevelViewBaseDataList 需要添加的数据集
     * @param listRound             每列的列名称集合
     * @param isChou                是否是序列模式
     */
    public void addChildView(@IntRange(from = 1, to = Integer.MAX_VALUE) int size, @Nullable List<LevelViewBaseData> LevelViewBaseDataList, @Nullable List<String> listRound, boolean isChou) {
        addChildView(size, 0, LevelViewBaseDataList, null, listRound, isChou);
    }

    /**
     * 设置子视图
     *
     * @param size                  最底层item个数
     * @param LevelViewBaseDataList 需要添加的数据集
     * @param listRound             每列的列名称集合
     * @param isChou                是否是序列模式
     */
    public void addChildView(@IntRange(from = 1, to = Integer.MAX_VALUE) int size, @Nullable List<LevelViewBaseData> LevelViewBaseDataList, @Nullable List<LevelViewBaseData> LevelViewBaseDataList2, @Nullable List<String> listRound, boolean isChou) {
        addChildView(size, 0, LevelViewBaseDataList, LevelViewBaseDataList2, listRound, isChou);
    }

    /**
     * 设置子视图
     *
     * @param size                  最底层item个数
     * @param numberLine            需要展示几层
     * @param levelViewDataBeanList 需要添加的数据集
     * @param listRound             每列的列名称集合
     * @param isChou                是否是序列模式
     */
    public void addChildView(@IntRange(from = 1, to = Integer.MAX_VALUE) int size, @IntRange(from = 0, to = Integer.MAX_VALUE) int numberLine, @Nullable List<LevelViewBaseData> levelViewDataBeanList, @NonNull List<String> listRound, boolean isChou) {
        addChildView(size, numberLine, levelViewDataBeanList, null, listRound, isChou);
    }

    /**
     * 设置子视图
     *
     * @param size       最底层item个数
     * @param numberLine 需要展示几层
     * @param listRound  每列的列名称集合
     * @param isChou     是否是序列模式
     */
    public void addChildView(@IntRange(from = 1, to = Integer.MAX_VALUE) int size, @IntRange(from = 0, to = Integer.MAX_VALUE) int numberLine, @Nullable List<String> listRound, boolean isChou) {
        addChildView(size, numberLine, null, null, listRound, isChou);
    }

    /**
     * 设置子视图
     *
     * @param size                  最底层item个数
     * @param numberLine            需要展示的层级数
     * @param levelViewDataBeanList 需要添加的数据集
     * @param isChou                是否是序列模式
     */
    public void addChildView(@IntRange(from = 1, to = Integer.MAX_VALUE) int size, @IntRange(from = 0, to = Integer.MAX_VALUE) int numberLine, @Nullable List<LevelViewBaseData> levelViewDataBeanList, @Nullable List<LevelViewBaseData> levelViewDataBeanList2, List<String> listRound, boolean isChou) {
        if (getChildAt(0) != null) {
            removeAllViews();
        }
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        int row = 0;
        double n = (Math.log(size) / Math.log(2));
        if (numberLine == 0) {

            //判断是否是整数
            if (n % 1 == 0) {
                row = (int) n + 1;
            } else {
                row = (int) n + 1 + 1;
            }
        } else {
            row = numberLine;
        }


        //判断有多少列
        double m = (Math.log(size) / Math.log(2));

        //判断是否是整数
        if (m % 1 == 0) {
            numberHeight = (int) Math.pow(2, (int) n);
        } else {
            numberHeight = (int) Math.pow(2, (int) n + 1);
        }

        numberWidth = row;

        List<String> listRoundTitle = new ArrayList<>(0);

        if (listRound != null) {
            listRoundTitle = listRound;
        }
        List<LevelViewBaseData> list = new ArrayList<>();
        if (levelViewDataBeanList == null || levelViewDataBeanList.isEmpty()) {
            list = addInitData(row);
        } else {
            list = levelViewDataBeanList;
        }
        addTitle(linearLayout, row, listRoundTitle);
        addLevelViewGroup(linearLayout, size, numberLine, list, levelViewDataBeanList2, isChou);
        addView(linearLayout);
    }

    private List<LevelViewBaseData> addInitData(int row) {
        List<LevelViewBaseData> list = new ArrayList<>();
        LevelViewBaseData levelViewDataBean;


        for (int c = row - 1; c >= 0; c--) {
            for (int h = 0; h < Math.pow(2, c); h++) {
                levelViewDataBean = new LevelViewBaseData() {
                    @Override
                    public String getId() {
                        return "";
                    }

                    @Override
                    public String getName() {
                        return "";
                    }

                    @Override
                    public String getCount() {
                        return "";
                    }

                    @Override
                    public String getIcon() {
                        return "";
                    }

                    @Override
                    public List<String> getList() {
                        return new ArrayList<String>(0);
                    }
                };

                list.add(levelViewDataBean);
            }
        }
        return list;
    }

    /**
     * 添加顶部指示器
     *
     * @param parent
     * @param number
     */
    private void addTitle(@NonNull ViewGroup parent, @IntRange(from = 0, to = Integer.MAX_VALUE) int number, @NonNull List<String> listRound) {
        List<String> list;
        LinearLayout title = new LinearLayout(context);
        title.setOrientation(LinearLayout.HORIZONTAL);
        title.setBackgroundColor(Color.parseColor(titleBgColor));
        TextView textView;
        if (listRound.isEmpty()) {
            for (int i = 0; i < number; i++) {
                listRound.add("Round" + (i + 1));
            }
        }
        if (listRound.size() > number) {
            list = listRound.subList(0, number);
        } else {
            list = listRound;
        }
        for (int i = 0; i < list.size(); i++) {
            textView = new TextView(context);
            textView.setText(listRound.get(i));
            textView.setTextSize(15F);
            textView.setTextColor(Color.parseColor(titleTextColor));
            textView.setGravity(Gravity.CENTER);
//            textView.setLayoutParams(params);
            textView.setWidth(WIDTH);
            textView.setHeight(titleHeight);
            title.addView(textView);
        }
        //获取屏幕尺寸
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//        Point size = new Point();
//        wm.getDefaultDisplay().getSize(size);
//        int width = size.x;
        //不在Activity中时 单位px
        int width = getContext().getResources().getDisplayMetrics().widthPixels;

        LinearLayout.LayoutParams paramsParent = new LinearLayout.LayoutParams(width, titleHeight);
        if (paramsParent.width < numberWidth * WIDTH) {
            paramsParent.width = numberWidth * WIDTH;
        }
        parent.addView(title, paramsParent);

    }

    private void addLevelViewGroup(ViewGroup parent, int size, int numberLine, List<LevelViewBaseData> levelViewDataBeanList, boolean isChou) {
        addLevelViewGroup(parent, size, numberLine, levelViewDataBeanList, null, isChou);
    }

    /**
     * 添加具体对阵图
     *
     * @param parent
     * @param size
     * @param numberLine
     * @param levelViewDataBeanList
     * @param isChou                是否是序列模式
     */
    private void addLevelViewGroup(ViewGroup parent, int size, int numberLine, List<LevelViewBaseData> levelViewDataBeanList, List<LevelViewBaseData> levelViewDataBeanList2, boolean isChou) {
        NestedScrollView scrollView = new NestedScrollView(context);

        RelativeLayout relativeLayout = new RelativeLayout(context);

        LevelViewBaseGroup group = new LevelViewBaseGroup(context);
        group.setBackgroundColor(Color.parseColor(levelViewBg));
        group.setListener(new LevelViewBaseGroup.GroupListen() {
            @Override
            public void error(String error) {
                if (levelViewListener == null) {
                    return;
                }
                levelViewListener.onError(error);
            }

            @Override
            public void onItemClik(LevelViewBaseView childView, int i) {
                if (levelViewListener == null) {
                    return;
                }
                levelViewListener.onItemClik(childView, i);
            }

            @Override
            public void onItemLongClik(LevelViewBaseView childView, int i) {
                if (levelViewListener == null) {
                    return;
                }
                levelViewListener.onItemLongClik(childView, i);

            }
        });
        group.paseBaseColors(levelBaseViewTextColor, levelBaseViewLineColor, levelBaseViewCountColor,iconBgWhite,iconBgTag);
        group.addData(size, numberLine, levelViewDataBeanList, levelViewDataBeanList2, isChou);
        FrameLayout.LayoutParams paramsGroup = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (paramsGroup.width < numberWidth * WIDTH) {
            paramsGroup.width = numberWidth * WIDTH;
        }
        if (paramsGroup.height < numberHeight * HEIGHT + titleHeight + HEIGHT) {
            paramsGroup.height = numberHeight * HEIGHT + titleHeight + HEIGHT;
        }
        relativeLayout.addView(group, paramsGroup);
        scrollView.addView(relativeLayout);
        parent.addView(scrollView);
    }

    public String getLevelBaseViewTextColor() {
        return levelBaseViewTextColor;
    }

    public void setLevelBaseViewTextColor(String levelBaseViewTextColor) {
        this.levelBaseViewTextColor = levelBaseViewTextColor;
    }

    public String getLevelBaseViewLineColor() {
        return levelBaseViewLineColor;
    }

    public void setLevelBaseViewLineColor(String levelBaseViewLineColor) {
        this.levelBaseViewLineColor = levelBaseViewLineColor;
    }

    public String getLevelBaseViewCountColor() {
        return levelBaseViewCountColor;
    }

    public void setLevelBaseViewCountColor(String levelBaseViewCountColor) {
        this.levelBaseViewCountColor = levelBaseViewCountColor;
    }

    public int getIconBgWhite() {
        return iconBgWhite;
    }

    public void setIconBgWhite(int iconBgWhite) {
        this.iconBgWhite = iconBgWhite;
    }

    public int getIconBgTag() {
        return iconBgTag;
    }

    public void setIconBgTag(int iconBgTag) {
        this.iconBgTag = iconBgTag;
    }

    public levelViewScrollListen getScrollListener() {
        return scrollListener;
    }

    public void setLevelViewListener(LevelViewListener levelViewListener) {
        this.levelViewListener = levelViewListener;
    }

    class HScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return Math.abs(distanceX) > Math.abs(distanceY);

        }
    }

    public interface LevelViewListener {
        void onError(String error);

        void onItemClik(LevelViewBaseView childView, int i);

        void onItemLongClik(LevelViewBaseView childView, int i);
    }

    public interface levelViewScrollListen {
        void onScroll();
    }
}
