package com.yx.user.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseMapper;
import com.yx.user.model.UserLoginLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户登录日志
 */
public interface UserLoginLogMapper  {

    /**
     * 插入数据
     * @param userLoginLog
     * @return
     */
    int insertSelective(UserLoginLog userLoginLog);

    /**
     * 分页查询
     * @param page
     * @param userLoginLog
     * @return
     */
    List<UserLoginLog> selectPage(Page<UserLoginLog> page, @Param("userLoginLog") UserLoginLog userLoginLog);

    /**
     * 查询用户最近的登陆日志
     * @param userId
     * @return
     */
    UserLoginLog selectLateLogByUserId(@Param("userId") Long userId,@Param("tableName") String tableName);

}


