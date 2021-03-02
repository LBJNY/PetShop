package com.lbj.pochi.constant;

/**
 * 核心常量
 */
public final class CoreConstant {
    /**
     * 请求头携带token的key
     */
    public static final String TOKEN_HEADER="Authorization";
    /**
     * 菜单默认父菜节点
     */
    public static final Long DEFAULT_PARENT_ID = 0L;

    /**
     * 菜单不显示
     */
    public static final Integer  HIDDEN_STATE=0;

    /**
     * 不跳转
     */
    public static final String NO_REDIRECT="noRedirect";

    /**
     * 路径间隔符
     */
    public static final String URL_SPLITE="/";

    /**
     * 菜单默认组件地址
     */
    public static final String DEFAULT_COMPONENT="Layout";
}
