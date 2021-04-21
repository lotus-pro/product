package com.platform.support.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 供应商信息表(SupplierInfo)表实体类
 *
 * @author zengzheng
 * @since 2021-04-21 13:36:38
 */
@Data
@TableName("supplier_info")
public class SupplierInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    //主键
    private Integer id;
    //供应商编号
    private String suprNo;
    //供应商全称
    private String suprFullname;
    //供应商名称简称
    private String suprAbb;
    //供应商有效状态标识
    private String suprEffectIdnt;
    //供应商联系人姓名
    private String suprContName;
    //供应商联系人联系方式
    private String suprContMode;
    //供应商联系人邮箱
    private String suprContMailbox;
    //创建人
    private String createUser;
    //创建时间
    private Date createTime;
    //最后更新用户
    private String lastUpdateUser;
    //最后更新时间
    private Date lastUpdateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSuprNo() {
        return suprNo;
    }

    public void setSuprNo(String suprNo) {
        this.suprNo = suprNo;
    }

    public String getSuprFullname() {
        return suprFullname;
    }

    public void setSuprFullname(String suprFullname) {
        this.suprFullname = suprFullname;
    }

    public String getSuprAbb() {
        return suprAbb;
    }

    public void setSuprAbb(String suprAbb) {
        this.suprAbb = suprAbb;
    }

    public String getSuprEffectIdnt() {
        return suprEffectIdnt;
    }

    public void setSuprEffectIdnt(String suprEffectIdnt) {
        this.suprEffectIdnt = suprEffectIdnt;
    }

    public String getSuprContName() {
        return suprContName;
    }

    public void setSuprContName(String suprContName) {
        this.suprContName = suprContName;
    }

    public String getSuprContMode() {
        return suprContMode;
    }

    public void setSuprContMode(String suprContMode) {
        this.suprContMode = suprContMode;
    }

    public String getSuprContMailbox() {
        return suprContMailbox;
    }

    public void setSuprContMailbox(String suprContMailbox) {
        this.suprContMailbox = suprContMailbox;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }


}