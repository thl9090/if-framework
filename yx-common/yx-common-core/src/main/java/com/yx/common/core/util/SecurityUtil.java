package com.yx.common.core.util;


import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;

/**
 * 加解密工具类
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public class SecurityUtil {

    /**
     * 加密密码
     *
     * @param password
     * @return String
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    public static String encryptPassword(String password) {
        return encryptMd5(encryptSHA(password));
    }

    public static String encryptSHA(String data) {
        return Base64.encode(SecureUtil.sha1(data));
    }

    public static String encryptMd5(String data) {
        return Base64.encode(SecureUtil.md5(data));
    }

    public static void main(String[] args) {
        String password = "123456";
        System.out.println("SecurityUtil->encryptPassword->password:" + SecurityUtil.encryptPassword(password));
    }
}
