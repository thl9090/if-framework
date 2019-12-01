package com.yx.common.core.constant;

public class AuthorizationConstant {

    public static final String ACCESS_TOKEN_AES_ENCRYPT_KEY = "yx!AT]2019./";

    public static final String ALIVE_ACCESS_TOKEN_PREFIX = "ALIVE_ACCESS_TOKEN:";

    public static final String ACCESS_TOKEN_CACHE_PREFIX = "USER_ACCESS_TOKEN:";

    public static final String ACCESS_TOKEN_HEADER = "Access_Token";

    public static final String CHANNEL_TYPE_HEADER = "Channel_Type";

    public static final String CHANNEL_VERSION_HEADER = "Channel_Version";

    public static final String DERVICE_NAME_HEADER = "Dervice_Name";


    public static final String USER_ID = "user_id";

    public static final String CHANNEL_TYPE = "channel_type";

    public static final Long ADMIN_AUTH_EXPIRE_TIME = 30 * 60L;

    public static final Long WEB_AUTH_EXPIRE_TIME = 30 * 60L;

    public static final Long APP_AUTH_EXPIRE_TIME = 30 * 60L;

    public static final Long WX_AUTH_EXPIRE_TIME = 30 * 60L;

    // 是否校验单点登录
    public static final String IS_SINGLE_SIGN_ON_KEY = "IS_SINGLE_SIGN_ON";

    /**
     * 设置用户是否只能同时登陆一台设备
     * 如果true:同一统一用户只能同时登陆一台设备
     * 如果false:同一用户可以同时登陆多台设备
     */
    public static final boolean ADMIN_SSO = false;
    public static final boolean WEB_SSO = false;
    public static final boolean APP_SSO = false;
    public static final boolean WX_SSO = false;

    /**
     * 用户被踢出标识
     */
    public static final String KICKOUT = "kickout";


}
