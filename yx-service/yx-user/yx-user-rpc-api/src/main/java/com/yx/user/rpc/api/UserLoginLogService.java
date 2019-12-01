package com.yx.user.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseService;
import com.yx.user.model.ClientInfo;
import com.yx.user.model.UserInfo;
import com.yx.user.model.UserLoginLog;

/**
 * 用户登录日志
 */
public interface UserLoginLogService {

    int insertSelective(UserLoginLog userLoginLog);

    /**
     * 添加登陆日志
     * @param userId
     * @param clientInfo
     */
    void addLoginLog(Long userId, ClientInfo clientInfo);

    /**
     * 分页查询
     * @param page
     * @param userLoginLog
     * @return
     */
    Page<UserLoginLog> selectPage(Page<UserLoginLog> page, UserLoginLog userLoginLog);

    /**
     * 查询用户最新的登陆日志
     * @param userId
     * @return
     */
    UserLoginLog selectLateLogByUserId(Long userId);


}
