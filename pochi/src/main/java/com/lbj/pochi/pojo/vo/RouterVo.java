package com.lbj.pochi.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 路由视图类
 */
@Data
public class RouterVo implements Serializable {
    /**
     * 路由名称
     */
    private String name;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 组件地址
     */
    private String component;

    /**
     * 当设置noRedirect时 会在面包屑导航中无法点击
     */
    private String redirect;
    /**
     * 是否隐藏
     */
    private boolean hidden;

    /**
     * 是否一直展示,如果为true,机试子菜单只有一个也会展示层级关系
     */
    private boolean alwaysShow;

    /**
     * 子菜单
     */
    private List<RouterVo> children;
    /**
     * 路由原信息
     */
    private MetaVo meta;

    /**
     * 路由原信息
     */
    @Data
    public static class MetaVo implements Serializable{
        /**
         * 菜单名称
         */
        private String title;
        /**
         * 菜单图形
         */
        private String icon;

        public MetaVo(String title, String icon) {
            this.title = title;
            this.icon = icon;
        }
    }
}
