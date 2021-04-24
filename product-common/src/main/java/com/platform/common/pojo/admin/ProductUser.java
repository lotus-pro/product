package com.platform.common.pojo.admin;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

public class ProductUser implements Serializable {
    @TableId("USER_CODE")
    @NotBlank(
            message = "{pcmc.user.valid.0001}"
    )
    @Pattern(
            regexp = "^[a-zA-Z0-9]{4,16}",
            message = "{pcmc.user.valid.0003}"
    )
    @Excel(
            name = "*用户编码",
            isImportField = "true"
    )
    private String userCode;
    @TableField("USER_NAME")
    @NotBlank(
            message = "{pcmc.user.valid.0002}"
    )
    @Excel(
            name = "*用户名称",
            isImportField = "true"
    )
    @Length(
            max = 256,
            message = "{pcmc.user.error.0021}"
    )
    private String userName;
    @TableField("USER_PASSWORD")
    private String userPassword;
    @TableField("PHONE")
    @Excel(
            name = "手机号",
            isImportField = "true"
    )
    private String phone;
    @TableField("PAGESIZE")
    @Excel(
            name = "分页",
            isImportField = "true",
            dict = "pcmc,pagesize",
            addressList = true
    )
    private Integer pagesize;
    @TableField("MENU_TYPE")
    private String menuType;
    @TableField("SKIN_CODE")
    private String skinCode;
    @TableField("LAST_ROLE")
    private String lastRole;
    @TableField("DEFAULT_ROLE")
    private String defaultRole;
    @TableField("UPDATE_PASSWORD_DATE")
    private Date updatePasswordDate;
    @TableField("UPDATE_PASSWORD_DAYS")
    @Excel(
            name = "修改密码天数",
            isImportField = "true"
    )
    private Integer updatePasswordDays;
    @NotBlank(
            message = "{jraf.valid.0001}"
    )
    @Excel(
            name = "*是否有效",
            isImportField = "true",
            dict = "pcmc,boolean",
            addressList = true
    )
    private String isEnabled;
    @TableField("UPDATE_PASSWORD_FLAG")
    private String updatePasswordFlag;

    public String getUserCode() {
        return this.userCode;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public Integer getPagesize() {
        return this.pagesize;
    }

    public String getMenuType() {
        return this.menuType;
    }

    public String getSkinCode() {
        return this.skinCode;
    }

    public String getLastRole() {
        return this.lastRole;
    }

    public String getDefaultRole() {
        return this.defaultRole;
    }

    public Date getUpdatePasswordDate() {
        return this.updatePasswordDate;
    }

    public Integer getUpdatePasswordDays() {
        return this.updatePasswordDays;
    }

    public String getIsEnabled() {
        return this.isEnabled;
    }

    public String getUpdatePasswordFlag() {
        return this.updatePasswordFlag;
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

    public void setPagesize(final Integer pagesize) {
        this.pagesize = pagesize;
    }

    public void setMenuType(final String menuType) {
        this.menuType = menuType;
    }

    public void setSkinCode(final String skinCode) {
        this.skinCode = skinCode;
    }

    public void setLastRole(final String lastRole) {
        this.lastRole = lastRole;
    }

    public void setDefaultRole(final String defaultRole) {
        this.defaultRole = defaultRole;
    }

    public void setUpdatePasswordDate(final Date updatePasswordDate) {
        this.updatePasswordDate = updatePasswordDate;
    }

    public void setUpdatePasswordDays(final Integer updatePasswordDays) {
        this.updatePasswordDays = updatePasswordDays;
    }

    public void setIsEnabled(final String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setUpdatePasswordFlag(final String updatePasswordFlag) {
        this.updatePasswordFlag = updatePasswordFlag;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ProductUser{" +
                "userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", phone='" + phone + '\'' +
                ", pagesize=" + pagesize +
                ", menuType='" + menuType + '\'' +
                ", skinCode='" + skinCode + '\'' +
                ", lastRole='" + lastRole + '\'' +
                ", defaultRole='" + defaultRole + '\'' +
                ", updatePasswordDate=" + updatePasswordDate +
                ", updatePasswordDays=" + updatePasswordDays +
                ", isEnabled='" + isEnabled + '\'' +
                ", updatePasswordFlag='" + updatePasswordFlag + '\'' +
                '}';
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
        if (pagesize != null ? !pagesize.equals(that.pagesize) : that.pagesize != null) return false;
        if (menuType != null ? !menuType.equals(that.menuType) : that.menuType != null) return false;
        if (skinCode != null ? !skinCode.equals(that.skinCode) : that.skinCode != null) return false;
        if (lastRole != null ? !lastRole.equals(that.lastRole) : that.lastRole != null) return false;
        if (defaultRole != null ? !defaultRole.equals(that.defaultRole) : that.defaultRole != null) return false;
        if (updatePasswordDate != null ? !updatePasswordDate.equals(that.updatePasswordDate) : that.updatePasswordDate != null)
            return false;
        if (updatePasswordDays != null ? !updatePasswordDays.equals(that.updatePasswordDays) : that.updatePasswordDays != null)
            return false;
        if (isEnabled != null ? !isEnabled.equals(that.isEnabled) : that.isEnabled != null) return false;
        return updatePasswordFlag != null ? updatePasswordFlag.equals(that.updatePasswordFlag) : that.updatePasswordFlag == null;
    }

    @Override
    public int hashCode() {
        int result = userCode != null ? userCode.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (pagesize != null ? pagesize.hashCode() : 0);
        result = 31 * result + (menuType != null ? menuType.hashCode() : 0);
        result = 31 * result + (skinCode != null ? skinCode.hashCode() : 0);
        result = 31 * result + (lastRole != null ? lastRole.hashCode() : 0);
        result = 31 * result + (defaultRole != null ? defaultRole.hashCode() : 0);
        result = 31 * result + (updatePasswordDate != null ? updatePasswordDate.hashCode() : 0);
        result = 31 * result + (updatePasswordDays != null ? updatePasswordDays.hashCode() : 0);
        result = 31 * result + (isEnabled != null ? isEnabled.hashCode() : 0);
        result = 31 * result + (updatePasswordFlag != null ? updatePasswordFlag.hashCode() : 0);
        return result;
    }


    public ProductUser() {
    }
    public ProductUser(@NotBlank(
            message = "{pcmc.user.valid.0001}"
    ) @Pattern(
            regexp = "^[a-zA-Z0-9]{4,16}",
            message = "{pcmc.user.valid.0003}"
    ) String userCode, @NotBlank(
            message = "{pcmc.user.valid.0002}"
    ) @Length(
            max = 256,
            message = "{pcmc.user.error.0021}"
    ) String userName, String userPassword, String phone, Integer pagesize, String menuType, String skinCode, String lastRole, String defaultRole, Date updatePasswordDate, Integer updatePasswordDays, @NotBlank(
            message = "{jraf.valid.0001}"
    ) String isEnabled, String updatePasswordFlag) {
        this.userCode = userCode;
        this.userName = userName;
        this.userPassword = userPassword;
        this.phone = phone;
        this.pagesize = pagesize;
        this.menuType = menuType;
        this.skinCode = skinCode;
        this.lastRole = lastRole;
        this.defaultRole = defaultRole;
        this.updatePasswordDate = updatePasswordDate;
        this.updatePasswordDays = updatePasswordDays;
        this.isEnabled = isEnabled;
        this.updatePasswordFlag = updatePasswordFlag;
    }
}
