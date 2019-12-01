package com.yx.sys.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yx.common.utils.StringUtils;
import com.yx.sys.common.SmsConfig;
import com.yx.sys.common.SmsTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SmsUtil {

    private final static Logger log = LoggerFactory.getLogger(SmsUtil.class);

    private static String account = null;
    private static String password = null;
    private static String url = null;

    private static String codeAccount = null;
    private static String codePassword = null;
    private static String codeUrl = null;

    /**
     * 最底层的发短信的方法
     */
    public static JSONObject sendSms(Long phone, String content) {
        if (phone == null
                || StringUtils.isEmpty(content)
                || content.length() > 536) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("account", account);
        map.put("password", password);
        map.put("msg", content);
        map.put("phone", phone.toString());
        String requestJson = JSON.toJSONString(map);
        log.info("发送短信参数：" + requestJson);
        log.info("发送短信：【" + phone + "," + content + "】");
        String responseJson = ChuangLanSmsUtil.sendSmsByPost(url, requestJson);
        //----------------测试代码--------------start-
//        Map<String,Object> data=new HashMap<>();
//        data.put("code","0");
//        String responseJson=JSON.toJSONString(data);
        //--------------------------------------end
        return JSON.parseObject(responseJson);
    }

    /**
     * 发送多个验证码短信
     *
     * @param phones 多个手机号和验证码拼接字符 如 "phones":"17600253356,3065;17600253354,7853"
     */
    public static JSONObject sendCodeSms(String phones) {
        if (phones != null && !StringUtils.isEmpty(phones)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("account", codeAccount);
            map.put("password", codePassword);
            map.put("msg", SmsTypeEnum.VERIFYCODE.getModelValue());
            map.put("params", phones);
            String requestJson = JSON.toJSONString(map);
            log.info("发送多个验证码短信参数：" + requestJson);
            log.info("发送多个验证码短信：【" + phones + "】");
            String responseJson = ChuangLanSmsUtil.sendSmsByPost(codeUrl, requestJson);
            //----------------测试代码--------------start-
//            Map<String,Object> data=new HashMap<>();
//            data.put("code","0");
//            String responseJson=JSON.toJSONString(data);
            //--------------------------------------end
            return JSON.parseObject(responseJson);
        }
        return null;
    }

    /**
     * 发送单个验证码短信
     *
     * @param phone 手机号
     * @param code  验证码
     */
    public static JSONObject sendCodeSms(Long phone, Long code) {
        if (phone == null || code == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("account", codeAccount);
        map.put("password", codePassword);
        map.put("msg", SmsTypeEnum.VERIFYCODE.getModelValue());
        map.put("params", phone + "," + code);
        String requestJson = JSON.toJSONString(map);
        log.info("发送单个验证码短信参数：" + requestJson);
        log.info("发送单个验证码短信：【" + phone + "," + code + "】");
        String responseJson = ChuangLanSmsUtil.sendSmsByPost(codeUrl, requestJson);
        //----------------测试代码--------------start-
//        Map<String,Object> data=new HashMap<>();
//        data.put("code","0");
//        String responseJson=JSON.toJSONString(data);
        //--------------------------------------end
        return JSON.parseObject(responseJson);
    }


    /**
     * 加载类时获取发送短信必须的参数
     * @throws Exception
     */
    static {
        try {
            if (account == null || password == null || url == null) {
                account = SmsConfig.CODE_SMS_ACCOUNT;
                password = SmsConfig.CODE_SMS_PASSWORD;
                url = SmsConfig.SMS_URL;
                codeAccount = SmsConfig.CODE_SMS_ACCOUNT;
                codePassword = SmsConfig.CODE_SMS_PASSWORD;
                codeUrl = SmsConfig.CODE_SMS_URL;
                log.info("加载配置参数完毕：参数：\nserialNo：" + account + ",password：" + password + ",url：" + url + ",\n" +
                        "codeSerialNo：" + codeAccount + ", codePassword：" + codePassword + ", codeUrl：" + codeUrl);
            }
        } catch (Exception e) {
            log.info("加载配置参数错误：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
