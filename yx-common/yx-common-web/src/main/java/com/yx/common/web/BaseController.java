package com.yx.common.web;

import com.yx.common.web.util.WebUtil;

/**
 * 控制器基类
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public abstract class BaseController {

    /**
     * 获取当前用户Id
     *
     * @return Long
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    protected Long getCurrentUserId() {
        return WebUtil.getCurrentUserId();
    }

    /**
     * 获取当前用户
     *
     * @return Object
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    protected Object getCurrentUser() {
        return WebUtil.getCurrentUser();
    }
}
