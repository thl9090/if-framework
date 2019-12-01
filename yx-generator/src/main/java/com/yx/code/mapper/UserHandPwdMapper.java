package com.yx.code.mapper;

import com.yx.code.entity.UserHandPwd;
import com.yx.common.core.base.BaseMapper;

import java.io.Serializable;

/**
 * <p>
 * 用户手势密码表 Mapper 接口
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-08-31
 */
public interface UserHandPwdMapper extends BaseMapper<UserHandPwd> {

    UserHandPwd selectByUserId(Long userId);
}
