package com.yx.sys.model.appVersion;

import com.yx.common.core.base.BaseModel;

import java.util.List;

/**
 * 新增App版本
 *
 * @author YanBingHao
 * @since 2018/12/5
 */
public class AddAppVersionModel extends BaseModel {

    private static final long serialVersionUID = -5198032011908608233L;

    private Long versionId;
    private Integer appType;
    private String packageVersion;
    private String packageUrl;
    private Integer upgradeMode;
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

    public List<String> getDescList() {
        return descList;
    }

    public void setDescList(List<String> descList) {
        this.descList = descList;
    }
}
