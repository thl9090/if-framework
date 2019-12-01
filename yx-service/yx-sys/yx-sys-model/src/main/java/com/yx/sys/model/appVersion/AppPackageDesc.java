package com.yx.sys.model.appVersion;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;

/**
 * <p>
 * App包版本描述表
 * </p>
 *
 * @author YanBingHao
 * @since 2018-12-05
 */
@TableName("app_package_desc")
public class AppPackageDesc extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 包版本id
     */
    @TableField("package_version_id")
    private Long packageVersionId;
    /**
     * 包描述
     */
    @TableField("package_desc")
    private String packageDesc;
    /**
     * 描述index
     */
    @TableField("desc_index")
    private Integer descIndex;

    public Long getPackageVersionId() {
        return packageVersionId;
    }

    public void setPackageVersionId(Long packageVersionId) {
        this.packageVersionId = packageVersionId;
    }

    public String getPackageDesc() {
        return packageDesc;
    }

    public void setPackageDesc(String packageDesc) {
        this.packageDesc = packageDesc;
    }

    public Integer getDescIndex() {
        return descIndex;
    }

    public void setDescIndex(Integer descIndex) {
        this.descIndex = descIndex;
    }
}
