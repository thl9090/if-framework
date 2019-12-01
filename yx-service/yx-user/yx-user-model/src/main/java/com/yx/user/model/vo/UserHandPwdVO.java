package com.yx.user.model.vo;

import com.yx.user.model.UserHandPwd;
import io.swagger.annotations.ApiModelProperty;

public class UserHandPwdVO extends UserHandPwd{

    @ApiModelProperty(value="确认密码")
    private String comfirmPassword;

    @ApiModelProperty(value="手机号码")
    private Long phone;

    public String getComfirmPassword() {
        return comfirmPassword;
    }

    public void setComfirmPassword(String comfirmPassword) {
        this.comfirmPassword = comfirmPassword;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
}
