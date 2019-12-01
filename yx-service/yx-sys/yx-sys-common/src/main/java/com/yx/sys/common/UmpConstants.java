package com.yx.sys.common;

/**
 * 通用常量类
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public class UmpConstants {

    /**
     * 超级管理员权限标识
     */
    public static final String PERMISSION_ADMIN = "sys:admin";
    /**
     * 超级管理员用户编号
     */
    public static final Long USERID_ADMIN = 1L;

    /**
     * 权限模块使用的缓存名
     */
    public class UmpCacheName {
        public static final String USER = "sys_user";
        public static final String MENU = "sys_menu";
        public static final String DEPT = "sys_dept";
        public static final String DIC = "sys_dic";
        public static final String PARAM = "sys_param";
        public static final String ROLE = "sys_role";
        public static final String USER_ROLE = "sys_user_role";
        public static final String ROLE_MENU = "sys_role_menu";
        public static final String AUTHOR = "sys_author";
    }
}
