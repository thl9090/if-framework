package com.yx.common.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统通用常量
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public final class Constants {

    /**
     * 当前用户
     */
    public static final String CURRENT_USER = "CURRENT_USER";
    /**
     * 当前用户
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * 当前用户(APP)
     */
    public static final String APP_CURRENT_USER = "APP_CURRENT_USER";

    /**
     * 当前用户(WX)
     */
    public static final String WX_CURRENT_USER = "WX_CURRENT_USER";

    /**
     * 当前用户(WEB)
     */
    public static final String WEB_CURRENT_USER = "WEB_CURRENT_USER";

    /**
     * 当前用户token
     */
    public static final String CURRENT_USER_TOKEN = "CURRENT_USER_TOKEN";


    /**
     * mapper方法中查询语句开头格式，必须以select开头，才会切换数据源
     */
    public static final String MAPPER_METHOD_STARTSWITH_SELECT = "select";

    /**
     * 缓存键值
     */
    public static final Map<Class<?>, String> CACHE_KEY_MAP = new HashMap<>(5);

    /**
     * 缓存命名空间前缀
     */
    public static final String CACHE_NAMESPACE_PREFIX = "yx:";

    /**
     * 缓存命名空间枚举
     */
    public enum CacheNamespaceEnum {
        /**
         * 数据缓存
         */
        DATA(CACHE_NAMESPACE_PREFIX + "data:", "数据缓存"),
        LOCK(CACHE_NAMESPACE_PREFIX + "lock:", "分布式锁"),
        TOKEN(CACHE_NAMESPACE_PREFIX + "token:", "票据"),
        CAPTCHA(CACHE_NAMESPACE_PREFIX + "captcha:", "验证码"),
        SHIRO(CACHE_NAMESPACE_PREFIX + "shiro:", "shiro"),
        IP(CACHE_NAMESPACE_PREFIX + "ip:", "ip归属地"),

        SMS_CAPTCHA(CACHE_NAMESPACE_PREFIX + "sms_captcha:", "短信验证码"),
        SMS_CAPTCHA_PHONE(CACHE_NAMESPACE_PREFIX + "sms_captcha_phone:", "短信验证码对应的手机号"),
        HAND_PWD_COUNT(CACHE_NAMESPACE_PREFIX + "hand_pwd_count:", "手势密码失败次数"),
        USER_LOGIN_TIME(CACHE_NAMESPACE_PREFIX + "user_login_time:", "用户最近登陆时间"),
        SESSION_USER_ID(CACHE_NAMESPACE_PREFIX+"session_user_id:","session对应的用户id"),
        SESSION_USER(CACHE_NAMESPACE_PREFIX+"session_user:","session对应的用户"),
        USER_ID_SESSION(CACHE_NAMESPACE_PREFIX+"user_id_session:","用户id对应的session"),

        ACCOUNT_LOGIN_FAIL_COUNT(CACHE_NAMESPACE_PREFIX+"account_login_fail_count:","系统用户登陆失败次数"),
        PLATFORM_COUNT_MONEY(CACHE_NAMESPACE_PREFIX+"platform_count_money:","平台累计交易金额"),
        USER_COUNT_MONEY(CACHE_NAMESPACE_PREFIX+"user_account_money:","累计为用户赚取金额"),
        USER_SEND_EMAIL_COUNT(CACHE_NAMESPACE_PREFIX+"user_send_email_count:","用户发送邮件记录次数"),
        USER_SEND_SMS_COUNT(CACHE_NAMESPACE_PREFIX+"user_send_sms_count:","用户发送短信次数"),
        CLIENT_IP_LOGIN_COUNT(CACHE_NAMESPACE_PREFIX+"client_ip_login_count:","客户端ip请求登陆接口的次数"),
        ;

        /**
         * 值
         */
        private String value;
        /**
         * 描述
         */
        private String message;

        CacheNamespaceEnum(String value, String message) {
            this.value = value;
            this.message = message;
        }

        public String value() {
            return this.value;
        }

        public String getMessage() {
            return this.message;
        }
    }

    /**
     * 返回码枚举
     */
    public enum ResultCodeEnum {
        /**
         * 成功
         */
        SUCCESS(200, "成功"),
        INTERNAL_SERVER_ERROR(500, "服务器异常"),
        PARAM_ERROR(400, "请求参数出错"),
        NO_SUPPORTED_MEDIATYPE(415, "不支持的媒体类型,请使用application/json;charset=UTF-8"),
        KICK_OUT(300,"账号在其它地方登陆"),
        LOGIN_FAIL(303, "登录失败"),
        LOGIN_FAIL_ACCOUNT_LOCKED(304, "用户被锁定"),
        LOGIN_FAIL_ACCOUNT_DISABLED(305, "用户未启用"),
        ACCESSTOKEN_EXPIRED(306, "accessToken已失效"),
        LOGIN_FAIL_ACCOUNT_UNKNOWN(307, "用户信息不存在"),
        LOGIN_FAIL_INCORRECT_CREDENTIALS(308, "密码不正确"),
        LOGIN_FAIL_CAPTCHA_ERROR(309, "验证码错误"),
        UNLOGIN(401, "没有登录"),
        UNAUTHORIZED(403, "未通过授权"),
        DECRYPTION_FAILED(405, "解密accessToken失败"),
        ACCESSTOKEN_EMPTY(406, "accessToken信息为空"),
        DATA_DUPLICATE_KEY(601, "数据重复"),
        DATA_NULL_KEY(602, "空数据");
        private final int value;
        private final String message;

        ResultCodeEnum(int value, String message) {
            this.value = value;
            this.message = message;
        }

        public int value() {
            return this.value;
        }

        public String getMessage() {
            return this.message;
        }

    }

    /**
     * 多数据源枚举
     */
    public enum DataSourceEnum {
        // 主库
        MASTER("masterDataSource", true),
        // 从库
        SLAVE("slaveDataSource", false);

        /**
         * 数据源名称
         */
        private String name;
        /**
         * 是否是默认数据源
         */
        private boolean master;

        DataSourceEnum(String name, boolean master) {
            this.name = name;
            this.master = master;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isMaster() {
            return master;
        }

        public void setMaster(boolean master) {
            this.master = master;
        }

        public String getDefault() {
            String defaultDataSource = "";
            for (DataSourceEnum dataSourceEnum : DataSourceEnum.values()) {
                if (!"".equals(defaultDataSource)) {
                    break;
                }
                if (dataSourceEnum.master) {
                    defaultDataSource = dataSourceEnum.getName();
                }
            }
            return defaultDataSource;
        }

    }

    /**
     * 日志操作类型枚举
     */
    public enum LogOptEnum {
        //查询
        QUERY(0, "查询"),
        ADD(1, "新增"),
        MODIFY(2, "修改"),
        DELETE(3, "删除"),
        LOGIN(4, "登录"),
        UNKNOW(9, "未知");
        private final int value;
        private final String message;

        LogOptEnum(int value, String message) {
            this.value = value;
            this.message = message;
        }

        public int value() {
            return this.value;
        }

        public String getMessage() {
            return this.message;
        }

    }

}
