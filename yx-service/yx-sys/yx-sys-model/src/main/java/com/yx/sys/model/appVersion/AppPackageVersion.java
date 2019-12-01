package com.yx.sys.model.appVersion;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;

/**
 * <p>
 * App包版本表
 * </p>
 *
 * @author YanBingHao
 * @since 2018-12-05
 */
@TableName("app_package_version")
public class AppPackageVersion extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * app类型（枚举类：AppTypeEnum）
     */
    @TableField("app_type")
    private Integer appType;
    /**
     * 包版本
     */
    @TableField("package_version")
    private String packageVersion;
    /**
     * 包下载地址
     */
    @TableField("package_url")
    private String packageUrl;
    /**
     * 升级方式（枚举类：AppUpgradeModeEnum）
     */
    @TableField("upgrade_mode")
    private Integer upgradeMode;
    /**
     * 版本状态（枚举类：AppVersionStatusEnum）
     */
    @TableField("version_status")
    private Integer versionStatus;

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public String getPackageVersion() {
        return packageVersion;
    }

    public void setPackageVersion(String packageVersion) {
        this.packageVersion = packageVersion;
    }

    public String getPackageUrl() {
        return packageUrl;
    }

    public void setPackageUrl(String packageUrl) {
        this.packageUrl = packageUrl;
    }

    public Integer getUpgradeMode() {
        return upgradeMode;
    }

    public void setUpgradeMode(Integer upgradeMode) {
        this.upgradeMode = upgradeMode;
    }

    public Integer getVersionStatus() {
        return versionStatus;
    }

    public void setVersionStatus(Integer versionStatus) {
        this.versionStatus = versionStatus;
    }
}
