package com.example.levelviewlibray;

import java.util.List;

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


    /**
     * 其余信息
     *
     * @return
     */
    public abstract List<String> getList();

}
