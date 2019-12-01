package com.yx.common.core.util;

/**
 * @Title: 正则表达式工具类
 * @Description:
 * @author TangHuaLiang
 * @Date: 17/12/29 16:17:06
 */
public class RegexUtil {

    /**
     * 返回根据Json的key获得value的正则表达式
     * 该表达式匹配如：{"password":"123456"}，则返回123456
     * @param jsonKey
     * @return java.lang.String
     * @author TangHuaLiang
     * @date 17/12/29 15:08:27
     */
    public static String getJSonValueRegex(String jsonKey){
        return "(?<=\""+ jsonKey +"\":\")[^\",]*";
    }
    /**
     * 返回根据Json的key获得Json的"key:value"的正则表达式
     * 该表达式匹配如：{"password":"123456"}，则返回"password":"123456"
     * @param jsonKey
     * @return java.lang.String
     * @author TangHuaLiang
     * @date 17/12/29 15:08:27
     */
    public static String getJSonRegex(String jsonKey){
        return "\""+ jsonKey +"\":\"([^\"]+)\"";
    }

    /**
     * 替换电话号码
     * 如：replaceAllPhoneNumer("idCard","\"idCard\":|"430101199012310000\"")
     * 返回结果："idCard":"430101****0000"
     * @param jsonKey, rawStr
     * @return java.lang.String
     * @author TangHuaLiang
     * @date 17/12/29 15:41:13
     */
    public static String replaceAllPhoneNumer(String jsonKey,String rawStr){
        return rawStr.replaceAll("\""+ jsonKey +"\":\"(\\d{3})(\\d{4})(\\d{4})\"","\""+ jsonKey +"\":\"$1****$3\"");
    }

    /**
     * 替换身份证号码
     * 如：replaceAllPhoneNumer("phone","\"phone\":|"18807318888\"")
     * 返回结果："phone":"188****8888"
     * @param jsonKey, rawStr
     * @return java.lang.String
     * @author TangHuaLiang
     * @date 17/12/29 15:41:13
     */
    public static String repalceAllidCard(String jsonKey,String rawStr){
        return rawStr.replaceAll("\""+ jsonKey +"\":\"(\\d{6})(?:\\d+)(\\d{4})\"","\""+ jsonKey +"\":\"$1****$2\"");
    }
}
