package com.yx.common.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 登录实体
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@ToString
public class LoginModel implements Serializable {
    private static final long serialVersionUID = 3951474329069548585L;

    /**
     * 用户名
     */
    @Getter
    @Setter
    @NotBlank(message = "账号不能为空")
    @Size(min = 4, max = 20, message = "账号长度必须在4至20之间")
    private String account;

    /**
     * 密码
     */
    @Getter
    @Setter
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 50, message = "密码长度必须在6至50之间")
    private String password;

    /**
     * 验证码ID
     */
    @Getter
    @Setter
    private String captchaId;

    /**
     * 验证码值
     */
    @Getter
    @Setter
    @NotBlank(message = "验证码不能为空")
    private String captchaValue;
}
