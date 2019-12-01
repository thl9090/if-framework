package com.yx.user.rpc.api;

import com.yx.common.core.base.BaseService;
import com.yx.user.model.UserHandPwd;

/**
 * <p>
 * 用户手势密码表 服务类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-08-31
 */
public interface UserHandPwdService extends BaseService<UserHandPwd> {

    UserHandPwd selectByUserId(Long userId);



}
