package com.platform.common.entity.dto.admin;

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
    @TableField("EMPLOYEE_CODE")
    @Excel(
            name = "员工编码",
            isImportField = "true"
    )
    private String employeeCode;
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
    @TableField(
            exist = false
    )
    private String createEmployee;
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
    @TableField("IMAGE")
    private String image;
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

    public String getEmployeeCode() {
        return this.employeeCode;
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

    public String getCreateEmployee() {
        return this.createEmployee;
    }

    public String getIsEnabled() {
        return this.isEnabled;
    }

    public String getImage() {
        return this.image;
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

    public void setEmployeeCode(final String employeeCode) {
        this.employeeCode = employeeCode;
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

    public void setCreateEmployee(final String createEmployee) {
        this.createEmployee = createEmployee;
    }

    public void setIsEnabled(final String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public void setUpdatePasswordFlag(final String updatePasswordFlag) {
        this.updatePasswordFlag = updatePasswordFlag;
    }

    public String toString() {
        return "PcmcUser(userCode=" + this.getUserCode() + ", userName=" + this.getUserName() + ", userPassword=" + this.getUserPassword() + ", employeeCode=" + this.getEmployeeCode() + ", pagesize=" + this.getPagesize() + ", menuType=" + this.getMenuType() + ", skinCode=" + this.getSkinCode() + ", lastRole=" + this.getLastRole() + ", defaultRole=" + this.getDefaultRole() + ", updatePasswordDate=" + this.getUpdatePasswordDate() + ", updatePasswordDays=" + this.getUpdatePasswordDays() + ", createEmployee=" + this.getCreateEmployee() + ", isEnabled=" + this.getIsEnabled() + ", image=" + this.getImage() + ", updatePasswordFlag=" + this.getUpdatePasswordFlag() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ProductUser)) {
            return false;
        } else {
            ProductUser other = (ProductUser)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                label193: {
                    Object this$userCode = this.getUserCode();
                    Object other$userCode = other.getUserCode();
                    if (this$userCode == null) {
                        if (other$userCode == null) {
                            break label193;
                        }
                    } else if (this$userCode.equals(other$userCode)) {
                        break label193;
                    }

                    return false;
                }

                Object this$userName = this.getUserName();
                Object other$userName = other.getUserName();
                if (this$userName == null) {
                    if (other$userName != null) {
                        return false;
                    }
                } else if (!this$userName.equals(other$userName)) {
                    return false;
                }

                Object this$userPassword = this.getUserPassword();
                Object other$userPassword = other.getUserPassword();
                if (this$userPassword == null) {
                    if (other$userPassword != null) {
                        return false;
                    }
                } else if (!this$userPassword.equals(other$userPassword)) {
                    return false;
                }

                label172: {
                    Object this$employeeCode = this.getEmployeeCode();
                    Object other$employeeCode = other.getEmployeeCode();
                    if (this$employeeCode == null) {
                        if (other$employeeCode == null) {
                            break label172;
                        }
                    } else if (this$employeeCode.equals(other$employeeCode)) {
                        break label172;
                    }

                    return false;
                }

                Object this$pagesize = this.getPagesize();
                Object other$pagesize = other.getPagesize();
                if (this$pagesize == null) {
                    if (other$pagesize != null) {
                        return false;
                    }
                } else if (!this$pagesize.equals(other$pagesize)) {
                    return false;
                }

                Object this$menuType = this.getMenuType();
                Object other$menuType = other.getMenuType();
                if (this$menuType == null) {
                    if (other$menuType != null) {
                        return false;
                    }
                } else if (!this$menuType.equals(other$menuType)) {
                    return false;
                }

                label151: {
                    Object this$skinCode = this.getSkinCode();
                    Object other$skinCode = other.getSkinCode();
                    if (this$skinCode == null) {
                        if (other$skinCode == null) {
                            break label151;
                        }
                    } else if (this$skinCode.equals(other$skinCode)) {
                        break label151;
                    }

                    return false;
                }

                Object this$lastRole = this.getLastRole();
                Object other$lastRole = other.getLastRole();
                if (this$lastRole == null) {
                    if (other$lastRole != null) {
                        return false;
                    }
                } else if (!this$lastRole.equals(other$lastRole)) {
                    return false;
                }

                label137: {
                    Object this$defaultRole = this.getDefaultRole();
                    Object other$defaultRole = other.getDefaultRole();
                    if (this$defaultRole == null) {
                        if (other$defaultRole == null) {
                            break label137;
                        }
                    } else if (this$defaultRole.equals(other$defaultRole)) {
                        break label137;
                    }

                    return false;
                }

                Object this$updatePasswordDate = this.getUpdatePasswordDate();
                Object other$updatePasswordDate = other.getUpdatePasswordDate();
                if (this$updatePasswordDate == null) {
                    if (other$updatePasswordDate != null) {
                        return false;
                    }
                } else if (!this$updatePasswordDate.equals(other$updatePasswordDate)) {
                    return false;
                }

                label123: {
                    Object this$updatePasswordDays = this.getUpdatePasswordDays();
                    Object other$updatePasswordDays = other.getUpdatePasswordDays();
                    if (this$updatePasswordDays == null) {
                        if (other$updatePasswordDays == null) {
                            break label123;
                        }
                    } else if (this$updatePasswordDays.equals(other$updatePasswordDays)) {
                        break label123;
                    }

                    return false;
                }

                Object this$createEmployee = this.getCreateEmployee();
                Object other$createEmployee = other.getCreateEmployee();
                if (this$createEmployee == null) {
                    if (other$createEmployee != null) {
                        return false;
                    }
                } else if (!this$createEmployee.equals(other$createEmployee)) {
                    return false;
                }

                label109: {
                    Object this$isEnabled = this.getIsEnabled();
                    Object other$isEnabled = other.getIsEnabled();
                    if (this$isEnabled == null) {
                        if (other$isEnabled == null) {
                            break label109;
                        }
                    } else if (this$isEnabled.equals(other$isEnabled)) {
                        break label109;
                    }

                    return false;
                }

                label102: {
                    Object this$image = this.getImage();
                    Object other$image = other.getImage();
                    if (this$image == null) {
                        if (other$image == null) {
                            break label102;
                        }
                    } else if (this$image.equals(other$image)) {
                        break label102;
                    }

                    return false;
                }

                Object this$updatePasswordFlag = this.getUpdatePasswordFlag();
                Object other$updatePasswordFlag = other.getUpdatePasswordFlag();
                if (this$updatePasswordFlag == null) {
                    if (other$updatePasswordFlag != null) {
                        return false;
                    }
                } else if (!this$updatePasswordFlag.equals(other$updatePasswordFlag)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProductUser;
    }

    public int hashCode() {
        int result = super.hashCode();
        Object $userCode = this.getUserCode();
        result = result * 59 + ($userCode == null ? 43 : $userCode.hashCode());
        Object $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        Object $userPassword = this.getUserPassword();
        result = result * 59 + ($userPassword == null ? 43 : $userPassword.hashCode());
        Object $employeeCode = this.getEmployeeCode();
        result = result * 59 + ($employeeCode == null ? 43 : $employeeCode.hashCode());
        Object $pagesize = this.getPagesize();
        result = result * 59 + ($pagesize == null ? 43 : $pagesize.hashCode());
        Object $menuType = this.getMenuType();
        result = result * 59 + ($menuType == null ? 43 : $menuType.hashCode());
        Object $skinCode = this.getSkinCode();
        result = result * 59 + ($skinCode == null ? 43 : $skinCode.hashCode());
        Object $lastRole = this.getLastRole();
        result = result * 59 + ($lastRole == null ? 43 : $lastRole.hashCode());
        Object $defaultRole = this.getDefaultRole();
        result = result * 59 + ($defaultRole == null ? 43 : $defaultRole.hashCode());
        Object $updatePasswordDate = this.getUpdatePasswordDate();
        result = result * 59 + ($updatePasswordDate == null ? 43 : $updatePasswordDate.hashCode());
        Object $updatePasswordDays = this.getUpdatePasswordDays();
        result = result * 59 + ($updatePasswordDays == null ? 43 : $updatePasswordDays.hashCode());
        Object $createEmployee = this.getCreateEmployee();
        result = result * 59 + ($createEmployee == null ? 43 : $createEmployee.hashCode());
        Object $isEnabled = this.getIsEnabled();
        result = result * 59 + ($isEnabled == null ? 43 : $isEnabled.hashCode());
        Object $image = this.getImage();
        result = result * 59 + ($image == null ? 43 : $image.hashCode());
        Object $updatePasswordFlag = this.getUpdatePasswordFlag();
        result = result * 59 + ($updatePasswordFlag == null ? 43 : $updatePasswordFlag.hashCode());
        return result;
    }

    public ProductUser() {
    }

    public ProductUser(final String userCode, final String userName, final String userPassword, final String employeeCode, final Integer pagesize, final String menuType, final String skinCode, final String lastRole, final String defaultRole, final Date updatePasswordDate, final Integer updatePasswordDays, final String createEmployee, final String isEnabled, final String image, final String updatePasswordFlag) {
        this.userCode = userCode;
        this.userName = userName;
        this.userPassword = userPassword;
        this.employeeCode = employeeCode;
        this.pagesize = pagesize;
        this.menuType = menuType;
        this.skinCode = skinCode;
        this.lastRole = lastRole;
        this.defaultRole = defaultRole;
        this.updatePasswordDate = updatePasswordDate;
        this.updatePasswordDays = updatePasswordDays;
        this.createEmployee = createEmployee;
        this.isEnabled = isEnabled;
        this.image = image;
        this.updatePasswordFlag = updatePasswordFlag;
    }
}
