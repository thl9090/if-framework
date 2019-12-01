package com.yx.sys.common.constant;

/**
 * @author YanBingHao
 * @since 2019/1/23
 */
public class ProductConstant {

    // 满标之后需要发送短信的平台运营人手机号码
    public static final String FULL_PRODUCT_REMIND_PLATFORM_PHONES_REDIS_KEY = "FULL_PRODUCT_REMIND_PLATFORM_PHONES";
    // 满标之后需要提醒的标的id
    public static final String FULL_PRODUCT_WAIT_REMIND_PRODUCT_IDS_REDIS_KEY = "FULL_PRODUCT_WAIT_REMIND_PRODUCT_IDS";
    // 满标之后已经提醒的标的id
    public static final String FULL_PRODUCT_REMINDED_PRODUCT_IDS_REDIS_KEY = "FULL_PRODUCT_REMINDED_PRODUCT_IDS";


}
