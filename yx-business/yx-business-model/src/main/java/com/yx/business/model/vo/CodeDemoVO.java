package com.yx.business.model.vo;

import com.yx.business.model.CodeDemo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 描述：demo扩展类
 * @author  TangHuaLiang
 * @date 2018-07-13
 */
@ApiModel(value="CodeDemoVO",description="demo扩展类")
public class CodeDemoVO extends CodeDemo {

    @ApiModelProperty(value="部门名称")
    private String userName;//用户名

    @ApiModelProperty(value="电话")
    private String phone;//电话

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
