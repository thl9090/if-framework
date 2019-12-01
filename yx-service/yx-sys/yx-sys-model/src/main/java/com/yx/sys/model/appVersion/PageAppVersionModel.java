package com.yx.sys.model.appVersion;

import com.yx.common.core.base.BaseModel;

import java.util.List;

/**
 * 新增App版本
 *
 * @author YanBingHao
 * @since 2018/12/5
 */
public class PageAppVersionModel extends BaseModel {

    private static final long serialVersionUID = -5198032011908608233L;

    private Long versionId;
    private Integer appType;
    private String appTypeStr;
    private String packageVersion;
    private String packageUrl;
    private Integer upgradeMode;
    private String upgradeModeStr;
    private Integer versionStatus;
    private String versionStatusStr;

    private List<String> descList;

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public String getAppTypeStr() {
        return appTypeStr;
    }

    public void setAppTypeStr(String appTypeStr) {
        this.appTypeStr = appTypeStr;
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

    public String getUpgradeModeStr() {
        return upgradeModeStr;
    }

    public void setUpgradeModeStr(String upgradeModeStr) {
        this.upgradeModeStr = upgradeModeStr;
    }

    public Integer getVersionStatus() {
        return versionStatus;
    }

    public void setVersionStatus(Integer versionStatus) {
        this.versionStatus = versionStatus;
    }

    public String getVersionStatusStr() {
        return versionStatusStr;
    }

    public void setVersionStatusStr(String versionStatusStr) {
        this.versionStatusStr = versionStatusStr;
    }

    public List<String> getDescList() {
        return descList;
    }

    public void setDescList(List<String> descList) {
        this.descList = descList;
    }
}
