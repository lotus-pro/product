package com.platform.config.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ProductRole implements Serializable {

    private static final long serialVersionUID = 3191452834109302550L;

    @TableId("ROLE_CODE")
    @NotBlank(
            message = "{pcmc.role.valid.0001}"
    )
    private String roleCode;
    @NotBlank(
            message = "{pcmc.role.valid.0002}"
    )
    private String roleName;
    private String appCode;
    @NotBlank(
            message = "{pcmc.role.valid.0004}"
    )
    private String roleType;
    private String copyRoleSource;
    private String homePageUrl;
    private String isEnabled = "1";

    @Override
    public String toString() {
        return "ProductRole{" +
                "roleCode='" + roleCode + '\'' +
                ", roleName='" + roleName + '\'' +
                ", appCode='" + appCode + '\'' +
                ", roleType='" + roleType + '\'' +
                ", copyRoleSource='" + copyRoleSource + '\'' +
                ", homePageUrl='" + homePageUrl + '\'' +
                ", isEnabled='" + isEnabled + '\'' +
                '}';
    }

    public ProductRole() {
    }

    public ProductRole(final String roleCode, final String roleName, final String appCode, final String roleType, final String copyRoleSource, final String homePageUrl, final String isEnabled) {
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.appCode = appCode;
        this.roleType = roleType;
        this.copyRoleSource = copyRoleSource;
        this.homePageUrl = homePageUrl;
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductRole that = (ProductRole) o;

        if (roleCode != null ? !roleCode.equals(that.roleCode) : that.roleCode != null) return false;
        if (roleName != null ? !roleName.equals(that.roleName) : that.roleName != null) return false;
        if (appCode != null ? !appCode.equals(that.appCode) : that.appCode != null) return false;
        if (roleType != null ? !roleType.equals(that.roleType) : that.roleType != null) return false;
        if (copyRoleSource != null ? !copyRoleSource.equals(that.copyRoleSource) : that.copyRoleSource != null)
            return false;
        if (homePageUrl != null ? !homePageUrl.equals(that.homePageUrl) : that.homePageUrl != null) return false;
        return isEnabled != null ? isEnabled.equals(that.isEnabled) : that.isEnabled == null;
    }

    @Override
    public int hashCode() {
        int result = roleCode != null ? roleCode.hashCode() : 0;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (appCode != null ? appCode.hashCode() : 0);
        result = 31 * result + (roleType != null ? roleType.hashCode() : 0);
        result = 31 * result + (copyRoleSource != null ? copyRoleSource.hashCode() : 0);
        result = 31 * result + (homePageUrl != null ? homePageUrl.hashCode() : 0);
        result = 31 * result + (isEnabled != null ? isEnabled.hashCode() : 0);
        return result;
    }
}
