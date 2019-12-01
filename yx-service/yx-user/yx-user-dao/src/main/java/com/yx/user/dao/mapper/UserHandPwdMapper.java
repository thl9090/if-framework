package com.yx.user.dao.mapper;

import com.yx.common.core.base.BaseMapper;
import com.yx.user.model.UserHandPwd;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户手势密码表 Mapper 接口
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-08-31
 */
public interface UserHandPwdMapper extends BaseMapper<UserHandPwd> {

    /**
     * 根据用户id查询手势密码
     * @param userId
     * @return
     */
    UserHandPwd selectByUserId(@Param("userId") Long userId);
}
