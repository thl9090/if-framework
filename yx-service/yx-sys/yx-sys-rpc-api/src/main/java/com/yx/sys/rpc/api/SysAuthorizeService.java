package com.yx.sys.rpc.api;

import java.util.List;

/**
 * 使用一段文字进行描述
 *
 * @author TangHuaLiang
 * @create 2017-11-29
 **/
public interface SysAuthorizeService {

    /**
     * 根据用户ID获取权限
     *
     * @param userId
     * @return List<String>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    List<String> queryPermissionsByUserId(Long userId);
}
