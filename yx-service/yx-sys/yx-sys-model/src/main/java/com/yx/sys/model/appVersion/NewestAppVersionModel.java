package com.yx.sys.model.appVersion;

import com.yx.common.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * App端查询版本信息
 *
 * @author YanBingHao
 * @since 2018/12/5
 */
@ApiModel(value = "App端查询版本信息")
public class NewestAppVersionModel extends BaseModel {

    private static final long serialVersionUID = -5198032011908608233L;

    @ApiModelProperty(value = "app类型；1：安卓；2：IOS；")
    private Integer appType;

    @ApiModelProperty(value = "当前版本号")
    private String currentVersion;

    @ApiModelProperty(value = "升级版本号")
    private String upgradeVersion;

    @ApiModelProperty(value = "升级包的下载地址")
    private String upgradePackageUrl;

    @ApiModelProperty(value = "升级方式；1：强制；2：提示；")
    private Integer upgradeMode;

    @ApiModelProperty(value = "升级信息集合")
    private List<String> upgradeDescList;

    @ApiModelProperty(value = "升级信息")
    private String upgradeDesc;

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getUpgradeVersion() {
        return upgradeVersion;
    }

    public void setUpgradeVersion(String upgradeVersion) {
        this.upgradeVersion = upgradeVersion;
    }

    public String getUpgradePackageUrl() {
        return upgradePackageUrl;
    }

    public void setUpgradePackageUrl(String upgradePackageUrl) {
        this.upgradePackageUrl = upgradePackageUrl;
    }

    public Integer getUpgradeMode() {
        return upgradeMode;
    }

    public void setUpgradeMode(Integer upgradeMode) {
        this.upgradeMode = upgradeMode;
    }

    public List<String> getUpgradeDescList() {
        return upgradeDescList;
    }

    public void setUpgradeDescList(List<String> upgradeDescList) {
        this.upgradeDescList = upgradeDescList;
    }

    public String getUpgradeDesc() {
        return upgradeDesc;
    }

    public void setUpgradeDesc(String upgradeDesc) {
        this.upgradeDesc = upgradeDesc;
    }
}
