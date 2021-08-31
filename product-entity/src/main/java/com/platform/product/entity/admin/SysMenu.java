package com.platform.product.entity.admin;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (SysMenu)表实体类
 *
 * @author zengzheng
 * @since 2021-05-12 16:31:52
 */
@Data
@TableName("sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    //主键id
    private Long id;
    //父菜单ID，一级菜单为0
    private Long parentId;
    //菜单名称
    private String menuName;
    //菜单URL
    private String menuPath;
    //授权(多个用逗号分隔，如：user:list,user:create)
    private String menPerms;
    //菜单组件
    private String component;
    //类型     0：目录   1：菜单   2：按钮
    private Integer type;
    //菜单图标
    private String icon;
    //排序
    private Integer orderNum;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //状态
    private String isEnabled;
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    public String getMenPerms() {
        return menPerms;
    }

    public void setMenPerms(String menPerms) {
        this.menPerms = menPerms;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }


}