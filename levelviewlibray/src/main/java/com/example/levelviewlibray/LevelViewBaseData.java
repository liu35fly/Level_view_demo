package com.example.levelviewlibray;

/**
 * Created by fly on 2017/10/28.
 */

public abstract class LevelViewBaseData {

    /**
     * id
     *
     * @return
     */
    public abstract String getId();

    /**
     * 名称
     *
     * @return
     */
    public abstract String getName();

    /**
     * 分数
     *
     * @return
     */
    public abstract String getCount();

    /**
     * 头像
     *
     * @return
     */
    public abstract String getIcon();

    //长按后展示信息

    /**
     * 时间
     *
     * @return
     */
    public abstract String getTime();

    /**
     * 地点
     *
     * @return
     */
    public abstract String getAddress();

    /**
     * 标示
     *
     * @return
     */
    public abstract String getMatch();
}
