package com.yx.sys.dao.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限mapper
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 **/
public interface SysAuthorizeMapper {

    /**
     * 根据用户ID获取用户权限集合
     *
     * @param userId 用户ID
     * @return List<String>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    List<String> selectPermissionsByUserId(@Param("userId") Long userId);
}
