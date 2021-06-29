package com.platform.core.entity;

import com.platform.common.pojo.admin.ProductRole;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class UserDetailInfo implements Serializable {
    private static final long serialVersionUID = 376425831761675407L;

    private String userCode;
    private String userName;
    private String userPassword;
    private String phone;
//    private String layout;
//    private Integer pageSize;
//    private String theme;
//    private String language;
    private String defaultRole;
    private String defaultRoleName;
    //后续涉及角色
    private List<ProductRole> roles;
    private Set<String> roleCodes;
    private String allRoleName;
    private String menuType;
    private String skinType;
    private Integer passwordErrorCount = 0;
    private Boolean lock = false;
    private String lockTime;
    private Boolean isOverdue;
    private String avatar;
    private Integer isEnabled;

    @Override
    public String toString() {
        return "UserDetailInfo{" +
                "userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", phone='" + phone + '\'' +
                ", defaultRole='" + defaultRole + '\'' +
                ", defaultRoleName='" + defaultRoleName + '\'' +
                ", roles=" + roles +
                ", roleCodes=" + roleCodes +
                ", allRoleName='" + allRoleName + '\'' +
                ", menuType='" + menuType + '\'' +
                ", skinType='" + skinType + '\'' +
                ", passwordErrorCount=" + passwordErrorCount +
                ", lock=" + lock +
                ", lockTime='" + lockTime + '\'' +
                ", isOverdue=" + isOverdue +
                ", avatar='" + avatar + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDetailInfo that = (UserDetailInfo) o;

        if (userCode != null ? !userCode.equals(that.userCode) : that.userCode != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (userPassword != null ? !userPassword.equals(that.userPassword) : that.userPassword != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (defaultRole != null ? !defaultRole.equals(that.defaultRole) : that.defaultRole != null) return false;
        if (defaultRoleName != null ? !defaultRoleName.equals(that.defaultRoleName) : that.defaultRoleName != null)
            return false;
        if (roles != null ? !roles.equals(that.roles) : that.roles != null) return false;
        if (roleCodes != null ? !roleCodes.equals(that.roleCodes) : that.roleCodes != null) return false;
        if (allRoleName != null ? !allRoleName.equals(that.allRoleName) : that.allRoleName != null) return false;
        if (menuType != null ? !menuType.equals(that.menuType) : that.menuType != null) return false;
        if (skinType != null ? !skinType.equals(that.skinType) : that.skinType != null) return false;
        if (passwordErrorCount != null ? !passwordErrorCount.equals(that.passwordErrorCount) : that.passwordErrorCount != null)
            return false;
        if (lock != null ? !lock.equals(that.lock) : that.lock != null) return false;
        if (lockTime != null ? !lockTime.equals(that.lockTime) : that.lockTime != null) return false;
        if (isOverdue != null ? !isOverdue.equals(that.isOverdue) : that.isOverdue != null) return false;
        if (avatar != null ? !avatar.equals(that.avatar) : that.avatar != null) return false;
        return isEnabled != null ? isEnabled.equals(that.isEnabled) : that.isEnabled == null;
    }

    @Override
    public int hashCode() {
        int result = userCode != null ? userCode.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (defaultRole != null ? defaultRole.hashCode() : 0);
        result = 31 * result + (defaultRoleName != null ? defaultRoleName.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (roleCodes != null ? roleCodes.hashCode() : 0);
        result = 31 * result + (allRoleName != null ? allRoleName.hashCode() : 0);
        result = 31 * result + (menuType != null ? menuType.hashCode() : 0);
        result = 31 * result + (skinType != null ? skinType.hashCode() : 0);
        result = 31 * result + (passwordErrorCount != null ? passwordErrorCount.hashCode() : 0);
        result = 31 * result + (lock != null ? lock.hashCode() : 0);
        result = 31 * result + (lockTime != null ? lockTime.hashCode() : 0);
        result = 31 * result + (isOverdue != null ? isOverdue.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (isEnabled != null ? isEnabled.hashCode() : 0);
        return result;
    }
}
