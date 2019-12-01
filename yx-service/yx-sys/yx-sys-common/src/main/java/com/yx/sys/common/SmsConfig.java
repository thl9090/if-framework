package com.yx.sys.common;

public class SmsConfig
{

    /**
     * 短信类型，如：亿美、建周、玄武
     */
    public static final String SMS_TYPE = "yx";

    /**
     * 当日短信发送最大次数
     */
    public static final String SMS_LIMIT = "sms_limit";

    /**
     * 注册或者更换手机时每日发送上限
     */
    public static final String SMS_LIMIT_UNACTIVATED = "sms_limit_unactivated";

    /**
     * 非注册或者更换手机时默认每日发送上限
     */
    public static final int DEFAULT_SMS_LIMIT_UNACTIVATED = 5;

    /**
     * 用户的默认单日短信发送上限
     */
    public static final int DEFAULT_SMS_LIMIT = 20;

    /**
     * 投标短信模板
     */
    public static final String SMS_TEM_BID = "sms_tem_bid";

    /**
     * 系统自动撤标短信模板
     */
    public static final String SMS_TEM_SYSTEM_WITHDRAWBID_SMS = "sms_tem_system_withdrawbid_sms";

    /**
     * 回款短信通知
     */
    public static final String SMS_TEM_RECEIPT = "sms_tem_receipt";

    /**
     * 还款短信模板
     */
    public static final String SMS_TEM_REPAY = "sms_tem_repay";

    /**
     * 用户主动撤标短信模板
     */
    public static final String SMS_TEM_USER_WITHDRAWBID_SMS = "sms_tem_user_withdrawbid_sms";

    /**
     * 验证码有效时间，单位为秒
     */
    public static final String TIME_VCODE_LIMIT = "time_vcode_limit";

    /**
     * 默认验证码有效时间，单位为秒
     */
    public static final int DEFAULT_TIME_VCODE_LIMIT = 300;

    /**
     * 发送普通短信用户名
     */
//    public static final java.lang.String SMS_ACCOUNT = "DK4716910"; dk短信类型不能发送北京、广州的用户，占时不用废弃

    /**
     * 发送普通短信的密码
     */
//    public static final java.lang.String SMS_PASSWORD = "2TpgErxyw";

    /**
     * 发送普通短信接口的根url
     */
    public static final java.lang.String SMS_URL = "http://smssh1.253.com/msg/send/json";

    /**
     * 发送普通短信用户名
     */
    public static final java.lang.String CODE_SMS_ACCOUNT = "yourpwd";

    /**
     * 发送普通短信的密码
     */
    public static final java.lang.String CODE_SMS_PASSWORD = "yourpwd";

    /**
     * 发送普通短信接口的根url
     */
    public static final java.lang.String CODE_SMS_URL = "http://smssh1.253.com/msg/variable/json";


}
