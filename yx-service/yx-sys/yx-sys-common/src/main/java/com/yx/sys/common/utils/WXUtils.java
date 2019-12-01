package com.yx.sys.common.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

/**
 * 微信工具类
 */
@Slf4j
public class WXUtils {

    public static String APPID="wx60dfdaa1a4027408";
            //"wx880858dad8f2bd4c";

    public static String SECRET="808b5e8cc4085d4314def34a6bda9ffc";
            //"20ddda06ae881e8345287817549ae8a5";

    /**
     * 生成token
     * @return
     * @throws Exception
     */
    public static String getWXAccessToken() throws Exception{
        String refreshTokenURL = "https://api.weixin.qq.com/cgi-bin/token?" +
                "grant_type=client_credential&appid="+ APPID +"&secret="+ SECRET;

        String result = HttpUtil.get(refreshTokenURL);
        log.info("===WXUtils========access_token:"+result);
        JSONObject jsonObject = JSONObject.parseObject(result);

        Object accessTokenObj = jsonObject.get("access_token");

        if (null == accessTokenObj) {
            return null;
        }
        String accessToken=accessTokenObj.toString();
        return accessToken;
    }


    public static  String getJsapiTicket(String accessToken) throws Exception{
        String getTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
                + accessToken+"&type=jsapi";
        String resultJsonString = HttpUtil.get(getTicketUrl);
        log.info("===WXUtils=========JsapiTicket:"+resultJsonString);
        JSONObject jsonObject = JSON.parseObject(resultJsonString);
        Object errmsg = jsonObject.get("errmsg");
        if (null != errmsg && errmsg.toString().equals("ok")) {
            String ticket = jsonObject.getString("ticket");
            return ticket;
        }
        return null;
    }


    public static String getSignature(String noncestr,String timestamp,String url,String jsAPITicket){
        Arrays.sort(noncestr.getBytes());
        Arrays.sort(timestamp.getBytes());
        Arrays.sort(url.getBytes());
        Arrays.sort(jsAPITicket.getBytes());
        String sortStr = "jsapi_ticket="+ jsAPITicket +"&noncestr="+ noncestr +"&timestamp="+ timestamp +"&url="+ url;
        return DigestUtils.sha1Hex(sortStr);
    }





}
