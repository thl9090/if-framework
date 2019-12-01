package com.yx.user.rpc.service.impl;

import com.yx.common.core.base.BaseServiceImpl;
import com.yx.user.dao.mapper.UserHandPwdMapper;
import com.yx.user.model.UserHandPwd;
import com.yx.user.rpc.api.UserHandPwdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户手势密码表 服务实现类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-08-31
 */
@Service("userHandPwdService")
public class UserHandPwdServiceImpl extends BaseServiceImpl<UserHandPwdMapper, UserHandPwd> implements UserHandPwdService {

    @Autowired
    private UserHandPwdMapper userHandPwdMapper;


    @Override
    public UserHandPwd selectByUserId(Long userId) {
        return userHandPwdMapper.selectByUserId(userId);
    }



}
