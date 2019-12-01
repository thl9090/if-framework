package com.yx.sys.model.vo;

import com.yx.sys.model.SysSmsLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 短信扩展类
 */
@ApiModel(value="SysSmsLogVO",description="短信记录扩展类")
public class SysSmsLogVO extends SysSmsLog{

    @ApiModelProperty(value="用户真实姓名")
    private String userRealName;


    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }
}
