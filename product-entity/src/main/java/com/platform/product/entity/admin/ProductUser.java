package com.platform.product.entity.admin;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductUser implements Serializable {

    @TableId("user_code")
    private String userCode;

    @TableField("user_name")
    private String userName;

    @TableField("user_password")
    private String userPassword;

    @TableField("phone")
    private String phone;

    @TableField("last_role")
    private String lastRole;

    @TableField("default_role")
    private String defaultRole;

    private String isEnabled;

    @TableField(exist = false)
    private List<ProductRole> sysRoles = new ArrayList<>();


    public String getUserCode() {
        return this.userCode;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public String getLastRole() {
        return this.lastRole;
    }

    public String getDefaultRole() {
        return this.defaultRole;
    }

    public String getIsEnabled() {
        return this.isEnabled;
    }

    public void setUserCode(final String userCode) {
        this.userCode = userCode;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public void setUserPassword(final String userPassword) {
        this.userPassword = userPassword;
    }

    public void setLastRole(final String lastRole) {
        this.lastRole = lastRole;
    }

    public void setDefaultRole(final String defaultRole) {
        this.defaultRole = defaultRole;
    }

    public void setIsEnabled(final String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public List<ProductRole> getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(List<ProductRole> sysRoles) {
        this.sysRoles = sysRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductUser that = (ProductUser) o;

        if (userCode != null ? !userCode.equals(that.userCode) : that.userCode != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (userPassword != null ? !userPassword.equals(that.userPassword) : that.userPassword != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (lastRole != null ? !lastRole.equals(that.lastRole) : that.lastRole != null) return false;
        if (defaultRole != null ? !defaultRole.equals(that.defaultRole) : that.defaultRole != null) return false;
        if (isEnabled != null ? !isEnabled.equals(that.isEnabled) : that.isEnabled != null) return false;
        return sysRoles != null ? sysRoles.equals(that.sysRoles) : that.sysRoles == null;
    }

    @Override
    public int hashCode() {
        int result = userCode != null ? userCode.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (lastRole != null ? lastRole.hashCode() : 0);
        result = 31 * result + (defaultRole != null ? defaultRole.hashCode() : 0);
        result = 31 * result + (isEnabled != null ? isEnabled.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductUser{" +
                "userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", phone='" + phone + '\'' +
                ", lastRole='" + lastRole + '\'' +
                ", defaultRole='" + defaultRole + '\'' +
                ", isEnabled='" + isEnabled + '\'' +
                '}';
    }


    public ProductUser() {
    }
    public ProductUser(String userCode,String userName, String userPassword, String phone, String lastRole, String defaultRole, String isEnabled) {
        this.userCode = userCode;
        this.userName = userName;
        this.userPassword = userPassword;
        this.phone = phone;
        this.lastRole = lastRole;
        this.defaultRole = defaultRole;
        this.isEnabled = isEnabled;
    }
}
